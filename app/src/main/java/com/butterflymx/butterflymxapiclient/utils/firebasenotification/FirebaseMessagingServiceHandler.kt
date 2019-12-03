package com.butterflymx.butterflymxapiclient.utils.firebasenotification

import android.content.Context
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.utils.Constants.SHARED_PREF_FIREBASE_KEY
import com.butterflymx.butterflymxapiclient.utils.Constants.SHARED_PREF_NAME
import com.butterflymx.sdk.core.BMXCore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONException
import org.json.JSONObject

class FirebaseMessagingServiceHandler : FirebaseMessagingService() {


    private fun onCloudMessage(from: String = "", data: Map<String, String>): Boolean {

        val customData: String = if (data.containsKey("custom_data")) data["custom_data"].toString() else "{}"
        val customDataJson = JSONObject(customData)
        val type = try {
            customDataJson.optString("notification_type", "call")
        } catch (e: JSONException) {
            //default value
            "call"
        }

        if ("call" == type) {
            if (data.containsKey("call_status") &&
                    data.containsKey("guid") &&
                    data.containsKey("panel_xmpp") &&
                    data.containsKey("panel_sip")) {

                BMXCore.getInstance(App.context).notifyCloudMessageReceived(from, data)
            }
            return true
        }
        return false
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        onCloudMessage(remoteMessage?.from!!, remoteMessage.data!!)
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
