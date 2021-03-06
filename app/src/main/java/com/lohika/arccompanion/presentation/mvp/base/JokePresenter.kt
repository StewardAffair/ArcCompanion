package com.lohika.arccompanion.presentation.mvp.base

import com.lohika.arccompanion.domain.ChuckUseCase
import com.lohika.arccompanion.domain.StandUpUseCase
import com.lohika.arccompanion.domain.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers

class JokePresenter(val chuckUseCase: ChuckUseCase, val standUpUseCase: StandUpUseCase) :
    BasePresenter<JokeView>() {

    fun searchButtonClicked(query: String) {
        compositeDisposable.add(chuckUseCase(query)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                view.showFirstJoke("")
                view.showSecondJoke("")
                view.changeFirstProgressBarVisibility(isNeedToShow = true)
                view.changeSecondProgressBarVisibility(isNeedToShow = false)
            }
            .doOnSuccess { joke ->
                view.changeFirstProgressBarVisibility(isNeedToShow = false)
                view.changeSecondProgressBarVisibility(isNeedToShow = true)
                view.showFirstJoke(joke.value)
            }
            .flatMap { standUpUseCase(UseCase.None()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { joke ->
                view.changeSecondProgressBarVisibility(isNeedToShow = false)
                view.showSecondJoke(joke.setup + " " + joke.punchline)
            })
    }
}