package br.com.wgomes.unit.domain.entity;

import br.com.wgomes.domain.entity.CustomerEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerEntityUnitTest {

    @Test
    void GetAndSetCustomerId() {
        CustomerEntity customerEntity = new CustomerEntity();
        UUID id = UUID.randomUUID();
        customerEntity.setCustomerId(id);
        assertEquals(id, customerEntity.getCustomerId());
    }

    @Test
    void GetAndSetName() {
        CustomerEntity customerEntity = new CustomerEntity();
        String name = "Customer";
        customerEntity.setName(name);
        assertEquals(name, customerEntity.getName());
    }

    @Test
    void GetAndSetCpf() {
        CustomerEntity customerEntity = new CustomerEntity();
        String cpf = "12345678910";
        customerEntity.setCpf(cpf);
        assertEquals(cpf, customerEntity.getCpf());
    }

    @Test
    void Equals() {
        UUID id = UUID.randomUUID();
        String name = "Customer";
        String cpf = "12345678910";
        CustomerEntity customerEntity1 = new CustomerEntity(id, name, cpf);
        CustomerEntity customerEntity2 = new CustomerEntity(id, name, cpf);
        assertEquals(customerEntity1, customerEntity2);
    }

    @Test
    void HashCode() {
        UUID id = UUID.randomUUID();
        String name = "Customer";
        String cpf = "12345678910";
        CustomerEntity customerEntity1 = new CustomerEntity(id, name, cpf);
        CustomerEntity customerEntity2 = new CustomerEntity(id, name, cpf);
        assertEquals(customerEntity1.hashCode(), customerEntity2.hashCode());
    }

    @Test
    void ToString() {
        UUID id = UUID.randomUUID();
        String name = "Customer";
        String cpf = "12345678910";
        CustomerEntity customerEntity = new CustomerEntity(id, name, cpf);
        String expectedString = "CustomerEntity(" +
                "customerId=" + id +
                ", name=" + name +
                ", cpf=" + cpf +
                ')';
        assertEquals(expectedString, customerEntity.toString());
    }
}