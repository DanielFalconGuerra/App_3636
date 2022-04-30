package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3636.database.DatabaseConnection;
import com.example.a3636.restaurantdata.RestaurantInformation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Interface3636 extends AppCompatActivity {
    DatabaseConnection connection = new DatabaseConnection();
    String numberNotificationReceived = "";
    String date = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface3636);

        TextView nameUserSession = findViewById(R.id.nameUserSession);
        ImageView imageUser = (ImageView) findViewById(R.id.imageUserSession);
        TextView logIn = findViewById(R.id.logIn);

        ImageView settingsIM = findViewById(R.id.settingsIM);
        ImageView notificationsIM = findViewById(R.id.notificationsIM);

        //Recuper ubicacion
        String location = ((MyLocation)getApplication()).getLocation();
        Toast.makeText(this,"Ubicacion: " + location,Toast.LENGTH_SHORT).show();

        String userName = getIntent().getStringExtra("userName");

        nameUserSession.setText(userName);
        //Obtener ID del Usuario
        connection.CONN();
        String ID = connection.getIDUser(userName);
        Toast.makeText(this,ID,Toast.LENGTH_SHORT).show();
        //Obtener imagen de usuario
        byte[] imageReceived = connection.getImage(ID);
        if(imageReceived != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageReceived, 0, imageReceived.length);
            imageUser.setImageBitmap(bitmap);
        }
        logIn.setText("Cambiar de usuario");
        logIn.setOnClickListener(view -> {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        });

        settingsIM.setOnClickListener(view -> {
            String userConnected = nameUserSession.getText().toString();
            Toast.makeText(this,userConnected, Toast.LENGTH_LONG).show();
            if(userConnected.equals("")){
                Toast.makeText(this,"Debes iniciar sesión para usar esta opción", Toast.LENGTH_LONG).show();
            }else{
                Intent settings = new Intent(this, UserSettings.class);
                settings.putExtra("userName",userConnected);
                startActivity(settings);
            }
        });

        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        connection.CONN();
        numberNotificationReceived = connection.getNumberNotifications(date);
        Toast.makeText(this,numberNotificationReceived, Toast.LENGTH_LONG).show();
        if(!numberNotificationReceived.equals("")){
            notificationsIM.setImageResource(R.drawable.iconsnotification);
        }

        notificationsIM.setOnClickListener(view -> {
            String userConnected = nameUserSession.getText().toString();
            if(userConnected.equals("")){
                Toast.makeText(this,"Debes iniciar sesión para usar esta opción", Toast.LENGTH_LONG).show();
            }else{
                notificationsIM.setImageResource(R.drawable.ic_baseline_notifications_24);
                Intent showNotifications = new Intent(this, NotificationsReceived.class);
                showNotifications.putExtra("numberNotifications",numberNotificationReceived);
                showNotifications.putExtra("date",date);
                startActivity(showNotifications);
            }
        });

        TypesOfFoodAndSchedules typesOfFoodAndSchedules = new TypesOfFoodAndSchedules();
        AtomicInteger identificadorDeRestaurante = new AtomicInteger();

        //Spinner para Tipo de Comida
        Spinner spinnerTipoComida = findViewById(R.id.menu_tipoComida);
        String foodType[];
        //Obtener tipos de comida de base de datos
        connection.CONN();
        //foodType = connection.getTypesOfFood();
        foodType = typesOfFoodAndSchedules.getTypesOfFood();
        //ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.TipodeComida, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter= new ArrayAdapter<>(this, R.layout.spinner_text_style, foodType);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTipoComida.setAdapter(adapter);
        //Spinner para Horario
        Spinner spinnerHorario = findViewById(R.id.menu_Horario);
        //Obtener tipos de comida de base de datos
        String hours[];
        connection.CONN();
        //Obtener horarios de restaurantes
        //hours = connection.getHoraries();
        hours = typesOfFoodAndSchedules.getItemsToArraySchedules();
        //ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.TipodeComida, android.R.layout.simple_spinner_item);
        ArrayAdapter <CharSequence>adapterHour= new ArrayAdapter<>(this, R.layout.spinner_text_style, hours);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerHorario.setAdapter(adapterHour);

        Button btnSearchRestaurant = findViewById(R.id.btnSearchRestaurant);
        btnSearchRestaurant.setBackgroundColor(Color.rgb(255, 128, 0));
        btnSearchRestaurant.setOnClickListener(view -> {
            String typeFood = spinnerTipoComida.getSelectedItem().toString();
            String hour = spinnerHorario.getSelectedItem().toString();
            if(!(typeFood.equals("Seleccione el tipo de comida")||(hour.equals("Selecciona la hora")))){
                Intent showRestaurantFound = new Intent(this, ShowRestaurantsFound.class);
                showRestaurantFound.putExtra("typeFood", typeFood);
                showRestaurantFound.putExtra("hour", hour);
                startActivity(showRestaurantFound);
            }
        });

        //Codigo para Buscar Por Categoría
        Button btnRestaurant = findViewById(R.id.btnRestaurant);
        //btnRestaurant.setBackgroundColor(Color.rgb(255, 128, 0));
        Button btnCoffee = findViewById(R.id.btnCoffee);
        //btnCoffee.setBackgroundColor(Color.rgb(255, 128, 0));
        Button btnBar = findViewById(R.id.btnBar);
        //btnBar.setBackgroundColor(Color.rgb(255, 128, 0));

        //Cambiar de Restaurante
        Button previos_restaurant = findViewById(R.id.previos_restaurant);
        previos_restaurant.setBackgroundTintList(this.getResources().getColorStateList(R.color.orange));
        Button next_restaurant = findViewById(R.id.next_restaurant);
        next_restaurant.setBackgroundTintList(this.getResources().getColorStateList(R.color.orange));
        Button somethingyoumightlikebutton = findViewById(R.id.somethingyoumightlikebutton);
        somethingyoumightlikebutton.setBackgroundColor(Color.rgb(255, 128, 0));

        ImageView logoRestaurant = findViewById(R.id.logoRestaurant);
        TextView nameText = findViewById(R.id.nameText);
        TextView addressText = findViewById(R.id.addressText);
        TextView availabilityText = findViewById(R.id.availabilityText);
        TextView typesOfFoodText = findViewById(R.id.typesOfFoodText);
        TextView phoneText = findViewById(R.id.phoneText);

        RestaurantInformation Arena88 = new RestaurantInformation();
        RestaurantInformation LaGenareria = new RestaurantInformation();
        RestaurantInformation CostillaWinebarlechon = new RestaurantInformation();

        Arena88.setNameText("Arena 88");
        Arena88.setAddressText("Dirección: Irapuato, Guanajuato. Plaza 3636 Gómez Morín");
        Arena88.setTypesOfFoodText("Tipos de Comida: Bar, Restaurante - Bar, Mariscos");
        Arena88.setPhoneText("Teléfono: 462 688 3664");
        Arena88.setAvailabilityText("Disponibilidad el día de hoy: 12:00 a 23:00");
        Arena88.setLogoRestaurant("@mipmap/arena88.jpg");

        LaGenareria.setNameText("La Genareria");
        LaGenareria.setAddressText("Dirección: Irapuato, Guanajuato. Plaza 3636 Gómez Morín");
        LaGenareria.setTypesOfFoodText("Tipos de Comida: Americana, Restaurante - Bar, Bar");
        LaGenareria.setPhoneText("Teléfono: 462 200 4863");
        LaGenareria.setAvailabilityText("Disponibilidad el día de hoy: 14:00 a 23:00");
        LaGenareria.setLogoRestaurant("@mipmap/lagenareria.png");

        CostillaWinebarlechon.setNameText("Costilla Winebarlechon");
        CostillaWinebarlechon.setAddressText("Dirección: Irapuato, Guanajuato. Plaza 3636 Gómez Morín");
        CostillaWinebarlechon.setTypesOfFoodText("Tipos de Comida: Bar, Restaurante - Bar, Café");
        CostillaWinebarlechon.setPhoneText("Teléfono: 462 607 9612");
        CostillaWinebarlechon.setAvailabilityText("Disponibilidad el día de hoy: 10:00 a 22:00");
        CostillaWinebarlechon.setLogoRestaurant("@mipmap/costilla.png");

        AtomicReference<Boolean> restaurantSelected = new AtomicReference<>(true);
        AtomicReference<Boolean> coffeeSelected = new AtomicReference<>(true);
        AtomicReference<Boolean> barSelected = new AtomicReference<>(true);

        addressText.setGravity(Gravity.CENTER);
        typesOfFoodText.setGravity(Gravity.CENTER);
        phoneText.setGravity(Gravity.CENTER);
        availabilityText.setGravity(Gravity.CENTER);

        btnRestaurant.setOnClickListener(view -> {
            restaurantSelected.set(true);
            coffeeSelected.set(false);
            barSelected.set(false);

            identificadorDeRestaurante.set(0);

            nameText.setText(Arena88.getNameText());
            addressText.setText(Arena88.getAddressText());
            typesOfFoodText.setText(Arena88.getTypesOfFoodText());
            phoneText.setText(Arena88.getPhoneText());
            availabilityText.setText(Arena88.getAvailabilityText());
            logoRestaurant.setImageResource(R.mipmap.arena88);
        });
        btnCoffee.setOnClickListener(view -> {
            restaurantSelected.set(false);
            coffeeSelected.set(true);
            barSelected.set(false);
            identificadorDeRestaurante.set(1);
            nameText.setText(CostillaWinebarlechon.getNameText());
            addressText.setText(CostillaWinebarlechon.getAddressText());
            typesOfFoodText.setText(CostillaWinebarlechon.getTypesOfFoodText());
            phoneText.setText(CostillaWinebarlechon.getPhoneText());
            availabilityText.setText(CostillaWinebarlechon.getAvailabilityText());
            logoRestaurant.setImageResource(R.mipmap.costilla);
        });
        btnBar.setOnClickListener(view -> {
            restaurantSelected.set(false);
            coffeeSelected.set(false);
            barSelected.set(true);
            identificadorDeRestaurante.set(0);
            nameText.setText(CostillaWinebarlechon.getNameText());
            addressText.setText(CostillaWinebarlechon.getAddressText());
            typesOfFoodText.setText(CostillaWinebarlechon.getTypesOfFoodText());
            phoneText.setText(CostillaWinebarlechon.getPhoneText());
            availabilityText.setText(CostillaWinebarlechon.getAvailabilityText());
            logoRestaurant.setImageResource(R.mipmap.costilla);

        });
        previos_restaurant.setOnClickListener(view -> {
            if(restaurantSelected.get()){
                switch (identificadorDeRestaurante.get()){
                    case 0:
                        identificadorDeRestaurante.set(2);
                        nameText.setText(CostillaWinebarlechon.getNameText());
                        addressText.setText(CostillaWinebarlechon.getAddressText());
                        typesOfFoodText.setText(CostillaWinebarlechon.getTypesOfFoodText());
                        phoneText.setText(CostillaWinebarlechon.getPhoneText());
                        availabilityText.setText(CostillaWinebarlechon.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.costilla);
                        break;
                    case 1:
                        identificadorDeRestaurante.set(0);
                        nameText.setText(Arena88.getNameText());
                        addressText.setText(Arena88.getAddressText());
                        typesOfFoodText.setText(Arena88.getTypesOfFoodText());
                        phoneText.setText(Arena88.getPhoneText());
                        availabilityText.setText(Arena88.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.arena88);
                        break;
                    case 2:
                        identificadorDeRestaurante.set(1);
                        nameText.setText(LaGenareria.getNameText());
                        addressText.setText(LaGenareria.getAddressText());
                        typesOfFoodText.setText(LaGenareria.getTypesOfFoodText());
                        phoneText.setText(LaGenareria.getPhoneText());
                        availabilityText.setText(LaGenareria.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.lagenareria);break;
                }
            }else
            if(coffeeSelected.get()){
                nameText.setText(CostillaWinebarlechon.getNameText());
                addressText.setText(CostillaWinebarlechon.getAddressText());
                typesOfFoodText.setText(CostillaWinebarlechon.getTypesOfFoodText());
                phoneText.setText(CostillaWinebarlechon.getPhoneText());
                availabilityText.setText(CostillaWinebarlechon.getAvailabilityText());
                logoRestaurant.setImageResource(R.mipmap.costilla);
            }else
            if(barSelected.get()){
                switch (identificadorDeRestaurante.get()){
                    case 0:
                        identificadorDeRestaurante.set(2);
                        nameText.setText(LaGenareria.getNameText());
                        addressText.setText(LaGenareria.getAddressText());
                        typesOfFoodText.setText(LaGenareria.getTypesOfFoodText());
                        phoneText.setText(LaGenareria.getPhoneText());
                        availabilityText.setText(LaGenareria.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.lagenareria);
                        break;
                    case 1:
                        identificadorDeRestaurante.set(0);
                        nameText.setText(CostillaWinebarlechon.getNameText());
                        addressText.setText(CostillaWinebarlechon.getAddressText());
                        typesOfFoodText.setText(CostillaWinebarlechon.getTypesOfFoodText());
                        phoneText.setText(CostillaWinebarlechon.getPhoneText());
                        availabilityText.setText(CostillaWinebarlechon.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.costilla);
                        break;
                    case 2:
                        identificadorDeRestaurante.set(1);
                        nameText.setText(Arena88.getNameText());
                        addressText.setText(Arena88.getAddressText());
                        typesOfFoodText.setText(Arena88.getTypesOfFoodText());
                        phoneText.setText(Arena88.getPhoneText());
                        availabilityText.setText(Arena88.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.arena88);
                        break;
                }
            }
        });
        next_restaurant.setOnClickListener(view -> {
            if(restaurantSelected.get()){
                switch (identificadorDeRestaurante.get()){
                    case 0:
                        identificadorDeRestaurante.set(1);
                        nameText.setText(LaGenareria.getNameText());
                        addressText.setText(LaGenareria.getAddressText());
                        typesOfFoodText.setText(LaGenareria.getTypesOfFoodText());
                        phoneText.setText(LaGenareria.getPhoneText());
                        availabilityText.setText(LaGenareria.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.lagenareria);
                        break;
                    case 1:
                        identificadorDeRestaurante.set(2);
                        nameText.setText(CostillaWinebarlechon.getNameText());
                        addressText.setText(CostillaWinebarlechon.getAddressText());
                        typesOfFoodText.setText(CostillaWinebarlechon.getTypesOfFoodText());
                        phoneText.setText(CostillaWinebarlechon.getPhoneText());
                        availabilityText.setText(CostillaWinebarlechon.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.costilla);
                        break;
                    case 2:
                        identificadorDeRestaurante.set(0);
                        nameText.setText(Arena88.getNameText());
                        addressText.setText(Arena88.getAddressText());
                        typesOfFoodText.setText(Arena88.getTypesOfFoodText());
                        phoneText.setText(Arena88.getPhoneText());
                        availabilityText.setText(Arena88.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.arena88);
                        break;
                }
            }else
            if(coffeeSelected.get()){
                nameText.setText(CostillaWinebarlechon.getNameText());
                addressText.setText(CostillaWinebarlechon.getAddressText());
                typesOfFoodText.setText(CostillaWinebarlechon.getTypesOfFoodText());
                phoneText.setText(CostillaWinebarlechon.getPhoneText());
                availabilityText.setText(CostillaWinebarlechon.getAvailabilityText());
                logoRestaurant.setImageResource(R.mipmap.costilla);
            }else
            if(barSelected.get()){
                switch (identificadorDeRestaurante.get()){
                    case 0:
                        identificadorDeRestaurante.set(1);
                        nameText.setText(LaGenareria.getNameText());
                        addressText.setText(LaGenareria.getAddressText());
                        typesOfFoodText.setText(LaGenareria.getTypesOfFoodText());
                        phoneText.setText(LaGenareria.getPhoneText());
                        availabilityText.setText(LaGenareria.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.lagenareria);
                        break;
                    case 1:
                        identificadorDeRestaurante.set(2);
                        nameText.setText(Arena88.getNameText());
                        addressText.setText(Arena88.getAddressText());
                        typesOfFoodText.setText(Arena88.getTypesOfFoodText());
                        phoneText.setText(Arena88.getPhoneText());
                        availabilityText.setText(Arena88.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.arena88);
                        break;
                    case 2:
                        identificadorDeRestaurante.set(0);
                        nameText.setText(CostillaWinebarlechon.getNameText());
                        addressText.setText(CostillaWinebarlechon.getAddressText());
                        typesOfFoodText.setText(CostillaWinebarlechon.getTypesOfFoodText());
                        phoneText.setText(CostillaWinebarlechon.getPhoneText());
                        availabilityText.setText(CostillaWinebarlechon.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.costilla);
                        break;
                }
            }
        });
        somethingyoumightlikebutton.setOnClickListener(view -> {
            Intent switchInterface = new Intent(this, SomethingYouMightLike.class);
            startActivity(switchInterface);
        });
        Button btnMore = findViewById(R.id.btnMore);
        btnMore.setBackgroundColor(Color.rgb(255, 128, 0));
        btnMore.setOnClickListener(view -> {
            Intent moreInterface = new Intent(this, More.class);
            startActivity(moreInterface);
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this,"Inicio", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item2:
                Toast.makeText(this,"Contacto", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item3:
                Toast.makeText(this,"Login", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}