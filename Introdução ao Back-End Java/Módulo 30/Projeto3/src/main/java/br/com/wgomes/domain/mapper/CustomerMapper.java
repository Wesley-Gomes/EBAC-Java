package br.com.wgomes.domain.mapper;

import br.com.wgomes.domain.entity.CustomerEntity;
import br.com.wgomes.domain.model.Customer;
import br.com.wgomes.domain.valueobject.Cpf;
import br.com.wgomes.domain.valueobject.Email;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
public class CustomerMapper implements ICustomerMapper {
    @Override
    public CustomerEntity mapToEntity(Customer input) {
        return new CustomerEntity(input.getCustomerId(), input.getName(), input.getCpf().value(), input.getEmail().value());
    }

    @Override
    public Customer mapToModel(CustomerEntity input) {
        Cpf cpf = new Cpf(input.getCpf());
        Email email = new Email(input.getEmail());
        return new Customer(input.getCustomerId(), input.getName(), cpf, email);
    }

    @Override
    public List<Customer> mapToModelList(List<CustomerEntity> input) {
        List<Customer> customerList = new ArrayList<>();
        input.forEach(customerEntity -> {
            Cpf cpf = new Cpf(customerEntity.getCpf());
            Email email = new Email(customerEntity.getEmail());
            customerList.add(new Customer(customerEntity.getCustomerId(), customerEntity.getName(), cpf, email));
        });
        return customerList;
    }
}
