package com.lohika.arccompanion.presentation.mvi.base

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.subjects.BehaviorSubject

fun EditText.toSubject(): BehaviorSubject<String> = BehaviorSubject.create<String>().also {

    it.onNext(this.text.toString())
    var lastSetText: String? = null

    it.subscribe { if (it != lastSetText) this.setText(it) }
    this.addTextChangedListener(object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {  }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val text = s?.toString() ?: return
            if (text != it.value) {
                lastSetText = text
                it.onNext(text)
            }
        }

    })
}