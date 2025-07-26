package com.mercadolibre.challenge.infrastructure.adapter.output;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.challenge.domain.exception.ConcurrencyException;
import com.mercadolibre.challenge.domain.model.PaymentMethod;
import com.mercadolibre.challenge.domain.model.Product;
import com.mercadolibre.challenge.domain.model.Review;
import com.mercadolibre.challenge.domain.model.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileProductAdapterTest {

    @Mock
    private ObjectMapper objectMapper;

    @TempDir
    Path tempDir;

    private FileProductAdapter fileProductAdapter;
    private String dataFilePath;
    private List<Product> testProducts;

    @BeforeEach
    void setUp() {
        dataFilePath = tempDir.resolve("test-products.json").toString();
        
        // Create more comprehensive test data that includes all fields in the Product model
        PaymentMethod paymentMethod1 = PaymentMethod.builder()
                .id("pm-001")
                .name("Credit Card")
                .description("Visa, Mastercard, American Express")
                .build();
                
        PaymentMethod paymentMethod2 = PaymentMethod.builder()
                .id("pm-002")
                .name("PayPal")
                .description("Online payment")
                .build();
                
        Seller seller1 = Seller.builder()
                .id("seller-001")
                .name("Test Seller 1")
                .email("seller1@test.com")
                .phone("+1234567890")
                .rating(4.8)
                .build();
                
        Seller seller2 = Seller.builder()
                .id("seller-002")
                .name("Test Seller 2")
                .email("seller2@test.com")
                .phone("+0987654321")
                .rating(4.5)
                .build();
                
        Review review1 = Review.builder()
                .id("rev-001")
                .userId("user-001")
                .userName("Test User 1")
                .comment("Great product!")
                .rating(5)
                .createdAt(LocalDateTime.now())
                .build();
                
        Review review2 = Review.builder()
                .id("rev-002")
                .userId("user-002")
                .userName("Test User 2")
                .comment("Good value for money")
                .rating(4)
                .createdAt(LocalDateTime.now())
                .build();
        
        testProducts = Arrays.asList(
                Product.builder()
                        .id("prod-001")
                        .title("Test Product 1")
                        .description("Test Description 1")
                        .price(new BigDecimal("99.99"))
                        .images(Arrays.asList("image1.jpg", "image2.jpg"))
                        .paymentMethods(Arrays.asList(paymentMethod1))
                        .seller(seller1)
                        .stock(10)
                        .rating(4.7)
                        .reviews(Arrays.asList(review1))
                        .build(),
                Product.builder()
                        .id("prod-002")
                        .title("Test Product 2")
                        .description("Test Description 2")
                        .price(new BigDecimal("199.99"))
                        .images(Arrays.asList("image3.jpg", "image4.jpg"))
                        .paymentMethods(Arrays.asList(paymentMethod1, paymentMethod2))
                        .seller(seller2)
                        .stock(5)
                        .rating(4.2)
                        .reviews(Arrays.asList(review1, review2))
                        .build()
        );
    }

    @Test
    void init_shouldLoadDataFromFile() throws IOException {
        // Arrange
        File file = new File(dataFilePath);
        assertTrue(file.createNewFile());
        
        // Make sure the file has some content to trigger the file.length() > 0 condition
        Files.writeString(file.toPath(), "[]");
        
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(testProducts);

        // Act
        fileProductAdapter = new FileProductAdapter(objectMapper, dataFilePath);
        fileProductAdapter.init();

        // Assert
        verify(objectMapper).readValue(any(File.class), any(TypeReference.class));
    }

    @Test
    void init_whenFileIsEmpty_shouldNotThrowException() throws IOException {
        // Arrange
        File file = new File(dataFilePath);
        assertTrue(file.createNewFile());

        // Act & Assert
        fileProductAdapter = new FileProductAdapter(objectMapper, dataFilePath);
        assertDoesNotThrow(() -> fileProductAdapter.init());
    }

    @Test
    void init_whenIOExceptionOccurs_shouldThrowConcurrencyException() throws IOException {
        // Arrange
        File file = new File(dataFilePath);
        assertTrue(file.createNewFile());
        
        // Make sure the file has some content to trigger the file.length() > 0 condition
        Files.writeString(file.toPath(), "[]");
        
        // Use any(File.class) instead of eq(file) to make the mock more flexible
        when(objectMapper.readValue(any(File.class), any(TypeReference.class)))
            .thenThrow(new IOException("Test exception"));

        // Act & Assert
        fileProductAdapter = new FileProductAdapter(objectMapper, dataFilePath);
        assertThrows(ConcurrencyException.class, () -> fileProductAdapter.init());
    }

    @Test
    void findById_whenProductExists_shouldReturnProduct() throws IOException, ExecutionException, InterruptedException {
        // Arrange
        File file = new File(dataFilePath);
        assertTrue(file.createNewFile());
        
        // Make sure the file has some content to trigger the file.length() > 0 condition
        Files.writeString(file.toPath(), "[]");
        
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(testProducts);
        
        fileProductAdapter = new FileProductAdapter(objectMapper, dataFilePath);
        fileProductAdapter.init();
        
        // Verify that the products were loaded into the cache
        verify(objectMapper).readValue(any(File.class), any(TypeReference.class));

        // Act
        Optional<Product> result = fileProductAdapter.findById("prod-001").get();

        // Assert
        assertTrue(result.isPresent());
        Product product = result.get();
        assertEquals("prod-001", product.getId());
        assertEquals("Test Product 1", product.getTitle());
        assertEquals("Test Description 1", product.getDescription());
        assertEquals(new BigDecimal("99.99"), product.getPrice());
        assertEquals(2, product.getImages().size());
        assertEquals("image1.jpg", product.getImages().get(0));
        assertEquals(1, product.getPaymentMethods().size());
        assertEquals("pm-001", product.getPaymentMethods().get(0).getId());
        assertEquals("Credit Card", product.getPaymentMethods().get(0).getName());
        assertNotNull(product.getSeller());
        assertEquals("seller-001", product.getSeller().getId());
        assertEquals("Test Seller 1", product.getSeller().getName());
        assertEquals(10, product.getStock());
        assertEquals(4.7, product.getRating());
        assertEquals(1, product.getReviews().size());
        assertEquals("rev-001", product.getReviews().get(0).getId());
    }

    @Test
    void findById_whenProductDoesNotExist_shouldReturnEmptyOptional() throws IOException, ExecutionException, InterruptedException {
        // Arrange
        File file = new File(dataFilePath);
        assertTrue(file.createNewFile());
        
        // Make sure the file has some content to trigger the file.length() > 0 condition
        Files.writeString(file.toPath(), "[]");
        
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(testProducts);
        
        fileProductAdapter = new FileProductAdapter(objectMapper, dataFilePath);
        fileProductAdapter.init();
        
        // Verify that the products were loaded into the cache
        verify(objectMapper).readValue(any(File.class), any(TypeReference.class));

        // Act
        Optional<Product> result = fileProductAdapter.findById("non-existent-id").get();

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void findAll_shouldReturnAllProducts() throws IOException, ExecutionException, InterruptedException {
        // Arrange
        File file = new File(dataFilePath);
        assertTrue(file.createNewFile());
        
        // Make sure the file has some content to trigger the file.length() > 0 condition
        Files.writeString(file.toPath(), "[]");
        
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(testProducts);
        
        fileProductAdapter = new FileProductAdapter(objectMapper, dataFilePath);
        fileProductAdapter.init();
        
        // Verify that the products were loaded into the cache
        verify(objectMapper).readValue(any(File.class), any(TypeReference.class));

        // Act
        List<Product> result = fileProductAdapter.findAll().get();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getId().equals("prod-001")));
        assertTrue(result.stream().anyMatch(p -> p.getId().equals("prod-002")));
        
        // Verify product details
        Product product2 = result.stream()
                .filter(p -> p.getId().equals("prod-002"))
                .findFirst()
                .orElseThrow();
        
        assertEquals("Test Product 2", product2.getTitle());
        assertEquals("Test Description 2", product2.getDescription());
        assertEquals(new BigDecimal("199.99"), product2.getPrice());
        assertEquals(2, product2.getImages().size());
        assertEquals(2, product2.getPaymentMethods().size());
        assertEquals("seller-002", product2.getSeller().getId());
        assertEquals(5, product2.getStock());
        assertEquals(4.2, product2.getRating());
        assertEquals(2, product2.getReviews().size());
    }
    
    @Test
    void init_shouldCreateDirectoryIfNotExists()  {
        // Arrange
        // Create a nested path that doesn't exist
        Path nestedDir = tempDir.resolve("nested/directory");
        String nestedFilePath = nestedDir.resolve("test-products.json").toString();
        
        // We don't need to mock the readValue call since the file will be empty
        // and the loadDataFromFile method won't call readValue
        
        // Act
        fileProductAdapter = new FileProductAdapter(objectMapper, nestedFilePath);
        fileProductAdapter.init();
        
        // Assert
        assertTrue(Files.exists(nestedDir));
        // No need to verify readValue since it won't be called for an empty file
    }
}