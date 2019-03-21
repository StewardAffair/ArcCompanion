package com.lohika.arccompanion.presentation.mvi.badoo

sealed class UIEvent {
    data class ButtonClicked(val query: String) : UIEvent()
}