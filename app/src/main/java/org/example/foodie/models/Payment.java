package org.example.foodie.models;

import com.google.gson.annotations.SerializedName;

public class Payment {

    @SerializedName("method")
    String method;

    @SerializedName("status")
    String status;

    @SerializedName("total")
    int total;

    public Payment(String method, String status) {
        this.method = method;
        this.status = status;

    }
}
