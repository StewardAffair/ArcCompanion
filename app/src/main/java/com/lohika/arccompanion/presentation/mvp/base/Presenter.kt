package com.lohika.arccompanion.presentation.mvp.base

interface Presenter<VIEW : View> {
    fun onAttach(view : VIEW)
    fun onCreate()
    fun onDestroy()
}