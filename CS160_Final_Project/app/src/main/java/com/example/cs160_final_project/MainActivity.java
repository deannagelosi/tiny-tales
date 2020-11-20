package com.example.cs160_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.util.Log.VERBOSE;

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

    //where the images and labels will be stored
    public ArrayList<Drawable> userPictures = new ArrayList<>();
    public ArrayList<Drawable> stockPictures = new ArrayList<>();
    public ArrayList<Set> userLabels = new ArrayList<>();
    public ArrayList<Set> stockLabels = new ArrayList<>();
    public ArrayList<ArrayList<Drawable>> stories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetupStock();
        PopulateStories(stories);
        //Log.println(VERBOSE, null, "here are the list of pictures ind " + stories.toString());

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

    public void PopulateStories(ArrayList<ArrayList<Drawable>> stories) {
        stories.clear();
        for (int i = 0; i < 4; i++) {
            ArrayList<Drawable> story = Tools.Generatestories(userPictures,stockPictures,userLabels,stockLabels);
            stories.add(story);
        }
    }

    public void SetupStock() {
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
        stockPictures.add(ResourcesCompat.getDrawable(getResources(), R.drawable.candy, null));

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
        stockPictures.add(ResourcesCompat.getDrawable(getResources(), R.drawable.lion, null));

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
        stockPictures.add(ResourcesCompat.getDrawable(getResources(), R.drawable.duck, null));

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
        stockPictures.add(ResourcesCompat.getDrawable(getResources(), R.drawable.frog, null));

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
        stockPictures.add(ResourcesCompat.getDrawable(getResources(), R.drawable.snow, null));
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

