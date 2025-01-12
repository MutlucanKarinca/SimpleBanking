package com.eteration.simplebanking.entity;


import com.eteration.simplebanking.enums.EnumTransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transaction extends BaseEntity {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    @Column(nullable = false, updatable = false)
    private OffsetDateTime date = OffsetDateTime.now(ZoneOffset.UTC);

    @Column(nullable = false, updatable = false)
    private double amount;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private EnumTransactionType type;

    private String approvalCode = UUID.randomUUID().toString();
}