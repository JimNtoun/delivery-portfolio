package com.acme.delivery.bootstrap;

import com.acme.delivery.base.BaseComponent;
import com.acme.delivery.domain.Account;
import com.acme.delivery.domain.Order;
import com.acme.delivery.domain.PaymentMethod;
import com.acme.delivery.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@org.springframework.core.annotation.Order(2)
public class OrderSampleContentCreator extends BaseComponent implements CommandLineRunner {

    private final AccountService accountService;
    private final StoreService storeService;
    private final OrderService orderService;
    @Override
    public void run(String... args) throws Exception {

        Account firstAccount = accountService.findByEmail("dimitris@gmail.com");
        Order firstOrder = orderService.initiateOrder(firstAccount, storeService.get(1L));
        orderService.addItem(firstOrder, storeService.findProductById(1L),3);
        orderService.insertPaymentMethod(firstOrder,PaymentMethod.WIRE_TRANSFER);
        orderService.checkout(firstOrder);

        Order secondOrder = orderService.initiateOrder(firstAccount, storeService.get(2L));
        orderService.addItem(secondOrder, storeService.findProductById(6L),3);
        orderService.insertPaymentMethod(secondOrder,PaymentMethod.CREDIT_CARD);
        orderService.checkout(secondOrder);

        Order thirdOrder = orderService.initiateOrder(firstAccount, storeService.get(2L));
        orderService.addItem(thirdOrder, storeService.findProductById(5L),3);
        orderService.insertPaymentMethod(thirdOrder,PaymentMethod.CREDIT_CARD);
        orderService.checkout(thirdOrder);

        Account secondAccount = accountService.findByEmail("spys@gmail.com");
        Order fourthOrder = orderService.initiateOrder(secondAccount, storeService.get(1L));
        orderService.addItem(fourthOrder, storeService.findProductById(1L),3);
        orderService.insertPaymentMethod(fourthOrder,PaymentMethod.CASH);
        orderService.checkout(fourthOrder);

        Order fifthOrder = orderService.initiateOrder(secondAccount, storeService.get(4L));
        orderService.addItem(fifthOrder, storeService.findProductById(11L),3);
        orderService.addItem(fifthOrder, storeService.findProductById(12L),3);
        orderService.addItem(fifthOrder, storeService.findProductById(13L),3);
        orderService.insertPaymentMethod(fifthOrder,PaymentMethod.CASH);
        orderService.checkout(fifthOrder);

        Account thirdAccount = accountService.findByEmail("marios@gmail.com");

        Order order6 = orderService.initiateOrder(secondAccount, storeService.get(5L));
        orderService.addItem(order6, storeService.findProductById(18L),1);
        orderService.addItem(order6, storeService.findProductById(16L),2);
        orderService.insertPaymentMethod(order6,PaymentMethod.CASH);
        orderService.checkout(order6);

        Order order7 = orderService.initiateOrder(thirdAccount, storeService.get(13L));
        orderService.addItem(order7, storeService.findProductById(47L),3);
        orderService.addItem(order7, storeService.findProductById(49L),4);
        orderService.insertPaymentMethod(order7,PaymentMethod.WIRE_TRANSFER);
        orderService.checkout(order7);

        Order order8 = orderService.initiateOrder(firstAccount, storeService.get(10L));
        orderService.addItem(order8, storeService.findProductById(36L),5);
        orderService.insertPaymentMethod(order8,PaymentMethod.CREDIT_CARD);
        orderService.checkout(order8);

        Order order9 = orderService.initiateOrder(secondAccount, storeService.get(16L));
        orderService.addItem(order9, storeService.findProductById(60L),3);
        orderService.addItem(order9, storeService.findProductById(61L),2);
        orderService.insertPaymentMethod(order9,PaymentMethod.WIRE_TRANSFER);
        orderService.checkout(order9);

        Order order10 = orderService.initiateOrder(thirdAccount, storeService.get(7L));
        orderService.addItem(order10, storeService.findProductById(23L),3);
        orderService.addItem(order10, storeService.findProductById(24L),2);
        orderService.addItem(order10, storeService.findProductById(25L),3);
        orderService.addItem(order10, storeService.findProductById(26L),2);
        orderService.insertPaymentMethod(order10,PaymentMethod.CASH);
        orderService.checkout(order10);

        Order order11 = orderService.initiateOrder(secondAccount, storeService.get(1L));
        orderService.addItem(order11, storeService.findProductById(1L),3);
        orderService.addItem(order11, storeService.findProductById(3L),4);
        orderService.insertPaymentMethod(order11,PaymentMethod.CASH);
        orderService.checkout(order11);

        Order order12 = orderService.initiateOrder(secondAccount, storeService.get(4L));
        orderService.addItem(order12, storeService.findProductById(11L),3);
        orderService.addItem(order12, storeService.findProductById(13L),2);
        orderService.addItem(order12, storeService.findProductById(14L),3);
        orderService.insertPaymentMethod(order12,PaymentMethod.WIRE_TRANSFER);
        orderService.checkout(order12);
    }
}
