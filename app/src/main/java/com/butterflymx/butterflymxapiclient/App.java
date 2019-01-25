package com.butterflymx.butterflymxapiclient;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.butterflymx.butterflymxapiclient.call.CallStateCustomListener;
import com.butterflymx.butterflymxapiclient.utils.Constants;
import com.butterflymx.butterflymxapiclient.utils.di.DaggerComponent;
import com.butterflymx.butterflymxapiclient.utils.di.DaggerDaggerComponent;
import com.butterflymx.sdk.call.ButterflyMxCall;

public class App extends Application {

    private static App app;
    private static DaggerComponent daggerComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initDagger();
        createNotificationChannel();
        ButterflyMxCall.Companion.getInstance(getContext()).getEvents().register(new CallStateCustomListener());

    }

    private void initDagger() {
        daggerComponent = DaggerDaggerComponent.builder().build();
    }

    public static Context getContext() {
        return app.getApplicationContext();
    }

    public static DaggerComponent getDagger() {
        return daggerComponent;
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, Constants.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
