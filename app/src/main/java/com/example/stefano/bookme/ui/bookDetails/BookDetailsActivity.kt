package com.example.stefano.bookme.ui.bookDetails

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.stefano.bookme.R
import com.example.stefano.bookme.data.models.Book
import com.example.stefano.bookme.ui.base.BaseActivity
import com.example.stefano.bookme.ui.base.BaseMvp
import com.example.stefano.bookme.ui.booksList.BOOK_ID
import kotlinx.android.synthetic.main.activity_book_details.*
import java.lang.RuntimeException
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

    override fun initUi(book: Book) {
        toolbar.title = book.bookInformation.title
        initToolbar()
        Glide.with(this)
                .load(book.bookInformation.imageLinks?.largeImage)
                .error(R.drawable.ic_no_cover)
                .into(bookCover)
    }
}