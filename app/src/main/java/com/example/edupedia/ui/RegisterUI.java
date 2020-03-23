package com.example.edupedia.ui;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.edupedia.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import java.util.Calendar;
import java.util.HashMap;

/**
 * class to register new user details
 */
public class RegisterUI extends AppCompatActivity implements View.OnClickListener{

    ProgressBar progressBar;
    TextView textEdLevel, mDisplayDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    EditText editTextEmail, editTextPassword, editTextUsername, editTextDateOfBirth,
            editTextName, editTextRetypePassword;
    String dateOfBirth;

    private FirebaseAuth mAuth = null;

    /**
     * default method that occurs upon the creation of the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ui);
        /**
         * edit text to allow for user input of email
         */
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        /**
         * edit text to allow for user input of their password
         */
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        /**
         * edit text to allow for user input of name
         */
        editTextName = (EditText) findViewById(R.id.editTextName);

        /**
         * edit text to allow for user input of their username
         */
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        /**
         * edit text to allow for user input of their retyped password for authentication
         */
        editTextRetypePassword = (EditText) findViewById(R.id.editTextRetypePassword);

        /**
         * progress bar
         */
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        /**
         * instantiation of firebase
         */
        mAuth = FirebaseAuth.getInstance();

        /**
         * spinner to allow for user input of their edculation level
         */
        Spinner spinner = (Spinner) findViewById(R.id.textEdLevel);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterUI.this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("Primary Level");
        adapter.add("Secondary Level");
        adapter.add("Tertiary Level");
        adapter.add("Select Education Level (Future)"); //This is the text that will be displayed as hint.

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount()); //set the hint the default selection so it appears on launch.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                textEdLevel = (TextView) view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });

        /**
         * text view to display the user's date of birth
         */
        mDisplayDate = (TextView) findViewById(R.id.textDateOfBirth);

        /**
         * calls date picker to get the date picker dialogue
         */
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegisterUI.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        /**
         * sets the date that the user selected using the  date picker
         */
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("RegisterUI", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                dateOfBirth = day + "/" + month + "/" + year;
                mDisplayDate.setText(dateOfBirth);
            }
        };

        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);



    }

    /**
     * constructor for register user class
     * it implements basic error handling
     */
    private void registerUser() {

        String username = editTextUsername.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String retypePassword = editTextRetypePassword.getText().toString().trim();
        final String edLevel = textEdLevel.getText().toString();


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

        if (!password.equals(retypePassword)){
            editTextPassword.setError("Password do not match. Try again.");
            editTextPassword.requestFocus();
            return;
        }

        if (edLevel.isEmpty()){
            textEdLevel.setError("Please select an education level that you are heading to");
            Toast.makeText(RegisterUI.this, "Please select an education level that you are heading to", Toast.LENGTH_SHORT).show();
            textEdLevel.requestFocus();
            return;
        }




        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();

                    //Get Firebase Current User ID to store user details
                    String user_id = mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("User_DB").child(user_id);

                    //Compile user details in HashMap
                    HashMap newPost = new HashMap();
                    newPost.put("name", name);
                    newPost.put("date_of_birth", dateOfBirth);
                    newPost.put("ed_level", edLevel);

                    //Write data into Firebase Realtime Database
                    current_user_db.setValue(newPost);


                    startActivity(new Intent(RegisterUI.this, StartUI.class));
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    /**
     * method for clicking the Sign Up button or if the user choses to go back and clicks on the View Login button
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignUp:
                registerUser();
                break;

            case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this, StartUI.class));
                break;
        }
    };
}