package com.example.edupedia.ui;

import android.app.Activity;
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

public class ChangeUsername extends Activity implements View.OnClickListener {
    EditText editTextEmail, editTextEmailRetype;
    String email, emailRetype;
    private UserController userController = UserController.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_username);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8), (int) (height*0.4));

        editTextEmail = (EditText) findViewById(R.id.newUsername);
        editTextEmailRetype = (EditText) findViewById(R.id.newUsernameRetype);

        findViewById(R.id.changeUsernameButton).setOnClickListener(this);

    }

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

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.changeUsernameButton:
                changeEmail();
                break;
        }
    }
}