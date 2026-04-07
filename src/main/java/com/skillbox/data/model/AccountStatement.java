package com.skillbox.data.model;

import java.util.List;

/**
 * Интерфейс для управления выписками по счету.
 */
public interface AccountStatement {

    // Реализация методов интерфейса AccountStatement
    String generateStatement();

    /**
     * Метод для получения списка всех транзакций по счету.
     *
     * @return Список транзакций.
     */
    List<Transaction> getTransactions();

}
