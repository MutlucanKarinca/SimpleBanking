package com.eteration.simplebanking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateRequest {
    private String owner;
    private Double balance;
}
