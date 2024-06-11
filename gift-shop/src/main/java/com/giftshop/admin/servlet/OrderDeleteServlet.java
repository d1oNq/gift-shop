package com.giftshop.admin.servlet;

import com.giftshop.dao.OrdersDAOImpl;
import com.giftshop.db.DBConnect;
import com.giftshop.log.GiftLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/delete_order")
public class OrderDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GiftLogger.logInfo("Received a request to delete an order.");

            int orderId = Integer.parseInt(req.getParameter("id"));
            GiftLogger.logInfo("Order ID to delete: " + orderId);

            OrdersDAOImpl dao = new OrdersDAOImpl(DBConnect.getConn());
            boolean isDeleted = dao.deleteOrder(orderId);
            HttpSession session = req.getSession();

            if (isDeleted) {
                GiftLogger.logInfo("Order successfully deleted.");
                session.setAttribute("successMsg", "Замовлення успішно видалено");
            } else {
                GiftLogger.logWarning("Failed to delete order.");
                session.setAttribute("failedMsg", "Щось пішло не так");
            }
            resp.sendRedirect("admin/orders.jsp");

        } catch (Exception e) {
            GiftLogger.logError("Error occurred while deleting order: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error deleting order", e);
        }
    }
}
