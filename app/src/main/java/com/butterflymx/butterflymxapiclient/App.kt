package com.butterflymx.butterflymxapiclient

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.multidex.MultiDexApplication
import com.butterflymx.butterflymxapiclient.call.CallStateCustomListener
import com.butterflymx.butterflymxapiclient.utils.ButterflyMxConfigBuilder
import com.butterflymx.butterflymxapiclient.utils.Constants
import com.butterflymx.butterflymxapiclient.utils.di.DaggerComponent
import com.butterflymx.butterflymxapiclient.utils.di.DaggerDaggerComponent
import com.butterflymx.sdk.core.BMXCore
import com.butterflymx.sdk.core.EndpointType

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        app = this
        dagger = DaggerDaggerComponent.builder().build()
        createNotificationChannel()
        CallStateCustomListener.init()
        context?.let {
            val sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_KEY_ENDPOINT, Context.MODE_PRIVATE)
            if (sharedPreferences.contains(Constants.SHARED_PREF_KEY_ENDPOINT)) {
                val endpointTypeInt = sharedPreferences.getInt(Constants.SHARED_PREF_KEY_ENDPOINT, EndpointType.STAGING.ordinal)
                val endpointType = EndpointType.values()[endpointTypeInt]

                val config = ButterflyMxConfigBuilder.getButterflyMxConfig(endpointType, it)
                BMXCore.getInstance(it).setConfig(config)
            }
        }
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

        val context: Context?
            get() = app?.applicationContext

    }
}
