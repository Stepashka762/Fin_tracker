package com.skillbox.controller.option;

public enum MainOption {
    VIEW_ACCOUNTS("Просмотр всех счетов"),
    ADD_ACCOUNT("Добавить новый счет"),
    TRANSACTIONS("Управление транзакциями"),
    SEARCH("Поиск счетов"),
    GROUP_OPERATIONS("Группировка счетов"),
    AGGREGATE_OPERATIONS("Аналитика и отчеты"),
    EXIT("Выход");

    private final String description;

    MainOption(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}