package com.giftshop.admin.servlet;

import com.giftshop.admin.servlet.ProductAddServlet;
import com.giftshop.dao.ProductDAOImpl;
import com.giftshop.entity.Product;
import com.giftshop.log.GiftLogger;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductAddServletTest {

    @InjectMocks
    private ProductAddServlet servlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private ProductDAOImpl productDAO;
    @Mock
    private Part part;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoPostException() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("Test Product");
        when(request.getParameter("category")).thenReturn("Test Category");
        when(request.getParameter("weight")).thenReturn("10");
        when(request.getParameter("price")).thenReturn("50");
        when(request.getParameter("status")).thenReturn("Active");
        when(request.getPart("image")).thenReturn(part);
        when(part.getSubmittedFileName()).thenReturn("test_image.jpg");
        when(productDAO.addProduct(any())).thenThrow(new RuntimeException("Database connection error"));
        when(request.getSession()).thenReturn(session);

        Assertions.assertThrows(ServletException.class, () -> {
            servlet.doPost(request, response);
        });
    }
}
