package com.giftshop.user.servlet;

import com.giftshop.dao.UserDAOImpl;
import com.giftshop.db.DBConnect;
import com.giftshop.entity.User;
import com.giftshop.log.GiftLogger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public abstract class BaseUpdateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp, boolean isProfileUpdate) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            GiftLogger.logInfo("Character encoding set to UTF-8");

            HttpSession session = req.getSession();
            User currentUser = (User) session.getAttribute("user");

            if (isProfileUpdate) {
                updateProfileDetails(req, currentUser);
                GiftLogger.logInfo("Updating profile details for user ID: " + currentUser.getId());
            } else {
                updateAddressDetails(req, currentUser);
                GiftLogger.logInfo("Updating address details for user ID: " + currentUser.getId());
            }

            UserDAOImpl userDAO = new UserDAOImpl(DBConnect.getConn());
            boolean isUpdated = isProfileUpdate ? userDAO.updateProfile(currentUser) : userDAO.updateAddress(currentUser);

            handleUpdateResult(session, resp, isUpdated, currentUser, isProfileUpdate ? "edit_profile.jsp" : "user_address.jsp", isProfileUpdate ? "Профіль успішно оновлено" : "Адресу успішно змінено");

        } catch (Exception e) {
            GiftLogger.logError("Error processing request: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error processing request", e);
        }
    }

    private void updateProfileDetails(HttpServletRequest req, User user) {
        user.setName(req.getParameter("name"));
        user.setEmail(req.getParameter("email"));
        user.setPhone(req.getParameter("phone"));
        user.setPassword(req.getParameter("password"));
        GiftLogger.logInfo("Profile details updated for user ID: " + user.getId());
    }

    private void updateAddressDetails(HttpServletRequest req, User user) {
        user.setAddress(req.getParameter("address"));
        user.setCity(req.getParameter("city"));
        user.setZip(req.getParameter("zip"));
        GiftLogger.logInfo("Address details updated for user ID: " + user.getId());
    }

    private void handleUpdateResult(HttpSession session, HttpServletResponse resp, boolean isUpdated, User user, String redirectPage, String successMessage) throws IOException {
        if (isUpdated) {
            session.setAttribute("user", user);
            session.setAttribute("successMsg", successMessage);
            GiftLogger.logInfo(successMessage + " for user ID: " + user.getId());
        } else {
            session.setAttribute("failedMsg", "Щось пішло не так. Спробуйте ще раз");
            GiftLogger.logWarning("Failed to update for user ID: " + user.getId());
        }
        resp.sendRedirect(redirectPage);
    }
}
