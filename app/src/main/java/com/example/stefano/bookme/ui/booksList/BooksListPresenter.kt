package com.example.stefano.bookme.ui.booksList

import com.example.stefano.bookme.data.interactors.BooksListInteractor
import javax.inject.Inject

class BooksListPresenter @Inject constructor(
        val interactor: BooksListInteractor
) : BooksListMvp.Presenter {

    override fun init() {

    }
}