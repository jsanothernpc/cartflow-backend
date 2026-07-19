package com.ayushman.EccomPayment.Controller;

import com.ayushman.EccomPayment.DTO.OrderRequest;
import com.ayushman.EccomPayment.DTO.PaymentResponse;
import com.ayushman.EccomPayment.DTO.PaymentUpdateDTO;
import com.ayushman.EccomPayment.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public PaymentResponse createOrder(@RequestBody OrderRequest req) throws Exception {
        return paymentService.createOrder(req);
    }

    @PostMapping("/verify")
    public void verifyPayment(@RequestBody PaymentUpdateDTO dto) throws Exception {

        paymentService.verifyPayment(
                dto.getOrderId(),
                dto.getPaymentId(),
                dto.getRazorpaySignature(),
                dto.getEmail()
        );
    }
}