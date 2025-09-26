package com.ra.shop.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateDTO {
    private String fullName;
    private String email;
    private String address;
    private String phone;
    private Boolean status;
    private String password;
}
