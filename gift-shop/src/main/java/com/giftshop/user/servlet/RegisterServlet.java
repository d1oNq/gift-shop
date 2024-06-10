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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GiftLogger.logInfo("Registration request received");

            req.setCharacterEncoding("UTF-8");

            User user = createUserFromRequest(req);
            HttpSession session = req.getSession();

            if (req.getParameter("check") != null) {
                handleRegistration(user, session, resp);
            } else {
                GiftLogger.logWarning("User did not agree to the privacy policy");
                setSessionAttributeAndRedirect(session, resp, "failedMsg", "Ви не погодилися з політикою конфіденційності");
            }

        } catch (Exception e) {
            GiftLogger.logError("Registration processing error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private User createUserFromRequest(HttpServletRequest req) {
        User user = new User();
        user.setName(req.getParameter("name"));
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        GiftLogger.logInfo("Created user from request: " + user.getEmail());
        return user;
    }

    private void handleRegistration(User user, HttpSession session, HttpServletResponse resp) throws IOException {
        UserDAOImpl userDAO = new UserDAOImpl(DBConnect.getConn());
        if (userDAO.checkUser(user.getEmail())) {
            GiftLogger.logInfo("Email is unique: " + user.getEmail());
            if (userDAO.userRegister(user)) {
                GiftLogger.logInfo("User registered successfully: " + user.getEmail());
                setSessionAttributeAndRedirect(session, resp, "successMsg", "Реєстрація пройшла успішно");
            } else {
                GiftLogger.logError("User registration failed: " + user.getEmail());
                setSessionAttributeAndRedirect(session, resp, "failedMsg", "Щось пішло не так. Спробуйте ще раз");
            }
        } else {
            GiftLogger.logWarning("User already exists with email: " + user.getEmail());
            setSessionAttributeAndRedirect(session, resp, "failedMsg", "Користувач з такою електронною поштою уже зареєстрований");
        }
    }

    private void setSessionAttributeAndRedirect(HttpSession session, HttpServletResponse resp, String attributeName, String message) throws IOException {
        session.setAttribute(attributeName, message);
        resp.sendRedirect("register.jsp");
    }
}
