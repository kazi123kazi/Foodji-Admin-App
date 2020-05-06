package org.example.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.example.foodie.viewmodels.OrdersViewModel;

public class OrdersActivity extends AppCompatActivity {

    OrdersViewModel ordersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);


    }
}
