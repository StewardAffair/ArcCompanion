package com.lohika.arccompanion.presentation.mvi.badoo

import com.badoo.mvicore.android.AndroidBindings
import com.badoo.mvicore.binder.using
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.BiFunction

class JokeFragmentBinder(
    view: JokeBadooFragment, private val chuckJokeFeature: ChuckJokeFeature,
    private val standUpJokeFeature: StandUpJokeFeature, private val newsListener: NewsListener
) :
    AndroidBindings<JokeBadooFragment>(view) {
    override fun setup(view: JokeBadooFragment) {
        binder.bind(combineLatest(chuckJokeFeature, standUpJokeFeature) to view using ViewModelTransformer())
        binder.bind(view to chuckJokeFeature using ChuckJokeUiEventTransformer())
        binder.bind(chuckJokeFeature.news to newsListener)
    }
}

fun <T1, T2> combineLatest(o1: ObservableSource<T1>, o2: ObservableSource<T2>): ObservableSource<Pair<T1, T2>> =
    Observable.combineLatest(
        o1,
        o2,
        BiFunction<T1, T2, Pair<T1, T2>> { t1, t2 ->
            t1 to t2
        }
    )
