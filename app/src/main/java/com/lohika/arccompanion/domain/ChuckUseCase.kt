package com.lohika.arccompanion.domain

import com.lohika.arccompanion.data.network.api.ChuckJokeApi
import com.lohika.arccompanion.data.model.ChuckJoke
import io.reactivex.Single

class ChuckUseCase(private val api: ChuckJokeApi) : UseCase<ChuckJoke, String>() {
    override fun run(params: String): Single<ChuckJoke> = api.findJoke(params).map {
        it.result.firstOrNull() ?: ChuckJoke(
            emptyList(), "", "", "", ""
        )
    }
}