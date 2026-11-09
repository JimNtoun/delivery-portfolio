package com.acme.delivery.bootstrap;

import com.acme.delivery.base.BaseComponent;
import com.acme.delivery.domain.*;
import com.acme.delivery.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@RequiredArgsConstructor
@org.springframework.core.annotation.Order(1)
public class BasicSamplerContentCreator extends BaseComponent implements CommandLineRunner {
    private final AccountService accountService;
    private final ProductCategoryService productCategoryService;
    private final StoreService storeService;
    private final StoreCategoryService storeCategoryService;

    @Override

    public void run(String... args) throws Exception {
        //@formatter:off

        //Create a new Account

        Account account1 = Account.builder().username("Dimitris").password("1234").email("dimitris@gmail.com").phoneNumber("6998717438")
                .age(24).address("Menedimou").city("Chalkida").build();
        Account account2 = Account.builder().username("Spyros").password("8796").email("spys@gmail.com").phoneNumber("6998743817")
                .age(24).address("Kolokotroni").city("Patras").build();
        Account account3 = Account.builder().username("Marios").password("5432").email("marios@gmail.com").phoneNumber("6998743834")
                .age(24).address("Karaiskaiki").city("Athens").build();

        accountService.createAll(account1, account2, account3);

        logger.info("Does account exist? {}.", (accountService.findByEmail("spys@gmail.com") != null));

        ProductCategory souvlakia = productCategoryService.create(ProductCategory.builder().description("Souvlakia").build());
        logger.info("Created {}.", souvlakia);

        ProductCategory coffee = productCategoryService.create(ProductCategory.builder().description("Coffee").build());
        logger.info("Created {}.", coffee);

        ProductCategory crepes = productCategoryService.create(ProductCategory.builder().description("Crepes").build());
        logger.info("Created {}.", crepes);

        ProductCategory pizza = productCategoryService.create(ProductCategory.builder().description("Pizza").build());
        logger.info("Created {}.", pizza);

        ProductCategory burgers = productCategoryService.create(ProductCategory.builder().description("Burgers").build());
        logger.info("Created {}.", burgers);

        StoreCategory souvlakeri = storeCategoryService.create(StoreCategory.builder().categoryName("Souvlakeri").build());
        logger.info("Created {}.", souvlakeri);

        StoreCategory creperi = storeCategoryService.create(StoreCategory.builder().categoryName("Creperi").build());
        logger.info("Created {}.", creperi);

        StoreCategory cafe = storeCategoryService.create(StoreCategory.builder().categoryName("Cafe").build());
        logger.info("Created {}.", cafe);

        StoreCategory pizzaria = storeCategoryService.create(StoreCategory.builder().categoryName("Pizzaria").build());
        logger.info("Created {}.", pizzaria);

        StoreCategory burgerShop = storeCategoryService.create(StoreCategory.builder().categoryName("Burger Shop").build());
        logger.info("Created {}.", burgerShop);

        Store souvlaki1 = storeService.initiateStore("Souvlaki1", souvlakeri);
        storeService.addProduct(souvlaki1,"GYROS-001", "Gyros 1", BigDecimal.valueOf(3), souvlakia);
        storeService.addProduct(souvlaki1,"GYROS-002", "Gyros 2", BigDecimal.valueOf(3.5), souvlakia);
        storeService.addProduct(souvlaki1,"GYROS-003", "Gyros 3", BigDecimal.valueOf(6), souvlakia);
        storeService.addProduct(souvlaki1,"GYROS-004", "Gyros 4", BigDecimal.valueOf(5), souvlakia);
        storeService.create(souvlaki1);
        logger.info("Created {}.", souvlaki1);

        Store souvlaki2 = storeService.initiateStore("Souvlaki2", souvlakeri);
        storeService.addProduct(souvlaki2,"GYROS-011", "Gyros 1", BigDecimal.valueOf(3.5), souvlakia);
        storeService.addProduct(souvlaki2,"GYROS-012", "Gyros 2", BigDecimal.valueOf(3), souvlakia);
        storeService.addProduct(souvlaki2,"GYROS-013", "Gyros 3", BigDecimal.valueOf(4), souvlakia);
        storeService.create(souvlaki2);
        logger.info("Created {}.", souvlaki2);

        Store souvlaki3 = storeService.initiateStore("Souvlaki3", souvlakeri);
        storeService.addProduct(souvlaki3,"GYROS-021", "Gyros 1", BigDecimal.valueOf(3), souvlakia);
        storeService.addProduct(souvlaki3,"GYROS-022", "Gyros 2", BigDecimal.valueOf(3.8), souvlakia);
        storeService.addProduct(souvlaki3,"GYROS-023", "Gyros 3", BigDecimal.valueOf(4.5), souvlakia);
        storeService.create(souvlaki3);
        logger.info("Created {}.", souvlaki3);

        Store crepe1 = storeService.initiateStore("Crepe1", creperi);
        storeService.addProduct(crepe1,"CREPE-001", "Crepe 1", BigDecimal.valueOf(4), crepes);
        storeService.addProduct(crepe1,"CREPE-002", "Crepe 2", BigDecimal.valueOf(5), crepes);
        storeService.addProduct(crepe1,"CREPE-003", "Crepe 3", BigDecimal.valueOf(5.5), crepes);
        storeService.addProduct(crepe1,"CREPE-004", "Crepe 4", BigDecimal.valueOf(6), crepes);
        storeService.create(crepe1);
        logger.info("Created {}.", crepe1);


        Store crepe2 = storeService.initiateStore("Crepe2", creperi);
        storeService.addProduct(crepe2,"CREPE-011", "Crepe 1", BigDecimal.valueOf(4), crepes);
        storeService.addProduct(crepe2,"CREPE-012", "Crepe 2", BigDecimal.valueOf(4.5), crepes);
        storeService.addProduct(crepe2,"CREPE-013", "Crepe 3", BigDecimal.valueOf(6), crepes);
        storeService.addProduct(crepe2,"CREPE-014", "Crepe 4", BigDecimal.valueOf(7.5), crepes);
        storeService.create(crepe2);
        logger.info("Created {}.", crepe2);

        Store crepe3 = storeService.initiateStore("Crepe3", creperi);
        storeService.addProduct(crepe3,"CREPE-021", "Crepe 1", BigDecimal.valueOf(4), crepes);
        storeService.addProduct(crepe3,"CREPE-022", "Crepe 2", BigDecimal.valueOf(6.5), crepes);
        storeService.addProduct(crepe3,"CREPE-023", "Crepe 3", BigDecimal.valueOf(4.5), crepes);
        storeService.addProduct(crepe3,"CREPE-024", "Crepe 4", BigDecimal.valueOf(5), crepes);
        storeService.create(crepe3);
        logger.info("Created {}.", crepe3);

        Store coffee1 = storeService.initiateStore("Coffee1", cafe);
        storeService.addProduct(coffee1,"COFFEE-001", "Coffee 1", BigDecimal.valueOf(3), coffee);
        storeService.addProduct(coffee1,"COFFEE-002", "Coffee 2", BigDecimal.valueOf(3.5), coffee);
        storeService.addProduct(coffee1,"COFFEE-003", "Coffee 3", BigDecimal.valueOf(3.5), coffee);
        storeService.addProduct(coffee1,"COFFEE-004", "Coffee 4", BigDecimal.valueOf(2.5), coffee);
        storeService.create(coffee1);
        logger.info("Created {}.", coffee1);

        Store coffee2 = storeService.initiateStore("Coffee2", cafe);
        storeService.addProduct(coffee2,"COFFEE-011", "Coffee 1", BigDecimal.valueOf(3), coffee);
        storeService.addProduct(coffee2,"COFFEE-012", "Coffee 2", BigDecimal.valueOf(3), coffee);
        storeService.addProduct(coffee2,"COFFEE-013", "Coffee 3", BigDecimal.valueOf(3.5), coffee);
        storeService.addProduct(coffee2,"COFFEE-014", "Coffee 4", BigDecimal.valueOf(3.5), coffee);
        storeService.create(coffee2);
        logger.info("Created {}.", coffee2);

        Store coffee3 = storeService.initiateStore("Coffee3", cafe);
        storeService.addProduct(coffee3,"COFFEE-021", "Coffee 1", BigDecimal.valueOf(2.5), coffee);
        storeService.addProduct(coffee3,"COFFEE-022", "Coffee 2", BigDecimal.valueOf(3), coffee);
        storeService.addProduct(coffee3,"COFFEE-023", "Coffee 3", BigDecimal.valueOf(2.8), coffee);
        storeService.addProduct(coffee3,"COFFEE-024", "Coffee 4", BigDecimal.valueOf(4), coffee);
        storeService.create(coffee3);
        logger.info("Created {}.", coffee3);

        Store coffee4 = storeService.initiateStore("Coffee4", cafe);
        storeService.addProduct(coffee4,"COFFEE-031", "Coffee 1", BigDecimal.valueOf(3), coffee);
        storeService.addProduct(coffee4,"COFFEE-032", "Coffee 2", BigDecimal.valueOf(3.5), coffee);
        storeService.addProduct(coffee4,"COFFEE-033", "Coffee 3", BigDecimal.valueOf(3.2), coffee);
        storeService.addProduct(coffee4,"COFFEE-034", "Coffee 4", BigDecimal.valueOf(4), coffee);
        storeService.create(coffee4);
        logger.info("Created {}.", coffee4);

        Store pizza1 = storeService.initiateStore("Pizza1", pizzaria);
        storeService.addProduct(pizza1,"PIZZA-001", "Pizza 1", BigDecimal.valueOf(4), pizza);
        storeService.addProduct(pizza1,"PIZZA-002", "Pizza 2", BigDecimal.valueOf(5), pizza);
        storeService.addProduct(pizza1,"PIZZA-003", "Pizza 3", BigDecimal.valueOf(6), pizza);
        storeService.addProduct(pizza1,"PIZZA-004", "Pizza 4", BigDecimal.valueOf(7), pizza);
        storeService.create(pizza1);
        logger.info("Created {}.", pizza1);

        Store pizza2 = storeService.initiateStore("Pizza2", pizzaria);
        storeService.addProduct(pizza2,"PIZZA-011", "Pizza 1", BigDecimal.valueOf(4), pizza);
        storeService.addProduct(pizza2,"PIZZA-012", "Pizza 2", BigDecimal.valueOf(6), pizza);
        storeService.addProduct(pizza2,"PIZZA-013", "Pizza 3", BigDecimal.valueOf(8), pizza);
        storeService.addProduct(pizza2,"PIZZA-014", "Pizza 4", BigDecimal.valueOf(10), pizza);
        storeService.create(pizza2);
        logger.info("Created {}.", pizza2);

        Store pizza3 = storeService.initiateStore("Pizza3", pizzaria);
        storeService.addProduct(pizza3,"PIZZA-021", "Pizza 1", BigDecimal.valueOf(4.5), pizza);
        storeService.addProduct(pizza3,"PIZZA-022", "Pizza 2", BigDecimal.valueOf(6.5), pizza);
        storeService.addProduct(pizza3,"PIZZA-023", "Pizza 3", BigDecimal.valueOf(8.5), pizza);
        storeService.addProduct(pizza3,"PIZZA-024", "Pizza 4", BigDecimal.valueOf(12), pizza);
        storeService.create(pizza3);
        logger.info("Created {}.", pizza3);


        Store burger1 = storeService.initiateStore("Burger1", burgerShop);
        storeService.addProduct(burger1,"BURGER-001", "Burger 1", BigDecimal.valueOf(4), burgers);
        storeService.addProduct(burger1,"BURGER-002", "Burger 2", BigDecimal.valueOf(5), burgers);
        storeService.addProduct(burger1,"BURGER-003", "Burger 3", BigDecimal.valueOf(5.5), burgers);
        storeService.addProduct(burger1,"BURGER-004", "Burger 4", BigDecimal.valueOf(6.5), burgers);
        storeService.create(burger1);
        logger.info("Created {}.", burger1);

        Store burger2 = storeService.initiateStore("Burger2", burgerShop);
        storeService.addProduct(burger2,"BURGER-011", "Burger 1", BigDecimal.valueOf(5), burgers);
        storeService.addProduct(burger2,"BURGER-012", "Burger 2", BigDecimal.valueOf(6), burgers);
        storeService.addProduct(burger2,"BURGER-013", "Burger 3", BigDecimal.valueOf(7), burgers);
        storeService.addProduct(burger2,"BURGER-014", "Burger 4", BigDecimal.valueOf(8), burgers);
        storeService.create(burger2);
        logger.info("Created {}.", burger2);

        Store burger3 = storeService.initiateStore("Burger3", burgerShop);
        storeService.addProduct(burger3,"BURGER-021", "Burger 1", BigDecimal.valueOf(4.4), burgers);
        storeService.addProduct(burger3,"BURGER-022", "Burger 2", BigDecimal.valueOf(6.3), burgers);
        storeService.addProduct(burger3,"BURGER-023", "Burger 3", BigDecimal.valueOf(5.8), burgers);
        storeService.addProduct(burger3,"BURGER-024", "Burger 4", BigDecimal.valueOf(4.9), burgers);
        storeService.create(burger3);
        logger.info("Created {}.", burger3);
    }
}












