package com.acme.delivery.service;

import com.acme.delivery.domain.*;
import com.acme.delivery.repository.StoreRepository;
import com.acme.delivery.transfer.KeyValue;
import com.acme.delivery.transfer.KeyValues;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl extends BaseServiceImpl<Store> implements StoreService {
    private final StoreRepository storeRepository;

    @Override
    public JpaRepository<Store, Long> getRepository() {
        return storeRepository;
    }

    @Override
    public List<Store> findByStoreCategory(Long storeCategory_id) {
        return storeRepository.findByStoreCategory_Id(storeCategory_id);
    }

    public Store findByName(String name) {
        return storeRepository.findByName(name);
    }

    @Override
    public Store initiateStore(String name, StoreCategory storeCategory) {
        return Store.builder().name(name).storeCategory(storeCategory).products(new HashSet<>()).build();
    }

    @Override
    public Store getLazy(Long id) {
        Optional<Store> store = storeRepository.getLazy(id);
        if (store.isPresent()) {
            return store.get();
        }
        throw new NoSuchElementException(String.format("There was no store found matching id %d.", id));    }

    @Override
    public void addProduct(Store store, String serial, String productName, BigDecimal price, ProductCategory productCategory) {
        store.getProducts().add(createProduct(store, serial, productName, price, productCategory));
    }

    private Product createProduct(Store store, String serial, String productName, BigDecimal price, ProductCategory productCategory) {
        return Product.builder().store(store).serial(serial).productName(productName).price(price).productCategory(productCategory).build();
    }

    @Override
    public Product findProductById(Long id) {
        return storeRepository.findProductById(id);
    }

    @Override
    public List<KeyValue<String,String>>  findStoresMostFamous() {
        return storeRepository.findStoresMostFamous();
    }

    @Override
    public List<KeyValue<String, String>> findStoresMostFamousPerCategory(Long id) {
        return storeRepository.findStoresMostFamousPerCategory(id);
    }

    @Override
    public List<KeyValues<String, String, String>> findTop10Products() {
        return storeRepository.findTop10Products();
    }
}
