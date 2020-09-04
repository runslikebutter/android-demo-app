package com.butterflymx.butterflymxapiclient.utils

import android.util.Log
import com.butterflymx.sdk.core.interfaces.Logger

class ButterflyMxLogger : Logger {

    val TAG = "testlogs"

    override fun d(tag: String, text: String) {
        Log.d(TAG, text)
    }

    override fun d(tag: String, text: String, throwable: Throwable) {
        Log.d(TAG, text, throwable)
    }

    override fun e(tag: String, text: String) {
        Log.e(TAG, text)
    }

    override fun e(tag: String, text: String, throwable: Throwable) {
        Log.e(TAG, text, throwable)
    }

    override fun getLogLevel(): Int {
        return 1
    }

    override fun i(tag: String, text: String) {
        Log.i(TAG, text)

    }

    override fun i(tag: String, text: String, throwable: Throwable) {
        Log.i(TAG, text, throwable)
    }

    override fun isLoggable(tag: String, level: Int): Boolean {
        return true
    }

    override fun setLogLevel(logLevel: Int) {
    }

    override fun v(tag: String, text: String) {
    }

    override fun v(tag: String, text: String, throwable: Throwable) {
    }

    override fun w(tag: String, text: String) {
    }

    override fun w(tag: String, text: String, throwable: Throwable) {
    }

}
