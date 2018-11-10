package com.example.stefano.bookme.ui.base.adapter

import android.support.v7.widget.RecyclerView

abstract class BaseAdapter<T : Any, VH : RecyclerView.ViewHolder>(
        protected open var items: List<T>
) : RecyclerView.Adapter<VH>() {

    override fun getItemCount(): Int = items.size

    fun update(list: List<T>) = items
            .takeUnless { items == list }
            ?.also {
                items = list
                notifyDataSetChanged()
            }
}
