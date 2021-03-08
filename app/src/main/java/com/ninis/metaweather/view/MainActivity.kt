package com.ninis.metaweather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ninis.metaweather.R
import com.ninis.metaweather.network.MainRepository
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

//    @Inject
//    lateinit var retrofitClient: Retrofit
//
//    val viewModel: MainViewModel by lazy {
//        MainViewModel(MainRepository(retrofitClient))
//    }

    @Inject
    lateinit var androidInjector : DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}