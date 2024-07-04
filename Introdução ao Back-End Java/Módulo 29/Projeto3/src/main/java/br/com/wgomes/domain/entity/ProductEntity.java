package br.com.wgomes.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    private String productId;
    private String name;
    private String description;
    private Double price;
    private String unitMeas;
}
