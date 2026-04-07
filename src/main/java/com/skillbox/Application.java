package com.skillbox;


import com.skillbox.controller.MainMenuController;
import com.skillbox.data.repository.AccountRepository;
import com.skillbox.data.repository.AccountRepositoryImpl;
import com.skillbox.exception.DataAccessException;

import java.util.Scanner;

public class Application {
    private static final String ACCOUNTS_FILE = "accounts.dat";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("=== Финансовый трекер ===");
            System.out.println("Инициализация хранилища...");

            AccountRepository accountRepository = new AccountRepositoryImpl(ACCOUNTS_FILE);
            MainMenuController mainMenu = new MainMenuController(accountRepository, scanner);

            System.out.println("Система готова к работе!");
            mainMenu.runMenu();

        } catch (DataAccessException e) {
            System.err.println("\nФатальная ошибка инициализации:");
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            System.out.println("\nПриложение завершено.");
        }
    }
}