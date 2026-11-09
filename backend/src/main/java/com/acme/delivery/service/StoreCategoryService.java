package com.acme.delivery.service;

import com.acme.delivery.domain.StoreCategory;

public interface StoreCategoryService extends BaseService<StoreCategory> {
    StoreCategory findByCategoryName(String categoryName);
}
