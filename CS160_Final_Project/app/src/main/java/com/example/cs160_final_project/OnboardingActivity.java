package com.example.cs160_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class OnboardingActivity extends AppCompatActivity {

    RelativeLayout page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        page = findViewById(R.id.page);

        page.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OnboardingActivity.this, LandingPageActivity.class);
                startActivity(intent);
            }
        });
    }
}