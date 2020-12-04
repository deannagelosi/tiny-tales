package com.example.cs160_final_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class PlayRecordingActivity extends AppCompatActivity {

    private VideoView videoView;
    private String videoPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_recording);
        Intent intent = getIntent();
        videoPath = intent.getExtras().getString("videoPath");
        videoView = findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        playVideo();
    }

    // Plays recording on the videoView object
    private void playVideo() {
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();
    }
}