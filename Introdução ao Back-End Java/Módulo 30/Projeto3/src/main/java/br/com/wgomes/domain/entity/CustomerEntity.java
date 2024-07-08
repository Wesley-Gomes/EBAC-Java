package br.com.wgomes.domain.entity;

import br.com.wgomes.annotations.PrimaryKey;
import br.com.wgomes.annotations.Table;
import br.com.wgomes.annotations.TableColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUSTOMER")
public class CustomerEntity {
    @PrimaryKey
    @TableColumn(name = "customer_id", setJavaName = "setCustomerId")
    private UUID customerId;
    @TableColumn(name = "name", setJavaName = "setName")
    private String name;
    @TableColumn(name = "cpf", setJavaName = "setCpf")
    private String cpf;
}
