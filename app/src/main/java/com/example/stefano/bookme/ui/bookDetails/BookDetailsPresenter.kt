package com.example.stefano.bookme.ui.bookDetails

import com.example.stefano.bookme.R
import com.example.stefano.bookme.data.interactors.BookDetailsInteractor
import com.example.stefano.bookme.data.models.Book
import com.example.stefano.bookme.util.extensions.addToCompositeDisposable
import com.example.stefano.bookme.util.managers.StringManager
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class BookDetailsPresenter @Inject constructor(
        private val view: BookDetailsMvp.View,
        private val interactor: BookDetailsInteractor,
        private val stringManager: StringManager
) : BookDetailsMvp.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun init(bookId: String) {
        interactor.execute(bookId)
                .also { view.showProgress() }
                .subscribe(::handleResponse, ::handleException)
                .addToCompositeDisposable(compositeDisposable)
    }

    private fun handleResponse(book: Book?) = when (book) {
        null -> view.showError(stringManager.getString(R.string.unknownError))
        else -> view.initUi(book)
    }.also { view.hideProgress() }


    private fun handleException(throwable: Throwable) {
        val message = when (throwable) {
            is UnknownHostException -> stringManager.getString(R.string.unknownHostException)
            else                    -> throwable
                    .takeIf { it.message != null }?.message
                    ?: stringManager.getString(R.string.unknownError)
        }

        view.apply {
            hideProgress()
            showError(message)
        }.also {
            Timber.e("$throwable")
        }
    }

    override fun cancel() {
        compositeDisposable.clear()
    }
}
