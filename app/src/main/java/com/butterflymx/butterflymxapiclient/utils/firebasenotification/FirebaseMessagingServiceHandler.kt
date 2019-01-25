package com.butterflymx.butterflymxapiclient.utils.firebasenotification

import android.content.Context
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.utils.Constants
import com.butterflymx.sdk.core.ButterflyMxApp
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingServiceHandler : FirebaseMessagingService() {

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        if (token != null) {

            val mSharedPreferences = App.getContext().getSharedPreferences("BfDemoAppPref", Context.MODE_PRIVATE)
            val mSharedPreferencesEditor = mSharedPreferences.edit()
            mSharedPreferencesEditor.putString(Constants.SHARED_PREF_FIREBASE_KEY, token).apply()

            if (ButterflyMxApp.getInstance(App.getContext()).isAuthorized()) {
                ButterflyMxApp.getInstance(App.getContext()).registerCloudMessaging(token)
            }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        ButterflyMxApp.getInstance(App.getContext()).notifyCloudMessageReceived(remoteMessage?.from!!, remoteMessage?.data!!)
    }
}
