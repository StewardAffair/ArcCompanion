package com.lohika.arccompanion.presentation.mvi.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.visibility
import com.lohika.arccompanion.R
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.joke_fragment.*
import org.koin.android.ext.android.get

class JokeMVIFragment : BaseMviFragment<JokeMviView, JokeMVIPresenter>(), JokeMviView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.joke_fragment, container, false)
    }

    override fun initializePresenter(): JokeMVIPresenter = JokeMVIPresenter(
        get(),
        get(),
        firstProgressBar.visibility(visibilityWhenFalse = View.INVISIBLE),
        secondProgressBar.visibility(visibilityWhenFalse = View.INVISIBLE),
        Consumer { firstTextView.text = it },
        Consumer { secondTextView.text = it },
        findButton.clicks(),
        searchEditText.toSubject()
    )
}