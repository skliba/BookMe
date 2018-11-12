package com.example.stefano.bookme.ui.bookDetails.di

import com.example.stefano.bookme.data.interactors.BookDetailsInteractor
import com.example.stefano.bookme.data.interactors.BookDetailsInteractorImpl
import com.example.stefano.bookme.ui.bookDetails.BookDetailsActivity
import com.example.stefano.bookme.ui.bookDetails.BookDetailsMvp
import com.example.stefano.bookme.ui.bookDetails.BookDetailsPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class BookDetailsModule {

    @Binds
    abstract fun bindView(activity: BookDetailsActivity): BookDetailsMvp.View

    @Binds
    abstract fun bindPresenter(presenter: BookDetailsPresenter): BookDetailsMvp.Presenter

    @Binds
    abstract fun bindInteractor(interactor: BookDetailsInteractorImpl): BookDetailsInteractor
}