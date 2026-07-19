package com.ayushman.Ecommerce.Service;

import com.ayushman.Ecommerce.DTO.OrderDto;
import com.ayushman.Ecommerce.DTO.OrderItemDto;
import com.ayushman.Ecommerce.DTO.OrderItemRequest;
import com.ayushman.Ecommerce.Entity.OrderItems;
import com.ayushman.Ecommerce.Entity.Orders;
import com.ayushman.Ecommerce.Entity.Product;
import com.ayushman.Ecommerce.Entity.User;
import com.ayushman.Ecommerce.repo.OrderRepository;
import com.ayushman.Ecommerce.repo.ProductRepository;
import com.ayushman.Ecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // Legacy method. Checkout now uses CartService.checkout().
    public OrderDto placeOrder(Long userId, Map<Long, Integer> productQuantities, double totalAmount) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Orders orders = new Orders();
        orders.setUser(user);
        orders.setOrderDate(new Date());
        orders.setStatus("PENDING");
        orders.setTotalAmount(totalAmount / 100);

        List<OrderItems> orderItems = new ArrayList<>();
        List<OrderItemDto> orderItemDtos = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {

            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItems orderItem = new OrderItems();
            orderItem.setOrders(orders);
            orderItem.setProduct(product);
            orderItem.setQuantity(entry.getValue());

            orderItems.add(orderItem);

            orderItemDtos.add(
                    new OrderItemDto(
                            product.getName(),
                            product.getPrice(),
                            entry.getValue()
                    )
            );
        }

        orders.setOrderItems(orderItems);

        Orders savedOrder = orderRepository.save(orders);

        return new OrderDto(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                savedOrder.getOrderDate(),
                orderItemDtos
        );
    }

    public List<OrderDto> getAllOrders() {

        List<Orders> orders = orderRepository.findAllOrdersWithUsers();

        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private OrderDto convertToDto(Orders orders) {

        List<OrderItemDto> orderItems = orders.getOrderItems()
                .stream()
                .map(item -> new OrderItemDto(
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());

        return new OrderDto(
                orders.getId(),
                orders.getTotalAmount(),
                orders.getStatus(),
                orders.getOrderDate(),
                orders.getUser() != null ? orders.getUser().getName() : "Unknown",
                orders.getUser() != null ? orders.getUser().getEmail() : orders.getEmail(),
                orderItems
        );
    }

    public List<OrderDto> getOrderByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Orders> ordersList = orderRepository.findByUser(user);

        return ordersList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void createPendingOrder(String razorpayOrderId,
                                   Long amount,
                                   String email,
                                   List<OrderItemRequest> items) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Orders order = new Orders();
        order.setUser(user);
        order.setEmail(email);
        order.setRazorpayOrderId(razorpayOrderId);
        order.setTotalAmount(amount / 100);
        order.setStatus("PENDING");
        order.setOrderDate(new Date());

        List<OrderItems> orderItemsList = new ArrayList<>();

        for (OrderItemRequest item : items) {

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItems orderItem = new OrderItems();
            orderItem.setOrders(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());

            orderItemsList.add(orderItem);
        }

        order.setOrderItems(orderItemsList);

        orderRepository.save(order);
    }

    public void updatePaymentSuccess(String razorpayOrderId, String paymentId) {

        Orders order = orderRepository.findByRazorpayOrderId(razorpayOrderId);

        if (order == null) {
            throw new RuntimeException("Order not found!");
        }

        order.setPaymentId(paymentId);
        order.setStatus("SUCCESS");

        orderRepository.save(order);
    }
}