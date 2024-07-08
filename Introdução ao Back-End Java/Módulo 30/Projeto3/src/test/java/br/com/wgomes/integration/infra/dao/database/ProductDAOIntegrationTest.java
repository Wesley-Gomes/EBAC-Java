package br.com.wgomes.integration.infra.dao.database;

import br.com.wgomes.domain.entity.ProductEntity;
import br.com.wgomes.infra.dao.database.ProductDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOIntegrationTest {
    private final ProductDAO productDAO = new ProductDAO();
    private ProductEntity productEntityBase;
    private ProductEntity productEntity;
    private String productId;

    @BeforeEach
    void setUp() throws Exception {
        productEntityBase = getProductEntityBase();
        productId = productEntityBase.getProductId();
        productEntity = getProductEntityBase();
        productEntity.setProductId("TST0002");
        productDAO.save(productEntityBase);
        productDAO.save(productEntity);
    }

    @AfterEach
    void tearDown() throws Exception {
        productDAO.delete(productId);
        productDAO.delete(productEntity.getProductId());
    }

    @Test
    void save() throws Exception {
        // Arrange
        ProductEntity newProductEntity = new ProductEntity("TEST001", "Test", "Test part", 10.0, "kg");

        // Act
        productDAO.save(newProductEntity);

        // Assert
        assertTrue(productDAO.existsById(newProductEntity.getProductId()));
        assertDoesNotThrow(() -> productDAO.delete(newProductEntity.getProductId()));
    }

    @Test
    void update() throws Exception {
        // Arrange
        productEntityBase.setName("New Name");
        productEntityBase.setDescription("New Description");
        productEntityBase.setPrice(100.0);

        // Act
        productDAO.update(productEntityBase);
        Optional<ProductEntity> result = productDAO.findById(productEntityBase.getProductId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals("New Name", result.get().getName());
        assertEquals("New Description", result.get().getDescription());
        assertEquals(100.0, result.get().getPrice());
    }

    @Test
    void delete() throws Exception {
        // Act
        productDAO.delete(productId);

        // Assert
        assertFalse(productDAO.existsById(productId));
    }

    @Test
    void existsById() throws Exception {
        // Act
        boolean result = productDAO.existsById(productId);

        // Assert
        assertTrue(result);
    }

    @Test
    void existsByIdNotFound() throws Exception {
        // Act
        boolean result = productDAO.existsById("NOTFOUND");

        // Assert
        assertFalse(result);
    }

    @Test
    void findById() throws Exception {
        // Act
        Optional<ProductEntity> result = productDAO.findById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(productEntityBase, result.get());
    }

    @Test
    void findByIdNotFound() throws Exception {
        // Act
        Optional<ProductEntity> result = productDAO.findById("NOTFOUND");

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll() throws Exception {
        // Act
        List<ProductEntity> result = productDAO.findAll();

        // Assert
        assertTrue(result.contains(productEntityBase));
        assertTrue(result.contains(productEntity));
    }

    private ProductEntity getProductEntityBase() {
        return new ProductEntity("TST0001", "Sheet Metal", "Sheet Metal 3m x 2m", 50.0, "m");
    }
}