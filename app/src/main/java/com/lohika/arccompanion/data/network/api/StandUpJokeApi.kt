package com.lohika.arccompanion.data.network.api

import com.lohika.arccompanion.data.model.StandUpJoke
import io.reactivex.Single
import retrofit2.http.GET

interface StandUpJokeApi {
    @GET("random_joke")
    fun randomJoke(): Single<StandUpJoke>
}