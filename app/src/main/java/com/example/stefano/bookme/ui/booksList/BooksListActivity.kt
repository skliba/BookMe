package com.example.stefano.bookme.ui.booksList

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.stefano.bookme.R
import com.example.stefano.bookme.data.models.Book
import com.example.stefano.bookme.ui.base.BaseActivity
import com.example.stefano.bookme.ui.base.BaseMvp
import com.example.stefano.bookme.ui.booksList.adapter.BooksAdapter
import com.example.stefano.bookme.util.extensions.show
import kotlinx.android.synthetic.main.activity_books_list.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class BooksListActivity : BaseActivity(), BooksListMvp.View {

    @Inject lateinit var presenter: BooksListMvp.Presenter

    override val layoutResourceId: Int = R.layout.activity_books_list

    override fun providePresenter(): BaseMvp.Presenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_list)
        initUi()
        attachListeners()
        presenter.init()
    }

    override fun displayList(booksList: List<Book>) {
        booksRecyclerView.takeIf { booksList.isNotEmpty() }?.show()
        booksRecyclerView.adapter = BooksAdapter(booksList)
    }

    override fun showEmptyState() {

    }

    private fun initUi() {
        toolbar.title = getString(R.string.app_name)
        initToolbar()
    }

    private fun attachListeners() {
        searchInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(editable: Editable) {
                presenter.onInputTextChanged(searchInput.text.toString().trim())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}
