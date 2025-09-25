package com.ra.shop.service;

import com.ra.shop.model.dto.ProductDTO;
import com.ra.shop.model.dto.ProductRequest;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    ProductDTO createProduct(ProductRequest productRequest);

    ProductDTO updateProduct(Long id, ProductRequest productRequest);

    ProductDTO changeProductStatus(Long id);
}
