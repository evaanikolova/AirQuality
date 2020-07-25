package com.example.airquality.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.airquality.R;
import com.example.airquality.ui.AQIController;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private SharedPreferences sharedPreferences;
    private String spFile = "com.example.airquality";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        Log.i("onCreateView", "syzdade se view-to");

        sharedPreferences = getContext().getSharedPreferences(spFile, Context.MODE_PRIVATE);

        String city = sharedPreferences.getString("favouriteCity", "");

        homeViewModel.requestData(city);

        root.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        root.findViewById(R.id.progress_bar_text_view).setVisibility(View.VISIBLE);

        TextView aqiStatusTextView = root.findViewById(R.id.fav_aqi_status_text_view);

        homeViewModel.getCity().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String city) {
                TextView cityTextView = getView().findViewById(R.id.fav_city_text_view);
                cityTextView.setText(city);

                getView().findViewById(R.id.progress_bar).setVisibility(View.GONE);
                getView().findViewById(R.id.progress_bar_text_view).setVisibility(View.GONE);

                getView().findViewById(R.id.fav_city_text_view).setVisibility(View.VISIBLE);
            }
        });

        homeViewModel.getAqi().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer aqi) {
                TextView aqiTextView = getView().findViewById(R.id.fav_aqi_text_view);
                
                aqiTextView.setText(aqi.toString());

                getView().findViewById(R.id.fav_aqi_text_view).setVisibility(View.VISIBLE);

                AQIController.setAqiData(getContext(), aqiTextView, aqiStatusTextView);
            }
        });

        homeViewModel.getDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String date) {
                TextView lastUpdatedTextView = getView().findViewById(R.id.fav_last_updated_text_view);
                lastUpdatedTextView.setText(date);

                getView().findViewById(R.id.fav_last_updated_text_view).setVisibility(View.VISIBLE);
            }
        });

        homeViewModel.getAqiStatus().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String aqiStatus) {
                aqiStatusTextView.setText(aqiStatus);

                getView().findViewById(R.id.fav_aqi_status_text_view).setVisibility(View.VISIBLE);
            }
        });



        return root;
    }
}