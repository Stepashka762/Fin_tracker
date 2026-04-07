package com.skillbox.data.model;

public enum AccountType {
    CURRENT(0),
    SAVINGS(1),
    CREDIT(2);

    private final int value;

    AccountType(int value) {
        this.value = value;
    }

    public static AccountType fromValue(int value) {
        for (AccountType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Неизвестный тип аккаунта: " + value);
    }
}