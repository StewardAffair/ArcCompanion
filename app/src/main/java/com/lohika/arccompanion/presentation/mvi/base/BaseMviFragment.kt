package com.lohika.arccompanion.presentation.mvi.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseMviFragment<VIEW : MviView, PRESENTER : MviPresenter<VIEW>> : Fragment() {

    protected lateinit var presenter : PRESENTER

    protected abstract fun initializePresenter() : PRESENTER

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = initializePresenter()
        presenter.attachView(this as VIEW)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}