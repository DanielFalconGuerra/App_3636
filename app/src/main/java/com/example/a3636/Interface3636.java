package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3636.database.DatabaseConnection;
import com.example.a3636.findrestaurants.FindRestaurantsByTypeOfFoodAndHorary;
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

        LinearLayout layoutShowRestaurantFound = findViewById(R.id.layoutShowRestaurantFound);

        //Recuper ubicacion
        String location = ((MyLocation)getApplication()).getLocation();

        String userName = getIntent().getStringExtra("userName");

        nameUserSession.setText(userName);
        try {
            //Obtener ID del Usuario
            connection.CONN();
            String ID = connection.getIDUser(userName);
            //Obtener imagen de usuario
            byte[] imageReceived = connection.getImage(ID);
            if(imageReceived != null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageReceived, 0, imageReceived.length);
                imageUser.setImageBitmap(bitmap);
            }
        }catch (Exception e){
            Toast.makeText(this,"Ha ocurrido un error, intentelo más tarde", Toast.LENGTH_LONG).show();
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        }
        logIn.setText("Cambiar de usuario");
        logIn.setOnClickListener(view -> {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        });

        settingsIM.setOnClickListener(view -> {
            String userConnected = nameUserSession.getText().toString();
            if(userConnected.equals("not logged in")){
                Toast.makeText(this,"Debes iniciar sesión para usar esta opción", Toast.LENGTH_LONG).show();
            }else{
                Intent settings = new Intent(this, UserSettings.class);
                settings.putExtra("userName",userConnected);
                startActivity(settings);
            }
        });

        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        try {
            connection.CONN();
            numberNotificationReceived = connection.getNumberNotifications(date);
            if(!numberNotificationReceived.equals("")){
                notificationsIM.setImageResource(R.drawable.iconsnotification);
            }
        }catch (Exception e){
            Toast.makeText(this,"Ha ocurrido un error obteniendo las notificaciones", Toast.LENGTH_LONG).show();
        }

        notificationsIM.setOnClickListener(view -> {
            String userConnected = nameUserSession.getText().toString();
            if(userConnected.equals("not logged in")){
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
        try {
            //Obtener tipos de comida de base de datos
            connection.CONN();
            //foodType = connection.getTypesOfFood();
            foodType = typesOfFoodAndSchedules.getTypesOfFood();
            //ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.TipodeComida, android.R.layout.simple_spinner_item);
            ArrayAdapter<CharSequence> adapter= new ArrayAdapter<>(this, R.layout.spinner_text_style, foodType);
            adapter.setDropDownViewResource(R.layout.spinner_item_style);
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
            adapterHour.setDropDownViewResource(R.layout.spinner_item_style);
            spinnerHorario.setAdapter(adapterHour);

            Button btnSearchRestaurant = findViewById(R.id.btnSearchRestaurant);
            btnSearchRestaurant.setBackgroundColor(Color.rgb(255, 128, 0));
            btnSearchRestaurant.setOnClickListener(view -> {
                layoutShowRestaurantFound.setVisibility(View.VISIBLE);
                String typeFood = spinnerTipoComida.getSelectedItem().toString();
                String hour = spinnerHorario.getSelectedItem().toString();
                if(!(typeFood.equals("Seleccione el tipo de comida")||(hour.equals("Selecciona la hora")))){
                    /*Intent showRestaurantFound = new Intent(this, ShowRestaurantsFound.class);
                    showRestaurantFound.putExtra("typeFood", typeFood);
                    showRestaurantFound.putExtra("hour", hour);
                    startActivity(showRestaurantFound);*/
                    if (layoutShowRestaurantFound.getChildCount() > 0)
                        layoutShowRestaurantFound.removeAllViews();

                    FindRestaurantsByTypeOfFoodAndHorary findRestaurantsObj = new FindRestaurantsByTypeOfFoodAndHorary();
                    String restaurants[] = findRestaurantsObj.findRestaurants(typeFood, hour);
                    for(int i = 0; i < restaurants.length; i++){
                        if(!restaurants[i].equals("")&&!restaurants[i].equals("No se encontraron restaurantes")&&!restaurants[i].equals("Error")){
                            layoutShowRestaurantFound.addView(showRestaurantsFound(restaurants[i]));
                        }
                    }
                    if(layoutShowRestaurantFound.getChildCount() == 0){
                        TextView errorTV = new TextView(this);
                        errorTV.setText("No se encontraron restaurantes");
                        errorTV.setTextColor(Color.GRAY);
                        errorTV.setTextSize(20);
                        errorTV.setGravity(Gravity.CENTER);
                        layoutShowRestaurantFound.addView(errorTV);
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(this,"Ha ocurrido un error, intentelo más tarde", Toast.LENGTH_LONG).show();
        }


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
    public LinearLayout showRestaurantsFound(String idRestaurant){
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.setPadding(0,30,0,0);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView nameBusiness = new TextView(this);
        TextView addressBusiness = new TextView(this);
        TextView typeOfFoodBusiness = new TextView(this);
        TextView phoneBusiness = new TextView(this);
        TextView availabilityBusiness = new TextView(this);
        ImageView logoBusiness = new ImageView(this);
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
            btnShowMenu.setOnClickListener(view -> {
                String urlMenu = "http://app.softappetit.mx:50293/apps/6If2zVm/genareria/MenuDigital.php";
                Bundle url = new Bundle();
                url.putString("url", urlMenu);
                Intent showDigitalMenu = new Intent(this, ShowDigitalMenu.class);
                showDigitalMenu.putExtras(url);
                startActivity(showDigitalMenu);
            });
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
            btnShowMenu.setOnClickListener(view -> {
                String urlMenu = "http://app.softappetit.mx:50293/apps/coA98wi/arena88/MenuDigital.php";
                Bundle url = new Bundle();
                url.putString("url", urlMenu);
                Intent showDigitalMenu = new Intent(this, ShowDigitalMenu.class);
                showDigitalMenu.putExtras(url);
                startActivity(showDigitalMenu);
            });
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
            btnShowMenu.setOnClickListener(view -> {
                String urlMenu = "http://app.softappetit.mx:50293/apps/bcuV1kz/costilla/MenuDigital.php";
                Bundle url = new Bundle();
                url.putString("url", urlMenu);
                Intent showDigitalMenu = new Intent(this, ShowDigitalMenu.class);
                showDigitalMenu.putExtras(url);
                startActivity(showDigitalMenu);
            });
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
        return layout;
    }
}