package com.giftshop.dao;

import com.giftshop.entity.Cart;
import com.giftshop.log.GiftLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDAOImpl implements CartDAO {

    private final Connection conn;

    public CartDAOImpl(Connection conn) {
        super();
        this.conn = conn;
    }

    @Override
    public boolean addToCart(Cart cart) {
        String sql = "INSERT INTO cart(productID, userID, productName, category, weight, price) VALUES(?, ?, ?, ?, ?, ?)";
        GiftLogger.logInfo("Adding to cart: " + cart.toString());
        return executeUpdate(sql, cart.getProductId(), cart.getUserId(), cart.getProductName(), cart.getCategory(), cart.getWeight(), cart.getPrice());
    }

    @Override
    public List<Cart> getProductByUser(int userId) {
        String sql = "SELECT * FROM cart WHERE userID = ?";
        GiftLogger.logInfo("Fetching products for user ID: " + userId);
        return executeQuery(sql, userId);
    }

    @Override
    public boolean removeFromCart(int productId, int userId, int cartId) {
        String sql = "DELETE FROM cart WHERE productID = ? AND userID = ? AND cartID = ?";
        GiftLogger.logInfo("Removing from cart - Product ID: " + productId + ", User ID: " + userId + ", Cart ID: " + cartId);
        return executeUpdate(sql, productId, userId, cartId);
    }

    @Override
    public boolean clearCart(int userId) {
        String sql = "DELETE FROM cart WHERE userID = ?";
        GiftLogger.logInfo("Clearing cart for user ID: " + userId);
        return executeUpdate(sql, userId);
    }

    private boolean executeUpdate(String sql, Object... params) {
        boolean success = false;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            int result = ps.executeUpdate();
            success = result == 1;
            if (success) {
                GiftLogger.logInfo("SQL update executed successfully: " + sql);
            } else {
                GiftLogger.logWarning("SQL update failed: " + sql);
            }
        } catch (Exception e) {
            GiftLogger.logError("Error executing SQL update: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }

    private List<Cart> executeQuery(String sql, Object... params) {
        List<Cart> cartList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setCartId(rs.getInt(1));
                cart.setProductId(rs.getInt(2));
                cart.setUserId(rs.getInt(3));
                cart.setProductName(rs.getString(4));
                cart.setCategory(rs.getString(5));
                cart.setWeight(rs.getDouble(6));
                cart.setPrice(rs.getDouble(7));
                cartList.add(cart);
            }
            GiftLogger.logInfo("SQL query executed successfully: " + sql);
        } catch (Exception e) {
            GiftLogger.logError("Error executing SQL query: " + e.getMessage());
            e.printStackTrace();
        }
        return cartList;
    }
}
