package com.acme.delivery.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "STORE_CATEGORIES")
@SequenceGenerator(name = "idGenerator", sequenceName = "STORE_CATEGORIES_SEQ", initialValue = 1, allocationSize = 1)
public class StoreCategory extends BaseModel{
    @Column(length = 50, nullable = false)
    @NotNull
    private String categoryName;
}
