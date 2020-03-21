package com.example.edupedia.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edupedia.R;
import com.example.edupedia.controller.WatchlistController;
import com.example.edupedia.model.UserID;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class StartUI extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;
    private SharedPreferences sharedPref;
    public static String firebase_key = "firebase_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_ui);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);

    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                finish();
                String userId = mAuth.getCurrentUser().getUid();
                Intent intent = new Intent(this, MainNavigationUI.class);
                intent.putExtra(firebase_key, userId);
                UserID.getInstance().setID(userId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Log.d("STARTUI", "Logged In");
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private UserID userID = UserID.getInstance();

    @Override
    protected void onStart() {
        super.onStart();

//        userID.setID(mAuth.getCurrentUser());
        if (userID.getID() != null) {
            finish();
            String userId = mAuth.getCurrentUser().getUid();
            Intent intent = new Intent(this, MainNavigationUI.class);
            intent.putExtra(firebase_key, userId);
            UserID.getInstance().setID(userId);
            Log.d("STARTUI", "Logged In");
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewSignup:
                finish();
                startActivity(new Intent(this, RegisterUI.class));
                break;

            case R.id.buttonLogin:
                userLogin();
                break;
        }
    }
}
