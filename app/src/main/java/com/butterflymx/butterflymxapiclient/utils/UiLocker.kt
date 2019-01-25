package com.butterflymx.butterflymxapiclient.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText


class UiLocker {

    class EmailTextWatcher(val button: Button, private val editText: EditText) : TextWatcher {

        override fun afterTextChanged(s: Editable) {
            button.isEnabled = (ValidateUtils().validateEmail(editText))
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

    }

}
