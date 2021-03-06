package com.ninis.metaweather

import android.content.res.Resources
import com.ninis.metaweather.dagger.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class ThisApplication : DaggerApplication() {
    companion object {
        lateinit var gResources: Resources
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent.factory().create(this)

    override fun onCreate() {
        super.onCreate()

        gResources = resources
    }
}