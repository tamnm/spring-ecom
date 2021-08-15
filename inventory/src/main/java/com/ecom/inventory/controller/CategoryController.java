package com.ecom.inventory.controller;


import com.ecom.inventory.dto.CategoryMapper;
import com.ecom.inventory.dto.SampleDto;
import com.ecom.inventory.entity.Category;
import com.ecom.inventory.service.CategoryService;
import com.ecom.models.CategoryDto;
import com.ecom.models.CategorySubmitDto;
import com.ecom.restlib.exception.DataNotFoundException;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("api/v1/category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/sample")
    public SampleDto postSample(@RequestBody SampleDto sample) {
        return sample;
    }


    @PostMapping()
    public CategoryDto create(@RequestBody CategorySubmitDto createCategoryDto) {
        var entity = new Category();
        entity.setName(createCategoryDto.getName());

        return CategoryMapper.toDto(categoryService.save(entity));
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable("id") @NotNull Long id,
                                              @RequestBody CategorySubmitDto updateCategoryDto) {

        var opt = categoryService.findById(id);
        if (opt.isPresent()) {
            var entity = opt.get();
            entity.setName(updateCategoryDto.getName());
            return ResponseEntity.ok(CategoryMapper.toDto(categoryService.save(entity)));
        } else {
            throw new DataNotFoundException("Product was not found for update");
        }
    }

    @GetMapping()
    public Collection<CategoryDto> getAll() {
        return categoryService.findAll().map(CategoryMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public Optional<CategoryDto> findById(@PathVariable("id") @NotNull Long id) {
        var entity = categoryService.findById(id);
        return entity.map(CategoryMapper::toDto);
    }

    @GetMapping("find")
    public Collection<CategoryDto> findByName(
            @RequestParam
            @ApiParam(name = "keyword", value = "enter a keyword to find category", required = true, example = "phone")
            @Size(min = 3, max = 128) String keyword) {
        return categoryService.findByName(keyword).map(CategoryMapper::toDto).collect(Collectors.toList());
    }
}
