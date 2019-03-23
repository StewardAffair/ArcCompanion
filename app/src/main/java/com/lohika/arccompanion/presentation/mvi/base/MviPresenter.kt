package com.lohika.arccompanion.presentation.mvi.base

interface MviPresenter<VIEW : MviView> {

    fun attachView(view: VIEW)

    fun detachView()
}