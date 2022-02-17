package com.udvash.videofolder.datamodels;

import android.net.Uri;

import java.util.List;

public class VideoDataModel {
    private Uri uri;
    private String name;
    private int duration;
    private int size;

    public VideoDataModel() {
    }

    public VideoDataModel(Uri uri, String name, int duration, int size) {
        this.uri = uri;
        this.name = name;
        this.duration = duration;
        this.size = size;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
