package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class More extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        Button btnJoin = (Button) findViewById(R.id.btnJoin);
        EditText txtRestaurantName = findViewById(R.id.txtRestaurantName);
        EditText txtMail = findViewById(R.id.txtMail);
        EditText txtContactName = findViewById(R.id.txtContactName);
        EditText txtAddress = findViewById(R.id.txtAddress);
        EditText txtPhone = findViewById(R.id.txtPhone);
        btnJoin.setOnClickListener(view -> {
            //Redaccion de correo
            String message = "Buen día.\nDeseo que mi restaurante sea agregado a su sistema Softappeti.\nMis datos son los siguientes: \n"+
                    "Nombre del restaurante: " + txtRestaurantName+"\n"+
                    "Contacto: " + txtContactName+"\n"+
                    "Dirección: " + txtAddress+"\n"+
                    "Número telefónico: " + txtPhone+"\n"+
                    "Espero su respuesta. \nSaludos."+
                    "\nEnviado desde la app de Softappeti";
            String[] TO = {"danielfalcon.87@gmail.com"};
            String[] CC = {String.valueOf(txtMail)};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");

            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Registro de restaurante"); //asunto
            emailIntent.putExtra(Intent.EXTRA_TEXT, message); //mensaje

            try {
                startActivity(Intent.createChooser(emailIntent, "Mensaje enviado..."));
                finish();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this,
                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}