package com.example.twitterclone.create_post;


import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;

public class ImageLoadTask implements Runnable{

    private Context context;
    private ImageLoaded imageLoaded;
    public interface  ImageLoaded {
        void onImageLoaded(ArrayList<PostImage> postImages);
    }

    public  ImageLoadTask (Context context,ImageLoaded imageLoaded){
        this.context=context;
        this.imageLoaded=imageLoaded;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        ArrayList<PostImage> images = fetchImages();
        images.sort(new PostImageComparator());
        imageLoaded.onImageLoaded(images);

    }

    private ArrayList<PostImage> fetchImages () {
        ArrayList<PostImage> postImages = new ArrayList<>();
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        String[] projection = new String[]{ MediaStore.Images.Media._ID};
        String order = MediaStore.Images.Media.DATE_ADDED;
        try (Cursor cursor =context.getContentResolver().query(uri, projection, null, null, order)) {
            //identify column indices of projection
            int idColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);


            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumnIndex);
                Uri newUri = ContentUris.withAppendedId(uri, id);
                postImages.add(new PostImage(newUri, false,id));
            }

        }
        return postImages;
    }

    public  class PostImageComparator implements Comparator<PostImage>{

        @Override
        public int compare(PostImage post1, PostImage post2) {
            return (int)(post1.getId()- post2.getId());
        }


    }
}

