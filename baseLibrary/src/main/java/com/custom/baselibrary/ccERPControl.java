package com.custom.baselibrary;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class ccERPControl extends ContentProvider
{
	public static String PROVIDER_NAME = ".eControl";
	      
	public static Uri CONTENT_URI = 
	     Uri.parse("content://"+ PROVIDER_NAME + "/econtrol");
	      
	public static final String _ID = "_id";
	public static final String SiteCode = "SiteCode";
	public static final String StartDate = "StartDate";
	public static final String EndDate = "EndDate";
	public static final String MobServerName = "MobServerName";
	public static final String MobDbName = "MobDbName";
	public static final String TrnServerName = "TrnServerName";
	public static final String TrnDbName = "TrnDbName";
	public static final String AccServerName = "AccServerName";
	public static final String AccDbName = "AccDbName";
	public static final String PRServerName = "PRServerName";
	public static final String PRDbName = "PRDbName";
	public static final String HRServerName = "HRServerName";
	public static final String HRDbName = "HRDbName";
	      
	private static final int eControl = 1;
	private static final int eControl_ID = 2;   
	
	private SQLiteDatabase dbERPCtrl;
	private static final String DATABASE_NAME = "erpControl";
	private static final String DATABASE_TABLE = "eControl";
	private static final UriMatcher uriMatcher;
		static
	      {
	         uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	         uriMatcher.addURI(PROVIDER_NAME, "econtrol", eControl);
	         uriMatcher.addURI(PROVIDER_NAME, "econtrol/#", eControl_ID);      
	      }
	      
	  @Override
	   public String getType(Uri uri) 
	   {
		   switch (uriMatcher.match(uri)) 
		   {
				case eControl:
					return "vnd.android.cursor.dir/vnd.baselibrary.econtrol ";
				case eControl_ID:
					return "vnd.android.cursor.item/vnd.baselibrary.econtrol ";
				default:
					throw new IllegalArgumentException("Unsupported URI: "+uri);
		   }
	   }
	   
	   @Override
	   public boolean onCreate() 
	   {
		  /*PROVIDER_NAME = LoginInfo.gStrPackageName + PROVIDER_NAME;
		  CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME + "/econtrol");
	      Context context = getContext();
	      dbErpControl dbeCont = new dbErpControl(context);
	      dbERPCtrl = dbeCont.getWritableDatabase();*/
	      return (dbERPCtrl == null)? false : true;
	   }
	   
	   @Override
	   public Cursor query(Uri uri, String[] projection, String selection,
	         String[] selectionArgs, String sortOrder) 
	   {
		   SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
		   sqlBuilder.setTables(DATABASE_TABLE);
		   if (uriMatcher.match(uri) == eControl_ID)
		   {
			   sqlBuilder.appendWhere(_ID + " = "+uri.getPathSegments().get(1));
		   }
		   if (sortOrder == null  || sortOrder == "")
			   sortOrder = SiteCode;
		   
		   Cursor c = sqlBuilder.query(dbERPCtrl, projection, 
				   selection, selectionArgs, 
				   null, null, sortOrder);
		   c.setNotificationUri(getContext().getContentResolver(), uri);
	      return c;
	   }

	   @Override
	   public Uri insert(Uri uri, ContentValues values) 
	   {
		   long rowId = dbERPCtrl.insert(DATABASE_TABLE, "", values);
		   if (rowId > 0)
		   {
			   Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowId);
			   getContext().getContentResolver().notifyChange(_uri, null);
			   return _uri;
		   }
	      throw new SQLException("Failed to insert row into " + uri);
	   }

	   @Override
	   public int update(Uri uri, ContentValues values, String selection,
	         String[] selectionArgs) 
	   {
		   int count = 0;
		   switch (uriMatcher.match(uri)){
		         case eControl:
		            count = dbERPCtrl.update(
		               DATABASE_TABLE, 
		               values,
		               selection, 
		               selectionArgs);
		            break;
		         case eControl_ID:                
		            count = dbERPCtrl.update(
		               DATABASE_TABLE, 
		               values,
		               _ID + " = " + uri.getPathSegments().get(1) + 
		               (!TextUtils.isEmpty(selection) ? " AND (" + 
		                  selection + ')' : ""), 
		                selectionArgs);
		            break;
		         default: throw new IllegalArgumentException(
		            "Unknown URI " + uri);    
		      }       
		      getContext().getContentResolver().notifyChange(uri, null);
		      return count;
	   }
	   
	   @Override
	   public int delete(Uri arg0, String arg1, String[] arg2) 
	   {
		   	  // arg0 = uri 
		      // arg1 = selection
		      // arg2 = selectionArgs
		      int count=0;
		      switch (uriMatcher.match(arg0)){
		         case eControl:
		            count = dbERPCtrl.delete(
		               DATABASE_TABLE,
		               arg1, 
		               arg2);
		            break;
		         case eControl_ID:
		            String id = arg0.getPathSegments().get(1);
		            count = dbERPCtrl.delete(
		               DATABASE_TABLE,                        
		               _ID + " = " + id + 
		               (!TextUtils.isEmpty(arg1) ? " AND (" + 
		               arg1 + ')' : ""), 
		               arg2);
		            break;
		         default: throw new IllegalArgumentException(
		            "Unknown URI " + arg0);    
		      }       
		      getContext().getContentResolver().notifyChange(arg0, null);
		      return count;      
	   }
}
