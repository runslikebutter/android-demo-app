package com.butterflymx.butterflymxapiclient.utils.firebasenotification

import android.content.Context
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.utils.Constants.SHARED_PREF_FIREBASE_KEY
import com.butterflymx.butterflymxapiclient.utils.Constants.SHARED_PREF_NAME
import com.butterflymx.sdk.core.BMXCore
import com.butterflymx.sdk.core.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingServiceHandler : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val bmxCore = BMXCore.getInstance(App.context)

        // all call logic is processed under the hood.
        // true if it is a valid butterfly mx cloud message
        val isButterflyNotification = bmxCore.notifyCloudMessageReceived(remoteMessage?.from!!, remoteMessage.data!!)

        Log.i(this::class.java.simpleName, "Demo App received a push. Is this push from ButterflyMX $isButterflyNotification")
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        if (token != null) {

            val mSharedPreferences = App.context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            val mSharedPreferencesEditor = mSharedPreferences.edit()
            mSharedPreferencesEditor.putString(SHARED_PREF_FIREBASE_KEY, token).apply()

            if (BMXCore.getInstance(App.context).isAuthorized()) {
                BMXCore.getInstance(App.context).registerCloudMessaging(token)
            }
        }
    }
}
