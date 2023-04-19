package com.example.raccacoonie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Log_In_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Button log_in_button = findViewById(R.id.button_log_in);
        log_in_button.setEnabled(false);
        log_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadHomeActivity= new Intent(Log_In_Activity.this,Home_Activity.class);
                startActivity(loadHomeActivity);
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