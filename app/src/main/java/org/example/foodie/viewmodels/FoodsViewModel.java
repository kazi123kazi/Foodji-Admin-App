package org.example.foodie.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.example.foodie.Respositories.FoodRepository;
import org.example.foodie.Respositories.RestaurantsRepository;
import org.example.foodie.models.Food;
import org.example.foodie.models.Restaurant;

import java.util.List;

public class FoodsViewModel extends ViewModel {
    private MutableLiveData<List<Food>> mutableLiveData;
    private FoodRepository foodRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        foodRepository = new FoodRepository().getInstance();
        mutableLiveData = foodRepository.getFoods();
    }

    public LiveData<List<Food>> getFoodRepository() {
        return mutableLiveData;
    }
}
