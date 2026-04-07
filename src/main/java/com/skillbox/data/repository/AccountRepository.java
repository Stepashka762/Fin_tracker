package com.skillbox.data.repository;


import com.skillbox.data.model.Account;
import com.skillbox.exception.DataAccessException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AccountRepository {

    void addAccount(Account account) throws DataAccessException;
    Optional<Account> getAccountById(Long id) throws DataAccessException;
    List<Account> getAllAccounts() throws DataAccessException;
    void updateAccount(Account account) throws DataAccessException;
    void deleteAccount(Long id) throws DataAccessException;


    Long getNextAccountId() throws DataAccessException;


    double getTotalBalance() throws DataAccessException;
    double getAverageBalance() throws DataAccessException;
    Optional<Account> getAccountWithMaxBalance() throws DataAccessException;
    Optional<Account> getAccountWithMinBalance() throws DataAccessException;


    Map<String, List<Account>> getAccountsGroupedByCurrency() throws DataAccessException;
    Map<String, List<Account>> getAccountsGroupedByBalanceRange() throws DataAccessException;


    Map<String, Double> getBalanceByCurrency() throws DataAccessException;
    int getAccountsCount() throws DataAccessException;


    void transfer(Long fromAccountId, Long toAccountId, double amount)
            throws DataAccessException, IllegalArgumentException;
}