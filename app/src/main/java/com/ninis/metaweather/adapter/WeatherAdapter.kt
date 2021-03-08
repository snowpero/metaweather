package com.ninis.metaweather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ninis.metaweather.R
import com.ninis.metaweather.data.WeatherItem
import com.ninis.metaweather.databinding.VhWeatherItemBinding

class WeatherAdapter: RecyclerView.Adapter<WeatherAdapter.ItemViewHolder>() {

    private val weatherItems = ArrayList<WeatherItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: VhWeatherItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.vh_weather_item, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBindViewHolder(weatherItems[position])
    }

    override fun getItemCount(): Int {
        return weatherItems.size
    }

    fun setItems(items: ArrayList<WeatherItem>) {
        if( weatherItems.isNotEmpty() )
            weatherItems.clear()

        weatherItems.addAll(items)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: VhWeatherItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBindViewHolder(model: WeatherItem) {
            binding.itemData = model

            model.getTodayWeather()?.let {
                Glide.with(itemView.context)
                    .load(it.getIcon()).into(binding.ivWeatherItemTodayIcon)
            }
            model.getTomorrowWeather()?.let {
                Glide.with(itemView.context)
                    .load(it.getIcon()).into(binding.ivWeatherItemTomorrowIcon)
            }

            binding.executePendingBindings()
        }
    }
}