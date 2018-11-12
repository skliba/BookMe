package com.example.stefano.bookme.ui.bookDetails

import com.example.stefano.bookme.R
import com.example.stefano.bookme.data.interactors.BookDetailsInteractor
import com.example.stefano.bookme.data.models.Book
import com.example.stefano.bookme.data.models.ImageLinks
import com.example.stefano.bookme.data.models.SaleInformation
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

    private fun handleResponse(book: Book?) {
        when (book) {
            null -> view.showError(stringManager.getString(R.string.unknownError))
            else -> populateScreenWithData(book)
        }.also { view.hideProgress() }
    }

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

    private fun populateScreenWithData(book: Book) = book.apply {
        view.initUi(bookInformation.title)
        view.displayBookDescription(bookInformation.description)

        handleAuthors(bookInformation.authors)
        handleImageLinks(bookInformation.imageLinks)
        handleGeneralInformation(bookInformation.publisher,
                                 bookInformation.date,
                                 bookInformation.pageCount)
        handleSaleInformation(saleInformation)
    }

    private fun handleSaleInformation(saleInformation: SaleInformation?) {
        if (saleInformation?.listPrice != null && saleInformation.retailPrice != null && saleInformation.buyLink != null) {
            view.displayListPrice(stringManager.getString(R.string.list_price,
                                                          "${saleInformation.listPrice.amount} ${saleInformation.listPrice.currency}"))
            view.displayRetailPrice(stringManager.getString(R.string.retail_price,
                                                            "${saleInformation.retailPrice.amount} ${saleInformation.retailPrice.currency} "))
            view.displayBuyLink(stringManager.getString(R.string.purchase_link,
                                                        "\n${saleInformation.buyLink}"))
        } else {
            view.hideSaleInformation()
        }
    }

    private fun handleGeneralInformation(publisher: String?, date: String?, pageCount: Int?) {
        if (publisher != null && date != null) {
            view.displayPublisherAndDate(
                    stringManager.getString(R.string.publisher_and_date, publisher, date))
        } else {
            view.displayPublisherAndDate(stringManager.getString(R.string.no_publisher_available))
        }

        if (pageCount != null) {
            view.displayNumberOfPages(
                    stringManager.getString(R.string.page_information, pageCount.toString()))
        } else {
            view.displayNumberOfPages(stringManager.getString(R.string.no_page_numbers_available))
        }
    }

    private fun handleImageLinks(imageLinks: ImageLinks?) {
        var cover: String? = ""
        if (imageLinks != null) {
            val urlsList = listOf(
                    imageLinks.largeImage,
                    imageLinks.mediumImage,
                    imageLinks.smallImage)
            cover = urlsList.first { it != null }
        }
        view.displayBookCover(cover)
    }

    private fun handleAuthors(authors: List<String>?) {
        when (authors?.size) {
            null, 0 -> view.displayAuthors(stringManager.getString(R.string.no_known_authors))
            1       -> view.displayAuthors(authors[0])
            else    -> {
                var authorsText = ""
                authors.forEach { authorsText = "$authorsText, $it" }
                view.displayAuthors(authorsText)
            }
        }
    }

    override fun cancel() = compositeDisposable.clear()
}
