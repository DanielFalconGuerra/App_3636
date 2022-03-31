package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3636.database.DatabaseConnection;
import com.example.a3636.restaurantdata.RestaurantInformation;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {
    TextView tv1;
    DatabaseConnection connection = new DatabaseConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AtomicInteger identificadorDeRestaurante = new AtomicInteger();

        //Spinner para Tipo de Comida
        Spinner spinnerTipoComida = findViewById(R.id.menu_tipoComida);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.TipodeComida, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTipoComida.setAdapter(adapter);
        //Spinner para Horario
        Spinner spinnerHorario = findViewById(R.id.menu_Horario);
        ArrayAdapter<CharSequence>adapterHorario=ArrayAdapter.createFromResource(this,R.array.Horario, android.R.layout.simple_spinner_item);
        adapterHorario.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerHorario.setAdapter(adapterHorario);

        //Codigo para Buscar Por Categoría
        Button btnRestaurant = findViewById(R.id.btnRestaurant);
        Button btnCoffee = findViewById(R.id.btnCoffee);
        Button btnBar = findViewById(R.id.btnBar);

        //Cambiar de Restaurante
        Button previos_restaurant = findViewById(R.id.previos_restaurant);
        Button next_restaurant = findViewById(R.id.next_restaurant);
        Button somethingyoumightlikebutton = findViewById(R.id.somethingyoumightlikebutton);

        ImageView logoRestaurant = findViewById(R.id.logoRestaurant);
        TextView addressText = findViewById(R.id.addressText);
        TextView availabilityText = findViewById(R.id.availabilityText);
        TextView typesOfFoodText = findViewById(R.id.typesOfFoodText);
        TextView phoneText = findViewById(R.id.phoneText);

        RestaurantInformation Arena88 = new RestaurantInformation();
        RestaurantInformation LaGenareria = new RestaurantInformation();
        RestaurantInformation CostillaWinebarlechon = new RestaurantInformation();

        Arena88.setAddressText("Dirección: Irapuato, Guanajuato. Plaza 3636 Gómez Morín");
        Arena88.setTypesOfFoodText("Tipos de Comida: Bar, Restaurante - Bar, Mariscos");
        Arena88.setPhoneText("Teléfono: 462 688 3664");
        Arena88.setAvailabilityText("Disponibilidad el día de hoy: 12:00 a 23:00");
        Arena88.setLogoRestaurant("@mipmap/arena88.jpg");

        LaGenareria.setAddressText("Dirección: Irapuato, Guanajuato. Plaza 3636 Gómez Morín");
        LaGenareria.setTypesOfFoodText("Tipos de Comida: Americana, Restaurante - Bar, Bar");
        LaGenareria.setPhoneText("Teléfono: 462 200 4863");
        LaGenareria.setAvailabilityText("Disponibilidad el día de hoy: 14:00 a 23:00");
        LaGenareria.setLogoRestaurant("@mipmap/lagenareria.png");

        CostillaWinebarlechon.setAddressText("Dirección: Irapuato, Guanajuato. Plaza 3636 Gómez Morín");
        CostillaWinebarlechon.setTypesOfFoodText("Tipos de Comida: Bar, Restaurante - Bar, Café");
        CostillaWinebarlechon.setPhoneText("Teléfono: 462 607 9612");
        CostillaWinebarlechon.setAvailabilityText("Disponibilidad el día de hoy: 10:00 a 22:00");
        CostillaWinebarlechon.setLogoRestaurant("@mipmap/costilla.png");

        AtomicReference<Boolean> restaurantSelected = new AtomicReference<>(true);
        AtomicReference<Boolean> coffeeSelected = new AtomicReference<>(true);
        AtomicReference<Boolean> barSelected = new AtomicReference<>(true);

        btnRestaurant.setOnClickListener(view -> {
            restaurantSelected.set(true);
            coffeeSelected.set(false);
            barSelected.set(false);

            identificadorDeRestaurante.set(0);

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
                        addressText.setText(CostillaWinebarlechon.getAddressText());
                        typesOfFoodText.setText(CostillaWinebarlechon.getTypesOfFoodText());
                        phoneText.setText(CostillaWinebarlechon.getPhoneText());
                        availabilityText.setText(CostillaWinebarlechon.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.costilla);
                        break;
                    case 1:
                        identificadorDeRestaurante.set(0);
                        addressText.setText(Arena88.getAddressText());
                        typesOfFoodText.setText(Arena88.getTypesOfFoodText());
                        phoneText.setText(Arena88.getPhoneText());
                        availabilityText.setText(Arena88.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.arena88);
                        break;
                    case 2:
                        identificadorDeRestaurante.set(1);
                        addressText.setText(LaGenareria.getAddressText());
                        typesOfFoodText.setText(LaGenareria.getTypesOfFoodText());
                        phoneText.setText(LaGenareria.getPhoneText());
                        availabilityText.setText(LaGenareria.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.lagenareria);break;
                }
            }else
            if(coffeeSelected.get()){
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
                        addressText.setText(LaGenareria.getAddressText());
                        typesOfFoodText.setText(LaGenareria.getTypesOfFoodText());
                        phoneText.setText(LaGenareria.getPhoneText());
                        availabilityText.setText(LaGenareria.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.lagenareria);
                        break;
                    case 1:
                        identificadorDeRestaurante.set(0);
                        addressText.setText(CostillaWinebarlechon.getAddressText());
                        typesOfFoodText.setText(CostillaWinebarlechon.getTypesOfFoodText());
                        phoneText.setText(CostillaWinebarlechon.getPhoneText());
                        availabilityText.setText(CostillaWinebarlechon.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.costilla);
                        break;
                    case 2:
                        identificadorDeRestaurante.set(1);
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
                        addressText.setText(LaGenareria.getAddressText());
                        typesOfFoodText.setText(LaGenareria.getTypesOfFoodText());
                        phoneText.setText(LaGenareria.getPhoneText());
                        availabilityText.setText(LaGenareria.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.lagenareria);
                        break;
                    case 1:
                        identificadorDeRestaurante.set(2);
                        addressText.setText(CostillaWinebarlechon.getAddressText());
                        typesOfFoodText.setText(CostillaWinebarlechon.getTypesOfFoodText());
                        phoneText.setText(CostillaWinebarlechon.getPhoneText());
                        availabilityText.setText(CostillaWinebarlechon.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.costilla);
                        break;
                    case 2:
                        identificadorDeRestaurante.set(0);
                        addressText.setText(Arena88.getAddressText());
                        typesOfFoodText.setText(Arena88.getTypesOfFoodText());
                        phoneText.setText(Arena88.getPhoneText());
                        availabilityText.setText(Arena88.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.arena88);
                        break;
                }
            }else
            if(coffeeSelected.get()){
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
                        addressText.setText(LaGenareria.getAddressText());
                        typesOfFoodText.setText(LaGenareria.getTypesOfFoodText());
                        phoneText.setText(LaGenareria.getPhoneText());
                        availabilityText.setText(LaGenareria.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.lagenareria);
                        break;
                    case 1:
                        identificadorDeRestaurante.set(2);
                        addressText.setText(Arena88.getAddressText());
                        typesOfFoodText.setText(Arena88.getTypesOfFoodText());
                        phoneText.setText(Arena88.getPhoneText());
                        availabilityText.setText(Arena88.getAvailabilityText());
                        logoRestaurant.setImageResource(R.mipmap.arena88);
                        break;
                    case 2:
                        identificadorDeRestaurante.set(0);
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
        //Mostrar informacion de restaurantes
        //LinearLayout datosRestaurantes = (LinearLayout) findViewById(R.id.datosRestaurantes);
        //datosRestaurantes.addView(layout);

    }
}