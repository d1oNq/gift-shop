package com.giftshop.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserGetterSetter() {
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setPhone("+1234567890");
        user.setAddress("123 Main St");
        user.setCity("City");
        user.setZip("12345");

        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("+1234567890", user.getPhone());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("City", user.getCity());
        assertEquals("12345", user.getZip());
    }

    @Test
    public void testUserToString() {
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setPhone("+1234567890");
        user.setAddress("123 Main St");
        user.setCity("City");
        user.setZip("12345");

        String expectedString = "User{id=1, name='John Doe', email='john@example.com', password='password', phone='+1234567890', address='123 Main St', city='City', zip='12345'}";
        assertEquals(expectedString, user.toString());
    }
}
