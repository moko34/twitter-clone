package com.example.twitterclone;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;

public class Post {
    private String user;
    private String caption;
    private String date;
    private HashMap<String,String> comments;
    private int likes;
    private ArrayList<Uri> imageUris;
    private Uri userImageUri;
    private String id;

    public Post(String user, String caption, String date, HashMap<String, String> comments, int likes, ArrayList<Uri> imageUris, Uri userImageUri, String id) {
        this.user = user;
        this.caption = caption;
        this.date = date;
        this.comments = comments;
        this.likes = likes;
        this.imageUris = imageUris;
        this.userImageUri = userImageUri;
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HashMap<String, String> getComments() {
        return comments;
    }

    public void setComments(HashMap<String, String> comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ArrayList<Uri> getImageUris() {
        return imageUris;
    }

    public void setImageUris(ArrayList<Uri> imageUris) {
        this.imageUris = imageUris;
    }

    public Uri getUserImageUri() {
        return userImageUri;
    }

    public void setUserImageUri(Uri userImageUri) {
        this.userImageUri = userImageUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
