package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3636.database.DatabaseConnection;
import com.example.a3636.encryption.BCrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Login extends AppCompatActivity {
    String user = "";
    String password = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DatabaseConnection dbConnect = new DatabaseConnection();
        Button BtnLogin = findViewById(R.id.BtnLogin);
        Button BtnRegister = findViewById(R.id.BtnRegister);
        BtnLogin.setBackgroundColor(Color.rgb(255, 128, 0));
        BtnRegister.setBackgroundColor(Color.rgb(255, 128, 0));
        EditText userEditText = findViewById(R.id.userEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        LinearLayout layoutErrorLogin = findViewById(R.id.layoutErrorLogin);
        Spinner spinnerCityLogin = findViewById(R.id.spinnerCityLogin);
        Button buttonSearchByCityLogin = findViewById(R.id.buttonSearchByCityLogin);

        String location = ((MyLocation)getApplication()).getLocation();

        BtnLogin.setOnClickListener(view -> {
            user = userEditText.getText().toString();
            password = passwordEditText.getText().toString();

            String[] userData = new String[2];
            boolean passwordOK = false;
            String hash = "";
            String IDType = "";

            try {
                dbConnect.CONN();
                if(!user.equals("") || !password.equals("")) {
                    userData = dbConnect.loginCheck(user);
                    hash = userData[0];
                    IDType = userData[1];
                    passwordOK = BCrypt.checkpw(password, hash);
                }
            }catch (Exception e){
                Toast.makeText(this,"Error", Toast.LENGTH_LONG).show();
            }

            if(passwordOK){
                int IDTypeUser = Integer.valueOf(IDType);
                switch (IDTypeUser){
                    case 1:

                        if(location.equals("Irapuato")){
                            dbConnect.CONN();
                            String numberRestaurants = dbConnect.getRestaurantNumberByCity("3");
                            if(!numberRestaurants.equals("0")) {
                                ((MyLocation) getApplication()).setLocation("Irapuato");
                                Intent showCityRestaurants = new Intent(this, Interface3636.class);
                                showCityRestaurants.putExtra("userName", user);
                                startActivity(showCityRestaurants);
                            }else{
                                messageError();
                            }
                        }else
                        if(location.equals("Guanajuato")){
                            dbConnect.CONN();
                            String numberRestaurants = dbConnect.getRestaurantNumberByCity("1");
                            if(!numberRestaurants.equals("0")) {
                                ((MyLocation) getApplication()).setLocation("Guanajuato");
                                Intent showCityRestaurants = new Intent(this, Interface3636.class);
                                showCityRestaurants.putExtra("userName", user);
                                startActivity(showCityRestaurants);
                            }else{
                                messageError();
                            }
                        }else
                        if(location.equals("León")){
                            dbConnect.CONN();
                            String numberRestaurants = dbConnect.getRestaurantNumberByCity("2");
                            if(!numberRestaurants.equals("0")) {
                                ((MyLocation) getApplication()).setLocation("León");
                                Intent showCityRestaurants = new Intent(this, Interface3636.class);
                                showCityRestaurants.putExtra("userName", user);
                                startActivity(showCityRestaurants);
                            }else{
                                messageError();
                            }
                        }else
                        if(location.equals("Celaya")){
                            dbConnect.CONN();
                            String numberRestaurants = dbConnect.getRestaurantNumberByCity("4");
                            if(!numberRestaurants.equals("0")) {
                                ((MyLocation) getApplication()).setLocation("Celaya");
                                Intent showCityRestaurants = new Intent(this, Interface3636.class);
                                showCityRestaurants.putExtra("userName", user);
                                startActivity(showCityRestaurants);
                            }else{
                                messageError();
                            }
                        }else
                        if(location.equals("Chihuahua")){
                            dbConnect.CONN();
                            String numberRestaurants = dbConnect.getRestaurantNumberByCity("5");
                            if(!numberRestaurants.equals("0")) {
                                ((MyLocation) getApplication()).setLocation("Chihuahua");
                                Intent showCityRestaurants = new Intent(this, Interface3636.class);
                                showCityRestaurants.putExtra("userName", user);
                                startActivity(showCityRestaurants);
                            }else{
                                messageError();
                            }
                        }else
                        if(location.equals("Juárez")) {
                            dbConnect.CONN();
                            String numberRestaurants = dbConnect.getRestaurantNumberByCity("5");
                            if(!numberRestaurants.equals("0")) {
                                ((MyLocation) getApplication()).setLocation("Juárez");
                                Intent showCityRestaurants = new Intent(this, Interface3636.class);
                                showCityRestaurants.putExtra("userName", user);
                                startActivity(showCityRestaurants);
                            }else{
                                messageError();
                            }
                        }else {
                            LinearLayout layoutLogin = findViewById(R.id.layoutLoginComplete);
                            layoutLogin.setVisibility(View.GONE);
                            layoutErrorLogin.setVisibility(View.VISIBLE);
                            String cities[] = {"Seleccione la ciudad", "Irapuato", "Guanajuato", "León", "Celaya", "Chihuahua", "Juárez"};
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_style,cities );
                            spinnerCityLogin.setAdapter(adapter);
                            buttonSearchByCityLogin.setOnClickListener(view1 -> {
                                String city = spinnerCityLogin.getSelectedItem().toString();
                                if(city.equals("Seleccione la ciudad")){
                                    Toast.makeText(this,"Debe seleccionar una ciudad antes de continuar", Toast.LENGTH_LONG).show();
                                }else{
                                    if(city.equals("Irapuato")){
                                        dbConnect.CONN();
                                        String numberRestaurants = dbConnect.getRestaurantNumberByCity("3");
                                        if(!numberRestaurants.equals("0")) {
                                            ((MyLocation) getApplication()).setLocation("Irapuato");
                                            Intent showCityRestaurants = new Intent(this, Interface3636.class);
                                            showCityRestaurants.putExtra("userName", user);
                                            startActivity(showCityRestaurants);
                                        }else{
                                            messageError();
                                        }
                                    }else
                                        if(city.equals("León")){
                                            dbConnect.CONN();
                                            String numberRestaurants = dbConnect.getRestaurantNumberByCity("2");
                                            if(!numberRestaurants.equals("0")) {
                                                ((MyLocation) getApplication()).setLocation("León");
                                                Intent showCityRestaurants = new Intent(this, Interface3636.class);
                                                showCityRestaurants.putExtra("userName", user);
                                                startActivity(showCityRestaurants);
                                            }else{
                                                messageError();
                                            }
                                        }else
                                        if(city.equals("Guanajuato")){
                                            dbConnect.CONN();
                                            String numberRestaurants = dbConnect.getRestaurantNumberByCity("1");
                                            if(!numberRestaurants.equals("0")) {
                                                ((MyLocation) getApplication()).setLocation("Guanajuato");
                                                Intent showCityRestaurants = new Intent(this, Interface3636.class);
                                                showCityRestaurants.putExtra("userName", user);
                                                startActivity(showCityRestaurants);
                                            }else{
                                                messageError();
                                            }
                                        }else
                                        if(city.equals("Chihuahua")){
                                            dbConnect.CONN();
                                            String numberRestaurants = dbConnect.getRestaurantNumberByCity("5");
                                            if(!numberRestaurants.equals("0")) {
                                                ((MyLocation) getApplication()).setLocation("Chihuahua");
                                                Intent showCityRestaurants = new Intent(this, Interface3636.class);
                                                showCityRestaurants.putExtra("userName", user);
                                                startActivity(showCityRestaurants);
                                            }else{
                                                messageError();
                                            }
                                        }else
                                        if(city.equals("Juárez")){
                                            dbConnect.CONN();
                                            String numberRestaurants = dbConnect.getRestaurantNumberByCity("5");
                                            if(!numberRestaurants.equals("0")) {
                                                ((MyLocation) getApplication()).setLocation("Juárez");
                                                Intent showCityRestaurants = new Intent(this, Interface3636.class);
                                                showCityRestaurants.putExtra("userName", user);
                                                startActivity(showCityRestaurants);
                                            }else{
                                                messageError();
                                            }
                                        }else
                                        if(city.equals("Celaya")){
                                            dbConnect.CONN();
                                            String numberRestaurants = dbConnect.getRestaurantNumberByCity("4");
                                            if(!numberRestaurants.equals("0")) {
                                                ((MyLocation) getApplication()).setLocation("Celaya");
                                                Intent showCityRestaurants = new Intent(this, Interface3636.class);
                                                showCityRestaurants.putExtra("userName", user);
                                                startActivity(showCityRestaurants);
                                            }else{
                                                messageError();
                                            }
                                        }
                                }
                            });
                            /*LinearLayout layoutError = new LinearLayout(this);
                            layoutError.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            layoutError.setOrientation(LinearLayout.VERTICAL);
                            TextView tvError = new TextView(this);
                            TextView tvSelectSpinner = new TextView(this);
                            Button btnCitySelected = new Button(this);
                            tvError.setText("Error, tu ciudad no cuenta con restaurantes registrados en estos momentos.");
                            tvError.setTextColor(Color.RED);
                            tvSelectSpinner.setTextColor(Color.RED);

                            tvError.setGravity(Gravity.CENTER);
                            tvSelectSpinner.setGravity(Gravity.CENTER);

                            btnCitySelected.setText("Buscar por ciudad");
                            btnCitySelected.setBackgroundColor(Color.rgb(255, 128, 0));
                            tvSelectSpinner.setText("Seleccione la ciudad en la que desea realizar su búsqueda");
                            Spinner spinnerCiudad = new Spinner(this);
                            String cities[] = {"Seleccione la ciudad", "Irapuato", "Guanajuato", "León", "Celaya", "Chihuahua", "Juarez"};
                            ArrayAdapter<CharSequence> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            spinnerCiudad.setAdapter(adapter);
                            //spinnerCiudad.setBackgroundColor(Color.rgb(249,240,173));
                            spinnerCiudad.setBackgroundColor(Color.BLACK);
                            LinearLayout layoutLoginComplete = findViewById(R.id.layoutLogin);
                            layoutError.addView(tvError);
                            layoutError.addView(tvSelectSpinner);
                            layoutError.addView(spinnerCiudad);
                            layoutError.addView(btnCitySelected);
                            layoutLoginComplete.addView(layoutError);
                            btnCitySelected.setOnClickListener(view2 -> {
                                String city = spinnerCiudad.getSelectedItem().toString();
                                if(city.equals("Seleccione la ciudad")){
                                    Toast.makeText(this,"Debe seleccionar una ciudad antes de continuar", Toast.LENGTH_LONG).show();
                                }else{
                                    if(city.equals("Irapuato")){
                                        ((MyLocation)getApplication()).setLocation("Irapuato");
                                            Intent Interfaz_3636 = new Intent(this, Interface3636.class);
                                            Interfaz_3636.putExtra("userName",user);
                                            startActivity(Interfaz_3636);
                                    }
                                }
                            });*/
                        }


                        break;
                    case 2:
                        Intent admin = new Intent(this, Administrator.class);
                        admin.putExtra("userName",user);
                        startActivity(admin);
                        break;
                    case 3:
                        Intent business = new Intent(this, ModifyRestaurantInformation.class);
                        business.putExtra("userName",user);
                        startActivity(business);
                        break;
                }
            }else{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        BtnRegister.setOnClickListener(view -> {
            Intent registerUser = new Intent(this, RegisterUser.class);
            startActivity(registerUser);
        });
    }
    public void messageError(){
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("No hay restaurantes registrados en  esta ciudad. \nSeleccione una ciuda diferente.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("alertDialog","OK");
                    }
                })
                .show();
    }
}