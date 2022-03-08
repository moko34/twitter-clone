package com.example.twitterclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;


import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final Pattern PASSWORD_PATTERN=Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[%$@*#])[a-zA-Z\\d%$@*#]{8,}$");
    private static  final Pattern USERNAME_PATTERN=Pattern.compile("^(?=.*[a-z])[a-z\\d_]{4,}$");
    private static final Pattern PASSWORD_PATTERN_2=Pattern.compile("^(?=.*[A-Z])[a-zA-Z\\d%$@*#]+$");
    private static final Pattern PASSWORD_PATTERN_1=Pattern.compile("^(?=.*[a-z])[a-zA-Z\\d%$@*#]+$");
    private static final Pattern PASSWORD_PATTERN_3=Pattern.compile("^(?=.*[%$@*#])[a-zA-Z\\d%$@*#]+$");
    private static final Pattern PASSWORD_PATTERN_4=Pattern.compile("^(?=.*[a-zA-Z\\d%$@*#]).{8,}$");
    private static final Pattern PASSWORD_PATTERN_5=Pattern.compile("^(?=.*[\\d])[a-zA-Z\\d%$@*#]+$");

    private final int code=1000;
    private boolean permissionGranted=true;


    private FirebaseAuth firebaseAuth;
    private EditText edtPassword,edtEmail;
    private EditText edtUsername;
    private TextInputLayout passwordLayout,emailLayout,usernameLayout;
    private TextView userStatus,userOptions;
    private Button btnSignUser,btnUserChoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        initViews();








    }



        private void initViews() {
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(R.string.app_name_second);
            View view = getLayoutInflater().inflate(R.layout.action_bar_title_view, null);
            getSupportActionBar().setCustomView(view);
            firebaseAuth=FirebaseAuth.getInstance();

            edtEmail = findViewById(R.id.edt_email);
            edtPassword = findViewById(R.id.edt_password);
            edtUsername = findViewById(R.id.edt_username);
            passwordLayout = findViewById(R.id.password_layout);
            emailLayout = findViewById(R.id.email_layout);
            usernameLayout = findViewById(R.id.username_layout);
            userStatus = findViewById(R.id.txt_user_status);
            userOptions = findViewById(R.id.user_options);
            btnSignUser = findViewById(R.id.btn_options);
            btnUserChoice = findViewById(R.id.btn_user_choice);
            btnSignUser.setOnClickListener(this);
            btnUserChoice.setOnClickListener(this);
            edtPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    validatePassword(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }




        private  void signUpUserAltOption(){
            userStatus.setText(getString(R.string.get_started));
            usernameLayout.setVisibility(View.VISIBLE);
            userOptions.setText(getString(R.string.sign_in_user_text));
            btnSignUser.setText(R.string.log_in);
            btnUserChoice.setText(R.string.sign_up_user);
            btnSignUser.setTag(getString(R.string.sign_up_user));
            btnUserChoice.setTag(getString(R.string.sign_up_user));

        }

        private void signInUserAltOption(){
            userStatus.setText(getString(R.string.log_in_user));
            usernameLayout.setVisibility(View.GONE);
            userOptions.setText(getString(R.string.sign_up_user_text));
            btnSignUser.setText(R.string.sign_up_user);
            btnSignUser.setTag(getString(R.string.log_in));
            btnUserChoice.setText(R.string.log_in);
            btnUserChoice.setTag("Sign In");


        }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_options:
        //if tag is sign in it denotes the user is currently seeing the log in screen
               if(btnSignUser.getTag()==getString(R.string.log_in)){
                    signUpUserAltOption();
                    return;
                }
                signInUserAltOption();
                break;
            case R.id.btn_user_choice:

        // if tag is sign in it denotes the user is currently seeing the log in screen
                if(btnUserChoice.getTag()=="Sign In"){
                    signInUser(edtEmail.getText().toString(),edtPassword.getText().toString());
                    return;
                }
                signUpUser();
                break;


        }
    }

    private  void signUpUser(){
        String emailInput=edtEmail.getText().toString().trim();
        String passwordInput=edtPassword.getText().toString().trim();
        String usernameInput=edtUsername.getText().toString().trim();

         if(validateInputs(true)) {
             AlertDialog alertDialog = showProgressDialog(getString(R.string.signing_up));
             alertDialog.show();
             firebaseAuth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()) {
                         alertDialog.dismiss();
                         FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                         updateProfile(usernameInput, firebaseUser, true);
                     } else {
                         alertDialog.dismiss();
                         Exception exception =task.getException();
                         showAccountStatusDialog(getString(R.string.status_dialog_error_title),getString(R.string.status_dialog_error_message,exception.getMessage())).show();
                     }
                 }
             });


         }
        }



    private boolean validateInputs(boolean isSigningUp){

        String emailInput=edtEmail.getText().toString().trim();
        String passwordInput=edtPassword.getText().toString().trim();
        String userNameInput;

        if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            emailLayout.setError(getString(R.string.email_error));
            return false;
        }
        if(passwordInput.equals("")){
            passwordLayout.setError(getString(R.string.password_error));
            return false;
        }else if(!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            passwordLayout.setError(getString(R.string.password_error_weak));
            return false;
        }
        if(isSigningUp){
            userNameInput=edtUsername.getText().toString().trim();
            if(!USERNAME_PATTERN.matcher(userNameInput).matches()){
                usernameLayout.setError(getString(R.string.username_error));
                return false;
            }

        }
        passwordLayout.setError(null);
        usernameLayout.setError(null);
        emailLayout.setError(null);
        return true;

    }


    private AlertDialog showProgressDialog(String message){

        AlertDialog.Builder alertBuilder= new AlertDialog.Builder(this);
        alertBuilder.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.progress_dialog_view,null);
        TextView textView=view.findViewById(R.id.progress_state);
        textView.setText(message);
        alertBuilder.setView(view);
        return alertBuilder.create();
    }

    private AlertDialog showAccountStatusDialog(String title,String message){
        AlertDialog.Builder alertBuilder= new AlertDialog.Builder(this);
        alertBuilder.setCancelable(false);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(message);
        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });



       return alertBuilder.create();

    }

    private boolean validatePassword(String s){
       if(!PASSWORD_PATTERN_1.matcher(s).matches()){
           passwordLayout.setError(getString(R.string.password_pattern_1));
           return false;
       }
        if(!PASSWORD_PATTERN_2.matcher(s).matches()){
            passwordLayout.setError(getString(R.string.password_pattern_2));
            return false;
        }
        if(!PASSWORD_PATTERN_3.matcher(s).matches()){
            passwordLayout.setError(getString(R.string.password_pattern_3));
            return false;
        }
        if(!PASSWORD_PATTERN_5.matcher(s).matches()){
            passwordLayout.setError(getString(R.string.password_pattern_5));
            return false;
        }
        if(!PASSWORD_PATTERN_4.matcher(s).matches()){
            passwordLayout.setError(getString(R.string.password_pattern_4));
            return false;
        }
        passwordLayout.setError(null);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser !=null && !firebaseUser.isEmailVerified()){
            showAccountStatusDialog("Oops",String.format("Please verify your email %s",firebaseUser.getEmail())).show();
            return;
         }

        if(firebaseUser==null){
         signUpUserAltOption();
         return;
        }
        if(permissionGranted){
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        }
    }

    private void updateProfile(String displayName,FirebaseUser user,boolean isAuthenticating){
        UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build();

        user.updateProfile(profileChangeRequest).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
               AlertDialog alertDialog = showAccountStatusDialog("Nice work ","Account created successfully");
                       alertDialog.setButton(
                        DialogInterface.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                            if(isAuthenticating){
                               user.sendEmailVerification().addOnCompleteListener(
                                       task1 -> {
                                           if(task.isSuccessful())showAccountStatusDialog(getString(R.string.status_dialog_success_title),getString(R.string.status_dialog_success_message)).show();
                                       }
                             );
                               return;
                            }
                            dialog.dismiss();
                        }
                       );
                       alertDialog.show();
            }
            else {
                showAccountStatusDialog(getString(R.string.status_dialog_error_title),getString(R.string.status_dialog_error_message)).show();
            }
        });
    }

    private  void  signInUser(String email,String password){
        if(validateInputs(false)){
            AlertDialog alertDialog = showProgressDialog("Please Wait");
            alertDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                alertDialog.dismiss();
                if (task.isSuccessful()) {
                    FirebaseUser user=firebaseAuth.getCurrentUser();
                    Log.i("ERRR",user.isEmailVerified()+"");
                    showAccountStatusDialog("Nice Work", "You logged in").show();
                } else {
                    Exception exception=task.getException();
                    showAccountStatusDialog("Error", String.format("Sign In failed \n %s",exception.getMessage())).show();
                }

            }

        });
    }
    }

    private  void requestPermission(){
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==code){
            if(grantResults.length<=0){
                permissionGranted=false;
                requestPermission();
            }else{

            }
    }
}

}


