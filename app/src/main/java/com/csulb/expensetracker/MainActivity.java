package com.csulb.expensetracker;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.csulb.expensetracker.charts.ExpenseReportActivity;
import com.csulb.expensetracker.charts.ItemReport;
import com.csulb.expensetracker.charts.SavingsReportActivity;
import com.csulb.expensetracker.main.AddExpenseAcitivity;
import com.csulb.expensetracker.main.ExpensesList;
import com.csulb.expensetracker.main.LoginActivity;
import com.csulb.expensetracker.navigation.AboutActivity;
import com.csulb.expensetracker.navigation.ChangePasswordActivity;
import com.csulb.expensetracker.navigation.EditProfile;
import com.csulb.expensetracker.navigation.HelpActivity;
import com.csulb.expensetracker.navigation.SettingsActivity;
import com.csulb.expensetracker.service.UserData;
import com.csulb.expensetracker.service.ExpenseData;
import com.csulb.expensetracker.service.IncomeData;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.csulb.quiztion.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UserData userDbdata;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDbdata = new UserData(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);


        TextView username = (TextView) view.findViewById(R.id.userusername);
        TextView email = (TextView) view.findViewById(R.id.useremail);
        username.setText(userDbdata.getUsername());
        email.setText(userDbdata.getEmail());
        navigationView.setNavigationItemSelectedListener(this);

        TextView daily=findViewById(R.id.dailysaving),annual=findViewById(R.id.annualsaving);
        UserData userData = new UserData(getApplicationContext());
        userid = userData.getId();

        String dailySaving = calcDailySavings();
        daily.setText("Daily Savings = $"+dailySaving);
        annual.setText("Annual Savings = $"+getAnnualSavings(dailySaving));
    }

    private String getAnnualSavings(String dailySaving){
        Float saving = Float.parseFloat(dailySaving)*365;
        if(saving < 0){
            Toast.makeText(getApplicationContext(),"WARNING: Savings is going negative!",Toast.LENGTH_SHORT).show();
        }
        return String.valueOf(saving);
    }

    private String calcDailySavings(){
        IncomeData settingsData=new IncomeData(getApplicationContext());
        ExpenseData expenseData=new ExpenseData(getApplicationContext());
        float val=((float) Integer.parseInt(settingsData.getAnnualIncome(userid))/365)-Integer.parseInt(settingsData.getDailyExpense(userid));
        val = val - expenseData.getPrice(userid);
        return String.valueOf(val);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        switch(id){

            case R.id.home : intent =new Intent(this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;

            case R.id.profile : intent =new Intent(this, EditProfile.class);
                                startActivity(intent);
                                break;

            case R.id.changepassword : intent=new Intent(this, ChangePasswordActivity.class);
                                        startActivity(intent);
                                        break;

            case R.id.help : intent=new Intent(this, HelpActivity.class);
                            startActivity(intent);
                            break;

            case R.id.about : intent=new Intent(this, AboutActivity.class);
                                startActivity(intent);
                                break;
            case R.id.expensereport : intent=new Intent(this, ExpenseReportActivity.class);
                                    startActivity(intent);
                                    break;
            case R.id.savingsreport : intent=new Intent(this, SavingsReportActivity.class);
                                        startActivity(intent);
                                        break;

            case R.id.itemreport : intent=new Intent(this, ItemReport.class);
                                    startActivity(intent);
                                    break;

            case R.id.settings : intent=new Intent(this, SettingsActivity.class);
                                    startActivity(intent);
                                    break;

            case R.id.logout : userDbdata.logout();
                                intent=new Intent(this, LoginActivity.class);
                                startActivity(intent);
                                finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void gotoAddExpense(View view){
        Intent intent=new Intent(getApplicationContext(), AddExpenseAcitivity.class);
        startActivity(intent);
    }

    public void checkExpense(View view) {
        startActivity(new Intent(getApplicationContext(), ExpensesList.class));
    }
}
