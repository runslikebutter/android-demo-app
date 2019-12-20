package com.butterflymx.butterflymxapiclient.utils.firebasenotification

import com.butterflymx.sdk.core.BMXCore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingServiceHandler : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        /**
         * If the app receives Firebase Cloud Messages not directly from ButterflyMX system,
         * you should call BMXCore.getInstance(context).notifyCloudMessageReceived(from, data),
         * check an example bellow.
         */

        //Any string value with length more than 2 letters (e.g. "123"), it will be removed in future releases
        val from = "123"

        remoteMessage?.let {
            val data = generateValidData(it.data)
            BMXCore.getInstance(this).notifyCloudMessageReceived(from, data)
        }
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        if (token != null) {
            // TODO Register device token on your server side
        }
    }

    /**
     * Implement this function according to your remoteMessage structure
     * You should set the following params to SDK to make it works:
     *
     * guid:        valid BMX call GUID value
     * panel_xmpp:  valid BMX panel XMPP Id
     * panel_sip:   valid BMX panel SIP Id
     * custom_data: {'notification_type':'call','notification_id':0}; notification_id should be >= 0
     * call_status: could be: initializing/canceled/connecting_sip/voip_rollover/rejected/
     *              timeout_online_signal/answered_on_another_device.
     *              inform the SDK with every new call statuses
     *
     * @param remoteMessageData MutableMap<String,String> from your cloud push
     * @return a valid SDK Map<String,String>
     */
    private fun generateValidData(remoteMessageData: MutableMap<String, String>): Map<String, String> {
        val data = HashMap<String, String>()
        data["custom_data"] = "{'notification_type':'call','notification_id':0}"
        data["call_status"] = remoteMessageData["call_status"] as String
        data["guid"] = remoteMessageData["guid"] as String
        data["panel_xmpp"] = remoteMessageData["panel_xmpp"] as String
        data["panel_sip"] = remoteMessageData["panel_sip"] as String
        return data
    }
}
