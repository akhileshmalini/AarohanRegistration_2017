package com.amrita.aarohanregistration;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * Created by Akhilesh on 10/8/2017.
 */


public class Aarohan_Application extends android.support.multidex.MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


    }
}
