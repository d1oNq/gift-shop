package com.giftshop.dao;

import com.giftshop.entity.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CartDAOImplTest {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private CartDAOImpl cartDAO;

    @BeforeEach
    public void setUp() throws Exception {
        conn = mock(Connection.class);
        ps = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);
        cartDAO = new CartDAOImpl(conn);
    }

    @Test
    public void testAddToCart() throws Exception {
        Cart cart = new Cart();
        cart.setProductId(1);
        cart.setUserId(1);
        cart.setProductName("Product1");
        cart.setCategory("Category1");
        cart.setWeight(1.0);
        cart.setPrice(10.0);

        when(conn.prepareStatement(any(String.class))).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        boolean result = cartDAO.addToCart(cart);

        assertTrue(result);
        verify(ps, times(1)).setObject(1, 1);
        verify(ps, times(1)).setObject(2, 1);
        verify(ps, times(1)).setObject(3, "Product1");
        verify(ps, times(1)).setObject(4, "Category1");
        verify(ps, times(1)).setObject(5, 1.0);
        verify(ps, times(1)).setObject(6, 10.0);
        verify(ps, times(1)).executeUpdate();
    }

    @Test
    public void testGetProductByUser() throws Exception {
        when(conn.prepareStatement(any(String.class))).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt(1)).thenReturn(1);
        when(rs.getInt(2)).thenReturn(1);
        when(rs.getInt(3)).thenReturn(1);
        when(rs.getString(4)).thenReturn("Product1");
        when(rs.getString(5)).thenReturn("Category1");
        when(rs.getDouble(6)).thenReturn(1.0);
        when(rs.getDouble(7)).thenReturn(10.0);

        List<Cart> carts = cartDAO.getProductByUser(1);

        assertEquals(1, carts.size());
        Cart cart = carts.get(0);
        assertEquals(1, cart.getCartId());
        assertEquals(1, cart.getProductId());
        assertEquals(1, cart.getUserId());
        assertEquals("Product1", cart.getProductName());
        assertEquals("Category1", cart.getCategory());
        assertEquals(1.0, cart.getWeight());
        assertEquals(10.0, cart.getPrice());

        verify(ps, times(1)).setObject(1, 1);
        verify(ps, times(1)).executeQuery();
    }

    @Test
    public void testRemoveFromCart() throws Exception {
        when(conn.prepareStatement(any(String.class))).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        boolean result = cartDAO.removeFromCart(1, 1, 1);

        assertTrue(result);
        verify(ps, times(1)).setObject(1, 1);
        verify(ps, times(1)).setObject(2, 1);
        verify(ps, times(1)).setObject(3, 1);
        verify(ps, times(1)).executeUpdate();
    }
}
