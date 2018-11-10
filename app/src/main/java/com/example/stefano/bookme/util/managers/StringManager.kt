package com.example.stefano.bookme.util.managers

import android.support.annotation.StringRes

interface StringManager {

    fun getString(@StringRes stringRes: Int): String
}