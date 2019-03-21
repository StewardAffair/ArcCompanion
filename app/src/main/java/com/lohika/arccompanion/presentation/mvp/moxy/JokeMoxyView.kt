package com.lohika.arccompanion.presentation.mvp.moxy

import com.arellomobile.mvp.MvpView

interface JokeMoxyView : MvpView {
    fun showFirstJoke(jokeString: String)
    fun showSecondJoke(jokeString: String)
    fun changeFirstProgressBarVisibility(isNeedToShow: Boolean)
    fun changeSecondProgressBarVisibility(isNeedToShow: Boolean)
}