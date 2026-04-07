package com.skillbox.controller.option;  // Правильный пакет

public enum GroupOption {
    BY_CURRENCY("Группировать по валюте"),
    BY_BALANCE_RANGE("Группировать по диапазону баланса"),
    BACK("Назад");

    private final String description;

    GroupOption(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}