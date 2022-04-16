package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a3636.database.DatabaseConnection;
import com.example.a3636.encryption.BCrypt;

public class RegisterUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        DatabaseConnection dbConnection = new DatabaseConnection();
        Button BtnRegisterUser = findViewById(R.id.BtnRegisterUser);
        BtnRegisterUser.setBackgroundColor(Color.rgb(255, 128, 0));
        EditText newUserET = findViewById(R.id.newUserET);
        EditText newMailET = findViewById(R.id.newMailET);
        EditText newNameET = findViewById(R.id.newNameET);
        EditText newPasswordET = findViewById(R.id.newPasswordET);
        EditText repeatPasswordET = findViewById(R.id.repeatPasswordET);

        BtnRegisterUser.setOnClickListener(view -> {
            String user = newUserET.getText().toString();
            dbConnection.CONN();
            boolean existingUser = dbConnection.checkIfTheUserAlreadyExists(user);
            if(existingUser){
                Toast.makeText(this, "Error, el usuario ingresado ya existe", Toast.LENGTH_SHORT).show();
            }else{
                String eMail = newMailET.getText().toString();
                String name = newNameET.getText().toString();
                String password = newPasswordET.getText().toString();
                String repeatPassword = repeatPasswordET.getText().toString();

                if(password.equals(repeatPassword)){
                    //Obtener el último ID de los usuarios
                    dbConnection.CONN();
                    String IDUser = String.valueOf(Integer.parseInt(dbConnection.getLastIDUser()) + 1);
                    //Cifrar password
                    String hash = BCrypt.hashpw(password, BCrypt.gensalt(8));
                    //Crear nuevo usuario
                    String response = dbConnection.createNewUser(IDUser, hash, user, eMail, name, "1");
                    Toast.makeText(this,response, Toast.LENGTH_LONG).show();
                    if(response.equals("Usuario creado con éxito")){
                        Intent login = new Intent(this, Login.class);
                        startActivity(login);
                    }
                }else{
                    Toast.makeText(this,"Error, las contraseñas ingresadas no son iguales", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}