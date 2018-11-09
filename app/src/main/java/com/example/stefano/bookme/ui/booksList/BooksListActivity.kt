package com.example.stefano.bookme.ui.booksList

import android.os.Bundle
import com.example.stefano.bookme.R
import com.example.stefano.bookme.ui.base.BaseActivity
import com.example.stefano.bookme.ui.base.BaseMvp
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
        presenter.init()
    }

    private fun initUi() {
        toolbar.title = getString(R.string.app_name)
        initToolbar()
    }
}
