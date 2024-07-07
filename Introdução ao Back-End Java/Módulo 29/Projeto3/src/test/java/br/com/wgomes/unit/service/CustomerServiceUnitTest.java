package br.com.wgomes.unit.service;

import br.com.wgomes.domain.dao.ICustomerDAO;
import br.com.wgomes.domain.entity.CustomerEntity;
import br.com.wgomes.exceptions.AlreadyExistException;
import br.com.wgomes.exceptions.CannotChangedException;
import br.com.wgomes.exceptions.NotFoundException;
import br.com.wgomes.domain.mapper.ICustomerMapper;
import br.com.wgomes.domain.model.Customer;
import br.com.wgomes.domain.valueobject.Cpf;
import br.com.wgomes.services.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class CustomerServiceUnitTest {
    AutoCloseable autoClose;
    @Mock
    private ICustomerDAO customerDAO;
    @Mock
    private ICustomerMapper customerMapper;
    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        autoClose = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoClose.close();
    }

    @Test
    void findByCpf() throws Exception {
        // Arrange
        Cpf cpf = new Cpf("12345678909");
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName("Customer");
        customerEntity.setCpf(cpf.value());
        Customer customer = new Customer("Customer", cpf);

        when(customerDAO.findByCpf(cpf.value())).thenReturn(Optional.of(customerEntity));
        when(customerMapper.mapToModel(customerEntity)).thenReturn(customer);

        // Act
        Customer result = customerService.findByCpf(cpf.value());

        // Assert
        assertEquals(cpf.value(), result.getCpf().value());
        verify(customerDAO).findByCpf(cpf.value());
        verify(customerMapper).mapToModel(customerEntity);
    }

    @Test
    void findByCpfNotFound() throws Exception {
        // Arrange
        Cpf cpf = new Cpf("12345678909");

        when(customerDAO.findByCpf(cpf.value())).thenReturn(Optional.empty());

        // Act
        assertThrows(NotFoundException.class, () -> customerService.findByCpf(cpf.value()));
        verify(customerDAO).findByCpf(cpf.value());
    }

    @Test
    void save() throws Exception {
        // Arrange
        Cpf cpf = new Cpf("12345678909");
        Customer customer = new Customer(UUID.randomUUID(), "Customer", cpf);
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customer.getName());
        customerEntity.setCpf(cpf.value());

        when(customerMapper.mapToEntity(customer)).thenReturn(customerEntity);
        when(customerMapper.mapToModel(any(CustomerEntity.class))).thenReturn(customer);

        // Act
        Customer result = customerService.save(customer);

        // Assert
        assertNotNull(result.getCustomerId());
        assertEquals(customer.getName(), result.getName());
        assertEquals(customer.getCpf().value(), result.getCpf().value());
        verify(customerDAO).save(any(CustomerEntity.class));
        verify(customerMapper).mapToEntity(customer);
        verify(customerMapper).mapToModel(any(CustomerEntity.class));
    }

    @Test
    void saveAlreadyExist() throws Exception {
        // Arrange
        Cpf cpf = new Cpf("12345678909");
        Customer customer = new Customer(UUID.randomUUID(), "Customer", cpf);
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customer.getName());
        customerEntity.setCpf(cpf.value());

        when(customerDAO.findByCpf(cpf.value())).thenReturn(Optional.of(customerEntity));

        // Act
        assertThrows(AlreadyExistException.class, () -> customerService.save(customer));
        verify(customerDAO).findByCpf(cpf.value());
    }

    @Test
    void update() throws Exception {
        // Arrange
        UUID customerId = UUID.randomUUID();
        Cpf cpf = new Cpf("12345678909");
        Customer customer = new Customer("Customer", cpf);
        customer.setCustomerId(customerId);
        CustomerEntity customerEntity = new CustomerEntity(customerId, "Customer update", cpf.value());

        when(customerDAO.findById(customerId)).thenReturn(Optional.of(customerEntity));
        when(customerMapper.mapToEntity(customer)).thenReturn(customerEntity);
        when(customerMapper.mapToModel(customerEntity)).thenReturn(customer);

        // Act
        customer.setName("Customer update");
        Customer result = customerService.update(customer);

        // Assert
        assertEquals(customerId, result.getCustomerId());
        assertEquals("Customer update", result.getName());
        assertEquals(cpf.value(), result.getCpf().value());
        verify(customerDAO).update(customerEntity);
        verify(customerMapper).mapToEntity(customer);
    }

    @Test
    void updateNotFound() throws Exception {
        // Arrange
        UUID customerId = UUID.randomUUID();
        Cpf cpf = new Cpf("12345678909");
        Customer customer = new Customer("Customer", cpf);
        customer.setCustomerId(customerId);

        when(customerDAO.findById(customerId)).thenReturn(Optional.empty());

        // Act
        assertThrows(NotFoundException.class, () -> customerService.update(customer));
        verify(customerDAO).findById(customerId);
    }

    @Test
    void updateCannotChanged() throws Exception {
        // Arrange
        UUID customerId = UUID.randomUUID();
        Cpf cpf = new Cpf("12345678909");
        Customer customer = new Customer("Customer", cpf);
        customer.setCustomerId(customerId);
        CustomerEntity customerEntity = new CustomerEntity(customerId, customer.getName(), cpf.value());

        when(customerDAO.findById(customerId)).thenReturn(Optional.of(customerEntity));

        // Act
        customer.setCpf(new Cpf("74855177083"));
        assertThrows(CannotChangedException.class, () -> customerService.update(customer));
        verify(customerDAO).findById(customerId);
    }

    @Test
    void delete() throws Exception {
        // Arrange
        UUID customerId = UUID.randomUUID();

        when(customerDAO.existsById(customerId)).thenReturn(true);

        // Act
        customerService.delete(customerId);

        // Assert
        verify(customerDAO).existsById(customerId);
        verify(customerDAO).delete(customerId);
    }

    @Test
    void deleteNotFound() throws Exception {
        // Arrange
        UUID customerId = UUID.randomUUID();

        when(customerDAO.existsById(customerId)).thenReturn(false);

        // Act
        assertThrows(NotFoundException.class, () -> customerService.delete(customerId));
        verify(customerDAO).existsById(customerId);
    }

    @Test
    void findById() throws Exception {
        // Arrange
        UUID customerId = UUID.randomUUID();
        Cpf cpf = new Cpf("12345678909");
        CustomerEntity customerEntity = new CustomerEntity(customerId, "Customer", cpf.value());

        when(customerDAO.findById(customerId)).thenReturn(Optional.of(customerEntity));
        when(customerMapper.mapToModel(customerEntity)).thenReturn(new Customer(customerId, "Customer", cpf));

        // Act
        Optional<Customer> customer = customerService.findById(customerId);

        // Assert
        assertTrue(customer.isPresent());
        assertEquals(customerId, customer.get().getCustomerId());
        verify(customerDAO).findById(customerId);
        verify(customerMapper).mapToModel(customerEntity);
    }

    @Test
    void findByIdNotFound() throws Exception {
        // Arrange
        UUID customerId = UUID.randomUUID();

        when(customerDAO.findById(customerId)).thenReturn(Optional.empty());

        // Act
        Optional<Customer> customer = customerService.findById(customerId);

        // Assert
        assertTrue(customer.isEmpty());
        verify(customerDAO).findById(customerId);
    }

    @Test
    void findAll() throws Exception {
        // Arrange
        Cpf cpf = new Cpf("12345678909");
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCpf(cpf.value());
        List<CustomerEntity> customerEntities = List.of(customerEntity);

        when(customerDAO.findAll()).thenReturn(customerEntities);
        when(customerMapper.mapToModelList(customerEntities)).thenReturn(List.of(new Customer("Customer", cpf)));

        // Act
        List<Customer> customers = customerService.findAll();

        // Assert
        assertEquals(1, customers.size());
        verify(customerDAO).findAll();
        verify(customerMapper).mapToModelList(customerEntities);
    }
}