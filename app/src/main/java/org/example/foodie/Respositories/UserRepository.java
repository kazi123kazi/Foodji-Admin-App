package org.example.foodie.Respositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.example.foodie.WelcomeActvity;
import org.example.foodie.models.ResponseUser;
import org.example.foodie.models.Restaurant;
import org.example.foodie.org.example.foodie.apifetch.FoodieClient;
import org.example.foodie.org.example.foodie.apifetch.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static UserRepository userRepository;
    private FoodieClient foodieClient;

    public UserRepository() {
        foodieClient = ServiceGenerator.createService(FoodieClient.class);
    }

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }

        return userRepository;

    }

    public static UserRepository getRestaurantsRepository() {
        return userRepository;
    }

    public MutableLiveData<ResponseUser> getUser() {

        MutableLiveData<ResponseUser> userData = new MutableLiveData<>();

        Call<ResponseUser> call = foodieClient.getData(WelcomeActvity.token);

        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {


                Log.i("Response", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    userData.setValue((ResponseUser) response.body());

                }

            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {

                userData.setValue(null);
            }
        });


        return userData;


    }

}