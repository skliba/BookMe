package com.example.stefano.bookme.data.interactors

import com.example.stefano.bookme.data.models.EntityType
import com.example.stefano.bookme.data.network.ApiService
import com.example.stefano.bookme.data.network.Interactor
import com.example.stefano.bookme.util.extensions.applySchedulers
import com.example.stefano.bookme.util.extensions.handleErrors
import io.reactivex.Observable
import javax.inject.Inject

interface BooksListInteractor : Interactor<String, Observable<EntityType>>

class BooksListInteractorImpl @Inject constructor(
        private val apiService: ApiService
) : BooksListInteractor {

    override fun execute(input: String): Observable<EntityType> =
            apiService
                    .findVolumesByTitle(input)
                    .applySchedulers()
                    .handleErrors()
}