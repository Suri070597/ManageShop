package com.ra.shop.service;

import com.ra.shop.model.dto.OrderDTO;
import com.ra.shop.model.dto.OrderDetailDTO;
import java.util.List;

public interface OrderService {
    List<OrderDTO> getUserOrders(Long userId);

    OrderDTO createOrder(Long userId);

    OrderDTO updateOrderStatus(Long orderId, String status);

    OrderDTO checkout(Long userId);

    List<OrderDetailDTO> getOrderDetails(Long orderId);
}
