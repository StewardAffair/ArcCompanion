package com.lohika.arccompanion.presentation.mvvm.aac

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lohika.arccompanion.domain.ChuckUseCase
import com.lohika.arccompanion.domain.StandUpUseCase
import com.lohika.arccompanion.domain.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class JokeViewModel(val chuckUseCase: ChuckUseCase, val standUpUseCase: StandUpUseCase) : ViewModel() {

    val liveData: MutableLiveData<JokeParam> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    fun onSeachButtonClicked(query: String) {
        compositeDisposable.add(chuckUseCase(query)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                liveData.postValue(JokeParam.EmptyDataParam())
            }
            .doOnSuccess { joke ->
                liveData.postValue(JokeParam.FirstJokeParam(firsJokeText = joke.value))
            }
            .flatMap { standUpUseCase(UseCase.None()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { joke ->
                liveData.postValue(
                    JokeParam.SecondJokeParam(
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

    sealed class JokeParam(
        open val showFirstLoading: Boolean = true,
        open val showSecondLoading: Boolean = true,
        open val firsJokeText: String = "",
        open val secondJokeText: String = ""
    ) {
        class EmptyDataParam : JokeParam()

        data class FirstJokeParam(
            override val firsJokeText: String,
            override val showFirstLoading: Boolean = false
        ) : JokeParam()

        data class SecondJokeParam(
            override val firsJokeText: String,
            override val secondJokeText: String,
            override val showFirstLoading: Boolean = false,
            override val showSecondLoading: Boolean = false
        ) : JokeParam()
    }
}