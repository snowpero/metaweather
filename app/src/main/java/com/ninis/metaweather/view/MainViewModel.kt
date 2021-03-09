package com.ninis.metaweather.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ninis.metaweather.base.BaseViewModel
import com.ninis.metaweather.data.ConsolidatedWeather
import com.ninis.metaweather.data.ConsolidatedWeatherItem
import com.ninis.metaweather.data.WeatherItem
import com.ninis.metaweather.event.SingleLiveEvent
import com.ninis.metaweather.network.MainRepository
import com.ninis.metaweather.notifyObserver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.operators.flowable.FlowableToListSingle
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainViewModel(
    private val repository: MainRepository
) : BaseViewModel() {

    val weathers = MutableLiveData<ArrayList<WeatherItem>>(ArrayList())
    val detailWeathersMap = MutableLiveData<HashMap<Int, ConsolidatedWeather>>(HashMap())

    val detailLoadingCount = MutableLiveData(0)

    val loadComplete = SingleLiveEvent<Any>()

    val isLoading = MutableLiveData(false)

    fun initData() {
        weathers.value?.apply { clear() }
        detailWeathersMap.value?.apply { clear() }
        detailLoadingCount.value = 0
    }

    fun getSearch() {
        isLoading.value = true

        repository.getSearch("se")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<ArrayList<WeatherItem>>> {
                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onSuccess(t: Response<ArrayList<WeatherItem>>?) {
                    t?.body()?.let {
                        initData()

                        weathers.value = it

                        getAllDetailData(it)
                    }
                }

                override fun onError(e: Throwable?) {
                    isLoading.value = false
                }
            })
    }

    fun getDetailWeather(woeid: Int = 646099) {
        repository.getDetailWeather(woeid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<ConsolidatedWeather>> {
                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onSuccess(t: Response<ConsolidatedWeather>?) {
                    t?.body()?.let {
                        detailWeathersMap.value?.let { map ->
                            map.put(it.woeid, it)

                            detailLoadingCount.value = detailLoadingCount.value!! + 1
                        }
                    }
                }

                override fun onError(e: Throwable?) {
                    isLoading.value = false
                }

            })
    }

    fun getAllDetailData(list: ArrayList<WeatherItem>) {
        disposable.add(Flowable.fromIterable(list.toList())
            .flatMap {
                return@flatMap repository.getDetailWeather(it.woeid).subscribeOn(Schedulers.io()).toFlowable()
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.body()?.let { detail ->
                    detailWeathersMap.value?.let { map ->
                        map.put(detail.woeid, detail)
                    }
                }
            },
                {
                    isLoading.value = false
                }, {
                    setListData()
                    isLoading.value = false
                })
        )
    }

    fun checkDetailLoadingComplete() {
        if (detailLoadingCount.value!! == weathers.value?.size) {
            loadComplete.call()
            isLoading.value = false
        }
    }

    fun setListData() {
        weathers.value?.let {
            for (weather in it) {
                detailWeathersMap.value?.let { detail ->
                    if (detail.containsKey(weather.woeid)) {
                        weather.setDailyWeathers(detail.getValue(weather.woeid))
                    }
                }
            }

            weathers.notifyObserver()
        }
    }
}