package com.whydidithavetobebugs.budgetapp.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.whydidithavetobebugs.budgetapp.R;
import com.whydidithavetobebugs.budgetapp.engine.BudgetEngine;

public class MainActivity extends AppCompatActivity {
    private static int defaultDailyBudget = 18;
    static final String DAILY_BUDGET_ID = "com.whydidithavetobebugs.budgetapp.DAILY_BUDGET_ID";
    private BudgetEngine budgetEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        toolbar.showOverflowMenu();
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorText));

        budgetEngine = new BudgetEngine(getSharedPreferences("Prefs", MODE_PRIVATE), defaultDailyBudget, getResources());
        setTotalAmount(0);
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

    public void submitTransaction(View view) {
        EditText editText = (EditText) findViewById(R.id.transaction_amount);
        String message = editText.getText().toString();

        setTotalAmount(Integer.parseInt(message));

        editText.setText("");
    }

    private void setTotalAmount(int transactionAmount) {
        String budgetText = budgetEngine.setTotalAmount(transactionAmount);
        TextView textView = (TextView) findViewById(R.id.current_amount);
        textView.setText(budgetText);
    }
}
