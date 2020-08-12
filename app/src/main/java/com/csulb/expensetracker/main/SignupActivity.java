package com.csulb.expensetracker.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.csulb.expensetracker.service.UserData;
import com.csulb.quiztion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText phone;
    private EditText password_retype;
    private Button signupBtn;
    private EditText company;
    private EditText name;

    private FirebaseAuth mAuth;

    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupBtn = (Button)findViewById(R.id.signup_btn);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.usernameid, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.passwordid, "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.retypeid, "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.phoneid, Patterns.PHONE, R.string.phoneerror);

        mAuth = FirebaseAuth.getInstance();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //If validation is successful then proceed
                if(awesomeValidation.validate()){
                    username = findViewById(R.id.usernameid);
                    password = findViewById(R.id.passwordid);
                    password_retype = findViewById(R.id.retypeid);
                    phone = findViewById(R.id.phoneid);
                    company = findViewById(R.id.company);
                    name = findViewById(R.id.nameuser);

                    if(!password_retype.getText().toString().equals(password.getText().toString())){
                        Toast.makeText(SignupActivity.this, "Passwords don't match!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        createAccount(username.getText().toString(), password.getText().toString(), phone.getText().toString());
                    }

                }
            }
        });
    }

    private void createAccount(String e_mail, String password_f, String phone_f) {

        final String email = e_mail;
        final String password = password_f;
        final String phone = phone_f;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(SignupActivity.this, "Signup successful!",
                                    Toast.LENGTH_SHORT).show();

                            UserData DBH=new UserData(getApplicationContext());
                            DBH.onInsert(email+"",email,email, phone, company.getText().toString(), name.getText().toString());

                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(SignupActivity.this, "Signup failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}