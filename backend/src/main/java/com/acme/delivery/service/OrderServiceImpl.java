package com.acme.delivery.service;

import com.acme.delivery.domain.*;
import com.acme.delivery.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public JpaRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    @Override
    public Order initiateOrder(Account account, Store store) {
        return Order.builder().account(account).store(store).orderItems(new HashSet<>()).build();
    }

    @Override
    public void addItem(Order order, Product product, int quantity) {
        if (checkNullability(order, product)) {
        }

      //  if(checkStore(order, product)) {

            boolean increasedQuantity = false;

            for (OrderItem oi : order.getOrderItems()) {
                if (oi.getProduct().getSerial().equals(product.getSerial())) {
                    oi.setQuantity(oi.getQuantity() + quantity);
                    increasedQuantity = true;
                    break;
                }
            }

            if (!increasedQuantity) {
                order.getOrderItems().add(newOrderItem(order, product, quantity));
            }

            logger.debug("Product[{}] added to Order[{}], quantity:[{}]", product, order, quantity);


        if(!checkStore(order, product)) {
            logger.warn("Unable to order from another store");
            order.getOrderItems().removeAll(order.getOrderItems());
            logger.debug("Items has removed from order {}",order);
            logger.debug("Adding new item on the order."); //AOP!!!!!!
            order.getOrderItems().add(newOrderItem(order,product,quantity));
        }


    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Order checkout(Order order) {
        if (!validate(order)) {
            logger.warn("Order should have account, order items, and payment type defined before being able to " +
                    "checkout the order.");
            return null;
        }

      //  order.setAccount(order.getAccount());
       // order.setStore(order.getStore());
        //order.setOrderItems(order.getOrderItems());
      //  order.setPaymentMethod(paymentMethod);
        order.setSubmitDate(new Date());
        order.setCost(totalCost(order));

        return create(order);
    }

    @Override
    public Order getLazy(Long id) {
        Optional<Order> order = orderRepository.getLazy(id);
        if (order.isPresent()) {
            return order.get();
        }
        throw new NoSuchElementException(String.format("There was no order found matching id %d.", id));
    }

    @Override
    public List<Order> findAllLazy() {
        return orderRepository.findAllLazy();
    }

    @Override
    public List<Order> findBySubmitDate(final Date submitDate) {
        return null;
    }

    @Override
    public List<Order> findOrdersByAccountEmail(String email) {
        return orderRepository.findOrdersByAccountEmail(email);
    }

    private boolean checkNullability(Order order, Product product) {
        if (order == null) {
            logger.warn("Order is null.");
            return true;
        }
        if (product == null) {
            logger.warn("Product is null.");
            return true;
        }
        return false;
    }

    private boolean validate(Order order) {
        return order != null && !order.getOrderItems().isEmpty() && order.getAccount() != null;
    }

    private OrderItem newOrderItem(Order order, Product product, int quantity) {
        return OrderItem.builder().product(product).order(order).quantity(quantity).price(product.getPrice()).build();
    }

    private BigDecimal totalCost(Order order) {

        BigDecimal finalCost = order.getOrderItems().stream()
                .map(oi -> oi.getPrice().multiply(BigDecimal.valueOf(oi.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return finalCost;
    }


    private boolean checkStore(Order order, Product product) {
        if(order.getOrderItems().iterator().next().getProduct().getStore().getId().equals(product.getStore().getId())){
            return true;
        }
        logger.warn("You can't add item from another store");
        return false;
    }

    @Override
    public Order insertPaymentMethod(Order order, PaymentMethod paymentMethod) {
        order.setPaymentMethod(paymentMethod);
        return order;
    }
}
