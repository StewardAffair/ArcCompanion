package com.lohika.arccompanion.presentation.mvp.base

import android.content.Context
import androidx.fragment.app.Fragment

@Suppress("UNCHECKED_CAST")
abstract class BaseMVPFragment<VIEW : View, PRESENTER : Presenter<VIEW>> : Fragment(),
    View {

    private lateinit var presenter : PRESENTER

    protected abstract fun initializePresenter() : PRESENTER

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = initializePresenter()
        presenter.onAttach(this as VIEW)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}