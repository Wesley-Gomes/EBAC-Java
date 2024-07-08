package br.com.wgomes.infra.dao.database;

import br.com.wgomes.annotations.Table;
import br.com.wgomes.domain.dao.IProductDAO;
import br.com.wgomes.domain.entity.ProductEntity;
import br.com.wgomes.infra.dao.base.BaseDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO extends BaseDAO<ProductEntity, String> implements IProductDAO {
    private final String tableName = ProductEntity.class.getAnnotation(Table.class).name();

    @Override
    public Class<ProductEntity> getEntityClass() {
        return ProductEntity.class;
    }

    @Override
    protected String getInsertCommand() {
        return String.format("INSERT INTO %s (product_id, name, description, price, unit_meas) VALUES (?, ?, ?, ?, ?)", tableName);
    }

    @Override
    protected String getDeleteCommand() {
        return String.format("DELETE FROM %s WHERE product_id = ?", tableName);
    }

    @Override
    protected String getUpdateCommand() {
        return String.format("UPDATE %s SET name = ?, description = ?, price = ?, unit_meas = ? WHERE product_id = ?", tableName);
    }

    @Override
    protected void setInsertCommandParams(PreparedStatement stmt, ProductEntity entity) throws SQLException {
        stmt.setString(1, entity.getProductId());
        stmt.setString(2, entity.getName());
        stmt.setString(3, entity.getDescription());
        stmt.setDouble(4, entity.getPrice());
        stmt.setString(5, entity.getUnitMeas());
    }

    @Override
    protected void setDeleteCommandParams(PreparedStatement stmt, String id) throws SQLException {
        stmt.setString(1, id);
    }

    @Override
    protected void setUpdateCommandParams(PreparedStatement stmt, ProductEntity entity) throws SQLException {
        stmt.setString(1, entity.getName());
        stmt.setString(2, entity.getDescription());
        stmt.setDouble(3, entity.getPrice());
        stmt.setString(4, entity.getUnitMeas());
        stmt.setString(5, entity.getProductId());
    }

    @Override
    protected void setQueryParams(PreparedStatement stmt, String id) throws SQLException {
        stmt.setString(1, id);
    }
}
