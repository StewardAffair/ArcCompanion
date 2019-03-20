package com.lohika.arccompanion.presentation.mvvm.aac

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lohika.arccompanion.R
import kotlinx.android.synthetic.main.joke_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class JokeMVVMFragment : Fragment() {

     //val viewModel by viewModel<JokeViewModel>()

    private val factory : MainViewModelFactory by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.joke_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(this, factory).get(JokeViewModel::class.java)
        viewModel.liveData.observe(this,
            Observer<JokeViewModel.JokeParam> {
                firstProgressBar.visibility = if (it.showFirstLoading) View.VISIBLE else View.INVISIBLE
                secondProgressBar.visibility = if (it.showSecondLoading) View.VISIBLE else View.INVISIBLE
                firstTextView.text = it.firsJokeText
                secondTextView.text = it.secondJokeText
            })
        findButton.setOnClickListener { viewModel.onSeachButtonClicked(searchEditText.text.toString()) }
    }
}