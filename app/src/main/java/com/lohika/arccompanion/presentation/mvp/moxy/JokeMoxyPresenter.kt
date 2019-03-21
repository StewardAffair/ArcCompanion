package com.lohika.arccompanion.presentation.mvp.moxy

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.lohika.arccompanion.domain.ChuckUseCase
import com.lohika.arccompanion.domain.StandUpUseCase
import com.lohika.arccompanion.domain.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class JokeMoxyPresenter(val chuckUseCase: ChuckUseCase, val standUpUseCase: StandUpUseCase) :
    MvpPresenter<JokeMoxyView>() {

    private val compositeDisposable = CompositeDisposable()

    fun searchButtonClicked(query: String) {
        compositeDisposable.add(chuckUseCase(query)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.showFirstJoke("")
                viewState.showSecondJoke("")
                viewState.changeFirstProgressBarVisibility(isNeedToShow = true)
                viewState.changeSecondProgressBarVisibility(isNeedToShow = true)
            }
            .doOnSuccess { joke ->
                viewState.changeFirstProgressBarVisibility(isNeedToShow = false)
                viewState.showFirstJoke(joke.value)
            }
            .flatMap { standUpUseCase(UseCase.None()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { joke ->
                viewState.changeSecondProgressBarVisibility(isNeedToShow = false)
                viewState.showSecondJoke(joke.setup + " " + joke.punchline)
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}