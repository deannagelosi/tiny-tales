package com.example.cs160_final_project;

import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class LandingPageActivity extends Activity {

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

    //footer tabs
    private ImageView listenTab;

    //To store our 4 arraylists filled with the corresponding story pictures used in recording
    public static ArrayList<ArrayList<Bitmap>> allStoriesList;

    //individual story arraylists
    private ArrayList<Bitmap> topLeftStoryList;
    private ArrayList<Bitmap> topRightStoryList;
    private ArrayList<Bitmap> bottomLeftStoryList;
    private ArrayList<Bitmap> bottomRightStoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

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

        listenTab = findViewById(R.id.listenTab);

        allStoriesList = new ArrayList<ArrayList<Bitmap>>();

        topLeftStoryList = new ArrayList<Bitmap>();
        topRightStoryList = new ArrayList<Bitmap>();
        bottomLeftStoryList = new ArrayList<Bitmap>();
        bottomRightStoryList = new ArrayList<Bitmap>();

        allStoriesList.add(topLeftStoryList);
        allStoriesList.add(topRightStoryList);
        allStoriesList.add(bottomLeftStoryList);
        allStoriesList.add(bottomRightStoryList);

        //TODO: Generate all 4 stories, set tag, and add to arraylist, probably make this a function
        //temporarily hardcoding in the top left story
        allStoriesList.get(0).add(BitmapFactory.decodeResource(this.getResources(), R.drawable.story_filler_1));
        allStoriesList.get(0).add(BitmapFactory.decodeResource(this.getResources(), R.drawable.story_filler_2));
        allStoriesList.get(0).add(BitmapFactory.decodeResource(this.getResources(),R.drawable.story_filler_3));
        allStoriesList.get(0).add(BitmapFactory.decodeResource(this.getResources(), R.drawable.story_filler_4));

        //just the first image for the 2 other stories so I can "set" it
        allStoriesList.get(1).add(BitmapFactory.decodeResource(this.getResources(), R.drawable.filler_image_2));
        allStoriesList.get(2).add(BitmapFactory.decodeResource(this.getResources(), R.drawable.filler_image_3));

        //Setting the option images
        topLeftImage.setImageBitmap(allStoriesList.get(0).get(0));
        topRightImage.setImageBitmap(allStoriesList.get(1).get(0));
        bottomLeftImage.setImageBitmap(allStoriesList.get(2).get(0));


        addPhotosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: open up camera row select and import pictures
                Intent intent = new Intent(LandingPageActivity.this, WaitActivity.class);
                startActivity(intent);
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: refresh 3 visible images
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, OnboardingActivity.class);
                startActivity(intent);
            }
        });

        //On click functions for 4 choices
        topLeftChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, RecordingActivity.class);
                intent.putExtra("story-index", 0);
                startActivity(intent);
            }
        });

        topRightChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, RecordingActivity.class);
                intent.putExtra("story-index", 1);
                startActivity(intent);
            }
        });

        bottomLeftChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, RecordingActivity.class);
                intent.putExtra("story-index", 2);
                startActivity(intent);
            }
        });

        bottomRightChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, RecordingActivity.class);
                intent.putExtra("story-index", 3);
                startActivity(intent);
            }
        });


        listenTab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, ListenActivity.class);
                startActivity(intent);
            }
        });
    }
}

//Frontend tasks to do:
//1. Dynamic Recycler View with Cardviews on Listen page
//3. Using viewpager to have animated page swipes