package com.giftshop.user.servlet;

import com.giftshop.dao.CartDAOImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RemoveFromCartServletTest {

    private RemoveFromCartServlet servlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private CartDAOImpl cartDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new RemoveFromCartServlet();
    }

    @Test
    void testDoGetProductRemovedSuccessfully() throws ServletException, IOException {
        when(request.getParameter("productId")).thenReturn("1");
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("cartId")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(cartDAO.removeFromCart(anyInt(), anyInt(), anyInt())).thenReturn(true);

        servlet.doGet(request, response);

        verify(session).setAttribute(eq("successMsg"), anyString());
        verify(response).sendRedirect("cart.jsp");
    }

    @Test
    void testDoGetProductRemovalFailed() throws ServletException, IOException {
        when(request.getParameter("productId")).thenReturn("1");
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("cartId")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(cartDAO.removeFromCart(anyInt(), anyInt(), anyInt())).thenReturn(false);

        servlet.doGet(request, response);

        verify(session).setAttribute(eq("failedMsg"), anyString());
        verify(response).sendRedirect("cart.jsp");
    }

    @Test
    void testDoGetInvalidParameterFormat() throws ServletException, IOException {
        when(request.getParameter("productId")).thenReturn("abc"); // invalid format
        when(request.getSession()).thenReturn(session);

        servlet.doGet(request, response);

        verify(session).setAttribute(eq("failedMsg"), anyString());
        verify(response).sendRedirect("cart.jsp");
    }

    @Test
    void testDoGetExceptionThrown() throws ServletException, IOException {
        when(request.getParameter("productId")).thenReturn("1");
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("cartId")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(cartDAO.removeFromCart(anyInt(), anyInt(), anyInt())).thenThrow(new RuntimeException("Database connection error"));

        servlet.doGet(request, response);

        verify(session).setAttribute(eq("failedMsg"), anyString());
        verify(response).sendRedirect("cart.jsp");
    }
}
