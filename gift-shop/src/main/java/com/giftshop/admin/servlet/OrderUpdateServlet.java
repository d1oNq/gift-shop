package com.giftshop.admin.servlet;

import com.giftshop.dao.OrdersDAOImpl;
import com.giftshop.db.DBConnect;
import com.giftshop.entity.Orders;
import com.giftshop.log.GiftLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/edit_order")
public class OrderUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GiftLogger.logInfo("Received a request to update an order.");
            req.setCharacterEncoding("UTF-8");

            Orders order = buildOrderFromRequest(req);
            GiftLogger.logInfo("Order details extracted from request: " + order.toString());

            OrdersDAOImpl dao = new OrdersDAOImpl(DBConnect.getConn());
            boolean isUpdated = dao.updateOrder(order);
            HttpSession session = req.getSession();

            if (isUpdated) {
                GiftLogger.logInfo("Order successfully updated.");
                session.setAttribute("successMsg", "Замовлення успішно оновлено");
            } else {
                GiftLogger.logWarning("Failed to update order.");
                session.setAttribute("failedMsg", "Щось пішло не так. Спробуйте ще раз");
            }
            resp.sendRedirect("admin/orders.jsp");

        } catch (Exception e) {
            GiftLogger.logError("Error occurred while updating order: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error updating order", e);
        }
    }

    protected Orders buildOrderFromRequest(HttpServletRequest req) {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        String userName = req.getParameter("userName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String productName = req.getParameter("productName");
        String price = req.getParameter("price");
        String weight = req.getParameter("weight");
        String payment = req.getParameter("payment");

        Orders order = new Orders();
        order.setOrderId(orderId);
        order.setUserName(userName);
        order.setEmail(email);
        order.setPhone(phone);
        order.setAddress(address);
        order.setProductName(productName);
        order.setPrice(price);
        order.setWeight(weight);
        order.setPayment(payment);

        GiftLogger.logInfo("Built order from request parameters: " + order.toString());
        return order;
    }
}
