package domain.repository;

import domain.entity.Customer;

import java.util.Collection;

public interface ICustomerRepository {
    void save(Customer customer) throws Exception;
    void delete(Long cpf) throws Exception;
    void update(Customer customer) throws Exception;
    Boolean checkExists(Long cpf);
    Collection<Customer> getAll();
    Customer getByCpf(Long cpf);
}
