package com.example.twitterclone.create_post;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.R;

public class ImageViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;

    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.chose_image);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
