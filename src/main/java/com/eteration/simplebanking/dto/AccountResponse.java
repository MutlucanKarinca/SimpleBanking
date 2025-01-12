package com.eteration.simplebanking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String approvalCode;
}
