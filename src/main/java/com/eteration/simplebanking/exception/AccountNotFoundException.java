package com.eteration.simplebanking.exception;

public class AccountNotFoundException extends BankingException {
    public AccountNotFoundException() {
        super("Account not found", "BANKING_002");
    }
}
