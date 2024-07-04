package br.com.wgomes.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String productId;
    private String name;
    private String description;
    private Double price;
    private String unitMeas;
}
