package com.skillbox.controller;


import com.skillbox.controller.option.MainOption;
import com.skillbox.data.model.Account;
import com.skillbox.data.repository.AccountRepository;
import com.skillbox.exception.DataAccessException;

import java.util.List;
import java.util.Scanner;

public class MainMenuController extends AbstractMenuController<MainOption> {
    private final AccountRepository accountRepository;
    private final TransactionMenuController transactionController;
    private final SearchMenuController searchController;
    private final GroupMenuController groupController;
    private final AggregateMenuController aggregateController;

    public MainMenuController(AccountRepository accountRepository, Scanner scanner)
            throws DataAccessException {
        super(MainOption.class, "=== Главное меню ===", scanner);
        this.accountRepository = accountRepository;
        this.transactionController = new TransactionMenuController(accountRepository, scanner);
        this.searchController = new SearchMenuController(accountRepository, scanner);
        this.groupController = new GroupMenuController(accountRepository, scanner);
        this.aggregateController = new AggregateMenuController(accountRepository, scanner);
    }

    @Override
    protected void processSelectedOption(MainOption option) {
        try {
            switch (option) {
                case VIEW_ACCOUNTS:
                    viewAccounts();
                    break;
                case ADD_ACCOUNT:
                    addAccount();
                    break;
                case TRANSACTIONS:
                    transactionController.runMenu();
                    break;
                case SEARCH:
                    searchController.runMenu();
                    break;
                case GROUP_OPERATIONS:
                    groupController.runMenu();
                    break;
                case AGGREGATE_OPERATIONS:
                    aggregateController.runMenu();
                    break;
                case EXIT:
                    System.out.println("Завершение работы...");
                    break;
            }
        } catch (DataAccessException e) {
            System.err.println("Ошибка доступа к данным: " + e.getMessage());
            waitForUserInput();
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
            waitForUserInput();
        }
    }

    @Override
    protected boolean isExitOption(MainOption option) {
        return option == MainOption.EXIT;
    }

    private void viewAccounts() throws DataAccessException {
        System.out.println("\n=== Список всех счетов ===");
        List<Account> accounts = accountRepository.getAllAccounts();

        if (accounts.isEmpty()) {
            System.out.println("Счета не найдены");
        } else {
            System.out.println("ID  | Название счета         | Баланс       | Валюта");
            System.out.println("--------------------------------------------------");
            accounts.forEach(acc -> System.out.printf(
                    "%-4d| %-23s| %12.2f | %s\n",
                    acc.getId(),
                    acc.getName(),
                    acc.getBalance(),
                    acc.getCurrency()
            ));
        }
        waitForUserInput();
    }

    private void addAccount() throws DataAccessException {
        System.out.println("\n=== Создание нового счета ===");

        String name = readAccountName();
        double balance = readInitialBalance();
        String currency = readCurrency();

        Account newAccount = new Account(
                accountRepository.getNextAccountId(),
                name,
                balance,
                currency
        );

        accountRepository.addAccount(newAccount);
        System.out.println("\nСчет успешно создан!");
        waitForUserInput();
    }

    private String readAccountName() {
        System.out.print("Введите название счета: ");
        return getScanner().nextLine().trim();
    }

    private double readInitialBalance() {
        while (true) {
            try {
                System.out.print("Введите начальный баланс: ");
                return Double.parseDouble(getScanner().nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число");
            }
        }
    }

    private String readCurrency() {
        while (true) {
            System.out.print("Введите валюту (RUB/USD/EUR): ");
            String input = getScanner().nextLine().toUpperCase();
            if (input.matches("RUB|USD|EUR")) {
                return input;
            }
            System.out.println("Ошибка: допустимые валюты - RUB, USD или EUR");
        }
    }
}