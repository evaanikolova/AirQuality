package com.example.airquality.ui.search;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airquality.R;
import com.example.airquality.request.AQIData;
import com.example.airquality.request.AQIService;
import com.example.airquality.ui.AQIController;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        final Button searchButton = view.findViewById(R.id.search_button);
        final ImageButton favouriteButton = view.findViewById(R.id.favourite_image);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request goes here
                EditText city = view.findViewById(R.id.city_edit_text);

                String cityText = city.getText().toString();
                mViewModel.requestData(cityText);
            }
        });

        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favouriteButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24);

                mViewModel.saveFavouriteCity(getContext(), ((EditText)view.findViewById(R.id.city_edit_text)).getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        TextView aqiTextView = getView().findViewById(R.id.aqi_text_view);
        TextView aqiStatusTextView = getView().findViewById(R.id.aqi_status_text_view);
        TextView lastUpdatedTextView = getView().findViewById(R.id.last_updated_text_view);

        mViewModel.getCity().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String city) {
                TextView cityTextView = getView().findViewById(R.id.city_text_view);
                ImageButton favouriteCity = getView().findViewById((R.id.favourite_image));

                if (city == "no results found :(") {
                    favouriteCity.setVisibility(View.GONE);
                    aqiTextView.setVisibility(View.GONE);
                    aqiStatusTextView.setVisibility(View.GONE);
                    lastUpdatedTextView.setVisibility(View.GONE);
                } else {

                    favouriteCity.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                    favouriteCity.setVisibility(View.VISIBLE);
                }

                cityTextView.setVisibility(View.VISIBLE);
                cityTextView.setText(city);
            }
        });

        mViewModel.getAqi().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer aqi) {
                aqiTextView.setVisibility(View.VISIBLE);
                aqiTextView.setText(aqi.toString());

                AQIController.setAqiData(getContext(), aqiTextView, aqiStatusTextView);
            }
        });

        mViewModel.getDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String date) {
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setText(date);
            }
        });

        mViewModel.getAqiStatus().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String aqiStatus) {
                aqiStatusTextView.setVisibility(View.VISIBLE);
                aqiStatusTextView.setText(aqiStatus);
            }
        });
    }
}