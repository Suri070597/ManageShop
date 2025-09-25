package com.ra.shop.model.dto;

import lombok.*;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private String address;
    private String phone;
    private Boolean status;
    private Set<String> roles;
}
