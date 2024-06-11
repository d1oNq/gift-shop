package com.giftshop.admin.servlet;

import com.giftshop.dao.OrdersDAOImpl;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderDeleteServletTest {

    @InjectMocks
    private OrderDeleteServlet servlet;

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
    public void testDoGet_Success() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(ordersDAO.deleteOrder(1)).thenReturn(true);

        servlet.doGet(request, response);

        verify(session).setAttribute("successMsg", "Замовлення успішно видалено");
        verify(response).sendRedirect("admin/orders.jsp");
        verify(ordersDAO).deleteOrder(1);
    }

    @Test
    public void testDoGet_Failure() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(ordersDAO.deleteOrder(1)).thenReturn(false);

        servlet.doGet(request, response);

        verify(session).setAttribute("failedMsg", "Щось пішло не так");
        verify(response).sendRedirect("admin/orders.jsp");
        verify(ordersDAO).deleteOrder(1);
    }

    @Test
    public void testDoGet_Exception() throws IOException {
        when(request.getParameter("id")).thenReturn("invalid_id");

        assertThrows(ServletException.class, () -> {
            servlet.doGet(request, response);
        });

        verify(session, never()).setAttribute(anyString(), anyString());
        verify(response, never()).sendRedirect(anyString());
        verify(ordersDAO, never()).deleteOrder(anyInt());
    }
}
