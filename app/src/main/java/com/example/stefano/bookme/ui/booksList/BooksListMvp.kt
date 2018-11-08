package com.example.stefano.bookme.ui.booksList

import com.example.stefano.bookme.ui.base.BaseMvp

interface BooksListMvp {

    interface View : BaseMvp.View {

    }

    interface Presenter : BaseMvp.Presenter {
        fun init()
    }
}