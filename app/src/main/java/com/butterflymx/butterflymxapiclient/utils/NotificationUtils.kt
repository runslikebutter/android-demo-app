package com.butterflymx.butterflymxapiclient.utils

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.text.TextUtils
import androidx.navigation.NavDeepLinkBuilder
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.MainActivity
import com.butterflymx.butterflymxapiclient.R


class NotificationUtils {

    private var intent: PendingIntent? = null

    fun showNotification(panelName: String?, guId: String, notificationType: String) {
        val title = if (TextUtils.isEmpty(panelName)) App.context?.getString(R.string.new_call_notif_default_title) else panelName
        var notificationBody: String? = null
        val notificationId = guId.hashCode()

        when (notificationType) {
            Constants.NOTIFICATION_TYPE_NEW_CALL -> {
                intent = newCallIntent(guId, panelName)
                notificationBody = App.context?.getString(R.string.new_call_notif_you_have_a_visitor)
            }

            Constants.NOTIFICATION_TYPE_MISSED_CALL -> {
                intent = missedOrAnsweredAnotherDeviceCall()
                notificationBody = App.context?.getString(R.string.missed_call)
            }

            Constants.NOTIFICATION_TYPE_ANSWERED_ON_ANOTHER_DEVICE -> {
                intent = missedOrAnsweredAnotherDeviceCall()
                notificationBody = App.context?.getString(R.string.call_was_answered_on_another_device)
            }
        }

        val mBuilder = App.context?.let {
            NotificationCompat.Builder(it, Constants.NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification_icon)
                    .setContentTitle(title)
                    .setContentText(notificationBody)
                    .setCategory(Notification.CATEGORY_CALL)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setAutoCancel(true)
        }

        wakeUpScreen()
        try { //avoid exceptions in custom android
            mBuilder?.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mBuilder?.setContentIntent(intent)
        App.context?.let {
            if (mBuilder != null) {
                NotificationManagerCompat.from(it).notify(notificationId, mBuilder.build())
            }
        }
    }

    private fun newCallIntent(guid: String, panelName: String?): PendingIntent? {
        val args = Bundle()
        args.putString(Constants.CALL_GUID, guid)
        args.putString(Constants.CALL_PANEL_NAME, panelName)
        return App.context?.let {
            NavDeepLinkBuilder(it)
                    .setGraph(R.navigation.mobile_navigation)
                    .setDestination(R.id.incoming_call)
                    .setArguments(args)
                    .createPendingIntent()
        }
    }

    private fun missedOrAnsweredAnotherDeviceCall(): PendingIntent {
        val pendingIntent = Intent(App.context, MainActivity::class.java)
        return PendingIntent.getActivity(App.context, System.currentTimeMillis().toInt(), pendingIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    @SuppressLint("InvalidWakeLockTag")
    private fun wakeUpScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            // Wake up screen
            val powerManager = App.context?.getSystemService(Context.POWER_SERVICE) as PowerManager?
            val isScreenOn: Boolean?
            isScreenOn = powerManager?.isInteractive
            if (isScreenOn == false) {
                val wl = powerManager?.newWakeLock(PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE, "MH24_SCREENLOCK")
                wl?.acquire(2000)
                val wlCpu = powerManager?.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MH24_SCREENLOCK")
                wlCpu?.acquire(2000)
            }
        }
    }

    fun deleteNotification(guid: String?) {
        App.context?.let { NotificationManagerCompat.from(it).cancel(guid?.hashCode()!!) }
    }
}