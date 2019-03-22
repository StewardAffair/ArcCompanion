package com.lohika.arccompanion.presentation.mvi.badoo

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.lohika.arccompanion.data.model.ChuckJoke
import com.lohika.arccompanion.data.network.api.ChuckJokeApi
import com.lohika.arccompanion.presentation.mvi.badoo.ChuckJokeFeature.*
import com.lohika.arccompanion.presentation.mvi.badoo.ChuckJokeFeature.Effect.*
import com.lohika.arccompanion.presentation.mvi.badoo.ChuckJokeFeature.News.ErrorExecutingRequest
import com.lohika.arccompanion.presentation.mvi.badoo.ChuckJokeFeature.News.Received
import com.lohika.arccompanion.presentation.mvi.badoo.ChuckJokeFeature.Wish.SearchJoke
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChuckJokeFeature(api: ChuckJokeApi) :
    ActorReducerFeature<Wish, Effect, State, News>(
        initialState = State(),
        actor = ActorImpl(api),
        reducer = ReducerImpl(),
        newsPublisher = NewsPublisherImpl()
    ) {

    sealed class Wish {
        class SearchJoke(val query: String) : Wish()
    }

    sealed class Effect {
        object StartedLoading : Effect()
        class JokeReceived(val joke: ChuckJoke) : Effect()
        data class ErrorLoading(val throwable: Throwable) : Effect()
    }

    sealed class News {
        object Received : News()
        data class ErrorExecutingRequest(val throwable: Throwable) : News()
    }

    data class State(
        val showLoading: Boolean = false,
        val jokeText: String = ""
    )

    class ActorImpl(private val api: ChuckJokeApi) : Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Observable<out Effect> =
            when (wish) {
                is SearchJoke -> api.findJoke(wish.query)
                    .map {
                        it.result.firstOrNull() ?: ChuckJoke(
                            emptyList(), "", "", "", ""
                        )
                    }.map { JokeReceived(it) as Effect }
                    .toObservable()
                    .startWith(Observable.just(StartedLoading))
                    .onErrorReturn { ErrorLoading(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State = when (effect) {
            StartedLoading -> State(showLoading = true)
            is JokeReceived -> State(showLoading = false, jokeText = effect.joke.value)
            is ErrorLoading -> State(showLoading = false)
        }
    }

    class NewsPublisherImpl : NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(wish: Wish, effect: Effect, state: State): News? = when (effect) {
            is ErrorLoading -> ErrorExecutingRequest(effect.throwable)
            is JokeReceived -> Received
            else -> null
        }
    }
}