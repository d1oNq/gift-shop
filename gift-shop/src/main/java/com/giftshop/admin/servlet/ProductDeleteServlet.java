package com.giftshop.admin.servlet;

import com.giftshop.dao.ProductDAOImpl;
import com.giftshop.db.DBConnect;
import com.giftshop.log.GiftLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/delete")
public class ProductDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GiftLogger.logInfo("Received a request to delete a product.");

            int productId = Integer.parseInt(req.getParameter("id"));
            GiftLogger.logInfo("Product ID to delete: " + productId);

            ProductDAOImpl dao = new ProductDAOImpl(DBConnect.getConn());
            boolean isDeleted = dao.deleteProduct(productId);
            HttpSession session = req.getSession();

            if (isDeleted) {
                GiftLogger.logInfo("Product successfully deleted.");
                session.setAttribute("successMsg", "Товар успішно видалено");
            } else {
                GiftLogger.logWarning("Failed to delete product.");
                session.setAttribute("failedMsg", "Щось пішло не так");
            }
            resp.sendRedirect("admin/all_products.jsp");

        } catch (Exception e) {
            GiftLogger.logError("Error occurred while deleting product: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error deleting product", e);
        }
    }
}
