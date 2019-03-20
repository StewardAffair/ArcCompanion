package com.lohika.arccompanion.data.network.api

import com.lohika.arccompanion.data.model.ChuckJokeResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckJokeApi {

    @GET("jokes/search")
    fun findJoke(@Query(value = "query") query : String): Single<ChuckJokeResult>
}