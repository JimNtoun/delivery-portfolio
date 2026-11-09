package com.acme.delivery.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCT_CATEGORIES")
@SequenceGenerator(name = "idGenerator", sequenceName = "PRODUCT_CATEGORIES_SEQ", initialValue = 1, allocationSize = 1)
public class ProductCategory extends BaseModel {
    @Column(length = 50, nullable = false)
    @NotNull
    private String description;
}

