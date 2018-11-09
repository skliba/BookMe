package com.example.stefano.bookme.ui.booksList

import android.annotation.SuppressLint
import com.example.stefano.bookme.data.interactors.BooksListInteractor
import com.example.stefano.bookme.util.extensions.applySchedulers
import com.jakewharton.rxrelay2.PublishRelay
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BooksListPresenter @Inject constructor(
        private val view: BooksListMvp.View,
        private val interactor: BooksListInteractor
) : BooksListMvp.Presenter {

    private val resultSubject = PublishRelay.create<String>()

    override fun init() {
        initResultSubject()
    }

    @SuppressLint("CheckResult")
    private fun initResultSubject() {
        resultSubject
                .debounce(1, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .switchMap { interactor.execute(it) }
                .applySchedulers()
                .subscribe({
                               view.hideProgress()
                               if (it.items != null) {
                                   view.displayList(it.items)
                               } else {
                                   view.showEmptyState()
                               }
                           },
                           {
                               Timber.e("$it")
                           })

    }

    override fun onInputTextChanged(query: String) {
        if (query.isNotEmpty()) {
            view.showProgress()
            resultSubject.accept(query)
        }
    }
}