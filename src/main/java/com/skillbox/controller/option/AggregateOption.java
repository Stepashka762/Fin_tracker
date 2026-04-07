package com.skillbox.controller.option;

public enum AggregateOption {
    TOTAL_BALANCE("Общий баланс"),
    AVG_BALANCE("Средний баланс"),
    MAX_BALANCE("Максимальный баланс"),
    MIN_BALANCE("Минимальный баланс"),
    BALANCE_BY_CURRENCY("Баланс по валютам"),
    BACK("Назад");

    private final String description;

    AggregateOption(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}