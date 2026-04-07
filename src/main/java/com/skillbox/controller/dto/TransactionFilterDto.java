package com.skillbox.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.StringJoiner;

public class TransactionFilterDto {
    private String category;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private String comment;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public String getActiveFilters() {
        StringJoiner joiner = new StringJoiner(", ");

        if (category != null) {
            joiner.add("категория: " + category);
        }
        if (minAmount != null) {
            joiner.add("мин. сумма: " + minAmount);
        }
        if (maxAmount != null) {
            joiner.add("макс. сумма: " + maxAmount);
        }
        if (comment != null) {
            joiner.add("комментарий: " + comment);
        }
        if (startDate != null) {
            joiner.add("с: " + startDate);
        }
        if (endDate != null) {
            joiner.add("по: " + endDate);
        }

        return joiner.length() == 0 ? "нет фильтров" : joiner.toString();
    }
}