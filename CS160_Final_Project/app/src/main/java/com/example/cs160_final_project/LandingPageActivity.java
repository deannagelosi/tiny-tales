package com.example.cs160_final_project;

import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
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
        topLeftImage.setImageBitmap(scaleCenterCrop(allStoriesList.get(0).get(0), 170, 170));
        topRightImage.setImageBitmap(scaleCenterCrop(allStoriesList.get(1).get(0), 170, 170));
        bottomLeftImage.setImageBitmap(scaleCenterCrop(allStoriesList.get(2).get(0), 170, 170));


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

    private Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        // Compute the scaling factors to fit the new height and width, respectively.
        // To cover the final image, the final scaling will be the bigger
        // of these two.
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        // Now get the size of the source bitmap when scaled
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        // Let's find out the upper left coordinates if the scaled bitmap
        // should be centered in the new size give by the parameters
        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        // The target rectangle for the new, scaled version of the source bitmap will now
        // be
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        // Finally, we create a new bitmap of the specified size and draw our new,
        // scaled bitmap onto it.
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(source, null, targetRect, null);

        return dest;
    }
}

//Frontend tasks to do:
//1. Dynamic Recycler View with Cardviews on Listen page
//3. Using viewpager to have animated page swipes