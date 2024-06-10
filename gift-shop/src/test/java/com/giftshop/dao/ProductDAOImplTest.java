package com.giftshop.dao;

import com.giftshop.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductDAOImplTest {

    @Mock
    private Connection conn;

    @Mock
    private PreparedStatement ps;

    @Mock
    private ResultSet rs;

    @InjectMocks
    private ProductDAOImpl productDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct() throws Exception {
        Product product = new Product();
        product.setProductName("Chocolate");
        product.setCategory("Candy");
        product.setWeight("100g");
        product.setPrice("10.00");
        product.setStatus("active");
        product.setPhoto("photo.jpg");

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        boolean result = productDAO.addProduct(product);
        assertTrue(result);
        verify(ps, times(1)).executeUpdate();
    }

    @Test
    void testGetAllProduct() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt(1)).thenReturn(1);
        when(rs.getString(2)).thenReturn("Chocolate");
        when(rs.getString(3)).thenReturn("Candy");
        when(rs.getString(4)).thenReturn("100g");
        when(rs.getString(5)).thenReturn("10.00");
        when(rs.getString(6)).thenReturn("active");
        when(rs.getString(7)).thenReturn("photo.jpg");

        List<Product> products = productDAO.getAllProduct();
        assertEquals(1, products.size());
        assertEquals("Chocolate", products.get(0).getProductName());
        verify(ps, times(1)).executeQuery();
    }

    @Test
    void testGetProductById() throws Exception {
        int productId = 1;

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt(1)).thenReturn(productId);
        when(rs.getString(2)).thenReturn("Chocolate");
        when(rs.getString(3)).thenReturn("Candy");
        when(rs.getString(4)).thenReturn("100g");
        when(rs.getString(5)).thenReturn("10.00");
        when(rs.getString(6)).thenReturn("active");
        when(rs.getString(7)).thenReturn("photo.jpg");

        Product product = productDAO.getProductById(productId);
        assertNotNull(product);
        assertEquals(productId, product.getProductId());
        verify(ps, times(1)).executeQuery();
    }


    @Test
    void testUpdateEditProduct() throws Exception {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Chocolate");
        product.setCategory("Candy");
        product.setWeight("100g");
        product.setPrice("10.00");
        product.setStatus("active");
        product.setPhoto("photo.jpg");

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        boolean result = productDAO.updateEditProduct(product);
        assertTrue(result);
        verify(ps, times(1)).executeUpdate();
    }

    @Test
    void testDeleteProduct() throws Exception {
        int productId = 1;

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        boolean result = productDAO.deleteProduct(productId);
        assertTrue(result);
        verify(ps, times(1)).executeUpdate();
    }

    @Test
    void testGetCandy() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt(1)).thenReturn(1);
        when(rs.getString(2)).thenReturn("Chocolate");
        when(rs.getString(3)).thenReturn("Candy");
        when(rs.getString(4)).thenReturn("100g");
        when(rs.getString(5)).thenReturn("10.00");
        when(rs.getString(6)).thenReturn("active");
        when(rs.getString(7)).thenReturn("photo.jpg");

        List<Product> products = productDAO.getCandy();
        assertEquals(1, products.size());
        assertEquals("Candy", products.get(0).getCategory());
        verify(ps, times(1)).executeQuery();
    }

    @Test
    void testGetProductBySearch() throws Exception {
        String search = "Chocolate";

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt(1)).thenReturn(1);
        when(rs.getString(2)).thenReturn("Chocolate");
        when(rs.getString(3)).thenReturn("Candy");
        when(rs.getString(4)).thenReturn("100g");
        when(rs.getString(5)).thenReturn("10.00");
        when(rs.getString(6)).thenReturn("active");
        when(rs.getString(7)).thenReturn("photo.jpg");

        List<Product> products = productDAO.getProductBySearch(search);
        assertEquals(1, products.size());
        assertEquals("Chocolate", products.get(0).getProductName());
        verify(ps, times(1)).executeQuery();
    }
}
