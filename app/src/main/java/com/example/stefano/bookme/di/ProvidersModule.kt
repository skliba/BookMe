package com.example.stefano.bookme.di

import android.content.Context
import com.example.stefano.bookme.util.managers.StringManager
import com.example.stefano.bookme.util.managers.StringManagerImpl
import dagger.Module
import dagger.Provides

@Module
class ProvidersModule {

    @Provides
    fun provideStringManager(context: Context): StringManager = StringManagerImpl(context)
}