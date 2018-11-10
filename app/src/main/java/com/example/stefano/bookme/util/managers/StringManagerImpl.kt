package com.example.stefano.bookme.util.managers

import android.content.Context

class StringManagerImpl(
        private val context: Context
) : StringManager {
    override fun getString(stringRes: Int): String = context.getString(stringRes)
}