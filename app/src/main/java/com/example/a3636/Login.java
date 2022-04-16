package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a3636.database.DatabaseConnection;
import com.example.a3636.encryption.BCrypt;

public class Login extends AppCompatActivity {
    String user = "";
    String password = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DatabaseConnection dbConnect = new DatabaseConnection();
        Button BtnLogin = findViewById(R.id.BtnLogin);
        BtnLogin.setBackgroundColor(Color.rgb(255, 128, 0));
        EditText userEditText = findViewById(R.id.userEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        userEditText.setText("CeLiu");
        passwordEditText.setText("$2y$10$8zfh/gzWl0JHtbp88nLeKukFSWc3Pr0Aty7i7lEaxJa2Y7cSvQVYu");

        BtnLogin.setOnClickListener(view -> {
            user = userEditText.getText().toString();
            password = passwordEditText.getText().toString();
            String hash = BCrypt.hashpw(user, BCrypt.gensalt());

            boolean s = BCrypt.checkpw(user, hash);
            //Toast.makeText(this, String.valueOf(s), Toast.LENGTH_SHORT).show();
            dbConnect.CONN();
            String res = dbConnect.loginCheck(user, password);
            if(res.equals("OK")){
                Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}