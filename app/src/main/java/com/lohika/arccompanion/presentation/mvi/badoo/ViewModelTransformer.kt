package com.lohika.arccompanion.presentation.mvi.badoo

class ViewModelTransformer : (ChuckJokeFeature.State) -> JokeBadooViewModel {
    override fun invoke(chuckState: ChuckJokeFeature.State): JokeBadooViewModel =
        JokeBadooViewModel(showFirstLoading = chuckState.showLoading, firsJokeText = chuckState.firsJokeText)
}