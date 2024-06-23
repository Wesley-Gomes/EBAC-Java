package br.com.wgomes.domain.model;

import br.com.wgomes.domain.valueobject.Cpf;

import java.util.Objects;
import java.util.UUID;

public class Customer {
    private UUID customerId;
    private String name;
    private Cpf cpf;

    public Customer(UUID customerId, String name, Cpf cpf) {
        this.customerId = customerId;
        this.name = name;
        this.cpf = cpf;
    }

    public Customer(String name, Cpf cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    public Customer() {
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public void setCpf(Cpf cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", cpf=" + cpf +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId) && Objects.equals(name, customer.name) && Objects.equals(cpf, customer.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, cpf);
    }
}
