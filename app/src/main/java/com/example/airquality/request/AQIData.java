package com.example.airquality.request;

import com.google.gson.annotations.SerializedName;

public class AQIData {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private Data data;


    public String getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }

    public class Data {
        @SerializedName("aqi")
        private int aqi;
        @SerializedName("city")
        private City city;
        @SerializedName("time")
        private Time time;

        public class City {
            @SerializedName("name")
            private String name;

            public String getName() {
                return name;
            }
        }

        public class Time {
            @SerializedName("s")
            private String s;

            public String getS() {
                return s;
            }
        }

        public int getAqi() {
            return aqi;
        }

        public City getCity() {
            return city;
        }

        public Time getTime() {
            return time;
        }
    }
}
