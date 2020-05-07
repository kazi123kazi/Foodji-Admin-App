package org.example.foodie.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Order {

    public double totalPrice;
    @SerializedName("user")
    public User user;
    @SerializedName("restaurant")
    Restaurant restaurant;
    @SerializedName("_id")
    String _id;
    @SerializedName("restaurantId")
    String restaurantId;

    List<Restaurant> restaurantList;


    public Order() {
    }

    public User getUser() {
        return user;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public String getRestaurantId() {
        return restaurantId;
    }
    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }
}
