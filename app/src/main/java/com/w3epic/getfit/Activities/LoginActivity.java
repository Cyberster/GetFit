package com.w3epic.getfit.Activities;

import android.content.Context;
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

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    EditText etEmail;
    EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);

        mAuth = FirebaseAuth.getInstance();

        // setting email and password if come from register activity
        String caller = getIntent().getStringExtra("caller");
        if (caller != null && caller.equals("RegisterActivity")) {
            etEmail.setText(getIntent().getStringExtra("email"));
            etPass.setText(getIntent().getStringExtra("pass"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ifLoggedInThenRedirectTo( this, HomeActivity.class );
    }

    public void btnLoginOnClick(View view) {
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,"Authentication Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this,"Authentication failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void btnRegisterOnClick(View view) {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        registerIntent.putExtra("caller", this.getClass().getSimpleName());
        registerIntent.putExtra("email", etEmail.getText().toString());
        registerIntent.putExtra("pass", etPass.getText().toString());
        startActivity(registerIntent);
    }

    private void ifLoggedInThenRedirectTo(Context context, Class targetClass) {
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) { // logged in
            startActivity(new Intent(context, targetClass));
            Toast.makeText(this, "Logged in as: " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
        }
        //updateUI(currentUser);
    }
}