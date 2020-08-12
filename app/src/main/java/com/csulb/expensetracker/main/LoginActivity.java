package com.csulb.expensetracker.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csulb.expensetracker.MainActivity;
import com.csulb.expensetracker.service.IncomeData;
import com.csulb.expensetracker.navigation.SettingsActivity;
import com.csulb.expensetracker.service.UserData;
import com.csulb.quiztion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//This handles events happening in the LoginActivity
public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button signup;
    private Button login;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.log_usernameid);
        password = (EditText) findViewById(R.id.log_passwordid);
        signup = (Button) findViewById(R.id.signup_btn);
        login = (Button) findViewById(R.id.login_btn);
        //When user clicks on SignUp button he will be navigated to SignupAcctivity
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        //Validates Login credentials, Gives access to welcome activity if user exist
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                String passcode = password.getText().toString();
                if(name.isEmpty() || passcode.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter Credentials",Toast.LENGTH_SHORT).show();
                } else {
                    signIn(name, passcode);
                }
            }
        });
    }

    private void signIn(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(LoginActivity.this, "Login successful!",
                                    Toast.LENGTH_SHORT).show();

                            IncomeData settingsData = new IncomeData(getApplicationContext());
                            String userid = new UserData(getApplicationContext()).getId();
                            if(settingsData.getCount(userid).equals("1")){
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            } else {
                                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                                finish();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Login problem!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

