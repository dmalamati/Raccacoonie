package com.example.raccacoonie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Log_In_Activity extends AppCompatActivity {

    EditText username;
    EditText password;
    DatabaseHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Button log_in_button = findViewById(R.id.button_log_in);
            log_in_button.setEnabled(true);
            username= findViewById(R.id.editTextUsername);
            password= findViewById(R.id.editTextPassword);
            dbh=new DatabaseHandler(this,1);
            DatabaseHandler dbh = new DatabaseHandler(this,1);
            log_in_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TEST","CLICKED LOGIN");
                    String user=username.getText().toString();
                    String pass=password.getText().toString();
                    dbh.printTable("USER");


                if (dbh.checkPassword(user,pass))
                {
                  //  Intent loadHomeActivity= new Intent(Log_In_Activity.this,Home_Activity.class);
                    //startActivity(loadHomeActivity);
                    //Toast.makeText(Log_In_Activity.this, "Hello "+user, Toast.LENGTH_SHORT).show();
                   Intent loadHomeActivity= new Intent(Log_In_Activity.this,Home_Activity.class);
                   Toast.makeText(getApplicationContext(),"Welcome!", Toast.LENGTH_SHORT);
                    if (sharedPreferences != null) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("id", dbh.getid(user));
                        editor.apply();
                    } else {

                        // Handle the case when the shared preferences object is null
                        Log.d("error","its null");
                        // Log an error or take appropriate action
                    }
                   startActivity(loadHomeActivity);



                }
                else
                {
                    Toast.makeText(Log_In_Activity.this, "Wrong username or password. Try making a new account", Toast.LENGTH_SHORT).show();
                }
                }
                //Toast.makeText(Log_In_Activity.this, dbh.checkPassword(user,pass), Toast.LENGTH_SHORT).show();




        });
        Button sign_up_button = findViewById(R.id.button_sign_up);
        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadSignUpActivity= new Intent(Log_In_Activity.this,Sign_Up_Activity.class);
                startActivity(loadSignUpActivity);
            }
        });
    }
}