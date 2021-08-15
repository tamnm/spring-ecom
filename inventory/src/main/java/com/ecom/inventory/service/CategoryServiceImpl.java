package com.ecom.inventory.service;

import com.ecom.inventory.entity.Category;
import com.ecom.inventory.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Stream<Category> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false);
    }

    @Override
    public Category save(Category category) {
        return repository.save(category);
    }

    @Override
    public Optional<Category> remove(long categoryId) {
        var cat = findById(categoryId);

        cat.ifPresent(repository::delete);

        return cat;
    }

    @Override
    public Optional<Category> findById(long categoryId) {
        return repository.findById(categoryId);
    }

    @Override
    public Stream<Category> findByName(String name) {

        return StreamSupport.stream(repository.findByName(name).spliterator(), false);
    }
}
