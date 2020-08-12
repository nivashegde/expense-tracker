package com.csulb.expensetracker.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.csulb.expensetracker.service.UserData;
import com.csulb.expensetracker.MainActivity;
import com.csulb.expensetracker.service.IncomeData;
import com.csulb.quiztion.R;

public class SettingsActivity extends AppCompatActivity {

    private EditText annualIncome, dailyExpenses, savings, expenses;
    private Boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        annualIncome = findViewById(R.id.amount_of_annual_income);
        dailyExpenses = findViewById(R.id.amount_of_daily_expenses);
        savings = findViewById(R.id.amount_of_desired_saving);
        expenses = findViewById(R.id.expense_items);
        IncomeData settingsData=new IncomeData(getApplicationContext());
        String userid = new UserData(getApplicationContext()).getId();
        if(settingsData.getCount(userid).equals("1"))
        {
            flag = false;
            annualIncome.setText(settingsData.getAnnualIncome(userid));
            dailyExpenses.setText(settingsData.getDailyExpense(userid));
            savings.setText(settingsData.getSaving(userid));
            expenses.setText(settingsData.getk4(userid));
        }
    }

    public void store(View view)
    {
        String s1= annualIncome.getText().toString();
        String s2= dailyExpenses.getText().toString();
        String s3= savings.getText().toString();
        String s4= expenses.getText().toString();
        if(s1.equals("") || s2.equals("") || s3.equals("") || s4.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Every field is mandatory",Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            IncomeData settingsData = new IncomeData(getApplicationContext());
            String userid = new UserData(getApplicationContext()).getId();
            if(flag)
            {
                settingsData.onInsert(s1,s2,s3,s4,"1",userid);
            }
            else
            {
                settingsData.update(s1,s2,s3,s4,"1",userid);
            }
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}