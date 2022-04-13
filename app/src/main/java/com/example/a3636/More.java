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
    private final String mainMail = "danielfalcon.87@gmail.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        Button btnJoin = (Button) findViewById(R.id.btnJoin);
        Button btnSubscribe = findViewById(R.id.btnSubscribe);
        EditText txtRestaurantName = findViewById(R.id.txtRestaurantName);
        EditText txtMail = findViewById(R.id.txtMail);
        EditText txtContactName = findViewById(R.id.txtContactName);
        EditText txtAddress = findViewById(R.id.txtAddress);
        EditText txtPhone = findViewById(R.id.txtPhone);
        EditText txtAddEmail = findViewById(R.id.txtAddEmail);
        btnJoin.setOnClickListener(view -> {
            //Redaccion de correo
            String message = "Buen día.\nDeseo que mi restaurante sea agregado a su sistema Softappetit.\nMis datos son los siguientes: \n"+
                    "Nombre del restaurante: " + txtRestaurantName.getText()+"\n"+
                    "Contacto: " + txtContactName.getText()+"\n"+
                    "Dirección: " + txtAddress.getText()+"\n"+
                    "Número telefónico: " + txtPhone.getText()+"\n"+
                    "Espero su respuesta. \nSaludos."+
                    "\nEnviado desde la app de Softappetit";
            sendMessage(String.valueOf(txtMail.getText()), message, "Registro de restaurante");
        });

        btnSubscribe.setOnClickListener(view -> {
            String mailToSubscribe = String.valueOf(txtAddEmail.getText());
            String message = "Buen día. \nDeseo suscribirme a su sistema Softappeti. \nCorreo: " + mailToSubscribe + "\nBuen día. \nEnviado desde la app de Softappetit";
            sendMessage(mailToSubscribe, message, "Suscripcion a sitio web Softappetit");
        });

    }
    private void sendMessage(String mail, String message, String subject){
        String[] TO = {mainMail};
        String[] CC = {mail};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject); //asunto
        emailIntent.putExtra(Intent.EXTRA_TEXT, message); //mensaje

        try {
            startActivity(Intent.createChooser(emailIntent, "Mensaje enviado..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}