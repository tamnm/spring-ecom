package com.ecom.inventory.controller;

import com.ecom.inventory.dto.ProductMapper;
import com.ecom.inventory.service.ProductService;
import com.ecom.models.ProductDto;
import com.ecom.models.ProductSubmitDto;
import com.ecom.restlib.exception.DataNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ProductDto create(@RequestBody ProductSubmitDto productDto) {
        var product = productService.save(ProductMapper.fromDto(productDto));

        return ProductMapper.toDto(product);
    }

    @PutMapping("{id}")
    public ProductDto update(@PathVariable("id") @NotNull Long id, @RequestBody ProductSubmitDto productSubmitDto) {
        var entity = productService.findById(id);

        if (entity.isPresent()) {
            var product = ProductMapper.fromDto(productSubmitDto);
            product.setId(id);

            return ProductMapper.toDto(productService.save(product));
        } else {
            throw new DataNotFoundException("Product was not found for update");
        }
    }

    @GetMapping("{id}")
    public Optional<ProductDto> get(@PathVariable("id") @NotNull Long id) {
        var opt = productService.findById(id);

        return opt.map(ProductMapper::toDto);
    }
}
