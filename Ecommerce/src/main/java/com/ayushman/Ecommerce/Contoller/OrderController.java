package com.ayushman.Ecommerce.Contoller;


import com.ayushman.Ecommerce.DTO.OrderDto;
import com.ayushman.Ecommerce.DTO.PaymentUpdateDTO;
import com.ayushman.Ecommerce.Entity.OrderRequest;
import com.ayushman.Ecommerce.Entity.Orders;
import com.ayushman.Ecommerce.Service.OrderService;
import com.ayushman.Ecommerce.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

      @PostMapping("/place/{userId}")
      public OrderDto placeOrder(@PathVariable Long userId, @RequestBody OrderRequest orderRequest)
      {

          return orderService.placeOrder(userId,orderRequest.getProductQuantities(),orderRequest.getTotalAmount());
      }

      @GetMapping("/all-orders")
      public List<OrderDto> getAllOrders()
      {
          return orderService.getAllOrders();
      }

       @GetMapping("/user/{userId}")
      public List<OrderDto> getAllOrdersByUser(@PathVariable Long userId)
      {
          return orderService.getOrderByUser(userId);
      }

    @PostMapping("/pending")
    public void createPendingOrder(@RequestBody PaymentUpdateDTO dto) {
        orderService.createPendingOrder(dto.getOrderId(), dto.getAmount(), dto.getEmail(),dto.getItems());
    }

    @PostMapping("/success")
    public void updatePayment(@RequestBody PaymentUpdateDTO dto) {
        orderService.updatePaymentSuccess(dto.getOrderId(), dto.getPaymentId());
    }

    @GetMapping("/by-razorpay/{razorId}")
    public Orders getByRazorpay(@PathVariable String razorId){
        return orderRepository.findByRazorpayOrderId(razorId);
    }

}
