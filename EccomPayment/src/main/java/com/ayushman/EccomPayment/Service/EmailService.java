package com.ayushman.EccomPayment.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service

public class EmailService {

     @Autowired
    private  JavaMailSender mailSender;

    public void sendOrderEmail(String to, Long amount, String paymentId, String orderId) throws MessagingException {

        String itemsHtml = ""; //

        String html =
                "<div style='font-family:Arial;background:#111;color:white;padding:20px;border-radius:10px'>" +
                        "<h2 style='color:#ff6600;text-align:center'>✅ Payment Successful</h2>" +
                        "<p>Hello,</p>" +
                        "<p>Thank you for shopping with <b style='color:#ff6600'>CartFlow</b> ❤️</p>" +
                        "<h3 style='border-bottom:1px solid #ff6600;padding-bottom:5px'>🧾 Order Summary</h3>" +
                        "<p><b>Order ID:</b> " + orderId + "</p>" +
                        "<p><b>Payment ID:</b> " + paymentId + "</p>" +
                        "<p><b>Total Amount:</b> ₹" + amount + "</p>" +
                        "<br>" +
                        "<p style='text-align:center;color:#ff6600'>We hope to see you again! 😊</p>" +
                        "</div>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("🧾 CartFlow Order Confirmation");
        helper.setText(html, true);

        mailSender.send(message);
    }
}
