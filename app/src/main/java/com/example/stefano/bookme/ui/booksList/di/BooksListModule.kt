package com.example.stefano.bookme.ui.booksList.di

import com.example.stefano.bookme.data.interactors.BooksListInteractor
import com.example.stefano.bookme.data.interactors.BooksListInteractorImpl
import com.example.stefano.bookme.ui.booksList.BooksListActivity
import com.example.stefano.bookme.ui.booksList.BooksListMvp
import com.example.stefano.bookme.ui.booksList.BooksListPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class BooksListModule {

    @Binds
    abstract fun bindView(activity: BooksListActivity): BooksListMvp.View

    @Binds
    abstract fun bindPresenter(presenter: BooksListPresenter): BooksListMvp.Presenter

    @Binds
    abstract fun bindInteractor(interactor: BooksListInteractorImpl): BooksListInteractor
}
