package com.example.cs160_final_project;

import android.graphics.Bitmap;

public class SavedVideo {
    private Bitmap coverImage;
    private String title;
    private String filename;

    public void setCoverImage(Bitmap cover) {
        coverImage = cover;
    }

    public Bitmap getCoverImage() {
        return coverImage;
    }

    public void setTitle(String titleString) {
        title = titleString;
    }

    public String getTitle() {
        return title;
    }

    public void setFilename(String filenameString) {
        filename = filenameString;
    }

    public String getFilename() {
        return filename;
    }
}
