package com.giftshop.dao;

import com.giftshop.entity.Product;
import com.giftshop.log.GiftLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    private final Connection conn;

    public ProductDAOImpl(Connection conn) {
        super();
        this.conn = conn;
    }

    @Override
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO product (productName, category, weight, price, status, photo) VALUES (?, ?, ?, ?, ?, ?)";
        GiftLogger.logInfo("Adding product: " + product.toString());
        return executeUpdate(sql, product.getProductName(), product.getCategory(), product.getWeight(), product.getPrice(), product.getStatus(), product.getPhoto());
    }

    @Override
    public List<Product> getAllProduct() {
        String sql = "SELECT * FROM product";
        GiftLogger.logInfo("Fetching all products.");
        return executeQuery(sql);
    }

    @Override
    public Product getProductById(int id) {
        String sql = "SELECT * FROM product WHERE productId = ?";
        GiftLogger.logInfo("Fetching product by ID: " + id);
        List<Product> products = executeQuery(sql, id);
        return products.isEmpty() ? null : products.get(0);
    }

    @Override
    public boolean updateEditProduct(Product product) {
        String sql = "UPDATE product SET productName = ?, category = ?, weight = ?, price = ?, status = ?, photo = ? WHERE productId = ?";
        GiftLogger.logInfo("Updating product: " + product.toString());
        return executeUpdate(sql, product.getProductName(), product.getCategory(), product.getWeight(), product.getPrice(), product.getStatus(), product.getPhoto(), product.getProductId());
    }

    @Override
    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM product WHERE productId = ?";
        GiftLogger.logInfo("Deleting product by ID: " + id);
        return executeUpdate(sql, id);
    }

    @Override
    public List<Product> getCandy() {
        return getProductsByCategory("Цукерки", true);
    }

    @Override
    public List<Product> getCookies() {
        return getProductsByCategory("Печиво", true);
    }

    @Override
    public List<Product> getGiftBox() {
        return getProductsByCategory("Подарункові Набори", true);
    }

    @Override
    public List<Product> getAllCandy() {
        return getProductsByCategory("Цукерки", false);
    }

    @Override
    public List<Product> getAllCookies() {
        return getProductsByCategory("Печиво", false);
    }

    @Override
    public List<Product> getAllGiftBox() {
        return getProductsByCategory("Подарункові Набори", false);
    }

    @Override
    public List<Product> getProductBySearch(String search) {
        String sql = "SELECT * FROM product WHERE (productName LIKE ? OR category LIKE ?) AND status = 'active' ORDER BY productId DESC";
        GiftLogger.logInfo("Searching products with keyword: " + search);
        return executeQuery(sql, "%" + search + "%", "%" + search + "%");
    }

    private List<Product> getProductsByCategory(String category, boolean limit) {
        String sql = "SELECT * FROM product WHERE category = ? AND status = 'Активний' ORDER BY productId DESC";
        if (limit) {
            sql += " LIMIT 4";
        }
        GiftLogger.logInfo("Fetching products by category: " + category + " with limit: " + limit);
        return executeQuery(sql, category);
    }

    private boolean executeUpdate(String sql, Object... params) {
        boolean result = false;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 1) {
                result = true;
                GiftLogger.logInfo("SQL update executed successfully: " + sql);
            } else {
                GiftLogger.logWarning("SQL update affected " + affectedRows + " rows: " + sql);
            }
        } catch (Exception e) {
            GiftLogger.logError("Error executing SQL update: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    private List<Product> executeQuery(String sql, Object... params) {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt(1));
                product.setProductName(rs.getString(2));
                product.setCategory(rs.getString(3));
                product.setWeight(rs.getString(4));
                product.setPrice(rs.getString(5));
                product.setStatus(rs.getString(6));
                product.setPhoto(rs.getString(7));
                products.add(product);
            }
            GiftLogger.logInfo("SQL query executed successfully: " + sql);
        } catch (Exception e) {
            GiftLogger.logError("Error executing SQL query: " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }
}
