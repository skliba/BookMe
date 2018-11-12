package com.example.stefano.bookme.ui.splash

import javax.inject.Inject

class SplashPresenter @Inject constructor(
        private val view: SplashMvp.View
) : SplashMvp.Presenter {

    override fun init() {

    }

    override fun onAnimationEnd() {
        view.navigateToBooksList()
    }

    override fun cancel() {

    }
}