package com.lohika.arccompanion.presentation.mvp.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<VIEW : View> :
    Presenter<VIEW> {

    lateinit var view: VIEW
    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(view: VIEW) {
        this.view = view
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    protected fun Disposable.addToSubscriptions() {
        compositeDisposable.add(this)
    }
}