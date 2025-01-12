package com.eteration.simplebanking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayRequest {
    private Double amount;
    private String payee;
    private String subscriber;
    private Long accountId;
}