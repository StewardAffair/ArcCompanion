package com.lohika.arccompanion.presentation.mvi.base

import io.reactivex.disposables.CompositeDisposable

abstract class BaseMVIPresenter<VIEW : MviView> : MviPresenter<VIEW> {

    lateinit var view: VIEW

    abstract fun bind()

    protected val compositeDisposable = CompositeDisposable()

    override fun attachView(view: VIEW) {
        this.view = view
        bind()
    }

    override fun detachView() {
        compositeDisposable.clear()
    }
}