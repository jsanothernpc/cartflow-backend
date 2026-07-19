package com.ayushman.Ecommerce.LLMTools;

import com.ayushman.Ecommerce.Entity.Orders;
import com.ayushman.Ecommerce.repo.OrderRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderTool {

    private final OrderRepository orderRepository;

    public OrderTool(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Tool(description = "Get latest order from ecommerce database")
    public String getLatestOrder() {

        Orders order = orderRepository.findTopByOrderByIdDesc();

        if(order == null) {
            return "No orders found";
        }

        return formatOrder(order);
    }


    @Tool(description = "Find order by razorpay order id")
    public String getOrderByRazorpayId(String razorpayOrderId) {

        Orders order =
                orderRepository.findByRazorpayOrderId(razorpayOrderId);

        if(order == null) {
            return "Order not found";
        }

        return formatOrder(order);
    }


    @Tool(description = "Get all orders of a customer using email")
    public String getOrdersByEmail(String email) {

        List<Orders> orders =
                orderRepository.findByEmail(email);

        if(orders.isEmpty()) {
            return "No orders found for email : " + email;
        }

        StringBuilder sb = new StringBuilder();

        for(Orders order : orders) {

            sb.append(formatOrder(order))
                    .append("\n--------------------------------\n");
        }

        return sb.toString();
    }


    @Tool(description = "Get orders by status. Supported values SUCCESS PENDING FAILED")
    public String getOrdersByStatus(String status) {

        List<Orders> orders =
                orderRepository.findByStatus(status.toUpperCase());

        if(orders.isEmpty()) {
            return "No orders found with status : " + status;
        }

        StringBuilder sb = new StringBuilder();

        for(Orders order : orders) {

            sb.append(formatOrder(order))
                    .append("\n--------------------------------\n");
        }

        return sb.toString();
    }

    @Tool(description = "Count orders by status")
    public String countOrdersByStatus(String status) {

        long count =
                orderRepository.countByStatus(status.toUpperCase());

        return "Total " + status + " orders = " + count;
    }


    @Tool(description = "Get latest successful payment")
    public String getLatestSuccessfulPayment() {

        Orders order =
                orderRepository.findTopByStatusOrderByIdDesc("SUCCESS");

        if(order == null) {
            return "No successful payment found";
        }

        return """
            Payment Id : %s
            Amount : %.2f
            Email : %s
            Razorpay Order Id : %s
            """
                .formatted(
                        order.getPaymentId(),
                        order.getTotalAmount(),
                        order.getEmail(),
                        order.getRazorpayOrderId()
                );
    }

    @Tool(description = "Get order details by order id")
    public String getOrderById(Long orderId){

        Orders order =
                orderRepository.findById(orderId)
                        .orElse(null);

        if(order == null){
            return "Order not found";
        }

        return formatOrder(order);
    }



    private String formatOrder(Orders order) {

        return """
                Order Id : %d
                Email : %s
                Status : %s
                Amount : %.2f
                Razorpay Order Id : %s
                Payment Id : %s
                Date : %s
                """
                .formatted(
                        order.getId(),
                        order.getEmail(),
                        order.getStatus(),
                        order.getTotalAmount(),
                        order.getRazorpayOrderId(),
                        order.getPaymentId(),
                        order.getOrderDate()
                );
    }
}