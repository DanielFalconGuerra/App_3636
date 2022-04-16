package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        Button BtnRegister = findViewById(R.id.BtnRegister);
        BtnLogin.setBackgroundColor(Color.rgb(255, 128, 0));
        BtnRegister.setBackgroundColor(Color.rgb(255, 128, 0));
        EditText userEditText = findViewById(R.id.userEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        BtnLogin.setOnClickListener(view -> {
            user = userEditText.getText().toString();
            password = passwordEditText.getText().toString();
            //String hash = BCrypt.hashpw(password, BCrypt.gensalt(8));
            dbConnect.CONN();
            String[] userData = new String[2];
            userData = dbConnect.loginCheck(user);
            String hash = userData[0];
            String IDType = userData[1];
            boolean passwordOK = BCrypt.checkpw(password, hash);

            if(passwordOK){
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
                int IDTypeUser = Integer.valueOf(IDType);
                switch (IDTypeUser){
                    case 1:
                        Toast.makeText(this, "Usuario detectado", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(this, "Administrador detectado", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(this, "Empresa detectada", Toast.LENGTH_SHORT).show();
                        break;
                }
            }else{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        BtnRegister.setOnClickListener(view -> {
            Intent registerUser = new Intent(this, RegisterUser.class);
            startActivity(registerUser);
        });
    }
}