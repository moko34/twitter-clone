package com.example.twitterclone.create_post;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.twitterclone.R;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class AddPostFragment extends Fragment implements ImageAdaptor.ImageIsClicked {

    @Override
    public void imageIsSelected(ImageView imageView, PostImage postImage) {
        if(postImage.isChecked()){
            imageView.setSelected(false);
            postImage.setChecked(false);
            chosenImages.remove(postImage);
            imageDataUpdated.onImageArrayChange(chosenImages,editText.getText().toString());
            return;
        }

        imageView.setSelected(true);
        postImage.setChecked(true);
        chosenImages.add(postImage);
        imageDataUpdated.onImageArrayChange(chosenImages,editText.getText().toString());

    }
    //update selected images array
    public interface ImageDataUpdated{
        void onImageArrayChange(ArrayList<PostImage> images,String caption);
    }
    private ImageAdaptor imageAdaptor;
    private  ImageDataUpdated imageDataUpdated;
    private EditText editText;
    private static ArrayList<PostImage> postImages;
    private ArrayList<PostImage>  chosenImages = new ArrayList<>();
    public AddPostFragment() {
        // Required empty public constructor
    }


    public static AddPostFragment newInstance( ArrayList<PostImage> images) {
        postImages=images;
        AddPostFragment fragment = new AddPostFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_post, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ImageDataUpdated){
            imageDataUpdated= (ImageDataUpdated) context;
            imageAdaptor=new ImageAdaptor(this,postImages,context);

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       editText=view.findViewById(R.id.edt_create_post);
       RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
       recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
       recyclerView.setAdapter(imageAdaptor);

    }


}