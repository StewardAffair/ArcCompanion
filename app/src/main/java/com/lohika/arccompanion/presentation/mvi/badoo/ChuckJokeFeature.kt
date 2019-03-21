package com.lohika.arccompanion.presentation.mvi.badoo

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Bootstrapper
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.lohika.arccompanion.data.model.ChuckJoke
import com.lohika.arccompanion.data.network.api.ChuckJokeApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChuckJokeFeature(api: ChuckJokeApi) :
    ActorReducerFeature<ChuckJokeFeature.Wish, ChuckJokeFeature.Effect, ChuckJokeFeature.State, ChuckJokeFeature.News>(
        initialState = State(),
        bootstrapper = BootStrapperImpl(),
        actor = ActorImpl(api),
        reducer = ReducerImpl(),
        newsPublisher = NewsPublisherImpl()
    ) {

    sealed class Wish {
        class SearchJoke(val querry: String) : Wish()
    }

    sealed class Effect {
        object StartedLoading : Effect()
        class JokeReceived(val joke: ChuckJoke) : Effect()
        data class ErrorLoading(val throwable: Throwable) : Effect()
    }

    sealed class News {
        data class ErrorExecutingRequest(val throwable: Throwable) : News()
    }

    data class State(
        val showLoading: Boolean = false,
        val firsJokeText: String = ""
    )

    class BootStrapperImpl : Bootstrapper<Wish> {
        override fun invoke(): Observable<Wish> = Observable.just(Wish.SearchJoke(""))
    }

    class ActorImpl(private val api: ChuckJokeApi) : Actor<State, Wish, Effect> {
        override fun invoke(p1: State, wish: Wish): Observable<out Effect> =
            when (wish) {
                is Wish.SearchJoke -> api.findJoke(wish.querry)
                    .map {
                        it.result.firstOrNull() ?: ChuckJoke(
                            emptyList(), "", "", "", ""
                        )
                    }.map { Effect.JokeReceived(it) as Effect }
                    .toObservable()
                    .startWith(Observable.just(Effect.StartedLoading))
                    .onErrorReturn { Effect.ErrorLoading(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State = when (effect) {
            Effect.StartedLoading -> State(showLoading = true)
            is Effect.JokeReceived -> State(showLoading = false, firsJokeText = effect.joke.value)
            is Effect.ErrorLoading -> State(showLoading = false)
        }
    }

    class NewsPublisherImpl : NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(wish: Wish, effect: Effect, state: State): News? = when (effect) {
            is Effect.ErrorLoading -> News.ErrorExecutingRequest(effect.throwable)
            else -> null
        }
    }
}