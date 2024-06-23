package br.com.wgomes.dao;

import br.com.wgomes.model.Customer;

import java.util.UUID;

public class CustomerDao implements ICustomerDao{
    private final IDatabaseConnection databaseConnection;

    public CustomerDao(IDatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void save(Customer customer) {
        databaseConnection.insert(customer);
    }

    @Override
    public void update(Customer customer) {
        databaseConnection.update(customer);
    }

    @Override
    public Customer findById(UUID customerId) {
        return (Customer) databaseConnection.findById(customerId);
    }

    @Override
    public void delete(UUID customerId) {
        databaseConnection.delete(customerId);
    }
}
