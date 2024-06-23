package br.com.wgomes.domain.entity;

import java.util.Objects;
import java.util.UUID;

public class CustomerEntity {
    private UUID customerId;
    private String name;
    private String cpf;

    public CustomerEntity(UUID customerId, String name, String cpf) {
        this.customerId = customerId;
        this.name = name;
        this.cpf = cpf;
    }

    public CustomerEntity() {
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", cpf=" + cpf +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return Objects.equals(customerId, that.customerId) && Objects.equals(name, that.name) && Objects.equals(cpf, that.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, cpf);
    }
}
