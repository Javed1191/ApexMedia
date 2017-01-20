package com.custom.baselibrary;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.view.Window;


public class SplashScreenActivity extends DashBoardActivity {
	private static int SPLASH_TIME_OUT = 500;
	private DataManager dbManager;
	private boolean flag;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash_screen);
		dbManager = new DataManager(getApplicationContext());

		dbManager.open();
		
		flag = dbManager.test();
		
		dbManager.close();
		new Handler().postDelayed(new Runnable() 
		{
			@Override
			public void run() 
			{
			
				if(flag==true)
				{
					Intent i = new Intent(SplashScreenActivity.this,LoginScreenActivity.class);
					i.putExtra("LaunchActivity",getIntent().getStringExtra("LaunchActivity"));
					startActivity(i);
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					finish();
				}
				else if(flag==false) 
				{
					Intent i = new Intent(SplashScreenActivity.this,Setting_Activity.class);
					i.putExtra("LaunchActivity",getIntent().getStringExtra("LaunchActivity"));
					startActivity(i);
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					finish();
				}
			}
		}, SPLASH_TIME_OUT);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
			}
		}, SPLASH_TIME_OUT);

	}

}
