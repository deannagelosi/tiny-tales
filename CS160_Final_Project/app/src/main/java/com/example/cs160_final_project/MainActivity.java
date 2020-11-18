package com.example.cs160_final_project;

import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ImageView addPhotosButton;
    private ImageView refreshButton;
    private ImageView infoButton;

    //the 4 picture choice buttons
    private CardView topLeftChoice;
    private CardView topRightChoice;
    private CardView bottomLeftChoice;
    private CardView bottomRightChoice;

    //the 3 choice images that change
    private ImageView topLeftImage;
    private ImageView topRightImage;
    private ImageView bottomLeftImage;

    //the tabs
    private ImageView createTab;
    private ImageView listenTab;

    //To store our 4 arraylists filled with the corresponding story pictures used in recording
    public static ArrayList<ArrayList<Integer>> allStoriesList;

    //individual story arraylists
    private ArrayList<Integer> topLeftStoryList;
    private ArrayList<Integer> topRightStoryList;
    private ArrayList<Integer> bottomLeftStoryList;
    private ArrayList<Integer> bottomRightStoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addPhotosButton = findViewById(R.id.addPhotoButton);
        refreshButton = findViewById(R.id.refreshButton);
        infoButton = findViewById(R.id.infoButton);

        topLeftChoice = findViewById(R.id.topLeftChoice);
        topRightChoice = findViewById(R.id.topRightChoice);
        bottomLeftChoice = findViewById(R.id.bottomLeftChoice);
        bottomRightChoice = findViewById(R.id.bottomRightChoice);

        topLeftImage = findViewById(R.id.topLeftImage);
        topRightImage = findViewById(R.id.topRightImage);
        bottomLeftImage = findViewById(R.id.bottomLeftImage);

        createTab = findViewById(R.id.createTab);
        listenTab = findViewById(R.id.listenTab);

        allStoriesList = new ArrayList<ArrayList<Integer>>();

        topLeftStoryList = new ArrayList<Integer>();
        topRightStoryList = new ArrayList<Integer>();
        bottomLeftStoryList = new ArrayList<Integer>();
        bottomRightStoryList = new ArrayList<Integer>();

        allStoriesList.add(topLeftStoryList);
        allStoriesList.add(topRightStoryList);
        allStoriesList.add(bottomLeftStoryList);
        allStoriesList.add(bottomRightStoryList);

        //TODO: Generate all 4 stories, set tag, and add to arraylist, probably make this a function
        //temporarily hardcoding in the top left story
        allStoriesList.get(0).add(R.drawable.story_filler_1);
        allStoriesList.get(0).add(R.drawable.story_filler_2);
        allStoriesList.get(0).add(R.drawable.story_filler_3);
        allStoriesList.get(0).add(R.drawable.story_filler_4);

        //TODO: Display the first 3 images's

        addPhotosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: open up camera row select and import pictures
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: refresh 3 visible images
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: show onboarding page
            }
        });

        //On click functions for 4 choices
        topLeftChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
                intent.putExtra("story-index", 0);
                startActivity(intent);
            }
        });

        topRightChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
                intent.putExtra("story-index", 1);
                startActivity(intent);            }
        });

        bottomLeftChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
                intent.putExtra("story-index", 2);
                startActivity(intent);            }
        });

        bottomRightChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
                intent.putExtra("story-index", 3);
                startActivity(intent);            }
        });


        listenTab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: 
            }
        });
    }
}

//Frontend tasks to do:
//1. Make title popup
//2. Make Listen Page
//3. Splash Page
//4. Onboarding page
// If we have timer
//1. Using viewpager to have animated page swipes
//2. Pause countdown timer when home/stop recording is pressed