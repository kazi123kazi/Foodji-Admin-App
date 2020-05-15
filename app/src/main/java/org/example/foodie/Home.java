package org.example.foodie;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class Home extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static String token;
    View rootView;
    ProgressBar loader;
    private TextView restaurantName, restaurantInfo;
    private ImageView restaurantImage;
    private EditText foodName, foodprice;
    private Button addFood;
    private Button test;
    private View progressBar;


    public Home() {

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = rootView.findViewById(R.id.progressBarAddFood);
        progressBar.setVisibility(View.GONE);

        foodName = (EditText) rootView.findViewById(R.id.foodName);
        foodprice = (EditText) rootView.findViewById(R.id.foodPrice);
        addFood = (Button) rootView.findViewById(R.id.addFood);


        Intent i = getActivity().getIntent();
        token = i.getStringExtra("token");
        //Log.i("token",token);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostFoodToRestaurantList();
            }
        });


        return rootView;
    }


    private void PostFoodToRestaurantList() {
        FoodieClient foodieClient = ServiceGenerator.createService(FoodieClient.class);
        Food food = new Food(foodName.getText().toString(), foodprice.getText().toString());
        Call<Foodid> foodCall = foodieClient.postFood(token, food);
        progressBar.setVisibility(View.VISIBLE);
        foodCall.enqueue(new Callback<Foodid>() {
            @Override
            public void onResponse(Call<Foodid> call, Response<Foodid> response) {
                if (response.code() == 201) {
                    Toast.makeText(getActivity(), response.body().getName() + " added Successfully.", Toast.LENGTH_SHORT).show();
                    foodName.setText("");
                    foodprice.setText("");
                    addFood.setText("Add More To Food List");

                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Foodid> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                  //  update.setVisibility(View.VISIBLE);
                    MultipartBody.Part body = ProcessImage(bitmap);
                    postImage(body);


                    //   viewImage.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                //   Log.i("path",filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.deactivate();

                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.i("path of image:", picturePath + "");

                // update.setVisibility(View.VISIBLE);
                MultipartBody.Part body = ProcessImage(thumbnail);
                postImage(body);

            }
        }


    }


    public void postImage(MultipartBody.Part image) {
        FoodieClient foodieClient = ServiceGenerator.createService(FoodieClient.class);


        Call<ResponseBody> call = foodieClient.postImage(MainActivity.token, image);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.i("Response:", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Response", t.getMessage());
            }
        });
    }




    public  MultipartBody.Part ProcessImage(Bitmap thumbnail){

        //create a file to write bitmap data
        File f = new File(getActivity().getCacheDir(),"fos.jpg");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

//Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 0,/*0 ignored for PNG,*/ bos);
        byte[] bitmapdata = bos.toByteArray();

        Log.i("START","STARTED");
//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("FINISHED","FINISHED");

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", f.getName(), reqFile);


        Log.i("TOKEN FROM HOME:",MainActivity.token);

        return body;

    }



}
