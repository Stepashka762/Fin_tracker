package com.skillbox.data.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;


public class Analytic {
    private LocalDateTime calculationDate;
    private String groupBy;
    private String aggregateFunction;
    private Map<String, BigDecimal> results;
    private String filterDescription;

    public Analytic() {
        this.calculationDate = LocalDateTime.now();
    }

    public Analytic(String groupBy, String aggregateFunction,
                    Map<String, BigDecimal> results, String filterDescription) {
        this();
        this.groupBy = groupBy;
        this.aggregateFunction = aggregateFunction;
        this.results = results;
        this.filterDescription = filterDescription;
    }


    public LocalDateTime getCalculationDate() {
        return calculationDate;
    }

    public void setCalculationDate(LocalDateTime calculationDate) {
        this.calculationDate = calculationDate;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getAggregateFunction() {
        return aggregateFunction;
    }

    public void setAggregateFunction(String aggregateFunction) {
        this.aggregateFunction = aggregateFunction;
    }

    public Map<String, BigDecimal> getResults() {
        return results;
    }

    public void setResults(Map<String, BigDecimal> results) {
        this.results = results;
    }

    public String getFilterDescription() {
        return filterDescription;
    }

    public void setFilterDescription(String filterDescription) {
        this.filterDescription = filterDescription;
    }


    public BigDecimal getTotal() {
        return results.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Analytic analytic = (Analytic) o;
        return Objects.equals(calculationDate, analytic.calculationDate) &&
                Objects.equals(groupBy, analytic.groupBy) &&
                Objects.equals(aggregateFunction, analytic.aggregateFunction) &&
                Objects.equals(results, analytic.results) &&
                Objects.equals(filterDescription, analytic.filterDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calculationDate, groupBy, aggregateFunction, results, filterDescription);
    }

    @Override
    public String toString() {
        return "Analytic{" +
                "calculationDate=" + calculationDate +
                ", groupBy='" + groupBy + '\'' +
                ", aggregateFunction='" + aggregateFunction + '\'' +
                ", results=" + results +
                ", filterDescription='" + filterDescription + '\'' +
                '}';
    }


    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append("Дата: ").append(calculationDate).append("\n");
        sb.append("Группировка: '").append(groupBy).append("' (").append(aggregateFunction).append(")\n");
        sb.append("Фильтр: ").append(filterDescription).append("\n");
        sb.append("---\n");
        sb.append("Аналитика:\n\n");

        results.forEach((key, value) ->
                sb.append(key).append(": ").append(value).append("\n")
        );

        sb.append("\nОбщий итог: ").append(getTotal());
        sb.append("\n==================================================");
        return sb.toString();
    }
}