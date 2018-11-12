package com.example.stefano.bookme.ui.bookDetails

import android.os.Build
import android.os.Bundle
import android.text.Html
import com.bumptech.glide.Glide
import com.example.stefano.bookme.R
import com.example.stefano.bookme.ui.base.BaseActivity
import com.example.stefano.bookme.ui.base.BaseMvp
import com.example.stefano.bookme.ui.booksList.BOOK_ID
import com.example.stefano.bookme.util.extensions.hide
import kotlinx.android.synthetic.main.activity_book_details.*
import kotlinx.android.synthetic.main.book_details_content.*
import javax.inject.Inject

class BookDetailsActivity : BaseActivity(), BookDetailsMvp.View {

    @Inject lateinit var presenter: BookDetailsMvp.Presenter

    override val layoutResourceId: Int = R.layout.activity_book_details
    override fun providePresenter(): BaseMvp.Presenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // I believe in early crashing the application with the correct message if the wrong params are sent or not sent at all
        val bookId = intent.extras?.getString(BOOK_ID)
        if (bookId != null) presenter.init(bookId)
        else throw RuntimeException("bookId must not be null in BookDetailsActivity")
    }

    override fun initUi(bookTitle: String) {
        toolbar.title = bookTitle
        initToolbar()
    }

    override fun displayBookCover(url: String?) {
        Glide.with(this)
                .load(url)
                .error(R.drawable.ic_no_cover)
                .into(bookCover)
    }

    override fun displayBookDescription(descriptionText: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            description.text = Html.fromHtml(descriptionText, Html.FROM_HTML_MODE_LEGACY)
        } else {
            description.text = Html.fromHtml(descriptionText)
        }
    }

    override fun displayPublisherAndDate(publisherAndDateText: String) {
        publisherAndDate.text = publisherAndDateText
    }

    override fun displayNumberOfPages(numberOfPagesText: String) {
        numberOfPages.text = numberOfPagesText
    }

    override fun displayAuthors(authorsText: String) {
        authors.text = authorsText
    }

    override fun displayListPrice(listPriceText: String) {
        listPrice.text = listPriceText
    }

    override fun displayRetailPrice(retailPriceText: String) {
        retailPrice.text = retailPriceText
    }

    override fun displayBuyLink(buyLink: String) {
        purchaseLink.text = buyLink
    }

    override fun hideSaleInformation() = purchaseInformationContent.hide()
}
