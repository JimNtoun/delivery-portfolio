package com.acme.delivery.service;

import com.acme.delivery.domain.*;
import com.acme.delivery.transfer.KeyValue;

import java.util.Date;
import java.util.List;


public interface OrderService extends BaseService<Order> {
    Order initiateOrder(Account account, Store store);

    void addItem(Order order, Product product, int quantity);

    Order checkout(Order order);

    Order getLazy(Long id);
    List<Order> findAllLazy();


    List<Order> findBySubmitDate(Date submitDate);

    List<Order> findOrdersByAccountEmail(String email);
    Order insertPaymentMethod(Order order, PaymentMethod paymentMethod);
    }
