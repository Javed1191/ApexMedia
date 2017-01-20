package com.apexmediatechnologies.apexmedia;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.Utility;


public class ForgotPasswordActivity extends AppCompatActivity
{

	private EditText edt_email;
	private Button btn_submit;
	private String strForgotPassUrl = Application_Constants.Main_URL+"keyword=forget_password";

	private Utility utility;
	private LinearLayout lay_search;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_pass);

		edt_email = (EditText) findViewById(R.id.edt_email);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		utility = new Utility(getApplicationContext());

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);


		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Forgot Password");
		//toolbar.setLogo(R.drawable.actionbar_32);

		// status bar color change
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
		}



		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		toolbar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
				intent.putExtra("menu", "dashboard");
				startActivity(intent);


			}
		});




		btn_submit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				String str_email = edt_email.getText().toString().trim();

				if(!str_email.equals("")&&!str_email.equals(null))
				{
					if(utility.checkEmail(str_email))
					{
						forgotPassword(str_email);
					}
					else
					{
						edt_email.setError("Enter valid email");
					}

				}
				else
				{
					edt_email.setError("Enter email");
				}



			}
		});




	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate( R.menu.main, menu );
		return true;
	}



	private void forgotPassword(String strUserEmail)
	{

		if(utility.checkInternet())
		{



			final Map<String, String> params = new HashMap<String, String>();
			params.put("UserEmailId", strUserEmail);


			ServiceHandler serviceHandler = new ServiceHandler(ForgotPasswordActivity.this);

			serviceHandler.registerUser(params, strForgotPassUrl, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status,str_msg;
					String siteUserID,userFirstName, userLastName,userEmail,mobileNumber,userProfileImage;
					try {
						if(str_json!=null)
						{
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if(str_status.equalsIgnoreCase("true"))
							{
								Toast.makeText(getApplicationContext(), str_msg, Toast.LENGTH_SHORT).show();

								Intent intent = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
								startActivity(intent);


							}
							else
							{
								Toast.makeText(getApplicationContext(), str_msg, Toast.LENGTH_SHORT).show();
							}
						}
						else
						{
							Toast.makeText(getApplicationContext(), "This may be server issue",Toast.LENGTH_SHORT).show();
						}

					} catch (JSONException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}

	}

}
