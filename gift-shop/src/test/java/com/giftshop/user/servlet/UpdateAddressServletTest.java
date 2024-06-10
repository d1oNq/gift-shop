package com.giftshop.user.servlet;

import com.giftshop.dao.UserDAOImpl;
import com.giftshop.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

class UpdateAddressServletTest {

    private UpdateAddressServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private UserDAOImpl userDAO;
    private User user;

    @BeforeEach
    void setUp() {
        servlet = new UpdateAddressServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        userDAO = mock(UserDAOImpl.class);
        user = new User();
        user.setId(1);

        // Set mock behavior
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("address")).thenReturn("123 Main St");
        when(request.getParameter("city")).thenReturn("Springfield");
        when(request.getParameter("zip")).thenReturn("12345");
    }

    @Test
    void testUpdateAddressSuccess() throws IOException, ServletException {
        when(userDAO.updateAddress(any(User.class))).thenReturn(true);

        servlet.processRequest(request, response, false);

        InOrder inOrder = inOrder(session, response);
        inOrder.verify(session).setAttribute("user", user);
        inOrder.verify(session).setAttribute("successMsg", "Адресу успішно змінено");
        inOrder.verify(response).sendRedirect("user_address.jsp");
    }

    @Test
    void testUpdateAddressFailure() throws IOException, ServletException {
        when(userDAO.updateAddress(any(User.class))).thenReturn(false);

        servlet.processRequest(request, response, false);

        InOrder inOrder = inOrder(session, response);
        inOrder.verify(session).setAttribute(eq("failedMsg"), eq("Щось пішло не так. Спробуйте ще раз"));
        inOrder.verify(response).sendRedirect("user_address.jsp");
    }

    @Test
    void testDoPost() throws IOException, ServletException {
        UpdateAddressServlet spyServlet = spy(servlet);

        spyServlet.doPost(request, response);

        verify(spyServlet).processRequest(request, response, false);
    }
}
