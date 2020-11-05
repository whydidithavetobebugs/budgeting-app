package com.whydidithavetobebugs.budgetapp.engine;

import com.whydidithavetobebugs.budgetapp.model.Transaction;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDateTime;

public class NewDayCalculatorTest {

    private NewDayCalculator newDayCalculator = new NewDayCalculatorImpl();

    @Test
    public void testMultipleDaysHavePassedSinceLastTransaction_Successful() throws NewDayCalculationException {
        Transaction previousTransaction = new Transaction(1, LocalDateTime.of(2020, 8, 27, 11, 34, 9));
        Transaction currentTransaction = new Transaction(2, LocalDateTime.of(2020, 9, 3, 0, 4, 17));

        assertEquals(7, newDayCalculator.getNumberOfMidnightsBetweenTransactions(currentTransaction, previousTransaction));
    }

    @Test
    public void testNoDaysHavePassedSinceLastTransaction_Successful() throws NewDayCalculationException {
        Transaction previousTransaction = new Transaction(1, LocalDateTime.of(2020, 9, 2, 3, 34, 9));
        Transaction currentTransaction = new Transaction(2, LocalDateTime.of(2020, 9, 2, 21, 4, 17));

        assertEquals(0, newDayCalculator.getNumberOfMidnightsBetweenTransactions(currentTransaction, previousTransaction));
    }

    @Test
    public void testNewTransactionBeforeLastTransaction_ThrowsException() {
        Transaction previousTransaction = new Transaction(1, LocalDateTime.of(2020, 9, 2, 23, 34, 9));
        Transaction currentTransaction = new Transaction(2, LocalDateTime.of(2020, 9, 2, 10, 4, 17));

        try {
            newDayCalculator.getNumberOfMidnightsBetweenTransactions(currentTransaction, previousTransaction);
            fail("Exception not thrown");
        } catch (NewDayCalculationException e) {
            assertTrue(e.getMessage().contains("Latest transaction cannot be before previous transaction"));
        }
    }
}
