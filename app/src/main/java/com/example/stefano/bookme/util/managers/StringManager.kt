package com.example.stefano.bookme.util.managers

import android.content.Context
import android.support.annotation.StringRes

interface StringManager {

    fun getString(@StringRes stringRes: Int): String
    fun getString(@StringRes stringRes: Int, vararg arguments: String): String
}

class StringManagerImpl(private val context: Context) : StringManager {

    override fun getString(stringRes: Int): String = context.getString(stringRes)
    override fun getString(stringRes: Int, vararg arguments: String): String =
            context.getString(stringRes, *arguments)
}
