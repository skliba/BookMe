package com.example.stefano.bookme.ui.booksList.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stefano.bookme.R
import com.example.stefano.bookme.ui.base.adapter.BaseAdapter

class BooksAdapter(
        private val items: List<Int>,
        private val xml: Int
) : BaseAdapter<Int, BooksAdapter.ViewHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(xml, parent, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindItem(items[position])
    }

    class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {

        }
    }
}

fun getBooksAdapter(items: List<Int>): BooksAdapter = BooksAdapter(items, R.layout.item_book)