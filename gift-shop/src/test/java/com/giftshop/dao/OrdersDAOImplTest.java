package com.giftshop.dao;

import com.giftshop.entity.Orders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class OrdersDAOImplTest {

    @Mock
    private Connection conn;

    @Mock
    private PreparedStatement ps;

    @Mock
    private ResultSet rs;

    @InjectMocks
    private OrdersDAOImpl ordersDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveOrder() throws Exception {
        Orders order = new Orders();
        order.setOrderId(123); // Оновлення відповідно до нового типу orderId
        order.setUserName("John Doe");
        order.setEmail("john@example.com");
        order.setPhone("1234567890");
        order.setAddress("123 Street");
        order.setProductName("Chocolate");
        order.setPrice("10.00");
        order.setWeight("100g");
        order.setPayment("Credit Card");

        List<Orders> orderList = new ArrayList<>();
        orderList.add(order);

        when(conn.prepareStatement(anyString())).thenReturn(ps);

        boolean result = ordersDAO.saveOrder(orderList);
        assertTrue(result);
        verify(ps, times(orderList.size())).addBatch();
        verify(ps, times(1)).executeBatch();
        verify(conn, times(1)).commit();
    }

    @Test
    void testGetOrdersByEmail() throws Exception {
        String email = "john@example.com";

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt(1)).thenReturn(123); // Оновлення відповідно до нового типу orderId
        when(rs.getString(2)).thenReturn("John Doe");
        when(rs.getString(3)).thenReturn(email);
        when(rs.getString(4)).thenReturn("1234567890");
        when(rs.getString(5)).thenReturn("123 Street");
        when(rs.getString(6)).thenReturn("Chocolate");
        when(rs.getString(7)).thenReturn("10.00");
        when(rs.getString(8)).thenReturn("100g");
        when(rs.getString(9)).thenReturn("Credit Card");

        List<Orders> orders = ordersDAO.getOrders(email);
        assertEquals(1, orders.size());
        assertEquals(email, orders.get(0).getEmail());
        verify(ps, times(1)).executeQuery();
    }

    @Test
    void testGetAllOrders() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt(1)).thenReturn(123); // Оновлення відповідно до нового типу orderId
        when(rs.getString(2)).thenReturn("John Doe");
        when(rs.getString(3)).thenReturn("john@example.com");
        when(rs.getString(4)).thenReturn("1234567890");
        when(rs.getString(5)).thenReturn("123 Street");
        when(rs.getString(6)).thenReturn("Chocolate");
        when(rs.getString(7)).thenReturn("10.00");
        when(rs.getString(8)).thenReturn("100g");
        when(rs.getString(9)).thenReturn("Credit Card");

        List<Orders> orders = ordersDAO.getOrders();
        assertEquals(1, orders.size());
        verify(ps, times(1)).executeQuery();
    }
}
