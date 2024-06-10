package com.giftshop.user.servlet;

import com.giftshop.dao.UserDAOImpl;
import com.giftshop.entity.User;
import com.giftshop.user.servlet.RegisterServlet;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegisterServletTest {

    private RegisterServlet servlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private UserDAOImpl userDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new RegisterServlet();
    }

    @Test
    void testDoPostRegistrationSuccess() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("Test User");
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("password")).thenReturn("testPassword");
        when(request.getParameter("check")).thenReturn("on");

        when(request.getSession()).thenReturn(session);
        when(userDAO.checkUser("test@example.com")).thenReturn(true);
        when(userDAO.userRegister(any(User.class))).thenReturn(true);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("successMsg"), anyString());
        verify(response).sendRedirect("register.jsp");
    }

    @Test
    void testDoPostDuplicateEmail() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("Test User");
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("password")).thenReturn("testPassword");
        when(request.getParameter("check")).thenReturn("on");

        when(request.getSession()).thenReturn(session);
        when(userDAO.checkUser("test@example.com")).thenReturn(false);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("failedMsg"), anyString());
        verify(response).sendRedirect("register.jsp");
    }

    @Test
    void testDoPostPrivacyPolicyNotChecked() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("Test User");
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("password")).thenReturn("testPassword");
        when(request.getParameter("check")).thenReturn(null);

        when(request.getSession()).thenReturn(session);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("failedMsg"), anyString());
        verify(response).sendRedirect("register.jsp");
    }
}
