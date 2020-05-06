package org.example.foodie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ResturantHome extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_resturant_home, container, false);

        //!IMPORTANT use rootView to get views by id eg Button button=rootView.findViewById(R.id.button.


        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
