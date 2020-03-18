package com.example.edupedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.edupedia.MainSearchUI;
import com.example.edupedia.RegisterUI;

public class Login extends AppCompatActivity {

    String userName;
    EditText userNameInput;

    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameInput = (EditText) findViewById(R.id.editTextEmail);

        loginButton = (Button) findViewById(R.id.loginproceed);
        registerButton = (Button) findViewById(R.id.loginproceed);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameInput.getText().toString();
                showToast(userName);
                openSearch();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });
    }

    public void openSearch(){
        Intent intent1 = new Intent(this, MainSearchUI.class);
        startActivity(intent1);
    }

    public void showToast(String text){
        Toast.makeText(Login.this, text, Toast.LENGTH_SHORT)
                .show();
    }

    public void openRegister(){
        Intent intent2 = new Intent(this, RegisterUI.class);
        startActivity(intent2);
    }


}
