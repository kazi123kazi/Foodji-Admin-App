package org.example.foodie.org.example.foodie.apifetch;


import org.example.foodie.models.Food;
import org.example.foodie.models.Foodid;
import org.example.foodie.models.ResponseUser;
import org.example.foodie.models.Restaurant;
import org.example.foodie.models.RestaurantCreate.RestaurantCreate;
import org.example.foodie.models.RestaurantLogIn.ResponseRestaurantUser;
import org.example.foodie.models.RestaurantLogIn.RestaurantUser;
import org.example.foodie.models.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface FoodieClient {

    //get all user info
    @GET("restaurant/me")
    Call<ResponseUser> getData(@Header("Authorization") String token);

    @GET("restaurant/{id}")
    Call<Restaurant> getFood(@Path("id") String id);
    //post food to restaurant
    @POST("food")
    Call<Foodid> postFood(@Header("Authorization") String token, @Body Food food);
    //create restaurant
    @POST("restaurant")
    Call<ResponseUser> createRestaurant(@Body RestaurantCreate restaurantCreate);
    //log in user
    @POST("restaurant/login")
    Call<ResponseRestaurantUser> logInRestaurant(@Body RestaurantUser restaurantUser);
    //Logout restaurant
    @POST("restaurant/logout")
    Call<Void> Logout(@Header("Authorization") String token);

    @Multipart
    @POST("restaurant/image")
    Call<ResponseBody> postImage(@Header("Authorization") String token, @Part MultipartBody.Part image);



}