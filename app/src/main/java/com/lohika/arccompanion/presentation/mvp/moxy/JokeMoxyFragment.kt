package com.lohika.arccompanion.presentation.mvp.moxy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.lohika.arccompanion.R
import kotlinx.android.synthetic.main.joke_fragment.*
import org.koin.android.ext.android.inject

class JokeMoxyFragment : MvpAppCompatFragment(), JokeMoxyView {

    @InjectPresenter
    internal lateinit var presenter: JokeMoxyPresenter

    @ProvidePresenter
    fun providePresenter(): JokeMoxyPresenter = inject<JokeMoxyPresenter>().value

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.joke_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findButton.setOnClickListener { presenter.searchButtonClicked(searchEditText.text.toString()) }
    }

    override fun showFirstJoke(jokeString: String) {
        firstTextView.text = jokeString
    }

    override fun showSecondJoke(jokeString: String) {
        secondTextView.text = jokeString
    }

    override fun changeFirstProgressBarVisibility(isNeedToShow: Boolean) {
        firstProgressBar.visibility = if (isNeedToShow) View.VISIBLE else View.INVISIBLE
    }

    override fun changeSecondProgressBarVisibility(isNeedToShow: Boolean) {
        secondProgressBar.visibility = if (isNeedToShow) View.VISIBLE else View.INVISIBLE
    }
}