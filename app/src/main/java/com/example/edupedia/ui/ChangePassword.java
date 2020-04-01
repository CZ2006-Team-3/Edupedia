package com.example.edupedia.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edupedia.R;
import com.example.edupedia.controller.UserController;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * change password class for users choosing to change their password
 */
public class ChangePassword extends Activity implements View.OnClickListener{
    EditText editTextPassword, editTextPasswordRetype;
    String pwd, pwdretype;
    private UserController userController = UserController.getInstance();

    /**
     * default method that occurs upon the creation of the activity
     * this method sets the dimensions for the change password pop up
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8), (int) (height*0.4));

        editTextPassword = (EditText) findViewById(R.id.newPassword);
        editTextPasswordRetype = (EditText) findViewById(R.id.newPasswordRetype);

        findViewById(R.id.changePasswordButton).setOnClickListener(this);
    }

    /**
     * method to change the password by called userController
     * method has error handling to ensure the password typed matches the password retyped by the user and password length should be atleast 6 digits
     */
    private void changePassword() {

        pwd = editTextPassword.getText().toString();
        pwdretype = editTextPasswordRetype.getText().toString();

        if (pwd.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (pwd.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (pwd.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        if (!pwd.equals(pwdretype)) {
            editTextPassword.setError("Password do not match. Try again.");
            editTextPassword.requestFocus();
            return;
        }

        userController.changePassword(pwd);
        Toast.makeText(this, "Password changed", Toast.LENGTH_SHORT).show();

        finish();
    }

    /**
     * button calling the change password method once the user clicks on the button
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.changePasswordButton:
                changePassword();
                break;
        }
    }
}