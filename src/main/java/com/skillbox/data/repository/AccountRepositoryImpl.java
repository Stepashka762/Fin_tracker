package com.skillbox.data.repository;


import com.skillbox.data.model.Account;
import com.skillbox.exception.DataAccessException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class AccountRepositoryImpl implements AccountRepository {
    private final List<Account> accounts = new ArrayList<>();
    private final String dataFile;

    public AccountRepositoryImpl(String dataFile) throws DataAccessException {
        this.dataFile = dataFile;
        loadData();
    }

    private void loadData() throws DataAccessException {
        File file = new File(dataFile);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Account> loadedAccounts = (List<Account>) ois.readObject();
            accounts.clear();
            accounts.addAll(loadedAccounts);
        } catch (IOException | ClassNotFoundException e) {
            throw new DataAccessException("Ошибка загрузки данных: " + e.getMessage(), e);
        }
    }

    private void saveData() throws DataAccessException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            throw new DataAccessException("Ошибка сохранения данных: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getNextAccountId() throws DataAccessException {
        return accounts.stream()
                .mapToLong(Account::getId)
                .max()
                .orElse(0L) + 1L;
    }

    @Override
    public double getTotalBalance() throws com.skillbox.exception.DataAccessException {
        return 0;
    }

    @Override
    public double getAverageBalance() throws com.skillbox.exception.DataAccessException {
        return 0;
    }

    @Override
    public Optional<com.skillbox.data.model.Account> getAccountWithMaxBalance() throws com.skillbox.exception.DataAccessException {
        return Optional.empty();
    }

    @Override
    public Optional<com.skillbox.data.model.Account> getAccountWithMinBalance() throws com.skillbox.exception.DataAccessException {
        return Optional.empty();
    }

    @Override
    public Map<String, List<com.skillbox.data.model.Account>> getAccountsGroupedByCurrency() throws com.skillbox.exception.DataAccessException {
        return Map.of();
    }

    @Override
    public Map<String, List<com.skillbox.data.model.Account>> getAccountsGroupedByBalanceRange() throws com.skillbox.exception.DataAccessException {
        return Map.of();
    }

    @Override
    public Map<String, Double> getBalanceByCurrency() throws com.skillbox.exception.DataAccessException {
        return Map.of();
    }

    @Override
    public int getAccountsCount() throws com.skillbox.exception.DataAccessException {
        return 0;
    }

    @Override
    public void addAccount(Account account) throws DataAccessException {
        if (account == null) {
            throw new IllegalArgumentException("Счет не может быть null");
        }
        accounts.add(account);
        saveData();
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
    public void updateAccount(com.skillbox.data.model.Account account) throws com.skillbox.exception.DataAccessException {

    }

    @Override
    public void deleteAccount(Long id) throws com.skillbox.exception.DataAccessException {

    }

    @Override
    public void transfer(Long fromId, Long toId, double amount) throws DataAccessException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть положительной");
        }

        Account fromAccount = getAccountById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("Счет отправителя не найден"));
        Account toAccount = getAccountById(toId)
                .orElseThrow(() -> new IllegalArgumentException("Счет получателя не найден"));

        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Недостаточно средств на счете отправителя");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        saveData();
    }


}