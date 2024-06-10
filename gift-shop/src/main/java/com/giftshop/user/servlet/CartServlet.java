package com.giftshop.user.servlet;

import com.giftshop.dao.CartDAOImpl;
import com.giftshop.dao.ProductDAOImpl;
import com.giftshop.db.DBConnect;
import com.giftshop.entity.Cart;
import com.giftshop.entity.Product;
import com.giftshop.log.GiftLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GiftLogger.logInfo("Received request to add product to cart");

            req.setCharacterEncoding("UTF-8");

            int productId = Integer.parseInt(req.getParameter("productId"));
            int userId = Integer.parseInt(req.getParameter("userId"));

            GiftLogger.logInfo("Creating cart item for product ID: " + productId + " and user ID: " + userId);

            Cart cart = createCart(productId, userId);

            CartDAOImpl cartDAO = new CartDAOImpl(DBConnect.getConn());
            boolean isAddedToCart = cartDAO.addToCart(cart);

            HttpSession session = req.getSession();
            String referer = req.getHeader("Referer");

            if (isAddedToCart) {
                GiftLogger.logInfo("Product successfully added to cart");
                session.setAttribute("cart", "Успішно добавлено в кошик");
            } else {
                GiftLogger.logWarning("Failed to add product to cart");
                session.setAttribute("cart", "Щось пішло не так. Спробуйте ще раз");
            }

            if (referer != null && !referer.isEmpty()) {
                resp.sendRedirect(referer);
            } else {
                resp.sendRedirect("index.jsp");
            }

        } catch (Exception e) {
            GiftLogger.logError("Error adding product to cart: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Cart createCart(int productId, int userId) {
        ProductDAOImpl productDAO = new ProductDAOImpl(DBConnect.getConn());
        Product product = productDAO.getProductById(productId);

        Cart cart = new Cart();
        cart.setProductId(productId);
        cart.setUserId(userId);
        cart.setProductName(product.getProductName());
        cart.setCategory(product.getCategory());
        cart.setWeight(Double.parseDouble(product.getWeight()));
        cart.setPrice(Double.parseDouble(product.getPrice()));

        GiftLogger.logInfo("Cart item created: " + cart);

        return cart;
    }
}
