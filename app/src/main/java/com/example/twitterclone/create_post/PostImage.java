package com.example.twitterclone.create_post;

import android.net.Uri;

public class PostImage {
    private Uri imageUri;
    private boolean isChecked;
    private long id;

    public PostImage(Uri imageUri, boolean isChecked,long id) {
        this.imageUri = imageUri;
        this.isChecked = isChecked;
        this.id =id;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public long getId() {
        return id;
    }
}


