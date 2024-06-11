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

@WebServlet("/edit_product")
@MultipartConfig
public class ProductEditServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GiftLogger.logInfo("Received a request to edit a product.");
            req.setCharacterEncoding("UTF-8");

            Product product = buildProductFromRequest(req);
            GiftLogger.logInfo("Product details extracted from request: " + product.toString());

            ProductDAOImpl dao = new ProductDAOImpl(DBConnect.getConn());
            Product existingProduct = dao.getProductById(product.getProductId());

            if (isImageUpdated(req)) {
                String newPhoto = uploadImageAndGetFileName(req);
                product.setPhoto(newPhoto);
                GiftLogger.logInfo("Product image updated to: " + newPhoto);
            } else {
                product.setPhoto(existingProduct.getPhoto());
                GiftLogger.logInfo("Product image remains the same: " + existingProduct.getPhoto());
            }

            boolean isUpdated = dao.editProduct(product);
            HttpSession session = req.getSession();

            if (isUpdated) {
                GiftLogger.logInfo("Product successfully updated.");
                session.setAttribute("successMsg", "Товар успішно оновлено");
            } else {
                GiftLogger.logWarning("Failed to update product.");
                session.setAttribute("failedMsg", "Щось пішло не так. Спробуйте ще раз");
            }
            resp.sendRedirect("admin/products.jsp");

        } catch (Exception e) {
            GiftLogger.logError("Error occurred while updating product: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error updating product", e);
        }
    }

    protected Product buildProductFromRequest(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String category = req.getParameter("category");
        String weight = req.getParameter("weight");
        String price = req.getParameter("price");
        String status = req.getParameter("status");

        Product product = new Product();
        product.setProductId(id);
        product.setProductName(name);
        product.setCategory(category);
        product.setWeight(weight);
        product.setPrice(price);
        product.setStatus(status);

        GiftLogger.logInfo("Built product from request parameters: " + product.toString());
        return product;
    }

    protected boolean isImageUpdated(HttpServletRequest req) throws IOException, ServletException {
        Part part = req.getPart("image");
        String fileName = part.getSubmittedFileName();
        boolean isUpdated = fileName != null && !fileName.isEmpty();
        GiftLogger.logInfo("Image update status: " + isUpdated);
        return isUpdated;
    }

    private String uploadImageAndGetFileName(HttpServletRequest req) throws IOException, ServletException {
        Part part = req.getPart("image");
        String fileName = part.getSubmittedFileName();
        String path = getServletContext().getRealPath("") + "product";

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
            GiftLogger.logInfo("Created directory for product images: " + path);
        }

        part.write(path + File.separator + fileName);
        GiftLogger.logInfo("Product image written to: " + path + File.separator + fileName);
        return fileName;
    }
}
