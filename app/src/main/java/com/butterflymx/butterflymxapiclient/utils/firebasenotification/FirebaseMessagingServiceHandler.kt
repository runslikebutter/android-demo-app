package com.butterflymx.butterflymxapiclient.utils.firebasenotification

import android.content.Context
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.utils.Constants
import com.butterflymx.butterflymxapiclient.utils.Constants.SHARED_PREF_KEY_FIREBASE
import com.butterflymx.butterflymxapiclient.utils.Constants.SHARED_PREF_NAME
import com.butterflymx.butterflymxapiclient.utils.NotificationUtils
import com.butterflymx.sdk.call.BMXCall
import com.butterflymx.sdk.call.CallDetailsLoadedListener
import com.butterflymx.sdk.call.interfaces.CallDetails
import com.butterflymx.sdk.core.BMXCore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingServiceHandler : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val data = remoteMessage?.data
        val status = data?.get("call_status") ?: ""
        val guid = data?.get("guid") ?: ""

        if (status.isNotEmpty() && guid.isNotEmpty()) {
            if (status == "initializing") {
                App.context?.let {
                    BMXCall.getInstance(it).getCall(guid).process(object : CallDetailsLoadedListener {
                        override fun onCallDetailsLoaded(details: CallDetails) {
                            NotificationUtils().showNotification(details.panelName, guid, Constants.NOTIFICATION_TYPE_NEW_CALL)
                        }
                    })
                }
            } else {
                NotificationUtils().deleteNotification(guid)
            }
        }
    }


    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        if (token != null) {
            App.context?.let {
                val mSharedPreferences = it.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                val mSharedPreferencesEditor = mSharedPreferences.edit()
                mSharedPreferencesEditor.putString(SHARED_PREF_KEY_FIREBASE, token).apply()

                if (BMXCore.getInstance(it).isAuthorized()) {
                    //TODO: register FCM token
                }
            }
        }
    }
}
