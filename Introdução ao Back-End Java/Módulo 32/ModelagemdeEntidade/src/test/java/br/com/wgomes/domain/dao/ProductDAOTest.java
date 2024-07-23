package br.com.wgomes.domain.dao;


import br.com.wgomes.domain.entity.ProductEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOTest {
    ProductDAO productDAO = new ProductDAO();
    Long productId;
    Long productIdTemp;

    @BeforeEach
    public void setUp() {
        ProductEntity productEntity = new ProductEntity("Product 1", "Description 1", 10.0);
        productDAO.save(productEntity);
        productId = productEntity.getProductId();
    }

    @AfterEach
    public void tearDown() {
        productDAO.delete(productId);
    }

    @Test
    void findById() {
        // Act
        Optional<ProductEntity> result = productDAO.findById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(productId, result.get().getProductId());
    }

    @Test
    void findAll() {
        // Arrange
        ProductEntity productEntity2 = new ProductEntity("Product 2", "Description 2", 20.0);
        ProductEntity productEntity3 = new ProductEntity("Product 3", "Description 3", 20.0);
        productDAO.save(productEntity2);
        productDAO.save(productEntity3);

        // Act
        int result = productDAO.findAll().size();
        productDAO.delete(productEntity2.getProductId());
        productDAO.delete(productEntity3.getProductId());

        // Assert
        assertEquals(3, result);
    }

    @Test
    void save() {
        // Arrange
        ProductEntity productEntity = new ProductEntity("Product 1", "Description 1", 10.0);

        // Act
        productDAO.save(productEntity);
        productIdTemp = productEntity.getProductId();
        productDAO.delete(productIdTemp);

        // Assert
        assertNotNull(productEntity.getProductId());
    }

    @Test
    void update() {
        // Arrange
        Optional<ProductEntity> productEntity = productDAO.findById(productId);
        if (productEntity.isEmpty()) fail("Product not found");
        productEntity.get().setName("Product 1 Updated");

        // Act
        productDAO.update(productEntity.orElse(null));
        Optional<ProductEntity> result = productDAO.findById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Product 1 Updated", result.get().getName());
    }

    @Test
    void delete() {
        // Arrange
        ProductEntity productEntity = new ProductEntity("Product 2", "Description 2", 20.0);
        productDAO.save(productEntity);
        productIdTemp = productEntity.getProductId();

        // Act
        productDAO.delete(productIdTemp);
        Optional<ProductEntity> result = productDAO.findById(productIdTemp);

        // Assert
        assertTrue(result.isEmpty());
    }
}
