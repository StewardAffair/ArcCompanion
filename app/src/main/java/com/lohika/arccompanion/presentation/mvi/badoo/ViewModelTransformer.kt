package com.lohika.arccompanion.presentation.mvi.badoo

class ViewModelTransformer : (Pair<ChuckJokeFeature.State, StandUpJokeFeature.State>) -> JokeBadooViewModel {
    override fun invoke(pair: Pair<ChuckJokeFeature.State, StandUpJokeFeature.State>): JokeBadooViewModel {
        val (chuckState, standUpJokeState) = pair
        return JokeBadooViewModel(
            showFirstLoading = chuckState.showLoading,
            firsJokeText = chuckState.jokeText,
            showSecondLoading = standUpJokeState.showLoading,
            secondJokeText = standUpJokeState.jokeText
        )
    }
}