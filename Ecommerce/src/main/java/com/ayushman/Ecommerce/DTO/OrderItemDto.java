package com.ayushman.Ecommerce.DTO;

public class OrderItemDto {


    private String productName;

    private Double productPrice;

    private int quantity;

    public OrderItemDto(String productName, Double productPrice, int quantity) {
        this.productName = productName;
        this.productPrice=productPrice;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
