package org.example.foodie;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.example.foodie.models.Restaurant;

import java.util.ArrayList;
import java.util.List;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.CustomViewHolder> implements Filterable {
    private FragmentManager f_manager;
    private Context context;
    private List<Restaurant> items;
    private List<Restaurant> filteredResturants;
    private RestaurantAdapterListener listener;
    AlertDialog.Builder restAlert;

    public RestaurantAdapter(Context context) {
        this.context = context;


    }



    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.restaurant_view, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.restaurantName.setText(filteredResturants.get(position).getName());

        Log.i("Filtered: ", filteredResturants.get(0).getName());
        holder.restaurantCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FoodsActivity.id = filteredResturants.get(position).getId();
                SharedPreferences sharedPreferences = context.getSharedPreferences("org.example.foodie", Context.MODE_PRIVATE);

                CartActivity.getPrefernce(sharedPreferences);

                restAlert = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogCustom));
                Log.i("AFNAN", String.valueOf(filteredResturants.get(position).getId()));
                if (!CartActivity.cartItems.isEmpty()) {//TODO:Build an alert dialog builder
                    if (!FoodsActivity.rest_id.equals(FoodsActivity.id)) {
                      /*  Toast.makeText(context, "CART CONTAINS ITEMS FROM ANOTHER RESTAURANT",
                                Toast.LENGTH_SHORT).show();*/
                        restAlert.setTitle("Cart contains items from other restaurants");
                        restAlert.setMessage("Empty Cart to continue").setCancelable(true).setPositiveButton("Empty Cart",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        CartActivity.cartItems.clear();
                                        CartActivity.saveData(sharedPreferences);
                                        Intent intent = new Intent(context, FoodsActivity.class);
                                        FoodsActivity.rest_id = FoodsActivity.id;
                                        CartActivity.saveData(sharedPreferences);
                                        intent.putExtra("restaurant name", filteredResturants.get(position).getName());
                                        context.startActivity(intent);


                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });

                        AlertDialog alert = restAlert.create();
                        alert.show();

                    } else {
                        FoodsActivity.rest_id = FoodsActivity.id;
                        Intent i = new Intent(context, FoodsActivity.class);
                        CartActivity.saveData(sharedPreferences);
                        i.putExtra("restaurant name", filteredResturants.get(position).getName());
                        context.startActivity(i);
                    }

                } else {
                    FoodsActivity.rest_id = FoodsActivity.id;
                    Intent i = new Intent(context, FoodsActivity.class);
                    CartActivity.saveData(sharedPreferences);
                    i.putExtra("restaurant name", filteredResturants.get(position).getName());
                    context.startActivity(i);
                }

                //   f_manager.popBackStack();
            }
        });
        //holder.description.setText(items.get(position).getDescription());
        //holder.rating.setText((int) items.get(position).getRating());
      //  holder.eta.setText(items.get(position).getEta());
    /*    Glide.with(context).asBitmap().load(items.get(position).getImageUrl())
                .into(holder.itemImage);
    */
    }
    @Override
    public int getItemCount() {
        return filteredResturants.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredResturants = items;
                } else {
                    List<Restaurant> filteredList = new ArrayList<>();
                    for (Restaurant row : items) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    filteredResturants = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredResturants;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredResturants = (List<Restaurant>) filterResults.values;
                notifyDataSetChanged();
            }
        };


    }

    public void setRestaurant(List<Restaurant> restaurants) {
        this.filteredResturants = restaurants;
        this.items = restaurants;

    }

    /*public void setRestaurants(List<Restaurant> restaurantsList) {
        this.items = restaurantsList;
        notifyDataSetChanged();
    }
*/
    public interface RestaurantAdapterListener {
        void OnRestaurantSelected(Restaurant restaurant);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView restaurantName;
        private TextView eta, rating, description;
        private CardView restaurantCard;


        public CustomViewHolder(View view) {
            super(view);
            //  itemImage = view.findViewById(R.id.item_image);
            restaurantName = view.findViewById(R.id.restaurantName);
            eta = view.findViewById(R.id.eta);
            description = view.findViewById(R.id.description);
            restaurantCard = view.findViewById(R.id.restaurant);


        }
    }

}