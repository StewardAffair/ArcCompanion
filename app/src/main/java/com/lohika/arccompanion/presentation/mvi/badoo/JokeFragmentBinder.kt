package com.lohika.arccompanion.presentation.mvi.badoo

import com.badoo.mvicore.android.AndroidBindings
import com.badoo.mvicore.binder.using

class JokeFragmentBinder(view: JokeBadooFragment, private val chuckJokeFeature: ChuckJokeFeature) :
    AndroidBindings<JokeBadooFragment>(view) {
    override fun setup(view: JokeBadooFragment) {
        binder.bind(chuckJokeFeature to view using ViewModelTransformer())
        binder.bind(view to chuckJokeFeature using UiEventTransformer())
    }
}