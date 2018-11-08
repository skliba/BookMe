package com.example.stefano.bookme.di

import com.example.stefano.bookme.ui.booksList.BooksListActivity
import com.example.stefano.bookme.ui.booksList.di.BooksListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(
            BooksListModule::class
    ))
    abstract fun bindBooksListActivity(): BooksListActivity
}