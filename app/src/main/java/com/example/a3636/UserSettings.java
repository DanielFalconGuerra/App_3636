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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3636.database.DatabaseConnection;
import com.example.a3636.encryption.BCrypt;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserSettings extends AppCompatActivity {
    DatabaseConnection connection = new DatabaseConnection();
    byte[] imageReceived = null;
    final int SELECT_FILE = 1;
    Button btnSelectImageSettings;
    String passwordReceived = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        //Recuper ubicacion
        String location = ((MyLocation)getApplication()).getLocation();
        Toast.makeText(this,"Ubicacion: " + location,Toast.LENGTH_SHORT).show();

        ImageView imageUserSessionSettings = findViewById(R.id.imageUserSessionSettings);
        TextView newUserSettings = findViewById(R.id.newUserSettings);
        TextView newMailSettings = findViewById(R.id.newMailSettings);
        TextView newNameSettings = findViewById(R.id.newNameSettings);
        TextView newPasswordSettings = findViewById(R.id.newPasswordSettings);
        TextView newPasswordSettingsToUpdate = findViewById(R.id.newPasswordSettingsToUpdate);

        btnSelectImageSettings = findViewById(R.id.btnSelectImageSettings);
        Button BtnUpdateUserInformation = findViewById(R.id.BtnUpdateUserInformation);
        Button BtnChangeLocation = findViewById(R.id.BtnChangeLocation);
        btnSelectImageSettings.setBackgroundColor(Color.rgb(255, 128, 0));
        BtnUpdateUserInformation.setBackgroundColor(Color.rgb(255, 128, 0));
        BtnChangeLocation.setBackgroundColor(Color.rgb(255, 128, 0));

        String userName = getIntent().getStringExtra("userName");

        newUserSettings.setText(userName);
        //Obtener ID del Usuario
        connection.CONN();
        String ID = connection.getIDUser(userName);
        Toast.makeText(this,ID,Toast.LENGTH_SHORT).show();
        //Obtener imagen de usuario
        imageReceived = connection.getImage(ID);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageReceived, 0, imageReceived.length);
        imageUserSessionSettings.setImageBitmap(bitmap);
        String dataUser[] = new String[3];
        connection.CONN();
        dataUser = connection.getDataUser(ID);
        passwordReceived = dataUser[0];
        newMailSettings.setText(dataUser[1]);
        newNameSettings.setText(dataUser[2]);
        //Codigo para verificar que la contraseña sea correcta
        BtnUpdateUserInformation.setOnClickListener(view -> {
            String user = newUserSettings.getText().toString();
            String mail = newMailSettings.getText().toString();
            String name = newNameSettings.getText().toString();
            String password = newPasswordSettings.getText().toString();
            String newPassword = newPasswordSettingsToUpdate.getText().toString();

            String hash = "";
            String response = "";
            if(user.equals("")||mail.equals("")||name.equals("")){
                Toast.makeText(this,"Los campos Usuario, Correo y Nombre no deben estar vaciós para continuar", Toast.LENGTH_LONG).show();
            }else{
                if(!password.equals("")){
                    boolean passwordOK = BCrypt.checkpw(password, passwordReceived);
                    if(passwordOK){
                        response = connection.updateMailUser(mail, user);
                        response = connection.updateNameUser(name, user);
                        hash = BCrypt.hashpw(newPassword, BCrypt.gensalt(8));
                        response = connection.updatePasswordUser(hash, user);
                        response = connection.updateImageUser(imageReceived, user);
                        Toast.makeText(this,response, Toast.LENGTH_SHORT).show();
                        Intent Login = new Intent(this,Login.class);
                        startActivity(Login);
                    }else{
                        Toast.makeText(this,"La contraseña ingresada es incorrecta", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    response = connection.updateMailUser(mail, user);
                    response = connection.updateNameUser(name, user);
                    response = connection.updateImageUser(imageReceived, user);
                    Toast.makeText(this,response, Toast.LENGTH_SHORT).show();

                }
            }
        });
        btnSelectImageSettings.setOnClickListener(view -> {
            abrirGaleria(btnSelectImageSettings);
        });
        BtnChangeLocation.setOnClickListener(view -> {
            Intent main = new Intent(this,MainActivity.class);
            startActivity(main);

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
                            imageReceived = byteArray;
                            btnSelectImageSettings.setText("Cambiar imagen");
                        }
                    }
                }
                break;
        }
    }
}