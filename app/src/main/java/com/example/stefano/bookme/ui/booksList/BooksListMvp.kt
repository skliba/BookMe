package com.example.stefano.bookme.ui.booksList

import com.example.stefano.bookme.data.models.Book
import com.example.stefano.bookme.ui.base.BaseMvp

interface BooksListMvp {

    interface View : BaseMvp.View {
        fun displayList(booksList: List<Book>)
        fun showEmptyState()
        fun showBookDetails(bookId: String)
    }

    interface Presenter : BaseMvp.Presenter {
        fun onInputTextChanged(query: String)
        fun onBookClicked(bookId: String)
    }
}
