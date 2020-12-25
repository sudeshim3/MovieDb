package com.example.openmoviedbswiggy

import com.example.openmoviedbswiggy.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerAppComponent
            .builder()
            .application(this).build()
        component.inject(this)
        return component
    }
}
