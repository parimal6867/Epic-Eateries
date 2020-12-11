package com.example.epiceateries2.chefFoodPanel;

public class FoodDetails {

    public String Dishes,Quantity,Price,Description,ImageUrl,RandomUID,chefid;

    //Alt+insert

    public FoodDetails(String dishes, String quantity, String price, String description, String imageUrl, String randomUID, String chefid) {
        Dishes = dishes;
        Quantity = quantity;
        Price = price;
        Description = description;
        ImageUrl = imageUrl;
        RandomUID = randomUID;
        this.chefid = chefid;
    }
}
