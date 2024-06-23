package br.com.wgomes.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    Customer customer = new Customer();
    UUID customerId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        customer.setCustomerId(customerId);
        customer.setName("Customer 1");
    }

    @Test
    void getCustomerId() {
        // Assert
        assertEquals(customerId, customer.getCustomerId());
    }

    @Test
    void setCustomerId() {
        // Arrange
        UUID newCustomerId = UUID.randomUUID();

        // Act
        customer.setCustomerId(newCustomerId);

        // Assert
        assertEquals(newCustomerId, customer.getCustomerId());
    }

    @Test
    void getName() {
        // Assert
        assertEquals("Customer 1", customer.getName());
    }

    @Test
    void setName() {
        // Arrange
        String newName = "Customer 2";

        // Act
        customer.setName(newName);

        // Assert
        assertEquals(newName, customer.getName());
    }
}