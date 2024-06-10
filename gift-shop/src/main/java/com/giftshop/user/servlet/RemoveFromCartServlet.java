package com.giftshop.user.servlet;

import com.giftshop.dao.CartDAOImpl;
import com.giftshop.db.DBConnect;
import com.giftshop.log.GiftLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/remove")
public class RemoveFromCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(req.getParameter("productId"));
            int userId = Integer.parseInt(req.getParameter("userId"));
            int cartId = Integer.parseInt(req.getParameter("cartId"));

            GiftLogger.logInfo("Removing product from cart: Product ID - " + productId + ", User ID - " + userId + ", Cart ID - " + cartId);

            CartDAOImpl dao = new CartDAOImpl(DBConnect.getConn());
            boolean isRemoved = dao.removeFromCart(productId, userId, cartId);

            HttpSession session = req.getSession();

            if (isRemoved) {
                GiftLogger.logInfo("Product removed successfully from cart");
                session.setAttribute("successMsg", "Успішно видалено з кошика");
            } else {
                GiftLogger.logError("Failed to remove product from cart");
                session.setAttribute("failedMsg", "Щось пішло не так. Спробуйте ще раз");
            }
            resp.sendRedirect("cart.jsp");

        } catch (NumberFormatException e) {
            GiftLogger.logError("Invalid parameter format for removing product from cart");
            HttpSession session = req.getSession();
            session.setAttribute("failedMsg", "Неправильний формат параметра. Спробуйте ще раз");
            resp.sendRedirect("cart.jsp");
        } catch (Exception e) {
            GiftLogger.logError("Error while removing product from cart: " + e.getMessage());
            e.printStackTrace();
            HttpSession session = req.getSession();
            session.setAttribute("failedMsg", "Щось пішло не так. Спробуйте ще раз");
            resp.sendRedirect("cart.jsp");
        }
    }
}
