package com.example.cs160_final_project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecordingActivity extends AppCompatActivity {

    // Recording Permission's Constant
    private static final int REQUEST_CODE = 1000;
    private static final int REQUEST_PERMISSION = 1001;

    public static ArrayList<Bitmap> imageSet;
    public static ArrayList<SavedVideo> savedVideosList = new ArrayList<SavedVideo>();
    private SavedVideo videoToBeSaved;

    private ImageView homeButton;
    private RelativeLayout popup;
    private ImageView popupBG;
    private TextView saveText;
    private CardView confirmButton;
    private CardView cancelButton;
    private TextView titleText;
    private EditText titleEditText;
    private Button doneButton;

    private ImageView currentlyDisplayedImg;

    private ImageView swipeInfoMessage;

    private ImageView statusBar1;
    private ImageView statusBar2;
    private ImageView statusBar3;
    private ImageView statusBar4;

    private RelativeLayout footer;
    private ImageView startRecordingButton;
    private ImageView stopRecordingButton;
    private TextView stopwatchText;

    private TextView theEndText;

    private boolean recordingStarted = false;

    private int pageIndex = 0;

    // Recording Variables
    private static final SparseIntArray ORIENTATION = new SparseIntArray();
    private MediaProjectionManager mediaProjectionManager;
    private MediaProjection mediaProjection;
    private VirtualDisplay virtualDisplay;
    private MediaProjectionCallback mediaProjectionCallBack;
    private MediaRecorder mediaRecorder;
    private int screenDensity;
    private static final int DISPLAY_WIDTH = 720;
    private static final int DISPLAY_HEIGHT = 1280;
    private RelativeLayout rootLayout;
    private String videoPath = "";


    static {
        ORIENTATION.append(Surface.ROTATION_0, 90);
        ORIENTATION.append(Surface.ROTATION_90, 0);
        ORIENTATION.append(Surface.ROTATION_180, 270);
        ORIENTATION.append(Surface.ROTATION_270, 180);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        videoToBeSaved = new SavedVideo();

        homeButton = findViewById(R.id.homeButton);
        popup = findViewById(R.id.popup);
        popupBG = findViewById(R.id.popupBG);
        saveText = findViewById(R.id.saveTextView);
        confirmButton = findViewById(R.id.confirm);
        cancelButton = findViewById(R.id.cancel);
        titleText = findViewById(R.id.titleTextView);
        titleEditText = findViewById(R.id.titleTextEdit);
        doneButton = findViewById(R.id.doneButton);

        currentlyDisplayedImg = findViewById(R.id.currentlyDisplayedImg);

        swipeInfoMessage = findViewById(R.id.swipeInfoMessage);

        statusBar1 = findViewById(R.id.statusBar1);
        statusBar2 = findViewById(R.id.statusBar2);
        statusBar3 = findViewById(R.id.statusBar3);
        statusBar4 = findViewById(R.id.statusBar4);

        footer = findViewById(R.id.footer);
        startRecordingButton = findViewById(R.id.startRecordingButton);
        stopRecordingButton = findViewById(R.id.stopRecordingButton);
        stopwatchText = findViewById(R.id.stopwatchText);

        theEndText = findViewById(R.id.theEndTextView);

        // Getting device's screen density
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        screenDensity = displayMetrics.densityDpi;

        // Initializing screen recording variables
        mediaRecorder = new MediaRecorder();
        mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        rootLayout = findViewById(R.id.rootLayout);

        final CountUpTimer timer = new CountUpTimer(3599000) {
            public void onTick(int second) {
                int min = 0;
                if (second>59) {
                    min = second / 60;
                    second = second % 60;
                }
                stopwatchText.setText(String.format("%02d:%02d",min, second));
            }
        };

        Intent intent = getIntent();
        imageSet = LandingPageActivity.allStoriesList.get(intent.getIntExtra("story-index", 0));
        currentlyDisplayedImg.setImageBitmap(scaleCenterCrop(imageSet.get(0), 380, 380));
        videoToBeSaved.setCoverImage(scaleCenterCrop(imageSet.get(0), 380, 380));

        startRecordingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Checking permissions to record audio and screen and write to storage
                if (ContextCompat.checkSelfPermission(RecordingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                        ContextCompat.checkSelfPermission(RecordingActivity.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(RecordingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(RecordingActivity.this, Manifest.permission.RECORD_AUDIO)) {

                        Snackbar.make(rootLayout, "Permission", Snackbar.LENGTH_INDEFINITE)
                                .setAction("ENABLE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        recordingStarted = false;
                                        ActivityCompat.requestPermissions(RecordingActivity.this,
                                                new String[] {
                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                        Manifest.permission.RECORD_AUDIO
                                                },
                                                REQUEST_PERMISSION);
                                    }
                                }).show();
                    } else {
                        ActivityCompat.requestPermissions(RecordingActivity.this,
                                new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.RECORD_AUDIO
                                },
                                REQUEST_PERMISSION);

                    }
                } else {

                    // Creating a notification stating that the recording will start
                    // (House Keeping stuff to use Media Projection)
                    startService(v);

                    // Initializing mediaRecorder
                    initRecorder();

                    // Begin recording
                    timer.start();
                    currentlyDisplayedImg.setAlpha((float) 1.0);
                    startRecordingButton.setVisibility(View.GONE);
                    stopRecordingButton.setVisibility(View.VISIBLE);
                    statusBar1.setImageResource(R.drawable.page_started_tab);
                    recordingStarted = true;
                    swipeInfoMessage.setVisibility(View.VISIBLE);
                    recordScreen();
                }
            }
        });

        currentlyDisplayedImg.setOnTouchListener(new OnSwipeTouchListener(RecordingActivity.this) {
            //turn page forward
            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeLeft() {
                if (pageIndex < 3 && recordingStarted) {
                    pageIndex++;
                    currentlyDisplayedImg.setImageBitmap(scaleCenterCrop(imageSet.get(pageIndex), 380, 380));
                    //gross code i know, but probably implementing it with viewpager this is just temporary
                    if (pageIndex == 1) {
                        swipeInfoMessage.setVisibility(View.GONE);
                        statusBar2.setImageResource(R.drawable.page_started_tab);
                    } else if (pageIndex == 2) {
                        statusBar3.setImageResource(R.drawable.page_started_tab);
                    }
                    else if (pageIndex == 3){
                        statusBar4.setImageResource(R.drawable.page_started_tab);
                        theEndText.setVisibility(View.VISIBLE);
                    }
                }
            }
            //turn page backward
            public void onSwipeRight() {
                if (pageIndex > 0) {
                    pageIndex--;
                    currentlyDisplayedImg.setImageBitmap(scaleCenterCrop(imageSet.get(pageIndex), 380, 380));
                    if (pageIndex == 0) {
                        swipeInfoMessage.setVisibility(View.GONE);
                        statusBar2.setImageResource(R.drawable.status_tabs);
                    } else if (pageIndex == 1) {
                        statusBar3.setImageResource(R.drawable.status_tabs);
                    }
                    else if (pageIndex == 2){
                        statusBar4.setImageResource(R.drawable.status_tabs);
                        theEndText.setVisibility(View.GONE);
                    }
                }
            }

        });

        stopRecordingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Pause Recording
                mediaRecorder.pause();

                popup.setVisibility(View.VISIBLE);
                saveText.setVisibility(View.VISIBLE);
                homeButton.setAlpha((float) .5);
                currentlyDisplayedImg.setAlpha((float) .5);
                statusBar1.setAlpha((float) .5);
                statusBar2.setAlpha((float) .5);
                statusBar3.setAlpha((float) .5);
                statusBar4.setAlpha((float) .5);
                footer.setAlpha((float) .5);

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Stop and save recording
                        timer.cancel();
                        stopRecording();
                        stopService(v);

                        saveText.setVisibility(View.GONE);
                        cancelButton.setVisibility(View.GONE);
                        confirmButton.setVisibility(View.GONE);

                        Animation anim = new ScaleAnimation(
                                1f, 1f, // Start and end values for the X axis scaling
                                1f, 0.65f, // Start and end values for the Y axis scaling
                                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                                Animation.RELATIVE_TO_SELF, 0f); // Pivot point of Y scaling
                        anim.setFillAfter(true); // Needed to keep the result of the animation
                        anim.setDuration(300);
                        popupBG.startAnimation(anim);
                        titleText.setVisibility(View.VISIBLE);
                        titleEditText.setVisibility(View.VISIBLE);
                        doneButton.setVisibility(View.VISIBLE);
                        doneButton.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                videoToBeSaved.setTitle(titleEditText.getText().toString());
                                videoToBeSaved.setFilename(videoPath);
                                savedVideosList.add(videoToBeSaved);
                                Intent intent = new Intent(RecordingActivity.this, ListenActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        popup.setVisibility(View.GONE);
                        saveText.setVisibility(View.GONE);
                        homeButton.setAlpha((float) 1.0);
                        currentlyDisplayedImg.setAlpha((float) 1.0);
                        statusBar1.setAlpha((float) 1.0);
                        statusBar2.setAlpha((float) 1.0);
                        statusBar3.setAlpha((float) 1.0);
                        statusBar4.setAlpha((float) 1.0);
                        footer.setAlpha((float) 1.0);

                        // Resume Recording
                        mediaRecorder.resume();
                        timer.start();

                    }
                });

            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popup.setVisibility(View.VISIBLE);
                homeButton.setAlpha((float) .5);
                currentlyDisplayedImg.setAlpha((float) .5);
                statusBar1.setAlpha((float) .5);
                statusBar2.setAlpha((float) .5);
                statusBar3.setAlpha((float) .5);
                statusBar4.setAlpha((float) .5);
                footer.setAlpha((float) .5);

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        stopRecording();
                        stopService(v);

                        Intent intent = new Intent(RecordingActivity.this, LandingPageActivity.class);
                        startActivity(intent);
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        popup.setVisibility(View.GONE);
                        homeButton.setAlpha((float) 1.0);
                        statusBar1.setAlpha((float) 1.0);
                        statusBar2.setAlpha((float) 1.0);
                        statusBar3.setAlpha((float) 1.0);
                        statusBar4.setAlpha((float) 1.0);
                        footer.setAlpha((float) 1.0);
                        if (recordingStarted) {
                            currentlyDisplayedImg.setAlpha((float) 1.0);
                        }
                    }
                });
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

    // Initializes media recorder which is the object that does the recording
    private void initRecorder() {
        try {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

            // Video's Path
            videoPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                        + new StringBuilder("/ScreenRecording-").append(new SimpleDateFormat("dd-MM-yyy-hh_mm_ss")
                            .format(new Date())).append(".mp4").toString();

            mediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setVideoEncodingBitRate(512 * 1000);
            mediaRecorder.setVideoFrameRate(30);
            mediaRecorder.setOutputFile(videoPath);


            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            int orientation =  ORIENTATION.get(rotation + 90);
            mediaRecorder.setOrientationHint(orientation);
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Begins the screen and audio recording
    private void recordScreen() {
        if (mediaProjection == null) {
            startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), REQUEST_CODE);
            return;
        }
        virtualDisplay = createVirtualDisplay();
        mediaRecorder.start();
    }


    // Sets the dimensions and flags for the virtual device which is the object that will be recorded
    private VirtualDisplay createVirtualDisplay() {
        return mediaProjection.createVirtualDisplay("RecordingActivity",
                DISPLAY_WIDTH, DISPLAY_HEIGHT, screenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mediaRecorder.getSurface(), null, null);
    }

    // Stops the media recorder and ends the recording
    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        stopRecordScreen();
    }

    // Stops and releases Virtual Display
    private void stopRecordScreen() {
        if (virtualDisplay == null) {
            return;
        }
        virtualDisplay.release();
        destroyMediaProjection();
    }

    // Stops and nulls the MediaProjection object
    private void destroyMediaProjection() {
        if (mediaProjection != null) {
            mediaProjection.unregisterCallback(mediaProjectionCallBack);
            mediaProjection.stop();
            mediaProjection = null;
        }
    }

    private class MediaProjectionCallback extends MediaProjection.Callback {

        @Override
        public void onStop() {
            super.onStop();

            if (recordingStarted) {
                recordingStarted = false;
                mediaRecorder.stop();
                mediaRecorder.release();
            }
            mediaProjection = null;
            stopRecordScreen();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case REQUEST_PERMISSION: {
                if (grantResults.length > 0 && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    initRecorder();
                    recordScreen();
                } else {
                    recordingStarted = false;
                    Snackbar.make(rootLayout, "Permission", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Enable", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ActivityCompat.requestPermissions(RecordingActivity.this,
                                            new String[]{
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                    Manifest.permission.RECORD_AUDIO
                                            },
                                            REQUEST_PERMISSION);
                                }
                            }).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != REQUEST_CODE) {
            Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show();
            return;
        }

        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            return;
        }
        mediaProjectionCallBack = new MediaProjectionCallback();
        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
        mediaProjection.registerCallback(mediaProjectionCallBack, null);
        virtualDisplay = createVirtualDisplay();
        mediaRecorder.start();
    }

    public void startService(View v) {
        Intent serviceIntent = new Intent(this, RecordingNotificationService.class);
        serviceIntent.putExtra("message", "Screen Record will begin");
        ContextCompat.startForegroundService(this, serviceIntent);
    }
    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, RecordingNotificationService.class);
        stopService(serviceIntent);
    }
}