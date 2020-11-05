package com.whydidithavetobebugs.budgetapp.engine;

import com.whydidithavetobebugs.budgetapp.model.Transaction;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class NewDayCalculatorImpl implements NewDayCalculator {
    @Override
    public int getNumberOfMidnightsBetweenTransactions(Transaction latestTransaction, Transaction previousTransaction) throws NewDayCalculationException {
        if (latestTransaction.getDateTime().isBefore(previousTransaction.getDateTime())) {
            throw new NewDayCalculationException(String.format("Latest transaction cannot be before previous transaction. Latest transaction datetime: %s, previous transaction datetime: %s", latestTransaction.getDateTime(), previousTransaction.getDateTime()));
        }

        LocalDateTime previousPrecedingMidnight = LocalDateTime.of(
                previousTransaction.getDateTime().getYear(),
                previousTransaction.getDateTime().getMonth(),
                previousTransaction.getDateTime().getDayOfMonth(),
                0,
                0,
                0,
                0
        );

        return (int) previousPrecedingMidnight.until(latestTransaction.getDateTime(), ChronoUnit.DAYS);
    }
}
