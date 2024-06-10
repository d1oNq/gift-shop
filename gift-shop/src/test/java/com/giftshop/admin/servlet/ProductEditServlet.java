package com.giftshop.admin.servlet;

import com.giftshop.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductEditServletTest {

    @Mock
    HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuildProductFromRequest() {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Test Product");
        when(request.getParameter("category")).thenReturn("TestCategory");
        when(request.getParameter("weight")).thenReturn("10");
        when(request.getParameter("price")).thenReturn("100");
        when(request.getParameter("status")).thenReturn("Active");

        ProductEditServlet servlet = new ProductEditServlet();
        Product product = servlet.buildProductFromRequest(request);

        assertEquals(1, product.getProductId());
        assertEquals("Test Product", product.getProductName());
        assertEquals("TestCategory", product.getCategory());
        assertEquals("10", product.getWeight());
        assertEquals("100", product.getPrice());
        assertEquals("Active", product.getStatus());
    }

    @Test
    void testIsImageUpdated() throws IOException, ServletException {
        Part part = mock(Part.class);
        when(part.getSubmittedFileName()).thenReturn("test.jpg");

        when(request.getPart("image")).thenReturn(part);

        ProductEditServlet servlet = new ProductEditServlet();
        assertTrue(servlet.isImageUpdated(request));
    }

    @Test
    void testIsImageNotUpdated() throws IOException, ServletException {
        Part part = mock(Part.class);
        when(part.getSubmittedFileName()).thenReturn(null);

        when(request.getPart("image")).thenReturn(part);

        ProductEditServlet servlet = new ProductEditServlet();
        assertFalse(servlet.isImageUpdated(request));
    }
}
