package com.example.stefano.bookme.util.extensions

import com.example.stefano.bookme.data.exceptions.ApiException
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.applySchedulers(): Single<T> = compose {
    it
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.applySchedulers(): Observable<T> = compose {
    it
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.handleErrors(): Single<T> = compose<T> { request ->
    request.doOnError { e ->
        val message = e.message
        Single.error<T>(ApiException(message))
    }
}

fun <T> Observable<T>.handleErrors(): Observable<T> = compose<T> { request ->
    request.doOnError { e ->
        val message = e.message
        Single.error<T>(ApiException(message))
    }
}

fun Disposable.addToCompositeDisposable(compositeDisposable: CompositeDisposable) =
        compositeDisposable.add(this)
