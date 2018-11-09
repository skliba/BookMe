package com.example.stefano.bookme.data.models

import com.squareup.moshi.Json

data class EntityType(
        @field:Json(name = "kind") val type: String,
        @field:Json(name = "totalItems") val totalItems: Int,
        @field:Json(name = "items") val items: List<Book>?
)