package com.example.stefano.bookme.util.managers

import android.content.Context
import android.support.annotation.StringRes

interface StringManager {

    fun getString(@StringRes stringRes: Int): String
}

class StringManagerImpl(private val context: Context) : StringManager {

    override fun getString(stringRes: Int): String = context.getString(stringRes)
}
