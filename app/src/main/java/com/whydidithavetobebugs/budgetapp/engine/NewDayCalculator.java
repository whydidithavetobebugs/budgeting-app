package com.whydidithavetobebugs.budgetapp.engine;

import com.whydidithavetobebugs.budgetapp.model.Transaction;

public interface NewDayCalculator {
    public int getNumberOfMidnightsBetweenTransactions(Transaction latestTransaction, Transaction previousTransaction) throws NewDayCalculationException;
}
