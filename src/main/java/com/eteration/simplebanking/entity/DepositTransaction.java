package com.eteration.simplebanking.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Getter
@Setter
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class DepositTransaction extends Transaction {

}
