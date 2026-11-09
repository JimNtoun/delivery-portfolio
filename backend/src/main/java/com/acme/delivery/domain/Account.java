package com.acme.delivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACCOUNTS")
@SequenceGenerator(name = "idGenerator", sequenceName = "ACCOUNTS_SEQ", initialValue = 1, allocationSize = 1)
public class Account extends BaseModel {
    @Column(length = 20, nullable = false)
    @NotNull
    @Size(min = 3, max =15)
    private String username;

    @Column(length = 30, nullable = false)
    @NotNull
    private String password;

    @Column(name = "email", nullable = false, unique = true, length = 30)
    @Email
    @NotNull
    private String email;

    @Column(length = 13, nullable = false, unique = true)
    @NotNull
    private String phoneNumber;

    @NotNull
    @Column(length = 50, nullable = false)
    @Max(value = 100, message = "too old for this application")
    @Min(value = 18, message = "too young for this application")
    private int age;

    @Column(length = 30, nullable = false)
    @NotNull
    private String address;

    @Column(length = 30, nullable = false)
    @NotNull
    private String city;
/*
    @ToString.Exclude
    @OneToOne(mappedBy = "account",fetch = FetchType.EAGER, orphanRemoval = true, cascade=CascadeType.ALL)
    private  Address address;

 */
}

