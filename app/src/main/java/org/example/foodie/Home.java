package org.example.foodie;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.example.foodie.models.Food;
import org.example.foodie.models.Foodid;
import org.example.foodie.org.example.foodie.apifetch.FoodieClient;
import org.example.foodie.org.example.foodie.apifetch.ServiceGenerator;
import org.example.foodie.viewmodels.RestaurantsViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private TextView restaurantName, restaurantInfo;
    private ImageView restaurantImage;
    private EditText foodName,foodprice;
    private Button addFood;
    View rootView;
    public static String token;
    ProgressBar loader;
    private  Button test;
    private View progressBar;


    public Home() {

    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar=rootView.findViewById(R.id.progressBarAddFood);
        progressBar.setVisibility(View.GONE);

        foodName=(EditText)rootView.findViewById(R.id.foodName);
        foodprice=(EditText)rootView.findViewById(R.id.foodPrice);
        addFood=(Button)rootView.findViewById(R.id.addFood);


       Intent i=getActivity().getIntent();
       token=i.getStringExtra("token");
       //Log.i("token",token);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostFoodToRestaurantList();
            }
        });


       return rootView;
    }



    private void PostFoodToRestaurantList()
        {
            FoodieClient foodieClient = ServiceGenerator.createService(FoodieClient.class);
            Food food=new Food(foodName.getText().toString(),foodprice.getText().toString());
            Call<Foodid> foodCall=foodieClient.postFood(token,food);
            progressBar.setVisibility(View.VISIBLE);
            foodCall.enqueue(new Callback<Foodid>() {
                @Override
                public void onResponse(Call<Foodid> call , Response<Foodid> response) {
                    if(response.code()==201){
                        Toast.makeText(getActivity(),response.body().getName() +" added Successfully.", Toast.LENGTH_SHORT).show();
                        foodName.setText("");
                        foodprice.setText("");
                        addFood.setText("Add More To Food List");
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<Foodid> call , Throwable t) {
                    Toast.makeText(getActivity(),t.getMessage() , Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("FOODJI ADMIN");
    }



}
