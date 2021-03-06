package com.example.a3636;

import static android.graphics.Color.GRAY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.a3636.database.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {
    TextView tv1;
    TextView latitud,longitud;
    TextView direccion;
    String ciudad = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView addressFound = findViewById(R.id.addressFound);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txtSearchingAddress = findViewById(R.id.txtSearchingAddress);
        LottieAnimationView imageAnimationLocation = findViewById(R.id.imageAnimationLocation);
        DatabaseConnection connection = new DatabaseConnection();
        connection.CONN();
        imageAnimationLocation.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e("Animation:","start");
                imageAnimationLocation.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("Animation:","end");
                imageAnimationLocation.setVisibility(View.GONE);
                txtSearchingAddress.setVisibility(View.GONE);
                ConstraintLayout layoutLocation = findViewById(R.id.layoutLocation);
                layoutLocation.setVisibility(View.VISIBLE);
                latitud = (TextView) findViewById(R.id.txtLatitud);
                longitud = (TextView) findViewById(R.id.txtLongitud);
                direccion = (TextView) findViewById(R.id.txtDireccion);

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
                } else {
                    locationStart();
                    Button btnSearch = findViewById(R.id.btnSearchBC);
                    Button btnTest = findViewById(R.id.btnTest);
                    Button btnLoginMain = findViewById(R.id.btnLoginMain);
                    //btnSearch.setBackgroundColor(Color.rgb(255, 128, 0));
                    btnSearch.setOnClickListener(view -> {
                        String dir = (String) direccion.getText();
                        if(dir.equals("")){
                            direccion.setText("La ubicacion no ha podido ser obtenida");
                            btnSearch.setVisibility(View.GONE);
                            direccion.setGravity(Gravity.CENTER);
                            elementsError();
                        }else{
                            String[] textElements = dir.split(",");
                            int index = textElements[2].indexOf(" ", 1);
                            ciudad = textElements[2].substring(index);

                            if(ciudad.equals("Irapuato")){
                                connection.CONN();
                                String numberRestaurants = connection.getRestaurantNumberByCity("3");
                                if(!numberRestaurants.equals("0")) {
                                    ((MyLocation) getApplication()).setLocation("Irapuato");
                                    Intent showCityRestaurants = new Intent(MainActivity.this, Interface3636.class);
                                    showCityRestaurants.putExtra("userName", "not logged in");
                                    startActivity(showCityRestaurants);
                                }else{
                                    messageError();
                                }
                            }else
                            if(ciudad.equals("Guanajuato")){
                                connection.CONN();
                                String numberRestaurants = connection.getRestaurantNumberByCity("1");
                                if(!numberRestaurants.equals("0")) {
                                    ((MyLocation) getApplication()).setLocation("Guanajuato");
                                    Intent showCityRestaurants = new Intent(MainActivity.this, Interface3636.class);
                                    showCityRestaurants.putExtra("userName", "not logged in");
                                    startActivity(showCityRestaurants);
                                }else{
                                    messageError();
                                }
                            }else
                            if(ciudad.equals("Le??n")){
                                connection.CONN();
                                String numberRestaurants = connection.getRestaurantNumberByCity("2");
                                if(!numberRestaurants.equals("0")) {
                                    ((MyLocation) getApplication()).setLocation("Le??n");
                                    Intent showCityRestaurants = new Intent(MainActivity.this, Interface3636.class);
                                    showCityRestaurants.putExtra("userName", "not logged in");
                                    startActivity(showCityRestaurants);
                                }else{
                                    messageError();
                                }
                            }else
                            if(ciudad.equals("Celaya")){
                                connection.CONN();
                                String numberRestaurants = connection.getRestaurantNumberByCity("4");
                                if(!numberRestaurants.equals("0")) {
                                    ((MyLocation) getApplication()).setLocation("Celaya");
                                    Intent showCityRestaurants = new Intent(MainActivity.this, Interface3636.class);
                                    showCityRestaurants.putExtra("userName", "not logged in");
                                    startActivity(showCityRestaurants);
                                }else{
                                    messageError();
                                }
                            }else
                            if(ciudad.equals("Chihuahua")){
                                connection.CONN();
                                String numberRestaurants = connection.getRestaurantNumberByCity("5");
                                if(!numberRestaurants.equals("0")) {
                                    ((MyLocation) getApplication()).setLocation("Chihuahua");
                                    Intent showCityRestaurants = new Intent(MainActivity.this, Interface3636.class);
                                    showCityRestaurants.putExtra("userName", "not logged in");
                                    startActivity(showCityRestaurants);
                                }else{
                                    messageError();
                                }
                            }else
                            if(ciudad.equals("Ju??rez")) {
                                connection.CONN();
                                String numberRestaurants = connection.getRestaurantNumberByCity("6");
                                if(!numberRestaurants.equals("0")) {
                                    ((MyLocation) getApplication()).setLocation("Ju??rez");
                                    Intent showCityRestaurants = new Intent(MainActivity.this, Interface3636.class);
                                    showCityRestaurants.putExtra("userName", "not logged in");
                                    startActivity(showCityRestaurants);
                                }else{
                                    messageError();
                                }
                            }else {
                                elementsError();
                                btnSearch.setVisibility(View.GONE);
                            }
                        }
                    });
                    //btnLoginMain.setBackgroundColor(Color.rgb(255, 128, 0));
                    btnLoginMain.setOnClickListener(view -> {
                        String dir = (String) direccion.getText();
                        String[] textElements = dir.split(",");
                        int index = textElements[2].indexOf(" ", 1);
                        ciudad = textElements[2].substring(index);

                        ((MyLocation)getApplication()).setLocation(ciudad);
                        Intent Login = new Intent(MainActivity.this, Login.class);
                        startActivity(Login);
                    });
                    btnTest.setVisibility(View.GONE);
                    btnTest.setOnClickListener(view -> {
                        DatabaseConnection connection = new DatabaseConnection();
                        connection.CONN();

                        //String inf = connection.getDayClosedRestaurant("1","1");
                        //ArrayList<String[]> test = connection.getRestaurantInformationByUserAdmin("17");
                        /*if(test == null || test.size()==0){
                            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,test.size(),Toast.LENGTH_SHORT).show();
                        }
                        for(int i = 0; i < test.size(); i++){
                            String[] elementos = test.get(i);
                            for(int j = 0; j < elementos.length; j++){
                                Toast.makeText(MainActivity.this,elementos[j],Toast.LENGTH_SHORT).show();
                            }
                        }*/

                        /*LinearLayout layoutUsuario = new LinearLayout(MainActivity.this);
                        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(50,50);
                        layoutUsuario.setBackgroundColor(GRAY);
                        layoutParams.gravity= Gravity.CENTER;
                        layoutUsuario.setLayoutParams(layoutParams);
                        ImageView image = new ImageView(MainActivity.this);
                        LinearLayout.LayoutParams layoutParamsImage=new LinearLayout.LayoutParams(40, 40);
                        layoutParamsImage.gravity= Gravity.CENTER;
                        image.setLayoutParams(layoutParamsImage);
                        DatabaseConnection dbConnect = new DatabaseConnection();
                        //obtener imagen de base de datos
                        //dbConnect.CONN();
                        //dbConnect.getImage();
                        /*byte[] imageReceived = dbConnect.getImage();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageReceived, 0, imageReceived.length);
                        Toast.makeText(MainActivity.this,String.valueOf(imageReceived.length),Toast.LENGTH_SHORT).show();
                        ImageView imageView = findViewById(R.id.imageView);
                        imageView.setImageBitmap(bitmap);*/

                            //System.out.println(obj.nextLine());
                        /*Intent mapTest = new Intent(getApplicationContext(), InterPCLocation.class);
                        startActivity(mapTest);*/
                    });
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

    private void elementsError(){
        /**
        TextView tvSuggestions = new TextView(this);
        Button btnCitySelected = new Button(this);
        LinearLayout layout = findViewById(R.id.layoutError);
        layout.setVisibility(View.VISIBLE);
        TextView tvError = new TextView(this);
        TextView tvSelectSpinner = new TextView(this);
        Spinner spinnerCiudad = findViewById(R.id.spinnerCiudad);
        LinearLayout layoutButton = new LinearLayout(this);
        String cities[] = {"Seleccione la ciudad", "Irapuato", "Guanajuato", "Le??n", "Celaya", "Chihuahua", "Juarez"};
        ArrayAdapter<CharSequence> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCiudad.setAdapter(adapter);
        spinnerCiudad.setVisibility(View.VISIBLE);
        tvError.setText("Tu ciudad no cuenta con restaurantes registrados en estos momentos");
        tvSuggestions.setText("O bien, seleccione la ciudad donde desea buscar");
        btnCitySelected.setText("Buscar por ciudad");
        tvSelectSpinner.setText("Seleccione la ciudad en la que desea realizar su b??squeda");
        btnCitySelected.setBackgroundColor(Color.rgb(255, 128, 0));
        tvError.setGravity(Gravity.CENTER);
        tvSuggestions.setGravity(Gravity.CENTER);
        tvSelectSpinner.setGravity(Gravity.CENTER);
        tvError.setPadding(0,30,0,20);
        tvSuggestions.setPadding(0,10,0,20);
        tvSelectSpinner.setPadding(0,10,0,20);
        tvError.setTextColor(GRAY);
        tvSuggestions.setTextColor(GRAY);
        tvSelectSpinner.setTextColor(GRAY);
        layout.addView(tvError);
        layout.addView(tvSuggestions);
        layoutButton.setGravity(Gravity.CENTER);
        layoutButton.setOrientation(LinearLayout.VERTICAL);
        layoutButton.addView(btnCitySelected);
        layoutButton.addView(tvSelectSpinner);
        layout.addView(layoutButton);
        /**/
        DatabaseConnection databaseConnection = new DatabaseConnection();
        LinearLayout layout = findViewById(R.id.layoutError);
        layout.setVisibility(View.VISIBLE);
        Spinner spinnerCiudad = findViewById(R.id.spinnerCiudad);
        String cities[] = {"Seleccione la ciudad", "Irapuato", "Guanajuato", "Le??n", "Celaya", "Chihuahua", "Ju??rez"};
        ArrayAdapter<CharSequence> adapter= new ArrayAdapter<>(this, R.layout.spinner_text_style, cities);
        adapter.setDropDownViewResource(R.layout.spinner_item_style);
        spinnerCiudad.setAdapter(adapter);
        Button btnSearchByCity = findViewById(R.id.buttonBuscar);
        btnSearchByCity.setOnClickListener(view1 -> {
            String city = spinnerCiudad.getSelectedItem().toString();
            if(city.equals("Seleccione la ciudad")){
                Toast.makeText(this,"Debe seleccionar una ciudad antes de continuar", Toast.LENGTH_LONG).show();
            }else{
                if(city.equals("Irapuato")){
                    databaseConnection.CONN();
                    String numberRestaurants = databaseConnection.getRestaurantNumberByCity("3");
                    if(!numberRestaurants.equals("0")) {
                        ((MyLocation) getApplication()).setLocation("Irapuato");
                        Intent showCityRestaurants = new Intent(this, Interface3636.class);
                        showCityRestaurants.putExtra("userName", "not logged in");
                        startActivity(showCityRestaurants);
                    }else{
                        messageError();
                    }
                }else
                if(city.equals("Le??n")){
                    databaseConnection.CONN();
                    String numberRestaurants = databaseConnection.getRestaurantNumberByCity("2");
                    if(!numberRestaurants.equals("0")) {
                        ((MyLocation) getApplication()).setLocation("Le??n");
                        Intent showCityRestaurants = new Intent(this, Interface3636.class);
                        showCityRestaurants.putExtra("userName", "not logged in");
                        startActivity(showCityRestaurants);
                    }else{
                        messageError();
                    }
                }else
                if(city.equals("Guanajuato")){
                    databaseConnection.CONN();
                    String numberRestaurants = databaseConnection.getRestaurantNumberByCity("1");
                    if(!numberRestaurants.equals("0")) {
                        ((MyLocation) getApplication()).setLocation("Guanajuato");
                        Intent showCityRestaurants = new Intent(this, Interface3636.class);
                        showCityRestaurants.putExtra("userName", "not logged in");
                        startActivity(showCityRestaurants);
                    }else{
                        messageError();
                    }
                }else
                if(city.equals("Chihuahua")){
                    databaseConnection.CONN();
                    String numberRestaurants = databaseConnection.getRestaurantNumberByCity("5");
                    if(!numberRestaurants.equals("0")) {
                        ((MyLocation) getApplication()).setLocation("Chihuahua");
                        Intent showCityRestaurants = new Intent(this, Interface3636.class);
                        showCityRestaurants.putExtra("userName", "not logged in");
                        startActivity(showCityRestaurants);
                    }else{
                        messageError();
                    }
                }else
                if(city.equals("Ju??rez")){
                    databaseConnection.CONN();
                    String numberRestaurants = databaseConnection.getRestaurantNumberByCity("6");
                    if(!numberRestaurants.equals("0")) {
                        ((MyLocation) getApplication()).setLocation("Ju??rez");
                        Intent showCityRestaurants = new Intent(this, Interface3636.class);
                        showCityRestaurants.putExtra("userName", "not logged in");
                        startActivity(showCityRestaurants);
                    }else{
                        messageError();
                    }
                }else
                if(city.equals("Celaya")){
                    databaseConnection.CONN();
                    String numberRestaurants = databaseConnection.getRestaurantNumberByCity("4");
                    if(!numberRestaurants.equals("0")) {
                        ((MyLocation) getApplication()).setLocation("Celaya");
                        Intent showCityRestaurants = new Intent(this, Interface3636.class);
                        showCityRestaurants.putExtra("userName", "not logged in");
                        startActivity(showCityRestaurants);
                    }else{
                        messageError();
                    }
                }
            }
        });
    }
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        latitud.setText("Localizaci??n agregada");
        direccion.setText("");
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }
    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    direccion.setText(DirCalle.getAddressLine(0));
                    Log.d("direccion", DirCalle.getAddressLine(0));
                    TextView addressFound = findViewById(R.id.addressFound);
                    addressFound.setText("Su Direcci??n ha sido detectada");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        MainActivity mainActivity;
        public MainActivity getMainActivity() {
            return mainActivity;
        }
        public void setMainActivity(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }
        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();
            String sLatitud = String.valueOf(loc.getLatitude());
            String sLongitud = String.valueOf(loc.getLongitude());
            latitud.setText(sLatitud);
            longitud.setText(sLongitud);
            this.mainActivity.setLocation(loc);
        }
        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            latitud.setText("GPS Desactivado");
        }
        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            latitud.setText("GPS Activado");
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }

    public void messageError(){
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("No hay restaurantes registrados en  esta ciudad. \nSeleccione una ciudad diferente.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("alertDialog","OK");
                    }
                })
                .show();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.item1:
                break;
            case R.id.item2:
                break;
            case R.id.item3:
                Intent login = new Intent(this, Login.class);
                startActivity(login);
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/
}