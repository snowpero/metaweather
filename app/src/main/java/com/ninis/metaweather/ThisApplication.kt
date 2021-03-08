package com.ninis.metaweather

import com.ninis.metaweather.dagger.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class ThisApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent.factory().create(this)

    override fun onCreate() {
        super.onCreate()
    }
}