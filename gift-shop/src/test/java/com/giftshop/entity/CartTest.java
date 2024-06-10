package com.giftshop.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    @Test
    public void testCartGetterSetter() {
        Cart cart = new Cart();
        cart.setCartId(1);
        cart.setProductId(1001);
        cart.setUserId(10);
        cart.setProductName("Test Product");
        cart.setCategory("Test Category");
        cart.setWeight(1.5);
        cart.setPrice(25.0);

        assertEquals(1, cart.getCartId());
        assertEquals(1001, cart.getProductId());
        assertEquals(10, cart.getUserId());
        assertEquals("Test Product", cart.getProductName());
        assertEquals("Test Category", cart.getCategory());
        assertEquals(1.5, cart.getWeight());
        assertEquals(25.0, cart.getPrice());
    }

    @Test
    public void testCartToString() {
        Cart cart = new Cart();
        cart.setCartId(1);
        cart.setProductId(1001);
        cart.setUserId(10);
        cart.setProductName("Test Product");
        cart.setCategory("Test Category");
        cart.setWeight(1.5);
        cart.setPrice(25.0);

        String expectedString = "Cart{cartId=1, productId=1001, userId=10, productName='Test Product', category='Test Category', weight=1.5, price=25.0}";
        assertEquals(expectedString, cart.toString());
    }
}
