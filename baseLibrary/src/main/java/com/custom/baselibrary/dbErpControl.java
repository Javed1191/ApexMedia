package com.custom.baselibrary;

import android.os.Bundle;
import android.renderscript.Sampler.Value;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Menu;
import android.view.View;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class dbErpControl extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "erpControl.db";

	public static final String eControl_TABLE_NAME = "eControl";
	public static final String eControl_COLUMN_SiteCode = "SiteCode";
	public static final String eControl_COLUMN_StartDate = "StartDate";
	public static final String eControl_COLUMN_EndDate = "EndDate";
	public static final String eControl_COLUMN_MobServerName = "MobServerName";
	public static final String eControl_COLUMN_MobDbName = "MobDbName";
	public static final String eControl_COLUMN_TrnServerName = "TrnServerName";
	public static final String eControl_COLUMN_TrnDbName = "TrnDbName";
	public static final String eControl_COLUMN_AccServerName = "AccServerName";
	public static final String eControl_COLUMN_AccDbName = "AccDbName";
	public static final String eControl_COLUMN_PRServerName = "PRServerName";
	public static final String eControl_COLUMN_PRDbName = "PRDbName";
	public static final String eControl_COLUMN_HRServerName = "HRServerName";
	public static final String eControl_COLUMN_HRDbName = "HRDbName";

	private HashMap hp;
	private Commons common;

	public dbErpControl(Context context) {
		super(context, DATABASE_NAME, null, 1);
		common = new Commons(context);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
        db.execSQL("create table eControl "
				+ "(id integer primary key," +
				"	SiteCode text," +
				"	StartDate text," +
				"	EndDate text," +
				"	MobServerName text," +
				"	MobDbName text," +
				"	TrnServerName text," +
				"	TrnDbName text," +
				"	AccServerName text," +
				"	AccDbName text," +
				"	PRServerName text," +
				"	PRDbName text," +
				"	HRServerName text," +
				"	HRDbName text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS eControl");

		onCreate(db);
	}
	
	public Cursor GetConnSettings(String pStrSiteCode, String pStrDeviceDate) 
	{
		Cursor res = null;
		try 
		{
			//Date lDtmDeviceDate = common.ConvertStringToDate(pStrDeviceDate);
			String lStrCurrDate = common.ConvertDateToString(pStrDeviceDate, "yyyyMMdd");
			SQLiteDatabase db = this.getReadableDatabase();
			res = db.rawQuery("select * from eControl where SiteCode = '" + pStrSiteCode + 
							  "' AND '" + lStrCurrDate + "' >= StartDate AND '"+
							  lStrCurrDate + "' <= EndDate"	, null);
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public boolean insertConnSettings(String pStrSiteCode, String pStrStartDate, String pStrEndDate,
			String pStrMobServerName, String pStrMobDbName, String pStrTrnServerName, 
			String pStrTrnDbName, String pStrAccServerName, String pStrAccDbName, 
			String pStrPRServerName, String pStrPRDbName, String pStrHRServerName, 
			String pStrHRDbName)
	{
		boolean lBlRetVal = false;
		try
		{
			pStrStartDate = common.ConvertSQLDateStringToString(pStrStartDate, "yyyyMMdd");
			pStrEndDate = common.ConvertSQLDateStringToString(pStrEndDate, "yyyyMMdd");
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();
			contentValues.put("SiteCode", pStrSiteCode);
			contentValues.put("StartDate", pStrStartDate);
			contentValues.put("EndDate", pStrEndDate);
			contentValues.put("MobServerName", pStrMobServerName);
			contentValues.put("MobDbName", pStrMobDbName);
			contentValues.put("TrnServerName", pStrTrnServerName);
			contentValues.put("TrnDbName", pStrTrnDbName);
			contentValues.put("AccServerName", pStrAccServerName);
			contentValues.put("AccDbName", pStrAccDbName);
			contentValues.put("PRServerName", pStrPRServerName);
			contentValues.put("PRDbName", pStrPRDbName);
			contentValues.put("HRServerName", pStrHRServerName);
			contentValues.put("HRDbName", pStrHRDbName);
			long lIntRowId = db.insert("eControl", null, contentValues);
			if (lIntRowId > 0)
				lBlRetVal = true;
			else
				lBlRetVal = false;
		}	
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return lBlRetVal;
	}
}
