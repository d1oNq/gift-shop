package com.giftshop.user.servlet;

import com.giftshop.dao.UserDAOImpl;
import com.giftshop.db.DBConnect;
import com.giftshop.entity.User;
import com.giftshop.log.GiftLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String ADMIN_EMAIL = "admin@giftshop.com";
    private static final String ADMIN_PASSWORD = "admin";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GiftLogger.logInfo("Received login request");

            req.setCharacterEncoding("UTF-8");

            String email = req.getParameter("email");
            String password = req.getParameter("password");

            GiftLogger.logInfo("Attempting login for email: " + email);

            HttpSession session = req.getSession();

            boolean isAdmin = ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password);

            if (isAdmin) {
                GiftLogger.logInfo("Admin login successful for email: " + email);
                loginAdmin(session, resp);
            } else {
                GiftLogger.logInfo("User login attempt for email: " + email);
                loginUser(email, password, session, resp);
            }

        } catch (Exception e) {
            GiftLogger.logError("Login error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loginAdmin(HttpSession session, HttpServletResponse resp) throws IOException {
        User adminUser = new User();
        adminUser.setName("Admin");
        session.setAttribute("user", adminUser);
        GiftLogger.logInfo("Admin logged in, redirecting to admin/home.jsp");
        resp.sendRedirect("admin/home.jsp");
    }

    private void loginUser(String email, String password, HttpSession session, HttpServletResponse resp) throws IOException {
        UserDAOImpl userDAO = new UserDAOImpl(DBConnect.getConn());
        User user = userDAO.userLogin(email, password);

        if (user != null) {
            GiftLogger.logInfo("User login successful for email: " + email);
            session.setAttribute("user", user);
            resp.sendRedirect("index.jsp");
        } else {
            GiftLogger.logWarning("Failed login attempt for email: " + email);
            session.setAttribute("failedMsg", "Email чи Пароль введено не правильно");
            resp.sendRedirect("login.jsp");
        }
    }
}
