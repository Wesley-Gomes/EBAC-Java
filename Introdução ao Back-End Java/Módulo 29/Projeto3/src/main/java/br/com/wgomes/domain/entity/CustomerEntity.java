package br.com.wgomes.domain.entity;

import br.com.wgomes.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUSTOMER")
public class CustomerEntity {
    private UUID customerId;
    private String name;
    private String cpf;
}
