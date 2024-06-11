package com.giftshop.admin.servlet;

import com.giftshop.dao.OrdersDAOImpl;
import com.giftshop.entity.Orders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderUpdateServletTest {

    @InjectMocks
    private OrderUpdateServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private OrdersDAOImpl ordersDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testDoPost_Success() throws ServletException, IOException {
        when(request.getParameter("orderId")).thenReturn("1");
        when(request.getParameter("userName")).thenReturn("John Doe");
        when(request.getParameter("email")).thenReturn("john@example.com");
        when(request.getParameter("phone")).thenReturn("1234567890");
        when(request.getParameter("address")).thenReturn("123 Street");
        when(request.getParameter("productName")).thenReturn("Test Product");
        when(request.getParameter("price")).thenReturn("100");
        when(request.getParameter("weight")).thenReturn("1kg");
        when(request.getParameter("payment")).thenReturn("Credit Card");

        Orders order = servlet.buildOrderFromRequest(request);
        when(ordersDAO.updateOrder(order)).thenReturn(true);

        servlet.doPost(request, response);

        verify(session).setAttribute("successMsg", "Замовлення успішно оновлено");
        verify(response).sendRedirect("admin/orders.jsp");
        verify(ordersDAO).updateOrder(order);
    }

    @Test
    public void testDoPost_Failure() throws ServletException, IOException {
        when(request.getParameter("orderId")).thenReturn("1");
        when(request.getParameter("userName")).thenReturn("John Doe");
        when(request.getParameter("email")).thenReturn("john@example.com");
        when(request.getParameter("phone")).thenReturn("1234567890");
        when(request.getParameter("address")).thenReturn("123 Street");
        when(request.getParameter("productName")).thenReturn("Test Product");
        when(request.getParameter("price")).thenReturn("100");
        when(request.getParameter("weight")).thenReturn("1kg");
        when(request.getParameter("payment")).thenReturn("Credit Card");

        Orders order = servlet.buildOrderFromRequest(request);
        when(ordersDAO.updateOrder(order)).thenReturn(false);

        servlet.doPost(request, response);

        verify(session).setAttribute("failedMsg", "Щось пішло не так. Спробуйте ще раз");
        verify(response).sendRedirect("admin/orders.jsp");
        verify(ordersDAO).updateOrder(order);
    }

    @Test
    public void testDoPost_Exception() throws IOException {
        when(request.getParameter("orderId")).thenReturn("invalid_id");

        assertThrows(ServletException.class, () -> {
            servlet.doPost(request, response);
        });

        verify(session, never()).setAttribute(anyString(), anyString());
        verify(response, never()).sendRedirect(anyString());
        verify(ordersDAO, never()).updateOrder(any(Orders.class));
    }

    @Test
    public void testBuildOrderFromRequest() {
        when(request.getParameter("orderId")).thenReturn("1");
        when(request.getParameter("userName")).thenReturn("John Doe");
        when(request.getParameter("email")).thenReturn("john@example.com");
        when(request.getParameter("phone")).thenReturn("1234567890");
        when(request.getParameter("address")).thenReturn("123 Street");
        when(request.getParameter("productName")).thenReturn("Test Product");
        when(request.getParameter("price")).thenReturn("100");
        when(request.getParameter("weight")).thenReturn("1kg");
        when(request.getParameter("payment")).thenReturn("Credit Card");

        Orders order = servlet.buildOrderFromRequest(request);

        assertEquals(1, order.getOrderId());
        assertEquals("John Doe", order.getUserName());
        assertEquals("john@example.com", order.getEmail());
        assertEquals("1234567890", order.getPhone());
        assertEquals("123 Street", order.getAddress());
        assertEquals("Test Product", order.getProductName());
        assertEquals("100", order.getPrice());
        assertEquals("1kg", order.getWeight());
        assertEquals("Credit Card", order.getPayment());
    }
}
