package br.com.wgomes.dao;

import br.com.wgomes.model.Customer;

import java.util.UUID;

public interface ICustomerDao {
    void save(Customer customer);
    void update(Customer customer);
    Customer findById(UUID customerId);
    void delete(UUID customerId);
}
