package br.com.wgomes.integration.service;

import br.com.wgomes.domain.dao.IProductDAO;
import br.com.wgomes.domain.mapper.IProductMapper;
import br.com.wgomes.domain.model.Product;
import br.com.wgomes.exceptions.AlreadyExistException;
import br.com.wgomes.exceptions.NotFoundException;
import br.com.wgomes.infra.dao.database.ProductDAO;
import br.com.wgomes.services.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceIntegrationTest {
    private final IProductDAO productDAO = new ProductDAO();
    private final ProductService productService = new ProductService(productDAO, IProductMapper.INSTANCE);
    private final Product product = createProduct("SERV0001");
    private final Product product2 = createProduct("SERV0002");

    @BeforeEach
    void setUp() throws Exception {
        productService.save(product);
        productService.save(product2);
    }

    @AfterEach
    void tearDown() throws Exception {
        productDAO.delete(product.getProductId());
        productDAO.delete(product2.getProductId());
    }

    @Test
    void save() throws Exception {
        // Arrange
        Product newProduct = createProduct("SERV0003");

        // Act
        productService.save(newProduct);

        // Assert
        assertNotNull(productService.findById(newProduct.getProductId()));
        assertDoesNotThrow(() -> productService.delete(newProduct.getProductId()));
    }

    @Test
    void saveAlreadyExist() {
        // Act & Assert
        assertThrows(AlreadyExistException.class, () -> productService.save(product));
    }

    @Test
    void update() throws Exception {
        // Arrange
        product.setName("Product 1 Updated");
        product.setDescription("Description 1 Updated");
        product.setPrice(20.0);
        product.setUnitMeas("g");

        // Act
        productService.update(product);
        Optional<Product> productUpdated = productService.findById(product.getProductId());

        // Assert
        assertTrue(productUpdated.isPresent());
        assertEquals(product.getName(), productUpdated.get().getName());
        assertEquals(product.getDescription(), productUpdated.get().getDescription());
        assertEquals(product.getPrice(), productUpdated.get().getPrice());
        assertEquals(product.getUnitMeas(), productUpdated.get().getUnitMeas());
    }

    @Test
    void updateNotFound() {
        // Arrange
        Product productNotSaved = createProduct("SERV0003");

        // Act & Assert
        assertThrows(NotFoundException.class, () -> productService.update(productNotSaved));
    }

    @Test
    void delete() throws Exception {
        // Arrange
        Product newProduct = createProduct("SERV0003");
        productService.save(newProduct);

        // Act
        productService.delete(newProduct.getProductId());

        // Assert
        assertFalse(productService.findById(newProduct.getProductId()).isPresent());
    }

    @Test
    void deleteNotFound() {
        // Act & Assert
        assertThrows(NotFoundException.class, () -> productService.delete("SERV0003"));
    }

    @Test
    void findById() throws Exception {
        // Act
        Optional<Product> productFound = productService.findById(product.getProductId());

        // Assert
        assertTrue(productFound.isPresent());
        assertEquals(product.getName(), productFound.get().getName());
    }

    @Test
    void findByIdNotFound() throws Exception {
        // Act
        Optional<Product> productFound = productService.findById("SERV0003");

        // Assert
        assertTrue(productFound.isEmpty());
    }

    @Test
    void findAll() throws Exception {
        // Act
        List<Product> products = productService.findAll();

        // Assert
        assertTrue(products.contains(product));
        assertTrue(products.contains(product2));
    }

    private Product createProduct(String productId) {
        Product newProduct = new Product();
        newProduct.setProductId(productId);
        newProduct.setName("Product 1");
        newProduct.setDescription("Description 1");
        newProduct.setPrice(10.0);
        newProduct.setUnitMeas("kg");
        return newProduct;
    }
}