package com.ra.shop.service;

import com.ra.shop.model.dto.CartRequest;
import com.ra.shop.model.dto.ShoppingCartDTO;
import java.util.List;

public interface CartService {
    List<ShoppingCartDTO> getUserCart(Long userId);

    ShoppingCartDTO addToCart(Long userId, CartRequest cartRequest);

    ShoppingCartDTO updateCartQuantity(Long userId, Long productId, Integer quantity);

    void removeFromCart(Long userId, Long productId);

    void clearCart(Long userId);
}
