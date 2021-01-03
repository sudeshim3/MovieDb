package com.example.openmoviedbswiggy.di

import com.example.openmoviedbswiggy.ui.DetailActivity
import com.example.openmoviedbswiggy.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindDetailActivity(): DetailActivity
}
