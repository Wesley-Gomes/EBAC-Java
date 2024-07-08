package br.com.wgomes.unit.service;

import br.com.wgomes.domain.dao.IProductDAO;
import br.com.wgomes.domain.entity.ProductEntity;
import br.com.wgomes.domain.mapper.IProductMapper;
import br.com.wgomes.domain.model.Product;
import br.com.wgomes.exceptions.AlreadyExistException;
import br.com.wgomes.exceptions.NotFoundException;
import br.com.wgomes.services.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceUnitTest {
    AutoCloseable autoClose;
    @Mock
    private IProductDAO productDAO;
    @Mock
    private IProductMapper productMapper;
    @InjectMocks
    private ProductService productService;
    private Product product;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        autoClose = MockitoAnnotations.openMocks(this);
        product = createProduct("SERV0001");
        productEntity = new ProductEntity(product.getProductId(), product.getName(), product.getDescription(), product.getPrice(), product.getUnitMeas());
    }

    @AfterEach
    void tearDown() throws Exception {
        autoClose.close();
    }

    @Test
    void save() throws Exception {
        // Arrange
        when(productDAO.existsById(product.getProductId())).thenReturn(false);
        when(productMapper.productToProductEntity(product)).thenReturn(productEntity);
        when(productMapper.productEntityToProduct(productEntity)).thenReturn(product);

        // Act
        Product result = productService.save(product);

        // Assert
        assertEquals(product, result);
        verify(productDAO).existsById(product.getProductId());
        verify(productMapper).productToProductEntity(product);
        verify(productDAO).save(productEntity);
        verify(productMapper).productEntityToProduct(productEntity);
    }

    @Test
    void saveAlreadyExist() throws Exception {
        // Arrange
        when(productDAO.existsById(product.getProductId())).thenReturn(true);

        // Act & Assert
        assertThrows(AlreadyExistException.class, () -> productService.save(product));
    }

    @Test
    void update() throws Exception {
        // Arrange
        Product productForUpdate = product;
        productForUpdate.setName("Product 1 Updated");
        productForUpdate.setDescription("Description 1 Updated");
        productForUpdate.setPrice(20.0);
        productForUpdate.setUnitMeas("g");

        ProductEntity productEntityForUpdate = new ProductEntity();
        productEntityForUpdate.setName("Product 1 Updated");
        productEntityForUpdate.setDescription("Description 1 Updated");
        productEntityForUpdate.setPrice(20.0);
        productEntityForUpdate.setUnitMeas("g");

        when(productDAO.existsById(productForUpdate.getProductId())).thenReturn(true);
        when(productMapper.productToProductEntity(productForUpdate)).thenReturn(productEntityForUpdate);
        when(productMapper.productEntityToProduct(productEntityForUpdate)).thenReturn(productForUpdate);

        // Act
        Product result = productService.update(product);

        // Assert
        assertEquals(productForUpdate.getName(), result.getName());
        assertEquals(productForUpdate.getDescription(), result.getDescription());
        assertEquals(productForUpdate.getPrice(), result.getPrice());
        assertEquals(productForUpdate.getUnitMeas(), result.getUnitMeas());
        verify(productDAO).existsById(productForUpdate.getProductId());
        verify(productMapper).productToProductEntity(productForUpdate);
        verify(productDAO).update(productEntityForUpdate);
        verify(productMapper).productEntityToProduct(productEntityForUpdate);
    }

    @Test
    void updateNotFound() {
        // Arrange
        Product productNotSaved = createProduct("SERV0002");

        // Act & Assert
        assertThrows(NotFoundException.class, () -> productService.update(productNotSaved));
    }

    @Test
    void delete() throws Exception {
        // Arrange
        when(productDAO.existsById(product.getProductId())).thenReturn(true);

        // Act
        productService.delete(product.getProductId());

        // Assert
        verify(productDAO).existsById(product.getProductId());
    }

    @Test
    void deleteNotFound() {
        // Act & Assert
        assertThrows(NotFoundException.class, () -> productService.delete("SERV0002"));
    }

    @Test
    void findById() throws Exception {
        // Arrange
        when(productDAO.findById(product.getProductId())).thenReturn(Optional.of(productEntity));
        when(productMapper.productEntityToProduct(productEntity)).thenReturn(product);

        // Act
        Optional<Product> productFound = productService.findById(product.getProductId());

        // Assert
        assertTrue(productFound.isPresent());
        assertEquals(product.getName(), productFound.get().getName());
        verify(productDAO).findById(product.getProductId());
    }

    @Test
    void findByIdNotFound() throws Exception {
        // Act
        Optional<Product> productFound = productService.findById("SERV0002");

        // Assert
        assertTrue(productFound.isEmpty());
        verify(productDAO).findById("SERV0002");
    }

    @Test
    void findAll() throws Exception {
        // Arrange
        Product product2 = createProduct("SERV0002");
        ProductEntity productEntity2 = new ProductEntity(product2.getProductId(), product2.getName(), product2.getDescription(), product2.getPrice(), product2.getUnitMeas());
        when(productDAO.findAll()).thenReturn(List.of(productEntity, productEntity2));
        when(productMapper.productEntityToProduct(productEntity)).thenReturn(product);
        when(productMapper.productEntityToProduct(productEntity2)).thenReturn(product2);

        // Act
        List<Product> products = productService.findAll();

        // Assert
        assertTrue(products.contains(product));
        assertTrue(products.contains(product2));
        verify(productDAO).findAll();
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