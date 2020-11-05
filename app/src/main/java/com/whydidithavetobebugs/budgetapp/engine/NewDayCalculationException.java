package com.whydidithavetobebugs.budgetapp.engine;

public class NewDayCalculationException extends Exception {
    public NewDayCalculationException(String message) {
        super(message);
    }

    public NewDayCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
