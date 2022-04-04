package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3636.database.DatabaseConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ShowRestaurantsFound extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_restaurants_found);
        DatabaseConnection dbConnect = new DatabaseConnection();
        //Datos recibidos de restaurantes
        String typeFood = getIntent().getStringExtra("typeFood");
        String hour = getIntent().getStringExtra("hour");

        //Detectar dia de la semana
        /*
        *
        *           Hay un error obteniendo la fecha, devuelve un dia de mas en la fecha
        *
        * */
        String fecha = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        String year = "", month = "", day = "";
        year = fecha.substring(0,4);
        month = fecha.substring(5,7);
        day = fecha.substring(8,10);
        Toast.makeText(this,fecha,Toast.LENGTH_SHORT).show();
        String inputDateStr = String.format("%s/%s/%s", day, month, year);
        Date inputDate = null;
        try {
            inputDate = new SimpleDateFormat("dd/MM/yyyy").parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US).toUpperCase();
        Toast.makeText(this,dayOfWeek,Toast.LENGTH_SHORT).show();
        int dayWeek = getDayToDatabase(dayOfWeek);
        Toast.makeText(this,String.valueOf(dayWeek),Toast.LENGTH_SHORT).show();

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
    public int getDayToDatabase(String day){
        switch (day) {
            case "MONDAY":
                return 1;
            case "TUESDAY":
                return 2;
            case "WEDNESDAY":
                return 3;
            case "THURSDAY":
                return 4;
            case "FRIDAY":
                return 5;
            case "SATURDAY":
                return 6;
            case "SUNDAY":
                return 7;
            default:
                return 0;
        }
    }
}