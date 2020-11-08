package com.example.cs160_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    //the 4 picture choice buttons
    private FrameLayout topLeftChoice;
    private FrameLayout topRightChoice;
    private FrameLayout bottomLeftChoice;
    private FrameLayout bottomRightChoice;

    //the 3 choice images that change
    private ImageView topLeftImage;
    private ImageView topRightImage;
    private ImageView bottomLeftImage;

    //the tabs
    private ImageView createTab;
    private ImageView listenTab;

    //To store our 4 generated story pictures
    HashSet<Drawable> storyImageSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topLeftChoice = findViewById(R.id.topLeftChoice);
        topRightChoice = findViewById(R.id.topRightChoice);
        bottomLeftChoice = findViewById(R.id.bottomLeftChoice);
        bottomRightChoice = findViewById(R.id.bottomRightChoice);

        topLeftImage = findViewById(R.id.topLeftImage);
        topRightImage = findViewById(R.id.topRightImage);
        bottomLeftImage = findViewById(R.id.bottomLeftImage);

        createTab = findViewById(R.id.selectedBookTab);
        listenTab = findViewById(R.id.unselectedEarTab);

        storyImageSet = new HashSet<Drawable>();

        //TODO: Set the first 3 images

        //On click functions for 4 choices
        topLeftChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                storyImageSet.add(topLeftImage.getDrawable());
                //TODO: generate the next 3 story images and place in hashmap
                Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
                //intent.putExtra( "imageSet", storyImageSet);

                //TODO:figure out how to pass drawable, possible way using bitmap
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), topLeftImage.getDraw);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                byte[] b = baos.toByteArray();
                intent.putExtra("temp_img", topLeftImage.getId());
                startActivity(intent);
            }
        });

        topRightChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                storyImageSet.add(topRightImage.getDrawable());
                //TODO: generate the next 3 story images and place in hashmap
                Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
                //intent.putExtra( "imageSet", storyImageSet);
                //TODO:figure out how to pass drawable, possible way using bitmap
                startActivity(intent);
            }
        });

        bottomLeftChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                storyImageSet.add(bottomLeftImage.getDrawable());
                //TODO: generate the next 3 story images and place in hashmap
                Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
                //intent.putExtra( "imageSet", storyImageSet);
                //TODO:figure out how to pass drawable, possible way using bitmap
                startActivity(intent);
            }
        });

        bottomRightChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: allow user to choose own 4 images, place these in hashmap
                Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
                //intent.putExtra( "imageSet", storyImageSet);
                //TODO:figure out how to pass drawable, possible way using bitmap
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

//Frontend Meeting topics:
//1. Should we still have a pause button/restart option since we're screen-recording continuously?
//2. Footer tab icons, difference not very noticeable when selected/unselected
//3. I've been exporting the square buttons from figma, should we create a drawable instead?

//Frontend tasks to do:
//1. Swiping to change image pages
//2. Recording time counter/(Start recording?)
//3. The End Page & Type Name and Title Page
    //Remove audio recording the title
    //Make default the date
    //Optional to type in title
    //Maybe change "title" to "what story is this"
//4. Listen Page
//5. Create status bar drawable and implement the different colors per page/find way to track page?
//6. New icons, possibly paper airplane for share
