package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
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
        //Iniciar y terminar animacion
        LottieAnimationView imageToTest = findViewById(R.id.imageToTest);
        imageToTest.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e("Animation:","start");
                imageToTest.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("Animation:","end");
                imageToTest.setVisibility(View.GONE);

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
                Toast.makeText(ShowRestaurantsFound.this,fecha,Toast.LENGTH_SHORT).show();
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
                int dayWeek = getDayToDatabase(dayOfWeek);

                //Obtener tipos de comida y IDs
                String getIDsAndTypesFood[][] = new String[2][14];
                getIDsAndTypesFood = dbConnect.getTypesFoodAndIDs();
                int idTypeFood = 0;
                for(int i = 0; i < 14; i++){
                    if(typeFood.equals(getIDsAndTypesFood[i][1])){
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
                        if(idTypeFood == Integer.parseInt(IDsRestaurantes[i][j])){
                            //Consultar ID de Restaurante
                            IDRestaurantFound[cont] = dbConnect.getIDRestaurant(String.valueOf(i + 1));
                            cont++;
                        }
                    }
                }

                for(int i = 0; i < 3; i++){
                    if(IDRestaurantFound[i] != null){
                        //Obtener el horario de los restaurantes
                        String schedule[] = dbConnect.getScheduleOfRestaurant(IDRestaurantFound[i],String.valueOf(dayWeek));
                        Log.d("prueba", IDRestaurantFound[i]);
                        Log.d("prueba", schedule[0]);
                        String openingTime = schedule[0];
                        String closingTime = schedule[1];
                        Log.d("prueba", openingTime);
                        if(!(openingTime.equals("") && closingTime.equals(""))){
                            //Obtener los horarios reales a partir de los IDs
                            String schedulesReceivedOne = dbConnect.getSchedule(openingTime);
                            String schedulesReceivedTwo = dbConnect.getSchedule(closingTime);
                            //Comparar horarios para revisar que el restaurante se encuentre abierto
                            if(schedulesReceivedOne.equals(hour)){
                            }else{
                                if(schedulesReceivedTwo.equals(hour)){
                                    Toast.makeText(ShowRestaurantsFound.this,"Error",Toast.LENGTH_SHORT).show();
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
                                        LinearLayout layout = findViewById(R.id.ShowRestaurantFound);
                                        layout.setOrientation(LinearLayout.VERTICAL);
                                        TextView nameBusiness = new TextView(ShowRestaurantsFound.this);
                                        TextView addressBusiness = new TextView(ShowRestaurantsFound.this);
                                        TextView typeOfFoodBusiness = new TextView(ShowRestaurantsFound.this);
                                        TextView phoneBusiness = new TextView(ShowRestaurantsFound.this);
                                        TextView availabilityBusiness = new TextView(ShowRestaurantsFound.this);
                                        ImageView logoBusiness = new ImageView(ShowRestaurantsFound.this);

                                        nameBusiness.setGravity(Gravity.CENTER);
                                        nameBusiness.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
                                        addressBusiness.setGravity(Gravity.CENTER);
                                        typeOfFoodBusiness.setGravity(Gravity.CENTER);
                                        phoneBusiness.setGravity(Gravity.CENTER);
                                        availabilityBusiness.setGravity(Gravity.CENTER);
                                        availabilityBusiness.setPadding(0,0,0,50);

                                        if(IDRestaurantFound[i].equals("1")){
                                            nameBusiness.setText("La Genarería");
                                            addressBusiness.setText("Dirección: Irapuato, Guanajuato. Plaza 3636 Gómez Morín");
                                            typeOfFoodBusiness.setText("Tipos de Comida: Americana, Restaurante - Bar, Bar");
                                            phoneBusiness.setText("Teléfono: 462 200 4863");
                                            availabilityBusiness.setText("Disponibilidad el día de hoy: 14:00 a 23:00");
                                            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(300, 300);
                                            layoutParams.gravity= Gravity.CENTER;
                                            logoBusiness.setLayoutParams(layoutParams);
                                            logoBusiness.setImageResource(R.mipmap.lagenareria);
                                        }else
                                        if(IDRestaurantFound[i].equals("2")){
                                            nameBusiness.setText("Arena 88");
                                            addressBusiness.setText("Dirección: Irapuato, Guanajuato. Plaza 3636 Gómez Morín");
                                            typeOfFoodBusiness.setText("Tipos de Comida: Bar, Restaurante - Bar, Mariscos");
                                            phoneBusiness.setText("Teléfono: 462 688 3664");
                                            availabilityBusiness.setText("Disponibilidad el día de hoy: 12:00 a 23:00");
                                            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(300, 300);
                                            layoutParams.gravity= Gravity.CENTER;
                                            logoBusiness.setLayoutParams(layoutParams);
                                            logoBusiness.setImageResource(R.mipmap.arena88);
                                        }else
                                        if(IDRestaurantFound[i].equals("16")){
                                            nameBusiness.setText("Costilla Winebarlechon");
                                            addressBusiness.setText("Dirección: Irapuato, Guanajuato. Plaza 3636 Gómez Morín");
                                            typeOfFoodBusiness.setText("Tipos de Comida: Bar, Restaurante - Bar, Café");
                                            phoneBusiness.setText("Teléfono: 462 607 9612");
                                            availabilityBusiness.setText("Disponibilidad el día de hoy: 10:00 a 22:00");
                                            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(300, 300);
                                            layoutParams.gravity= Gravity.CENTER;
                                            logoBusiness.setLayoutParams(layoutParams);
                                            logoBusiness.setImageResource(R.mipmap.costilla);
                                        }else
                                            nameBusiness.setText("Error");

                                        layout.addView(logoBusiness);
                                        layout.addView(nameBusiness);
                                        layout.addView(addressBusiness);
                                        layout.addView(typeOfFoodBusiness);
                                        layout.addView(phoneBusiness);
                                        layout.addView(availabilityBusiness);
                                    }else{
                                        Toast.makeText(ShowRestaurantsFound.this,"Error",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }else{
                            Toast.makeText(ShowRestaurantsFound.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e("Animation:","cancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.e("Animation:","repeat");
            }
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