package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3636.database.DatabaseConnection;

public class Administrator extends AppCompatActivity {
    DatabaseConnection connection = new DatabaseConnection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        String location = ((MyLocation)getApplication()).getLocation();

        ImageView imageAdminSession = findViewById(R.id.imageAdminSession);
        ImageView settingsIMAdmin = findViewById(R.id.settingsIMAdmin);
        TextView nameAdminSession = findViewById(R.id.nameAdminSession);
        TextView logInAdmin = findViewById(R.id.logInAdmin);

        String userName = getIntent().getStringExtra("userName");

        nameAdminSession.setText(userName);

        try{
            //Obtener ID del Usuario
            connection.CONN();
            String ID = connection.getIDUser(userName);
            //Obtener imagen de usuario
            byte[] imageReceived = connection.getImage(ID);
            if(imageReceived != null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageReceived, 0, imageReceived.length);
                imageAdminSession.setImageBitmap(bitmap);
            }
        }catch(Exception e){
            Toast.makeText(this,"Ha ocurrido un error, intentelo más tarde", Toast.LENGTH_LONG).show();
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        }
            logInAdmin.setText("Cambiar de usuario");
            logInAdmin.setOnClickListener(view -> {
                Intent login = new Intent(this, Login.class);
                startActivity(login);
            });

        settingsIMAdmin.setOnClickListener(view -> {
            String userConnected = nameAdminSession.getText().toString();
            Intent settings = new Intent(this, UserSettings.class);
            settings.putExtra("userName",userConnected);
            startActivity(settings);
        });

        EditText newNameRestaurantAdminET = findViewById(R.id.newNameRestaurantAdminET);
        EditText newAddressRestaurantAdminET = findViewById(R.id.newAddressRestaurantAdminET);
        EditText newDescrptionRestaurantAdminET = findViewById(R.id.newDescrptionRestaurantAdminET);
        EditText newPhoneRestaurantAdminET = findViewById(R.id.newPhoneRestaurantAdminET);
        Spinner spinnerUserAdminRestaurantBusiness = findViewById(R.id.spinnerUserAdminRestaurantBusiness);
        Button btnRegisterRestaurant = findViewById(R.id.btnRegisterRestaurant);
        CheckBox IrapuatoCB = findViewById(R.id.IrapuatoCB);
        CheckBox GuanajuatoCB = findViewById(R.id.GuanajuatoCB);
        CheckBox LeonCB = findViewById(R.id.LeonCB);
        CheckBox CelayaCB = findViewById(R.id.CelayaCB);
        CheckBox ChihuahuaCB = findViewById(R.id.ChihuahuaCB);
        CheckBox JuarezCB = findViewById(R.id.JuarezCB);
        CheckBox homeServiceCB = findViewById(R.id.homeServiceCB);
        CheckBox bookingCB = findViewById(R.id.bookingCB);
        CheckBox stateChihuahuaCB = findViewById(R.id.stateChihuahuaCB);
        CheckBox stateGtoCB = findViewById(R.id.stateGtoCB);

        TextView newLinkHomeServiceAdminTV = findViewById(R.id.newLinkHomeServiceAdminTV);
        EditText newLinkHomeOfficeAdminET = findViewById(R.id.newLinkHomeOfficeAdminET);
        TextView newLinkBookingAdminTV = findViewById(R.id.newLinkBookingAdminTV);
        EditText newLinkBookingAdminET = findViewById(R.id.newLinkBookingAdminET);

        btnRegisterRestaurant.setBackgroundColor(Color.rgb(255, 128, 0));

        stateGtoCB.setChecked(true);
        stateGtoCB.setOnClickListener(view -> {
            stateGtoCB.setVisibility(View.GONE);
            stateChihuahuaCB.setVisibility(View.VISIBLE);
            IrapuatoCB.setChecked(true);
            GuanajuatoCB.setVisibility(View.VISIBLE);
            LeonCB.setVisibility(View.VISIBLE);
            CelayaCB.setVisibility(View.VISIBLE);
            IrapuatoCB.setVisibility(View.VISIBLE);
            ChihuahuaCB.setVisibility(View.GONE);
            JuarezCB.setVisibility(View.GONE);
            stateChihuahuaCB.setChecked(false);
        });

        stateChihuahuaCB.setOnClickListener(view -> {
            stateChihuahuaCB.setVisibility(View.GONE);
            stateGtoCB.setVisibility(View.VISIBLE);
            ChihuahuaCB.setChecked(true);
            GuanajuatoCB.setVisibility(View.GONE);
            LeonCB.setVisibility(View.GONE);
            CelayaCB.setVisibility(View.GONE);
            IrapuatoCB.setVisibility(View.GONE);
            ChihuahuaCB.setVisibility(View.VISIBLE);
            JuarezCB.setVisibility(View.VISIBLE);
            stateGtoCB.setChecked(false);
        });

        IrapuatoCB.setOnClickListener(view -> {
            GuanajuatoCB.setChecked(false);
            LeonCB.setChecked(false);
            CelayaCB.setChecked(false);
            ChihuahuaCB.setChecked(false);
            JuarezCB.setChecked(false);
        });

        GuanajuatoCB.setOnClickListener(view -> {
            IrapuatoCB.setChecked(false);
            LeonCB.setChecked(false);
            CelayaCB.setChecked(false);
            ChihuahuaCB.setChecked(false);
            JuarezCB.setChecked(false);
        });

        LeonCB.setOnClickListener(view -> {
            IrapuatoCB.setChecked(false);
            GuanajuatoCB.setChecked(false);
            CelayaCB.setChecked(false);
            ChihuahuaCB.setChecked(false);
            JuarezCB.setChecked(false);
        });

        CelayaCB.setOnClickListener(view -> {
            IrapuatoCB.setChecked(false);
            GuanajuatoCB.setChecked(false);
            LeonCB.setChecked(false);
            ChihuahuaCB.setChecked(false);
            JuarezCB.setChecked(false);
        });

        ChihuahuaCB.setOnClickListener(view -> {
            IrapuatoCB.setChecked(false);
            GuanajuatoCB.setChecked(false);
            LeonCB.setChecked(false);
            CelayaCB.setChecked(false);
            JuarezCB.setChecked(false);
        });

        JuarezCB.setOnClickListener(view -> {
            IrapuatoCB.setChecked(false);
            GuanajuatoCB.setChecked(false);
            LeonCB.setChecked(false);
            CelayaCB.setChecked(false);
            ChihuahuaCB.setChecked(false);
        });
        final int[] cont = {0};
        homeServiceCB.setOnClickListener(view -> {
            if(cont[0] == 0){
                newLinkHomeServiceAdminTV.setVisibility(View.VISIBLE);
                newLinkHomeOfficeAdminET.setVisibility(View.VISIBLE);
                cont[0] = 1;
            }else{
                newLinkHomeServiceAdminTV.setVisibility(View.GONE);
                newLinkHomeOfficeAdminET.setVisibility(View.GONE);
                cont[0] = 0;
            }
        });

        final int[] contBooking = {0};
        bookingCB.setOnClickListener(view -> {
            if(contBooking[0] == 0){
                newLinkBookingAdminTV.setVisibility(View.VISIBLE);
                newLinkBookingAdminET.setVisibility(View.VISIBLE);
                contBooking[0] = 1;
            }else{
                newLinkBookingAdminTV.setVisibility(View.GONE);
                newLinkBookingAdminET.setVisibility(View.GONE);
                contBooking[0] = 0;
            }
        });

        try{
            connection.CONN();
            String[] usersBusiness = connection.getUserBusiness();
            if(usersBusiness == null || usersBusiness.length == 0 || usersBusiness[0] == null){
                Toast.makeText(this,"Error, no hay usuario tipo empresa registrados", Toast.LENGTH_SHORT).show();
            }else{
                int count = 0;
                for (String business : usersBusiness) {
                    if (business != null) {
                        count++;
                    }
                }
                String[] users = new String[count];
                for(int j = 0; j < count; j++){
                    users[j] = usersBusiness[j];
                }
                ArrayAdapter<CharSequence> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinnerUserAdminRestaurantBusiness.setAdapter(adapter);

                btnRegisterRestaurant.setOnClickListener(view -> {
                    String name = newNameRestaurantAdminET.getText().toString();
                    String address = newAddressRestaurantAdminET.getText().toString();
                    String description = newDescrptionRestaurantAdminET.getText().toString();
                    String phone = newPhoneRestaurantAdminET.getText().toString();
                    String ciudad = "";
                    String homeService = "NO";
                    String booking = "NO";

                    if(!name.equals("") && !address.equals("") && !description.equals("") && !phone.equals("")){
                        if(!IrapuatoCB.isChecked() && !GuanajuatoCB.isChecked() && !LeonCB.isChecked() && !CelayaCB.isChecked() && !ChihuahuaCB.isChecked() && !JuarezCB.isChecked()){
                            Toast.makeText(this,"Error, debe seleccionar una ciudad", Toast.LENGTH_SHORT).show();
                        }else{
                            if(GuanajuatoCB.isChecked()){
                                ciudad = "1";
                            }else
                            if(LeonCB.isChecked()){
                                ciudad = "2";
                            }else
                            if(IrapuatoCB.isChecked()){
                                ciudad = "3";
                            }else
                            if(CelayaCB.isChecked()){
                                ciudad = "4";
                            }else
                            if(ChihuahuaCB.isChecked()){
                                ciudad = "5";
                            }else
                            if(JuarezCB.isChecked()){
                                ciudad = "6";
                            }else
                                ciudad = "0";
                            //String linkHomeOffice = ".";
                            //String linkBooking = ".";
                            if(homeServiceCB.isChecked()){
                                //homeService = "SI";
                                homeService = newLinkHomeOfficeAdminET.getText().toString();
                            }else
                                homeService = "NO";
                            if(bookingCB.isChecked()){
                                //booking = "SI";
                                booking = newLinkBookingAdminET.getText().toString();
                            }else
                                booking = "NO";
                            String userAdministratorRestaurant = spinnerUserAdminRestaurantBusiness.getSelectedItem().toString();
                            connection.CONN();
                            String IDUser = connection.getIDUser(userAdministratorRestaurant);

                            connection.CONN();
                            String response = connection.registerRestaurant(name, address, description, phone, ciudad, IDUser, homeService, booking);
                            Toast.makeText(this,response, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this,"Error, todos los datos deben ser llenados", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }catch (Exception e){
            Toast.makeText(this,"Ha ocurrido un error, intentelo más tarde", Toast.LENGTH_LONG).show();
        }
    }
}