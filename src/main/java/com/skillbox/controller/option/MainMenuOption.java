package com.skillbox.controller.option;

public enum MainMenuOption {
    EXIT(0, "Выход"),
    SET_FILTERS(1, "Задать критерии поиска"),
    SET_GROUPING(2, "Выбрать группировку"),
    SET_AGGREGATION(3, "Выбрать агрегацию"),
    CALCULATE_ANALYTICS(4, "Рассчитать аналитику"),
    SAVE_ANALYTICS(5, "Сохранить результаты");

    private final int value;
    private final String description;

    MainMenuOption(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption option : values()) {
            if (option.value == value) {
                return option;
            }
        }
        throw new IllegalArgumentException("Неизвестное значение: " + value);
    }
}