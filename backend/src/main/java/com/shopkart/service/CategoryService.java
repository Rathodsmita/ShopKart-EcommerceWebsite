package com.shopkart.service;

import com.shopkart.dto.CategoryRequest;
import com.shopkart.dto.CategoryResponse;
import com.shopkart.exception.BadRequestException;
import com.shopkart.exception.ResourceNotFoundException;
import com.shopkart.model.Category;
import com.shopkart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::from)
                .toList();
    }

    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsBySlug(request.getSlug())) {
            throw new BadRequestException("A category with this slug already exists");
        }

        Category category = Category.builder()
                .slug(request.getSlug())
                .label(request.getLabel())
                .imageUrl(request.getImageUrl())
                .build();

        return CategoryResponse.from(categoryRepository.save(category));
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));

        category.setSlug(request.getSlug());
        category.setLabel(request.getLabel());
        category.setImageUrl(request.getImageUrl());

        return CategoryResponse.from(categoryRepository.save(category));
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
