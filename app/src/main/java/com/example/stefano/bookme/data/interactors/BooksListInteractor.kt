package com.example.stefano.bookme.data.interactors

import com.example.stefano.bookme.data.network.ApiService
import com.example.stefano.bookme.data.network.Interactor
import javax.inject.Inject

interface BooksListInteractor : Interactor<Unit, Unit>

class BooksListInteractorImpl @Inject constructor(
        private val apiService: ApiService
) : BooksListInteractor {

    override fun execute(inputModel: Unit): Unit {

    }
}