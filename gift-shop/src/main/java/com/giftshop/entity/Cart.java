package com.giftshop.entity;

public class Cart {
    private int cartId;
    private int productId;
    private int userId;
    private String productName;
    private String category;
    private Double weight;
    private Double price;

    public Cart() {
        super();
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", productId=" + productId +
                ", userId=" + userId +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }
}
