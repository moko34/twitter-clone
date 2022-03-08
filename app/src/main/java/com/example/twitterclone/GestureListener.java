package com.example.twitterclone;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twitterclone.create_post.PostImage;

import java.util.ArrayList;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final int MIN_SWIPING_DISTANCE = 50;
    private static final int THRESHOLD_VELOCITY = 50;
    private Context context;
    private  ArrayList<PostImage> images;
    private ImageView imageView;
    private TextView textView;
    private int index=0;


    public GestureListener (Context context, ArrayList<PostImage> images, ImageView imageView,TextView textView){
        this.context=context;
        this.images =images;
        this.imageView=imageView;
        this.textView=textView;
        imageView.setImageURI(this.images.get(0).getImageUri());
        textView.setText(String.format(context.getString(R.string.page_index),index + 1,images.size()));
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if (e1.getX() - e2.getX() > MIN_SWIPING_DISTANCE && Math.abs(velocityX) > THRESHOLD_VELOCITY)
        {
            if((index < (images.size()-1)) && (index >= 0)){ imageView.setImageURI(images.get(index + 1).getImageUri());
            index++;

                textView.setText(String.format(context.getString(R.string.page_index),index + 1,images.size()));
                textView.animate().translationX(-50).alphaBy(1f).setDuration(1500).start();
            }


            /* Code that you want to do on swiping left side*/
            return false;
        }
        else if (e2.getX() - e1.getX() > MIN_SWIPING_DISTANCE && Math.abs(velocityX) > THRESHOLD_VELOCITY)
        {
            if(index > 0 && index < images.size()){
                imageView.setImageURI(images.get(index - 1).getImageUri());
                index--;
                textView.setText(String.format(context.getString(R.string.page_index),index + 1,images.size()));
                textView.animate().alphaBy(-1f).translationX(10).setDuration(1500).start();
            }

            /* Code that you want to do on swiping right side*/
            return false;
        }

        if(e1.getY() - e2.getY() >MIN_SWIPING_DISTANCE && Math.abs(velocityY) > THRESHOLD_VELOCITY) {
            // Bottom to top
            //Your code to flip the coin
            return false;
        }  else if (e2.getY() - e1.getY() >MIN_SWIPING_DISTANCE && Math.abs(velocityY) > THRESHOLD_VELOCITY) {
            // Top to bottom
            //
            return false;
        }
        return false; }
}
