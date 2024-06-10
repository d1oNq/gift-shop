package com.giftshop.user.servlet;

import com.giftshop.dao.UserDAOImpl;
import com.giftshop.db.DBConnect;
import com.giftshop.entity.User;
import com.giftshop.log.GiftLogger;
import com.giftshop.user.servlet.UpdateAddressServlet;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateAddressServletTest {

    private UpdateAddressServlet servlet;

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
    private MockedStatic<GiftLogger> giftLoggerMockedStatic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dbConnectMockedStatic = mockStatic(DBConnect.class);
        giftLoggerMockedStatic = mockStatic(GiftLogger.class);

        dbConnectMockedStatic.when(DBConnect::getConn).thenReturn(connection);
        servlet = new UpdateAddressServlet();
    }

    @AfterEach
    void tearDown() {
        dbConnectMockedStatic.close();
        giftLoggerMockedStatic.close();
    }

    @Test
    void testDoPostAddressUpdate() throws Exception {
        User user = new User();
        user.setId(1);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("address")).thenReturn("123 Street");
        when(request.getParameter("city")).thenReturn("City");
        when(request.getParameter("zip")).thenReturn("12345");
        when(userDAO.updateAddress(any(User.class))).thenReturn(true);

        servlet.doPost(request, response);

        verify(session).setAttribute("user", user);
        verify(session).setAttribute("successMsg", "Адресу успішно змінено");
        verify(response).sendRedirect("user_address.jsp");
    }

    @Test
    void testDoPostAddressUpdateFailed() throws Exception {
        User user = new User();
        user.setId(1);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("address")).thenReturn("123 Street");
        when(request.getParameter("city")).thenReturn("City");
        when(request.getParameter("zip")).thenReturn("12345");
        when(userDAO.updateAddress(any(User.class))).thenReturn(false);

        servlet.doPost(request, response);

        verify(session).setAttribute("failedMsg", "Щось пішло не так. Спробуйте ще раз");
        verify(response).sendRedirect("user_address.jsp");
    }

    @Test
    void testDoPostException() throws Exception {
        User user = new User();
        user.setId(1);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        dbConnectMockedStatic.when(DBConnect::getConn).thenThrow(new RuntimeException("Test Exception"));

        assertThrows(ServletException.class, () -> servlet.doPost(request, response));
        giftLoggerMockedStatic.verify(() -> GiftLogger.logError(anyString()), times(1));
    }
}
