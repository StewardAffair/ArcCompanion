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
    private var firstLoadingConsumer: Consumer<in Boolean>,
    private var secondLoadingConsumer: Consumer<in Boolean>,
    private var firstJokeConsumer: Consumer<in String>,
    private var secondJokeConsumer: Consumer<in String>,
    private var findButtonClicks: Observable<Unit>,
    private var searchEditTextSubject: BehaviorSubject<String>
) : BaseMVIPresenter<JokeMviView>() {

    override fun bind() {
        compositeDisposable.add(findButtonClicks
            .switchMap { chuckUseCase(searchEditTextSubject.value ?: "").toObservable() }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                firstJokeConsumer.accept("")
                secondJokeConsumer.accept("")
                firstLoadingConsumer.accept(true)
                secondLoadingConsumer.accept(false)
            }
            .doOnNext { joke ->
                firstLoadingConsumer.accept(false)
                secondLoadingConsumer.accept(true)
                firstJokeConsumer.accept(joke.value)
            }
            .flatMap { standUpUseCase(UseCase.None()).toObservable() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { joke ->
                secondLoadingConsumer.accept(false)
                secondJokeConsumer.accept(joke.setup + " " + joke.punchline)
            })
    }

}