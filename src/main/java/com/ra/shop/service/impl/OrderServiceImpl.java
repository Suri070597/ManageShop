package com.ra.shop.service.impl;

import com.ra.shop.model.dto.OrderDTO;
import com.ra.shop.model.dto.OrderDetailDTO;
import com.ra.shop.model.entity.Order;
import com.ra.shop.model.entity.OrderDetail;
import com.ra.shop.model.entity.ShoppingCart;
import com.ra.shop.model.entity.User;
import com.ra.shop.repository.OrderDetailRepository;
import com.ra.shop.repository.OrdersRepository;
import com.ra.shop.repository.ShoppingCartRepository;
import com.ra.shop.repository.UserRepository;
import com.ra.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Override
    public List<OrderDTO> getUserOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO createOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus("CREATED");
        order.setOrderDetails(new ArrayList<>());

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    @Override
    public OrderDTO updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        if ("DELIVERED".equals(status)) {
            order.setReceiveDate(LocalDate.now());
        }

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    @Override
    @Transactional
    public OrderDTO checkout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ShoppingCart> cartItems = cartRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus("PROCESSING");

        Order savedOrder = orderRepository.save(order);

        // Create order details from cart items
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (ShoppingCart cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(savedOrder);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setUnitPrice(cartItem.getProduct().getPrice());

            orderDetails.add(orderDetail);
        }

        orderDetailRepository.saveAll(orderDetails);
        savedOrder.setOrderDetails(orderDetails);

        // Clear cart after checkout
        cartRepository.deleteByUser(user);

        return convertToDTO(savedOrder);
    }

    @Override
    public List<OrderDetailDTO> getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return order.getOrderDetails().stream()
                .map(this::convertToDetailDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .receiveDate(order.getReceiveDate())
                .status(order.getStatus())
                .userId(order.getUser().getId())
                .userName(order.getUser().getFullName())
                .build();
    }

    private OrderDetailDTO convertToDetailDTO(OrderDetail orderDetail) {
        return OrderDetailDTO.builder()
                .id(orderDetail.getId())
                .quantity(orderDetail.getQuantity())
                .unitPrice(orderDetail.getUnitPrice())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .productName(orderDetail.getProduct().getName())
                .build();
    }
}
