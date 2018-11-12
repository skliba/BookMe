package com.example.stefano.bookme.data.interactors

import com.example.stefano.bookme.data.models.EntityType
import com.example.stefano.bookme.data.network.ApiService
import com.example.stefano.bookme.data.network.Interactor
import com.example.stefano.bookme.util.extensions.applySchedulers
import com.example.stefano.bookme.util.extensions.handleErrors
import io.reactivex.Observable
import javax.inject.Inject

interface BooksListInteractor : Interactor<String, Observable<EntityType>> {
    fun execute(input: String, pageNumber: Int, pageSize: Int): Observable<EntityType>
}

class BooksListInteractorImpl @Inject constructor(
        private val apiService: ApiService
) : BooksListInteractor {

    override fun execute(input: String, pageNumber: Int, pageSize: Int) =
            apiService
                    .findVolumesByTitle(input, pageNumber, pageSize)
                    .applySchedulers()
                    .handleErrors()

    override fun execute(input: String) =
            apiService
                    .findVolumesByTitle(input)
                    .applySchedulers()
                    .handleErrors()
}
