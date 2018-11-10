package com.example.stefano.bookme.ui.booksList

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.stefano.bookme.R
import com.example.stefano.bookme.data.models.Book
import com.example.stefano.bookme.ui.base.BaseActivity
import com.example.stefano.bookme.ui.base.BaseMvp
import com.example.stefano.bookme.ui.bookDetails.BookDetailsActivity
import com.example.stefano.bookme.ui.booksList.adapter.BooksAdapter
import com.example.stefano.bookme.util.extensions.hide
import com.example.stefano.bookme.util.extensions.show
import com.example.stefano.bookme.util.extensions.startActivity
import kotlinx.android.synthetic.main.activity_books_list.*
import kotlinx.android.synthetic.main.empty_state.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

const val BOOK_ID = "BookId"

class BooksListActivity : BaseActivity(), BooksListMvp.View {

    @Inject lateinit var presenter: BooksListMvp.Presenter

    override val layoutResourceId: Int = R.layout.activity_books_list
    override fun providePresenter(): BaseMvp.Presenter = presenter

    private val bookClickCallback: (String) -> Unit = { bookId -> presenter.onBookClicked(bookId) }

    private val booksAdapter = BooksAdapter(clickListener = bookClickCallback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        attachListeners()
        presenter.init()
    }

    override fun displayList(booksList: List<Book>) = booksRecyclerView
            .show()
            .also { booksAdapter.update(booksList) }
            .also { emptyState.hide() }

    override fun showEmptyState() = emptyState
            .show()
            .also { booksRecyclerView.hide() }
            .also {
                emptyStateDescription.text =
                        getString(R.string.empty_state_description, searchInput.text.toString())
            }

    override fun showBookDetails(bookId: String) {
        val bundle = Bundle()
        bundle.putString(BOOK_ID, bookId)
        startActivity<BookDetailsActivity>(bundle)
    }

    private fun initUi() {
        toolbar.title = getString(R.string.app_name)
        initToolbar()
        booksRecyclerView.adapter = booksAdapter
    }

    private fun attachListeners() = searchInput.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable) = presenter.onInputTextChanged(
                searchInput.text.toString().trim())

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}
