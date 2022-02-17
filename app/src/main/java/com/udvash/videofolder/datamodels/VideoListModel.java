package com.udvash.videofolder.datamodels;

import android.graphics.Bitmap;
import android.net.Uri;

public class VideoListModel {

    public int id;
    public String videoTitle;
    private byte[] size;
    private Bitmap bitmap;

    int duration, fileSize;

    private Uri contentUri;

    public VideoListModel(){}


    public VideoListModel(int id, String videoTitle, byte[] size, Bitmap bitmap, int duration, int fileSize, Uri contentUri) {
        this.id = id;
        this.videoTitle = videoTitle;
        this.size = size;
        this.bitmap = bitmap;
        this.duration = duration;
        this.fileSize = fileSize;
        this.contentUri = contentUri;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public Uri getContentUri() {
        return contentUri;
    }

    public void setContentUri(Uri contentUri) {
        this.contentUri = contentUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public byte[] getSize() {
        return size;
    }

    public void setSize(byte[] size) {
        this.size = size;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
