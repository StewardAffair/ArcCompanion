package com.lohika.arccompanion.presentation.mvi.badoo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lohika.arccompanion.R
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.joke_fragment.*
import org.koin.android.ext.android.get

class JokeBadooFragment : ObservableSourseFragment<UIEvent>(), Consumer<JokeBadooViewModel> {

    private val binder: JokeFragmentBinder by lazy {
        JokeFragmentBinder(this, get(), get(), get())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.joke_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder.setup(this)
        findButton.setOnClickListener { onNext(UIEvent.ButtonClicked(searchEditText.text.toString())) }
    }

    override fun accept(viewModel: JokeBadooViewModel) {
        viewModel.let {
            firstProgressBar.visibility = if (it.showFirstLoading) View.VISIBLE else View.INVISIBLE
            secondProgressBar.visibility = if (it.showSecondLoading) View.VISIBLE else View.INVISIBLE
            firstTextView.text = it.firsJokeText
            secondTextView.text = it.secondJokeText
        }
    }
}