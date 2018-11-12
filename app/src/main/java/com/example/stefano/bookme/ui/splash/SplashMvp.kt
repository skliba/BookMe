package com.example.stefano.bookme.ui.splash

import com.example.stefano.bookme.ui.base.BaseMvp

interface SplashMvp {

    interface View : BaseMvp.View {
        fun navigateToBooksList()
    }

    interface Presenter : BaseMvp.Presenter {
        fun onAnimationEnd()
    }
}