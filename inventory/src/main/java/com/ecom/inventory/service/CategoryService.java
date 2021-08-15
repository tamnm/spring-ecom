package com.ecom.inventory.service;

import com.ecom.inventory.entity.Category;

import java.util.Optional;
import java.util.stream.Stream;

public interface CategoryService {
    Stream<Category> findAll();

    Category save(Category category);

    Optional<Category> remove(long categoryId);

    Optional<Category> findById(long categoryId);

    Stream<Category> findByName(String name);
}
