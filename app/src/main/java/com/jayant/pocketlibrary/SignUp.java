package com.jayant.pocketlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private EditText signUpName, signUpEmail, signUpPassword, signUpConPassword;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpName = findViewById(R.id.signup_name);
        signUpEmail = findViewById(R.id.signup_email);
        signUpPassword = findViewById(R.id.signup_password);
        signUpConPassword = findViewById(R.id.signup_con_pasword);
        signUpBtn = findViewById(R.id.sign_up_btn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = signUpName.getText().toString();
                String email = signUpEmail.getText().toString();
                String password = signUpPassword.getText().toString();
                String conPassword = signUpConPassword.getText().toString();

                if(name.length() == 0) {
                    signUpName.setError("Name is required");
                    signUpName.requestFocus();
                    return;
                }

                if(email.length() == 0) {
                    signUpEmail.setError("Email is required");
                    signUpEmail.requestFocus();
                    return;
                }

                if(password.length() == 0) {
                    signUpPassword.setError("Password is required");
                    signUpPassword.requestFocus();
                    return;
                }

                if(conPassword.length() == 0) {
                    signUpConPassword.setError("Confirm password is required");
                    signUpConPassword.requestFocus();
                    return;
                }

                if (password.compareTo(conPassword) != 0) {
                    signUpConPassword.setError("Confirm password doesn't match");
                    signUpConPassword.requestFocus();
                    return;
                }

                Toast.makeText(SignUp.this, "Data verified", Toast.LENGTH_SHORT).show();


            }
        });

    }
}