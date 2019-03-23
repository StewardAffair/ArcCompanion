package com.lohika.arccompanion.presentation.mvvm.aac

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lohika.arccompanion.domain.ChuckUseCase
import com.lohika.arccompanion.domain.StandUpUseCase
import com.lohika.arccompanion.domain.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class JokeViewModel(val chuckUseCase: ChuckUseCase, val standUpUseCase: StandUpUseCase) : ViewModel() {

    val liveData: MutableLiveData<JokeState> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    fun onSearchButtonClicked(query: String) {
        compositeDisposable.add(chuckUseCase(query)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                liveData.postValue(JokeState.EmptyDataState())
            }
            .doOnSuccess { joke ->
                liveData.postValue(JokeState.FirstJokeState(firsJokeText = joke.value))
            }
            .flatMap { standUpUseCase(UseCase.None()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { joke ->
                liveData.postValue(
                    JokeState.SecondJokeState(
                        firsJokeText = liveData.value?.firsJokeText ?: "",
                        secondJokeText = joke.setup + " " + joke.punchline
                    )
                )
            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    sealed class JokeState(
        open val showFirstLoading: Boolean = true,
        open val showSecondLoading: Boolean = false,
        open val firsJokeText: String = "",
        open val secondJokeText: String = ""
    ) {
        class EmptyDataState : JokeState()

        data class FirstJokeState(
            override val firsJokeText: String,
            override val showFirstLoading: Boolean = false,
            override val showSecondLoading: Boolean = true
        ) : JokeState()

        data class SecondJokeState(
            override val firsJokeText: String,
            override val secondJokeText: String,
            override val showFirstLoading: Boolean = false,
            override val showSecondLoading: Boolean = false
        ) : JokeState()
    }
}