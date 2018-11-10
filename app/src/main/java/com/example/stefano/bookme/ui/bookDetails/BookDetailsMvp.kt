package com.example.stefano.bookme.ui.bookDetails

import com.example.stefano.bookme.data.models.Book
import com.example.stefano.bookme.ui.base.BaseMvp

interface BookDetailsMvp {

    interface View : BaseMvp.View {
        fun initUi(book: Book)
    }

    interface Presenter : BaseMvp.Presenter {
        override fun init() {}
        fun init(bookId: String)
    }
}