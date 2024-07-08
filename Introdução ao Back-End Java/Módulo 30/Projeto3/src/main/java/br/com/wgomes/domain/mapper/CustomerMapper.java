package br.com.wgomes.domain.mapper;

import br.com.wgomes.domain.entity.CustomerEntity;
import br.com.wgomes.domain.model.Customer;
import br.com.wgomes.domain.valueobject.Cpf;

import java.util.ArrayList;
import java.util.List;

public class CustomerMapper implements ICustomerMapper {
    public CustomerMapper() {
    }

    @Override
    public CustomerEntity mapToEntity(Customer input) {
        return new CustomerEntity(input.getCustomerId(), input.getName(), input.getCpf().value());
    }

    @Override
    public Customer mapToModel(CustomerEntity input) {
        Cpf cpf = new Cpf(input.getCpf());
        return new Customer(input.getCustomerId(), input.getName(), cpf);
    }

    @Override
    public List<Customer> mapToModelList(List<CustomerEntity> input) {
        List<Customer> customerList = new ArrayList<>();
        input.forEach(customerEntity -> {
            Cpf cpf = new Cpf(customerEntity.getCpf());
            customerList.add(new Customer(customerEntity.getCustomerId(), customerEntity.getName(), cpf));
        });
        return customerList;
    }
}
