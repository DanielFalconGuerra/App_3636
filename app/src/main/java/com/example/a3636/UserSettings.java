package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3636.database.DatabaseConnection;

public class UserSettings extends AppCompatActivity {
    DatabaseConnection connection = new DatabaseConnection();
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
        Button btnSelectImageSettings = findViewById(R.id.btnSelectImageSettings);
        Button BtnRegisterUser = findViewById(R.id.BtnRegisterUser);
        Button BtnChangeLocation = findViewById(R.id.BtnChangeLocation);
        btnSelectImageSettings.setBackgroundColor(Color.rgb(255, 128, 0));
        BtnRegisterUser.setBackgroundColor(Color.rgb(255, 128, 0));
        BtnChangeLocation.setBackgroundColor(Color.rgb(255, 128, 0));

        String userName = getIntent().getStringExtra("userName");

        newUserSettings.setText(userName);
        //Obtener ID del Usuario
        connection.CONN();
        String ID = connection.getIDUser(userName);
        Toast.makeText(this,ID,Toast.LENGTH_SHORT).show();
        //Obtener imagen de usuario
        byte[] imageReceived = connection.getImage(ID);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageReceived, 0, imageReceived.length);
        imageUserSessionSettings.setImageBitmap(bitmap);
        String dataUser[] = new String[3];
        connection.CONN();
        dataUser = connection.getDataUser(ID);
        //newPasswordSettings.setText(dataUser[0]);
        newMailSettings.setText(dataUser[1]);
        newNameSettings.setText(dataUser[2]);
        //Codigo para verificar que la contraseña sea correcta

    }
}