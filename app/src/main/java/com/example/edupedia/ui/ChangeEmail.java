package com.example.edupedia.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.edupedia.R;
import com.example.edupedia.controller.UserController;

/**
 * change email class for users choosing to change the email listed in their profile
 */
public class ChangeEmail extends Activity implements View.OnClickListener {
    EditText editTextEmail, editTextEmailRetype;
    String email, emailRetype;
    private UserController userController = UserController.getInstance();

    /**
     * default method that occurs upon the creation of the activity
     * this method sets the dimensions for the change email pop up
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_email);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8), (int) (height*0.4));

        editTextEmail = (EditText) findViewById(R.id.newUsername);
        editTextEmailRetype = (EditText) findViewById(R.id.newUsernameRetype);

        findViewById(R.id.changeUsernameButton).setOnClickListener(this);

    }
    /**
     * method to change the email by called userController
     * method has error handling to ensure the email typed matches the email retyped by the user and it must be a valid email address
     */
    private void changeEmail(){
        email = editTextEmail.getText().toString();
        emailRetype = editTextEmailRetype.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (!email.equals(emailRetype)) {
            editTextEmail.setError("Email do not match. Try again.");
            editTextEmail.requestFocus();
            return;
        }

        userController.changeEmail(email);
        Toast.makeText(this, "Email changed", Toast.LENGTH_SHORT).show();

        finish();
    }

    /**
     * button calling the change email method once the user clicks on the button
     * @param view
     */
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.changeUsernameButton:
                changeEmail();
                break;
        }
    }
}