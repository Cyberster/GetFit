package com.w3epic.getfit.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.w3epic.getfit.Models.DBEntities.User;
import com.w3epic.getfit.R;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    EditText etName;
    EditText etEmail;
    EditText etPhone;
    EditText etCity;
    EditText etHeight;
    EditText etWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etCity = findViewById(R.id.etCity);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // read data from Firebase database start @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        DatabaseReference mUserReference = mDatabase.child("user").child(currentUser.getUid());
        mUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    // Get User object and use the values to update the UI
                    User user = dataSnapshot.getValue(User.class);
                    //Log.d("test", "Read: " + (user != null ? user.toString() : ""));
                    etName.setText(user.getName());
                    etEmail.setText(currentUser.getEmail());
                    etEmail.setEnabled(false);
                    etPhone.setText(user.getPhone());
                    etCity.setText(user.getCity());
                    etHeight.setText(user.getHeight_cm());
                    etWeight.setText(user.getWeight_kg());
                } catch (Exception e) {
                    Log.d("test", e.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting User failed, log a message
                Log.w("test", "loadPost:onCancelled", databaseError.toException());
            }
        });
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // read data from Firebase database end @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    public void btnSaveOnClickHandler(View view) {
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // update data in Firebase database start @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // updating age in user table
        String userId = currentUser.getUid();
        DatabaseReference mUserRef = mDatabase.child("user").child(userId);
        mUserRef.child("name").setValue(etName.getText().toString());
        mUserRef.child("email").setValue(etEmail.getText().toString());
        mUserRef.child("phone").setValue(etPhone.getText().toString());
        mUserRef.child("city").setValue(etCity.getText().toString());
        mUserRef.child("height_cm").setValue(etHeight.getText().toString());
        mUserRef.child("weight_kg").setValue(etWeight.getText().toString());
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // update data in Firebase database end @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }
}
