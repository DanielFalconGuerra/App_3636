package com.example.a3636;

import android.util.Log;

public class TypesOfFoodAndSchedules {
    private String typesOfFood[] = new String[]{
            "Seleccione el tipo de comida",
            "Japonesa",
            "China",
            "Italiana",
            "Argentina",
            "Mexicana",
            "Bar",
            "Mariscos",
            "Carnes",
            "Vegana",
            "Americana",
            "Postres",
            "Restaurante - Bar",
            "Café",
            "Gourmet",
    };

    public TypesOfFoodAndSchedules(String[] typesOfFood) {
        this.typesOfFood = typesOfFood;
    }

    public TypesOfFoodAndSchedules() {

    }

    public String[] getTypesOfFood() {
        return typesOfFood;
    }

    public void setTypesOfFood(String[] typesOfFood) {
        this.typesOfFood = typesOfFood;
    }

    public String[] getItemsToArraySchedules(){
        String schedules[] = new String[50];
        int hour = 0;
        schedules[0] = "Selecciona la hora";
        schedules[1] = "00:00";
        for(int i = 2; i < 50; i++){
            String timeFormed = "";
            if(hour < 9){
                if((i % 2 == 0)){
                    timeFormed += "0" + String.valueOf(hour) + ":00";
                }else{
                    timeFormed += "0" + String.valueOf(hour) + ":30";
                    hour++;
                }
            }else{
                if(i % 2 == 0){
                    timeFormed += String.valueOf(hour) + ":00";
                }else{
                    timeFormed += String.valueOf(hour) + ":30";
                    hour++;
                }
            }
            schedules[i] = timeFormed;
            Log.d("schedules", String.valueOf(schedules));
        }
        return schedules;
    }
}
