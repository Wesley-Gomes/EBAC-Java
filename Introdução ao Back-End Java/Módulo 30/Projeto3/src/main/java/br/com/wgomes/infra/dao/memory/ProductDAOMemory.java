package br.com.wgomes.infra.dao.memory;

import br.com.wgomes.domain.dao.IProductDAO;
import br.com.wgomes.domain.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAOMemory implements IProductDAO {
    private final List<ProductEntity> productEntityList = new ArrayList<>();

    @Override
    public void save(ProductEntity entity) {
        productEntityList.add(entity);
    }

    @Override
    public void update(ProductEntity entity) {
        productEntityList.stream()
                .filter(product -> product.getProductId().equals(entity.getProductId()))
                .forEach(product -> {
                    product.setName(entity.getName());
                    product.setDescription(entity.getDescription());
                    product.setPrice(entity.getPrice());
                    product.setUnitMeas(entity.getUnitMeas());
                });
    }

    @Override
    public void delete(String id) {
        productEntityList.removeIf(product -> product.getProductId().equals(id));
    }

    @Override
    public boolean existsById(String id) {
        return productEntityList.stream()
                .anyMatch(product -> product.getProductId().equals(id));
    }

    @Override
    public Optional<ProductEntity> findById(String id) {
        return productEntityList.stream()
                .filter(product -> product.getProductId().equals(id))
                .findFirst();
    }

    @Override
    public List<ProductEntity> findAll() {
        return productEntityList;
    }
}
