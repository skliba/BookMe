package com.example.stefano.bookme.ui.booksList

import com.example.stefano.bookme.data.models.Book
import com.example.stefano.bookme.ui.base.BaseMvp
import com.paginate.Paginate

interface BooksListMvp {

    interface View : BaseMvp.View {
        fun initUi()
        fun displayList(booksList: List<Book>, callbacks: Paginate.Callbacks)
        fun showEmptyState()
        fun showBookDetails(bookId: String)
        fun addMoreItems(books: List<Book>)
        fun hideRecyclerViewLoading()
    }

    interface Presenter : BaseMvp.Presenter {
        fun onInputTextChanged(query: String)
        fun onBookClicked(bookId: String)
    }
}
