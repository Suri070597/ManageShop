package com.ra.shop.service;

import com.ra.shop.model.dto.UserDTO;
import com.ra.shop.model.dto.UserCreateDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO changeUserStatus(Long userId);

    UserDTO addRoleToUser(Long userId, Long roleId);

    UserDTO removeRoleFromUser(Long userId, Long roleId);

    UserDTO createUser(UserCreateDTO userCreateDTO);

    void deleteUser(Long userId);
}
