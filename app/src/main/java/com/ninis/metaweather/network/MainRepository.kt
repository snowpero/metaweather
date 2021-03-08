package com.ninis.metaweather.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class MainRepository(
    retrofit: Retrofit
) {
    private val service by lazy { retrofit.create(ApiService::class.java) }

    interface ApiService {
        @GET("/api/location/search/")
        fun getSearch(@Query("query") query: String)

        @GET("/api/location/{woeid}/")
        fun getDetailWeather(@Path("woeid") woeid: Int)
    }

    data class WeatherItem(
        @SerializedName("title")
        @Expose
        val title: String,
        @SerializedName("location_type")
        @Expose
        val locationType: String,
        @SerializedName("woeid")
        @Expose
        val woeid: Int,
        @SerializedName("latt_long")
        @Expose
        val latLng: String
    )
}