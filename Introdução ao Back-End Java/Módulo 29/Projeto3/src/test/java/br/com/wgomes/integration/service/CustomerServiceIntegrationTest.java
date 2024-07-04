package br.com.wgomes.integration.service;

import br.com.wgomes.domain.dao.ICustomerDAO;
import br.com.wgomes.domain.exception.AlreadyExistException;
import br.com.wgomes.domain.exception.CannotChangedException;
import br.com.wgomes.domain.exception.NotFoundException;
import br.com.wgomes.domain.mapper.CustomerMapper;
import br.com.wgomes.domain.mapper.ICustomerMapper;
import br.com.wgomes.domain.model.Customer;
import br.com.wgomes.domain.valueobject.Cpf;
import br.com.wgomes.infra.dao.database.CustomerDAO;
import br.com.wgomes.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class CustomerServiceIntegrationTest {
    private final ICustomerDAO customerDAO = new CustomerDAO();
    private final ICustomerMapper customerMapper = new CustomerMapper();
    private final CustomerService customerService = new CustomerService(customerDAO, customerMapper);
    private Customer customer;
    private Customer customer2;

    @BeforeEach
    void setUp() throws Exception {
        customer = createCustomer("12345678909");
        customer2 = createCustomer("74855177083");
        customer = customerService.save(customer);
        customer2 = customerService.save(customer2);
    }

    @AfterEach
    void tearDown() throws Exception {
        customerDAO.delete(customer.getCustomerId());
        customerDAO.delete(customer2.getCustomerId());
    }

    @Test
    void findByCpf() throws Exception {
        // Act
        Customer result = customerService.findByCpf(customer.getCpf().value());

        // Assert
        assertEquals(customer.getCustomerId(), result.getCustomerId());
    }

    @Test
    void findByCpfNotFound() {
        // Act
        assertThrows(NotFoundException.class, () -> customerService.findByCpf("111.111.111-11"));
    }

    @Test
    void save() throws Exception {
        // Arrange
        Cpf cpf = new Cpf("69748794075");
        Customer customer = new Customer("Customer", cpf);

        // Act
        Customer result = customerService.save(customer);
        Optional<Customer> customerOptional = customerService.findById(result.getCustomerId());

        // Assert
        assertTrue(customerOptional.isPresent());
        assertEquals(result.getCustomerId(), customerOptional.get().getCustomerId());
        assertEquals(result.getCpf().value(), customerOptional.get().getCpf().value());
        assertDoesNotThrow(() -> customerService.delete(result.getCustomerId()));
    }

    @Test
    void saveAlreadyExist() {
        // Act & Assert
        assertThrows(AlreadyExistException.class, () -> customerService.save(customer));
    }

    @Test
    void update() throws Exception {
        // Arrange
        Customer customerForUpd = customer;
        customerForUpd.setName("Customer update");

        // Act
        customerService.update(customerForUpd);
        Optional<Customer> customerOptional = customerService.findById(customerForUpd.getCustomerId());

        // Assert
        assertTrue(customerOptional.isPresent());
        assertEquals(customer.getName(), customerOptional.get().getName());
    }

    @Test
    void updateNotFound() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        Cpf cpf = new Cpf("12345678909");
        Customer customer = new Customer(customerId, "Customer", cpf);
        // Act
        assertThrows(NotFoundException.class, () -> customerService.update(customer));
    }

    @Test
    void updateCannotChanged() {
        // Arrange
        Customer customerForUpdCpf = customer;
        customerForUpdCpf.setCpf(new Cpf("74855177083"));

        // Act
        assertThrows(CannotChangedException.class, () -> customerService.update(customerForUpdCpf));
    }


    @Test
    void delete() throws Exception {
        // Arrange
        Customer customerToDelete = createCustomer("18946277092");
        customerToDelete = customerService.save(customerToDelete);

        // Act
        customerService.delete(customerToDelete.getCustomerId());

        // Assert
        assertFalse(customerService.findById(customerToDelete.getCustomerId()).isPresent());
    }

    @Test
    void deleteNotFound() {
        // Act & Assert
        assertThrows(NotFoundException.class, () -> customerService.delete(UUID.randomUUID()));
    }

    @Test
    void findById() throws Exception {
        // Act
        Optional<Customer> result = customerService.findById(customer.getCustomerId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(customer.getCustomerId(), result.get().getCustomerId());
    }

    @Test
    void findAll() throws Exception {
        // Act
        List<Customer> customers = customerService.findAll();

        // Assert
        assertTrue(customers.contains(customer));
        assertTrue(customers.contains(customer2));
    }

    private Customer createCustomer(String cpf) {
        return new Customer("Customer", new Cpf(cpf));
    }
}