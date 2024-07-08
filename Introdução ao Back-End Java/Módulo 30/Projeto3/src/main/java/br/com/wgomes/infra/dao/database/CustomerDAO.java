package br.com.wgomes.infra.dao.database;

import br.com.wgomes.annotations.Table;
import br.com.wgomes.domain.dao.ICustomerDAO;
import br.com.wgomes.domain.entity.CustomerEntity;
import br.com.wgomes.infra.dao.base.BaseDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class CustomerDAO extends BaseDAO<CustomerEntity, UUID> implements ICustomerDAO {
    private final String tableName = CustomerEntity.class.getAnnotation(Table.class).name();

    @Override
    public Optional<CustomerEntity> findByCpf(String cpf) throws Exception {
        Connection connection = getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = String.format("SELECT * FROM %s WHERE cpf = ?", tableName);
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();
            if (rs.next()) {
                UUID customerId = UUID.fromString(rs.getString("customer_id"));
                String name = rs.getString("name");
                return Optional.of(new CustomerEntity(customerId, name, cpf));
            }
        } finally {
            closeConnection(stmt, rs);
        }
        return Optional.empty();
    }

    @Override
    public Class<CustomerEntity> getEntityClass() {
        return CustomerEntity.class;
    }

    @Override
    protected String getInsertCommand() {
        return String.format("INSERT INTO %s (customer_id, name, cpf) VALUES (?, ?, ?)", tableName);
    }

    @Override
    protected String getDeleteCommand() {
        return String.format("DELETE FROM %s WHERE customer_id = ?", tableName);
    }

    @Override
    protected String getUpdateCommand() {
        return String.format("UPDATE %s SET name = ?, cpf = ? WHERE customer_id = ?", tableName);
    }

    @Override
    protected void setInsertCommandParams(PreparedStatement stmt, CustomerEntity entity) throws SQLException {
        stmt.setString(1, entity.getCustomerId().toString());
        stmt.setString(2, entity.getName());
        stmt.setString(3, entity.getCpf());
    }

    @Override
    protected void setDeleteCommandParams(PreparedStatement stmt, UUID value) throws SQLException {
        stmt.setString(1, value.toString());
    }

    @Override
    protected void setUpdateCommandParams(PreparedStatement stmt, CustomerEntity entity) throws SQLException {
        stmt.setString(1, entity.getName());
        stmt.setString(2, entity.getCpf());
        stmt.setString(3, entity.getCustomerId().toString());
    }

    @Override
    protected void setQueryParams(PreparedStatement stmt, UUID value) throws SQLException {
        stmt.setString(1, value.toString());
    }
}
