package com.lohika.arccompanion.presentation.mvi.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseMVIPresenter<VIEW : MviView> : MviPresenter<VIEW> {

    lateinit var view: VIEW

    abstract fun bind()

    private val compositeDisposable = CompositeDisposable()

    override fun attachView(view: VIEW) {
        this.view = view
        bind()
    }

    override fun detachView() {
        compositeDisposable.clear()
    }

    protected fun Disposable.addToSubscriptions() {
        compositeDisposable.add(this)
    }
}