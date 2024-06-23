package br.com.wgomes.model;

import java.util.UUID;

public class Customer {
    private UUID customerId;
    private String name;

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
}
