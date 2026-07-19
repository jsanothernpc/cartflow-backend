package com.ayushman.EccomPayment.DTO;


import java.util.List;

public class OrderRequest {
    private String email;
    private List<OrderItemRequest> items;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}