package com.acme.delivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORDERS")
@SequenceGenerator(name = "idGenerator", sequenceName = "ORDERS_SEQ", initialValue = 1, allocationSize = 1)
public class Order extends BaseModel {
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @NotNull
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @NotNull
    private Store store;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date submitDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @NotNull
    private Set<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    @Column(length = 13, nullable = false)
    @NotNull
    private PaymentMethod paymentMethod;



    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal cost;

}

