package com.lohika.arccompanion.presentation.mvi.badoo

class ChuckJokeUiEventTransformer : (UIEvent) -> ChuckJokeFeature.Wish {
    override fun invoke(uiEvent: UIEvent): ChuckJokeFeature.Wish = when (uiEvent) {
        is UIEvent.ButtonClicked -> ChuckJokeFeature.Wish.SearchJoke(uiEvent.query)
    }
}