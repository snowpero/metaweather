package com.ninis.metaweather.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ninis.metaweather.data.ConsolidatedWeather
import com.ninis.metaweather.data.WeatherItem
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*
import kotlin.collections.ArrayList

class MainRepository(
    retrofit: Retrofit
) {
    private val service by lazy { retrofit.create(ApiService::class.java) }

    fun getSearch(query: String): Single<Response<ArrayList<WeatherItem>>> {
        return service.getSearch(query)
    }

    fun getDetailWeather(woeid: Int): Single<Response<ConsolidatedWeather>> {
        return service.getDetailWeather(woeid)
    }

    interface ApiService {
        @GET("/api/location/search/")
        fun getSearch(@Query("query") query: String): Single<Response<ArrayList<WeatherItem>>>

        @GET("/api/location/{woeid}/")
        fun getDetailWeather(@Path("woeid") woeid: Int): Single<Response<ConsolidatedWeather>>
    }
}