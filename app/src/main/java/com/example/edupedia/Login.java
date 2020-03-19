package com.example.edupedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.edupedia.ui.SearchFragment;


public class Login extends AppCompatActivity{

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
        registerButton = (Button) findViewById(R.id.signUpButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment fragment = new SearchFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_search, fragment);
                transaction.commit();
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
        Intent intent1 = new Intent(this, SettingsFragment.class);
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
