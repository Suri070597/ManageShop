package com.ra.shop.service;

import com.ra.shop.model.dto.UserDTO;
import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO changeUserStatus(Long userId);
}
