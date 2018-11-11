package com.example.stefano.bookme.ui.booksList

import android.annotation.SuppressLint
import com.example.stefano.bookme.R
import com.example.stefano.bookme.data.interactors.BooksListInteractor
import com.example.stefano.bookme.data.models.EntityType
import com.example.stefano.bookme.util.extensions.addToCompositeDisposable
import com.example.stefano.bookme.util.extensions.applySchedulers
import com.example.stefano.bookme.util.managers.StringManager
import com.jakewharton.rxrelay2.PublishRelay
import com.paginate.Paginate
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

    private val compositeDisposable = CompositeDisposable()
    private val paginationCallbacks = object : Paginate.Callbacks {
        override fun onLoadMore() {
            onLoadMoreTriggered()
        }

        override fun isLoading() = loading
        override fun hasLoadedAllItems() = hasLoadedAllData
    }

    private var resultSubject = PublishRelay.create<String>()
    private var pageNumber = 0
    private var pageSize = 40
    private var loading = false
    private var hasLoadedAllData = true

    private lateinit var previousQuery: String

    override fun init() {
        initResultSubject()
        view.initUi()
    }

    override fun onInputTextChanged(query: String) {
        if (query.isNotEmpty()) {
            previousQuery = query
            view.showProgress()
            resultSubject.accept(query)
        }
    }

    @SuppressLint("CheckResult")
    private fun onLoadMoreTriggered() {
        loading = true
        hasLoadedAllData = false
        pageNumber += pageSize
        interactor.execute(previousQuery, pageNumber, pageSize)
                .subscribe(::handleMoreData, ::handleException)
                .addToCompositeDisposable(compositeDisposable)
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

    private fun handleResult(entityType: EntityType) {
        val books = entityType.items
        if (entityType.totalItems > pageSize) hasLoadedAllData = false
        when (books) {
            null -> view.showEmptyState()
            else -> view.displayList(books, paginationCallbacks)
        }.also { view.hideProgress() }
    }

    private fun handleMoreData(entityType: EntityType) {
        val books = entityType.items
        loading = false
        if (entityType.totalItems <= pageNumber) {
            hasLoadedAllData = true
        }

        if (books != null && books.size > 1) {
            view.addMoreItems(books)
        } else {
            //This is just here because the lib I'm using to create pagination has a bug, and this quickly fixes it.
            view.hideRecyclerViewLoading()
        }
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
        }.also { Timber.e("$throwable") }
    }
}
