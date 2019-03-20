package com.lohika.arccompanion.presentation.mvp.base

interface JokeView : View {
    fun showFirstJoke(jokeString: String)
    fun showSecondJoke(jokeString: String)
    fun changeFirstProgressBarVisibility(isNeedToShow: Boolean)
    fun changeSecondProgressBarVisibility(isNeedToShow: Boolean)
}