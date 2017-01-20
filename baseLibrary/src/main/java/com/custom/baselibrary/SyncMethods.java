package com.custom.baselibrary;

import org.json.JSONArray;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

public class SyncMethods 
{
	private Context context;
	private Commons commons;
	private DataManager dbManager;
	private JSONArray mJsonArray;
	private JSONArray cJsonArray;
	private JSONArray fJsonArray;
	private JSONArray sJsonArray;
	ProgressDialog dialog; 
	
	
	public SyncMethods(Context context)
	{
		this.context = context;
		commons = new Commons(this.context);
		dbManager = new DataManager(this.context);
		//dialog = new ProgressDialog(this.context);
	}
	
	
	public void syncAllMethods()
	{
		
		try {
			dbManager.open();

			Cursor cursor_mDate = dbManager.selectAllmDate();
			Cursor cursor_cDate = dbManager.selectAllcDate();
			Cursor cursor_fDate = dbManager.selectAllfDate();
			Cursor cursor_sDate = dbManager.selectAllsDate();
			
			String Str_mDate="",Str_cDate="",Str_fDate="",Str_sDate="";
			String Str_mDate_Time=null,Str_cDate_Time=null,Str_fDate_Time=null,Str_sDate_Time=null;
			
			if (cursor_mDate.moveToFirst()) 
			{
				do {
					 Str_mDate =(cursor_mDate.getString(cursor_mDate.getColumnIndex(DataManager.m_date)));
					 if(Str_mDate!=null || Str_cDate!=null || Str_fDate!=null || Str_sDate!=null)
					 {
						 Str_mDate_Time = Str_mDate;
						/* Str_cDate_Time = Str_cDate;
						 Str_fDate_Time = Str_fDate;
						 Str_sDate_Time = Str_sDate;*/
					 }

				} while (cursor_mDate.moveToNext());
				cursor_mDate.close();
			}
				
				if (cursor_cDate.moveToFirst()) 
				{
					do {
						Str_cDate =(cursor_cDate.getString(cursor_cDate.getColumnIndex(DataManager.c_date)));
						 if(Str_cDate!=null)
						 {
							 
							 Str_cDate_Time = Str_cDate;
							 
						 }

					} while (cursor_cDate.moveToNext());
					cursor_cDate.close();
				}
					
					if (cursor_fDate.moveToFirst()) 
					{
						do {
							Str_fDate =(cursor_fDate.getString(cursor_fDate.getColumnIndex(DataManager.f_date)));
							 if(Str_cDate!=null)
							 {
								 
								 Str_fDate_Time = Str_fDate;
								 
							 }

						} while (cursor_fDate.moveToNext());
						cursor_fDate.close();
					}
						if (cursor_sDate.moveToFirst()) 
						{
							do {
								Str_sDate =(cursor_sDate.getString(cursor_sDate.getColumnIndex(DataManager.s_date)));
								 if(Str_cDate!=null)
								 {
									 
									 Str_sDate_Time = Str_sDate;
									 
								 }

							} while (cursor_sDate.moveToNext());
							cursor_sDate.close();
						}
				
				/*Str_cDate =(cursor_mDate.getString(cursor_mDate.getColumnIndex(DataManager.c_date)));
				 Str_fDate =(cursor_mDate.getString(cursor_mDate.getColumnIndex(DataManager.f_date)));
				 Str_sDate =(cursor_mDate.getString(cursor_mDate.getColumnIndex(DataManager.s_date)));*/
			
				//Str_mDate = Str_mDate.toString();
						if(Str_mDate_Time!=null || Str_cDate_Time!=null ||Str_fDate_Time!=null ||Str_sDate_Time!=null)
						{
							mJsonArray = (JSONArray)commons.GetData("GetModels",Str_mDate_Time);
							cJsonArray = (JSONArray) commons.GetData("GetColors",Str_cDate_Time);
							fJsonArray = (JSONArray)commons.GetData("GetFinancer",Str_fDate_Time);
							sJsonArray = (JSONArray)commons.GetData("GetSalesman",Str_sDate_Time);
						}
				/*if (mJsonArray != null && mJsonArray.length() > 0)
				{
				
					dbManager.open();
					dbManager.insertModelDetails(mJsonArray);
					dbManager.close();
				//Toast.makeText(getApplicationContext(), "Sync Models Called", Toast.LENGTH_LONG).show();
				
					Log.i("test", mJsonArray.toString());
				}*/
			
				 if(Str_mDate_Time==null && Str_cDate_Time==null && Str_fDate_Time==null&& Str_sDate_Time==null)
				 {
					mJsonArray = (JSONArray)commons.GetData("GetModels","");
					 cJsonArray = (JSONArray) commons.GetData("GetColors","");
					 fJsonArray = (JSONArray)commons.GetData("GetFinancer","");
					 sJsonArray = (JSONArray)commons.GetData("GetSalesman","");
				 }
			
				/*// mJsonArray = (JSONArray)commons.GetData("GetModels","");
				 cJsonArray = (JSONArray) commons.GetData("GetColors","");
				 fJsonArray = (JSONArray)commons.GetData("GetFinancer","");
				 sJsonArray = (JSONArray)commons.GetData("GetSalesman","");*/
				 
				 
				if (mJsonArray != null && mJsonArray.length() > 0 || cJsonArray != null && cJsonArray.length() > 0 || fJsonArray != null && fJsonArray.length() > 0 || sJsonArray != null && sJsonArray.length() > 0)
				{
					/*dbManager.insertModelDetails(mJsonArray);
					dbManager.insertColorDetails(cJsonArray);
					dbManager.insertFinncerDetails(fJsonArray);
					dbManager.insertSalesManDetails(sJsonArray);*/
					dbManager.insertSyncData(mJsonArray, cJsonArray, fJsonArray, sJsonArray);
				//Toast.makeText(getApplicationContext(), "Sync Models Called", Toast.LENGTH_LONG).show();
					//Log.i("test", mJsonArray.toString());
				}
			dbManager.close();			
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}	
		}
	
	/*public void SyncModels()
	{
		try {
			dbManager.open();

			Cursor cursor_mDate = dbManager.selectAllDate();
			
			String Str_mDate="";
			String Str_mDate_Time=null;
			
			if (cursor_mDate.moveToFirst()) 
			{
				do {
					 Str_mDate =(cursor_mDate.getString(cursor_mDate.getColumnIndex(DataManager.m_date)));
					 
					 if(Str_mDate!=null)
					 {
						 Str_mDate_Time = Str_mDate;
					 }

				} while (cursor_mDate.moveToNext());
				cursor_mDate.close();
			}
			dbManager.close();
			if(Str_mDate_Time!=null)
			{
				//Str_mDate = Str_mDate.toString();
				JSONArray lJsnArray = (JSONArray)commons.GetData("GetModels",Str_mDate_Time);
				if (lJsnArray != null && lJsnArray.length() > 0)
				{
				
					dbManager.open();
					dbManager.insertModelDetails(lJsnArray);
					dbManager.close();
				//Toast.makeText(getApplicationContext(), "Sync Models Called", Toast.LENGTH_LONG).show();
				
					Log.i("test", lJsnArray.toString());
				}
			}
			
				 mJsonArray = (JSONArray)commons.GetData("GetModels","");
				if (mJsonArray != null && mJsonArray.length() > 0)
				{
					dbManager.open();
					dbManager.insertModelDetails(mJsonArray);
					
				//Toast.makeText(getApplicationContext(), "Sync Models Called", Toast.LENGTH_LONG).show();
				
					Log.i("test", mJsonArray.toString());
				}
			dbManager.close();
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void SyncColors()
	{
		try {
			dbManager.open();

			Cursor cursor_cDate = dbManager.selectAllDate();

			String Str_cDate = "";
			String Str_cDate_Time = null;

			if (cursor_cDate.moveToFirst()) 
			{
				do {
					Str_cDate = (cursor_cDate.getString(cursor_cDate.getColumnIndex(DataManager.c_date)));
					if (Str_cDate != null) 
					{
						Str_cDate_Time = Str_cDate;
					}

				} while (cursor_cDate.moveToNext());
				cursor_cDate.close();
			}
			dbManager.close();
			if (Str_cDate_Time != null) 
			{
				Str_cDate = Str_cDate.toString();
				JSONArray lJsnArray = (JSONArray) commons.GetData("GetColors",Str_cDate_Time);
				if (lJsnArray != null && lJsnArray.length() > 0) 
				{
					dbManager.open();
					dbManager.insertColorDetails(lJsnArray);
					dbManager.close();
					// Toast.makeText(getApplicationContext(),
					// "Sync Models Called", Toast.LENGTH_LONG).show();

					//Log.i("test", lJsnArray.toString());
				}
			}
			

			cJsonArray = (JSONArray) commons.GetData("GetColors","");
				if (cJsonArray != null && cJsonArray.length() > 0) 
				{

					dbManager.open();
					dbManager.insertColorDetails(cJsonArray);
					
					// Toast.makeText(getApplicationContext(),
					// "Sync Models Called", Toast.LENGTH_LONG).show();
					// Log.i("test", lJsnArray.toString());
				}
			
			dbManager.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void SyncFinancer()
	{
		try {
			dbManager.open();

			Cursor cursor_fDate = dbManager.selectAllDate();
			
			
			String Str_fDate="";
			String Str_fDate_Time=null;
			
			
			if (cursor_fDate.moveToFirst()) 
			{
				do {
					Str_fDate =(cursor_fDate.getString(cursor_fDate.getColumnIndex(DataManager.f_date)));
					if (Str_fDate != null) 
					{
						Str_fDate_Time = Str_fDate;
					}


				} while (cursor_fDate.moveToNext());
				cursor_fDate.close();
			}
			dbManager.close();
			if(Str_fDate_Time!=null)
			{
				JSONArray lJsnArray = (JSONArray)commons.GetData("GetFinancer",Str_fDate_Time);
				if (lJsnArray != null && lJsnArray.length() > 0)
				{
				
					dbManager.open();
					dbManager.insertFinncerDetails(lJsnArray);
					dbManager.close();
				//Toast.makeText(getApplicationContext(), "Sync Models Called", Toast.LENGTH_LONG).show();
				
					Log.i("test", lJsnArray.toString());
				}
			}
			
			
			fJsonArray = (JSONArray)commons.GetData("GetFinancer","");
				if (fJsonArray != null && fJsonArray.length() > 0)
				{
				
					dbManager.open();
					dbManager.insertFinncerDetails(fJsonArray);
					
				//Toast.makeText(getApplicationContext(), "Sync Models Called", Toast.LENGTH_LONG).show();
				
					Log.i("test", fJsonArray.toString());
				}
			
			dbManager.close();
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public void SyncSalesMan()
	{
		try {
			dbManager.open();

			Cursor cursor_sDate = dbManager.selectAllDate();
			
			String Str_sDate="";
			String Str_sDate_Time=null;
			
			if (cursor_sDate.moveToFirst()) {
				do {
					 Str_sDate =(cursor_sDate.getString(cursor_sDate.getColumnIndex(DataManager.s_date)));
					 if (Str_sDate != null) 
						{
						 Str_sDate_Time = Str_sDate;
						}

				
				} while (cursor_sDate.moveToNext());
				cursor_sDate.close();
			}
			dbManager.close();
			if(Str_sDate_Time!=null)
			{
				
				JSONArray lJsnArray = (JSONArray)commons.GetData("GetSalesman",Str_sDate_Time);
				if (lJsnArray != null && lJsnArray.length() > 0)
				{
				
					dbManager.open();
					dbManager.insertSalesManDetails(lJsnArray);
					dbManager.close();
				//Toast.makeText(getApplicationContext(), "Sync Models Called", Toast.LENGTH_LONG).show();
				
					Log.i("test", lJsnArray.toString());
				}
			}
			
			
			sJsonArray = (JSONArray)commons.GetData("GetSalesman","");
				if (sJsonArray != null && sJsonArray.length() > 0)
				{
				
					dbManager.open();
					dbManager.insertSalesManDetails(sJsonArray);
					
				//Toast.makeText(getApplicationContext(), "Sync Models Called", Toast.LENGTH_LONG).show();
				
					Log.i("test", sJsonArray.toString());
				}
			
			dbManager.close();
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}*/

	


}
