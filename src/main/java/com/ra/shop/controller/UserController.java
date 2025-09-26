package com.ra.shop.controller;

import com.ra.shop.model.dto.MessageResponse;
import com.ra.shop.model.dto.UserCreateDTO;
import com.ra.shop.model.dto.UserDTO;
import com.ra.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeUserStatus(@PathVariable Long id) {
        try {
            UserDTO user = userService.changeUserStatus(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        try {
            UserDTO created = userService.createUser(userCreateDTO);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @PostMapping("/{userId}/role/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        try {
            UserDTO updated = userService.addRoleToUser(userId, roleId);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}/role/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId) {
        try {
            UserDTO updated = userService.removeRoleFromUser(userId, roleId);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: " + e.getMessage()));
        }
    }
}
