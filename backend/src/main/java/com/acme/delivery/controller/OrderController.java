package com.acme.delivery.controller;

import com.acme.delivery.domain.Account;
import com.acme.delivery.domain.Order;
import com.acme.delivery.domain.PaymentMethod;
import com.acme.delivery.service.BaseService;
import com.acme.delivery.service.OrderService;
import com.acme.delivery.service.StoreService;
import com.acme.delivery.transfer.ApiResponse;
import com.acme.delivery.transfer.KeyValue;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrderController extends BaseController<Order> {
    private final OrderService orderService;
    private final StoreService storeService;
    @Override
    protected BaseService<Order> getBaseService() {
        return orderService;
    }

/*
@GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> findAll() {
        return new ResponseEntity<>(ApiResponse.<List<Order>>builder().data(orderService.findAllLazy()).build(),
                HttpStatus.OK);
    }

 */

    @GetMapping(value = "retrieveAllOrders/{email}")
    public ResponseEntity<ApiResponse<List<Order>>> retrieveAllOrders(@PathVariable String email) {
        List<Order> byOrders = orderService.findOrdersByAccountEmail(email);
        logger.info(String.valueOf(orderService));
        if (byOrders == null) {
            throw new NoSuchElementException("Cannot find any order");
        }
        return ResponseEntity.ok(ApiResponse.<List<Order>>builder().data(byOrders).build());
    }

    @Override
    @PostMapping("checkout")
    public ResponseEntity<ApiResponse<Order>> create(@RequestBody Order order) {
        logger.info("{}", order);
        order.getOrderItems().forEach(oi -> oi.setOrder(order));
        return ResponseEntity.ok(ApiResponse.<Order>builder().data(
                orderService.checkout(order)
        ).build());
    }
}
