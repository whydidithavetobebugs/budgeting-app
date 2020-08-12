package com.whydidithavetobebugs.budgetapp.engine;

import android.content.SharedPreferences;
import android.content.res.Resources;

import com.whydidithavetobebugs.budgetapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BudgetEngine {
    private SharedPreferences sharedPreferences;
    private int defaultDailyBudget;
    private Resources resources;

    public BudgetEngine(SharedPreferences sharedPreferences, int defaultDailyBudget, Resources resources) {
        this.sharedPreferences = sharedPreferences;
        this.defaultDailyBudget = defaultDailyBudget;
        this.resources = resources;
    }

    public String setTotalAmount(int expenditureToAdd) {
        int updatedAmount = calculateAmount(expenditureToAdd);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("amount", updatedAmount);
        editor.apply();

        if (updatedAmount > 0) {
            return resources.getString(R.string.over_budget, updatedAmount);
        } else {
            return resources.getString(R.string.under_budget, updatedAmount * -1);
        }
    }

    private int calculateAmount(int expenditureToAdd) {
        int updatedAmount = 0;
        try {
            int dailyBudget = sharedPreferences.getInt("budget", defaultDailyBudget);
            int currentAmount = sharedPreferences.getInt("amount", dailyBudget * -1);
            int dailyBudgetToAdd = getNumberOfDaysChangesSinceLastExecution() * dailyBudget;
            updatedAmount = currentAmount - dailyBudgetToAdd + expenditureToAdd;
            if (updatedAmount < dailyBudget * -1) {
                updatedAmount = dailyBudget * -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return updatedAmount;
    }

    private int getNumberOfDaysChangesSinceLastExecution() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar now = Calendar.getInstance();

        String previousTimestamp = sharedPreferences.getString("previousTimestamp", simpleDateFormat.format(now.getTime()));
        Calendar previous = Calendar.getInstance();
        previous.setTime(simpleDateFormat.parse(previousTimestamp));

        Calendar original = Calendar.getInstance();
        original.setTime(now.getTime());

        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        int numberOfDayChanges = 0;
        if (now.after(previous)) {
            now.add(Calendar.DAY_OF_MONTH, -1);
            numberOfDayChanges++;
            while (now.after(previous)) {
                now.add(Calendar.DAY_OF_MONTH, -1);
                numberOfDayChanges++;
            }
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("previousTimestamp", simpleDateFormat.format(original.getTime().getTime()));
        editor.apply();

        return numberOfDayChanges;
    }
}
