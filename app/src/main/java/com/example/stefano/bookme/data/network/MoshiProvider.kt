package com.example.stefano.bookme.data.network

import com.squareup.moshi.Moshi

object MoshiProvider {

    val moshi: Moshi

    init {
        moshi = Moshi.Builder()
                .build()
    }
}