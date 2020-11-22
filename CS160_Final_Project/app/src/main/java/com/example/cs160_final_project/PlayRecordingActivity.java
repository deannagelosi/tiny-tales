package com.example.cs160_final_project;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
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
        playVideo();
        // TODO: Go back to Listen Activity once video is finished
    }

    // Plays recording on the videoView object
    private void playVideo() {
        videoView.setVideoURI(Uri.parse(videoPath));
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,20, 0);
        videoView.start();
    }
}