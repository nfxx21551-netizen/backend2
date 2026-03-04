package com.ultimateflange.service;

import com.ultimateflange.dto.OrderRequest;
import com.ultimateflange.model.Order;
import com.ultimateflange.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailService emailService;

    public Order createOrder(OrderRequest request) {
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setProduct(request.getProduct());
        order.setQuantity(request.getQuantity());
        order.setSize(request.getSize());
        order.setMaterial(request.getMaterial());
        order.setSpecifications(request.getSpecifications());
        order.setFirstName(request.getFirstName());
        order.setLastName(request.getLastName());
        order.setEmail(request.getEmail());
        order.setPhone(request.getPhone());
        order.setCompany(request.getCompany());
        order.setAddress(request.getAddress());
        order.setUserId(request.getUserId());
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        // Send emails
        try {
            emailService.sendOrderConfirmation(savedOrder);
            emailService.sendOrderToSales(savedOrder);
        } catch (Exception e) {
            // Log error but don't fail the order
            System.err.println("Email sending failed: " + e.getMessage());
        }

        return savedOrder;
    }

    private String generateOrderNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Random random = new Random();
        int randomNum = 1000 + random.nextInt(9000);
        return "ORD-" + timestamp + "-" + randomNum;
    }
}