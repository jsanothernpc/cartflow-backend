package com.ayushman.Ecommerce.DTO;

import java.util.Date;
import java.util.List;

public class OrderDto {

    private Long id;



    private String userName;


    private double totalAmount;

    private String status;

    private Date orderDate;

    private String Email;

    private List<OrderItemDto> orderItemDtos;

    public OrderDto(Long id, String userName, double totalAmount, String status, Date orderDate, String email, List<OrderItemDto> orderItemDtos) {
        this.id = id;
        this.userName = userName;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        Email = email;
        this.orderItemDtos = orderItemDtos;
    }

    public OrderDto(Long id,  double totalAmount, String status, Date orderDate,  List<OrderItemDto> orderItemDtos) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.orderItemDtos = orderItemDtos;
    }

    public OrderDto(Long id, double totalAmount, String status, Date orderDate, String userName, String email, List<OrderItemDto> orderItems) {
        this.id=id;
        this.totalAmount=totalAmount;
        this.status=status;
        this.orderDate=orderDate;
        this.userName=userName;
        this.Email=email;
        this.orderItemDtos=orderItems;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public List<OrderItemDto> getOrderItemDtos() {
        return orderItemDtos;
    }

    public void setOrderItemDtos(List<OrderItemDto> orderItemDtos) {
        this.orderItemDtos = orderItemDtos;
    }
}
