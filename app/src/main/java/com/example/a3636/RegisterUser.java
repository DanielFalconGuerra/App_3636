package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a3636.database.DatabaseConnection;
import com.example.a3636.encryption.BCrypt;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;

public class RegisterUser extends AppCompatActivity {
    final int SELECT_FILE = 1;
    byte[] image = null;
    Button btnSelectImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        DatabaseConnection dbConnection = new DatabaseConnection();
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSelectImage.setBackgroundColor(Color.rgb(255, 128, 0));
        btnSelectImage.setOnClickListener(view -> {
            abrirGaleria(btnSelectImage);
        });

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
                    String response = dbConnection.createNewUser(IDUser, hash, user, eMail, name, "1", image);
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
    public void abrirGaleria(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                SELECT_FILE);
    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImageUri = null;
        Uri selectedImage;

        String filePath = null;
        switch (requestCode) {
            case SELECT_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath=selectedImage.getPath();
                    if (requestCode == SELECT_FILE) {

                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                            /*ImageView mImg = (ImageView) findViewById(R.id.imageTest);
                            mImg.setImageBitmap(bmp);*/
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            bmp.recycle();
                            image = byteArray;
                            btnSelectImage.setText("Cambiar imagen");
                        }
                    }
                }
                break;
        }
    }
}