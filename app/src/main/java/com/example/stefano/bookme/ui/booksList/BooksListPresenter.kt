package com.example.stefano.bookme.ui.booksList

import android.annotation.SuppressLint
import com.example.stefano.bookme.R
import com.example.stefano.bookme.data.interactors.BooksListInteractor
import com.example.stefano.bookme.data.models.EntityType
import com.example.stefano.bookme.util.extensions.addToCompositeDisposable
import com.example.stefano.bookme.util.extensions.applySchedulers
import com.example.stefano.bookme.util.managers.StringManager
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val DEBOUNCE_PERIOD_SECONDS = 1L

class BooksListPresenter @Inject constructor(
        private val view: BooksListMvp.View,
        private val interactor: BooksListInteractor,
        private val stringManager: StringManager
) : BooksListMvp.Presenter {

    private var resultSubject = PublishRelay.create<String>()
    private val compositeDisposable = CompositeDisposable()
    private lateinit var data: EntityType

    override fun init() {
        initResultSubject()
    }

    override fun onInputTextChanged(query: String) {
        if (query.isNotEmpty()) {
            view.showProgress()
            resultSubject.accept(query)
        }
    }

    override fun onBookClicked(bookId: String) = view.showBookDetails(bookId)
    override fun cancel() = compositeDisposable.clear()

    @SuppressLint("CheckResult")
    private fun initResultSubject() = resultSubject
            .debounce(DEBOUNCE_PERIOD_SECONDS, TimeUnit.SECONDS)
            .distinctUntilChanged()
            .switchMap { interactor.execute(it) }
            .applySchedulers()
            .subscribe(::handleResult, ::handleException)
            .addToCompositeDisposable(compositeDisposable)

    private fun handleResult(it: EntityType) {
        data = it
        val books = it.items
        when (books) {
            null -> view.showEmptyState()
            else -> view.displayList(books)
        }.also { view.hideProgress() }
    }

    private fun handleException(throwable: Throwable) {
        //We need to reinitialize the publish relay here because the observable gets completed after onError gets called.
        initResultSubject()

        val message = when (throwable) {
            is UnknownHostException -> stringManager.getString(R.string.unknownHostException)
            else                    -> throwable.takeIf { it.message != null }?.message
                    ?: stringManager.getString(R.string.unknownError)
        }

        view.apply {
            hideProgress()
            showError(message)
        }.also {
            Timber.e("$throwable")
        }
    }
}
