package br.com.wgomes.domain.model;

import br.com.wgomes.domain.valueobject.Cpf;
import br.com.wgomes.domain.valueobject.Email;
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
    private Email email;

    public Customer(String name, Cpf cpf, Email email) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
    }
}
