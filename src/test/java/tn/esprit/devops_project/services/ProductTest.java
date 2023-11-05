package tn.esprit.devops_project.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.ProductServiceImpl;



import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
@RunWith(MockitoJUnitRunner.class)

class ProductTest {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testaddproduct() {
        Product product = new Product();
        product.setTitle("Sample Product");

        // Create a Stock entity or configure its behavior based on your test requirements
        Stock stock = new Stock();
        stock.setIdStock(1L);
        // Configure the behavior of stockRepository.findById
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        // Configure the behavior of productRepository.save
        when(productRepository.save(product)).thenReturn(product);

        // Call the method to test
        Product result = productService.addProduct(product, 1L);

        // Assertions
        assertEquals(product, result);

        // Optionally, you can verify that certain methods were called
        verify(stockRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }
    @Test
    public void retrievebycategorie() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(new Product (), new Product()));

        List<Product > secteurActivite  = productService.retrieveProductByCategory(ProductCategory.ELECTRONICS);

        assertEquals(0, secteurActivite .size());
    }
    @Test
    void testdeleteproduct() {
        Long stockId = 1L;
        doNothing().when(productRepository).deleteById(stockId);
        productService.deleteProduct(stockId);
        verify(productRepository).deleteById(stockId);
    }

    @Test
    public void testretrrievebyid() {
        Long id = 1L;
        Product product = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product  retproduct  = productService.retrieveProduct(id);

        assertNotNull(retproduct );
    }
    @Test
    public void testretrieeveall () {
        when(productRepository.findAll()).thenReturn(Arrays.asList(new Product (), new Product()));

        List<Product > secteurActivite  = productService.retreiveAllProduct();

        assertEquals(2, secteurActivite .size());
    }
}