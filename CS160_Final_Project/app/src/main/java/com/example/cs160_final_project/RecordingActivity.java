package com.example.cs160_final_project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;

public class RecordingActivity extends Activity {

    public static ArrayList<Integer> imageSet;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

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
        imageSet = MainActivity.allStoriesList.get(intent.getIntExtra("story-index", 0));
        Picasso.get().load(imageSet.get(0)).fit()
                .centerCrop().into(currentlyDisplayedImg);

        startRecordingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timer.start();
                currentlyDisplayedImg.setAlpha((float) 1.0);
                startRecordingButton.setVisibility(View.GONE);
                stopRecordingButton.setVisibility(View.VISIBLE);
                statusBar1.setImageResource(R.drawable.page_started_tab);
                //TODO: start recording
                recordingStarted = true;
                swipeInfoMessage.setVisibility(View.VISIBLE);
            }
        });

        currentlyDisplayedImg.setOnTouchListener(new OnSwipeTouchListener(RecordingActivity.this) {
            //turn page forward
            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeLeft() {
                if (pageIndex < 3 && recordingStarted) {
                    pageIndex++;
                    Picasso.get().load(imageSet.get(pageIndex)).fit()
                            .centerCrop().into(currentlyDisplayedImg);
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
                    Picasso.get().load(imageSet.get(pageIndex)).fit()
                            .centerCrop().into(currentlyDisplayedImg);
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
                //TODO: stop recording
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
                        timer.start();
                        //TODO: stop recording/save recording
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
                                //TODO: Add story to list of finished stories
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
                        //TODO: stop recording/discard
                        Intent intent = new Intent(RecordingActivity.this, MainActivity.class);
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
}