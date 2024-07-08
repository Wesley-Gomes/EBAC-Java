package br.com.wgomes.services;

import br.com.wgomes.domain.dao.IProductDAO;
import br.com.wgomes.domain.entity.ProductEntity;
import br.com.wgomes.domain.mapper.IProductMapper;
import br.com.wgomes.domain.model.Product;
import br.com.wgomes.exceptions.AlreadyExistException;
import br.com.wgomes.exceptions.NotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ProductService implements IProductService {
    private final IProductDAO productDAO;
    private final IProductMapper productMapper;

    @Override
    public Product save(Product entity) throws Exception {
        if (productDAO.existsById(entity.getProductId())) throw new AlreadyExistException("Product already exists");
        ProductEntity productEntity = productMapper.productToProductEntity(entity);
        productDAO.save(productEntity);
        return productMapper.productEntityToProduct(productEntity);
    }

    @Override
    public Product update(Product entity) throws Exception {
        if (!productDAO.existsById(entity.getProductId())) throw new NotFoundException("Product not found");
        ProductEntity productEntity = productMapper.productToProductEntity(entity);
        productDAO.update(productEntity);
        return productMapper.productEntityToProduct(productEntity);
    }

    @Override
    public void delete(String id) throws Exception {
        if (!productDAO.existsById(id)) throw new NotFoundException("Product not found");
        productDAO.delete(id);
    }

    @Override
    public Optional<Product> findById(String id) throws Exception {
        Optional<ProductEntity> productEntity = productDAO.findById(id);
        return productEntity.map(productMapper::productEntityToProduct);
    }

    @Override
    public List<Product> findAll() throws Exception {
        List<ProductEntity> productEntities = productDAO.findAll();
        return productEntities.stream()
                .map(productMapper::productEntityToProduct)
                .toList();
    }
}
