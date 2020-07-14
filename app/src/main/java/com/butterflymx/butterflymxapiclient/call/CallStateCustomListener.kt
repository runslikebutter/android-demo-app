package com.butterflymx.butterflymxapiclient.call

import com.butterflymx.butterflymxapiclient.utils.Constants
import com.butterflymx.butterflymxapiclient.utils.NotificationUtils
import com.butterflymx.sdk.call.CallReqState
import com.butterflymx.sdk.call.CallReqStateListener
import com.butterflymx.sdk.call.interfaces.Call

class CallStateCustomListener : CallReqStateListener {

    override fun onCallReqState(state: CallReqState, call: Call, panelID: Int, panelName: String) {
        when (state) {
            CallReqState.NEW -> {
                NotificationUtils().showNotification(panelName, call.guid, Constants.NOTIFICATION_TYPE_NEW_CALL)
            }
            CallReqState.ACCEPTED_ON_ANOTHER_DEVICE -> {
                NotificationUtils().deleteNotification(call.guid)
                NotificationUtils().showNotification(panelName, call.guid, Constants.NOTIFICATION_TYPE_ANSWERED_ON_ANOTHER_DEVICE)
            }
            CallReqState.MISSED -> {
                NotificationUtils().deleteNotification(call.guid)
                NotificationUtils().showNotification(panelName, call.guid, Constants.NOTIFICATION_TYPE_MISSED_CALL)
            }
        }
    }
}