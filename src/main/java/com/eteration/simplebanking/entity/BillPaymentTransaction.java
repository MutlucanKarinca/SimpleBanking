package com.eteration.simplebanking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "bill_payment_transactions")
@PrimaryKeyJoinColumn(name = "id")
public class BillPaymentTransaction extends Transaction {
    private String payee;

    private String subscriberNumber;

}

