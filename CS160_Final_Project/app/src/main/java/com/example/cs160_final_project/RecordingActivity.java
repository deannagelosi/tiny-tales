package com.example.cs160_final_project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;

public class RecordingActivity extends Activity {

    public static ArrayList<Integer> imageSet;

    private ImageView homeButton;
    private LinearLayout homeButtonConfirmationPopup;
    private CardView homeButtonConfirm;
    private CardView homeButtonCancel;

    private ImageView currentlyDisplayedImg;

    private ImageView swipeInfoMessage;

    private ImageView statusBar1;
    private ImageView statusBar2;
    private ImageView statusBar3;
    private ImageView statusBar4;

    private ImageView startRecordingButton;
    private ImageView stopRecordingButton;

    private boolean recordingStarted = false;

    private int pageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        homeButton = findViewById(R.id.homeButton);
        homeButtonConfirmationPopup = findViewById(R.id.homePopup);
        homeButtonConfirm = findViewById(R.id.confirm);
        homeButtonCancel = findViewById(R.id.cancel);

        currentlyDisplayedImg = findViewById(R.id.currentlyDisplayedImg);

        swipeInfoMessage = findViewById(R.id.swipeInfoMessage);

        statusBar1 = findViewById(R.id.statusBar1);
        statusBar2 = findViewById(R.id.statusBar2);
        statusBar3 = findViewById(R.id.statusBar3);
        statusBar4 = findViewById(R.id.statusBar4);

        startRecordingButton = findViewById(R.id.startRecordingButton);
        stopRecordingButton = findViewById(R.id.stopRecordingButton);

        Intent intent = getIntent();
        imageSet = MainActivity.allStoriesList.get(intent.getIntExtra("story-index", 0));
        Picasso.get().load(imageSet.get(0)).fit()
                .centerCrop().into(currentlyDisplayedImg);

        startRecordingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
                    else {
                        statusBar4.setImageResource(R.drawable.page_started_tab);
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
                    else {
                        statusBar4.setImageResource(R.drawable.status_tabs);
                    }
                }
            }

        });
        stopRecordingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: stop recording
                //TODO: save options
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                homeButtonConfirmationPopup.setVisibility(View.VISIBLE);

                homeButtonConfirm.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //TODO: stop recording/discard
                        Intent intent = new Intent(RecordingActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                homeButtonCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        homeButtonConfirmationPopup.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}