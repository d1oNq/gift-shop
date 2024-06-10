package com.giftshop.user.servlet;

import com.giftshop.dao.CartDAOImpl;
import com.giftshop.dao.OrdersDAOImpl;
import com.giftshop.entity.Cart;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OrdersServletTest {

    private OrdersServlet servlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private CartDAOImpl cartDAO;
    @Mock
    private OrdersDAOImpl ordersDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new OrdersServlet();
    }

    @Test
    void testDoPostOrderSuccess() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Test User");
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("phone")).thenReturn("1234567890");
        when(request.getParameter("address")).thenReturn("Test Address");
        when(request.getParameter("city")).thenReturn("Test City");
        when(request.getParameter("zip")).thenReturn("12345");
        when(request.getParameter("payment")).thenReturn("Credit Card");

        List<Cart> cartItems = new ArrayList<>();
        cartItems.add(new Cart());
        when(cartDAO.getProductByUser(1)).thenReturn(cartItems);

        when(ordersDAO.saveOrder(anyList())).thenReturn(true);

        when(request.getSession()).thenReturn(session);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("successMsg"), anyString());
        verify(response).sendRedirect("order_success.jsp");
    }

    @Test
    void testDoPostEmptyCart() throws Exception {
        when(request.getParameter("id")).thenReturn("1");

        when(cartDAO.getProductByUser(1)).thenReturn(new ArrayList<>());

        when(request.getSession()).thenReturn(session);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("failedMsg"), anyString());
        verify(response).sendRedirect("cart.jsp");
    }

    @Test
    void testDoPostNoPaymentMethodSelected() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("payment")).thenReturn("no-select");

        when(cartDAO.getProductByUser(1)).thenReturn(new ArrayList<>());

        when(request.getSession()).thenReturn(session);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("failedMsg"), anyString());
        verify(response).sendRedirect("cart.jsp");
    }

    @Test
    void testDoPostOrderFailed() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Test User");
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("phone")).thenReturn("1234567890");
        when(request.getParameter("address")).thenReturn("Test Address");
        when(request.getParameter("city")).thenReturn("Test City");
        when(request.getParameter("zip")).thenReturn("12345");
        when(request.getParameter("payment")).thenReturn("Credit Card");

        List<Cart> cartItems = new ArrayList<>();
        cartItems.add(new Cart());
        when(cartDAO.getProductByUser(1)).thenReturn(cartItems);

        when(ordersDAO.saveOrder(anyList())).thenReturn(false);

        when(request.getSession()).thenReturn(session);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("failedMsg"), anyString());
        verify(response).sendRedirect("cart.jsp");
    }
}
