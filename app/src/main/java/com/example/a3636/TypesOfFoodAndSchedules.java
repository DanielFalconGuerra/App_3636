package com.example.a3636;

public class TypesOfFoodAndSchedules {
    private String typesOfFood[] = new String[]{
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
        String schedules[] = new String[48];
        int hour = 0;
        for(int i = 0; i < 48; i++){
            String timeFormed = "";
            if(hour < 10){
                if(i % 2 == 0){
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
        }
        return schedules;
    }
}
