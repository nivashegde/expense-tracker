package com.csulb.expensetracker.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.csulb.expensetracker.service.UserData;
import com.csulb.expensetracker.MainActivity;
import com.csulb.quiztion.R;

public class EditProfile extends AppCompatActivity {

    private EditText company;
    private EditText phone;
    private EditText name;

    private UserData dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        dataBaseHandler = new UserData(getApplicationContext());

        company = findViewById(R.id.company);
        phone = findViewById(R.id.phoneid);
        name = findViewById(R.id.nameuser);
        company.setText(dataBaseHandler.getCompany());
        phone.setText(dataBaseHandler.getPhone());
        name.setText(dataBaseHandler.getName());
    }

    /*validates and makes a request*/
    public void save(View view)
    {
        Boolean flag = true;
        if(company.getText().toString().isEmpty()){
            company.setError("Enter Valid Company name!");
            flag = false;
        }
        if(name.getText().toString().isEmpty()){
            name.setError("Enter Valid Name!");
            flag = false;
        }
        if(phone.getText().toString().isEmpty() || phone.getText().toString().length()!=10) {
            phone.setError("Enter Valid Phone number!");
            flag = false;
        }
        if(flag)
        {
            dataBaseHandler.update(dataBaseHandler.getEmail(), phone.getText().toString(), company.getText().toString(), name.getText().toString());
            Toast.makeText(getApplicationContext(),"Details changed",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}
