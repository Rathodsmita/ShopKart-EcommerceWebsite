package com.shopkart.service;

import com.shopkart.dto.ProductRequest;
import com.shopkart.dto.ProductResponse;
import com.shopkart.exception.BadRequestException;
import com.shopkart.exception.ResourceNotFoundException;
import com.shopkart.model.Category;
import com.shopkart.model.Product;
import com.shopkart.repository.CategoryRepository;
import com.shopkart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ProductResponse> getProducts(String category, String search) {
        List<Product> products;

        if (StringUtils.hasText(search)) {
            products = productRepository.findByTitleContainingIgnoreCaseAndActiveTrue(search.trim());
        } else if (StringUtils.hasText(category) && !"all".equalsIgnoreCase(category)) {
            products = productRepository.findByCategory_SlugAndActiveTrue(category.trim());
        } else {
            products = productRepository.findByActiveTrue();
        }

        return products.stream().map(ProductResponse::from).toList();
    }

    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return ProductResponse.from(product);
    }

    public ProductResponse create(ProductRequest request) {
        Category category = categoryRepository.findBySlug(request.getCategorySlug())
                .orElseThrow(() -> new BadRequestException("Unknown category: " + request.getCategorySlug()));

        Product product = Product.builder()
                .title(request.getTitle())
                .category(category)
                .price(request.getPrice())
                .mrp(request.getMrp())
                .imageUrl(request.getImageUrl())
                .badge(request.getBadge())
                .stock(request.getStock() != null ? request.getStock() : 100)
                .active(true)
                .build();

        return ProductResponse.from(productRepository.save(product));
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));

        Category category = categoryRepository.findBySlug(request.getCategorySlug())
                .orElseThrow(() -> new BadRequestException("Unknown category: " + request.getCategorySlug()));

        product.setTitle(request.getTitle());
        product.setCategory(category);
        product.setPrice(request.getPrice());
        product.setMrp(request.getMrp());
        product.setImageUrl(request.getImageUrl());
        product.setBadge(request.getBadge());
        if (request.getStock() != null) {
            product.setStock(request.getStock());
        }

        return ProductResponse.from(productRepository.save(product));
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        // Soft delete so past order line items still resolve correctly.
        product.setActive(false);
        productRepository.save(product);
    }
}
