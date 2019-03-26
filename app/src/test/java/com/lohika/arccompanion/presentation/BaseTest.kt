package com.lohika.arccompanion.presentation

import com.lohika.arccompanion.data.model.ChuckJoke
import com.lohika.arccompanion.data.model.StandUpJoke
import com.lohika.arccompanion.domain.ChuckUseCase
import com.lohika.arccompanion.domain.StandUpUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single

open class BaseTest {

    val testChuckJoke = ChuckJoke(emptyList(), "", "", "", "very funny joke")

    val testStandUpJoke = StandUpJoke(-1, "", "Why do always Java programmers wear glasses?", "Because they don't C#")

    val testChuckUseCase = mock<ChuckUseCase> {
        on { invoke(any()) } doReturn Single.just(testChuckJoke)
    }

    val testStandUpUseCase = mock<StandUpUseCase> {
        on { invoke(any()) } doReturn Single.just(testStandUpJoke)
    }
}