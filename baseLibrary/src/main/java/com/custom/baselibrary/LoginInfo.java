package com.custom.baselibrary;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class LoginInfo 
{
	/*private static final LoginInfo instance = new LoginInfo();
	public static LoginInfo Instance() {return instance;}*/
	
	/*public static String gStrServerInstance = "192.168.1.5";
	public static String gStrSQLInstance = "192.168.1.5";
	public static String gStrInterface = "AIMSAPI";
	public static String gStrHandler = "AIMSWS";
	public static String gStrEntCode = "BB";
	public static String gStrLocCode = "BB";
	public static String gStrFyrCode = "2014";
	public static String gStrUserCode = "SUPERVISOR";
	public static String gStrDeviceID = "DEVICE1234";
	public static String gStrLaunchActivity = "";
	public static String gStrPackageName = "";*/
	
	
	
	public static String gStrServerInstance = ""; 
	public static String gStrSQLInstance = "";
	public static String gStrInterface = "";
	public static String gStrHandler = "";
	public static String gStrEntCode = "";
	public static String gStrLocCode = "";
	public static String gStrFyrCode = "";
	public static String gStrUserCode = "";
	public static String gStrDeviceID = "";
	public static String gStrLaunchActivity = "";
	public static String gStrPackageName = "";
	
	static Context context;
	//static TextView textUserCode;
	
	
	public LoginInfo(Context context)
	{
		DataManager dbManager = new DataManager(context);
		
		try {
			/*String strServerName = null;
			String strSql_Instance = null;
			String strHandler = null;
			String strEntCode = null;
			String strYear = null;
			String strLoc_Code = null;
			String strInterface = null;
*/
			dbManager.open();
			Cursor c = dbManager.settingsSelect();

			if (c.moveToFirst()) {
				do {
					gStrServerInstance = c.getString(c
							.getColumnIndex(DataManager.server_instance));
					gStrSQLInstance = c.getString(c
							.getColumnIndex(DataManager.sql_Instance));
					gStrHandler = c.getString(c
							.getColumnIndex(DataManager.api_handler));
					gStrEntCode = c.getString(c
							.getColumnIndex(DataManager.ent_code));
					gStrFyrCode = c.getString(c
							.getColumnIndex(DataManager.fyr_code));
					gStrLocCode = c.getString(c
							.getColumnIndex(DataManager.loc_code));
					gStrInterface = c.getString(c
							.getColumnIndex(DataManager.ws_interface));
							
							gStrDeviceID = c.getString(c.getColumnIndex(DataManager.divice_id));
				} while (c.moveToNext());
				
				Log.i("Login", gStrServerInstance);
				Log.i("Login", gStrSQLInstance);
				Log.i("Login", gStrHandler);
				Log.i("Login", gStrEntCode);
				Log.i("Login", gStrFyrCode);
				Log.i("Login", gStrLocCode);
				Log.i("Login", gStrInterface);
				Log.i("Login", gStrDeviceID);
				
			}

		/*	edtServerInstance.setText(strServerName);
			edt_SQLInstance.setText(strSql_Instance);
			edt_Handler.setText(strHandler);
			edt_EntCode.setText(strEntCode);
			edt_FyrCode.setText(strYear);
			edt_LocCode.setText(strLoc_Code);
			edt_Interface.setText(strInterface);
*/
			dbManager.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*public void getClassContext(Context cnx)
	{
		context = cnx;
	}*/
	/*public void temp()
	{
		//Setting_Activity.setData();
		String str = Setting_Activity.gStrDeviceID;
		//context = new Setting_Activity().getInputText();
		
		
		String str1 =  new Setting_Activity().getInputText();
		
		
		Log.i("test",str1 );
		Setting_Activity S  = new Setting_Activity();
		
		
	}*/
	
	
	/*public void setInputData(String  StrServerInstance, String  StrSQLInstance, String  StrInterface, String  StrHandler, String  StrEntCode,
			String  StrLocCode, String  StrFyrCode, String  StrDeviceID)
	{
		//textUserCode = txtUserCode;
		//String str1 = textUserCode.getText().toString();
		
		
		 gStrServerInstance = StrServerInstance;
		 gStrSQLInstance = StrSQLInstance;
		 gStrInterface = StrInterface;
		 gStrHandler = StrHandler;
		 gStrEntCode = StrEntCode;
		 gStrLocCode = StrLocCode;
		 gStrFyrCode = StrFyrCode;
		 gStrDeviceID = StrDeviceID;
	}
	
	void temp()
	{
		Log.i("test",gStrServerInstance);
		 Log.i("test",gStrSQLInstance);
		Log.i("test",gStrSQLInstance);
		 Log.i("test",gStrInterface);
		 Log.i("test",gStrHandler);
		 Log.i("test",gStrEntCode);
		 Log.i("test",gStrLocCode);
		 Log.i("test",gStrFyrCode);
		// Log.i("test",gStrUserCode);
		 Log.i("test",gStrDeviceID);
	}*/
	 
		
	/*public void getInfo()
	{
		LoginInfo.gStrServerInstance = Setting_Activity.gStrServerInstance;
		LoginInfo.gStrSQLInstance = Setting_Activity.gStrSQLInstance;
		LoginInfo.gStrInterface = Setting_Activity.gStrInterface;
		LoginInfo.gStrHandler = Setting_Activity.gStrHandler;
		LoginInfo.gStrEntCode = Setting_Activity.gStrEntCode;
		LoginInfo.gStrLocCode = Setting_Activity.gStrLocCode;
		LoginInfo.gStrFyrCode = Setting_Activity.gStrFyrCode;
		LoginInfo.gStrUserCode = Setting_Activity.gStrUserCode;
		LoginInfo.gStrDeviceID = Setting_Activity.gStrDeviceID;
	}*/
	
	/*public LoginInfo(Context cnx)
	{
		context = cnx;
	}*/
	

	
	//DataManager dbManager = new DataManager();
	/*public void setData() {
		try {
			String strServerName = null;
			String strSql_Instance = null;
			String strHandler = null;
			String strEntCode = null;
			String strYear = null;
			String strLoc_Code = null;
			String strInterface = null;

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
				} while (c.moveToNext());
			}

			edtServerInstance.setText(strServerName);
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
	}*/
	
	
	
}

