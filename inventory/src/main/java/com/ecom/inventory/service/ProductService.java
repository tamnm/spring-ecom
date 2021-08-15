package com.ecom.inventory.service;

import com.ecom.inventory.entity.Product;

import java.util.Optional;

public interface ProductService {
    Product save(Product product);
    Optional<Product> findById(long productId);
}
