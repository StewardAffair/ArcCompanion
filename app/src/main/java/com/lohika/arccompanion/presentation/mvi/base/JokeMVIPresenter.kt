package com.lohika.arccompanion.presentation.mvi.base

import com.lohika.arccompanion.domain.ChuckUseCase
import com.lohika.arccompanion.domain.StandUpUseCase
import com.lohika.arccompanion.domain.UseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject

class JokeMVIPresenter(
    val chuckUseCase: ChuckUseCase,
    val standUpUseCase: StandUpUseCase,
    var firstLoadingConsumer: Consumer<in Boolean>,
    var secondLoadingConsumer: Consumer<in Boolean>,
    var firstJokeConsumer: Consumer<in String>,
    var secondJokeConsumer: Consumer<in String>,
    var findButtonClicks: Observable<Unit>,
    var searchEditTextSubject: BehaviorSubject<String>
) : BaseMVIPresenter<JokeMviView>() {

    override fun bind() {
        findButtonClicks
            .switchMap { chuckUseCase(searchEditTextSubject.value ?: "").toObservable() }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                firstJokeConsumer.accept("")
                secondJokeConsumer.accept("")
                firstLoadingConsumer.accept(true)
                secondLoadingConsumer.accept(true)
            }
            .doOnNext { joke ->
                firstLoadingConsumer.accept(false)
                firstJokeConsumer.accept(joke.value)
            }
            .flatMap { standUpUseCase(UseCase.None()).toObservable() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { joke ->
                secondLoadingConsumer.accept(false)
                secondJokeConsumer.accept(joke.setup + " " + joke.punchline)
            }.addToSubscriptions()
    }

}