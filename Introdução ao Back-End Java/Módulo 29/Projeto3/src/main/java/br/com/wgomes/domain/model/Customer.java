package br.com.wgomes.domain.model;

import br.com.wgomes.domain.valueobject.Cpf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private UUID customerId;
    private String name;
    private Cpf cpf;

    public Customer(String name, Cpf cpf) {
        this.name = name;
        this.cpf = cpf;
    }
}
