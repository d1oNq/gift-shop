package com.giftshop.user.servlet;

import com.giftshop.dao.CartDAOImpl;
import com.giftshop.dao.OrdersDAOImpl;
import com.giftshop.db.DBConnect;
import com.giftshop.entity.Cart;
import com.giftshop.entity.Orders;
import com.giftshop.log.GiftLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/order")
public class OrdersServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GiftLogger.logInfo("Order request received");

            req.setCharacterEncoding("UTF-8");

            HttpSession session = req.getSession();

            int userId = Integer.parseInt(req.getParameter("id"));
            String userName = req.getParameter("name");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String fullAddress = formatFullAddress(req.getParameter("address"), req.getParameter("city"), req.getParameter("zip"));
            String paymentMethod = req.getParameter("payment");

            GiftLogger.logInfo("User details: userId=" + userId + ", userName=" + userName + ", email=" + email + ", phone=" + phone);

            List<Cart> cartItems = getCartItemsByUser(userId);

            if (cartItems.isEmpty()) {
                GiftLogger.logWarning("Cart is empty for userId=" + userId);
                setSessionMessage(session, "failedMsg", "Кошик не може бути пустим", resp, "cart.jsp");
                return;
            }

            if ("no-select".equals(paymentMethod)) {
                GiftLogger.logWarning("Payment method not selected for userId=" + userId);
                setSessionMessage(session, "failedMsg", "Виберіть спосіб оплати", resp, "cart.jsp");
                return;
            }

            Orders order = createOrder(userName, email, phone, fullAddress, paymentMethod, cartItems);

            boolean isOrderSaved = saveOrder(order);

            if (isOrderSaved) {
                GiftLogger.logInfo("Order successfully placed for userId=" + userId);
                setSessionMessage(session, "successMsg", "Замовлення оформлено успішно", resp, "order_success.jsp");
            } else {
                GiftLogger.logError("Order not saved for userId=" + userId);
                setSessionMessage(session, "failedMsg", "Щось пішло не так. Замовлення не оформлено, спробуйте ще раз", resp, "cart.jsp");
            }

        } catch (Exception e) {
            GiftLogger.logError("Order processing error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String formatFullAddress(String address, String city, String zip) {
        return address + ", " + city + ", " + zip;
    }

    private List<Cart> getCartItemsByUser(int userId) throws Exception {
        CartDAOImpl cartDao = new CartDAOImpl(DBConnect.getConn());
        return cartDao.getProductByUser(userId);
    }

    private Orders createOrder(String userName, String email, String phone, String fullAddress, String paymentMethod, List<Cart> cartItems) {
        Orders order = new Orders();
        order.setUserName(userName);
        order.setEmail(email);
        order.setPhone(phone);
        order.setAddress(fullAddress);
        order.setPayment(paymentMethod);

        StringBuilder products = new StringBuilder();
        double totalWeight = 0.0;
        double totalPrice = 0.0;

        for (Cart cart : cartItems) {
            products.append(cart.getProductName()).append(" (").append(cart.getCategory()).append("); ");
            totalWeight += cart.getWeight();
            totalPrice += cart.getPrice();
        }
        order.setProductName(products.toString());
        order.setWeight(String.valueOf(totalWeight));
        order.setPrice(String.valueOf(totalPrice));

        return order;
    }

    private boolean saveOrder(Orders order) throws Exception {
        OrdersDAOImpl ordersDao = new OrdersDAOImpl(DBConnect.getConn());
        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(order);
        return ordersDao.saveOrder(ordersList);
    }

    private void setSessionMessage(HttpSession session, String attributeName, String message, HttpServletResponse resp, String redirectPage) throws IOException {
        session.setAttribute(attributeName, message);
        resp.sendRedirect(redirectPage);
    }
}
