package com.skillbox.controller;


import com.skillbox.controller.option.GroupOption;
import com.skillbox.data.model.Account;
import com.skillbox.data.repository.AccountRepository;
import com.skillbox.exception.DataAccessException;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GroupMenuController extends AbstractMenuController<GroupOption> {

    private final AccountRepository accountRepository;

    public GroupMenuController(AccountRepository accountRepository, Scanner scanner) {
        super(GroupOption.class, "=== Группировка счетов ===", scanner);
        this.accountRepository = accountRepository;
    }

    @Override
    protected void processSelectedOption(GroupOption option) {
        try {
            switch (option) {
                case BY_CURRENCY:
                    showAccountsGroupedByCurrency();
                    break;
                case BY_BALANCE_RANGE:
                    showAccountsGroupedByBalanceRange();
                    break;
                case BACK:

                    break;
            }
        } catch (DataAccessException e) {
            System.err.println("Ошибка доступа к данным: " + e.getMessage());
        }
    }

    @Override
    protected boolean isExitOption(GroupOption option) {
        return option == GroupOption.BACK;
    }

    private void showAccountsGroupedByCurrency() throws DataAccessException {
        System.out.println("\n=== Счета по валютам ===");
        Map<String, List<Account>> accountsByCurrency = accountRepository.getAccountsGroupedByCurrency();

        if (accountsByCurrency.isEmpty()) {
            System.out.println("Нет данных для отображения");
            return;
        }

        accountsByCurrency.forEach((currency, accounts) -> {
            System.out.printf("\nВалюта: %s\n", currency);
            accounts.forEach(account ->
                    System.out.printf("- %s: %.2f\n", account.getName(), account.getBalance()));
        });
        waitForUserInput();
    }

    private void showAccountsGroupedByBalanceRange() throws DataAccessException {
        System.out.println("\n=== Счета по диапазонам баланса ===");
        Map<String, List<Account>> accountsByRange = accountRepository.getAccountsGroupedByBalanceRange();

        if (accountsByRange.isEmpty()) {
            System.out.println("Нет данных для отображения");
            return;
        }

        accountsByRange.forEach((range, accounts) -> {
            System.out.printf("\nДиапазон: %s\n", range);
            accounts.forEach(account ->
                    System.out.printf("- %s: %.2f %s\n",
                            account.getName(), account.getBalance(), account.getCurrency()));
        });
        waitForUserInput();
    }
}