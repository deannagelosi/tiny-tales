package com.example.cs160_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListenActivity extends AppCompatActivity {

    //footer tab
    private ImageView createTab;

    private RecyclerView videoGrid;

    public static RelativeLayout renamePopup;
    public static RelativeLayout confirmationPopup;
    public static CardView confirmButton;
    public static CardView cancelButton;
    public static EditText titleEditText;
    public static Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);

        createTab = findViewById(R.id.createTab);
        videoGrid = findViewById(R.id.videoGrid);
        renamePopup = findViewById(R.id.renamePopup);
        confirmationPopup = findViewById(R.id.confirmationPopup);
        confirmButton = findViewById(R.id.confirm);
        cancelButton = findViewById(R.id.cancel);
        titleEditText = findViewById(R.id.titleTextEdit);
        doneButton = findViewById(R.id.doneButton);

        videoGrid.setAdapter(new ListenPageRecycleViewAdapter(this, RecordingActivity.savedVideosList));
        videoGrid.setLayoutManager(new GridLayoutManager(ListenActivity.this, 2));

        createTab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ListenActivity.this, LandingPageActivity.class);
                startActivity(intent);
            }
        });
    }
}