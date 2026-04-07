package com.skillbox.controller;

import java.util.Scanner;

public abstract class AbstractMenuController<T extends Enum<T>> {
    private final Class<T> enumClass;
    private final String menuTitle;
    private final Scanner scanner;

    protected AbstractMenuController(Class<T> enumClass, String menuTitle, Scanner scanner) {
        this.enumClass = enumClass;
        this.menuTitle = menuTitle;
        this.scanner = scanner;
    }

    public final void runMenu() {
        while (true) {
            printMenu();
            T option = readUserSelection();
            if (option == null) {
                System.out.println("Неверный ввод, попробуйте еще раз");
                continue;
            }
            processSelectedOption(option);
            if (isExitOption(option)) {
                break;
            }
        }
    }

    protected abstract void processSelectedOption(T option);
    protected abstract boolean isExitOption(T option);

    protected final Scanner getScanner() {
        return scanner;
    }

    private void printMenu() {
        System.out.println("\n" + menuTitle);
        int index = 1;
        for (T option : enumClass.getEnumConstants()) {
            System.out.printf("%d. %s%n", index++, option);
        }
        System.out.print("Выберите вариант: ");
    }

    private T readUserSelection() {
        try {
            int choice = Integer.parseInt(getScanner().nextLine());
            T[] options = enumClass.getEnumConstants();
            if (choice > 0 && choice <= options.length) {
                return options[choice - 1];
            }
        } catch (NumberFormatException e) {

        }
        return null;
    }

    protected final void waitForUserInput() {
        System.out.print("\nНажмите Enter чтобы продолжить...");
        getScanner().nextLine();
    }
}