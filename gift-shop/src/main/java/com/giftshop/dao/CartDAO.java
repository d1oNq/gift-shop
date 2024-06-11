package com.giftshop.dao;

import com.giftshop.entity.Cart;

import java.util.List;

public interface CartDAO {
    boolean addToCart(Cart cart);

    List<Cart> getProductByUser(int userId);

    boolean removeFromCart(int productId, int userId, int cartId);

    boolean clearCart(int userId);
}
