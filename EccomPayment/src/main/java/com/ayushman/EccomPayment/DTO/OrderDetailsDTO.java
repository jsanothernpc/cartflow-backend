package com.ayushman.EccomPayment.DTO;

public class OrderDetailsDTO {
    private Long id;
    private Long totalAmount;
    private String razorpayOrderId;

    public Long getId() { return id; }
    public Long getTotalAmount() { return totalAmount; }
    public String getRazorpayOrderId() { return razorpayOrderId; }

    public void setId(Long id) { this.id = id; }
    public void setTotalAmount(Long totalAmount) { this.totalAmount = totalAmount; }
    public void setRazorpayOrderId(String razorpayOrderId) { this.razorpayOrderId = razorpayOrderId; }
}
