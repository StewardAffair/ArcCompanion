package com.lohika.arccompanion.presentation.mvp.base

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<VIEW : View> :
    Presenter<VIEW> {

    lateinit var view: VIEW
    protected val compositeDisposable = CompositeDisposable()

    override fun onAttach(view: VIEW) {
        this.view = view
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}