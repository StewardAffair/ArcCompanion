package com.lohika.arccompanion.data.model

import com.squareup.moshi.Json

data class ChuckJokeResult(
    @Json(name = "total") val total: Int,
    @Json(name = "result") val result: List<ChuckJoke>
)

data class ChuckJoke(
    @Json(name = "category") val category: List<String>,
    @Json(name = "icon_url") val icon: String,
    @Json(name = "id") val id: String,
    @Json(name = "url") val url: String,
    @Json(name = "value") val value: String
)