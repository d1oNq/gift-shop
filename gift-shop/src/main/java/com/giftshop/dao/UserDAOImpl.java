package com.giftshop.dao;

import com.giftshop.entity.User;
import com.giftshop.log.GiftLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    private final Connection conn;

    public UserDAOImpl(Connection conn) {
        super();
        this.conn = conn;
    }

    @Override
    public boolean userRegister(User user) {
        String sql = "INSERT INTO user (name, email, password) VALUES (?, ?, ?)";
        GiftLogger.logInfo("Registering user: " + user.toString());
        return executeUpdate(sql, user.getName(), user.getEmail(), user.getPassword());
    }

    @Override
    public User userLogin(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        GiftLogger.logInfo("User login attempt: email=" + email);
        return executeQuery(sql, email, password);
    }

    @Override
    public boolean updateProfile(User user) {
        String sql = "UPDATE user SET name=?, email=?, phone=?, password=? WHERE id=?";
        GiftLogger.logInfo("Updating profile for user: " + user.toString());
        return executeUpdate(sql, user.getName(), user.getEmail(), user.getPhone(), user.getPassword(), user.getId());
    }

    @Override
    public boolean updateAddress(User user) {
        String sql = "UPDATE user SET address=?, city=?, zip=? WHERE id=?";
        GiftLogger.logInfo("Updating address for user: " + user.toString());
        return executeUpdate(sql, user.getAddress(), user.getCity(), user.getZip(), user.getId());
    }

    @Override
    public boolean checkUser(String email) {
        boolean isUserUnique = true;
        try {
            String sql = "SELECT * FROM user WHERE email = ?";
            GiftLogger.logInfo("Checking if user exists with email: " + email);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isUserUnique = false;
                GiftLogger.logWarning("User already exists with email: " + email);
            } else {
                GiftLogger.logInfo("No user exists with email: " + email);
            }
        } catch (SQLException e) {
            GiftLogger.logError("Error checking user existence: " + e.getMessage());
            e.printStackTrace();
        }
        return isUserUnique;
    }

    private boolean executeUpdate(String sql, Object... params) {
        boolean isSuccess = false;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            int i = ps.executeUpdate();
            if (i == 1) {
                isSuccess = true;
                GiftLogger.logInfo("SQL update executed successfully: " + sql);
            } else {
                GiftLogger.logWarning("SQL update affected " + i + " rows: " + sql);
            }
        } catch (SQLException e) {
            GiftLogger.logError("Error executing SQL update: " + e.getMessage());
            e.printStackTrace();
        }
        return isSuccess;
    }

    private User executeQuery(String sql, Object... params) {
        User user = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setPhone(rs.getString(5));
                user.setAddress(rs.getString(6));
                user.setCity(rs.getString(7));
                user.setZip(rs.getString(8));
                GiftLogger.logInfo("User fetched successfully: " + user.toString());
            } else {
                GiftLogger.logWarning("No user found for the provided query: " + sql);
            }
        } catch (SQLException e) {
            GiftLogger.logError("Error executing SQL query: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }
}
