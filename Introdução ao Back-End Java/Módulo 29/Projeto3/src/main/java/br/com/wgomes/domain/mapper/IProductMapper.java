package br.com.wgomes.domain.mapper;

import br.com.wgomes.domain.entity.ProductEntity;
import br.com.wgomes.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface IProductMapper {
    IProductMapper INSTANCE = Mappers.getMapper(IProductMapper.class);

    Product productEntityToProduct(ProductEntity productEntity);

    ProductEntity productToProductEntity(Product product);
}
