package br.com.wgomes.dao;

import br.com.wgomes.model.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.UUID;

@DisplayName("Tests- Customer DAO")
@ExtendWith(MockitoExtension.class)
class CustomerDaoTest {
    @Mock
    IDatabaseConnection databaseConnection;

    @InjectMocks
    CustomerDao customerDao;

    UUID customerId = UUID.randomUUID();

    @Test
    @DisplayName("Save Customer")
    void save() {
        // Arrange
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setName("Customer 1");
        doNothing().when(databaseConnection).insert(customer);

        // Act
        customerDao.save(customer);

        // Assert
        verify(databaseConnection).insert(customer);
    }

    @Test
    @DisplayName("Update Customer")
    void update() {
        // Arrange
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setName("Customer 2");

        // Act
        customerDao.update(customer);

        // Assert
        verify(databaseConnection).update(customer);
    }

    @Test
    @DisplayName("Find Customer by ID")
    void findById() {
        // Arrange
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setName("Customer 1");

        when(databaseConnection.findById(customerId)).thenReturn(customer);

        // Act
        Customer result = customerDao.findById(customerId);

        // Assert
        assertEquals(result, customer);
        assertEquals(result.getCustomerId(), customerId);
        verify(databaseConnection).findById(customerId);
    }

    @Test
    @DisplayName("Delete Customer")
    void delete() {
        // Act
        customerDao.delete(customerId);

        // Assert
        verify(databaseConnection).delete(customerId);
    }
}