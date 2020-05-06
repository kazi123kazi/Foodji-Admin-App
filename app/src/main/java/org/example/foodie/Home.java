package org.example.foodie;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.example.foodie.models.Food;
import org.example.foodie.models.Restaurant;
import org.example.foodie.viewmodels.RestaurantsViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Home extends Fragment {


    List<org.example.foodie.models.Restaurant> restaurantList = new ArrayList<>();

    RestaurantsViewModel restaurantsViewModel;
    public static RestaurantAdapter adapter;
    View rootView;
    List<Restaurant> restaurant = new ArrayList<>();
    ProgressBar loader;
    private  static  RecyclerView subrecview;
    public Home() {

    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        subrecview=rootView.findViewById(R.id.my_recycler_view);
        loader = rootView.findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);

        //this section here is to get the data updated everytime there is a change in data base
        restaurantsViewModel = ViewModelProviders.of(this).get(RestaurantsViewModel.class);

        restaurantsViewModel.init();


        restaurantsViewModel.getRestaurantRepository().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {


                if (restaurants != null) {
                    adapter = new RestaurantAdapter(getActivity());
                    adapter.setRestaurant(restaurants);
                    subrecview.setAdapter(adapter);
                }
                loader.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        });

        subrecview.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        //SETTING up recyclerview
        setupRecyclerView();

       /* adapter.setOnItemCLickListener(new RestaurantAdapter.OnItemCLickListener() {
            @Override
            public void OnItemClick(int position) {
                FragmentManager f_manager=getActivity().getSupportFragmentManager();
                FoodsFragment.id=restaurant.get(position).getId();
                Log.i("AFNAN",String.valueOf(restaurant.get(position).getId()));
                f_manager.beginTransaction()
                        .replace(R.id.flContent,new FoodsFragment())
                        .commit();
            }
        });
*/
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("FOODIE");
    }


    public void setupRecyclerView() {

        if (adapter == null) {
            subrecview.setLayoutManager(new GridLayoutManager(getActivity(), 1));

            subrecview.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }

    }


}
