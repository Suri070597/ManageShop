package com.ra.shop.service;

import com.ra.shop.model.dto.ProductDTO;
import com.ra.shop.model.dto.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    ProductDTO createProduct(ProductRequest productRequest);

    ProductDTO updateProduct(Long id, ProductRequest productRequest);

    ProductDTO changeProductStatus(Long id);

    Page<ProductDTO> searchProducts(String name, Long categoryId, Double minPrice, Double maxPrice, Boolean status, Pageable pageable);
}
