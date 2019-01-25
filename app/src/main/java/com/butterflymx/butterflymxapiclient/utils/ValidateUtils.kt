package com.butterflymx.butterflymxapiclient.utils

import android.widget.EditText

internal class ValidateUtils {

    fun validateEmail(emailEditText: EditText): Boolean {
        val email = emailEditText.editableText.toString()
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
