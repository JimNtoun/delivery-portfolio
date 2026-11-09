package com.acme.delivery.controller;

import com.acme.delivery.base.BaseComponent;
import com.acme.delivery.domain.BaseModel;
import com.acme.delivery.service.BaseService;
import com.acme.delivery.transfer.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin
public abstract class BaseController<T extends BaseModel> extends BaseComponent {
    protected abstract BaseService<T> getBaseService();


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<T>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(ApiResponse.<T>builder().data(getBaseService().get(id)).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<T>>> findAll() {
        List<T> all = getBaseService().findAll();
        return ResponseEntity.ok(ApiResponse.<List<T>>builder()
                        .data(all)
                        .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<T>> create(@Valid @RequestBody final T entity) {
        return new ResponseEntity<>(ApiResponse
                .<T>builder()
                .data(getBaseService().create(entity))
                .build(),
                HttpStatus.CREATED);
    }

    @PostMapping( headers = "content=list" )
    public  ResponseEntity<ApiResponse<List<T>>> createAll(@RequestBody List<T> entities){
        return new ResponseEntity<>(ApiResponse.<List<T>>builder().data(getBaseService().createAll(entities)).build(), HttpStatus.CREATED);
    }


    @PutMapping
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void update(@Valid @RequestBody final T entity) {
        getBaseService().update(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") final Long id) {
        getBaseService().deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @RequestBody final T entity) {
        if (getBaseService().exists(entity)) {
            getBaseService().delete(entity);
        }
    }
}
