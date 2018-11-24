package com.w3epic.getfit.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.w3epic.getfit.R;

public class ContactUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        setTitle("Contact us");
    }

    public void btnContactOnCLickHandler(View view) {
        // todo: store contact us message to firebase db
    }
}
