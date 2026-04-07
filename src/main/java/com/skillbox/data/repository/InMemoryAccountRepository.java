package com.skillbox.data.repository;

import com.skillbox.data.model.Account;
import com.skillbox.exception.DataAccessException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryAccountRepository implements AccountRepository {
    private final List<Account> accounts = new ArrayList<>();

    @Override
    public void addAccount(Account account) throws DataAccessException {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        if (accounts.stream().anyMatch(a -> a.getId().equals(account.getId()))) {
            throw new DataAccessException("Account with this ID already exists");
        }
        accounts.add(account);
    }

    @Override
    public void transfer(Long fromId, Long toId, double amount)
            throws DataAccessException, IllegalArgumentException {

        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        Account fromAccount = getAccountById(fromId)
                .orElseThrow(() -> new DataAccessException("Source account not found"));
        Account toAccount = getAccountById(toId)
                .orElseThrow(() -> new DataAccessException("Destination account not found"));

        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
    }

    @Override
    public Optional<Account> getAccountById(Long id) throws DataAccessException {
        return accounts.stream()
                .filter(acc -> acc.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Account> getAllAccounts() throws DataAccessException {
        return new ArrayList<>(accounts);
    }

    @Override
    public Long getNextAccountId() throws DataAccessException {
        return accounts.stream()
                .mapToLong(Account::getId)
                .max()
                .orElse(0L) + 1L;
    }

    @Override
    public double getTotalBalance() throws DataAccessException {
        return accounts.stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }


    @Override
    public double getAverageBalance() throws DataAccessException {
        return accounts.stream()
                .mapToDouble(Account::getBalance)
                .average()
                .orElse(0.0);
    }

    @Override
    public Optional<Account> getAccountWithMaxBalance() throws DataAccessException {
        return accounts.stream()
                .max(Comparator.comparingDouble(Account::getBalance));
    }

    @Override
    public Optional<Account> getAccountWithMinBalance() throws DataAccessException {
        return accounts.stream()
                .min(Comparator.comparingDouble(Account::getBalance));
    }

    @Override
    public Map<String, List<Account>> getAccountsGroupedByCurrency() throws DataAccessException {
        return accounts.stream()
                .collect(Collectors.groupingBy(Account::getCurrency));
    }

    @Override
    public Map<String, List<Account>> getAccountsGroupedByBalanceRange() throws DataAccessException {
        return accounts.stream()
                .collect(Collectors.groupingBy(acc -> {
                    double balance = acc.getBalance();
                    if (balance < 1000) return "Менее 1000";
                    if (balance < 5000) return "1000-5000";
                    if (balance < 10000) return "5000-10000";
                    return "Более 10000";
                }));
    }

    @Override
    public Map<String, Double> getBalanceByCurrency() throws DataAccessException {
        return accounts.stream()
                .collect(Collectors.groupingBy(
                        Account::getCurrency,
                        Collectors.summingDouble(Account::getBalance)
                ));
    }

    @Override
    public int getAccountsCount() throws DataAccessException {
        return accounts.size();
    }

    @Override
    public void updateAccount(Account account) throws DataAccessException {
        deleteAccount(account.getId());
        addAccount(account);
    }

    @Override
    public void deleteAccount(Long id) throws DataAccessException {
        if (!accounts.removeIf(acc -> acc.getId().equals(id))) {
            throw new DataAccessException("Account not found");
        }
    }
}