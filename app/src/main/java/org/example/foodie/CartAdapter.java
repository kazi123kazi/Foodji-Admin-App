package org.example.foodie;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import org.example.foodie.models.Food;
import org.example.foodie.models.Foodid;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CustomViewHolder> {

    public static int pos;

    List<Food> items;
    private OnItemCLickListener mOnItemCLickListener;
    private FragmentManager f_manager;
    private Context context;

    public CartAdapter(Context context) {
        this.context = context;

        // this.items = items;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartAdapter.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_list, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("org.example.foodie", Context.MODE_PRIVATE);
        int count = items.get(position).getCount();
        holder.itemName.setText(items.get(position).getFoodid().getName());
        holder.itemQuantity.setText(String.valueOf(count));
        holder.itemPrice.setText(String.valueOf(Integer.parseInt(items.get(position).getPrice()) * count));

        Food currItem = items.get(position);
        isPresent(CartActivity.cartItems, currItem.getFoodid().getName());


        holder.adddFood.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Food currItem = items.get(position);
                if (isPresent(CartActivity.cartItems, currItem.getFoodid().getName())) {

                    CartActivity.cartItems.get(pos).addCount();
                    //items.get(position).addCount();
                }

                updateTotal();

                CartActivity.saveData(sharedPreferences);
                holder.itemQuantity.setText(String.valueOf(items.get(position).getCount()));
                holder.itemPrice.setText(String.valueOf(Integer.parseInt(items.get(position).getPrice()) * items.get(position).getCount()));

                return false;
            }
        });





        holder.adddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Food currItem = items.get(position);
                if (isPresent(CartActivity.cartItems, currItem.getFoodid().getName())) {

                    CartActivity.cartItems.get(pos).addCount();
                    //items.get(position).addCount();
                }

                updateTotal();

                CartActivity.saveData(sharedPreferences);
                holder.itemQuantity.setText(String.valueOf(items.get(position).getCount()));
                holder.itemPrice.setText(String.valueOf(Integer.parseInt(items.get(position).getPrice()) * items.get(position).getCount()));
                return;
            }

        });


        holder.removeFood.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Food currItem = items.get(position);
                if (isPresent(CartActivity.cartItems, currItem.getFoodid().getName())) {

                    CartActivity.cartItems.get(pos).decreaseCount();
                    //items.get(position).addCount();
                    if (CartActivity.cartItems.get(pos).getCount() <= 0) {
                        CartActivity.cartItems.remove(CartActivity.cartItems.get(pos));
                        CartActivity.saveData(sharedPreferences);
                        updateTotal();
                        if (CartActivity.cartItems.isEmpty()) {
                            CartActivity.cartView.setVisibility(View.GONE);
                            CartActivity.emptyCart.setVisibility(View.VISIBLE);

                        }


                        notifyDataSetChanged();
                        Log.i("working fine: ", "yes");
                        return false;
                    }


                    updateTotal();

                    CartActivity.saveData(sharedPreferences);
                    holder.itemQuantity.setText(String.valueOf(items.get(position).getCount()));
                    holder.itemPrice.setText(String.valueOf(Integer.parseInt(items.get(position).getPrice()) * items.get(position).getCount()));

                    return true;
                }


                return false;
            }
        });

        holder.removeFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food currItem = items.get(position);
                if (isPresent(CartActivity.cartItems, currItem.getFoodid().getName())) {

                    CartActivity.cartItems.get(pos).decreaseCount();
                    //items.get(position).addCount();
                    if (CartActivity.cartItems.get(pos).getCount() <= 0) {
                        CartActivity.cartItems.remove(CartActivity.cartItems.get(pos));
                        CartActivity.saveData(sharedPreferences);
                        updateTotal();
                        if (CartActivity.cartItems.isEmpty()) {
                            CartActivity.cartView.setVisibility(View.GONE);
                            CartActivity.emptyCart.setVisibility(View.VISIBLE);

                        }


                        notifyDataSetChanged();
                        Log.i("working fine: ", "yes");
                        return;
                    }
                }

                updateTotal();

                CartActivity.saveData(sharedPreferences);
                holder.itemQuantity.setText(String.valueOf(items.get(position).getCount()));
                holder.itemPrice.setText(String.valueOf(Integer.parseInt(items.get(position).getPrice()) * items.get(position).getCount()));


            }
        });


        holder.removeFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartActivity.cartItems.remove(CartActivity.cartItems.get(pos));
                CartActivity.saveData(sharedPreferences);
                updateTotal();
                if (CartActivity.cartItems.isEmpty()) {
                    CartActivity.cartView.setVisibility(View.GONE);
                    CartActivity.emptyCart.setVisibility(View.VISIBLE);

                }
                notifyDataSetChanged();
                return;

            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setFood(List<Food> foods) {
        this.items = foods;
        notifyDataSetChanged();
    }

    public Boolean isPresent(List<Food> foods, String name) {

        for (int i = 0; i < foods.size(); i++) {
            if (name.equals(foods.get(i).getFoodid().getName())) {
                pos = i;
                return true;
            }
        }
        return false;
    }

    public void updateTotal() {
        int total = 0;
        for (int i = 0; i < CartActivity.cartItems.size(); i++) {
            total = total + Integer.parseInt(CartActivity.cartItems.get(i).getPrice()) * CartActivity.cartItems.get(i).getCount();
        }

        CartActivity.totalPrice.setText(String.valueOf(total));
    }


    public interface OnItemCLickListener {
        void OnItemClick(int position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName;
        private TextView itemPrice;
        private ImageView removeFromCart;
        private TextView itemQuantity;
        private ImageView adddFood;
        private ImageView removeFood;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            removeFromCart = itemView.findViewById(R.id.removeFromCart);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            adddFood = itemView.findViewById(R.id.addItem);
            removeFood = itemView.findViewById(R.id.removeItem);

        }
    }


}
