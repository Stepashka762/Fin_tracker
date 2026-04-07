package com.skillbox.service;


import com.skillbox.data.model.Transaction;
import com.skillbox.data.repository.TransactionRepository;
import com.skillbox.exception.DataAccessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Long getNextTransactionId() throws DataAccessException {
        return transactionRepository.getAllTransactions().stream()
                .mapToLong(Transaction::getId)
                .max()
                .orElse(0L) + 1L;
    }

    @Override
    public void addTransaction(Transaction transaction) throws DataAccessException {
        validateTransaction(transaction);
        transactionRepository.addTransaction(transaction);
    }

    private void validateTransaction(Transaction transaction) throws DataAccessException {
        if (transaction == null) {
            throw new DataAccessException("Transaction cannot be null");
        }
        if (transaction.getAmount() == 0) {
            throw new DataAccessException("Transaction amount cannot be zero");
        }
    }

    @Override
    public Transaction getTransactionById(Long id) throws DataAccessException {
        return transactionRepository.getTransactionById(id)
                .orElseThrow(() -> new DataAccessException("Transaction not found"));
    }

    @Override
    public List<Transaction> getAllTransactions() throws DataAccessException {
        return transactionRepository.getAllTransactions();
    }

    @Override
    public List<Transaction> getTransactionsByAccount(Long accountId) throws DataAccessException {
        return transactionRepository.getTransactionsByAccount(accountId);
    }

    @Override
    public double getAccountBalance(Long accountId) throws DataAccessException {
        return getTotalIncomeByAccount(accountId) -
                Math.abs(getTotalExpensesByAccount(accountId));
    }


    @Override
    public List<Transaction> getTransactionsByDateRange(LocalDate start, LocalDate end) throws DataAccessException {
        return transactionRepository.getTransactionsByDateRange(start, end);
    }

    @Override
    public double getTotalIncomeByAccount(Long accountId) throws DataAccessException {
        return transactionRepository.getTotalIncomeByAccount(accountId);
    }

    @Override
    public double getTotalExpensesByAccount(Long accountId) throws DataAccessException {
        return transactionRepository.getTotalExpensesByAccount(accountId);
    }

    @Override
    public Map<String, Double> getExpensesByCategory() throws DataAccessException {
        return transactionRepository.getExpensesByCategory();
    }

    @Override
    public List<Transaction> getTransactionsByCategory(String category) throws DataAccessException {
        return transactionRepository.getTransactionsByCategory(category);
    }

    @Override
    public void updateTransaction(Transaction transaction) throws DataAccessException {
        validateTransaction(transaction);
        transactionRepository.updateTransaction(transaction);
    }

    @Override
    public void deleteTransaction(Long id) throws DataAccessException {
        transactionRepository.deleteTransaction(id);
    }
}