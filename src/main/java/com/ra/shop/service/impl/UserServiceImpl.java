package com.ra.shop.service.impl;

import com.ra.shop.model.dto.UserCreateDTO;
import com.ra.shop.model.dto.UserDTO;
import com.ra.shop.model.entity.Role;
import com.ra.shop.model.entity.User;
import com.ra.shop.repository.RoleRepository;
import com.ra.shop.repository.UserRepository;
import com.ra.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO changeUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(!user.isStatus());
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setFullName(userCreateDTO.getFullName());
        user.setEmail(userCreateDTO.getEmail());
        user.setAddress(userCreateDTO.getAddress());
        user.setPhone(userCreateDTO.getPhone());
        user.setStatus(userCreateDTO.getStatus() != null ? userCreateDTO.getStatus() : true);
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));

        Role defaultRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.getRoles().add(defaultRole);

        User saved = userRepository.save(user);
        return convertToDTO(saved);
    }


    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public UserDTO addRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id " + roleId));
        user.getRoles().add(role);
        userRepository.save(user);
        return convertToDTO(user);
    }

    @Override
    public UserDTO removeRoleFromUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id " + roleId));
        user.getRoles().removeIf(r -> r.getId().equals(role.getId()));
        userRepository.save(user);
        return convertToDTO(user);
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .address(user.getAddress())
                .phone(user.getPhone())
                .status(user.isStatus())
                .roles(user.getRoles().stream()
                        .map(role -> role.getRoleName())
                        .collect(Collectors.toSet()))
                .build();
    }
}
