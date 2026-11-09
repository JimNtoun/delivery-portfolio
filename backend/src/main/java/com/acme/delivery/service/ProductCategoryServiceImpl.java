package com.acme.delivery.service;

import com.acme.delivery.domain.ProductCategory;
import com.acme.delivery.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl extends BaseServiceImpl<ProductCategory> implements ProductCategoryService{
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public JpaRepository<ProductCategory, Long> getRepository() {
        return productCategoryRepository;
    }

    @Override
    public ProductCategory findByDescription(final String description) {
        return productCategoryRepository.findByDescription(description);
    }
}
