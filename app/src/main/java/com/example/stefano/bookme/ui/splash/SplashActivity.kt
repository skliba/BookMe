package com.example.stefano.bookme.ui.splash

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.stefano.bookme.R
import com.example.stefano.bookme.ui.base.BaseActivity
import com.example.stefano.bookme.ui.base.BaseMvp
import com.example.stefano.bookme.ui.booksList.BooksListActivity
import com.example.stefano.bookme.util.extensions.startActivity
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashMvp.View {

    @Inject lateinit var presenter: SplashMvp.Presenter
    override val layoutResourceId: Int = R.layout.activity_splash

    override fun providePresenter(): BaseMvp.Presenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    private fun initUi() {
        logoImage.pathAnimator.duration(
                this.resources.getInteger(R.integer.animation_duration)).start()
        appNameText.animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_bottom)
        appNameText.animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
                // Nothing here
            }

            override fun onAnimationEnd(p0: Animation?) {
                presenter.onAnimationEnd()
            }

            override fun onAnimationStart(p0: Animation?) {
                // Nothing here
            }
        })
    }

    override fun navigateToBooksList() {
        startActivity<BooksListActivity>()
        supportFinishAfterTransition()
    }
}