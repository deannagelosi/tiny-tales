package com.example.cs160_final_project;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

// BEGIN BACKEND IMPORTS (some might not be needed)
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
    // public static ArrayList<ArrayList<Bitmap>> allStoriesList;

    //individual story arraylists
    private ArrayList<Bitmap> topLeftStoryList;
    private ArrayList<Bitmap> topRightStoryList;
    private ArrayList<Bitmap> bottomLeftStoryList;
    private ArrayList<Bitmap> bottomRightStoryList;

    // BEGIN BACKEND: where the images and labels will be stored
    public ArrayList<Bitmap> userPictures = new ArrayList<>();
    public ArrayList<Bitmap> stockPictures = new ArrayList<>();
    public ArrayList<Set> userLabels = new ArrayList<>();
    public ArrayList<Set> stockLabels = new ArrayList<>();
    public static ArrayList<ArrayList<Bitmap>> allStoriesList = new ArrayList<>();
    // END BACKEND

    // BEGIN BACKEND: CV instance variables
    private static final String CLOUD_VISION_API_KEY = BuildConfig.API_KEY;
    public static final String FILE_NAME = "temp.jpg";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private static final int MAX_LABEL_RESULTS = 10;
    private static final int MAX_DIMENSION = 1200;

    private static final String TAG = LandingPageActivity.class.getSimpleName();
    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_IMAGE_REQUEST = 3;
    // END BACKEND



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        // BEGIN BACKEND: Setup stories, images
        setupStock();
        populateStories();
        // END BACKEND

        addPhotosButton = findViewById(R.id.addPhotoButton);
        refreshButton = findViewById(R.id.refreshButton);
        infoButton = findViewById(R.id.infoButton);

        // BEGIN BACKEND: button
        addPhotosButton.setOnClickListener(view -> {
            Log.println(VERBOSE, "debug", "Opening gallery prompt");
            AlertDialog.Builder builder = new AlertDialog.Builder(LandingPageActivity.this);
            builder
                    .setMessage(R.string.dialog_select_prompt)
                    .setPositiveButton(R.string.dialog_select_gallery, (dialog, which) -> startGalleryChooser())
                    .setNegativeButton(R.string.dialog_select_camera, (dialog, which) -> startCamera());
            builder.create().show();
        });
        // END BACKEND

        topLeftChoice = findViewById(R.id.topLeftChoice);
        topRightChoice = findViewById(R.id.topRightChoice);
        bottomLeftChoice = findViewById(R.id.bottomLeftChoice);
        bottomRightChoice = findViewById(R.id.bottomRightChoice);

        topLeftImage = findViewById(R.id.topLeftImage);
        topRightImage = findViewById(R.id.topRightImage);
        bottomLeftImage = findViewById(R.id.bottomLeftImage);

        listenTab = findViewById(R.id.listenTab);

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


        /* TODO: remove this later
        addPhotosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: open up camera row select and import pictures
                Intent intent = new Intent(LandingPageActivity.this, WaitActivity.class);
                startActivity(intent);
            }
        });*/

        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: refresh 3 visible images
                populateStories();
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

    // BEGIN CV
    public void startGalleryChooser() {
        if (PermissionUtils.requestPermission(this, GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                    GALLERY_IMAGE_REQUEST);
        }
    }

    public void startCamera() {
        if (PermissionUtils.requestPermission(
                this,
                CAMERA_PERMISSIONS_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
        }
    }

    public File getCameraFile() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(dir, FILE_NAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            uploadImage(data.getData());
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            uploadImage(photoUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) {
                    startCamera();
                }
                break;
            case GALLERY_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, GALLERY_PERMISSIONS_REQUEST, grantResults)) {
                    startGalleryChooser();
                }
                break;
        }
    }

    public void uploadImage(Uri uri) {
        if (uri != null) {
            try {
                // scale the image to save on bandwidth
                Bitmap bitmap =
                        scaleBitmapDown(
                                MediaStore.Images.Media.getBitmap(getContentResolver(), uri),
                                MAX_DIMENSION);

                callCloudVision(bitmap);
                userPictures.add(bitmap);
                //mMainImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }
    }

    private Vision.Images.Annotate prepareAnnotationRequest(final Bitmap bitmap) throws IOException {
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        VisionRequestInitializer requestInitializer =
                new VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                    /**
                     * We override this so we can inject important identifying fields into the HTTP
                     * headers. This enables use of a restricted cloud platform API key.
                     */
                    @Override
                    protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                            throws IOException {
                        super.initializeVisionRequest(visionRequest);

                        String packageName = getPackageName();
                        visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                        String sig = PackageManagerUtils.getSignature(getPackageManager(), packageName);

                        visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                    }
                };

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);

        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

            // Add the image
            Image base64EncodedImage = new Image();
            // Convert the bitmap to a JPEG
            // Just in case it's a format that Android understands but Cloud Vision
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Base64 encode the JPEG
            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);

            // add the features we want
            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature labelDetection = new Feature();
                labelDetection.setType("LABEL_DETECTION");
                labelDetection.setMaxResults(MAX_LABEL_RESULTS);
                add(labelDetection);
            }});

            // Add the list of one thing to the request
            add(annotateImageRequest);
        }});

        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotateRequest.setDisableGZipContent(true);
        Log.d(TAG, "created Cloud Vision request object, sending request");

        return annotateRequest;
    }

    private static class LabelDetectionTask extends AsyncTask<Object, Void, String> {
        private final WeakReference<LandingPageActivity> mActivityWeakReference;
        private Vision.Images.Annotate mRequest;

        LabelDetectionTask(LandingPageActivity activity, Vision.Images.Annotate annotate) {
            mActivityWeakReference = new WeakReference<>(activity);
            mRequest = annotate;
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                Log.d(TAG, "created Cloud Vision request object, sending request");
                BatchAnnotateImagesResponse response = mRequest.execute();
                return convertResponseToString(response);

            } catch (GoogleJsonResponseException e) {
                Log.d(TAG, "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                Log.d(TAG, "failed to make API request because of other IOException " +
                        e.getMessage());
            }
            return "Cloud Vision API request failed. Check logs for details.";
        }

        protected void onPostExecute(String result) {
            LandingPageActivity activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                HashSet<String> labelSet = new HashSet<>();
                String[] labels = result.split(", ");
                for (String label : labels) {
                    labelSet.add(label);
                }
                activity.userLabels.add(labelSet);
                activity.populateStories();
                //TextView imageDetail = activity.findViewById(R.id.image_details);
                //imageDetail.setText(result);
                //Log.d(TAG, result);
                Log.d("debug", "Labels detected: " + labelSet.toString());
            }
        }
    }

    private void callCloudVision(final Bitmap bitmap) {
        // Switch text to loading
        //mImageDetails.setText(R.string.loading_message);


        // Do the real work in an async task, because we need to use the network anyway
        try {
            AsyncTask<Object, Void, String> labelDetectionTask = new LabelDetectionTask(this, prepareAnnotationRequest(bitmap));
            labelDetectionTask.execute();
        } catch (IOException e) {
            Log.d(TAG, "failed to make API request because of other IOException " +
                    e.getMessage());
        }
    }

    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    private static String convertResponseToString(BatchAnnotateImagesResponse response) {
        StringBuilder message = new StringBuilder("I found these things:\n\n");

        String labelsString = "";
        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
        if (labels != null) {
            for (EntityAnnotation label : labels) {
                message.append(String.format(Locale.US, "%.3f: %s", label.getScore(), label.getDescription()));
                message.append("\n");
                labelsString = labelsString + ", " + label.getDescription();
            }
        } else {
            labelsString += ", " + "nothing";
        }

        //return message.toString();
        return labelsString.substring(2);
    }
    // END CV

    // BEGIN ALGORITHMS
    // BACKEND: Generate content in this.stories (called once by onCreate)
    private void populateStories() {
        allStoriesList.clear();
        for (int i = 0; i < 4; i++) {
            ArrayList<Bitmap> story = Tools.generateStories(userPictures,stockPictures,userLabels,stockLabels);
            allStoriesList.add(story);
        }
        Log.println(VERBOSE, "debug", "New stories: " + allStoriesList.toString());
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

    // END ALGORITHMS
    
}

//Frontend tasks to do:
//1. Dynamic Recycler View with Cardviews on Listen page
//3. Using viewpager to have animated page swipes