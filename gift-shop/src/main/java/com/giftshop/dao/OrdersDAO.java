package com.giftshop.dao;

import com.giftshop.entity.Orders;

import java.util.List;

public interface OrdersDAO {
    boolean saveOrder(List<Orders> orderList);

    List<Orders> getOrders(String email);

    List<Orders> getOrders();
}
