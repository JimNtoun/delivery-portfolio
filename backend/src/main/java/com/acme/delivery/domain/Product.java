package com.acme.delivery.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCTS")
@SequenceGenerator(name = "idGenerator", sequenceName = "PRODUCTS_SEQ", initialValue = 1, allocationSize = 1)
public class Product extends BaseModel {
    @Column(length = 30, nullable = false)
    @NotNull
    private String serial;

    @Column(length = 50, nullable = false)
    @NotNull
    private String productName;

    @Column(precision = 10, scale = 2, nullable = false)
    @NotNull
    private BigDecimal price;

    @NotNull
    @ManyToOne
    private ProductCategory productCategory;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private Store store;
}
