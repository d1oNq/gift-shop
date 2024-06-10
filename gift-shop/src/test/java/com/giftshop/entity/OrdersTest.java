package com.giftshop.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrdersTest {

    @Test
    public void testOrdersGetterSetter() {
        Orders order = new Orders();
        order.setId(1);
        order.setOrderId("ORDER1234");
        order.setUserName("John Doe");
        order.setEmail("john@example.com");
        order.setPhone("+1234567890");
        order.setAddress("123 Main St, City");
        order.setProductName("Test Product");
        order.setPrice("25.00");
        order.setWeight("1.5");
        order.setPayment("Credit Card");

        assertEquals(1, order.getId());
        assertEquals("ORDER1234", order.getOrderId());
        assertEquals("John Doe", order.getUserName());
        assertEquals("john@example.com", order.getEmail());
        assertEquals("+1234567890", order.getPhone());
        assertEquals("123 Main St, City", order.getAddress());
        assertEquals("Test Product", order.getProductName());
        assertEquals("25.00", order.getPrice());
        assertEquals("1.5", order.getWeight());
        assertEquals("Credit Card", order.getPayment());
    }

    @Test
    public void testOrdersToString() {
        Orders order = new Orders();
        order.setId(1);
        order.setOrderId("ORDER1234");
        order.setUserName("John Doe");
        order.setEmail("john@example.com");
        order.setPhone("+1234567890");
        order.setAddress("123 Main St, City");
        order.setProductName("Test Product");
        order.setPrice("25.00");
        order.setWeight("1.5");
        order.setPayment("Credit Card");

        String expectedString = "Orders{id=1, orderId='ORDER1234', userName='John Doe', email='john@example.com', phone='+1234567890', address='123 Main St, City', productName='Test Product', price='25.00', weight='1.5', payment='Credit Card'}";
        assertEquals(expectedString, order.toString());
    }
}
