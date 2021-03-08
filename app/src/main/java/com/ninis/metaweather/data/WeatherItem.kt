package com.ninis.metaweather.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class WeatherItem(
    @SerializedName("title")
    @Expose
    val title: String = "",
    @SerializedName("location_type")
    @Expose
    val locationType: String = "",
    @SerializedName("woeid")
    @Expose
    val woeid: Int = 0,
    @SerializedName("latt_long")
    @Expose
    val latLng: String = "",
    val dailyWeathers: ArrayList<ConsolidatedWeatherItem> = ArrayList()
) {
    fun setDailyWeathers(detail: ConsolidatedWeather) {
        if( dailyWeathers.isNotEmpty() )
            dailyWeathers.clear()

        dailyWeathers.addAll(detail.consolidatedWeather)
    }

    fun getTodayWeather(): ConsolidatedWeatherItem? {
        return if( dailyWeathers.isEmpty() ) null else dailyWeathers[0]
    }

    fun getTomorrowWeather(): ConsolidatedWeatherItem? {
        return if( dailyWeathers.isEmpty() ) null else dailyWeathers[1]
    }
}
