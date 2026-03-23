package com.pet.expensetracker.service;

import com.pet.expensetracker.entity.Category;
import com.pet.expensetracker.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByCategoryName(name)
                .orElseThrow(() -> new RuntimeException("Category not found: " + name));
    }
}
