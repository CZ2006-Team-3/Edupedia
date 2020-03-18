package com.example.edupedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.edupedia.Login;
import com.example.edupedia.RegisterUI;

public class MainActivity extends AppCompatActivity {
    private Button buttonA;
    private Button buttonB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the button
        buttonA = (Button) findViewById(R.id.login);
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
        buttonB = (Button) findViewById(R.id.newUser);
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });

    }

    public void openLogin(){
        Intent intent1 = new Intent(this, Login.class);
        startActivity(intent1);
    }

    public void openRegister(){
        Intent intent2 = new Intent(this, RegisterUI.class);
        startActivity(intent2);
    }

}
