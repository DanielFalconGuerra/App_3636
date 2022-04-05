package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

        //Obtener tipos de comida y IDs
        String getIDsAndTypesFood[][] = new String[2][14];
        getIDsAndTypesFood = dbConnect.getTypesFoodAndIDs();
        int idTypeFood = 0;
        for(int i = 0; i < 14; i++){
            //Toast.makeText(this,getIDsAndTypesFood[i][j],Toast.LENGTH_SHORT).show();
            if(typeFood.equals(getIDsAndTypesFood[i][1])){
                //Toast.makeText(this," IDRestaurante: " + getIDsAndTypesFood[i][0],Toast.LENGTH_SHORT).show();
                idTypeFood = Integer.parseInt(getIDsAndTypesFood[i][0]);
                break;
            }
        }

        //Obtener tipos de comida de cada restaurante
        String IDsRestaurantes[][] = new String[3][3];
        String IDRestaurantFound[] = new String[17];

        IDsRestaurantes = dbConnect.getTypesFoodEachRestaurant();
        int cont = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                //Toast.makeText(this,IDsRestaurantes[i][j],Toast.LENGTH_SHORT).show();
                if(idTypeFood == Integer.parseInt(IDsRestaurantes[i][j])){
                    //Consultar ID de Restaurante
                    //IDRestaurantFound[cont] = String.valueOf(i + 1);
                    IDRestaurantFound[cont] = dbConnect.getIDRestaurant(String.valueOf(i + 1));
                    cont++;
                }
            }
        }

        for(int i = 0; i < 3; i++){
            if(IDRestaurantFound[i] != null){
                Toast.makeText(this,"ID Restaurant Found " + IDRestaurantFound[i],Toast.LENGTH_SHORT).show();
                //Obtener el horario de los restaurantes
                String schedule[] = dbConnect.getScheduleOfRestaurant(IDRestaurantFound[i],String.valueOf(dayWeek));
                Log.d("prueba", IDRestaurantFound[i]);
                Log.d("prueba", schedule[0]);
                String openingTime = schedule[0];
                String closingTime = schedule[1];
                Log.d("prueba", openingTime);
                Toast.makeText(this,"openingTime: "+openingTime+" "+closingTime,Toast.LENGTH_SHORT).show();
                if(!(openingTime.equals("") && closingTime.equals(""))){
                    Toast.makeText(this,"Horario: "+openingTime+" "+closingTime,Toast.LENGTH_SHORT).show();
                    //Obtener los horarios reales a partir de los IDs
                    String schedulesReceivedOne = dbConnect.getSchedule(openingTime);
                    String schedulesReceivedTwo = dbConnect.getSchedule(closingTime);
                    Toast.makeText(this,"Horario a probar: "+schedulesReceivedOne+" "+closingTime,Toast.LENGTH_SHORT).show();
                    //Comparar horarios para revisar que el restaurante se encuentre abierto
                    if(schedulesReceivedOne.equals(hour)){
                        Toast.makeText(this,"ID Restaurant Found Tried " + IDRestaurantFound[i],Toast.LENGTH_SHORT).show();
                    }else{
                        if(schedulesReceivedTwo.equals(hour)){
                            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
                        }else{
                            Date timeToReview = null;
                            Date startTime = null;
                            Date finalTime = null;
                            try {
                                timeToReview = new SimpleDateFormat("HH:mm").parse(hour.trim());
                                startTime = new SimpleDateFormat("HH:mm").parse(schedulesReceivedOne.trim());
                                finalTime = new SimpleDateFormat("HH:mm").parse(schedulesReceivedTwo.trim());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if(timeToReview.after(startTime) && timeToReview.before(finalTime)){
                                //Obtener datos del restaurante
                                Toast.makeText(this,"ID Restaurant Found Tried " + IDRestaurantFound[i],Toast.LENGTH_SHORT).show();
                                LinearLayout layout = new LinearLayout(this);
                                layout.setOrientation(LinearLayout.VERTICAL);
                                TextView nameBusiness = new TextView(this);
                                if(IDRestaurantFound[i].equals("1")){
                                    nameBusiness.setText("La Genarería");
                                }else
                                if(IDRestaurantFound[i].equals("2")){
                                    nameBusiness.setText("Arena 88");
                                }else
                                if(IDRestaurantFound[i].equals("16")){
                                    nameBusiness.setText("Costilla Winebarlechon");
                                }else
                                    nameBusiness.setText("Error");
                                layout.addView(nameBusiness);
                            }else{
                                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }else{
                    Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        }

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