package com.butterflymx.butterflymxapiclient.utils.firebasenotification

import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.sdk.call.BMXCall
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONException
import org.json.JSONObject

class FirebaseMessagingServiceHandler : FirebaseMessagingService() {

    private fun onCloudMessage(from: String = "", data: Map<String, String>): Boolean {

        val customData: String = if (data!!.containsKey("custom_data")) data["custom_data"].toString() else "{}"
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

//                BMXCore.getInstance(App.getContext()).notifyCloudMessageReceived(from, data)

                BMXCall.getInstance(App.getContext()).processCallStatus(data.getValue("guid"),
                        data.getValue("call_status"),
                        data.getValue("panel_xmpp"),
                        data.getValue("panel_sip"))
            }
            return true
        }
        return false
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        onCloudMessage(remoteMessage?.from!!, remoteMessage.data!!)
    }
}
