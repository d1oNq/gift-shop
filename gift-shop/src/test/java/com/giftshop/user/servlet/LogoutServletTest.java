package com.giftshop.user.servlet;

import com.giftshop.user.servlet.LogoutServlet;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

class LogoutServletTest {

    private LogoutServlet servlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new LogoutServlet();
    }

    @Test
    void testDoGetLogoutSuccess() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn("testUser");

        servlet.doGet(request, response);

        verify(session).removeAttribute("user");
        verify(response).sendRedirect("login.jsp");
    }

    @Test
    void testDoGetNoUserInSession() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        servlet.doGet(request, response);

        verify(session, never()).removeAttribute("user");
        verify(response).sendRedirect("login.jsp");
    }

    @Test
    void testDoGetException() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn("testUser");
        doThrow(new RuntimeException("Test Exception")).when(session).removeAttribute("user");

        servlet.doGet(request, response);

        verify(response, never()).sendRedirect(anyString());
    }
}
