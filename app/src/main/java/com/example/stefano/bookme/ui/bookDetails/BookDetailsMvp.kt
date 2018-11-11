package com.example.stefano.bookme.ui.bookDetails

import com.example.stefano.bookme.ui.base.BaseMvp

interface BookDetailsMvp {

    interface View : BaseMvp.View {
        fun initUi(bookTitle: String)
        fun displayBookCover(url: String?)
        fun displayBookDescription(descriptionText: String)
        fun displayAuthors(authorsText: String)
        fun displayNumberOfPages(numberOfPagesText: String)
        fun displayListPrice(listPriceText: String)
        fun displayRetailPrice(retailPriceText: String)
        fun displayPublisherAndDate(publisherAndDateText: String)
        fun hideSaleInformation()
        fun displayBuyLink(buyLink: String)
    }

    interface Presenter : BaseMvp.Presenter {
        override fun init() {}
        fun init(bookId: String)
    }
}