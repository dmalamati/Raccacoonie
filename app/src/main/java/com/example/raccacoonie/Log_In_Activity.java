package com.example.raccacoonie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Log_In_Activity extends AppCompatActivity {
    EditText username;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Button log_in_button = findViewById(R.id.button_log_in);
        log_in_button.setEnabled(true);
        username= findViewById(R.id.editTextUsername);
        password= findViewById(R.id.editTextPassword);
        DatabaseHandler dbh = new DatabaseHandler(this,1);
        log_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEST","CLICKED LOGIN");
                String user=username.getText().toString();
                String pass=password.getText().toString();

                if (dbh.checkPassword(user,pass))
                {
                   Intent loadHomeActivity= new Intent(Log_In_Activity.this,Home_Activity.class);
                    startActivity(loadHomeActivity);



                }
                else
                {
                    Toast.makeText(Log_In_Activity.this, "WRONG LOGIN", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Log_In_Activity.this, dbh.checkPassword(user,pass), Toast.LENGTH_SHORT).show();














            }
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