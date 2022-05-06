package com.example.a3636.findrestaurants;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a3636.ShowRestaurantsFound;
import com.example.a3636.database.DatabaseConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FindRestaurantsByTypeOfFoodAndHorary {
    public String[] findRestaurants(String typeFood, String hour){
        DatabaseConnection dbConnect = new DatabaseConnection();
        final int[] countRestaurantFound = {0};
        String IDRestaurantFound[] = new String[17];
        String RestaurantFound[] = {"","","","","","","","","","","","","","","","","",""};
        int count = 0;

        String fecha = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        String year = "", month = "", day = "";
        year = fecha.substring(0,4);
        month = fecha.substring(5,7);
        day = fecha.substring(8,10);
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
        try {
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
                                            RestaurantFound[count] = IDRestaurantFound[i];
                                            count++;
                                        }else{
                                            if(countRestaurantFound[0] == 0){
                                                countRestaurantFound[0] += 1;
                                            }
                                        }
                                    }
                                }
                            }else{
                                if(countRestaurantFound[0] == 0){
                                    countRestaurantFound[0] += 1;
                                }
                            }
                        }
                    }
                }
            }else{
                RestaurantFound[0] = "No se encontraron restaurantes";
                return  RestaurantFound;
            }
        }catch (Exception e){
            RestaurantFound[0] = "Error";
            return  RestaurantFound;
        }
        return  RestaurantFound;

    }
    private int getDayToDatabase(String day){
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
}
