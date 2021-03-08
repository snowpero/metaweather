package com.ninis.metaweather.data

import android.annotation.SuppressLint
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ninis.metaweather.R
import com.ninis.metaweather.ThisApplication
import java.util.*
import kotlin.collections.ArrayList

data class ConsolidatedWeather(
    @SerializedName("consolidated_weather")
    @Expose
    val consolidatedWeather: ArrayList<ConsolidatedWeatherItem>,
    @SerializedName("time")
    @Expose
    val time: Date,
    @SerializedName("sun_rise")
    @Expose
    val sunRise: Date,
    @SerializedName("sun_set")
    @Expose
    val sunSet: Date,
    @SerializedName("timezone_name")
    @Expose
    val timeZoneName: String,
    @SerializedName("parent")
    @Expose
    val parent: WeatherItem,
    @SerializedName("sources")
    @Expose
    val sources: ArrayList<SourceItem>
) : WeatherItem()

data class ConsolidatedWeatherItem(
    @SerializedName("id")
    @Expose
    val id: Double,
    @SerializedName("weather_state_name")
    @Expose
    val stateName: String,
    @SerializedName("weather_state_abbr")
    @Expose
    val stateAbbr: String,
    @SerializedName("wind_direction_compass")
    @Expose
    val directionCompass: String,
    @SerializedName("created")
    @Expose
    val createData: Date,
    @SerializedName("applicable_date")
    @Expose
    val applicableData: Date,
    @SerializedName("min_temp")
    @Expose
    val minTemp: Float,
    @SerializedName("max_temp")
    @Expose
    val maxTemp: Float,
    @SerializedName("the_temp")
    @Expose
    val theTemp: Float,
    @SerializedName("wind_speed")
    @Expose
    val windSpeed: Float,
    @SerializedName("wind_direction")
    @Expose
    val windDirection: Float,
    @SerializedName("air_pressure")
    @Expose
    val airPressure: Float,
    @SerializedName("humidity")
    @Expose
    val humidity: Int,
    @SerializedName("visibility")
    @Expose
    val visibility: Float,
    @SerializedName("predictability")
    @Expose
    val predictability: Int
) {
    fun getIcon(): Int? {
        when(stateAbbr) {
            "sn" -> {
                return R.drawable.ic_sn
            }
            "sl" -> {
                return R.drawable.ic_sl
            }
            "h" -> {
                return R.drawable.ic_h
            }
            "t" -> {
                return R.drawable.ic_t
            }
            "hr" -> {
                return R.drawable.ic_hr
            }
            "lr" -> {
                return R.drawable.ic_lr
            }
            "s" -> {
                return R.drawable.ic_s
            }
            "hc" -> {
                return R.drawable.ic_hc
            }
            "lc" -> {
                return R.drawable.ic_lc
            }
            "c" -> {
                return R.drawable.ic_c
            }
            else -> {
                return null
            }
        }
    }

    fun getTempFormatWithValue(): String {
        return String.format(ThisApplication.gResources.getString(R.string.format_temp), theTemp)
    }

    fun getHumidityWithValue(): String {
        return String.format(ThisApplication.gResources.getString(R.string.format_humidity), humidity)
    }
}

data class SourceItem(
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("slug")
    @Expose
    val slug: String,
    @SerializedName("url")
    @Expose
    val url: String,
    @SerializedName("crawl_rate")
    @Expose
    val crawlRate: Int
)
