package com.whydidithavetobebugs.budgetapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        int defaultDailyBudget = intent.getIntExtra(MainActivity.DAILY_BUDGET_ID, 18);

        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", MODE_PRIVATE);
        int dailyBudget = sharedPreferences.getInt("budget", defaultDailyBudget);

        TextView textView = (TextView) findViewById(R.id.budget_amount);
        textView.setText(String.valueOf(dailyBudget));
    }

    public void saveNewBudget(View view) {
        TextView textView = (TextView) findViewById(R.id.budget_amount);
        int updatedBudget = Integer.parseInt(textView.getText().toString());

        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("budget", updatedBudget);
        editor.commit();

        finish();
    }
}
