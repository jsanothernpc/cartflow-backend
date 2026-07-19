package com.ayushman.EccomPayment.Service;

import com.ayushman.EccomPayment.DTO.*;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import jakarta.mail.MessagingException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmailService emailService;

    @Value("${ecommerce.service.url}")
    private String ecommerceServiceUrl;

    @Value("${razorpay.keyId}")
    private String key;

    @Value("${razorpay.keySecret}")
    private String secret;

    public PaymentResponse createOrder(OrderRequest req) throws Exception {

        RazorpayClient client = new RazorpayClient(key, secret);

        int totalAmount = calculateAmount(req);

        JSONObject options = new JSONObject();
        options.put("amount", totalAmount * 100);
        options.put("currency", "INR");

        Order razorOrder = client.orders.create(options);

        restTemplate.postForObject(
                ecommerceServiceUrl + "/orders/pending",
                new PaymentUpdateDTO(
                        razorOrder.get("id").toString(),
                        null,
                        null,
                        (long) totalAmount * 100,
                        req.getEmail(),
                        req.getItems()
                ),
                Void.class
        );

        PaymentResponse response = new PaymentResponse();
        response.setRazorpayOrderId(razorOrder.get("id").toString());
        response.setKey(key);
        response.setAmount((long) totalAmount * 100);

        return response;
    }

    private int calculateAmount(OrderRequest req) {
        int total = 0;

        for (OrderItemRequest item : req.getItems()) {
            total += item.getQuantity() * item.getPrice();
        }

        return total;
    }

    public void verifyPayment(
            String razorOrderId,
            String paymentId,
            String razorpaySignature,
            String email
    ) throws Exception {

        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", razorOrderId);
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_signature", razorpaySignature);

        boolean valid = Utils.verifyPaymentSignature(options, secret);

        if (!valid) {
            throw new RuntimeException("Invalid Razorpay Signature");
        }

        OrderDetailsDTO savedOrder =
                restTemplate.getForObject(
                        ecommerceServiceUrl + "/orders/by-razorpay/" + razorOrderId,
                        OrderDetailsDTO.class
                );

        restTemplate.postForObject(
                ecommerceServiceUrl + "/orders/success",
                new PaymentUpdateDTO(
                        razorOrderId,
                        paymentId,
                        razorpaySignature,
                        null,
                        email
                ),
                Void.class
        );

        emailService.sendOrderEmail(
                email,
                savedOrder.getTotalAmount(),
                paymentId,
                razorOrderId
        );
    }
}