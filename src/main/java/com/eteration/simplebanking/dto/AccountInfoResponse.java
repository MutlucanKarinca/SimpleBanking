package com.eteration.simplebanking.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Setter
@Getter
public class AccountInfoResponse {
    private String accountNumber;
    private String owner;
    private Double balance;
    private OffsetDateTime createdAt;
    private List<TransactionResponse> transactions;
}
