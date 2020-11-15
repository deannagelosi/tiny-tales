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

    //To store our 4 generated story pictures used in recording
    public static ArrayList<Integer> storyImageSet;

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

        storyImageSet = new ArrayList<Integer>();

        //TODO: Set the first 3 images's drawable resource tag and save them
        //temporarily using the filler images
        topLeftImage.setTag(R.drawable.filler_image_1);
        topRightImage.setTag(R.drawable.filler_image_2);
        bottomLeftImage.setTag(R.drawable.filler_image_3);

        addPhotosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: open up camera row select and import pictures
            }
        });

        //On click functions for 4 choices
        topLeftChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                storyImageSet.clear();
                //getTag gets the resourceID, so we can use Picasso
                storyImageSet.add((Integer) topLeftImage.getTag());
                //TODO: generate the next 3 story images and place in hashmap
                Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
                //TODO:figure out how to pass drawable, possible way using bitmap
                intent.putExtra("temp_img", topLeftImage.getId());
                startActivity(intent);
            }
        });

        topRightChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                storyImageSet.clear();
                storyImageSet.add((Integer) topRightImage.getTag());
                //TODO: generate the next 3 story images and place in hashmap
                Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
                startActivity(intent);
            }
        });

        bottomLeftChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                storyImageSet.clear();
                storyImageSet.add((Integer) bottomLeftImage.getTag());
                //TODO: generate the next 3 story images and place in hashmap
                Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
                startActivity(intent);
            }
        });

        bottomRightChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                storyImageSet.clear();
                //TODO: generate 4 random images, place these in hashmap
                Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
                startActivity(intent);
            }
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

//2. Footer tab icons, difference not very noticeable when selected/unselected
//3. I've been exporting the square buttons from figma, should we create a drawable instead?

//Frontend tasks to do:
//1. Swiping to change image pages
//2. Recording time counter/(Start recording?)
//3. Make save popup (should be similar to home popup)
//4. Make title page
//4. Make Listen Page
//5. Splash Page
//6. Onboarding page