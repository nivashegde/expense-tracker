package com.csulb.expensetracker.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.csulb.expensetracker.service.UserData;
import com.csulb.quiztion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
    }

    public void forgotaction(View view) {
        UserData dataBaseHandler = new UserData(getApplicationContext());
        FirebaseAuth.getInstance().sendPasswordResetEmail(dataBaseHandler.getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Password Reset link sent to e-mail!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
