package com.giftshop.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void testProductGetterSetter() {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Test Product");
        product.setCategory("Test Category");
        product.setWeight("1.5");
        product.setPrice("25.00");
        product.setStatus("Available");
        product.setPhoto("photo.jpg");

        assertEquals(1, product.getProductId());
        assertEquals("Test Product", product.getProductName());
        assertEquals("Test Category", product.getCategory());
        assertEquals("1.5", product.getWeight());
        assertEquals("25.00", product.getPrice());
        assertEquals("Available", product.getStatus());
        assertEquals("photo.jpg", product.getPhoto());
    }

    @Test
    public void testProductToString() {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Test Product");
        product.setCategory("Test Category");
        product.setWeight("1.5");
        product.setPrice("25.00");
        product.setStatus("Available");
        product.setPhoto("photo.jpg");

        String expectedString = "Product{productId=1, productName='Test Product', category='Test Category', weight='1.5', price='25.00', status='Available', photo='photo.jpg'}";
        assertEquals(expectedString, product.toString());
    }
}
