package com.example.stefano.bookme.ui.base

import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.example.stefano.bookme.R
import com.example.stefano.bookme.util.extensions.getColorCompat
import com.example.stefano.bookme.util.extensions.getDrawableCompat
import com.example.stefano.bookme.util.extensions.hide
import com.example.stefano.bookme.util.extensions.show
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.progress.*
import kotlinx.android.synthetic.main.toolbar.*

abstract class BaseActivity : AppCompatActivity(), BaseMvp.View {

    protected abstract fun providePresenter(): BaseMvp.Presenter
    @get:LayoutRes protected abstract val layoutResourceId: Int

    private lateinit var presenter: BaseMvp.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutResourceId)
        presenter = providePresenter()

        initToolbar()
    }

    override fun showError(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
    }

    override fun showProgress() {
        progressBar.show()
    }

    override fun hideProgress() {
        progressBar.hide()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.navigationIcon = getDrawableCompat(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = getColorCompat(R.color.colorPrimaryDark)
        }
    }
}
