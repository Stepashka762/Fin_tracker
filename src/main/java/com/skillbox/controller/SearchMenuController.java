package com.skillbox.controller;


import com.skillbox.controller.option.SearchOption;
import com.skillbox.data.model.Account;
import com.skillbox.data.repository.AccountRepository;
import com.skillbox.exception.DataAccessException;

import java.util.List;
import java.util.Scanner;

public class SearchMenuController extends AbstractMenuController<SearchOption> {

    private final AccountRepository accountRepository;

    public SearchMenuController(AccountRepository accountRepository, Scanner scanner) {
        super(SearchOption.class, "=== Поиск счетов ===", scanner);
        this.accountRepository = accountRepository;
    }

    @Override
    protected void processSelectedOption(SearchOption option) {
        try {
            switch (option) {
                case BY_NAME:
                    searchByName();
                    break;
                case BY_CURRENCY:
                    searchByCurrency();
                    break;
                case BY_BALANCE_RANGE:
                    searchByBalanceRange();
                    break;
                case BACK:

                    break;
            }
        } catch (DataAccessException e) {
            System.err.println("Ошибка доступа к данным: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Ошибка ввода: введите корректное число");
        }
    }

    @Override
    protected boolean isExitOption(SearchOption option) {
        return option == SearchOption.BACK;
    }

    private void searchByName() throws DataAccessException {
        System.out.print("\nВведите имя для поиска: ");
        String name = getScanner().nextLine();

        List<Account> results = accountRepository.getAllAccounts().stream()
                .filter(acc -> acc.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();

        printSearchResults(results);
    }

    private void searchByCurrency() throws DataAccessException {
        System.out.print("\nВведите валюту (RUB/USD/EUR): ");
        String currency = getScanner().nextLine().toUpperCase();

        List<Account> results = accountRepository.getAllAccounts().stream()
                .filter(acc -> acc.getCurrency().equals(currency))
                .toList();

        printSearchResults(results);
    }

    private void searchByBalanceRange() throws DataAccessException {
        System.out.print("\nВведите минимальный баланс: ");
        double min = Double.parseDouble(getScanner().nextLine());

        System.out.print("Введите максимальный баланс: ");
        double max = Double.parseDouble(getScanner().nextLine());

        if (min > max) {
            System.err.println("Ошибка: минимальное значение не может быть больше максимального");
            return;
        }

        List<Account> results = accountRepository.getAllAccounts().stream()
                .filter(acc -> acc.getBalance() >= min && acc.getBalance() <= max)
                .toList();

        printSearchResults(results);
    }

    private void printSearchResults(List<Account> accounts) {
        if (accounts.isEmpty()) {
            System.out.println("\nСчета не найдены");
        } else {
            System.out.println("\nРезультаты поиска:");
            accounts.forEach(acc -> System.out.printf(
                    "- ID: %d | %s | Баланс: %.2f %s\n",
                    acc.getId(),
                    acc.getName(),
                    acc.getBalance(),
                    acc.getCurrency()));
        }
        waitForUserInput();
    }
}