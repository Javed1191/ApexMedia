package com.apexmediatechnologies.apexmedia;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import services.Shared_Preferences_Class;

/**
 * Created by Belal on 5/27/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private  String str_user_type="", click_action="";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        str_user_type = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_TYPE,"");


        String str = remoteMessage.getNotification().getBody();
        click_action = remoteMessage.getNotification().getClickAction();

        for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d(TAG, "key, " + key + " value " + value);
        }



        //Calling method to generate notification
        sendNotification(remoteMessage.getNotification().getBody());


    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody)
    {

        if(str_user_type.equalsIgnoreCase("Individual"))
        {
            if(click_action.equalsIgnoreCase("job_detail"))
            {
                Intent intent = new Intent(click_action);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("menu","Notification");
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                Bitmap icon = BitmapFactory.decodeResource(getResources(),
                        R.drawable.apex_launcher);

                Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setLargeIcon(icon)/*Notification icon image*/
                        .setSmallIcon(R.drawable.apex_launcher)
                        .setContentTitle("Apex")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0, notificationBuilder.build());
            }


        }
        else
        {
            Intent intent = new Intent(click_action);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("menu","Notification");
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.apex_launcher);

            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setLargeIcon(icon)/*Notification icon image*/
                    .setSmallIcon(R.drawable.apex_launcher)
                    .setContentTitle("Apex")
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }

    }
}