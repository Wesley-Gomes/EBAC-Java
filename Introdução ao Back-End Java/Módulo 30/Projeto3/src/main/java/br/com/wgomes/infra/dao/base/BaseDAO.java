package br.com.wgomes.infra.dao.base;

import br.com.wgomes.annotations.PrimaryKey;
import br.com.wgomes.annotations.Table;
import br.com.wgomes.annotations.TableColumn;
import br.com.wgomes.domain.dao.base.IDAOBase;
import br.com.wgomes.domain.factories.ConnectionFactory;
import br.com.wgomes.exceptions.*;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
public abstract class BaseDAO<T, I> implements IDAOBase<T, I> {
    private Connection connection;

    private PreparedStatement stmt;

    private ResultSet rs;

    public abstract Class<T> getEntityClass();

    protected abstract String getInsertCommand();

    protected abstract String getDeleteCommand();

    protected abstract String getUpdateCommand();

    protected abstract void setInsertCommandParams(PreparedStatement stmt, T entity) throws SQLException;

    protected abstract void setDeleteCommandParams(PreparedStatement stmt, I value) throws SQLException;

    protected abstract void setUpdateCommandParams(PreparedStatement stmt, T entity) throws SQLException;

    protected abstract void setQueryParams(PreparedStatement stmt, I value) throws SQLException;

    public String getKeyFieldName() {
        Field[] fields = getEntityClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(PrimaryKey.class) &&
                    field.isAnnotationPresent(TableColumn.class)) {
                TableColumn column = field.getAnnotation(TableColumn.class);
                return column.name();
            }
        }
        return null;
    }

    private void setEntityField(@NotNull T entity, @NotNull Field field, ResultSet rs, String dbName) throws Exception {
        TableColumn column = field.getAnnotation(TableColumn.class);
        String javaSetName = column.setJavaName();
        Class<?> classField = field.getType();
        Method method = entity.getClass().getMethod(javaSetName, classField);
        setValueByType(entity, method, classField, rs, dbName);
    }

    private void setValueByType(T entity, Method method, @NotNull Class<?> classField, ResultSet rs, String fieldName) throws ElementTypeNotKnownException, SQLException, InvocationTargetException, IllegalAccessException {
        if (classField.equals(Integer.class)) {
            Integer value = rs.getInt(fieldName);
            method.invoke(entity, value);
        } else if (classField.equals(Long.class)) {
            Long value = rs.getLong(fieldName);
            method.invoke(entity, value);
        } else if (classField.equals(Double.class)) {
            Double value = rs.getDouble(fieldName);
            method.invoke(entity, value);
        } else if (classField.equals(Short.class)) {
            Short value = rs.getShort(fieldName);
            method.invoke(entity, value);
        } else if (classField.equals(BigDecimal.class)) {
            BigDecimal value = rs.getBigDecimal(fieldName);
            method.invoke(entity, value);
        } else if (classField.equals(String.class)) {
            String value = rs.getString(fieldName);
            method.invoke(entity, value);
        } else if (classField.equals(UUID.class)) {
            UUID value = UUID.fromString(rs.getString(fieldName));
            method.invoke(entity, value);
        } else {
            throw new ElementTypeNotKnownException("Class Type Unknown: " + classField);
        }
    }

    protected Connection getConnection() throws ConnectionException {
        try {
            return ConnectionFactory.getConnection();
        } catch (Exception e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    protected void closeConnection(PreparedStatement stmt, ResultSet rs) throws SQLException, ConnectionException {
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }
        if (stmt != null && !stmt.isClosed()) {
            stmt.close();
        }
        ConnectionFactory.closeConnection();
    }

    private String getTableName() throws TableException {
        if (getEntityClass().isAnnotationPresent(Table.class)) {
            Table table = getEntityClass().getAnnotation(Table.class);
            return table.name();
        } else {
            throw new TableException("Table in type " + getEntityClass().getName() + " Not Found!");
        }
    }

    @Override
    public void save(T entity) throws Exception {
        try {
            connection = getConnection();
            String sql = getInsertCommand();
            stmt = connection.prepareStatement(sql);
            setInsertCommandParams(stmt, entity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Insert error: " + e.getMessage());
        } finally {
            closeConnection(stmt, rs);
        }
    }

    @Override
    public void update(T entity) throws Exception {
        try {
            connection = getConnection();
            String sql = getUpdateCommand();
            stmt = connection.prepareStatement(sql);
            setUpdateCommandParams(stmt, entity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Update error: " + e.getMessage());
        } finally {
            closeConnection(stmt, rs);
        }
    }

    @Override
    public void delete(I id) throws Exception {
        try {
            connection = getConnection();
            String sql = getDeleteCommand();
            stmt = connection.prepareStatement(sql);
            setDeleteCommandParams(stmt, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Delete error: " + e.getMessage());
        } finally {
            closeConnection(stmt, rs);
        }
    }

    @Override
    public boolean existsById(I id) throws Exception {
        return findById(id).isPresent();
    }

    @Override
    public Optional<T> findById(I id) throws Exception {
        try {
            connection = getConnection();
            String sql = String.format("SELECT * FROM %s WHERE %s = ?", getTableName(), getKeyFieldName());
            stmt = connection.prepareStatement(sql);
            setQueryParams(stmt, id);
            rs = stmt.executeQuery();
            int count = 1;
            T entity = null;
            while (rs.next()) {
                if (count > 1) throw new MoreThanOneRecordException("More than one result found for ID: " + id);
                entity = getEntityClass().getConstructor().newInstance();
                Field[] fields = entity.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(TableColumn.class)) {
                        setEntityField(entity, field, rs, field.getAnnotation(TableColumn.class).name());
                    }
                }
                count++;
            }
            return entity != null ? Optional.of(entity) : Optional.empty();
        } catch (Exception e) {
            throw new DAOException("Find error: " + e.getMessage());
        } finally {
            closeConnection(stmt, rs);
        }
    }

    @Override
    public List<T> findAll() throws Exception {
        List<T> list = new ArrayList<>();
        try {
            connection = getConnection();
            String sql = String.format("SELECT * FROM %s", getTableName());
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                T entity = getEntityClass().getConstructor().newInstance();
                Field[] fields = entity.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(TableColumn.class)) {
                        setEntityField(entity, field, rs, field.getAnnotation(TableColumn.class).name());
                    }
                }
                list.add(entity);
            }
        } catch (Exception e) {
            throw new DAOException("Find all error: " + e.getMessage());
        } finally {
            closeConnection(stmt, rs);
        }
        return list;
    }
}
