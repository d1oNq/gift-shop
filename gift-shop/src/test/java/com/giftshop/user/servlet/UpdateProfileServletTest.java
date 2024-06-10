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

class UpdateProfileServletTest {

    private UpdateProfileServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private UserDAOImpl userDAO;
    private User user;

    @BeforeEach
    void setUp() {
        servlet = new UpdateProfileServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        userDAO = mock(UserDAOImpl.class);
        user = new User();
        user.setId(1);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("name")).thenReturn("John Doe");
        when(request.getParameter("email")).thenReturn("john.doe@example.com");
        when(request.getParameter("phone")).thenReturn("1234567890");
        when(request.getParameter("password")).thenReturn("password123");
    }

    @Test
    void testUpdateProfileSuccess() throws IOException, ServletException {
        when(userDAO.updateProfile(any(User.class))).thenReturn(true);

        servlet.processRequest(request, response, true);

        InOrder inOrder = inOrder(session, response);
        inOrder.verify(session).setAttribute("user", user);
        inOrder.verify(session).setAttribute("successMsg", "Профіль успішно оновлено");
        inOrder.verify(response).sendRedirect("edit_profile.jsp");
    }

    @Test
    void testUpdateProfileFailure() throws IOException, ServletException {
        when(userDAO.updateProfile(any(User.class))).thenReturn(false);

        servlet.processRequest(request, response, true);

        InOrder inOrder = inOrder(session, response);
        inOrder.verify(session).setAttribute(eq("failedMsg"), eq("Щось пішло не так. Спробуйте ще раз"));
        inOrder.verify(response).sendRedirect("edit_profile.jsp");
    }

    @Test
    void testDoPost() throws IOException, ServletException {
        UpdateProfileServlet spyServlet = spy(servlet);

        spyServlet.doPost(request, response);

        verify(spyServlet).processRequest(request, response, true);
    }
}
