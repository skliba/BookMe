package com.example.stefano.bookme.data.network

import com.example.stefano.bookme.data.models.Book
import com.example.stefano.bookme.data.models.EntityType
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("volumes")
    fun findVolumesByTitle(
            @Query("q") title: String,
            @Query("printType") printType: String = "books"
    ): Observable<EntityType>

    @GET("volumes/{bookId}")
    fun getVolumeById(
            @Path("bookId") bookId: String
    ): Single<Book>
}
