package com.eteration.simplebanking.dto;

import com.eteration.simplebanking.enums.EnumTransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class TransactionResponse {
    private OffsetDateTime date;
    private Double amount;
    private EnumTransactionType type;
    private String approvalCode;
}
