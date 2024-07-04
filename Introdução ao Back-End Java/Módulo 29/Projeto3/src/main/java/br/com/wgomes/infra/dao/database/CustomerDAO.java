package br.com.wgomes.infra.dao.database;

import br.com.wgomes.config.singleton.ConnectionFactory;
import br.com.wgomes.domain.dao.ICustomerDAO;
import br.com.wgomes.domain.entity.CustomerEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerDAO implements ICustomerDAO {
    private final String tableName = "CUSTOMER";
    private Connection connection;
    private PreparedStatement stmt;
    private ResultSet rs;

    public CustomerDAO() {
    }

    @Override
    public Optional<CustomerEntity> findByCpf(String cpf) throws Exception {
        try {
            connection = getConnection();
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
    public void save(CustomerEntity entity) throws Exception {
        try {
            connection = getConnection();
            String sql = String.format("INSERT INTO %s (customer_id, name, cpf) VALUES (?, ?, ?)", tableName);
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, entity.getCustomerId().toString());
            stmt.setString(2, entity.getName());
            stmt.setString(3, entity.getCpf());
            stmt.executeUpdate();
        } finally {
            closeConnection(stmt, rs);
        }
    }

    @Override
    public void update(CustomerEntity entity) throws Exception {
        try {
            connection = getConnection();
            String sql = String.format("UPDATE %s SET name = ?, cpf = ? WHERE customer_id = ?", tableName);
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getCpf());
            stmt.setString(3, entity.getCustomerId().toString());
            stmt.executeUpdate();
        } finally {
            closeConnection(stmt, rs);
        }
    }

    @Override
    public void delete(UUID id) throws Exception {
        try {
            connection = getConnection();
            String sql = String.format("DELETE FROM %s WHERE customer_id = ?", tableName);
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } finally {
            closeConnection(stmt, rs);
        }
    }

    @Override
    public boolean existsById(UUID id) throws Exception {
        try {
            connection = getConnection();
            String sql = String.format("SELECT * FROM %s WHERE customer_id = ?", tableName);
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, id.toString());
            rs = stmt.executeQuery();
            return rs.next();
        } finally {
            closeConnection(stmt, rs);
        }
    }

    @Override
    public Optional<CustomerEntity> findById(UUID id) throws Exception {
        connection = getConnection();
        String sql = String.format("SELECT * FROM %s WHERE customer_id = ?", tableName);
        stmt = connection.prepareStatement(sql);
        stmt.setString(1, id.toString());
        rs = stmt.executeQuery();
        if (rs.next()) {
            String name = rs.getString("name");
            String cpf = rs.getString("cpf");
            return Optional.of(new CustomerEntity(id, name, cpf));
        }
        return Optional.empty();
    }

    @Override
    public List<CustomerEntity> findAll() throws Exception {
        List<CustomerEntity> customerEntityList = new ArrayList<>();
        try {
            connection = getConnection();
            String sql = String.format("SELECT * FROM %s", tableName);
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                UUID customerId = UUID.fromString(rs.getString("customer_id"));
                String name = rs.getString("name");
                String cpf = rs.getString("cpf");
                customerEntityList.add(new CustomerEntity(customerId, name, cpf));
            }
        } finally {
            closeConnection(stmt, rs);
        }
        return customerEntityList;
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
