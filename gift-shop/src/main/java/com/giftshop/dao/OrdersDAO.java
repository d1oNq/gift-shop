package com.giftshop.dao;

import com.giftshop.entity.Orders;

import java.util.List;

public interface OrdersDAO {
    boolean saveOrder(List<Orders> orderList);

    Orders getOrderById(int id);

    List<Orders> getOrders(String email);

    List<Orders> getOrders();

    boolean updateOrder(Orders order);

    boolean deleteOrder(int orderId);
}

