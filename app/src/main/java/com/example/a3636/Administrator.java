package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
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

        btnRegisterRestaurant.setBackgroundColor(Color.rgb(255, 128, 0));

        IrapuatoCB.setChecked(true);
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

        try{
            connection.CONN();
            String[] usersBusiness = connection.getUserBusiness();
            if(usersBusiness == null || usersBusiness.length == 0 || usersBusiness[0] == null){
                Toast.makeText(this,"Error, no hay usuario tipo empresa registrados", Toast.LENGTH_SHORT).show();
            }else{
                int count = 0;
                for(int i = 0; i < usersBusiness.length; i++){
                    if(!usersBusiness[i].equals("")){
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

                            String userAdministratorRestaurant = spinnerUserAdminRestaurantBusiness.getSelectedItem().toString();
                            connection.CONN();
                            String IDUser = connection.getIDUser(userAdministratorRestaurant);

                            connection.CONN();
                            String response = connection.registerRestaurant(name, address, description, phone, ciudad, IDUser);
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