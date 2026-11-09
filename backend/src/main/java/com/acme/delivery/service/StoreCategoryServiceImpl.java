package com.acme.delivery.service;

import com.acme.delivery.domain.StoreCategory;
import com.acme.delivery.repository.StoreCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreCategoryServiceImpl extends BaseServiceImpl<StoreCategory> implements StoreCategoryService {
    private final StoreCategoryRepository storeCategoryRepository;
    @Override
    public JpaRepository<StoreCategory, Long> getRepository() {
        return storeCategoryRepository;
    }

    @Override
    public StoreCategory findByCategoryName(String categoryName) {
        return storeCategoryRepository.findByCategoryName(categoryName);
    }
}
