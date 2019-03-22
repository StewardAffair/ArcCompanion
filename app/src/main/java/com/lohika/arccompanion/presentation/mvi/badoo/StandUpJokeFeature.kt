package com.lohika.arccompanion.presentation.mvi.badoo

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Bootstrapper
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.lohika.arccompanion.data.model.StandUpJoke
import com.lohika.arccompanion.data.network.api.StandUpJokeApi
import com.lohika.arccompanion.presentation.mvi.badoo.StandUpJokeFeature.*
import com.lohika.arccompanion.presentation.mvi.badoo.StandUpJokeFeature.Effect.*
import com.lohika.arccompanion.presentation.mvi.badoo.StandUpJokeFeature.Wish.SearchJoke
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class StandUpJokeFeature(api: StandUpJokeApi, chuckJokeFeature: ChuckJokeFeature) :
    ActorReducerFeature<Wish, Effect, State, Nothing>(
        initialState = State(),
        actor = ActorImpl(api),
        reducer = ReducerImpl(),
        bootstrapper = BoostrapperIml(chuckJokeFeature)
    ) {

    sealed class Wish {
        object None : Wish()
        object SearchJoke : Wish()
    }

    sealed class Effect {
        object StartedLoading : Effect()
        class JokeReceived(val joke: StandUpJoke) : Effect()
        data class ErrorLoading(val throwable: Throwable) : Effect()
    }

    data class State(
        val showLoading: Boolean = false,
        val jokeText: String = ""
    )

    class ActorImpl(private val api: StandUpJokeApi) : Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Observable<out Effect> =
            when (wish) {
                is SearchJoke -> api.randomJoke()
                    .map { JokeReceived(it) as Effect }
                    .toObservable()
                    .startWith(Observable.just(StartedLoading))
                    .onErrorReturn { ErrorLoading(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                is Wish.None -> Observable.empty()
            }
    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State = when (effect) {
            StartedLoading -> State(showLoading = true)
            is JokeReceived -> State(showLoading = false, jokeText = effect.joke.setup + " " + effect.joke.punchline)
            is ErrorLoading -> State(showLoading = false)
        }
    }

    class BoostrapperIml(private val chuckJokeFeature: ChuckJokeFeature) : Bootstrapper<Wish> {
        override fun invoke(): Observable<Wish> = (chuckJokeFeature.news as PublishSubject).map { SearchJoke }
    }
}