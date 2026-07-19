package com.ayushman.Ecommerce.DTO;

import java.util.List;

public class PaymentUpdateDTO {
    private String orderId;
    private String paymentId;
    private Long amount;
    private String email;
    private List<OrderItemRequest> items;

    public PaymentUpdateDTO() {}

    public PaymentUpdateDTO(String orderId, String paymentId, Long amount, String email, List<OrderItemRequest> items) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.email = email;
        this.items = items;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

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
