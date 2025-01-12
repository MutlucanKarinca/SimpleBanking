package com.eteration.simplebanking.exception;

public class RequiredFieldException extends BankingException {
    public RequiredFieldException(String message) {
        super(message,"BANKING_004");
    }
}
