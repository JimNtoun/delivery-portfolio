package com.acme.delivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ITEMS")
@SequenceGenerator(name = "idGenerator", sequenceName = "ITEMS_SEQ", initialValue = 1, allocationSize = 1)
public class OrderItem extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    private Product product;

    @Column(nullable = false)
    @NotNull
    private int quantity;

    @Column(precision = 10, scale = 2, nullable = false)
    @NotNull
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private Order order;
}

