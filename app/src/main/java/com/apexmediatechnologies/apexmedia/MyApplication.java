package com.apexmediatechnologies.apexmedia;

import android.app.Application;
import android.support.multidex.MultiDex;


import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by LENOVO on 10-Nov-16.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Initializing firebase
       // Firebase.setAndroidContext(getApplicationContext());

        MultiDex.install(this);
    }
}