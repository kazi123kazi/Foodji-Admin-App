package org.example.foodie;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.example.foodie.models.Order;
import org.example.foodie.models.ResponseUser;
import org.example.foodie.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.CustomViewHolder> {
    private Context context;
    private List<Order> items;
    //  private ArrayList<NEWS> subjects;

    public OrdersAdapter(Context context) {
        this.context = context;
        // this.items = items;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.restaurant_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {


        //set Elements here

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setRestaurants(List<Order> orders) {
        this.items = orders;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        /*   private ImageView itemImage;
           private TextView restaurantName;
           private TextView eta,rating,description;*/
        public CustomViewHolder(View view) {
            super(view);
        /*    itemImage = view.findViewById(R.id.item_image);
            restaurantName = view.findViewById(R.id.restaurantName);
            rating=view.findViewById(R.id.rating);
            eta=view.findViewById(R.id.eta);
            description=view.findViewById(R.id.description);*/

        }
    }
}