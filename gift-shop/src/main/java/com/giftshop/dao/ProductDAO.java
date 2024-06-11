package com.giftshop.dao;

import com.giftshop.entity.Product;

import java.util.List;

public interface ProductDAO {
    boolean addProduct(Product product);

    List<Product> getAllProduct();

    Product getProductById(int id);

    boolean editProduct(Product product);

    boolean deleteProduct(int id);

    List<Product> getCandy();

    List<Product> getCookies();

    List<Product> getGiftBox();

    List<Product> getAllCandy();

    List<Product> getAllCookies();

    List<Product> getAllGiftBox();

    List<Product> getProductBySearch(String search);
}
