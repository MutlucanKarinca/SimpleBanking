package com.eteration.simplebanking.enums;

public enum EnumTransactionType {
    DEPOSIT(1),
    WITHDRAWAL(2),
    BILL_PAYMENT(3);

    EnumTransactionType(final int value) {
        this.value = value;
    }

    private final int value;

    public int getValue() {
        return value;
    }
}
