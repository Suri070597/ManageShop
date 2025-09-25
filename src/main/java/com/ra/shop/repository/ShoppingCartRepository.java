package com.ra.shop.repository;

import com.ra.shop.model.entity.Product;
import com.ra.shop.model.entity.ShoppingCart;
import com.ra.shop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findByUser(User user);

    Optional<ShoppingCart> findByUserAndProduct(User user, Product product);

    void deleteByUser(User user);
}