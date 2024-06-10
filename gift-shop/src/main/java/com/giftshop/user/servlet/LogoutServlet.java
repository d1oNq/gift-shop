package com.giftshop.user.servlet;

import com.giftshop.log.GiftLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GiftLogger.logInfo("Logout request received");

            HttpSession session = req.getSession();
            if (session.getAttribute("user") != null) {
                GiftLogger.logInfo("User " + session.getAttribute("user") + " logging out");
                session.removeAttribute("user");
                GiftLogger.logInfo("User successfully logged out");
            } else {
                GiftLogger.logWarning("No user found in session during logout");
            }
            resp.sendRedirect("login.jsp");
        } catch (Exception e) {
            GiftLogger.logError("Logout error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
