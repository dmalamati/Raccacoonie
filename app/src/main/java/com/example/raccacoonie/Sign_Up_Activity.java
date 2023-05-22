package com.example.raccacoonie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Sign_Up_Activity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //UI ELEMENTS
        Button actual_sign_up = findViewById(R.id.button_actual_sign_up);
        CheckBox terms_and_conditions = findViewById(R.id.checkBox);

        EditText username= findViewById(R.id.editTextUsername2);
        EditText password=findViewById(R.id.editTextPassword2);
        EditText email=findViewById(R.id.editTextEmail);



        //DB FOR TESTS (AND USER HANDLING)
        DatabaseHandler MyHandler = new DatabaseHandler(this,1);


        actual_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();
                String pass=password.getText().toString();
                String mail=email.getText().toString();

                Intent loadLog_In_Activity= new Intent(Sign_Up_Activity.this,Log_In_Activity.class);
                if (terms_and_conditions.isChecked())
                {

                   //TODO: 1: check if The fields are correct in terms of database
                    // TODO:kill this activity so it cant be accesed by pressing the 'back button'
                    if(MyHandler.checkEmail(mail)){
                        Toast.makeText(Sign_Up_Activity.this, "Email already used in another account", Toast.LENGTH_SHORT).show();
                    }
                    else{
                    User u=new User(user,pass,mail);
                    MyHandler.addUser(u);
                    //Toast.makeText(Sign_Up_Activity.this, MyHandler.getUsers(), Toast.LENGTH_LONG).show();
                        MyHandler.printTable("USER");
                    startActivity(loadLog_In_Activity);
                    finish();

                }}
                else
                {
                    Toast.makeText(Sign_Up_Activity.this, "You need to accept terms and conditions first", Toast.LENGTH_SHORT).show(); //this never runs
                }


            }
        });
        terms_and_conditions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (terms_and_conditions.isChecked())
                {

                    actual_sign_up.setEnabled(true);
                    //Toast.makeText(Sign_Up_Activity.this, "enabled", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    actual_sign_up.setEnabled(false);
                }
            }
        });
    }
}