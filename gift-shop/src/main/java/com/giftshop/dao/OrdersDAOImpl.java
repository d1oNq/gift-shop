package com.giftshop.dao;

import com.giftshop.entity.Orders;
import com.giftshop.log.GiftLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAOImpl implements OrdersDAO {
    private final Connection conn;

    public OrdersDAOImpl(Connection conn) {
        super();
        this.conn = conn;
    }

    @Override
    public boolean saveOrder(List<Orders> orderList) {
        boolean isSaved = false;

        try {
            GiftLogger.logInfo("Saving orders: " + orderList.toString());
            String sql = "INSERT INTO orders(orderId, userName, email, phone, address, productName, price, weight, payment) VALUES (?,?,?,?,?,?,?,?,?)";
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            for (Orders order : orderList) {
                setOrderParams(ps, order);
                ps.addBatch();
            }

            ps.executeBatch();
            conn.commit();
            isSaved = true;
            conn.setAutoCommit(true);
            GiftLogger.logInfo("Orders saved successfully.");

        } catch (Exception e) {
            GiftLogger.logError("Error saving orders: " + e.getMessage());
            e.printStackTrace();
        }

        return isSaved;
    }

    @Override
    public List<Orders> getOrders(String email) {
        String sql = "SELECT * FROM orders WHERE email = ?";
        GiftLogger.logInfo("Fetching orders for email: " + email);
        return executeQuery(sql, email);
    }

    @Override
    public List<Orders> getOrders() {
        String sql = "SELECT * FROM orders";
        GiftLogger.logInfo("Fetching all orders.");
        return executeQuery(sql);
    }

    private void setOrderParams(PreparedStatement ps, Orders order) throws Exception {
        ps.setString(1, order.getOrderId());
        ps.setString(2, order.getUserName());
        ps.setString(3, order.getEmail());
        ps.setString(4, order.getPhone());
        ps.setString(5, order.getAddress());
        ps.setString(6, order.getProductName());
        ps.setString(7, order.getPrice());
        ps.setString(8, order.getWeight());
        ps.setString(9, order.getPayment());
    }

    private List<Orders> executeQuery(String sql, Object... params) {
        List<Orders> orderList = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Orders order = new Orders();
                order.setOrderId(rs.getString(1));
                order.setUserName(rs.getString(2));
                order.setEmail(rs.getString(3));
                order.setPhone(rs.getString(4));
                order.setAddress(rs.getString(5));
                order.setProductName(rs.getString(6));
                order.setPrice(rs.getString(7));
                order.setWeight(rs.getString(8));
                order.setPayment(rs.getString(9));
                orderList.add(order);
            }
            GiftLogger.logInfo("SQL query executed successfully: " + sql);

        } catch (Exception e) {
            GiftLogger.logError("Error executing SQL query: " + e.getMessage());
            e.printStackTrace();
        }

        return orderList;
    }
}
