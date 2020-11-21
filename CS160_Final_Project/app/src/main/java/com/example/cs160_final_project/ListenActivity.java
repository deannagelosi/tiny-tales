package com.example.cs160_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ListenActivity extends AppCompatActivity {

    //footer tab
    private ImageView createTab;

    private RecyclerView videoGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);

        createTab = findViewById(R.id.createTab);
        videoGrid = findViewById(R.id.videoGrid);

        videoGrid.setAdapter(new ListenPageRecycleViewAdapter(RecordingActivity.savedVideosList));
        videoGrid.setLayoutManager(new GridLayoutManager(ListenActivity.this, 2));

        createTab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ListenActivity.this, LandingPageActivity.class);
                startActivity(intent);
            }
        });
    }
}