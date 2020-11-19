package com.example.cs160_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class PlayRecordingActivity extends AppCompatActivity {

    private VideoView videoView;
    private String videoUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_recording);
        Intent intent = getIntent();
        videoUrl = intent.getExtras().getString("path");
        videoView = findViewById(R.id.videoView);
        playVideo();

    }

    // Plays recording on the videoView object
    private void playVideo() {
        videoView.setVideoURI(Uri.parse(videoUrl));
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,20, 0);
        videoView.start();
    }
}