package br.com.wgomes.domain.entity;

import br.com.wgomes.annotations.PrimaryKey;
import br.com.wgomes.annotations.Table;
import br.com.wgomes.annotations.TableColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT")
public class ProductEntity {
    @PrimaryKey
    @TableColumn(name = "product_id", setJavaName = "setProductId")
    private String productId;
    @TableColumn(name = "name", setJavaName = "setName")
    private String name;
    @TableColumn(name = "description", setJavaName = "setDescription")
    private String description;
    @TableColumn(name = "price", setJavaName = "setPrice")
    private Double price;
    @TableColumn(name = "unit_meas", setJavaName = "setUnitMeas")
    private String unitMeas;
}
