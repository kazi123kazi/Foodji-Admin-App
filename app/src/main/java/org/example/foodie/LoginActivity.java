package org.example.foodie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.example.foodie.models.RestaurantLogIn.ResponseRestaurant;
import org.example.foodie.models.RestaurantLogIn.ResponseRestaurantUser;
import org.example.foodie.models.ResponseUser;
import org.example.foodie.models.RestaurantLogIn.RestaurantUser;
import org.example.foodie.models.User;
import org.example.foodie.org.example.foodie.apifetch.FoodieClient;
import org.example.foodie.org.example.foodie.apifetch.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static User user = new User("", "");
    private Button LoginButton;
    private EditText InputPhone, InputPassword,InputRestaurantId;
    private ProgressBar spinner;
    private TextView adminPanelLogin,notAdminPaneLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        initWidgets();
        adminPanelLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  contactNos.add(InputPhoneNumber.getText().toString()); //adding no to list as in api
                adminPanelLogin.setVisibility(View.INVISIBLE);
                notAdminPaneLogin.setVisibility(View.VISIBLE);
                InputPhone.setVisibility(View.INVISIBLE);
                InputRestaurantId.setVisibility(View.VISIBLE);

            }
        });
        notAdminPaneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  contactNos.add(InputPhoneNumber.getText().toString()); //adding no to list as in api

                notAdminPaneLogin.setVisibility(View.INVISIBLE);
                adminPanelLogin.setVisibility(View.VISIBLE);
                InputRestaurantId.setVisibility(View.INVISIBLE);
                InputPhone.setVisibility(View.VISIBLE);

            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adminPanelLogin.getVisibility()==View.VISIBLE) { //he is an ordinary user

                    CreateUser(String.valueOf(InputPhone.getText()) , String.valueOf(InputPassword.getText()));
                    //  Log.i("Credentials", user.getEmail() + " " + user.getPassword());
                    LoginUser(user);
                } else { //log in a resturant user
                    restaurantLogin();
                }
            }
        });
        if (user.getToken() != null) {
            Log.i("ok", user.getToken());
            WelcomeActvity.getInstance().finish();
        }

    }

    private void restaurantLogin() {
        FoodieClient foodieClient = ServiceGenerator.createService(FoodieClient.class);
        RestaurantUser restaurantUser=new RestaurantUser(InputRestaurantId.getText().toString(),InputPassword.getText().toString());
        Call<ResponseRestaurantUser> responseRestaurantUserCall= foodieClient.logInRestaurant(restaurantUser);
        spinner.setVisibility(View.VISIBLE);
        responseRestaurantUserCall.enqueue(new Callback<ResponseRestaurantUser>() {
            @Override
            public void onResponse(Call<ResponseRestaurantUser> call , Response<ResponseRestaurantUser> response) {
                if (response.code()==200) {
                    Toast.makeText(getApplicationContext() , "Success!" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(),RestaurantFoodAdd.class);
                    //added all restaurant to obj
                    assert response.body() != null;
                    ResponseRestaurant restaurantObj=response.body().getRestaurant();

                    intent.putExtra("token", response.body().getToken());
                    intent.putExtra("name",restaurantObj.getName());
                    intent.putExtra("restId",restaurantObj.getRest_id());
                    intent.putExtra("address",restaurantObj.getAddress());
                    startActivity(intent);


                    spinner.setVisibility(View.GONE);
                    WelcomeActvity.getInstance().finish();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseRestaurantUser> call , Throwable t) {

                spinner.setVisibility(View.GONE);
                Log.d("errorMessage",t.getMessage());
            }
        });

    }


    //Login user function
    public void LoginUser(User user) {

        FoodieClient foodieClient = ServiceGenerator.createService(FoodieClient.class);

        Call<ResponseUser> call = foodieClient.Login(user);

        spinner.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {


                //Get user logged if resposne code is 200
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                    User use = response.body().getUser();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("token", response.body().getToken());
                    intent.putExtra("name", use.getName());

                    startActivity(intent);


                    Log.i("name", use.getName());


                    //setting token value here

                    SharedPreferences sharedPreferences = getSharedPreferences("org.example.foodie", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    WelcomeActvity.token = response.body().getToken();
                    editor.putString("name", use.getName());
                    editor.putString("token", response.body().getToken());
                    editor.commit();
                    spinner.setVisibility(View.GONE);


                    WelcomeActvity.getInstance().finish();

                    finish();

                } else {

                    spinner.setVisibility(View.GONE);
                    Log.i("Response", "Invalid Credentials");
                    Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    //function to initialise all the widgets
    public void initWidgets() {
        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPhone = (EditText) findViewById(R.id.login_phone_input);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        InputRestaurantId = (EditText) findViewById(R.id.login_restaurant_id_input);
        adminPanelLogin=(TextView)findViewById(R.id.admin_panel_linkLogin);
        notAdminPaneLogin=(TextView)findViewById(R.id.not_admin_panel_linkLogn);
    }


    //function for creating user
    public void CreateUser(String phone, String password) {
        user = new User(phone, password);
    }

}
