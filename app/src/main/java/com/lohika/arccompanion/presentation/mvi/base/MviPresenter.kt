package com.lohika.arccompanion.presentation.mvi.base

interface MviPresenter<VIEW : MviView> {

    abstract fun attachView(view: VIEW)

    abstract fun detachView()
}