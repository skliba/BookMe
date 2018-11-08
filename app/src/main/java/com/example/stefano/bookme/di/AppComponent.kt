package com.example.stefano.bookme.di

import android.content.Context
import com.example.stefano.bookme.BookApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        ApiModule::class,
        ProvidersModule::class,
        ActivityBuilder::class
))
interface AppComponent {

    fun context(): Context

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app : BookApplication)
}
