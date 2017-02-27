package com.apexmediatechnologies.apexmedia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.custom.baselibrary.DashBoardActivity;
import com.custom.baselibrary.DataManager;
import com.custom.baselibrary.LoginScreenActivity;
import com.custom.baselibrary.Setting_Activity;
import com.google.firebase.iid.FirebaseInstanceId;

import services.Shared_Preferences_Class;


public class SplashScreenActivity extends AppCompatActivity {
	private static int SPLASH_TIME_OUT = 3000;
	private String userGcmRegID="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.custom.baselibrary.R.layout.activity_splash_screen);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		userGcmRegID = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.FCM_ID,"");

		if(userGcmRegID.isEmpty())
		{
			// Resets Instance ID and revokes all tokens.
			try {
				// FirebaseInstanceId.getInstance().deleteInstanceId();
				userGcmRegID  = FirebaseInstanceId.getInstance().getToken();
				Shared_Preferences_Class.writeString(getApplicationContext(),Shared_Preferences_Class.FCM_ID,userGcmRegID);

				Log.i("FCMID",userGcmRegID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{

					//Intent i = new Intent(SplashScreenActivity.this,LoginActivity.class);
				Intent i = new Intent(SplashScreenActivity.this,PaymentActivity.class);
					startActivity(i);
					overridePendingTransition(com.custom.baselibrary.R.anim.fade_in, com.custom.baselibrary.R.anim.fade_out);
					finish();
			}
		}, SPLASH_TIME_OUT);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
			}
		}, SPLASH_TIME_OUT);

	}

}
