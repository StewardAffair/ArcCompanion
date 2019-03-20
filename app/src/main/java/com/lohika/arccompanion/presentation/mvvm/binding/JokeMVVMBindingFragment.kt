package com.lohika.arccompanion.presentation.mvvm.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lohika.arccompanion.databinding.JokeFragmentBindingBinding
import org.koin.android.ext.android.inject

class JokeMVVMBindingFragment : Fragment() {

    private val jokeBindingViewModel : JokeBindingViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = JokeFragmentBindingBinding.inflate(inflater, container, false)
        binding.viewModel = jokeBindingViewModel
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        jokeBindingViewModel.onDestroy()
    }
}