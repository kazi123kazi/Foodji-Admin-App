package org.example.foodie.org.example.foodie.apifetch;


import com.google.gson.JsonObject;

import org.example.foodie.models.Order;
import org.example.foodie.models.ResponseUser;
import org.example.foodie.models.Restaurant;
import org.example.foodie.models.RestaurantCreate.RestaurantCreate;
import org.example.foodie.models.RestaurantLogIn.ResponseRestaurantUser;
import org.example.foodie.models.RestaurantLogIn.RestaurantUser;
import org.example.foodie.models.User;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FoodieClient {
    //Create user endpoint
    @POST("user")
    Call<ResponseUser> Register(@Body User user);

    //Login user endpoint
    @POST("user/login")
    Call<ResponseUser> Login(@Body User user);

    //Logout user endpoint
    @POST("user/logout")
    Call<Void> Logout(@Header("Authorization") String token);

    //get all user info
    @GET("user/me")
    Call<ResponseUser> getData(@Header("Authorization") String token);

    //Ordering route for user
    @POST("user/order")
    Call<Order> placeOrder(@Header("Authorization") String token, @Body Order order);



    //Connecting to endpoint to see all restaurants available
    @GET("restaurant")
    Call<List<Restaurant>> getRestaurant();

    @GET("restaurant/{id}")
    Call<Restaurant> getFood(@Path("id") String id);


    //Restaturant create
    @POST("restaurant")
    Call<ResponseUser> createRestaurant(@Body RestaurantCreate restaurantCreate);

    @POST("restaurant/login")
    Call<ResponseRestaurantUser> logInRestaurant(@Body RestaurantUser restaurantUser);
}