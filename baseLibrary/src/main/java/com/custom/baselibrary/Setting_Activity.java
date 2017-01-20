package com.custom.baselibrary;


import org.json.JSONArray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Setting_Activity extends Activity {

	private Commons common;
	private static DataManager dbManager;
	private CustomText txt_Setting;
	private Button btn_cancel, btn_Save;
	private static EditText edt_ServerInstance;
	private static EditText edt_SQLInstance;
	private static EditText edt_EntCode;
	private static EditText edt_Handler;
	private static EditText edt_FyrCode;
	private static EditText edt_LocCode;
	private static EditText edt_Interface;

	
	public static String gStrServerInstance = "";
	public static String gStrSQLInstance = "";
	public static String gStrInterface = "";
	public static String gStrHandler = "";
	public static String gStrEntCode = "";
	public static String gStrLocCode = "";
	public static String gStrFyrCode = "";

	public static String gStrDeviceID = "";
	ProgressDialog dialog;
	
	

	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_new);
		dbManager = new DataManager(Setting_Activity.this);
		edt_ServerInstance = (EditText) findViewById(R.id.edt_ServerInstance);
		edt_SQLInstance = (EditText) findViewById(R.id.edt_SQLInstance);
		edt_EntCode = (EditText) findViewById(R.id.edt_EntCode);
		edt_Handler = (EditText) findViewById(R.id.edt_Handler);
		edt_FyrCode = (EditText) findViewById(R.id.edt_FyrCode);
		edt_LocCode = (EditText) findViewById(R.id.edt_LocCode);
		edt_Interface = (EditText) findViewById(R.id.edt_Interface);
		
		btn_Save = (Button) findViewById(R.id.btn_Save);;
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		common = new Commons(getApplicationContext());
		dialog= new ProgressDialog(Setting_Activity.this);
		
		try {
			dbManager.open();
			Cursor cursor = dbManager.settingsSelect();
			
			if(cursor!=null && cursor.getCount()>0)
			{
				setData();
			}
			dbManager.close();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		edt_ServerInstance.addTextChangedListener(new addListenerOnTextChange(getApplicationContext(), edt_ServerInstance));
		edt_SQLInstance.addTextChangedListener(new addListenerOnTextChange(getApplicationContext(), edt_SQLInstance));
		edt_EntCode.addTextChangedListener(new addListenerOnTextChange(getApplicationContext(), edt_EntCode));
		edt_Handler.addTextChangedListener(new addListenerOnTextChange(getApplicationContext(), edt_Handler));
		edt_FyrCode.addTextChangedListener(new addListenerOnTextChange(getApplicationContext(), edt_FyrCode));
		edt_LocCode.addTextChangedListener(new addListenerOnTextChange(getApplicationContext(), edt_LocCode));
		edt_Interface.addTextChangedListener(new addListenerOnTextChange(getApplicationContext(), edt_Interface));
		
		
		
		
		/*btn_Save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {
					// if(!strServerName.equals(""))
					// {
					 gStrServerInstance = edtServerInstance.getText()
							.toString();
					 gStrSQLInstance = edt_SQLInstance.getText()
							.toString();
					 gStrHandler = edt_Handler.getText().toString();
					 gStrEntCode = edt_EntCode.getText().toString();
					 gStrFyrCode = edt_FyrCode.getText().toString();
					 gStrUserCode = edt_UserCode.getText().toString();
					 gStrLocCode = edt_LocCode.getText().toString();
					 gStrInterface = edt_Interface.getText().toString();
					 gStrDeviceID = getDiviceID();
					if (gStrServerInstance.equals("") && gStrSQLInstance.equals("")
							&& gStrHandler.equals("") && gStrEntCode.equals("")
							&& gStrFyrCode.equals("") && gStrUserCode.equals("") && gStrLocCode.equals("")
							&& gStrInterface.equals("")
							&& gStrDeviceID.equals("")) {

						Toast.makeText(getApplicationContext(),
								"Enter All The Feilds", Toast.LENGTH_LONG)
								.show();
					} else {
						insertSettingData();

						Toast.makeText(getApplicationContext(),
								"Data Inserted", Toast.LENGTH_LONG).show();

						Intent i = new Intent(Setting_Activity.this,
								LoginScreenActivity.class);
						i.putExtra("LaunchActivity", getIntent()
								.getStringExtra("LaunchActivity"));
						startActivity(i);
						// overridePendingTransition(R.anim.fade_in,
						// R.anim.fade_out);
						finish();
					}

					new LoginInfo().setInputData(gStrServerInstance,gStrSQLInstance,gStrHandler,gStrEntCode,gStrFyrCode,gStrUserCode,
							gStrLocCode,gStrInterface,gStrDeviceID);
					// }
					// else
					// {
					// Toast.makeText(getApplicationContext(),
					// "Insert Server Name", Toast.LENGTH_LONG).show();
					// }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});*/
		
		btn_Save.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
				if(common.checkInternet()== true)
				{
					try {
						// if(!strServerName.equals(""))
						// {
						 gStrServerInstance = edt_ServerInstance.getText()
								.toString();
						 gStrSQLInstance = edt_SQLInstance.getText()
								.toString();
						 gStrHandler = edt_Handler.getText().toString();
						 gStrEntCode = edt_EntCode.getText().toString();
						 gStrFyrCode = edt_FyrCode.getText().toString();
						
						 gStrLocCode = edt_LocCode.getText().toString();
						 gStrInterface = edt_Interface.getText().toString();
						 gStrDeviceID = getDiviceID();
						if (gStrServerInstance.equals("") && gStrSQLInstance.equals("")
								&& gStrHandler.equals("") && gStrEntCode.equals("")
								&& gStrFyrCode.equals("") && gStrLocCode.equals("")
								&& gStrInterface.equals("")
								&& gStrDeviceID.equals("")) {

							Toast.makeText(getApplicationContext(),
									"Enter All The Feilds", Toast.LENGTH_LONG)
									.show();
						} 
						else 
						{
							
							new ExecuteMethods().execute();
							
						}

						/*new LoginInfo().setInputData(gStrServerInstance,gStrSQLInstance,gStrHandler,gStrEntCode,gStrFyrCode,gStrUserCode,
								gStrLocCode,gStrInterface,gStrDeviceID);*/
						
						//new LoginInfo(Setting_Activity.this).setInputData(gStrServerInstance, gStrSQLInstance, gStrInterface, gStrHandler, gStrEntCode, gStrLocCode, gStrFyrCode, gStrDeviceID);
						// }
						// else
						// {
						// Toast.makeText(getApplicationContext(),
						// "Insert Server Name", Toast.LENGTH_LONG).show();
						// }
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				
				else if (common.checkInternet()== false) 
				{
					Toast.makeText(getApplicationContext(), "Please Check your internet connection", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		btn_cancel.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				// close setting activity here
				// onclik
				finish();
				
			}
		});
		/*
		 * btn_cancel.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent intent = new Intent(Setting_Activity.this,
		 * LoginScreenActivity.class); startActivity(intent); finish(); } });
		 */
	}

	public void insertSettingData() 
	{
		/*
		 * String strServerName = "HP SERVER"; String strWeb_serve_Instance
		 * ="HP"; String strCharacter = "AB"; String strHandler = "Web Service";
		 * String strYear = "2014";
		 */
		
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			gStrDeviceID = telephonyManager.getDeviceId();

			dbManager.open();
		// dbManager.insertDetails(strUser, strPassword);
		// dbManager.insertSettingsDetails(strServerName, strWeb_serve_Instance,
		// strCharacter, strHandler, strYear);
		dbManager.insertSettingsDetails(gStrServerInstance, gStrInterface,
				gStrHandler, gStrEntCode, gStrLocCode, gStrFyrCode, gStrSQLInstance,
				gStrDeviceID);

		/*
		 * luserName.clear(); lpassword.clear();
		 */

		// edt_Password.setText("");

		System.out.println("data Inserted..");
		/*Toast.makeText(getApplicationContext(), "Data Inserted",
				Toast.LENGTH_LONG).show();
*/
		
		/*
		 * if(c.moveToFirst()) { do { strServerName =
		 * c.getString(c.getColumnIndex(DataManager.server_instance));
		 * strWeb_serve_Instance =
		 * c.getString(c.getColumnIndex(DataManager.ws_interface)); strCharacter
		 * = c.getString(c.getColumnIndex(DataManager.api_handler)); strHandler
		 * = c.getString(c.getColumnIndex(DataManager.ent_code)); strYear =
		 * c.getString(c.getColumnIndex(DataManager.fyr_code));
		 * 
		 * //System.out.println("User Name: "+strUsername+"\nPassword: "+strPassword
		 * ) }while(c.moveToNext()); }
		 */

		dbManager.close();
	}
	
	/*public static String getInputText()
	{
		String str1 = edt_UserCode.getText().toString();
		return str1;
	}
	
	public void setLoginInfoData()
	{
		
		
		
		new LoginInfo().setInputData("strServerName");	
		
	}*/
	
	
	

	public String getDiviceID() 
	{
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String diviceID = telephonyManager.getDeviceId();
		return diviceID;

	}

	// Method for setting data to the setting form
	public void setData() {
		try {
			String strServerName = null;
			String strSql_Instance = null;
			String strHandler = null;
			String strEntCode = null;
			String strYear = null;
			String strLoc_Code = null;
			String strInterface = null;
			String strUserCode = null;
			String strDiviceId = null;

			dbManager.open();
			Cursor c = dbManager.settingsSelect();

			if (c.moveToFirst()) {
				do {
					strServerName = c.getString(c
							.getColumnIndex(DataManager.server_instance));
					strSql_Instance = c.getString(c
							.getColumnIndex(DataManager.sql_Instance));
					strHandler = c.getString(c
							.getColumnIndex(DataManager.api_handler));
					strEntCode = c.getString(c
							.getColumnIndex(DataManager.ent_code));
					strYear = c.getString(c
							.getColumnIndex(DataManager.fyr_code));
					strLoc_Code = c.getString(c
							.getColumnIndex(DataManager.loc_code));
					strInterface = c.getString(c
							.getColumnIndex(DataManager.ws_interface));
					
					
					strDiviceId = c.getString(c.getColumnIndex(DataManager.divice_id));
					
					
				} while (c.moveToNext());
				
				/*new LoginInfo().setInputData(gStrServerInstance,gStrSQLInstance,gStrHandler,gStrEntCode,gStrFyrCode,gStrUserCode,
						gStrLocCode,gStrInterface,gStrDeviceID);*/
			}

			edt_ServerInstance.setText(strServerName);
			edt_SQLInstance.setText(strSql_Instance);
			edt_Handler.setText(strHandler);
			edt_EntCode.setText(strEntCode);
			edt_FyrCode.setText(strYear);
			edt_LocCode.setText(strLoc_Code);
			edt_Interface.setText(strInterface);
			

			dbManager.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void validations()
	{
		if(edt_ServerInstance.getText().toString().equals(""))
		{
			edt_ServerInstance.setError("Enter Server Instance");
		}
		else
		{
			edt_ServerInstance.setError(null);
		}
		if(edt_SQLInstance.getText().toString().equals(""))
		{
			edt_SQLInstance.setError("Enter SQL Instance");
		}
		else
		{
			edt_SQLInstance.setError(null);
		}
		if(edt_Handler.getText().toString().equals(""))
		{
			edt_Handler.setError("Enter Hanler");
		}
		else
		{
			edt_Handler.setError(null);
		}
		if(edt_EntCode.getText().toString().equals(""))
		{
			edt_EntCode.setError("Enter Entity Code");
		}
		else
		{
			edt_EntCode.setError(null);
		}
		if(edt_FyrCode.getText().toString().equals(""))
		{
			edt_FyrCode.setError("Enter Year Code");
		}
		else
		{
			edt_FyrCode.setError(null);
		}
		if(edt_LocCode.getText().toString().equals(""))
		{
			edt_LocCode.setError("Enter Location Code");
		}
		else
		{
			edt_LocCode.setError(null);
		}
		if(edt_Interface.getText().toString().equals(""))
		{
			edt_Interface.setError("Enter Interface");
		}
		else
		{
			edt_Interface.setError(null);
		}
		
		
	}
	public class addListenerOnTextChange implements TextWatcher 
	{
		private Context mContext;
		EditText mEdittextview;
		

		public addListenerOnTextChange(Context context, EditText edittextview) 
		{
		    super();
		    this.mContext = context;
		    this.mEdittextview= edittextview;
		}


		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			validations();
			
		}
	}
	
	
	
	public class ExecuteMethods extends AsyncTask<String,String, String>
	{

		@Override
		protected void onPreExecute() 
		{
			// TODO Auto-generated method stub
			dialog.setTitle("Loading....");
		    dialog.setMessage("Please wait.");
		    dialog.setIndeterminate(true);
		    dialog.setCancelable(false);
		    
		    dialog.show();
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) 
		{
			// TODO Auto-generated method stub
			// executing methods
			
			insertSettingData();
			
			
			
			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) 
		{
		//	StrUserName = spinner1.getSelectedItem().toString();
			// TODO Auto-generated method stub
			if(dialog.isShowing())
			{
				dialog.dismiss();
				
				
				Intent i = new Intent(Setting_Activity.this,
						LoginScreenActivity.class);
				
				/*i.putExtra("LaunchActivity", getIntent()
						.getStringExtra("LaunchActivity"));*/
				startActivity(i);
				// overridePendingTransition(R.anim.fade_in,
				// R.anim.fade_out);
				finish();
				
			}
			
		/*	final Intent myIntent = new Intent();
			  myIntent.setClassName(LoginInfo.gStrPackageName, mStrLaunchActivity);
			  myIntent.putExtra("UserId", StrUserName);
			  startActivity(myIntent );
			  finish();*/
			
			super.onPostExecute(result);
		}

	}

}
