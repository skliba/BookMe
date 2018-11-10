package com.example.stefano.bookme.data.models

import com.squareup.moshi.Json

data class EntityType(
        @field:Json(name = "kind") val type: String,
        @field:Json(name = "totalItems") val totalItems: Int,
        @field:Json(name = "items") val items: List<Book>?
)

data class Book(
        @field:Json(name = "id") val id: String,
        @field:Json(name = "selfLink") val selfLink: String,
        @field:Json(name = "volumeInfo") val bookInformation: BookInformation,
        @field:Json(name = "saleInformation") val saleInformation: SaleInformation
)

data class BookInformation(
        @field:Json(name = "id") val id: String,
        @field:Json(name = "title") val title: String,
        @field:Json(name = "description") val description: String,
        @field:Json(name = "publishedDate") val date: String,
        @field:Json(name = "publisher") val publisher: String,
        @field:Json(name = "authors") val authors: List<String>?,
        @field:Json(name = "pageCount") val pageCount: Int,
        @field:Json(name = "imageLinks") val imageLinks: ImageLinks?
)

data class SaleInformation(
        @field:Json(name = "country") val country: String,
        @field:Json(name = "retailPrice") val bookPrice: BookPrice
)

data class ImageLinks(
        @field:Json(name = "smallThumbnail") val smallThumbnail: String,
        @field:Json(name = "thumbnail") val thumbnail: String,
        @field:Json(name = "small") val smallImage: String,
        @field:Json(name = "medium") val mediumImage: String,
        @field:Json(name = "large") val largeImage: String,
        @field:Json(name = "extraLarge") val extraLargeImage: String
)

data class BookPrice(
        @field:Json(name = "amount") val amount: Double,
        @field:Json(name = "currency") val currency: String
)
