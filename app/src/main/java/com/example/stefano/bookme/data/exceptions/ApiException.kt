package com.example.stefano.bookme.data.exceptions

import java.lang.Exception

data class ApiException(override val message: String?) : Exception()