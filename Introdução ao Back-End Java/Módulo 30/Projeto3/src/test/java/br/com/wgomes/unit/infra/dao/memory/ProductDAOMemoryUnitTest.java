package br.com.wgomes.unit.infra.dao.memory;

import br.com.wgomes.domain.entity.ProductEntity;
import br.com.wgomes.infra.dao.memory.ProductDAOMemory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOMemoryUnitTest {
    private final ProductDAOMemory productDAOMemory = new ProductDAOMemory();
    private final String productId = "TST0001";
    private ProductEntity productEntityBase;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        productEntityBase = getProductEntityBase();
        productEntity = getProductEntityBase();
        productEntity.setProductId("TST0002");
        productDAOMemory.save(productEntityBase);
        productDAOMemory.save(productEntity);
    }

    @AfterEach
    void tearDown() {
        productDAOMemory.delete(productId);
        productDAOMemory.delete(productEntity.getProductId());
    }

    @Test
    void save() {
        // Arrange
        ProductEntity productEntity = new ProductEntity("TST0002", "Test", "Test part", 10.0, "kg");

        // Act
        productDAOMemory.save(productEntity);

        // Assert
        assertTrue(productDAOMemory.existsById(productEntity.getProductId()));
    }

    @Test
    void update() {
        // Arrange
        productEntityBase.setName("New Name");
        productEntityBase.setDescription("New Description");
        productEntityBase.setPrice(100.0);

        // Act
        productDAOMemory.update(productEntityBase);
        Optional<ProductEntity> result = productDAOMemory.findById(productEntityBase.getProductId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals("New Name", result.get().getName());
        assertEquals("New Description", result.get().getDescription());
        assertEquals(100.0, result.get().getPrice());
    }

    @Test
    void delete() {
        // Act
        productDAOMemory.delete(productId);

        // Assert
        assertFalse(productDAOMemory.existsById(productId));
    }

    @Test
    void existsById() {
        // Act
        boolean result = productDAOMemory.existsById(productId);

        // Assert
        assertTrue(result);
    }

    @Test
    void findById() {
        // Act
        Optional<ProductEntity> result = productDAOMemory.findById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(productId, result.get().getProductId());
    }

    @Test
    void findAll() {
        // Act
        List<ProductEntity> result = productDAOMemory.findAll();

        // Assert
        assertTrue(result.contains(productEntityBase));
        assertTrue(result.contains(productEntity));
    }

    private ProductEntity getProductEntityBase() {
        return new ProductEntity(productId, "Sheet Metal", "Sheet Metal 3m x 2m", 50.0, "m");
    }
}