package com.lohika.arccompanion.presentation.mvvm.aac

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lohika.arccompanion.domain.ChuckUseCase
import com.lohika.arccompanion.domain.StandUpUseCase

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val chuckUseCase: ChuckUseCase,
    private val standUpUseCase: StandUpUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JokeViewModel::class.java)) {
            return JokeViewModel(chuckUseCase, standUpUseCase) as T
        }
        throw IllegalArgumentException(String.format("%s Not Found", modelClass.simpleName))
    }

}