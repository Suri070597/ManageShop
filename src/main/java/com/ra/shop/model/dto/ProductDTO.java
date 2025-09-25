package com.ra.shop.model.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String productName;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;
    private String categoryName;
    private Boolean status;
}
