package com.acme.delivery.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @Column(name = "id")
    private Long id;
}

