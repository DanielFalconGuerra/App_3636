package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3636.database.DatabaseConnection;

public class ShowRestaurantsFound extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_restaurants_found);
        DatabaseConnection dbConnect = new DatabaseConnection();

        Button btnRealizarConsulta = findViewById(R.id.btnRealizarConsulta);
        btnRealizarConsulta.setOnClickListener(view -> {
            dbConnect.CONN();
            String datos = dbConnect.MortarRestaurants();
            LinearLayout layoutDatos = new LinearLayout(this);
            layoutDatos.setOrientation(LinearLayout.VERTICAL);
            TextView tvDatos = new TextView(this);
            tvDatos.setText(datos);
            layoutDatos.addView(tvDatos);
            Toast.makeText(this,datos,Toast.LENGTH_SHORT).show();
        });
    }
}