package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
        LottieAnimationView imageAnimation = findViewById(R.id.imageAnimation);
        ImageView logoSoft = findViewById(R.id.logoSoft);
        imageAnimation.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e("Animation:","start");
                imageAnimation.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("Animation:", "end");
                imageAnimation.setVisibility(View.GONE);
                logoSoft.setVisibility(View.INVISIBLE);
                LinearLayout layoutAnimation = findViewById(R.id.layoutAnimation);
                layoutAnimation.setVisibility(View.GONE);
                RelativeLayout relativeLayoutShowRestaurantsFound = findViewById(R.id.relativeLayoutShowRestaurantsFound);
                relativeLayoutShowRestaurantsFound.setVisibility(View.VISIBLE);
                DatabaseConnection dbConnect = new DatabaseConnection();
                final int[] countRestaurantFound = {0};
                LinearLayout layoutRestaurantsFound = findViewById(R.id.layoutRestaurantsFound);
                //Datos recibidos de restaurantes
                String typeFood = getIntent().getStringExtra("typeFood");
                String hour = getIntent().getStringExtra("hour");

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
                //if para verificar que se encontró al menos un restaurante
                if(IDRestaurantFound[0]!=null){
                    for(int i = 0; i < 3; i++){
                        if(IDRestaurantFound[i] != null){
                            //Obtener los dias que los restuarantes no estan cerrados
                            String IDDIa = dbConnect.getDayClosedRestaurant(IDRestaurantFound[i],String.valueOf(dayWeek));
                            Log.d("IDDia",IDDIa);
                            if(!IDDIa.equals("8")){
                                //Obtener el horario de los restaurantes
                                String schedule[] = dbConnect.getScheduleOfRestaurant(IDRestaurantFound[i],String.valueOf(dayWeek));
                                Log.d("prueba", IDRestaurantFound[i]);
                                Log.d("prueba_horario", schedule[0]);
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
                                                LinearLayout layout = new LinearLayout(ShowRestaurantsFound.this);
                                                layout.addView(showRestaurantsFound(IDRestaurantFound[i]));
                                                layoutRestaurantsFound.addView(layout);
                                            }else{
                                                if(countRestaurantFound[0] == 0){
                                                    LinearLayout layout = new LinearLayout(ShowRestaurantsFound.this);
                                                    layout.addView(errorRestaurantsNotFound());
                                                    layoutRestaurantsFound.addView(layout);
                                                    countRestaurantFound[0] += 1;
                                                }
                                            }
                                        }
                                    }
                                }else{
                                    if(countRestaurantFound[0] == 0){
                                        LinearLayout layout = new LinearLayout(ShowRestaurantsFound.this);
                                        layout.addView(errorRestaurantsNotFound());
                                        layoutRestaurantsFound.addView(layout);
                                        countRestaurantFound[0] += 1;
                                    }
                                }
                            }
                        }
                    }
                }else{
                    if(countRestaurantFound[0] == 0){
                        LinearLayout layout = new LinearLayout(ShowRestaurantsFound.this);
                        layout.addView(errorRestaurantsNotFound());
                        layoutRestaurantsFound.addView(layout);
                        countRestaurantFound[0] += 1;
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
                Log.d("diaSemana","MONDAY");
                return 1;
            case "TUESDAY":
                Log.d("diaSemana","TUESDAY");
                return 2;
            case "WEDNESDAY":
                Log.d("diaSemana","WEDNESDAY");
                return 3;
            case "THURSDAY":
                Log.d("diaSemana","THURSDAY");
                return 4;
            case "FRIDAY":
                Log.d("diaSemana","FRIDAY");
                return 5;
            case "SATURDAY":
                Log.d("diaSemana","SATURDAY");
                return 6;
            case "SUNDAY":
                Log.d("diaSemana","SUNDAY");
                return 7;
            default:
                Log.d("diaSemana","Cero");
                return 0;
        }
    }
    public LinearLayout errorRestaurantsNotFound(){
        Toast.makeText(ShowRestaurantsFound.this,"Error",Toast.LENGTH_SHORT).show();
        LottieAnimationView imageAnimation = new LottieAnimationView(ShowRestaurantsFound.this);
        TextView RestaurantNotFound = new TextView(ShowRestaurantsFound.this);
        TextView actionsToPerform = new TextView(ShowRestaurantsFound.this);
        LinearLayout layout = new LinearLayout(ShowRestaurantsFound.this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        //layout.setPadding(0,30,0,0);
        layout.setOrientation(LinearLayout.VERTICAL);
        RestaurantNotFound.setText("Restaurantes no encontrados");
        actionsToPerform.setText("Pruebe con un horario y/o tipo de comida distinto");
        RestaurantNotFound.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
        actionsToPerform.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        RestaurantNotFound.setGravity(Gravity.CENTER);
        actionsToPerform.setGravity(Gravity.CENTER);

        LinearLayout layoutAnimationError = new LinearLayout(ShowRestaurantsFound.this);
        layoutAnimationError.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1000));
        layoutAnimationError.setGravity(Gravity.CENTER);
        layoutAnimationError.addView(imageAnimation);

        RestaurantNotFound.setTextColor(Color.GRAY);
        actionsToPerform.setTextColor(Color.GRAY);
        imageAnimation.setAnimation(R.raw.tomatoerror);
        imageAnimation.playAnimation();
        imageAnimation.setVisibility(View.VISIBLE);
        imageAnimation.setRepeatCount(10);
        layout.addView(RestaurantNotFound);
        layout.addView(actionsToPerform);
        layout.addView(layoutAnimationError);
        return layout;
    }
    public LinearLayout showRestaurantsFound(String idRestaurant){
        LinearLayout layout = new LinearLayout(ShowRestaurantsFound.this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.setPadding(0,30,0,0);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView nameBusiness = new TextView(ShowRestaurantsFound.this);
        TextView addressBusiness = new TextView(ShowRestaurantsFound.this);
        TextView typeOfFoodBusiness = new TextView(ShowRestaurantsFound.this);
        TextView phoneBusiness = new TextView(ShowRestaurantsFound.this);
        TextView availabilityBusiness = new TextView(ShowRestaurantsFound.this);
        ImageView logoBusiness = new ImageView(ShowRestaurantsFound.this);
        Button btnShowMenu = new Button(this);

        nameBusiness.setGravity(Gravity.CENTER);
        nameBusiness.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
        addressBusiness.setGravity(Gravity.CENTER);
        typeOfFoodBusiness.setGravity(Gravity.CENTER);
        phoneBusiness.setGravity(Gravity.CENTER);
        availabilityBusiness.setGravity(Gravity.CENTER);
        availabilityBusiness.setPadding(0,0,0,50);
        btnShowMenu.setText("Menu");
        btnShowMenu.setBackgroundColor(Color.rgb(255, 128, 0));

        if(idRestaurant.equals("1")){
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
        if(idRestaurant.equals("2")){
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
        if(idRestaurant.equals("16")){
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
        //nameBusiness.setTextSize(20);
        nameBusiness.setTextColor(Color.GRAY);
        addressBusiness.setTextColor(Color.GRAY);
        typeOfFoodBusiness.setTextColor(Color.GRAY);
        phoneBusiness.setTextColor(Color.GRAY);
        availabilityBusiness.setTextColor(Color.GRAY);

        layout.addView(logoBusiness);
        layout.addView(nameBusiness);
        layout.addView(addressBusiness);
        layout.addView(typeOfFoodBusiness);
        layout.addView(phoneBusiness);
        layout.addView(availabilityBusiness);
        layout.addView(btnShowMenu);
        btnShowMenu.setOnClickListener(view -> {
            Toast.makeText(this,"Menu selected", Toast.LENGTH_SHORT).show();
        });
        return layout;
    }
}