package com.ninis.metaweather.dagger

import com.ninis.metaweather.ThisApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        NetworkModule::class,
        ActivityBuilder::class
    ]
)

interface ApplicationComponent : AndroidInjector<ThisApplication> {
    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<ThisApplication>
}