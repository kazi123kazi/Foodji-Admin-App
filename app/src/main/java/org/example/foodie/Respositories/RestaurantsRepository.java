package org.example.foodie.Respositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.example.foodie.models.Restaurant;
import org.example.foodie.org.example.foodie.apifetch.FoodieClient;
import org.example.foodie.org.example.foodie.apifetch.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantsRepository {

    public static RestaurantsRepository restaurantsRepository;
    private FoodieClient foodieClient;

    public RestaurantsRepository() {
        foodieClient = ServiceGenerator.createService(FoodieClient.class);
    }

    public static RestaurantsRepository getInstance() {
        if (restaurantsRepository == null) {
            restaurantsRepository = new RestaurantsRepository();
        }

        return restaurantsRepository;

    }

    public static RestaurantsRepository getRestaurantsRepository() {
        return restaurantsRepository;
    }

    public MutableLiveData<List<Restaurant>> getRestaurant() {

        MutableLiveData<List<Restaurant>> restaurantData = new MutableLiveData<>();

        Call<List<Restaurant>> call = foodieClient.getRestaurant();

        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {


                Log.i("Response", String.valueOf(response.body().get(0).getName()));
                if (response.isSuccessful()) {
                    restaurantData.setValue(response.body());

                }

            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {

                restaurantData.setValue(null);
            }
        });


        return restaurantData;


    }

}
