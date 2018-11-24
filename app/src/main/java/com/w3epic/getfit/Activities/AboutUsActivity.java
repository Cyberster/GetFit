package com.w3epic.getfit.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.w3epic.getfit.R;

public class AboutUsActivity extends AppCompatActivity {

    TextView tvAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setTitle("About us");

        tvAboutUs = findViewById(R.id.tvAboutUs);
        tvAboutUs.setMovementMethod(new ScrollingMovementMethod()); // to enable scroll
    }

    public void btnContactUsOnClickHandler(View view) {
        startActivity(new Intent(AboutUsActivity.this, ContactUsActivity.class));
    }
}
