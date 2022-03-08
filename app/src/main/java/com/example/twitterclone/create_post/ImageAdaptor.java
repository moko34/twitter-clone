package com.example.twitterclone.create_post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.R;

import java.util.ArrayList;

public class ImageAdaptor extends RecyclerView.Adapter<ImageViewHolder> {
    public interface ImageIsClicked{
        void imageIsSelected(ImageView imageView, PostImage postImage);
    }
    private ImageIsClicked imageIsClicked;
    private ArrayList<PostImage> postImages;
    private Context context;

    public ImageAdaptor(ImageIsClicked imageIsClicked, ArrayList<PostImage> postImages, Context context) {
        this.imageIsClicked = imageIsClicked;
        this.postImages = postImages;

        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_layout,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        PostImage postImage=postImages.get(holder.getAdapterPosition());
        holder.getImageView().setImageURI(postImage.getImageUri());
        holder.getImageView().setSelected(postImage.isChecked());
        holder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageIsClicked.imageIsSelected(holder.getImageView(),postImage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postImages.size();
    }
}
