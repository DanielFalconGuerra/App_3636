package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3636.database.DatabaseConnection;
import com.google.android.gms.common.util.ArrayUtils;

import java.sql.Connection;
import java.util.ArrayList;

public class ModifyRestaurantInformation extends AppCompatActivity {
    DatabaseConnection connection = new DatabaseConnection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_restaurant_information);
        String ID = "";

        //Recuper ubicacion
        String location = ((MyLocation)getApplication()).getLocation();
        Toast.makeText(this,"Ubicacion: " + location,Toast.LENGTH_SHORT).show();

        TextView nameBusinessSession = findViewById(R.id.nameBusinessSession);
        ImageView imageBusinessSession = findViewById(R.id.imageBusinessSession);
        TextView logInBusiness = findViewById(R.id.logInBusiness);
        ImageView notificationsIMBusiness = findViewById(R.id.notificationsIMBusiness);
        ImageView settingsIMBusiness = findViewById(R.id.settingsIMBusiness);

        TextView newNameRestaurantTV = findViewById(R.id.newNameRestaurantTV);
        TextView newAddressRestaurantTV = findViewById(R.id.newAddressRestaurantTV);
        TextView newDescriptionRestaurantTV = findViewById(R.id.newDescriptionRestaurantTV);
        TextView newPhoneRestaurantTV = findViewById(R.id.newPhoneRestaurantTV);

        EditText newNameRestaurantET = findViewById(R.id.newNameRestaurantET);
        EditText newAddressRestaurantET = findViewById(R.id.newAddressRestaurantET);
        EditText newDescriptionRestaurantET = findViewById(R.id.newDescriptionRestaurantET);
        EditText newPhoneRestaurantET = findViewById(R.id.newPhoneRestaurantET);

        Button btnUpdateInformationRestaurant = findViewById(R.id.btnUpdateInformationRestaurant);
        Button btnUpdateHorariesRestaurant = findViewById(R.id.btnUpdateHorariesRestaurant);

        LinearLayout horariesLayoutColumns = findViewById(R.id.horariesLayoutColumns);
        LinearLayout horariesLayoutMonday = findViewById(R.id.horariesLayoutMonday);
        LinearLayout horariesLayoutTuesday = findViewById(R.id.horariesLayoutTuesday);
        LinearLayout horariesLayoutWednesday = findViewById(R.id.horariesLayoutWednesday);
        LinearLayout horariesLayoutThursday = findViewById(R.id.horariesLayoutThursday);
        LinearLayout horariesLayoutFriday = findViewById(R.id.horariesLayoutFriday);
        LinearLayout horariesLayoutSaturday = findViewById(R.id.horariesLayoutSaturday);
        LinearLayout horariesLayoutSunday = findViewById(R.id.horariesLayoutSunday);

        EditText newOpeningHoraryRestaurantMondayET = findViewById(R.id.newOpeningHoraryRestaurantMondayET);
        EditText newClosingHoraryRestaurantMondayET = findViewById(R.id.newClosingHoraryRestaurantMondayET);
        EditText newOpeningHoraryRestaurantTuesdayET = findViewById(R.id.newOpeningHoraryRestaurantTuesdayET);
        EditText newClosingHoraryRestaurantTuesdayET = findViewById(R.id.newClosingHoraryRestaurantTuesdayET);
        EditText newOpeningHoraryRestaurantWednesdayET = findViewById(R.id.newOpeningHoraryRestaurantWednesdayET);
        EditText newClosingHoraryRestaurantWednesdayET = findViewById(R.id.newClosingHoraryRestaurantWednesdayET);
        EditText newOpeningHoraryRestaurantThursdayET = findViewById(R.id.newOpeningHoraryRestaurantThursdayET);
        EditText newClosingHoraryRestaurantThursdayET = findViewById(R.id.newClosingHoraryRestaurantThursdayET);
        EditText newOpeningHoraryRestaurantFridayET = findViewById(R.id.newOpeningHoraryRestaurantFridayET);
        EditText newClosingHoraryRestaurantFridayET = findViewById(R.id.newClosingHoraryRestaurantFridayET);
        EditText newOpeningHoraryRestaurantSaturdayET = findViewById(R.id.newOpeningHoraryRestaurantSaturdayET);
        EditText newClosingHoraryRestaurantSaturdayET = findViewById(R.id.newClosingHoraryRestaurantSaturdayET);
        EditText newOpeningHoraryRestaurantSundayET = findViewById(R.id.newOpeningHoraryRestaurantSundayET);
        EditText newClosingHoraryRestaurantSundayET = findViewById(R.id.newClosingHoraryRestaurantSundayET);

        btnUpdateInformationRestaurant.setBackgroundColor(Color.rgb(255, 128, 0));
        btnUpdateHorariesRestaurant.setBackgroundColor(Color.rgb(255, 128, 0));

        String userName = getIntent().getStringExtra("userName");

        nameBusinessSession.setText(userName);
        //Obtener ID del Usuario
        connection.CONN();
        ID = connection.getIDUser(userName);
        //Obtener imagen de usuario
        byte[] imageReceived = connection.getImage(ID);
        if(imageReceived != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageReceived, 0, imageReceived.length);
            imageBusinessSession.setImageBitmap(bitmap);
        }

        logInBusiness.setText("Cambiar de usuario");
        logInBusiness.setOnClickListener(view -> {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        });

        settingsIMBusiness.setOnClickListener(view -> {
            String userConnected = nameBusinessSession.getText().toString();
            Intent settings = new Intent(this, UserSettings.class);
            settings.putExtra("userName",userConnected);
            startActivity(settings);
        });

        Spinner spinnerRestaurant = findViewById(R.id.spinnerRestaurantBusiness);

        //Obtener restaurantes añadidos por el usuario y su informacion

        //Falta obtener el ID del usuario
        connection.CONN();
        ArrayList<String[]> restaurantsAdded = connection.getRestaurantInformationByUserAdmin(ID);

       if(restaurantsAdded == null || restaurantsAdded.size()==0){
           String error[] = new String[1];
           error[0] = "Aun no tiene restaurantes añadidos";
           ArrayAdapter<CharSequence> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, error);
           adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
           spinnerRestaurant.setAdapter(adapter);
           spinnerRestaurant.setBackgroundColor(Color.BLACK);
        }else{
            String restaurants[] = new String[restaurantsAdded.size()];
            for(int i = 0; i < restaurantsAdded.size(); i++){
                String[] elements = restaurantsAdded.get(i);
                restaurants[i] = elements[2];
            }
           ArrayAdapter<CharSequence> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, restaurants);
           adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
           spinnerRestaurant.setAdapter(adapter);
           spinnerRestaurant.setBackgroundColor(Color.BLACK);
           /*spinnerRestaurant.setOnTouchListener(new View.OnTouchListener() {
               @Override
               public boolean onTouch(View v, MotionEvent event) {
                   if (event.getAction() == MotionEvent.ACTION_UP) {
                       String restaurant = spinnerRestaurant.getSelectedItem().toString();
                       Toast.makeText(ModifyRestaurantInformation.this, restaurant ,Toast.LENGTH_SHORT).show();
                   }
                   return false;
               }
           });*/
           spinnerRestaurant.setOnItemSelectedListener(
                   new AdapterView.OnItemSelectedListener() {
                       public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                           Toast.makeText(spn.getContext(), "Has seleccionado " + spn.getItemAtPosition(posicion).toString(), Toast.LENGTH_LONG).show();
                           newNameRestaurantTV.setVisibility(View.VISIBLE);
                           newAddressRestaurantTV.setVisibility(View.VISIBLE);
                           newDescriptionRestaurantTV.setVisibility(View.VISIBLE);
                           newPhoneRestaurantTV.setVisibility(View.VISIBLE);

                           newNameRestaurantET.setVisibility(View.VISIBLE);
                           newAddressRestaurantET.setVisibility(View.VISIBLE);
                           newDescriptionRestaurantET.setVisibility(View.VISIBLE);
                           newPhoneRestaurantET.setVisibility(View.VISIBLE);

                           btnUpdateInformationRestaurant.setVisibility(View.VISIBLE);


                           int IDRestaurant = (int) spn.getSelectedItemId();
                           newNameRestaurantET.setText(restaurantsAdded.get(IDRestaurant)[2]);
                           newAddressRestaurantET.setText(restaurantsAdded.get(IDRestaurant)[1]);
                           newDescriptionRestaurantET.setText(restaurantsAdded.get(IDRestaurant)[3]);
                           newPhoneRestaurantET.setText(restaurantsAdded.get(IDRestaurant)[4]);

                           btnUpdateInformationRestaurant.setOnClickListener(view -> {
                               if(newNameRestaurantET.getText().toString().equals("")||newAddressRestaurantET.getText().toString().equals("")||
                                       newDescriptionRestaurantET.getText().toString().equals("")||newPhoneRestaurantET.getText().toString().equals("")){
                                   Toast.makeText(ModifyRestaurantInformation.this,"Los campos no deben estar vacios",Toast.LENGTH_SHORT).show();
                               }else{
                                   String response = "";
                                   response = connection.updateNameRestaurant(newNameRestaurantET.getText().toString(), restaurantsAdded.get(IDRestaurant)[0]);
                                   response = connection.updateAddressRestaurant(newAddressRestaurantET.getText().toString(), restaurantsAdded.get(IDRestaurant)[0]);
                                   response = connection.updateDescriptionRestaurant(newDescriptionRestaurantET.getText().toString(), restaurantsAdded.get(IDRestaurant)[0]);
                                   response = connection.updatePhoneRestaurant(newPhoneRestaurantET.getText().toString(), restaurantsAdded.get(IDRestaurant)[0]);
                                   Toast.makeText(ModifyRestaurantInformation.this,response,Toast.LENGTH_SHORT).show();
                               }
                           });

                           //Obtener todos los ID de horarios
                           connection.CONN();
                           String Horaries[] = connection.getHoraries();
                           //Obtener horarios del restuarante por ID
                           connection.CONN();
                           String[] HorariesByID = connection.getHorariesByID(restaurantsAdded.get(IDRestaurant)[0]);
                           if(HorariesByID == null || HorariesByID.length == 0 || HorariesByID[0] == null){
                               Toast.makeText(ModifyRestaurantInformation.this, "Este restaurante no tiene horarios registrados", Toast.LENGTH_SHORT).show();
                               btnUpdateHorariesRestaurant.setVisibility(View.INVISIBLE);

                               horariesLayoutColumns.setVisibility(View.INVISIBLE);
                               horariesLayoutMonday.setVisibility(View.INVISIBLE);
                               horariesLayoutTuesday.setVisibility(View.INVISIBLE);
                               horariesLayoutWednesday.setVisibility(View.INVISIBLE);
                               horariesLayoutThursday.setVisibility(View.INVISIBLE);
                               horariesLayoutFriday.setVisibility(View.INVISIBLE);
                               horariesLayoutSaturday.setVisibility(View.INVISIBLE);
                               horariesLayoutSunday.setVisibility(View.INVISIBLE);
                           }else{
                               btnUpdateHorariesRestaurant.setVisibility(View.VISIBLE);

                               horariesLayoutColumns.setVisibility(View.VISIBLE);
                               horariesLayoutMonday.setVisibility(View.VISIBLE);
                               horariesLayoutTuesday.setVisibility(View.VISIBLE);
                               horariesLayoutWednesday.setVisibility(View.VISIBLE);
                               horariesLayoutThursday.setVisibility(View.VISIBLE);
                               horariesLayoutFriday.setVisibility(View.VISIBLE);
                               horariesLayoutSaturday.setVisibility(View.VISIBLE);
                               horariesLayoutSunday.setVisibility(View.VISIBLE);

                               if(HorariesByID[0].equals("8")){
                                   newOpeningHoraryRestaurantMondayET.setText("Cerrado");
                                   newClosingHoraryRestaurantMondayET.setText("Cerrado");
                               }else{
                                   newOpeningHoraryRestaurantMondayET.setText(Horaries[Integer.parseInt(HorariesByID[1])-1]);
                                   newClosingHoraryRestaurantMondayET.setText(Horaries[Integer.parseInt(HorariesByID[2])-1]);
                               }
                               if(HorariesByID[3].equals("8")){
                                   newOpeningHoraryRestaurantTuesdayET.setText("Cerrado");
                                   newClosingHoraryRestaurantTuesdayET.setText("Cerrado");
                               }else{
                                   newOpeningHoraryRestaurantTuesdayET.setText(Horaries[Integer.parseInt(HorariesByID[4])-1]);
                                   newClosingHoraryRestaurantTuesdayET.setText(Horaries[Integer.parseInt(HorariesByID[5])-1]);
                               }
                               if(HorariesByID[6].equals("8")){
                                   newOpeningHoraryRestaurantWednesdayET.setText("Cerrado");
                                   newClosingHoraryRestaurantWednesdayET.setText("Cerrado");
                               }else{
                                   newOpeningHoraryRestaurantWednesdayET.setText(Horaries[Integer.parseInt(HorariesByID[7])-1]);
                                   newClosingHoraryRestaurantWednesdayET.setText(Horaries[Integer.parseInt(HorariesByID[8])-1]);
                               }
                               if(HorariesByID[9].equals("8")){
                                   newOpeningHoraryRestaurantThursdayET.setText("Cerrado");
                                   newClosingHoraryRestaurantThursdayET.setText("Cerrado");
                               }else{
                                   newOpeningHoraryRestaurantThursdayET.setText(Horaries[Integer.parseInt(HorariesByID[10])-1]);
                                   newClosingHoraryRestaurantThursdayET.setText(Horaries[Integer.parseInt(HorariesByID[11])-1]);
                               }
                               if(HorariesByID[12].equals("8")){
                                   newOpeningHoraryRestaurantFridayET.setText("Cerrado");
                                   newClosingHoraryRestaurantFridayET.setText("Cerrado");
                               }else{
                                   newOpeningHoraryRestaurantFridayET.setText(Horaries[Integer.parseInt(HorariesByID[13])-1]);
                                   newClosingHoraryRestaurantFridayET.setText(Horaries[Integer.parseInt(HorariesByID[14])-1]);
                               }
                               if(HorariesByID[15].equals("8")){
                                   newOpeningHoraryRestaurantSaturdayET.setText("Cerrado");
                                   newClosingHoraryRestaurantSaturdayET.setText("Cerrado");
                               }else{
                                   newOpeningHoraryRestaurantSaturdayET.setText(Horaries[Integer.parseInt(HorariesByID[16])-1]);
                                   newClosingHoraryRestaurantSaturdayET.setText(Horaries[Integer.parseInt(HorariesByID[17])-1]);
                               }
                               if(HorariesByID[18].equals("8")){
                                   newOpeningHoraryRestaurantSundayET.setText("Cerrado");
                                   newClosingHoraryRestaurantSundayET.setText("Cerrado");
                               }else{
                                   newOpeningHoraryRestaurantSundayET.setText(Horaries[Integer.parseInt(HorariesByID[19])-1]);
                                   newClosingHoraryRestaurantSundayET.setText(Horaries[Integer.parseInt(HorariesByID[20])-1]);
                               }
                               btnUpdateHorariesRestaurant.setOnClickListener(view -> {

                                   //Obtener todos los horarios
                                   String OpeningHoraryRestaurantMonday = newOpeningHoraryRestaurantMondayET.getText().toString();
                                   String ClosingHoraryRestaurantMonday = newClosingHoraryRestaurantMondayET.getText().toString();

                                   String OpeningHoraryRestaurantTuesday = newOpeningHoraryRestaurantTuesdayET.getText().toString();
                                   String ClosingHoraryRestaurantTuesday = newClosingHoraryRestaurantTuesdayET.getText().toString();

                                   String OpeningHoraryRestaurantWednesday = newOpeningHoraryRestaurantWednesdayET.getText().toString();
                                   String ClosingHoraryRestaurantMondayWednesday = newClosingHoraryRestaurantWednesdayET.getText().toString();

                                   String OpeningHoraryRestaurantThursday = newOpeningHoraryRestaurantThursdayET.getText().toString();
                                   String ClosingHoraryRestaurantThursday = newClosingHoraryRestaurantThursdayET.getText().toString();

                                   String OpeningHoraryRestaurantFriday = newOpeningHoraryRestaurantFridayET.getText().toString();
                                   String ClosingHoraryRestaurantFriday = newClosingHoraryRestaurantFridayET.getText().toString();

                                   String OpeningHoraryRestaurantSaturday = newOpeningHoraryRestaurantSaturdayET.getText().toString();
                                   String ClosingHoraryRestaurantSaturday = newClosingHoraryRestaurantSaturdayET.getText().toString();

                                   String OpeningHoraryRestaurantSunday = newOpeningHoraryRestaurantSundayET.getText().toString();
                                   String ClosingHoraryRestaurantSunday = newClosingHoraryRestaurantSundayET.getText().toString();

                                   if(OpeningHoraryRestaurantMonday.equals("")||ClosingHoraryRestaurantMonday.equals("")||
                                           OpeningHoraryRestaurantTuesday.equals("")||ClosingHoraryRestaurantTuesday.equals("")||
                                           OpeningHoraryRestaurantWednesday.equals("")||ClosingHoraryRestaurantMondayWednesday.equals("")||
                                           OpeningHoraryRestaurantThursday.equals("")||ClosingHoraryRestaurantThursday.equals("")||
                                           OpeningHoraryRestaurantFriday.equals("")||ClosingHoraryRestaurantFriday.equals("")||
                                           OpeningHoraryRestaurantSaturday.equals("")||ClosingHoraryRestaurantSaturday.equals("")||
                                           OpeningHoraryRestaurantSunday.equals("")||ClosingHoraryRestaurantSunday.equals("")){
                                       Toast.makeText(ModifyRestaurantInformation.this, "Ningun horario no debe quedar vacios", Toast.LENGTH_LONG).show();
                                   }else{
                                       //Obtener el ID de la hora a actualizar
                                       int IDHoraryOpeningMonday = getIDHorary(Horaries, OpeningHoraryRestaurantMonday);
                                       int IDHoraryClosingMonday = getIDHorary(Horaries, ClosingHoraryRestaurantMonday);
                                       int IDHoraryOpeningTuesday = getIDHorary(Horaries, OpeningHoraryRestaurantTuesday);
                                       int IDHoraryClosingTuesday = getIDHorary(Horaries, ClosingHoraryRestaurantTuesday);
                                       int IDHoraryOpeningWednesday = getIDHorary(Horaries, OpeningHoraryRestaurantWednesday);
                                       int IDHoraryClosingWednesday = getIDHorary(Horaries, ClosingHoraryRestaurantMondayWednesday);
                                       int IDHoraryOpeningThursday = getIDHorary(Horaries, OpeningHoraryRestaurantThursday);
                                       int IDHoraryClosingThursday = getIDHorary(Horaries, ClosingHoraryRestaurantThursday);
                                       int IDHoraryOpeningFriday = getIDHorary(Horaries, OpeningHoraryRestaurantFriday);
                                       int IDHoraryClosingFriday = getIDHorary(Horaries, ClosingHoraryRestaurantFriday);
                                       int IDHoraryOpeningSaturday = getIDHorary(Horaries, OpeningHoraryRestaurantSaturday);
                                       int IDHoraryClosingSaturday = getIDHorary(Horaries, ClosingHoraryRestaurantSaturday);
                                       int IDHoraryOpeningSunday = getIDHorary(Horaries, OpeningHoraryRestaurantSunday);
                                       int IDHoraryClosingSunday = getIDHorary(Horaries, ClosingHoraryRestaurantSunday);

                                       //Verificar que los IDs sean diferentes de -1
                                       if(IDHoraryOpeningMonday != -1 && IDHoraryClosingMonday != -1 && IDHoraryOpeningTuesday != -1 &&
                                               IDHoraryClosingTuesday != -1 && IDHoraryOpeningWednesday != -1 && IDHoraryClosingWednesday != -1 &&
                                               IDHoraryOpeningThursday != -1 && IDHoraryClosingThursday != -1 && IDHoraryOpeningFriday != -1 &&
                                               IDHoraryClosingFriday != -1 && IDHoraryOpeningSaturday != -1 && IDHoraryClosingSaturday != -1 &&
                                               IDHoraryOpeningSunday != -1 && IDHoraryClosingSunday != -1){
                                                String response = "";
                                                response = connection.updateHoraryRestaurantByID("IDHorario1Dia1",String.valueOf(IDHoraryOpeningMonday),restaurantsAdded.get(IDRestaurant)[0]);
                                                response = connection.updateHoraryRestaurantByID("IDHorario2Dia1",String.valueOf(IDHoraryOpeningTuesday),restaurantsAdded.get(IDRestaurant)[0]);
                                                   response = connection.updateHoraryRestaurantByID("IDHorario1Dia2",String.valueOf(IDHoraryOpeningMonday),restaurantsAdded.get(IDRestaurant)[0]);
                                                   response = connection.updateHoraryRestaurantByID("IDHorario2Dia2",String.valueOf(IDHoraryClosingTuesday),restaurantsAdded.get(IDRestaurant)[0]);
                                                   response = connection.updateHoraryRestaurantByID("IDHorario1Dia3",String.valueOf(IDHoraryOpeningWednesday),restaurantsAdded.get(IDRestaurant)[0]);
                                                   response = connection.updateHoraryRestaurantByID("IDHorario2Dia3",String.valueOf(IDHoraryClosingWednesday),restaurantsAdded.get(IDRestaurant)[0]);
                                                   response = connection.updateHoraryRestaurantByID("IDHorario1Dia4",String.valueOf(IDHoraryOpeningThursday),restaurantsAdded.get(IDRestaurant)[0]);
                                                   response = connection.updateHoraryRestaurantByID("IDHorario2Dia4",String.valueOf(IDHoraryClosingThursday),restaurantsAdded.get(IDRestaurant)[0]);
                                                   response = connection.updateHoraryRestaurantByID("IDHorario1Dia5",String.valueOf(IDHoraryOpeningFriday),restaurantsAdded.get(IDRestaurant)[0]);
                                                   response = connection.updateHoraryRestaurantByID("IDHorario2Dia5",String.valueOf(IDHoraryClosingFriday),restaurantsAdded.get(IDRestaurant)[0]);
                                                   response = connection.updateHoraryRestaurantByID("IDHorario1Dia6",String.valueOf(IDHoraryOpeningSaturday),restaurantsAdded.get(IDRestaurant)[0]);
                                                   response = connection.updateHoraryRestaurantByID("IDHorario2Dia6",String.valueOf(IDHoraryClosingSaturday),restaurantsAdded.get(IDRestaurant)[0]);
                                                   response = connection.updateHoraryRestaurantByID("IDHorario1Dia7",String.valueOf(IDHoraryOpeningSunday),restaurantsAdded.get(IDRestaurant)[0]);
                                                   response = connection.updateHoraryRestaurantByID("IDHorario2Dia7",String.valueOf(IDHoraryClosingSunday),restaurantsAdded.get(IDRestaurant)[0]);
                                           Toast.makeText(ModifyRestaurantInformation.this, response, Toast.LENGTH_LONG).show();
                                       }else{
                                           Toast.makeText(ModifyRestaurantInformation.this, "Alguna de las fechas no tienen el formato adecuado", Toast.LENGTH_LONG).show();
                                       }
                                   }
                               });

                           }
                       }
                       public void onNothingSelected(AdapterView<?> spn) {
                           Toast.makeText(spn.getContext(), "No has seleccionado ningun restaurante", Toast.LENGTH_LONG).show();
                       }
                   });
       }
    }
    public int getIDHorary(String[] Horaries, String Hour){
        for(int columna = 0; columna < Horaries.length; columna++){
            if(Horaries[columna].equals(Hour)){
                int idValor = columna + 1;
                //Toast.makeText(ModifyRestaurantInformation.this, "El ID es" + idValor, Toast.LENGTH_LONG).show();
                return idValor;
            }
        }
        return -1;
    }
}