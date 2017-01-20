package com.custom.baselibrary;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.SuperToast;

public class LoginScreenActivity extends DashBoardActivity 
{
	// WSPSAPI api = new WSPSAPI();
	private Button btn_Login_Submit,btn_cancel,btn_Save;
	
	private EditText edt_SiteCode;
	private EditText edt_EmpCode, edt_Password;
	
	// private TextView txt_ForgotPwd;

	private String mStrLaunchActivity = "";
	private dbErpControl dbSQLite;
	private String mStrPrefenceName = "DefaultSites";
	private String mStrDefaultSiteKey = "DefaultSiteCode";
	private String mStrDefaultSiteCode = "";
	private String mStrDefaultUserKey = "DefaultUserCode";
	private String mStrDefaultUserCode = "";
	private TextView mtxt_LoginStatus;
	private Spinner spinner1;
	private List<String> luserName,lpassword;
	private List<String>DbluserName,Dblpassword;
	private DataManager dbManager;
	static int position;
	static boolean isData;
	ProgressDialog dialog; 
	String StrUserName;
	static String str_date_time=null;
	Cursor mCursor=null;
	SyncMethods synMethods;
	
	
	//private EditText edtServerInstance,edt_SQLInstance,edt_EntCode,edt_Handler,edt_FyrCode,edt_LocCode,edt_Interface;
	//private CustomText txt_Setting;
	private Button btn_setting,btn_close;
	
	Commons commons;
	Setting_Activity setting;
	
	
	
	/*SharedPreferences  pref;
	Editor editor;*/

	// flag for Internet connection status
	Boolean isInternetPresent = false, mBlIsLoginSuccessful = false;
	// Connection detector class
	
	// public AppConfig CurrApp;
	// validating

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_new_login_screen_updated);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		 
		dbManager = new DataManager(LoginScreenActivity.this);
		commons = new Commons(LoginScreenActivity.this);
		dialog= new ProgressDialog(LoginScreenActivity.this);
		 synMethods = new SyncMethods(LoginScreenActivity.this);
		 
		// btn_close = (Button) findViewById(R.id.btn_close);
		
	
	   /* LoginInfo abc = new LoginInfo();
	    abc.getInfo();*/
		
		//dbSQLite = new dbErpControl(LoginScreenActivity.this);
	    
	    try {
			/*LoginInfo l = new LoginInfo();
			l.temp();*/
	    	
	    	 //new Setting_Activity(getApplicationContext()).setLoginInfoData();
	    
	    	//setData();
	    	
	    	new LoginInfo(LoginScreenActivity.this);

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		
		DbluserName = new ArrayList<String>();
		Dblpassword = new ArrayList<String>();
		/*pref = getApplicationContext().getSharedPreferences("UserInfo", MODE_PRIVATE);
		 editor = pref.edit();*/
		JSONObject lJsn = new JSONObject();
		try {
			lJsn = AIMSAPI.Instance().GetData("HelloWorld");	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		//txt_Setting=(CustomText)findViewById(R.id.txt_Setting);
		btn_setting = (Button) findViewById(R.id.btn_setting);
		
		
		
		new UserData().execute();
		
		
		/*LoginInfo  l = new LoginInfo();
		l.setData();*/
		
		/*edt_serverName = (EditText) findViewById(R.id.edt_serverName);
		edt_webserviceIns = (EditText) findViewById(R.id.edt_webserviceIns);
		edt_char =(EditText) findViewById(R.id.edt_char);
		edt_handler = (EditText) findViewById(R.id.edt_handler);
		edt_year = (EditText) findViewById(R.id.edt_year);
		*/
		//btn_Save = (Button) findViewById(R.id.btn_Save);
		//btn_cancel = (Button) findViewById(R.id.btn_cancel);
		
		/*luserName = new ArrayList<String>();
		lpassword = new ArrayList<String>();*/
		
		
		/*luserName.add("Vishal");
		luserName.add("Gourav");
		luserName.add("Monish");
		luserName.add("Vijay");
		luserName.add("Javed");
		
		lpassword.add("Vishal");
		lpassword.add("Gourav");
		lpassword.add("Monish");
		lpassword.add("Vijay");
		lpassword.add("Javed");
		*/
		
		
		
		
		/* ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,luserName);
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner1.setAdapter(adapter);*/
		
		//if(isData)
		//{
			
			 /*boolean flag = commons.checkInternet();
			try {
				if(flag==true)
				{
					//Deleting previous data from the user table
					SyncEntities();
					SyncUsers();
					getUserData();
					
					 SuperToast superToast = new SuperToast(getApplicationContext());  
					 superToast.setDuration(SuperToast.Duration.SHORT); 
					 superToast.setText("You Are Online");  
					 superToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
					 superToast.setIcon(R.drawable.icon_green_enter,SuperToast.IconPosition.LEFT);
					 superToast.show();  
					// Inserting User Name and password Into the User table
					// insertData();
				}
				else if (flag==false) 
				{
					 SuperToast superToast = new SuperToast(getApplicationContext());  
					 superToast.setDuration(SuperToast.Duration.SHORT); 
					 superToast.setText("You Are Offline");  
					 superToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
					 superToast.setIcon(R.drawable.icon_red_exit,SuperToast.IconPosition.LEFT);
					 superToast.show();  
					getUserData();
				}
			} catch (Exception e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	 */
		//}
        
        // Cursor c = dbManager.settingsSelect();
         btn_setting.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) {
				
				  // TODO Auto-generated method stub
				/* sdialog = new Dialog(LoginScreenActivity.this);
				 sdialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.background1));
				sdialog.setContentView(R.layout.activity_setting);
				sdialog.getWindow().setLayout(575, 850);
				sdialog.setTitle("SETTING SERVER NAME AND INSTANCES");
				sdialog.show();*/
				
				
				//EditText edt_serverName;
				Intent i = new Intent(LoginScreenActivity.this,Setting_Activity.class);
				//i.putExtra("LaunchActivity",getIntent().getStringExtra("LaunchActivity"));
				startActivity(i);
				finish();
				//overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				//finish();
				
				
				 /**we initializing this objects as sdialog.findViewById
				Because the layout activity_setting set here in dialog box
				if we directly initialize this objects like other objects (edt_serverName = (EditText)findViewById(R.id.edt_serverName);)
				it will through an error NULL pointer Exception */
				
				
				/*edtServerInstance = (EditText) sdialog.findViewById(R.id.edtServerInstance);
				edt_SQLInstance = (EditText)sdialog.findViewById(R.id.edt_SQLInstance);
				edt_EntCode =(EditText)sdialog.findViewById(R.id.edt_EntCode);
				edt_Handler = (EditText)sdialog.findViewById(R.id.edt_Handler);
				edt_FyrCode = (EditText)sdialog.findViewById(R.id.edt_FyrCode);
				edt_LocCode =(EditText)sdialog.findViewById(R.id.edt_LocCode);
				edt_Interface =(EditText)sdialog.findViewById(R.id.edt_Interface);
				btn_Save = (Button)sdialog.findViewById(R.id.btn_Save);*/			
				//final String strServerName = edt_serverName.getText().toString();
				
				
				/* settings save button onclick listner
				to save setting data into the data base*/
				
				/*btn_Save.setOnClickListener(new OnClickListener() 
				{
					
					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						
						try {
							//if(!strServerName.equals(""))
							//{
								//insertSettingData();
								
								Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_LONG).show();
								
							//}
							//else
							//{
								//Toast.makeText(getApplicationContext(), "Insert Server Name", Toast.LENGTH_LONG).show();
							//}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});	*/
				// get the Refferences of views
			}
		});
		
	/*mStrLaunchActivity = getIntent().getStringExtra("LaunchActivity");
		LoginInfo.gStrLaunchActivity = mStrLaunchActivity;
		LoginInfo.gStrPackageName = mStrLaunchActivity.substring(0,mStrLaunchActivity.lastIndexOf("."));*/
	
		edt_SiteCode = (EditText) findViewById(R.id.edt_SiteCode);
	//	edt_EmpCode = (EditText) findViewById(R.id.edt_EmpCode);
		edt_Password = (EditText) findViewById(R.id.edt_Password);
		mtxt_LoginStatus = (TextView) findViewById(R.id.txt_LoginStatus);
		
		final SharedPreferences lSpDefSite = getSharedPreferences(mStrPrefenceName, LoginScreenActivity.MODE_WORLD_READABLE);;
		if (lSpDefSite.contains(mStrDefaultSiteKey)) {
			mStrDefaultSiteCode = lSpDefSite.getString(mStrDefaultSiteKey, "");
			edt_SiteCode.setText(mStrDefaultSiteCode);
			edt_SiteCode.setEnabled(false);
			edt_EmpCode.requestFocus();
		}
		if (lSpDefSite.contains(mStrDefaultUserKey)) {
			mStrDefaultUserCode = lSpDefSite.getString(mStrDefaultUserKey, "");
			edt_EmpCode.setText(mStrDefaultUserCode);
			edt_Password.requestFocus();
		}

		btn_Login_Submit = (Button) findViewById(R.id.btn_Login_Submit);
		// TextView txtForgotPwd = (TextView) findViewById(R.id.txt_ForgotPwd);

		//common = new Commons(LoginScreenActivity.this);
		btn_Login_Submit.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				/*
				showProgressDialog(getApplicationContext(), "Loding.....", ProgressDialog.STYLE_SPINNER, true);
				closeProgressDialog();*/
				
				//btn_Login_Submit.setEnabled(false);
				//btn_Login_Submit.setBackgroundResource(com.custom.baselib rary.R.color.CLR_INVISIBLE);
				
				
				/*if (common.checkInternet()) 
				{
					// Checking Internet Connection If It is Available
					showAlertDialog(LoginScreenActivity.this, "Internet Connection",
							"You have internet connection", true);
					try {
						int i = 0;
						final String lStrSiteCode = edt_SiteCode.getText()
								.toString();
						if (lStrSiteCode.length() != 5) {
							edt_SiteCode.setError("Enter Site Code");
							i = 1;
						}
						final String lStrUserCode = edt_EmpCode.getText()
								.toString();
						if (!isValid(lStrUserCode)) {
							edt_EmpCode.setError("Enter Employee Code");
							i = 1;
						}
						final String lStrPassword = edt_Password.getText()
								.toString();
						if (!isValid(lStrPassword)) {
							edt_Password.setError("Enter Password");
							i = 1;
						}
						// TODO Auto-generated method stub
						if (i == 0) {
							new LaunchStartActivity().execute();
							
						}
						// calling user login method here
						
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					Toast.makeText(LoginScreenActivity.this,
							"NO Internet Connection Found", Toast.LENGTH_LONG)
							.show();
					
					try {
						//btn_Login_Submit.setBackgroundResource(com.custom.baselibrary.R.color.CLR_VISIBLE);
						common.showAlertDialog(LoginScreenActivity.this, "No Internet Connection",
								"You don't have internet connection.", false);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//btn_Login_Submit.setEnabled(true);
				//btn_Login_Submit.setBackgroundResource(com.custom.baselibrary.R.color.CLR_VISIBLE);
				 */
				try {
					userLogin();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btn_close.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						LoginScreenActivity.this);
				// Setting Dialog Title
				alertDialog.setTitle("Enquiry Application");
				// Setting Dialog Message
				alertDialog
						.setMessage("Are you sure you want to leave the application?");
				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.ic_launcher);
				// Setting Positive "Yes" Button
				alertDialog.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								finish();
							}
						});
				// Setting Negative "NO" Button
				alertDialog.setNegativeButton("NO",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// Write your code here to invoke NO event
								dialog.cancel();
							}
						});
				// Showing Alert Message
				alertDialog.show();
				
				
				
			}
		});
		
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() 
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) 
			{
				// TODO Auto-generated method stub
				position = arg2;
				System.out.println(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	
	private boolean isValid(String pass) {
		if (pass != null && pass.length() >= 3 && pass.length() <= 14) {
			return true;
		}
		return false;
	}
	
	/**
	 * Function to display simple Alert Dialog
	 * @param context - application context
	 * @param title - alert dialog title
	 * @param message - alert message
	 * @param status - success/failure (used to set icon)
	 * */
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);
		
		// Setting alert dialog icon
		alertDialog.setIcon((status) ? R.drawable.bullet : R.drawable.bullet);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which)
			{
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
	
	
/*	private class LaunchStartActivity extends AsyncTask<Void, Void, Void> {
		private ProgressDialog dialog1;
		String status;
		String msg;
		private String lStrLoginStatus = "";
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			dialog1 = new ProgressDialog(LoginScreenActivity.this);
			dialog1.setMessage("Logging into Application....");
			dialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog1.setCancelable(false);
			dialog1.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				String lStrUserCode = edt_EmpCode.getText().toString();
				String lStrPassword = edt_Password.getText().toString();
				String lStrSiteCode = edt_SiteCode.getText().toString();
				JSONObject jsonObj = null;
				String lStrCurrDate = common.ConvertDateToString(
						common.GetDeviceDateString(), "yyyyMMdd");
				// Cursor lCurRow =
				// dbSQLite.GetConnSettings(lStrSiteCode,
				// common.GetDeviceDateString());
				// Uri allRecs =
				// Uri.parse("content://"+LoginInfo.PackageName+".eControl"+".eControl");
				String[] lStrProjection = {
						ccERPControl.SiteCode,
						ccERPControl.MobServerName,
						ccERPControl.MobDbName,
						ccERPControl.TrnServerName,
						ccERPControl.TrnDbName,
						ccERPControl.AccServerName,
						ccERPControl.AccDbName,
						ccERPControl.PRServerName,
						ccERPControl.PRDbName,
						ccERPControl.HRServerName,
						ccERPControl.HRDbName };
				String lStrSelection = "SiteCode = '"
						+ lStrSiteCode + "' AND '" + lStrCurrDate
						+ "' >= StartDate AND '" + lStrCurrDate
						+ "' <= EndDate";
				Cursor lCurRow = managedQuery(
						ccERPControl.CONTENT_URI, lStrProjection,
						lStrSelection, null, "SiteCode");
				if (lCurRow != null && lCurRow.getCount() > 0) {
					jsonObj = new JSONObject();
					if (lCurRow.moveToFirst()) {
						for (int j = 0; j < lCurRow
								.getColumnCount(); j++) {
							jsonObj.put(lCurRow.getColumnName(j),
									lCurRow.getString(j));
						}
					}
				} else {
					jsonObj = common
							.GetSiteConnectionDetails(lStrSiteCode);
					if (jsonObj != null) {
						ContentValues cVal = new ContentValues();
						String lStrStartDate = common.ConvertSQLDateStringToString(
								jsonObj.getString("StartDate"),
								"yyyyMMdd");
						String lStrEndDate = common.ConvertSQLDateStringToString(
								jsonObj.getString("EndDate"),
								"yyyyMMdd");
						cVal.put(ccERPControl.SiteCode,
								lStrSiteCode);
						cVal.put(ccERPControl.StartDate,
								lStrStartDate);
						cVal.put(ccERPControl.EndDate, lStrEndDate);
						cVal.put(ccERPControl.MobServerName,
								jsonObj.getString("MobServerName"));
						cVal.put(ccERPControl.MobDbName,
								jsonObj.getString("MobDbName"));
						cVal.put(ccERPControl.TrnServerName,
								jsonObj.getString("TrnServerName"));
						cVal.put(ccERPControl.TrnDbName,
								jsonObj.getString("TrnDbName"));
						cVal.put(ccERPControl.AccServerName,
								jsonObj.getString("AccServerName"));
						cVal.put(ccERPControl.AccDbName,
								jsonObj.getString("AccDbName"));
						cVal.put(ccERPControl.PRServerName,
								jsonObj.getString("PRServerName"));
						cVal.put(ccERPControl.PRDbName,
								jsonObj.getString("PRDbName"));
						cVal.put(ccERPControl.HRServerName,
								jsonObj.getString("HRServerName"));
						cVal.put(ccERPControl.HRDbName,
								jsonObj.getString("HRDbName"));
						Uri uri = getContentResolver().insert(
								ccERPControl.CONTENT_URI, cVal);

						
						 * dbSQLite.insertConnSettings(
						 * lStrSiteCode,
						 * jsonObj.getString("StartDate"),
						 * jsonObj.getString("EndDate"),
						 * jsonObj.getString("MobServerName"),
						 * jsonObj.getString("MobDbName"),
						 * jsonObj.getString("TrnServerName"),
						 * jsonObj.getString("TrnDbName"),
						 * jsonObj.getString("AccServerName"),
						 * jsonObj.getString("AccDbName"),
						 * jsonObj.getString("PRServerName"),
						 * jsonObj.getString("PRDbName"),
						 * jsonObj.getString("HRServerName"),
						 * jsonObj.getString("HRDbName"));
						 
					}
				}
				if (jsonObj != null) {
					AIMSAPI.JsonConnObj = jsonObj;
					String[] lStrArr = new String[2];
					lStrArr[0] = "pStrUserCode";
					lStrArr[1] = "pStrPassword";
					Object[] lObjArr = new Object[2];
					lObjArr[0] = lStrUserCode;
					lObjArr[1] = common.EntryptPassword(lStrPassword);
				
					JSONObject jsnLogin = AIMSAPI.Instance().GetData("IsLoginSuccessful", lStrArr, lObjArr);
					String lStrStatus = jsnLogin.getString("Status");
					if (lStrStatus.equalsIgnoreCase("Success"))
					{
						mBlIsLoginSuccessful = true;
					}
					else
					{
						mBlIsLoginSuccessful = false;
						lStrLoginStatus = jsnLogin.getString("ErrorMessage");
				    }
					//mBlIsLoginSuccessful = common
					//		.LoginUser(WSPSAPI.Instance(),
					//				lStrUserCode, lStrPassword);
					if (mBlIsLoginSuccessful) {
						TelephonyManager teleMan = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
						String lStrDeviceId = teleMan.getDeviceId();
						if (lStrDeviceId == null)
							lStrDeviceId = "";
						final SharedPreferences lSpDefSite = getSharedPreferences(mStrPrefenceName, LoginScreenActivity.MODE_WORLD_READABLE);
						Editor lSpEditor = lSpDefSite.edit();
						lSpEditor.putString(mStrDefaultUserKey,
								lStrUserCode);
						if (!lSpDefSite
								.contains(mStrDefaultSiteKey))
							lSpEditor.putString(mStrDefaultSiteKey,
									lStrSiteCode);
						lSpEditor.commit();
						jsonObj.put("SiteCode", lStrSiteCode);
						jsonObj.put("DeviceID", lStrDeviceId);
						jsonObj.put("UserCode", lStrUserCode);
						AIMSAPI.JsonConnObj = jsonObj;
						LoginInfo.gStrEntCode = lStrSiteCode;
						LoginInfo.gStrUserCode = lStrUserCode;
						LoginInfo.gStrDeviceID = lStrDeviceId;
						
						dbAssyData lAssyData = new dbAssyData(LoginScreenActivity.this);
						lAssyData.SyncAssyLines();
						lAssyData.SyncAssyStations();
						lAssyData.SyncAssyMachines();
						
						final Intent i1 = new Intent();
						i1.setClassName(LoginInfo.gStrPackageName, mStrLaunchActivity);
						startActivity(i1);
					}
					else
						mtxt_LoginStatus.setVisibility();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {
			
			dialog1.dismiss();
			dialog1 = null;
			if (mBlIsLoginSuccessful)
			{
				finish();
			}
			else
			{
				//Toast.makeText(_context, lStrMsg, Toast.LENGTH_LONG).show();
				try {
					common.showAlertDialog(LoginScreenActivity.this, "Login Failed", lStrLoginStatus, false);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//LoginScreenActivity lLogAct = (LoginScreenActivity)_context;
				//edt_Password.setError(lStrLoginStatus);
			}
			
		}
	}*/
	public void onBackPressed() 
	{
		
		// Display alert message when back button has been pressed
		backButtonHandler();
		return;
	}

	public void backButtonHandler() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				LoginScreenActivity.this);
		// Setting Dialog Title
		alertDialog.setTitle("Enquiry Application");
		// Setting Dialog Message
		alertDialog
				.setMessage("Are you sure you want to leave the application?");
		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.ic_launcher);
		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to invoke NO event
						dialog.cancel();
					}
				});
		// Showing Alert Message
		alertDialog.show();
	}
	
	/*public void insertData()
	{
		
		
		isData =true;
		dbManager.open();
		//dbManager.insertDetails(strUser, strPassword);
	//	dbManager.insertUserDetails(luserName, lpassword,isData);
		
		luserName.clear();
		lpassword.clear();
		dbManager.close();
		
		//edt_Password.setText("");
		
		System.out.println("data Inserted..");
		Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_LONG).show();
		
		
		//editor.putBoolean("Is_data", true);
		editor.putInt("Is_data", 1);
		editor.commit();

	}*/
	
	public void getUserData()
    {
    	 try {
 			String StrUser_name = ""; String strPassword = "";
 			dbManager.open();
 			Cursor c = dbManager.selectAllUser();
 			
 			if(c.moveToFirst())
 			{
 				do
 				{
 					/*strUsername = c.getString(c.getColumnIndex(DataManager.user_code));
 					strPassword = c.getString(c.getColumnIndex(DataManager.password));*/
 					
 					StrUser_name = c.getString(1);
 					strPassword = c.getString(2);
 					
 					//System.out.println("User Name: "+strUsername+"\nPassword: "+strPassword);
 					DbluserName.add(StrUser_name);
	 					Dblpassword.add(strPassword);
 					
 					/*if(DbluserName.contains(strUsername) && Dblpassword.contains(strPassword))
 					{
 						
 					}
 					else
 					{
 						DbluserName.add(strUsername);
 	 					Dblpassword.add(strPassword);
 					}	*/
 				}while(c.moveToNext());
 			}
 			//System.out.println("Data Base List:" +DbluserName);
 			//System.out.println("Data Base List:" +Dblpassword);
 			 /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.row,DbluserName);
 			adapter.setDropDownViewResource(R.layout.row);
 			spinner1.setAdapter(adapter);*/
 			
 			spinner1.setAdapter(new MyCustomAdapter(LoginScreenActivity.this, R.layout.user_row, DbluserName));
 			
 			dbManager.close();
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    }
	
	// check is data available in database or not
	/*public boolean isDataAvailable()
    {
		String strIs_data;
			boolean flag =true;
		
    	 try {
 			
 			dbManager.open();
 			Cursor c = dbManager.selectAllUser();
 			
 			if(c.moveToFirst())
 			{
 				do
 				{
 					strIs_data = c.getString(c.getColumnIndex(DataManager.flag));
 					
 					
 					if (strIs_data.equals("1")) 
 					{
						flag=false;
					}
 					
 					System.out.println("Is Data Available:" +strIs_data);
 				}while(c.moveToNext());
 			}
 			
 			
 			dbManager.close();
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
		;
		return flag;
    }*/
	
	public void userLogin()
	{
		String strPassword = edt_Password.getText().toString();
		String strUser = spinner1.getSelectedItem().toString();
		
		String pass = Dblpassword.get(position);
		
		//System.out.println("Edit Text:"+strPassword);
		//System.out.println(pass);
		//System.out.println(position);
		if(strPassword.equals(pass))
		{
			// Showing progress bar
			 SuperToast superToast = new SuperToast(getApplicationContext());  
			 superToast.setDuration(SuperToast.Duration.SHORT); 
			 superToast.setText("Login Succsefully...");  
			 superToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
			// superToast.setIcon(R.drawable.recyclebin_empty,SuperToast.IconPosition.LEFT);
			 superToast.setBackground(R.drawable.background_kitkat_orange);
			 superToast.show();  
			 
			edt_Password.setError(null);
			
				// calling insertDataIntoModel method
				/*try {
					insertDataIntoModel();
					//calling insertDataIntoColor method
					insertDataIntoColor();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			if(commons.checkInternet()==true)
			{
				/*dialog.setTitle("Loading....");
			    dialog.setMessage("Please wait.");
			    dialog.setIndeterminate(true);
			    dialog.setCancelable(false);
			    dialog.show();

			    long delayInMillis = 10000;
			    Timer timer = new Timer();
			    timer.schedule(new TimerTask() 
			    {
			        @Override
			        public void run() {
			            dialog.dismiss();
			        }
			    }, delayInMillis);*/
				try 
				{
					
					/*ExecuteMethods executemethod = new ExecuteMethods();
					executemethod.execute();*/
					
					// executing async task class for calling modelsync and colorsync methods 
					
					dbManager.open();
					mCursor = dbManager.selectAllColors();
					
					if (mCursor.moveToFirst()) 
					{
						do {
							 String str_model_name =(mCursor.getString(mCursor.getColumnIndex(DataManager.c_color_name)));
							 
							 if(str_model_name!=null)
							 {
								System.out.print(str_model_name);
							 }

						} while (mCursor.moveToNext());
						
					}
					
					if(mCursor.getCount()==0)
					{
						ExecuteMethods executemethod = new ExecuteMethods();
						executemethod.execute();	
					}
		
					
					
					else if(mCursor.getCount()!=0)
					{
						StrUserName = spinner1.getSelectedItem().toString();
						  
						  Intent myIntent = new Intent(this,Class.forName("com.eMsysSolutionsPvtLtd.enquireapp.MenuScreenActivity"));
						  myIntent.putExtra("UserId", StrUserName);
						  startActivity(myIntent );
						  finish();
					}
					dbManager.close();	
					mCursor.close();
					
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(commons.checkInternet()==false)
			{
				
				
				try {
						StrUserName = spinner1.getSelectedItem().toString();
						Intent loginIntent = new Intent(this,Class.forName("com.eMsysSolutionsPvtLtd.enquireapp.MenuScreenActivity"));
						loginIntent.putExtra("UserId", StrUserName);
						startActivity(loginIntent );
						finish();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			     
			}
			
			/*Intent mIntent = getPackageManager().getLaunchIntentForPackage(
						                "com.eMsysSolutionsPvtLtd.enquireapp.EnquiryActivity");
						        if (mIntent != null) 
						        {
						            try 
						            {
						            	startActivity(mIntent);
						            } catch (ActivityNotFoundException err) 
						            {
						                
						            }
						        }	*/
			
			
		}
		else
		{
			 SuperToast superToast = new SuperToast(getApplicationContext());  
			 superToast.setDuration(SuperToast.Duration.SHORT); 
			 superToast.setText("Please Enter Correct Password");  
			 superToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
			// superToast.setIcon(R.drawable.recyclebin_empty,SuperToast.IconPosition.LEFT);
			 superToast.setBackground(R.drawable.background_kitkat_red);
			 superToast.show();  
			
			edt_Password.requestFocus();
			edt_Password.setError("Please Enter Corrrect Passward");
			
		}

	}
	
	
	
	public void SyncUsers()
	{
		try {
		//	JSONObject lJsnArray = (JSONObject)common.GetData("GetUsers");
			JSONArray lJsnArray = (JSONArray)commons.GetData("GetUsers");
			if (lJsnArray != null && lJsnArray.length() > 0)
			{
				//Log.i("test", lJsnArray.toString());
				dbManager.open();
				dbManager.insertUserDetails(lJsnArray);
				dbManager.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void SyncEntities()
	{
		try {

				JSONArray lJsnArray = (JSONArray) commons.GetData("GetEntityDetails","pStrEntCode",LoginInfo.gStrEntCode);
				if (lJsnArray != null && lJsnArray.length() > 0) 
				{
					// Toast.makeText(getApplicationContext(),
					// "Sync Models Called", Toast.LENGTH_LONG).show();
					dbManager.open();
					dbManager.insertEntityDetails(lJsnArray);
					
					//Log.i("test", lJsnArray.toString());
					
				}
				dbManager.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// Custom array adapter class for custom list....
		public class MyCustomAdapter extends ArrayAdapter<String>
		{

			  public MyCustomAdapter(Context context, int textViewResourceId,
			    List<String> list_model_name) {
			   super(context, textViewResourceId, list_model_name);
			   // TODO Auto-generated constructor stub
			  }

			  @Override
			  public View getDropDownView(int position, View convertView,
			    ViewGroup parent) {
			   // TODO Auto-generated method stub
			   return getCustomView(position, convertView, parent);
			  }

			  @Override
			  public View getView(int position, View convertView, ViewGroup parent) {
			   // TODO Auto-generated method stub
			   return getCustomView(position, convertView, parent);
			  }

			  public View getCustomView(int position, View convertView, ViewGroup parent) {
			   // TODO Auto-generated method stub
			   //return super.getView(position, convertView, parent);

			   LayoutInflater inflater=getLayoutInflater();
			   View row=inflater.inflate(R.layout.user_row, parent, false);
			   TextView label=(TextView)row.findViewById(R.id.weekofday);
			   
			   label.setText(DbluserName.get(position));
			   return row;
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
				synMethods.syncAllMethods();
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) 
			{
				StrUserName = spinner1.getSelectedItem().toString();
				// TODO Auto-generated method stub
				if(dialog.isShowing())
				{
					dialog.dismiss();
				}
				
				/*final Intent intent = new Intent();
				intent.setClassName(LoginInfo.gStrPackageName, mStrLaunchActivity);*/
				
				try {
					Intent myIntent = new Intent(getApplicationContext(),Class.forName("com.eMsysSolutionsPvtLtd.enquireapp.MenuScreenActivity"));
					  myIntent.putExtra("UserId", StrUserName);
					  startActivity(myIntent );
					  finish();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
				super.onPostExecute(result);
			}
 
		}
		
		class UserData extends AsyncTask<String, String, String>
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
				
				boolean flag = commons.checkInternet();
				try {
					if(flag==true)
					{
						//Deleting previous data from the user table
						SyncEntities();
						SyncUsers();

						runOnUiThread(new Runnable() 
						{
							
							@Override
							public void run() 
							{
								// TODO Auto-generated method stub
								SuperToast superToast = new SuperToast(getApplicationContext());  
								 superToast.setDuration(SuperToast.Duration.SHORT); 
								 superToast.setText("You Are Online");  
								 superToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
								 superToast.setIcon(R.drawable.bullet,SuperToast.IconPosition.LEFT);
								 superToast.show();  
								
							}
						});
						 
						// Inserting User Name and password Into the User table
						// insertData();
					}
					else if (flag==false) 
					{
						runOnUiThread(new Runnable() 
						{
							
							@Override
							public void run() 
							{
								// TODO Auto-generated method stub
								 SuperToast superToast = new SuperToast(getApplicationContext());  
								 superToast.setDuration(SuperToast.Duration.SHORT); 
								 superToast.setText("You Are Offline");  
								 superToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
								 superToast.setIcon(R.drawable.bullet,SuperToast.IconPosition.LEFT);
								 superToast.show();
								// getUserData();
								
							}
						});
						 
						
					}
				} catch (Exception e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	 
				return null;
			}

			@Override
			protected void onPostExecute(String result) 
			{
				// TODO Auto-generated method stub
				
				getUserData();
				if(dialog.isShowing())
				{
					dialog.dismiss();
				}
				super.onPostExecute(result);
			}

			
			
			
		}
		  
		
}
