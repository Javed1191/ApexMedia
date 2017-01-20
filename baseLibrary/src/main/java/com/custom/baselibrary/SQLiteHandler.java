package com.custom.baselibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class SQLiteHandler extends SQLiteOpenHelper {
 
    // for our logs
    public static final String TAG = "SQLiteHandler.java";
 
    // database version
    private static final int DATABASE_VERSION = 5;
 
    // database name
    protected static final String DATABASE_NAME = "AutoTextSample";
 
    // table details
    public String tableName = "locations";
    public String fieldObjectId = "id";
    public String fieldObjectName = "name";
 
    // constructor
    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // creating table
    @Override
    public void onCreate(SQLiteDatabase db) 
    {
        String sql = "";
 
        sql += "CREATE TABLE " + tableName;
        sql += " ( ";
        sql += fieldObjectId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += fieldObjectName + " TEXT ";
        sql += " ) ";
 
        db.execSQL(sql);
 
    }
 
     
    // When upgrading the database, it will drop the current table and recreate.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
        String sql = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(sql);
 
        onCreate(db);
    }
 
     
    // create new record
    // @param myObj contains details to be added as single row.
    public boolean create(AdapterObject myObj) {
 
        boolean createSuccessful = false;
 
        if(!checkIfExists(myObj.mObjName)){
                     
            SQLiteDatabase db = this.getWritableDatabase();
             
            ContentValues values = new ContentValues();
            values.put(fieldObjectName, myObj.mObjName);
            createSuccessful = db.insert(tableName, null, values) > 0;
             
            db.close();
             
            if(createSuccessful){
                Log.e(TAG, myObj.mObjName + " created.");
            }
        }
         
        return createSuccessful;
    }
     
    // check if a record exists so it won't insert the next time you run this code
    public boolean checkIfExists(String objectName){
         
        boolean recordExists = false;
                 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + fieldObjectId + " FROM " + tableName + " WHERE " + fieldObjectName + " = '" + objectName + "'", null);
         
        if(cursor!=null) {
             
            if(cursor.getCount()>0) {
                recordExists = true;
            }
        }
 
        cursor.close();
        db.close();
         
        return recordExists;
    }
 
    // Read records related to the search term
    public AdapterObject[] read(String searchTerm) {
 
        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldObjectName + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + fieldObjectId + " DESC";
        sql += " LIMIT 0,5";
 
        SQLiteDatabase db = this.getWritableDatabase();
 
        // execute the query
        Cursor cursor = db.rawQuery(sql, null);
 
        int recCount = cursor.getCount();
         
        AdapterObject[] ObjectItemData = new AdapterObject[recCount];
        int x = 0;
         
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
 
            	//String objectCode = cursor.getString(cursor.getColumnIndex(fieldObjectId));
                String objectName = cursor.getString(cursor.getColumnIndex(fieldObjectName));
                Log.e(TAG, "objectName: " + objectName);
                 
                //AdapterObject myObject = new AdapterObject(objectCode, objectName);
                AdapterObject myObject = new AdapterObject(objectName);
 
                ObjectItemData[x] = myObject;
                 
                x++;
                 
            } while (cursor.moveToNext());
        }
 
        cursor.close();
        db.close();
 
        return ObjectItemData;
         
    }
 
}
