package com.lohika.arccompanion.presentation.mvp.moxy

import com.lohika.arccompanion.presentation.BaseTest
import com.lohika.arccompanion.presentation.RxImmediateSchedulerRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JokeMoxyPresenterTest : BaseTest() {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private val presenter = JokeMoxyPresenter(testChuckUseCase, testStandUpUseCase)

    private val state: `JokeMoxyView$$State` = mock()

    @Before
    fun setUp() {
        presenter.setViewState(state)
    }

    @Test
    fun `when user clicked find button progress view shown`() {
        presenter.searchButtonClicked("test")

        verify(state).changeFirstProgressBarVisibility(true)
    }

    @Test
    fun `when user clicked find button and answer received jokes is shown`() {
        presenter.searchButtonClicked("test")

        verify(state).showFirstJoke("")
        verify(state).showFirstJoke(testChuckJoke.value)
    }

}