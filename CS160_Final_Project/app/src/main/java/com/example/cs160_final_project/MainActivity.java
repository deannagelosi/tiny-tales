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

        //TODO: Generate all 4 stories, set tag, and add to arraylist
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

//Frontend Discussion:
//1. I made some UI changes in figma
//      I took out the arrows on the splash and onboarding because I thought it was really crowded
//      at the top already and Janaki commented that the splash could probably be timed. For the
//      onboarding page, I added "tap to continue" at the bottom, I also changed some of the wording.

//Frontend tasks to do:
//1. Swiping to change image pages
//2. Recording time counter/(Start recording?)
//3. Make save popup (should be similar to home popup)
//4. Make title page
//4. Make Listen Page
//5. Splash Page
//6. Onboarding page