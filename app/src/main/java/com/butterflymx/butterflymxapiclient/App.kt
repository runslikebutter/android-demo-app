package com.butterflymx.butterflymxapiclient

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

import com.butterflymx.butterflymxapiclient.call.CallStateCustomListener
import com.butterflymx.butterflymxapiclient.utils.Constants
import com.butterflymx.butterflymxapiclient.utils.di.DaggerComponent
import com.butterflymx.butterflymxapiclient.utils.di.DaggerDaggerComponent
import com.butterflymx.sdk.call.BMXCall

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        dagger = DaggerDaggerComponent.builder().build()
        createNotificationChannel()
        BMXCall.getInstance(context).events.register(CallStateCustomListener())

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, Constants.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {

        private var app: App? = null
        var dagger: DaggerComponent? = null
            private set

        val context: Context
            get() = app!!.applicationContext
    }
}
