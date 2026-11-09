package com.acme.delivery.repository;

import com.acme.delivery.domain.Account;
import com.acme.delivery.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select distinct o from Order o join fetch o.account join fetch o.orderItems oi join fetch oi.product where o.id = :id")
    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH, value = "false"))
    Optional<Order> getLazy(Long id);

    @Query("select o from Order o join fetch o.orderItems")
    List<Order> findAllLazy();


    List<Order> findOrdersByAccountEmail(String email);


}
