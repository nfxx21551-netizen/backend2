package com.ultimateflange.service;

import com.ultimateflange.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final String salesEmail = "mushaabkhan894@gmail.com"; // Your sales email

    public void sendOrderConfirmation(Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(order.getEmail());
        message.setSubject("Order Confirmation - " + order.getOrderNumber());

        String emailBody = String.format(
                "Dear %s %s,\n\n" +
                        "Thank you for your order! Your order number is: %s\n\n" +
                        "Order Details:\n" +
                        "Product: %s\n" +
                        "Quantity: %d\n" +
                        "Size: %s\n" +
                        "Material: %s\n\n" +
                        "We will contact you shortly with a quote.\n\n" +
                        "Best regards,\n" +
                        "Ultimate Flange Team",
                order.getFirstName(), order.getLastName(),
                order.getOrderNumber(),
                order.getProduct(), order.getQuantity(),
                order.getSize(), order.getMaterial()
        );

        message.setText(emailBody);
        mailSender.send(message);
    }

    public void sendOrderToSales(Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(salesEmail);
        message.setSubject("NEW ORDER: " + order.getOrderNumber());

        String emailBody = String.format(
                "NEW ORDER RECEIVED\n" +
                        "==================\n\n" +
                        "Order Number: %s\n" +
                        "Date: %s\n\n" +
                        "CUSTOMER DETAILS:\n" +
                        "Name: %s %s\n" +
                        "Email: %s\n" +
                        "Phone: %s\n" +
                        "Company: %s\n" +
                        "Address: %s\n\n" +
                        "ORDER DETAILS:\n" +
                        "Product: %s\n" +
                        "Quantity: %d\n" +
                        "Size: %s\n" +
                        "Material: %s\n" +
                        "Specifications: %s\n\n" +
                        "Please process this order immediately.",
                order.getOrderNumber(), order.getCreatedAt(),
                order.getFirstName(), order.getLastName(),
                order.getEmail(), order.getPhone(),
                order.getCompany(), order.getAddress(),
                order.getProduct(), order.getQuantity(),
                order.getSize(), order.getMaterial(),
                order.getSpecifications()
        );

        message.setText(emailBody);
        mailSender.send(message);
    }
}