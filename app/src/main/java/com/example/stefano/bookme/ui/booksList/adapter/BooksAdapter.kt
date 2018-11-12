package com.example.stefano.bookme.ui.booksList.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.stefano.bookme.R
import com.example.stefano.bookme.data.models.Book
import com.example.stefano.bookme.ui.base.adapter.BaseAdapter
import com.example.stefano.bookme.util.extensions.inflate
import kotlinx.android.synthetic.main.item_book.view.*

class BooksAdapter(
        override var items: List<Book> = emptyList(),
        private val clickListener: (String) -> Unit
) : BaseAdapter<Book, BooksAdapter.ViewHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) =
            ViewHolder(parent.inflate(R.layout.item_book, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindItem(items[position], clickListener)
    }

    // items.size returns the actual amount of items in the list, whereas the indexes start with 0
    // we need to subtract 1 in order to get the last index in the list, and remove that one also hence the -2
    fun removeLoading() {
        items = items.subList(0, items.size - 2)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(book: Book, clickListener: (String) -> Unit) = itemView.apply {
            setOnClickListener { clickListener.invoke(book.id) }
            bookTitle.text = book.bookInformation.title
            bookDescription.text = book.bookInformation.description
            bookAuthors.text = book.bookInformation.authors?.toString()
            Glide.with(context)
                    .load(book.bookInformation.imageLinks?.smallThumbnail)
                    .error(R.drawable.ic_no_cover)
                    .into(itemView.bookCover)
        }
    }
}
