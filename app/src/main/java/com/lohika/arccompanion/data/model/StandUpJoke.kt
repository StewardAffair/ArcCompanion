package com.lohika.arccompanion.data.model

import com.squareup.moshi.Json

data class StandUpJoke(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: String,
    @Json(name = "setup") val setup: String,
    @Json(name = "punchline") val punchline: String
)