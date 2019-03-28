package com.lohika.arccompanion.presentation.mvp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lohika.arccompanion.R
import kotlinx.android.synthetic.main.joke_fragment.*
import org.koin.android.ext.android.inject

class MvpFragment : BaseMVPFragment<JokeView, JokePresenter>(),
    JokeView {

    private val jokePresenter: JokePresenter by inject()

    override fun initializePresenter(): JokePresenter = jokePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.joke_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findButton.setOnClickListener {
            jokePresenter.searchButtonClicked(searchEditText.text.toString())
        }
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
