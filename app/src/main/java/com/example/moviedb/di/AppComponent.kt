package com.example.moviedb.di

import android.app.Application
import com.example.moviedb.App
import com.example.moviedb.di.modules.NetworkModule
import com.example.moviedb.di.modules.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        AppModule::class,
        ViewModelFactoryModule::class,
        ActivityBuilderModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
