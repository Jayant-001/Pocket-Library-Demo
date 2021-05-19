package com.jayant.pocketlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {

    private EditText signInEmail, signInPassword;
    private TextView forgetPassword, noAccountSignUp;
    private Button signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInEmail =findViewById(R.id.signin_email);
        signInPassword = findViewById(R.id.signin_password);
        forgetPassword = findViewById(R.id.forget_password);
        signInBtn = findViewById(R.id.sign_in_btn);
        noAccountSignUp = findViewById(R.id.no_account_signup);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signInEmail.getText().toString();
                String password = signInPassword.getText().toString();


                Toast.makeText(SignIn.this, email + " : " + password, Toast.LENGTH_SHORT).show();

            }
        });

        noAccountSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });
    }
}