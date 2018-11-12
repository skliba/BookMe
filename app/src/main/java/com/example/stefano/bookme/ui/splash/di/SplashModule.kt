package com.example.stefano.bookme.ui.splash.di

import com.example.stefano.bookme.ui.splash.SplashActivity
import com.example.stefano.bookme.ui.splash.SplashMvp
import com.example.stefano.bookme.ui.splash.SplashPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class SplashModule {

    @Binds
    abstract fun bindView(activity: SplashActivity): SplashMvp.View

    @Binds
    abstract fun bindPresenter(presenter: SplashPresenter): SplashMvp.Presenter
}