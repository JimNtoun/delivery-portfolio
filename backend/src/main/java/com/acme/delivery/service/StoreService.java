package com.acme.delivery.service;

import com.acme.delivery.domain.Product;
import com.acme.delivery.domain.ProductCategory;
import com.acme.delivery.domain.Store;
import com.acme.delivery.domain.StoreCategory;
import com.acme.delivery.transfer.KeyValue;
import com.acme.delivery.transfer.KeyValues;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface StoreService extends BaseService<Store>{
    List<Store> findByStoreCategory(Long storeCategory_id);
    Store findByName(String name);
    Store initiateStore(String name, StoreCategory storeCategory);
    Store getLazy(Long id);
    void addProduct(Store store, String serial, String productName, BigDecimal price, ProductCategory productCategory);
    Product findProductById(Long id);
    List<KeyValue<String,String>>  findStoresMostFamous();
    List<KeyValue<String, String>> findStoresMostFamousPerCategory(Long id);
    List<KeyValues<String, String, String>> findTop10Products();

}
