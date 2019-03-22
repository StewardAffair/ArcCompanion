package com.lohika.arccompanion.presentation.mvi.badoo

import android.util.Log
import io.reactivex.functions.Consumer

class NewsListener : Consumer<ChuckJokeFeature.News> {
    override fun accept(t: ChuckJokeFeature.News) {
        Log.d("TEST", t.toString())
    }
}