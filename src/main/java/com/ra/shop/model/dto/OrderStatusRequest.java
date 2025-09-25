package com.ra.shop.model.dto;

import lombok.*;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusRequest {
    @NotBlank(message = "Status is required")
    private String status;
}
