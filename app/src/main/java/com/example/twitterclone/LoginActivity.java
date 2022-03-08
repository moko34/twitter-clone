package com.example.twitterclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twitterclone.create_post.AddPostFragment;
import com.example.twitterclone.create_post.ImageLoadTask;
import com.example.twitterclone.create_post.ImageLoadTaskExecutor;
import com.example.twitterclone.create_post.PostImage;
import com.example.twitterclone.preview_post.PreviewPostFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements ImageLoadTask.ImageLoaded,AddPostFragment.ImageDataUpdated,View.OnClickListener {

    private AddPostFragment addPostFragment;
    private PreviewPostFragment previewPostFragment;
    private ArrayList<PostImage> selectedImages =new ArrayList<>();
    private ArrayList<PostImage>  allPostImages=new ArrayList<>();
    private  String postCaption="";
    private  static final String TRANSACTION_KEY = "transaction_key";
    private  static final String TRANSACTION_KEY_1 = "transaction_key_1";
    private MenuItem addPost,visitProfile;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("");
        View view = getLayoutInflater().inflate(R.layout.action_bar_title_view,null);
        TextView textView=view.findViewById(R.id.text);
        textView.setText(R.string.create_post);
        getSupportActionBar().setCustomView(view);
        new ImageLoadTaskExecutor().execute(new ImageLoadTask(this,this));
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(this);








}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.menu_add){
            addPost=item;
            Log.i("hyy","jdi");
            if(allPostImages.size() > 0 && addPostFragment == null){
                addPostFragment=AddPostFragment.newInstance(allPostImages);
                getSupportFragmentManager().beginTransaction().
                        add(R.id.fragment_container,addPostFragment).
                        addToBackStack(TRANSACTION_KEY).commit();
                item.setVisible(false);
                visitProfile.setVisible(false);
            }

        }
        if(item.getItemId()==R.id.menu_profile){

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu,menu);
        addPost = menu.findItem(R.id.menu_add);
        visitProfile=menu.findItem(R.id.menu_profile);
        return true;
    }


    @Override
    public void onImageLoaded(ArrayList<PostImage> postImages) {
         allPostImages =postImages;

    }

    @Override
    public void onImageArrayChange(ArrayList<PostImage> images,String caption) {
            selectedImages=images;
            postCaption=caption;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.fab){
            if(addPostFragment != null && selectedImages.size()>0 && postCaption != ""){
                previewPostFragment=PreviewPostFragment.newInstance(selectedImages,postCaption);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,previewPostFragment)
                        .addToBackStack(TRANSACTION_KEY_1).commit();

            }
        }
    }
}