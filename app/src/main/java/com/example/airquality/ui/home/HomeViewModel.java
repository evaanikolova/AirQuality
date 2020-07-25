package com.example.airquality.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.airquality.request.AQIData;
import com.example.airquality.request.AQIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private MutableLiveData<String> city;
    private MutableLiveData<Integer> aqi;
    private MutableLiveData<String> date;
    private MutableLiveData<String> aqiStatus;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        city = new MutableLiveData<>();
        aqi = new MutableLiveData<>();
        date = new MutableLiveData<>();
        aqiStatus = new MutableLiveData<>();
    }

    public void requestData(String cityText) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.waqi.info")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AQIService service = retrofit.create(AQIService.class);

        Call<AQIData> aqiData = service.aqiData(cityText);
        aqiData.enqueue(new Callback<AQIData>() {
            @Override
            public void onResponse(Call<AQIData> call, Response<AQIData> response) {
//                Snackbar.make(searchButton, String.valueOf(response.body().getData().getAqi()), Snackbar.LENGTH_SHORT).show();

                Log.i("onResponse", String.valueOf(response.body().getData().getAqi()));
                Log.i("onResponse", String.valueOf(response.body().getData().getCity().getName()));
                Log.i("onResponse", String.valueOf(response.body().getData().getTime().getS()));
                Log.i("onResponse", String.valueOf(response.body().getStatus()));

                city.setValue(String.valueOf(response.body().getData().getCity().getName()));
                aqi.setValue(Integer.valueOf(response.body().getData().getAqi()));
                date.setValue("Last updated on " + response.body().getData().getTime().getS());
            }

            @Override
            public void onFailure(Call<AQIData> call, Throwable t) {
//                Snackbar.make(searchButton, t.getMessage(), Snackbar.LENGTH_SHORT).show();
                city.setValue("no results found :(");
            }
        });
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<String> getCity() {
        if (city == null) {
            city = new MutableLiveData<String>();
        }

        return city;
    }

    public MutableLiveData<Integer> getAqi() {
        if (aqi == null) {
            aqi = new MutableLiveData<Integer>();
        }

        return aqi;
    }

    public MutableLiveData<String> getDate() {
        if (date == null) {
            date = new MutableLiveData<String>();
        }

        return date;
    }

    public MutableLiveData<String> getAqiStatus() {
        if (aqiStatus == null) {
            aqiStatus = new MutableLiveData<String>();
        }

        return aqiStatus;
    }
}