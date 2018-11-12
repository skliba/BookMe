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
import com.paginate.Paginate
import kotlinx.android.synthetic.main.activity_books_list.*
import kotlinx.android.synthetic.main.empty_state.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

const val BOOK_ID = "BookId"

class BooksListActivity : BaseActivity(), BooksListMvp.View {

    private val bookClickCallback: (String) -> Unit = { bookId -> presenter.onBookClicked(bookId) }
    private val booksAdapter = BooksAdapter(clickListener = bookClickCallback)

    override val layoutResourceId: Int = R.layout.activity_books_list
    override fun providePresenter(): BaseMvp.Presenter = presenter

    @Inject lateinit var presenter: BooksListMvp.Presenter
    private var paginate: Paginate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachListeners()
        presenter.init()
    }

    override fun displayList(
            booksList: List<Book>,
            callbacks: Paginate.Callbacks
    ) = booksRecyclerView.show()
            .also { booksAdapter.update(booksList) }
            .also { emptyState.hide() }
            .also {
                paginate = Paginate.with(booksRecyclerView, callbacks)
                        .addLoadingListItem(true)
                        .build()
            }

    override fun showEmptyState() = emptyState
            .show()
            .also { booksRecyclerView.hide() }
            .also {
                emptyStateDescription.text =
                        getString(R.string.empty_state_description, searchInput.text.toString())
            }

    override fun addMoreItems(books: List<Book>) {
        booksAdapter.append(books)
    }

    override fun hideRecyclerViewLoading() {
        booksAdapter.removeLoading()
    }

    override fun showBookDetails(bookId: String) {
        val bundle = Bundle()
        bundle.putString(BOOK_ID, bookId)
        startActivity<BookDetailsActivity>(bundle)
    }

    override fun initUi() {
        toolbar.title = getString(R.string.app_name)
        initToolbar()
        booksRecyclerView.adapter = booksAdapter
    }

    private fun attachListeners() = searchInput.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            if (paginate != null) {
                paginate!!.unbind()
            }
            presenter.onInputTextChanged(searchInput.text.toString().trim())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}
