package com.example.airquality.request;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AQIService {
    @GET("feed/{city}/?token=672247a023acb42220b9513ef47ab3df3b2f6ddc")
    Call<AQIData> aqiData(@Path("city") String city);
}
