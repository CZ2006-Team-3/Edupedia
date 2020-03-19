package com.example.edupedia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edupedia.R;

public class FrontPageUI extends AppCompatActivity {
    private Button buttonA;
    private Button buttonB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage_ui);

        // get the button
        buttonA = (Button) findViewById(R.id.login);

        buttonB = (Button) findViewById(R.id.newUser);

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
        Intent intent1 = new Intent(this, StartUI.class);
        startActivity(intent1);
    }

    public void openRegister(){
        Intent intent2 = new Intent(this, RegisterUI.class);
        startActivity(intent2);
    }


}
