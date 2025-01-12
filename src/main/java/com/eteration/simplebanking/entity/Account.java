package com.eteration.simplebanking.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class Account extends BaseEntity {

    @Column(nullable = false, updatable = false)
    private String owner;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false, updatable = false, unique = true)
    private String accountNumber = UUID.randomUUID().toString();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();


}
