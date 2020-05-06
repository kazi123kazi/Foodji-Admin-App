package org.example.foodie.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.example.foodie.Respositories.RestaurantsRepository;
import org.example.foodie.Respositories.UserRepository;
import org.example.foodie.models.ResponseUser;
import org.example.foodie.models.Restaurant;

import java.util.List;

public class OrdersViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<ResponseUser> mutableLiveData;
    private UserRepository userRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        userRepository = new UserRepository().getInstance();
        mutableLiveData = userRepository.getUser();
    }

    public LiveData<ResponseUser> getRestaurantRepository() {
        return mutableLiveData;
    }

}
