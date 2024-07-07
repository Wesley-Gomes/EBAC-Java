package br.com.wgomes.domain.entity;

import br.com.wgomes.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT")
public class ProductEntity {
    private String productId;
    private String name;
    private String description;
    private Double price;
    private String unitMeas;
}
