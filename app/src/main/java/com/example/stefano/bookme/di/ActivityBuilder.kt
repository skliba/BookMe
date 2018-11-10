package com.example.stefano.bookme.di

import com.example.stefano.bookme.ui.bookDetails.BookDetailsActivity
import com.example.stefano.bookme.ui.bookDetails.di.BookDetailsModule
import com.example.stefano.bookme.ui.booksList.BooksListActivity
import com.example.stefano.bookme.ui.booksList.di.BooksListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [BooksListModule::class])
    abstract fun bindBooksListActivity(): BooksListActivity

    @ContributesAndroidInjector(modules = [BookDetailsModule::class])
    abstract fun bindBookDetailsActivity(): BookDetailsActivity
}