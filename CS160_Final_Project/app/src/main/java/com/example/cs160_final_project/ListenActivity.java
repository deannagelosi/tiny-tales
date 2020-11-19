package com.example.cs160_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ListenActivity extends AppCompatActivity {

    //footer tab
    private ImageView createTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);

        createTab = findViewById(R.id.createTab);

        createTab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ListenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}