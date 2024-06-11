package com.giftshop.admin.servlet;

import com.giftshop.dao.ProductDAOImpl;

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

public class ProductDeleteServletTest {

    @InjectMocks
    private ProductDeleteServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private ProductDAOImpl productDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoGet_Success() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(productDAO.deleteProduct(1)).thenReturn(true);

        servlet.doGet(request, response);

        verify(session).setAttribute("successMsg", "Товар успішно видалено");
        verify(response).sendRedirect("admin/products.jsp");
        verify(productDAO).deleteProduct(1);
    }

    @Test
    public void testDoGet_Failure() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(productDAO.deleteProduct(1)).thenReturn(false);

        servlet.doGet(request, response);

        verify(session).setAttribute("failedMsg", "Щось пішло не так");
        verify(response).sendRedirect("admin/products.jsp");
        verify(productDAO).deleteProduct(1);
    }

    @Test
    public void testDoGet_Exception() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("invalid_id");
        when(request.getSession()).thenReturn(session);

        assertThrows(ServletException.class, () -> {
            servlet.doGet(request, response);
        });

        verify(session, never()).setAttribute(anyString(), anyString());
        verify(response, never()).sendRedirect(anyString());
        verify(productDAO, never()).deleteProduct(anyInt());
    }
}