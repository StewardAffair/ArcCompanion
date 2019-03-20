package com.lohika.arccompanion.domain

import com.lohika.arccompanion.data.network.api.StandUpJokeApi
import com.lohika.arccompanion.data.model.StandUpJoke
import io.reactivex.Single

class StandUpUseCase (private val standUpJokeApi: StandUpJokeApi) : UseCase<StandUpJoke, UseCase.None>() {
    override fun run(params: None): Single<StandUpJoke> = standUpJokeApi.randomJoke()
}