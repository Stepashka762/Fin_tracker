package com.skillbox.data.repository;


import com.skillbox.data.model.Transaction;
import com.skillbox.exception.DataAccessException;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionRepositoryImpl implements TransactionRepository {
    private static final long serialVersionUID = 1L;
    private final List<Transaction> transactions = new ArrayList<>();
    private final String dataFile;

    public TransactionRepositoryImpl(String dataFile) throws DataAccessException {
        this.dataFile = dataFile;
        loadData();
    }

    public String getDataFilePath() {
        return dataFile;
    }

    @SuppressWarnings("unchecked")
    private void loadData() throws DataAccessException {
        File file = new File(dataFile);
        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object loaded = ois.readObject();

            if (!(loaded instanceof List)) {
                throw new DataAccessException("Invalid data format: expected List");
            }

            List<?> rawList = (List<?>) loaded;
            if (!rawList.isEmpty() && !(rawList.get(0) instanceof Transaction)) {
                throw new DataAccessException("Invalid data type in storage");
            }

            transactions.clear();
            transactions.addAll((List<Transaction>) loaded);
        } catch (IOException | ClassNotFoundException e) {
            throw new DataAccessException("Failed to load transactions", e);
        }
    }

    private void saveData() throws DataAccessException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            oos.writeObject(new ArrayList<>(transactions));
        } catch (IOException e) {
            throw new DataAccessException("Failed to save transactions", e);
        }
    }

    @Override
    public void addTransaction(Transaction transaction) throws DataAccessException {
        Objects.requireNonNull(transaction, "Transaction cannot be null");
        transactions.add(transaction);
        saveData();
    }

    @Override
    public Optional<Transaction> getTransactionById(Long id) throws DataAccessException {
        return transactions.stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst();
    }

    @Override
    public List<Transaction> getAllTransactions() throws DataAccessException {
        return Collections.unmodifiableList(transactions);
    }

    @Override
    public List<Transaction> getTransactionsByAccount(Long accountId) throws DataAccessException {
        Objects.requireNonNull(accountId, "Account ID cannot be null");
        return transactions.stream()
                .filter(t -> Objects.equals(t.getAccountId(), accountId))
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(LocalDate start, LocalDate end)
            throws DataAccessException {
        Objects.requireNonNull(start, "Start date cannot be null");
        Objects.requireNonNull(end, "End date cannot be null");

        if (start.isAfter(end)) {
            throw new DataAccessException("Start date cannot be after end date");
        }

        return transactions.stream()
                .filter(t -> !t.getDate().isBefore(start) && !t.getDate().isAfter(end))
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public double getTotalIncomeByAccount(Long accountId) throws DataAccessException {
        Objects.requireNonNull(accountId, "Account ID cannot be null");
        return transactions.stream()
                .filter(t -> Objects.equals(t.getAccountId(), accountId))
                .filter(t -> t.getAmount() > 0)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    @Override
    public double getTotalExpensesByAccount(Long accountId) throws DataAccessException {
        Objects.requireNonNull(accountId, "Account ID cannot be null");
        return transactions.stream()
                .filter(t -> Objects.equals(t.getAccountId(), accountId))
                .filter(t -> t.getAmount() < 0)
                .mapToDouble(t -> Math.abs(t.getAmount()))
                .sum();
    }

    @Override
    public Map<String, Double> getExpensesByCategory() throws DataAccessException {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(t -> Math.abs(t.getAmount()))
                ));
    }

    @Override
    public List<Transaction> getTransactionsByCategory(String category) throws DataAccessException {
        Objects.requireNonNull(category, "Category cannot be null");
        if (category.isBlank()) {
            throw new DataAccessException("Category cannot be blank");
        }

        return transactions.stream()
                .filter(t -> category.equalsIgnoreCase(t.getCategory()))
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void updateTransaction(Transaction transaction) throws DataAccessException {
        Objects.requireNonNull(transaction, "Transaction cannot be null");
        deleteTransaction(transaction.getId());
        addTransaction(transaction);
    }

    @Override
    public void deleteTransaction(Long id) throws DataAccessException {
        Objects.requireNonNull(id, "Transaction ID cannot be null");
        if (!transactions.removeIf(t -> Objects.equals(t.getId(), id))) {
            throw new DataAccessException("Transaction not found with ID: " + id);
        }
        saveData();
    }
}