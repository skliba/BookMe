package com.example.stefano.bookme.data.network

import com.example.stefano.bookme.data.models.EntityType
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("volumes")
    fun findVolumesByTitle(
            @Query("q") title: String,
            @Query("printType") printType: String = "books"
    ): Observable<EntityType>
}