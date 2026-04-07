package com.skillbox.data.repository;


import com.skillbox.data.model.Transaction;
import com.skillbox.exception.DataAccessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TransactionRepository {

    void addTransaction(Transaction transaction) throws DataAccessException;
    Optional<Transaction> getTransactionById(Long id) throws DataAccessException;
    List<Transaction> getAllTransactions() throws DataAccessException;
    void updateTransaction(Transaction transaction) throws DataAccessException;
    void deleteTransaction(Long id) throws DataAccessException;


    List<Transaction> getTransactionsByAccount(Long accountId) throws DataAccessException;
    List<Transaction> getTransactionsByDateRange(LocalDate start, LocalDate end) throws DataAccessException;
    List<Transaction> getTransactionsByCategory(String category) throws DataAccessException;


    double getTotalIncomeByAccount(Long accountId) throws DataAccessException;
    double getTotalExpensesByAccount(Long accountId) throws DataAccessException;
    Map<String, Double> getExpensesByCategory() throws DataAccessException;
}