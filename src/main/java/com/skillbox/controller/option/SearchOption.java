package com.skillbox.controller.option;

public enum SearchOption {
    BY_NAME("Поиск по имени"),
    BY_CURRENCY("Поиск по валюте"),
    BY_BALANCE_RANGE("Поиск по диапазону баланса"),
    BACK("Назад в главное меню");

    private final String description;

    SearchOption(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}