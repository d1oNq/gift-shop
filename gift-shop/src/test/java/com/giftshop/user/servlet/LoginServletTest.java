package com.giftshop.user.servlet;

import com.giftshop.dao.UserDAOImpl;
import com.giftshop.db.DBConnect;
import com.giftshop.entity.User;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginServletTest {

    private LoginServlet servlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private UserDAOImpl userDAO;
    @Mock
    private Connection connection;

    private MockedStatic<DBConnect> dbConnectMockedStatic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dbConnectMockedStatic = mockStatic(DBConnect.class);

        dbConnectMockedStatic.when(DBConnect::getConn).thenReturn(connection);
        servlet = new LoginServlet();
    }

    @AfterEach
    void tearDown() {
        dbConnectMockedStatic.close();
    }

    @Test
    void testDoPostAdminLogin() throws Exception {
        when(request.getParameter("email")).thenReturn("admin@giftshop.com");
        when(request.getParameter("password")).thenReturn("admin");
        when(request.getSession()).thenReturn(session);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("user"), any(User.class));
        verify(response).sendRedirect("admin/home.jsp");
    }

    @Test
    void testDoPostUserLoginSuccess() throws Exception {
        when(request.getParameter("email")).thenReturn("user@example.com");
        when(request.getParameter("password")).thenReturn("password123");
        when(request.getSession()).thenReturn(session);
        when(userDAO.userLogin("user@example.com", "password123")).thenReturn(new User());

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("user"), any(User.class));
        verify(response).sendRedirect("index.jsp");
    }

    @Test
    void testDoPostUserLoginFailed() throws Exception {
        when(request.getParameter("email")).thenReturn("user@example.com");
        when(request.getParameter("password")).thenReturn("password123");
        when(request.getSession()).thenReturn(session);
        when(userDAO.userLogin("user@example.com", "password123")).thenReturn(null);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("failedMsg"), anyString());
        verify(response).sendRedirect("login.jsp");
    }

    @Test
    void testDoPostException() throws Exception {
        when(request.getParameter("email")).thenReturn("user@example.com");
        when(request.getParameter("password")).thenReturn("password123");
        when(request.getSession()).thenReturn(session);
        when(userDAO.userLogin("user@example.com", "password123")).thenThrow(new RuntimeException("Test Exception"));

        servlet.doPost(request, response);

        verify(response, never()).sendRedirect(anyString());
        verify(session, never()).setAttribute(eq("user"), any(User.class));
        verify(session, never()).setAttribute(eq("failedMsg"), anyString());
    }
}
