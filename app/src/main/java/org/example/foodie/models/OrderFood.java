package org.example.foodie.models;

import com.google.gson.annotations.SerializedName;

public class OrderFood {


    @SerializedName("name")
    public String foodName;

    @SerializedName("price")
    public int price;
    @SerializedName("foodid")
    String _id;
    @SerializedName("quantity")
    int count;


    public OrderFood(String _id, int count) {
        this._id = _id;
        this.count = count;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void addCount() {
        this.count = count + 1;
//setCount(this.count);
    }


    public String get_id() {
        return _id;
    }


}
