package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

public class RegisterUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Button BtnRegisterUser = findViewById(R.id.BtnRegisterUser);
        BtnRegisterUser.setBackgroundColor(Color.rgb(255, 128, 0));
    }
}