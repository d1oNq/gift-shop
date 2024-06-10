package com.giftshop.entity;

public class Product {
    private int productId;
    private String productName;
    private String category;
    private String weight;
    private String price;
    private String status;
    private String photo;

    public Product() {
        super();
    }

    public Product(String productName, String category, String weight, String price, String status, String photo) {
        super();
        this.productName = productName;
        this.category = category;
        this.weight = weight;
        this.price = price;
        this.status = status;
        this.photo = photo;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", weight='" + weight + '\'' +
                ", price='" + price + '\'' +
                ", status='" + status + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
