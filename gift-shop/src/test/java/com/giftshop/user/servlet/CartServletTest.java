package com.giftshop.user.servlet;

import com.giftshop.dao.CartDAOImpl;
import com.giftshop.db.DBConnect;
import com.giftshop.entity.Cart;
import com.giftshop.entity.Product;
import com.giftshop.user.servlet.CartServlet;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartServletTest {

    private CartServlet servlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private CartDAOImpl cartDAO;
    @Mock
    private Connection connection;

    private MockedStatic<DBConnect> dbConnectMockedStatic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dbConnectMockedStatic = mockStatic(DBConnect.class);

        dbConnectMockedStatic.when(DBConnect::getConn).thenReturn(connection);
        servlet = new CartServlet();
    }

    @AfterEach
    void tearDown() {
        dbConnectMockedStatic.close();
    }

    @Test
    void testDoGetAddToCartSuccess() throws Exception {
        when(request.getParameter("productId")).thenReturn("1");
        when(request.getParameter("userId")).thenReturn("1");

        Product product = new Product();
        product.setProductName("Test Product");
        product.setCategory("Test Category");
        product.setWeight("1.0");
        product.setPrice("10.0");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("cart")).thenReturn(null);
        when(cartDAO.addToCart(any(Cart.class))).thenReturn(true);

        servlet.doGet(request, response);

        verify(session).setAttribute("cart", "Успішно добавлено в кошик");
    }

    @Test
    void testDoGetAddToCartFailed() throws Exception {
        when(request.getParameter("productId")).thenReturn("1");
        when(request.getParameter("userId")).thenReturn("1");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("cart")).thenReturn(null);
        when(cartDAO.addToCart(any(Cart.class))).thenReturn(false);

        servlet.doGet(request, response);

        verify(session).setAttribute("cart", "Щось пішло не так. Спробуйте ще раз");
    }

    @Test
    void testDoGetErrorHandling() throws Exception {
        when(request.getParameter("productId")).thenReturn("1");
        when(request.getParameter("userId")).thenReturn("1");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("cart")).thenReturn(null);
        when(cartDAO.addToCart(any(Cart.class))).thenThrow(new RuntimeException("Test Exception"));

        servlet.doGet(request, response);

        verify(response, never()).sendRedirect(anyString());
        verify(session, never()).setAttribute(eq("cart"), anyString());
    }
}
