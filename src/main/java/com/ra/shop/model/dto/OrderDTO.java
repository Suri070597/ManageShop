package com.ra.shop.model.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private LocalDate orderDate;
    private LocalDate receiveDate;
    private Long userId;
    private String userName;
    private String status;
    private List<OrderDetailDTO> details;
}
