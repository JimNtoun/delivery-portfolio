package com.acme.delivery.service;

import com.acme.delivery.domain.ProductCategory;

public interface ProductCategoryService extends BaseService<ProductCategory> {
    ProductCategory findByDescription(String description);
}
