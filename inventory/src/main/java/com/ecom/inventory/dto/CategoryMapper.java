package com.ecom.inventory.dto;

import com.ecom.inventory.entity.Category;
import com.ecom.models.CategoryDto;

public class CategoryMapper {
    private CategoryMapper(){}

    public static Category fromDto(CategoryDto dto){
        var cat = new Category();
        cat.setId(dto.getId());
        cat.setName(dto.getName());

        return cat;
    }

    public static CategoryDto toDto(Category entity){
        var cat = new CategoryDto();
        cat.setId(entity.getId());
        cat.setName(entity.getName());

        return cat;
    }
}
