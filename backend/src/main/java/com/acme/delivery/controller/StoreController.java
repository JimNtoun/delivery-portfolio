package com.acme.delivery.controller;

import com.acme.delivery.domain.Product;
import com.acme.delivery.domain.Store;
import com.acme.delivery.domain.StoreCategory;
import com.acme.delivery.service.BaseService;
import com.acme.delivery.service.StoreService;
import com.acme.delivery.transfer.ApiResponse;
import com.acme.delivery.transfer.KeyValue;
import com.acme.delivery.transfer.KeyValues;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequiredArgsConstructor
@RequestMapping("stores")
@CrossOrigin("*")
public class StoreController extends  BaseController<Store> {
    private final StoreService storeService;
    @Override
    protected BaseService<Store> getBaseService(){
        return storeService;
    }


    @GetMapping(params = "name")
    public ResponseEntity<ApiResponse<Store>> findByName(@RequestParam String name){
        final Store byName = storeService.findByName(name);

        if(byName == null){
            throw new NoSuchElementException("Store not found");
        }
        return ResponseEntity.ok(ApiResponse.<Store>builder().data(byName).build());
    }

    @GetMapping(params = "storeCategory_id")
    public ResponseEntity<ApiResponse<List<Store>>> findByCategory(@RequestParam Long storeCategory_id){
        final List<Store> byCategory = storeService.findByStoreCategory(storeCategory_id);

        if(byCategory == null){
            throw new NoSuchElementException("Store not found");
        }
        return ResponseEntity.ok(ApiResponse.<List<Store>>builder().data(byCategory).build());
    }

    @GetMapping(params = "storeId")
    public ResponseEntity<ApiResponse<Store>> getByStoreId(@RequestParam Long storeId) {
        final Store byStoreId = storeService.getLazy(storeId);
        if(byStoreId == null){
            throw new NoSuchElementException("Product not found");
        }
        return ResponseEntity.ok(ApiResponse.<Store>builder().data(byStoreId).build());
    }

    @GetMapping("reportFamousStores")
    public ResponseEntity<ApiResponse<List<KeyValue<String,String>> >> reportFamousStores() {
        return ResponseEntity.ok(ApiResponse.<List<KeyValue<String,String>> >builder().data(storeService.findStoresMostFamous()).build());
    }

    @GetMapping("reportFamousStoresPerCategory/{id}")
    public ResponseEntity<ApiResponse<List<KeyValue<String, String>>>> reportFamousStoresPerCategory(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<List<KeyValue<String, String>>>builder().data(storeService.findStoresMostFamousPerCategory(id)).build());
    }

    @GetMapping("reportTop10Products")
    public ResponseEntity<ApiResponse<List<KeyValues<String, String, String>>>> reportTop10Products() {
        return ResponseEntity.ok(ApiResponse.<List<KeyValues<String, String, String>>>builder().data(storeService.findTop10Products()).build());
    }
}
