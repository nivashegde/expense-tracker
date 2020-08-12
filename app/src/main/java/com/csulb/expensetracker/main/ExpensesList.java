package com.csulb.expensetracker.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.csulb.expensetracker.service.ExpenseData;
import com.csulb.expensetracker.service.UserData;
import com.csulb.quiztion.R;

import java.util.ArrayList;
import java.util.List;

public class ExpensesList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_list);

        List<String> list = new ArrayList<>();
        ExpenseData expenseData = new ExpenseData(getApplicationContext());
        UserData userData = new UserData(getApplicationContext());
        String userid = userData.getId();
        String temp[]=expenseData.getitemized(userid).split(",");
        for(int i=0;i<temp.length;i++){
            int pri=Integer.parseInt(temp[i]);
            String itemname=temp[++i];
            list.add(itemname + " - $" +pri);
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, list);

        ListView listView = (ListView) findViewById(R.id.expense_list);
        listView.setAdapter(adapter);
    }
}