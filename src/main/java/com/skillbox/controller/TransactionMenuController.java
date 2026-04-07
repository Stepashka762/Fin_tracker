package com.skillbox.controller;


import com.skillbox.controller.option.TransactionOption;
import com.skillbox.data.model.Transaction;
import com.skillbox.data.repository.AccountRepository;
import com.skillbox.exception.DataAccessException;
import com.skillbox.service.TransactionService;
import com.skillbox.service.TransactionServiceImpl;
import com.skillbox.data.repository.TransactionRepositoryImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TransactionMenuController extends AbstractMenuController<TransactionOption> {
    private final TransactionService transactionService;
    private final AccountRepository accountRepository;

    public TransactionMenuController(AccountRepository accountRepository, Scanner scanner)
            throws DataAccessException {
        super(TransactionOption.class, "=== Управление транзакциями ===", scanner);
        this.accountRepository = accountRepository;
        this.transactionService = new TransactionServiceImpl(
                new TransactionRepositoryImpl("transactions.dat")
        );
    }

    @Override
    protected void processSelectedOption(TransactionOption option) {
        try {
            switch (option) {
                case ADD_INCOME:
                    addTransaction(true);
                    break;
                case ADD_EXPENSE:
                    addTransaction(false);
                    break;
                case VIEW_ALL:
                    viewTransactions();
                    break;
                case VIEW_BY_ACCOUNT:
                    viewTransactionsByAccount();
                    break;
                case BACK:
                    break;
            }
        } catch (DataAccessException e) {
            System.err.println("Ошибка доступа к данным: " + e.getMessage());
            waitForUserInput();
        }
    }

    @Override
    protected boolean isExitOption(TransactionOption option) {
        return option == TransactionOption.BACK;
    }

    private void addTransaction(boolean isIncome) throws DataAccessException {
        System.out.println("\n=== Добавление " + (isIncome ? "дохода" : "расхода") + " ===");

        Long accountId = readAccountId();
        double amount = readAmount(isIncome);
        String category = readCategory();
        String description = readDescription();

        Transaction transaction = new Transaction(
                transactionService.getNextTransactionId(),
                accountId,
                LocalDate.now(),
                isIncome ? amount : -amount,
                category,
                description
        );

        transactionService.addTransaction(transaction);
        System.out.println("\nТранзакция успешно добавлена!");
        waitForUserInput();
    }

    private Long readAccountId() throws DataAccessException {
        while (true) {
            try {
                System.out.print("Введите ID счета: ");
                Long accountId = Long.parseLong(getScanner().nextLine());

                if (accountRepository.getAccountById(accountId).isPresent()) {
                    return accountId;
                }
                System.out.println("Ошибка: счет с таким ID не найден");
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректный числовой ID");
            }
        }
    }

    private double readAmount(boolean isIncome) {
        while (true) {
            try {
                System.out.print("Введите сумму " + (isIncome ? "дохода" : "расхода") + ": ");
                double amount = Double.parseDouble(getScanner().nextLine());
                if (amount <= 0) {
                    System.out.println("Ошибка: сумма должна быть положительной");
                    continue;
                }
                return amount;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число");
            }
        }
    }

    private String readCategory() {
        System.out.print("Введите категорию: ");
        return getScanner().nextLine().trim();
    }

    private String readDescription() {
        System.out.print("Введите описание (необязательно): ");
        return getScanner().nextLine().trim();
    }

    private void viewTransactions() throws DataAccessException {
        List<Transaction> transactions = transactionService.getAllTransactions();
        printTransactions(transactions, "Все транзакции");
    }

    private void viewTransactionsByAccount() throws DataAccessException {
        Long accountId = readAccountId();
        List<Transaction> transactions = transactionService.getTransactionsByAccount(accountId);
        printTransactions(transactions, "Транзакции по счету ID: " + accountId);
    }

    private void printTransactions(List<Transaction> transactions, String title) {
        System.out.println("\n=== " + title + " ===");
        if (transactions.isEmpty()) {
            System.out.println("Транзакции не найдены");
        } else {
            System.out.println("ID  | Счет  | Дата       | Сумма     | Категория       | Описание");
            System.out.println("---------------------------------------------------------------");
            transactions.forEach(t -> System.out.printf(
                    "%-4d| %-6d| %s | %9.2f | %-15s | %s\n",
                    t.getId(),
                    t.getAccountId(),
                    t.getDate(),
                    t.getAmount(),
                    t.getCategory(),
                    t.getDescription()
            ));
        }
        waitForUserInput();
    }
}