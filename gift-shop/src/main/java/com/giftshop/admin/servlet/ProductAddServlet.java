package com.giftshop.admin.servlet;

import com.giftshop.dao.ProductDAOImpl;
import com.giftshop.db.DBConnect;
import com.giftshop.entity.Product;
import com.giftshop.log.GiftLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet("/add_product")
@MultipartConfig
public class ProductAddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GiftLogger.logInfo("Received a request to add a new product.");
            req.setCharacterEncoding("UTF-8");

            Product product = buildProductFromRequest(req);
            GiftLogger.logInfo("Product details extracted from request: " + product.toString());

            ProductDAOImpl dao = new ProductDAOImpl(DBConnect.getConn());
            HttpSession session = req.getSession();
            boolean isAdded = dao.addProduct(product);

            if (isAdded) {
                GiftLogger.logInfo("Product successfully added to the database.");
                saveProductImage(req, product.getPhoto());
                GiftLogger.logInfo("Product image saved successfully.");
                session.setAttribute("successMsg", "Товар успішно добавлений");
            } else {
                GiftLogger.logWarning("Failed to add product to the database.");
                session.setAttribute("failedMsg", "Щось пішло не так");
            }
            resp.sendRedirect("admin/add_product.jsp");

        } catch (Exception e) {
            GiftLogger.logError("Error occurred while adding product: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error adding product", e);
        }
    }

    private Product buildProductFromRequest(HttpServletRequest req) throws IOException, ServletException {
        String name = req.getParameter("name");
        String category = req.getParameter("category");
        String weight = req.getParameter("weight");
        String price = req.getParameter("price");
        String status = req.getParameter("status");
        Part imagePart = req.getPart("image");
        String fileName = imagePart.getSubmittedFileName();

        GiftLogger.logInfo("Building product from request parameters.");
        return new Product(name, category, weight, price, status, fileName);
    }

    private void saveProductImage(HttpServletRequest req, String fileName) throws IOException, ServletException {
        Part part = req.getPart("image");
        String path = getServletContext().getRealPath("") + "product";

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
            GiftLogger.logInfo("Created directory for product images: " + path);
        }

        part.write(path + File.separator + fileName);
        GiftLogger.logInfo("Product image written to: " + path + File.separator + fileName);
    }
}
