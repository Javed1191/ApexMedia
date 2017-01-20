package com.apexmediatechnologies.apexmedia;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import services.Shared_Preferences_Class;

/**
 * Created by Belal on 5/27/2016.
 */


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";


    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Shared_Preferences_Class.writeString(getApplicationContext(),Shared_Preferences_Class.FCM_ID,refreshedToken);
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }

    private void sendRegistrationToServer(String token) {

    }
}