package br.com.wgomes.unit.infra.dao.memory;

import br.com.wgomes.domain.entity.CustomerEntity;
import br.com.wgomes.infra.dao.memory.CustomerDAOMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDAOMemoryUnitTest {
    final CustomerDAOMemory customerDAOMemory = new CustomerDAOMemory();
    UUID customerId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        CustomerEntity customerEntity = new CustomerEntity(customerId, "Customer", "12345678909");
        customerDAOMemory.save(customerEntity);
    }

    @Test
    void findByCpf() {
        // Act
        Optional<CustomerEntity> result = customerDAOMemory.findByCpf("12345678909");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(customerId, result.get().getCustomerId());
        assertEquals("Customer", result.get().getName());
        assertEquals("12345678909", result.get().getCpf());
    }

    @Test
    void save() {
        // Arrange
        CustomerEntity customerEntity = new CustomerEntity(UUID.randomUUID(), "New Customer", "12345678909");

        // Act
        customerDAOMemory.save(customerEntity);

        // Assert
        assertTrue(customerDAOMemory.existsById(customerEntity.getCustomerId()));
    }

    @Test
    void update() {
        // Arrange
        Optional<CustomerEntity> optionalCustomerEntity = customerDAOMemory.findById(customerId);
        if (optionalCustomerEntity.isEmpty()) fail("Customer not found");
        CustomerEntity customerEntity = optionalCustomerEntity.get();
        customerEntity.setName("Updated Customer");

        // Act
        customerDAOMemory.update(customerEntity);
        Optional<CustomerEntity> customerEntityUpdated = customerDAOMemory.findById(customerId);

        // Assert
        assertTrue(customerEntityUpdated.isPresent());
        assertEquals("Updated Customer", customerEntityUpdated.get().getName());
    }

    @Test
    void delete() {
        // Arrange
        UUID newCustomerId = UUID.randomUUID();
        customerDAOMemory.save(new CustomerEntity(newCustomerId, "Customer for Delete", "12345678909"));

        // Act
        customerDAOMemory.delete(newCustomerId);

        // Assert
        assertFalse(customerDAOMemory.existsById(newCustomerId));
    }

    @Test
    void existsById() {
        // Act & Assert
        assertTrue(customerDAOMemory.existsById(customerId));
    }

    @Test
    void notExistsById() {
        // Act & Assert
        assertFalse(customerDAOMemory.existsById(UUID.randomUUID()));
    }

    @Test
    void findById() {
        // Act
        Optional<CustomerEntity> result = customerDAOMemory.findById(customerId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(customerId, result.get().getCustomerId());
        assertEquals("Customer", result.get().getName());
        assertEquals("12345678909", result.get().getCpf());
    }

    @Test
    void notFindById() {
        // Act
        Optional<CustomerEntity> result = customerDAOMemory.findById(UUID.randomUUID());

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll() {
        // Act
        List<CustomerEntity> result = customerDAOMemory.findAll();

        // Assert
        assertTrue(result.stream().anyMatch(c -> c.getCustomerId().equals(customerId)));
    }
}