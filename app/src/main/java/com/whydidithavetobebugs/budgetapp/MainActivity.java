package com.whydidithavetobebugs.budgetapp;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static int defaultDailyBudget = 18;
    static final String DAILY_BUDGET_ID = "com.whydidithavetobebugs.budgetapp.DAILY_BUDGET_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        toolbar.showOverflowMenu();
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorText));

        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", MODE_PRIVATE);
        setTotalAmount(sharedPreferences, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings_button) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra(DAILY_BUDGET_ID, defaultDailyBudget);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int getNumberOfDaysChangesSinceLastExecution(SharedPreferences sharedPreferences) throws ParseException {
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
        editor.commit();

        return numberOfDayChanges;
    }

    private int calculateAmount(SharedPreferences sharedPreferences, int expenditureToAdd) {
        int updatedAmount = 0;
        try {
            int dailyBudget = sharedPreferences.getInt("budget", defaultDailyBudget);
            int currentAmount = sharedPreferences.getInt("amount", dailyBudget * -1);
            int dailyBudgetToAdd = getNumberOfDaysChangesSinceLastExecution(sharedPreferences) * dailyBudget;
            updatedAmount = currentAmount - dailyBudgetToAdd + expenditureToAdd;
            if (updatedAmount < dailyBudget * -1) {
                updatedAmount = dailyBudget * -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return updatedAmount;
    }

    private void setTotalAmount(SharedPreferences sharedPreferences, int expenditureToAdd) {
        int updatedAmount = calculateAmount(sharedPreferences, expenditureToAdd);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("amount", updatedAmount);
        editor.commit();

        String budgetText;
        if (updatedAmount > 0) {
            budgetText = getResources().getString(R.string.over_budget, updatedAmount);
        } else {
            budgetText = getResources().getString(R.string.under_budget, updatedAmount * -1);
        }

        TextView textView = (TextView) findViewById(R.id.current_amount);
        textView.setText(budgetText);
    }

    public void submitTransaction(View view) {
        EditText editText = (EditText) findViewById(R.id.transaction_amount);
        String message = editText.getText().toString();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Prefs", MODE_PRIVATE);

        setTotalAmount(sharedPreferences, Integer.parseInt(message));

        editText.setText("");
    }
}
