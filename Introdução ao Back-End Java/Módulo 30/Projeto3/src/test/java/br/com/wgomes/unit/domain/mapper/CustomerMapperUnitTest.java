package br.com.wgomes.unit.domain.mapper;

import br.com.wgomes.domain.entity.CustomerEntity;
import br.com.wgomes.domain.mapper.CustomerMapper;
import br.com.wgomes.domain.model.Customer;
import br.com.wgomes.domain.valueobject.Cpf;
import br.com.wgomes.domain.valueobject.Email;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMapperUnitTest {
    private final CustomerMapper customerMapper = new CustomerMapper();

    @Test
    void mapToEntity() {
        // Arrange
        Cpf cpf = new Cpf("12345678909");
        Email email = new Email("test@test.com");
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "Customer", cpf, email);
        CustomerEntity customerEntity = new CustomerEntity(customerId, "Customer", cpf.value(), email.value());

        // Act
        CustomerEntity result = customerMapper.mapToEntity(customer);

        // Assert
        assertEquals(customerEntity.getCustomerId(), result.getCustomerId());
        assertEquals(customerEntity.getName(), result.getName());
        assertEquals(customerEntity.getCpf(), result.getCpf());
    }

    @Test
    void mapToModel() {
        // Arrange
        Cpf cpf = new Cpf("12345678909");
        Email email = new Email("test@test.com");
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "Customer", cpf, email);
        CustomerEntity customerEntity = new CustomerEntity(customerId, "Customer", cpf.value(), email.value());

        // Act
        Customer result = customerMapper.mapToModel(customerEntity);

        // Assert
        assertEquals(customer.getCustomerId(), result.getCustomerId());
        assertEquals(customer.getName(), result.getName());
        assertEquals(customer.getCpf().value(), result.getCpf().value());
    }

    @Test
    void mapToModelList() {
        // Arrange
        Cpf cpf = new Cpf("12345678909");
        Email email = new Email("test@test.com");
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "Customer", cpf, email);
        CustomerEntity customerEntity = new CustomerEntity(customerId, "Customer", cpf.value(), email.value());

        // Act
        List<Customer> result = customerMapper.mapToModelList(List.of(customerEntity));

        // Assert
        assertEquals(1, result.size());
        assertEquals(customer.getCustomerId(), result.get(0).getCustomerId());
        assertEquals(customer.getName(), result.get(0).getName());
        assertEquals(customer.getCpf().value(), result.get(0).getCpf().value());
    }
}