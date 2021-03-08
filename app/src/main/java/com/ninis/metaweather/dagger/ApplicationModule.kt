package com.ninis.metaweather.dagger

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.ninis.metaweather.ThisApplication
import com.ninis.metaweather.network.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {
    @Provides
    @Singleton
    fun provideContext(application: ThisApplication): Context = application
}