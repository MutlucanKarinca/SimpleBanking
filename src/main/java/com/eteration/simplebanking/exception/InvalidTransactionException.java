package com.eteration.simplebanking.exception;

public class InvalidTransactionException extends BankingException {
    public InvalidTransactionException(String message) {
        super(message, "BANKING_003");
    }
}
