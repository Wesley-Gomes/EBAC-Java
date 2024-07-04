package br.com.wgomes.infra.dao.database;

import br.com.wgomes.config.singleton.ConnectionFactory;
import br.com.wgomes.domain.dao.IProductDAO;
import br.com.wgomes.domain.entity.ProductEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO implements IProductDAO {
    private final String tableName = "PRODUCT";
    private Connection connection;
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public void save(ProductEntity entity) throws Exception {
        try {
            connection = getConnection();
            String sql = String.format("INSERT INTO %s (product_id, name, description, price, unit_meas) VALUES (?, ?, ?, ?, ?)", tableName);
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, entity.getProductId());
            stmt.setString(2, entity.getName());
            stmt.setString(3, entity.getDescription());
            stmt.setDouble(4, entity.getPrice());
            stmt.setString(5, entity.getUnitMeas());
            stmt.executeUpdate();
        } finally {
            closeConnection(stmt, rs);
        }
    }

    @Override
    public void update(ProductEntity entity) throws Exception {
        try {
            connection = getConnection();
            String sql = String.format("UPDATE %s SET name = ?, description = ?, price = ?, unit_meas = ? WHERE product_id = ?", tableName);
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescription());
            stmt.setDouble(3, entity.getPrice());
            stmt.setString(4, entity.getUnitMeas());
            stmt.setString(5, entity.getProductId());
            stmt.executeUpdate();
        } finally {
            closeConnection(stmt, rs);
        }
    }

    @Override
    public void delete(String id) throws Exception {
        try {
            connection = getConnection();
            String sql = String.format("DELETE FROM %s WHERE product_id = ?", tableName);
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.executeUpdate();
        } finally {
            closeConnection(stmt, rs);
        }
    }

    @Override
    public boolean existsById(String id) throws Exception {
        try {
            connection = getConnection();
            String sql = String.format("SELECT * FROM %s WHERE product_id = ?", tableName);
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            return rs.next();
        } finally {
            closeConnection(stmt, rs);
        }
    }

    @Override
    public Optional<ProductEntity> findById(String id) throws Exception {
        try {
            connection = getConnection();
            String sql = String.format("SELECT * FROM %s WHERE product_id = ?", tableName);
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new ProductEntity(
                        rs.getString("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("unit_meas")
                ));
            }
            return Optional.empty();
        } finally {
            closeConnection(stmt, rs);
        }
    }

    @Override
    public List<ProductEntity> findAll() throws Exception {
        List<ProductEntity> productEntityList = new ArrayList<>();
        try {
            connection = getConnection();
            String sql = String.format("SELECT * FROM %s", tableName);
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                productEntityList.add(new ProductEntity(
                        rs.getString("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("unit_meas")
                ));
            }
        } finally {
            closeConnection(stmt, rs);
        }
        return productEntityList;
    }

    private Connection getConnection() throws Exception {
        return ConnectionFactory.getConnection();
    }

    private void closeConnection(PreparedStatement stmt, ResultSet rs) throws Exception {
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }
        if (stmt != null && !stmt.isClosed()) {
            stmt.close();
        }
        ConnectionFactory.closeConnection();
    }
}
