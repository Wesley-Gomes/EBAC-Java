package br.com.wgomes.integration.infra.dao.database;

import br.com.wgomes.domain.entity.CustomerEntity;
import br.com.wgomes.exceptions.DAOException;
import br.com.wgomes.infra.dao.database.CustomerDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDAOIntegrationTest {
    private final CustomerDAO customerDAO = new CustomerDAO();
    private UUID customerId;
    private UUID customerId2;

    @BeforeEach
    void setUp() throws Exception {
        CustomerEntity customerEntity = getCustomerEntity();
        customerId = customerEntity.getCustomerId();
        CustomerEntity customerEntity2 = getCustomerEntity();
        customerId2 = customerEntity2.getCustomerId();
        customerEntity2.setCpf("74855177083");
        customerDAO.save(customerEntity);
        customerDAO.save(customerEntity2);
    }

    @AfterEach
    void tearDown() throws Exception {
        customerDAO.delete(customerId);
        customerDAO.delete(customerId2);
    }

    @Test
    void findByCpf() throws Exception {
        // Act
        Optional<CustomerEntity> result = customerDAO.findByCpf("12345678909");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(customerId.toString(), result.get().getCustomerId().toString());
        assertEquals("Customer", result.get().getName());
        assertEquals("12345678909", result.get().getCpf());
    }

    @Test
    void save() throws Exception {
        // Arrange
        CustomerEntity customerEntity = new CustomerEntity(UUID.randomUUID(), "New Customer", "69446316014");

        // Act
        customerDAO.save(customerEntity);

        // Assert
        assertTrue(customerDAO.existsById(customerEntity.getCustomerId()));
    }

    @Test
    void saveWithSameCpf() {
        // Arrange
        CustomerEntity customerEntity = new CustomerEntity(UUID.randomUUID(), "New Customer", "12345678909");

        // Act & Assert
        assertThrows(DAOException.class, () -> customerDAO.save(customerEntity));
    }

    @Test
    void update() throws Exception {
        // Arrange
        Optional<CustomerEntity> optionalCustomerEntity = customerDAO.findById(customerId);
        if (optionalCustomerEntity.isEmpty()) fail("Customer not found");
        CustomerEntity customerEntity = optionalCustomerEntity.get();
        customerEntity.setName("Updated Customer");

        // Act
        customerDAO.update(customerEntity);
        Optional<CustomerEntity> customerEntityUpdated = customerDAO.findById(customerId);

        // Assert
        assertTrue(customerEntityUpdated.isPresent());
        assertEquals("Updated Customer", customerEntityUpdated.get().getName());
    }

    @Test
    void delete() throws Exception {
        // Arrange
        UUID newCustomerId = UUID.randomUUID();
        customerDAO.save(new CustomerEntity(newCustomerId, "Customer for Delete", "69748794075"));

        // Act
        customerDAO.delete(newCustomerId);

        // Assert
        assertFalse(customerDAO.existsById(newCustomerId));
    }

    @Test
    void existsById() throws Exception {
        // Act & Assert
        assertTrue(customerDAO.existsById(customerId));
    }

    @Test
    void notExistsById() throws Exception {
        // Act & Assert
        assertFalse(customerDAO.existsById(UUID.randomUUID()));
    }

    @Test
    void findById() throws Exception {
        // Act
        Optional<CustomerEntity> result = customerDAO.findById(customerId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(customerId, result.get().getCustomerId());
        assertEquals("Customer", result.get().getName());
        assertEquals("12345678909", result.get().getCpf());
    }

    @Test
    void notFindById() throws Exception {
        // Act
        Optional<CustomerEntity> result = customerDAO.findById(UUID.randomUUID());

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll() throws Exception {
        // Act
        List<CustomerEntity> result = customerDAO.findAll();

        // Assert
        assertTrue(result.stream().anyMatch(c -> c.getCustomerId().equals(customerId)));
        assertTrue(result.stream().anyMatch(c -> c.getCustomerId().equals(customerId2)));
    }

    private CustomerEntity getCustomerEntity() {
        return new CustomerEntity(UUID.randomUUID(), "Customer", "12345678909");
    }
}
