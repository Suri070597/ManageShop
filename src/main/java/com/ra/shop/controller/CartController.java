package com.ra.shop.controller;

import com.ra.shop.model.dto.CartRequest;
import com.ra.shop.model.dto.MessageResponse;
import com.ra.shop.model.dto.ShoppingCartDTO;
import com.ra.shop.security.UserPrinciple;
import com.ra.shop.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<List<ShoppingCartDTO>> getUserCart(Authentication authentication) {
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        List<ShoppingCartDTO> cart = cartService.getUserCart(userPrincipal.getId());
        return ResponseEntity.ok(cart);
    }

    @PostMapping
    public ResponseEntity<?> addToCart(@Valid @RequestBody CartRequest cartRequest,
            Authentication authentication) {
        try {
            UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
            ShoppingCartDTO cartItem = cartService.addToCart(userPrincipal.getId(), cartRequest);
            return ResponseEntity.ok(cartItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<?> updateCartQuantity(@PathVariable Long productId,
            @RequestParam Integer quantity,
            Authentication authentication) {
        try {
            UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
            ShoppingCartDTO cartItem = cartService.updateCartQuantity(userPrincipal.getId(), productId, quantity);
            return ResponseEntity.ok(cartItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long productId,
            Authentication authentication) {
        try {
            UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
            cartService.removeFromCart(userPrincipal.getId(), productId);
            return ResponseEntity.ok(new MessageResponse("Product removed from cart successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> clearCart(Authentication authentication) {
        try {
            UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
            cartService.clearCart(userPrincipal.getId());
            return ResponseEntity.ok(new MessageResponse("Cart cleared successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: " + e.getMessage()));
        }
    }
}
