package com.ayushman.EccomPayment.DTO;

import java.util.List;

public class PaymentUpdateDTO {

    private String orderId;
    private String paymentId;
    private String razorpaySignature;
    private Long amount;
    private String email;
    private List<OrderItemRequest> items;

    public PaymentUpdateDTO() {
    }

    public PaymentUpdateDTO(String orderId,
                            String paymentId,
                            String razorpaySignature,
                            Long amount,
                            String email,
                            List<OrderItemRequest> items) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.razorpaySignature = razorpaySignature;
        this.amount = amount;
        this.email = email;
        this.items = items;
    }

    public PaymentUpdateDTO(String orderId,
                            String paymentId,
                            String razorpaySignature,
                            Long amount,
                            String email) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.razorpaySignature = razorpaySignature;
        this.amount = amount;
        this.email = email;
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

    public String getRazorpaySignature() {
        return razorpaySignature;
    }

    public void setRazorpaySignature(String razorpaySignature) {
        this.razorpaySignature = razorpaySignature;
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