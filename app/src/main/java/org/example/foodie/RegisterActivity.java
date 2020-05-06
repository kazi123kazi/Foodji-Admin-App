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

import org.example.foodie.models.ResponseUser;
import org.example.foodie.models.RestaurantCreate.RestaurantCreate;
import org.example.foodie.models.RestaurantCreate.RestaurantUser;
import org.example.foodie.models.RestaurantCreate.SuperAdminUser;
import org.example.foodie.models.User;
import org.example.foodie.org.example.foodie.apifetch.FoodieClient;
import org.example.foodie.org.example.foodie.apifetch.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    public static User user = new User("", "");
    public Button CreateAccountButton;

    private ProgressBar progressBar;
    public EditText InputName, InputPhoneNumber, InputPassword, InputAddress, InputEmail,RestaurantIdInput;
    public TextView adminPanelRegister,notadminPanelRegister;
    final static String username="admin";
    final static  String password="password";
    SuperAdminUser superAdminUser;
    RestaurantUser restaurantUser;
    RestaurantCreate restaurantCreate;
    List<String> contactNos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);
        initWidgets();

        //  Log.i("Response", String.valueOf(user.getPhone()));

        adminPanelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  contactNos.add(InputPhoneNumber.getText().toString()); //adding no to list as in api
                adminPanelRegister.setVisibility(View.INVISIBLE);
                notadminPanelRegister.setVisibility(View.VISIBLE);
                InputEmail.setVisibility(View.INVISIBLE);
                RestaurantIdInput.setVisibility(View.VISIBLE);

            }
        });
        notadminPanelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  contactNos.add(InputPhoneNumber.getText().toString()); //adding no to list as in api

                notadminPanelRegister.setVisibility(View.INVISIBLE);
                adminPanelRegister.setVisibility(View.VISIBLE);
                InputEmail.setVisibility(View.VISIBLE);
                RestaurantIdInput.setVisibility(View.INVISIBLE);

            }
        });

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  /*            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);*/
             if (adminPanelRegister.getVisibility()==View.VISIBLE) { //i.e. anew normal user is creating a ac
                 CreateUser(InputName.getText().toString(), InputEmail.getText().toString(), InputPassword.getText().toString(), InputAddress.getText().toString(), "+91" + InputPhoneNumber.getText().toString());
                 RegisterUser(user);
             } else {  //for a restaturant user
                  CreateRestaturantUser();
               }
            }
        });
        if (user.getToken() != null) {
            Log.i("ok", user.getToken());
            WelcomeActvity.getInstance().finish();
        }


    }

    public void RegisterUser(User user) {

        FoodieClient foodieClient = ServiceGenerator.createService(FoodieClient.class);
        Call<ResponseUser> call = foodieClient.Register(user);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {


                //Log.i("Response", String.valueOf(response.body().getToken()));
                if (response.code() == 201) {
                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                    User use = response.body().getUser();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    intent.putExtra("token", response.body().getToken());
                    intent.putExtra("name", use.getName());
                    WelcomeActvity.token = response.body().getToken();

                    SharedPreferences sharedPreferences = getSharedPreferences("org.example.foodie", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", use.getName());
                    editor.putString("token", response.body().getToken());
                    editor.commit();
                    progressBar.setVisibility(View.GONE);
                    finish();
                    WelcomeActvity.getInstance().finish();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void initWidgets() {

        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputEmail = (EditText) findViewById(R.id.register_email_input);
        InputAddress = (EditText) findViewById(R.id.register_address_input);
        adminPanelRegister=(TextView)findViewById(R.id.admin_panel_linkRegister);
        notadminPanelRegister=(TextView)findViewById(R.id.not_admin_panel_linkRegister);
        RestaurantIdInput=(EditText)findViewById(R.id.register_restaurantId_input);


    }


    public void CreateUser(String name, String email, String password, String address, String phone) {
        user = new User(name, email, password, address, phone);
    }

   public  void CreateRestaturantUser()
   {

       FoodieClient foodieClient = ServiceGenerator.createService(FoodieClient.class);
       superAdminUser=new SuperAdminUser(username,password);
       restaurantUser=new RestaurantUser(InputName.getText().toString(),
               RestaurantIdInput.getText().toString(),
               InputAddress.getText().toString(),
               InputPassword.getText().toString(),
               contactNos);
       RestaurantCreate restaurantCreate=new RestaurantCreate(superAdminUser,restaurantUser);
       Call<ResponseUser> call2=foodieClient.createRestaurant(restaurantCreate);//just post::Response class for this should be made;
       progressBar.setVisibility(View.VISIBLE);
       call2.enqueue(new Callback<ResponseUser>() {
           @Override
           public void onResponse(Call<ResponseUser> call , Response<ResponseUser> response) {


               if (response.code() == 201) {
                   Toast.makeText(getApplicationContext() , "Success!" , Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(RegisterActivity.this, RestaurantFoodAdd.class);
                   startActivity(intent);
                   intent.putExtra("token", response.body().getToken());//for further functionality need this token id
                   intent.putExtra("name",InputName.getText().toString());
                   intent.putExtra("restId",RestaurantIdInput.getText().toString());
                   intent.putExtra("address",InputAddress.getText().toString());
                   progressBar.setVisibility(View.GONE);
                   WelcomeActvity.getInstance().finish();
               } else {
                   Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<ResponseUser> call , Throwable t) {
               Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });

   }
}
