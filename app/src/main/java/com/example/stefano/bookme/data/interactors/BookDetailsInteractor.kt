package com.example.stefano.bookme.data.interactors

import com.example.stefano.bookme.data.models.Book
import com.example.stefano.bookme.data.network.ApiService
import com.example.stefano.bookme.data.network.Interactor
import com.example.stefano.bookme.util.extensions.applySchedulers
import com.example.stefano.bookme.util.extensions.handleErrors
import io.reactivex.Single
import javax.inject.Inject

interface BookDetailsInteractor : Interactor<String, Single<Book>>

class BookDetailsInteractorImpl @Inject constructor(
        private val apiService: ApiService
) : BookDetailsInteractor {

    override fun execute(input: String): Single<Book> = apiService
            .getVolumeById(input)
            .handleErrors()
            .applySchedulers()
}
