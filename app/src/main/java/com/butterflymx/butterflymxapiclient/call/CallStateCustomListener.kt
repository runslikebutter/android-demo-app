package com.butterflymx.butterflymxapiclient.call

import android.util.Log
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.utils.Constants
import com.butterflymx.butterflymxapiclient.utils.NotificationUtils
import com.butterflymx.sdk.call.BMXCall
import com.butterflymx.sdk.call.CallState
import com.butterflymx.sdk.call.CallStateListener
import com.butterflymx.sdk.call.interfaces.Call

object CallStateCustomListener : CallStateListener {

    private const val TAG = "CallStateCustomListener"

    fun init() {
        App.context?.let { BMXCall.getInstance(it).events.register(this) }
    }

    override fun onCallState(state: CallState, call: Call) {
        App.context?.let {
            val panelName = BMXCall.getInstance(it).getCall(call.guid).getDetails()?.panelName
            Log.d(TAG, "New State $state")
            when (state) {
                CallState.ACCEPTED_ON_ANOTHER_DEVICE -> {
                    NotificationUtils().deleteNotification(call.guid)
                    NotificationUtils().showNotification(panelName, call.guid, Constants.NOTIFICATION_TYPE_ANSWERED_ON_ANOTHER_DEVICE)
                }
                CallState.END -> {
                    NotificationUtils().deleteNotification(call.guid)
                }

                else -> {
                    Log.d(TAG, "$state")
                }
            }
        }
    }
}