package com.example.cs160_final_project;

import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

// BEGIN BACKEND IMPORTS (some might not be needed)
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.FrameLayout;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import static android.util.Log.VERBOSE;
// END BACKEND IMPORTS

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
    public static ArrayList<ArrayList<Integer>> allStoriesList;

    //individual story arraylists
    private ArrayList<Integer> topLeftStoryList;
    private ArrayList<Integer> topRightStoryList;
    private ArrayList<Integer> bottomLeftStoryList;
    private ArrayList<Integer> bottomRightStoryList;

    // BEGIN BACKEND: where the images and labels will be stored
    public ArrayList<Bitmap> userPictures = new ArrayList<>();
    public ArrayList<Bitmap> stockPictures = new ArrayList<>();
    public ArrayList<Set> userLabels = new ArrayList<>();
    public ArrayList<Set> stockLabels = new ArrayList<>();
    public ArrayList<ArrayList<Bitmap>> stories = new ArrayList<>();
    // END BACKEND

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupStock();
        populateStories(stories);
        Log.println(VERBOSE, "debug", "here are the list of pictures ind " + stories.toString());

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
                //TODO: show onboarding page
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
                startActivity(intent);            }
        });

        bottomLeftChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, RecordingActivity.class);
                intent.putExtra("story-index", 2);
                startActivity(intent);            }
        });

        bottomRightChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, RecordingActivity.class);
                intent.putExtra("story-index", 3);
                startActivity(intent);            }
        });


        listenTab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, ListenActivity.class);
                startActivity(intent);
            }
        });
    }
    
    // BACKEND: Generate content in this.stories (called once by onCreate)
    private void populateStories(ArrayList<ArrayList<Bitmap>> stories) {
        stories.clear();
        for (int i = 0; i < 4; i++) {
            ArrayList<Bitmap> story = Tools.generateStories(userPictures,stockPictures,userLabels,stockLabels);
            stories.add(story);
        }
    }

    // BACKEND: Add labels to stock images
    private void setupStock() {
        Bitmap bm;
        Set<String> label;

        label = new HashSet<>();
        label.add("Food");
        label.add("Meal");
        label.add("Cuisine");
        label.add("Dish");
        label.add("Ingredient");
        label.add("Convenience Food");
        label.add("Food Group");
        label.add("Lunch");
        label.add("Prepackaged Meal");
        label.add("Junk Food");
        label.add("Natural Foods");
        label.add("Produce");
        label.add("Take-out Food");
        label.add("Confectionery");
        label.add("Comfort Food");
        label.add("Vegetarian Food");
        label.add("Sweetness");
        label.add("Vegetable");
        label.add("Finger Food");
        label.add("Vegan Nutrition");
        label.add("Fast Food");
        label.add("Hawaiian Food");
        label.add("Snack");
        stockLabels.add(label);
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.candy);
        stockPictures.add(bm);

        label = new HashSet<>();
        label.add("Facial Expression");
        label.add("Wildlife");
        label.add("Lion");
        label.add("Felidae");
        label.add("Carnivore");
        label.add("Big Cats");
        label.add("Snout");
        label.add("Fun");
        label.add("Roar");
        label.add("Adaptation");
        label.add("Masai Lion");
        label.add("Fawn");
        label.add("Happy");
        label.add("Yawn");
        label.add("Hug");
        label.add("Laugh");
        stockLabels.add(label);
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.lion);
        stockPictures.add(bm);

        label = new HashSet<>();
        label.add("Duck");
        label.add("Bird");
        label.add("Vertebrate");
        label.add("Water Bird");
        label.add("Waterfowl");
        label.add("Ducks, Geese And Swans");
        label.add("Beak");
        label.add("Mallard");
        label.add("Water");
        label.add("American Black Duck");
        label.add("Seaduck");
        label.add("Wildlife");
        label.add("Pied Billed Grebe");
        label.add("Goose");
        label.add("Ruddy Duck");
        label.add("Wing");
        label.add("Pond");
        label.add("Feather");
        stockLabels.add(label);
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.duck);
        stockPictures.add(bm);

        label = new HashSet<>();
        label.add("Green");
        label.add("Water");
        label.add("Frog");
        label.add("Organism");
        label.add("True Frog");
        label.add("Amphibian");
        label.add("Eye");
        label.add("Adaptation");
        label.add("Macro Photography");
        label.add("Aquatic Plant");
        label.add("Plant");
        label.add("Photography");
        label.add("Wildlife");
        label.add("Toad");
        stockLabels.add(label);
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.frog);
        stockPictures.add(bm);

        label = new HashSet<>();
        label.add("Snow");
        label.add("Geological Phenomenon");
        label.add("Winter");
        label.add("Mountainous Landforms");
        label.add("Glacial Landform");
        label.add("Mountain");
        label.add("Mountain Range");
        label.add("Ice Cap");
        label.add("Nunatak");
        label.add("Recreation");
        label.add("Ridge");
        label.add("Adventure");
        label.add("Glacier");
        label.add("Vehicle");
        label.add("Ice");
        label.add("Mountaineering");
        label.add("Alps");
        label.add("Arctic");
        label.add("Winter Sport");
        label.add("Terrain");
        label.add("Massif");
        label.add("Summit");
        label.add("Landscape");
        label.add("Fell");
        label.add("Freezing");
        label.add("Mountaineer");
        label.add("Mountain Guide");
        label.add("Ski Mountaineering");
        label.add("Tourism");
        stockLabels.add(label);
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.snow);
        stockPictures.add(bm);
    }
}

//Frontend tasks to do:
//1. Dynamic Recycler View with Cardviews on Listen page
//2. Splash Page
//3. Onboarding page
//4. Using viewpager to have animated page swipes
