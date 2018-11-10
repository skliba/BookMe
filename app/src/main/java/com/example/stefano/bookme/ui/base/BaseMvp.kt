package com.example.stefano.bookme.ui.base

interface BaseMvp {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun showError(message: String)
    }

    interface Presenter {
        fun init()
        fun cancel()
    }
}