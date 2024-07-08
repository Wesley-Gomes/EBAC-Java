package br.com.wgomes.unit.domain.model;

import br.com.wgomes.domain.model.Customer;
import br.com.wgomes.domain.valueobject.Cpf;
import br.com.wgomes.domain.valueobject.Email;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerUnitTest {

    @Test
    void GetAndSetCustomerId() {
        Customer customer = new Customer();
        UUID id = UUID.randomUUID();
        customer.setCustomerId(id);
        assertEquals(id, customer.getCustomerId());
    }

    @Test
    void GetAndSetName() {
        Customer customer = new Customer();
        String name = "Customer";
        customer.setName(name);
        assertEquals(name, customer.getName());
    }

    @Test
    void GetAndSetCpf() {
        Customer customer = new Customer();
        Cpf cpf = new Cpf("12345678909");
        customer.setCpf(cpf);
        assertEquals(cpf, customer.getCpf());
    }

    @Test
    void Equals() {
        UUID id = UUID.randomUUID();
        String name = "Customer";
        Cpf cpf = new Cpf("12345678909");
        Email email = new Email("test@test.com");
        Customer customer1 = new Customer(id, name, cpf, email);
        Customer customer2 = new Customer(id, name, cpf, email);
        assertEquals(customer1, customer2);
    }

    @Test
    void HashCode() {
        UUID id = UUID.randomUUID();
        String name = "Customer";
        Cpf cpf = new Cpf("12345678909");
        Email email = new Email("test@test.com");
        Customer customer1 = new Customer(id, name, cpf, email);
        Customer customer2 = new Customer(id, name, cpf, email);
        assertEquals(customer1.hashCode(), customer2.hashCode());
    }

    @Test
    void ToString() {
        UUID id = UUID.randomUUID();
        String name = "Customer";
        Cpf cpf = new Cpf("12345678909");
        Email email = new Email("test@test.com");
        Customer customer = new Customer(id, name, cpf, email);
        String expectedString = "Customer(" +
                "customerId=" + id +
                ", name=" + name +
                ", cpf=" + cpf +
                ", email=" + email +
                ')';
        assertEquals(expectedString, customer.toString());
    }
}