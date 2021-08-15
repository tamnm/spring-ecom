package com.ecom.inventory.dto;


import com.ecom.inventory.entity.Product;
import com.ecom.models.ProductDto;
import com.ecom.models.ProductSubmitDto;

public class ProductMapper {
    private ProductMapper() {

    }

    public static Product fromDto(ProductSubmitDto dto) {
        var product = new Product();
        product.setSku(dto.getSku());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setThumbUrl(dto.getThumbUrl());
        return product;
    }

    public static ProductDto toDto(Product product) {
        var dto = new ProductDto();
        dto.setId(product.getId());
        dto.setSku(product.getSku());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setThumbUrl(product.getThumbUrl());

        return dto;
    }
}
