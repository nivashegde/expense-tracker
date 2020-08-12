package com.csulb.expensetracker.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.csulb.expensetracker.service.ExpenseData;
import com.csulb.expensetracker.MainActivity;
import com.csulb.expensetracker.service.IncomeData;
import com.csulb.expensetracker.service.UserData;
import com.csulb.quiztion.R;

public class AddExpenseAcitivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    private Spinner spinner;
    private String expenseName="",expenseprice="";
    ArrayList<String> spinnerArray = new ArrayList<String>();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense_acitivity);
        IncomeData DBH=new IncomeData(getApplicationContext());
        UserData DBH1=new UserData(getApplicationContext());
        String userid=DBH1.getId();
        String breaker[]=DBH.getk4(userid).split(",");
        for(int i=0;i<breaker.length;i++)
        {
            spinnerArray.add(breaker[i]);
        }
        spinner = (Spinner) findViewById(R.id.expenselist);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
    }

    public void AddingExpense(View view)
    {
        EditText price=findViewById(R.id.price);
        expenseprice=price.getText().toString();
        String date=dtf.format(now).toString().split(" ")[0];
        UserData DBH1=new UserData(getApplicationContext());
        String userid=DBH1.getId();
        ExpenseData DBH=new ExpenseData(getApplicationContext());
        DBH.onInsert(expenseName,expenseprice,date,userid);
        Toast.makeText(getApplicationContext(),"Expense Added", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void checkExpense(View view){
        startActivity(new Intent(getApplicationContext(), ExpensesList.class));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int
            position, long id) {
        expenseName=spinnerArray.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}