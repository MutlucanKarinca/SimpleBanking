package com.eteration.simplebanking.exception;


public class InsufficientBalanceException extends BankingException {
    public InsufficientBalanceException() {
        super("Insufficient balance", "BANKING_001");
    }
}
