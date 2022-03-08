package com.example.twitterclone.preview_post;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twitterclone.GestureListener;
import com.example.twitterclone.R;
import com.example.twitterclone.create_post.PostImage;

import java.util.ArrayList;


public class PreviewPostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<PostImage> postImages;
    private String caption;
    private  EditText editText;
    public PreviewPostFragment() {
        // Required empty public constructor
    }



    public static PreviewPostFragment newInstance(ArrayList<PostImage> images,String caption) {
        PreviewPostFragment fragment = new PreviewPostFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1,images);
        args.putString(ARG_PARAM2,caption);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            postImages = (ArrayList<PostImage>) getArguments().getSerializable(ARG_PARAM1);
            caption = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preview_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView=view.findViewById(R.id.swipe);
        editText=view.findViewById(R.id.edt_final_caption);
        editText.setText(caption);
        TextView textView = view.findViewById(R.id.txt_page);
        GestureDetector gestureDetector=new GestureDetector(getContext(), new GestureListener(getContext(),postImages,imageView,textView));

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

    }


    public ArrayList<java.io.Serializable> getPostData(){
        ArrayList<java.io.Serializable> arrayList = new ArrayList<java.io.Serializable>();
        arrayList.add(0,postImages);
        arrayList.add(1,editText.getText().toString());
        return arrayList;
    }
}