package com.example.stefano.bookme.data.network

interface Interactor<Input, Output> {

    fun execute(inputModel: Input): Output
}