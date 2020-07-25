package com.example.airquality.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.airquality.R;

public class AQIController {

    public static void setAqiData(Context c, TextView aqi, TextView aqiStatus) {

        int aqiNumber = Integer.parseInt(aqi.getText().toString());

        Log.i("aqiNumber", "aqi number " + aqiNumber);

        if (aqiNumber >= 0 && aqiNumber <= 50) {
            aqi.setBackgroundColor(ContextCompat.getColor(c, R.color.good));
            Log.i("aqiNumber", "aqi number good " + aqiNumber);
            aqiStatus.setText("Good");
            aqiStatus.setVisibility(View.VISIBLE);
        } else if (aqiNumber > 50 && aqiNumber <= 100) {
            aqi.setBackgroundColor(ContextCompat.getColor(c, R.color.moderate));
            aqiStatus.setText("Moderate");
            aqiStatus.setVisibility(View.VISIBLE);
        } else if (aqiNumber > 100 && aqiNumber <= 150) {
            aqi.setBackgroundColor(ContextCompat.getColor(c, R.color.unhealthy_one));
            aqiStatus.setText("Unhealthy for Sensitive Groups");
            aqiStatus.setVisibility(View.VISIBLE);
        } else if (aqiNumber > 151 && aqiNumber <= 200) {
            aqi.setBackgroundColor(ContextCompat.getColor(c, R.color.unhealthy_two));
            aqiStatus.setText("Unhealthy");
            aqiStatus.setVisibility(View.VISIBLE);
        } else if (aqiNumber > 200 && aqiNumber <= 300) {
            aqi.setBackgroundColor(ContextCompat.getColor(c, R.color.very_unhealthy));
            aqiStatus.setText("Very Unhealthy");
            aqiStatus.setVisibility(View.VISIBLE);
        } else if (aqiNumber > 300) {
            aqi.setBackgroundColor(ContextCompat.getColor(c, R.color.hazardous));
            aqiStatus.setText("Hazardous");
            aqiStatus.setVisibility(View.VISIBLE);
        } else {
            throw new RuntimeException("This aqi index it's not in the expected possible ranges :(");
        }
    }
}
