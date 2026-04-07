package com.skillbox.controller.option;

public enum TransactionOption {
    ADD_INCOME("Добавить доход"),
    ADD_EXPENSE("Добавить расход"),
    VIEW_ALL("Просмотреть все транзакции"),
    VIEW_BY_ACCOUNT("Просмотр по счету"),
    BACK("Назад");

    private final String description;

    TransactionOption(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}