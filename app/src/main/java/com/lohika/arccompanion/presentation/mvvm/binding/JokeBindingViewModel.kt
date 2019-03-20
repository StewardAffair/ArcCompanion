package com.lohika.arccompanion.presentation.mvvm.binding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.lohika.arccompanion.domain.ChuckUseCase
import com.lohika.arccompanion.domain.StandUpUseCase
import com.lohika.arccompanion.domain.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class JokeBindingViewModel(val chuckUseCase: ChuckUseCase, val standUpUseCase: StandUpUseCase) : BaseObservable() {

    @Bindable
    var secondJoke: CharSequence = ""

    @Bindable
    var firstJoke: CharSequence = ""

    @Bindable
    var firstProgressBarVisibility: Boolean = false

    @Bindable
    var secondProgressBarVisibility: Boolean = false

    private val compositeDisposable = CompositeDisposable()

    fun searchButtonClicked(query: String) {
        compositeDisposable.add(chuckUseCase(query)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{
                firstJoke = ""
                secondJoke = ""
                firstProgressBarVisibility = true
                secondProgressBarVisibility = true
                notifyChange()
            }
            .doOnSuccess { joke ->
                firstProgressBarVisibility = false
                firstJoke = joke?.value ?: ""
                notifyChange()
            }
            .flatMap { standUpUseCase(UseCase.None()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { joke ->
                secondProgressBarVisibility = false
                secondJoke = joke?.let { it.setup + " " + it.punchline } ?: ""
                notifyChange()
            })
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }
}