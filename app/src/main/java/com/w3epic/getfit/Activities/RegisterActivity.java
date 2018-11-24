package com.w3epic.getfit.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.w3epic.getfit.R;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText etEmail;
    EditText etPass;
    EditText etConfPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        etConfPass = findViewById(R.id.etConfPass);

        mAuth = FirebaseAuth.getInstance();

        // setting email and password if come from main activity
        String caller = getIntent().getStringExtra("caller");
        if (caller != null && caller.equals("HomeActivity")) {
            etEmail.setText(getIntent().getStringExtra("email"));
            etPass.setText(getIntent().getStringExtra("pass"));
        }

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
            Toast.makeText(this, "Logged in as: " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
        }
        //updateUI(currentUser);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void btnRegisterOnClick(View view) {
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        String confPass = etConfPass.getText().toString();

        if (!pass.equals(confPass)) {
            String errorMsg = "Password and confirm password doesn't match!";
            Toast.makeText(RegisterActivity.this, errorMsg, Toast.LENGTH_LONG).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this,"Registration Successful", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this,"Registration failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public void btnLoginOnClick(View view) {
        Intent mainIntent = new Intent(this, LoginActivity.class);
        mainIntent.putExtra("caller", this.getClass().getSimpleName());
        mainIntent.putExtra("email", etEmail.getText().toString());
        mainIntent.putExtra("pass", etPass.getText().toString());
        startActivity(mainIntent);
    }
}
