package com.ra.shop.repository;

import com.ra.shop.model.entity.Order;
import com.ra.shop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}