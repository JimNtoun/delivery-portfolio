package com.acme.delivery.repository;

import com.acme.delivery.domain.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Long> {
    StoreCategory findByCategoryName(String categoryName);
}
