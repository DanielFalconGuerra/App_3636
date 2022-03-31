package com.example.a3636.restaurantdata;


public class RestaurantInformation {

    private String logoRestaurant = "";
    private String addressText = "";
    private String availabilityText = "";
    private String typesOfFoodText = "";
    private String phoneText = "";

    public RestaurantInformation() {
        this.logoRestaurant = logoRestaurant;
        this.addressText = addressText;
        this.availabilityText = availabilityText;
        this.typesOfFoodText = typesOfFoodText;
        this.phoneText = phoneText;
    }

    public String getLogoRestaurant() {
        return logoRestaurant;
    }

    public String getAddressText() {
        return addressText;
    }

    public String getAvailabilityText() {
        return availabilityText;
    }

    public String getTypesOfFoodText() {
        return typesOfFoodText;
    }

    public String getPhoneText() {
        return phoneText;
    }

    public void setLogoRestaurant(String logoRestaurant) {
        this.logoRestaurant = logoRestaurant;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public void setAvailabilityText(String availabilityText) {
        this.availabilityText = availabilityText;
    }

    public void setTypesOfFoodText(String typesOfFoodText) {
        this.typesOfFoodText = typesOfFoodText;
    }

    public void setPhoneText(String phoneText) {
        this.phoneText = phoneText;
    }
}
