package org.example.foodie.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseUser {



    @SerializedName("token")
    String token;

    @SerializedName("user")
    User user;
    @SerializedName("orders")
    List<Order> orders;


    public ResponseUser(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public User getUser() {
        return user;
    }
}
