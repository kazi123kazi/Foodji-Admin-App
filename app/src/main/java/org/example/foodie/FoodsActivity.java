package org.example.foodie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.example.foodie.models.Food;
import org.example.foodie.models.Restaurant;
import org.example.foodie.viewmodels.FoodsViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FoodsActivity extends AppCompatActivity {


    //public static List<Food> foods=new ArrayList<>();

    static String rest_id = null;
    public static String id = null;
    private static FoodAdapter adapter;
    private static RecyclerView recyclerView;
    private Toolbar toolbar;
    View rootView;
    FoodsViewModel foodsViewModel;
    private FoodsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);


        recyclerView = findViewById(R.id.food_recycler_view);
        mViewModel = ViewModelProviders.of(FoodsActivity.this).get(FoodsViewModel.class);
        //  foodsViewModel=ViewModelProviders.of(this).get(FoodsViewModel.class);

        mViewModel.init();

        mViewModel.getFoodRepository().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                if (foods != null) {
                    //List<Food> list=new ArrayList<>();

                    //Collections.copy(foods,restaurant.getFoods());
                    adapter = new FoodAdapter(FoodsActivity.this);
                    adapter.setFood(foods);

                    recyclerView.setLayoutManager(new GridLayoutManager(FoodsActivity.this, 1));
                    recyclerView.setAdapter(adapter);

                    Log.i("foods", String.valueOf("hi"));


                }


//                Log.i("foodiee", String.valueOf(restaurant.getFoods()));
                adapter.notifyDataSetChanged();

            }
        });
        //Log.i("foods", String.valueOf("hi"));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(getIntent().getStringExtra("restaurant name"));




        setupRecyclerView();
    }

    public void setupRecyclerView() {
        //      Log.i("foods", String.valueOf("hi"));

        if (adapter == null) {
            //   adapter.setFood(foods);
            recyclerView.setLayoutManager(new GridLayoutManager(FoodsActivity.this, 1));
            recyclerView.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}





