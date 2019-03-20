package com.lohika.arccompanion.presentation.di

import android.app.Application
import com.lohika.arccompanion.R
import com.lohika.arccompanion.data.network.api.ChuckJokeApi
import com.lohika.arccompanion.data.network.api.StandUpJokeApi
import com.lohika.arccompanion.data.network.NetworkFactory
import com.lohika.arccompanion.domain.ChuckUseCase
import com.lohika.arccompanion.domain.StandUpUseCase
import com.lohika.arccompanion.presentation.mvi.base.JokeMVIPresenter
import com.lohika.arccompanion.presentation.mvp.base.JokePresenter
import com.lohika.arccompanion.presentation.mvvm.aac.JokeViewModel
import com.lohika.arccompanion.presentation.mvvm.aac.MainViewModelFactory
import com.lohika.arccompanion.presentation.mvvm.binding.JokeBindingViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { NetworkFactory() }

    single {
        get<NetworkFactory>().createApi(
            StandUpJokeApi::class.java,
            get<Application>().getString(R.string.randomJokeUrl)
        )
    }

    single {
        get<NetworkFactory>().createApi(
            ChuckJokeApi::class.java,
            get<Application>().getString(R.string.chackNorrisUrl)
        )
    }

    single { StandUpUseCase(get()) }

    single { ChuckUseCase(get()) }

    single { JokePresenter(get(), get()) }

    single { JokeBindingViewModel (get(), get())}

    single { MainViewModelFactory(get(), get()) }

    viewModel { JokeViewModel(get(), get()) }
}