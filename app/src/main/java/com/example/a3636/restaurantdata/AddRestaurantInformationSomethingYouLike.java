package com.example.a3636.restaurantdata;

import com.example.a3636.R;

public class AddRestaurantInformationSomethingYouLike {
    private String businessName = "";
    private String businessDescription = "";
    private int businessLogo = R.mipmap.logo3636;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public int getBusinessLogo() {
        return businessLogo;
    }

    public void setBusinessLogo(int businessLogo) {
        this.businessLogo = businessLogo;
    }

    public AddRestaurantInformationSomethingYouLike() {
        this.businessName = businessName;
        this.businessDescription = businessDescription;
        this.businessLogo = businessLogo;

    }
}
