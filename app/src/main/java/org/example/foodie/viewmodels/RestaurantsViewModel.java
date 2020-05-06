package org.example.foodie.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.example.foodie.Respositories.RestaurantsRepository;
import org.example.foodie.models.Restaurant;

import java.util.List;

public class RestaurantsViewModel extends ViewModel {

    private MutableLiveData<List<Restaurant>> mutableLiveData;
    private RestaurantsRepository restaurantsRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        restaurantsRepository = new RestaurantsRepository().getInstance();
        mutableLiveData = restaurantsRepository.getRestaurant();
    }

    public LiveData<List<Restaurant>> getRestaurantRepository() {
        return mutableLiveData;
    }

}
