package com.dawan.utils;

import android.app.Application;

public class App extends Application {
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //FacebookSdk.setIsDebugEnabled(true);
       // FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);

    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    /*public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }*/
}