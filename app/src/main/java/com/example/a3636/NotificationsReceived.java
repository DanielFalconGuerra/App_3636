package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3636.database.DatabaseConnection;

import java.sql.Array;

public class NotificationsReceived extends AppCompatActivity {
    DatabaseConnection connection = new DatabaseConnection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_received);

        //Recuper ubicacion
        String location = ((MyLocation)getApplication()).getLocation();

        String numberNotifications = getIntent().getStringExtra("numberNotifications");
        String date = getIntent().getStringExtra("date");

        if(!numberNotifications.equals("")){
            LinearLayout layoutNotifications = findViewById(R.id.layoutNotifications);

            String[] idsNotifications;
            idsNotifications = connection.getIDNotifications(date);

            for(int i = 0; i < Integer.parseInt(numberNotifications); i++){
                LinearLayout layoutNotificationsReceived = new LinearLayout(this);
                layoutNotificationsReceived.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layoutNotificationsReceived.setGravity(Gravity.CENTER);
                layoutNotificationsReceived.setOrientation(LinearLayout.VERTICAL);

                TextView restaurantNameNot = new TextView(this);
                TextView informationNot = new TextView(this);
                TextView startDateNot = new TextView(this);
                TextView finalDateNot = new TextView(this);
                ImageView logoRestaurantNot = new ImageView(this);

                restaurantNameNot.setTextColor(Color.BLACK);
                informationNot.setTextColor(Color.GRAY);
                startDateNot.setTextColor(Color.GRAY);
                finalDateNot.setTextColor(Color.GRAY);

                restaurantNameNot.setGravity(Gravity.CENTER);
                informationNot.setGravity(Gravity.CENTER);
                startDateNot.setGravity(Gravity.CENTER);
                finalDateNot.setGravity(Gravity.CENTER);

                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(300, 300);
                layoutParams.gravity= Gravity.CENTER;
                layoutParams.setMargins(0,10,0,10);
                logoRestaurantNot.setLayoutParams(layoutParams);
                restaurantNameNot.setTextSize(25);

                try {
                    connection.CONN();
                    String dataNotifications[] = new String[4];
                    dataNotifications = connection.getNotifications(idsNotifications[i]);

                    if(dataNotifications[3].equals("1")){
                        logoRestaurantNot.setImageResource(R.mipmap.lagenareria);
                    }else
                    if(dataNotifications[3].equals("2")){
                        logoRestaurantNot.setImageResource(R.mipmap.arena88);
                    }else
                    if(dataNotifications[3].equals("3")){
                        logoRestaurantNot.setImageResource(R.mipmap.manhattanbar);
                    }else
                    if(dataNotifications[3].equals("4")){
                        logoRestaurantNot.setImageResource(R.mipmap.indigobar);
                    }else
                    if(dataNotifications[3].equals("5")){
                        logoRestaurantNot.setImageResource(R.mipmap.elcurandero);
                    }else
                    if(dataNotifications[3].equals("6")){
                        logoRestaurantNot.setImageResource(R.mipmap.cuevalobo);
                    }else
                    if(dataNotifications[3].equals("7")){
                        logoRestaurantNot.setImageResource(R.mipmap.granchamorro);
                    }else
                    if(dataNotifications[3].equals("8")){
                        logoRestaurantNot.setImageResource(R.mipmap.raices);
                    }else
                    if(dataNotifications[3].equals("9")){
                        logoRestaurantNot.setImageResource(R.mipmap.negritocafe);
                    }else
                    if(dataNotifications[3].equals("10")){
                        logoRestaurantNot.setImageResource(R.mipmap.monchster);
                    }else
                    if(dataNotifications[3].equals("11")){
                        logoRestaurantNot.setImageResource(R.mipmap.buenavida);
                    }else
                    if(dataNotifications[3].equals("12")){
                        logoRestaurantNot.setImageResource(R.mipmap.marinba);
                    }else
                    if(dataNotifications[3].equals("13")){
                        logoRestaurantNot.setImageResource(R.mipmap.happybox);
                    }else
                    if(dataNotifications[3].equals("14")){
                        logoRestaurantNot.setImageResource(R.mipmap.mirrey);
                    }else
                    if(dataNotifications[3].equals("15")){
                        logoRestaurantNot.setImageResource(R.mipmap.laescotilla);
                    }else
                    if(dataNotifications[3].equals("16")){
                        logoRestaurantNot.setImageResource(R.mipmap.costilla);
                    }else
                    if(dataNotifications[3].equals("17")){
                        logoRestaurantNot.setImageResource(R.mipmap.posdata);
                    }else{
                        //Obtener imagen de restaurante
                        byte[] logo = connection.getLogoRestaurant(dataNotifications[3]);
                        if(logo != null){
                            Bitmap bitmap = BitmapFactory.decodeByteArray(logo, 0, logo.length);
                            logoRestaurantNot.setImageBitmap(bitmap);
                        }else{
                            logoRestaurantNot.setImageResource(R.drawable.iconscubiertos);
                        }
                    }

                    connection.CONN();
                    String nameRestaurant = connection.getRestaurant(dataNotifications[3]);

                    restaurantNameNot.setText(nameRestaurant);
                    informationNot.setText(dataNotifications[2]);
                    String informationStartDate = "Aprovecha del " + dataNotifications[0];
                    startDateNot.setText(informationStartDate);
                    String informationFinalDate = "Termina el " + dataNotifications[1];
                    finalDateNot.setText(informationFinalDate);

                    layoutNotificationsReceived.addView(logoRestaurantNot);
                    layoutNotificationsReceived.addView(restaurantNameNot);
                    layoutNotificationsReceived.addView(informationNot);
                    layoutNotificationsReceived.addView(startDateNot);
                    layoutNotificationsReceived.addView(finalDateNot);

                    layoutNotifications.addView(layoutNotificationsReceived);
                }catch (Exception e){
                    Toast.makeText(this,"Ha ocurrido un error, intentelo más tarde", Toast.LENGTH_LONG).show();
                }
            }
        }else{
            LinearLayout layoutNotifications = findViewById(R.id.layoutNotifications);

            LinearLayout layoutNotificationsReceived = new LinearLayout(this);
            layoutNotificationsReceived.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutNotificationsReceived.setGravity(Gravity.CENTER);
            layoutNotificationsReceived.setOrientation(LinearLayout.VERTICAL);

            TextView sinNotificaciones = new TextView(this);
            sinNotificaciones.setTextColor(Color.GRAY);
            sinNotificaciones.setGravity(Gravity.CENTER);

            sinNotificaciones.setTextSize(25);

            layoutNotificationsReceived.addView(sinNotificaciones);
            layoutNotifications.addView(layoutNotificationsReceived);
        }
    }
}