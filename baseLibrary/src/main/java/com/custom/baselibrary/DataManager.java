package com.custom.baselibrary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * Data Manager Class to handle database
 * 
 */
public class DataManager 
{
	// fields for model table
	public static final String model_code = "model_code";
	public static final String wrmod_code = "wrmod_code";
	public static final String model_name = "model_name";
	public static final String tax_rate = "tax_rate";
	public static final String mrp_oct = "mrp_oct";
	public static final String mrp = "mrp";
	public static final String mrp_oct_rate = "mrp_oct_rate";
	public static final String mrp_rate = "mrp_rate";
	public static final String tax_amt_oct = "tax_amt_oct";
	public static final String tax_amt = "tax_amt";
	public static final String acc_amt = "acc_amt";
	public static final String acc_amt_oct = "acc_amt_oct";
	public static final String rto_amt = "rto_amt";
	public static final String rto_amt_oct = "rto_amt_oct";
	public static final String crtm_amt = "crtm_amt";
	public static final String crtm_amt_oct = "crtm_amt_oct";
	public static final String ins_amt = "ins_amt";
	public static final String ins_amt_oct = "ins_amt_oct";
	public static final String hp_amt = "hp_amt";
	public static final String hp_amt_oct = "hp_amt_oct";
	public static final String scard_amt = "scard_amt";
	public static final String scard_amt_oct = "scard_amt_oct";
	public static final String oth_amt = "oth_amt";
	public static final String oth_amt_oct = "oth_amt_oct";
	
	
	
	// Fields for color table
	public static final String m_model_code = "m_model_code";
	public static final String m_color_code = "m_color_code";
	public static final String c_color_name = "c_color_name";
	
	
	// fields for login table
	public static final String user_code  = "user_code ";
	public static final String password  = "password ";
	//public static final String flag = "flag";

	// fields for setting table
	public static final String server_instance = "server_instance";
	public static final String ws_interface = "ws_interface";
	public static final String api_handler = "api_handler";
	public static final String ent_code = "ent_code";
	public static final String loc_code = "loc_code";
	public static final String fyr_code = "fyr_code";
	

	public static final String sql_Instance = "sql_Instance";
	public static final String divice_id = "divice_id";
	
	// Basic fields for basic details table as well as for enquire table
		public static final String customer_code = "customercode";
		public static final String date = "date";
		public static final String customer_type = "cutomertype";
		public static final String model = "model";
		public static final String colour = "colour";
		public static final String ex_show_price = "exshowprice";
		public static final String on_road_price = "onrpadprice";
		
		
		// fields for only enquire table
		public static final String customer_name = "customer_name";
		public static final String address_line1 = "add1";
		public static final String address_line2 = "add2";
		public static final String address_line3 = "add3";
		public static final String phone_no = "phone_no";
		public static final String e_model_code = "model_code";
		public static final String e_tax_rate= "tax_rate";
		public static final String e_tax_amt= "e_tax_amt";
		public static final String e_color_code = "color_code";
		public static final String mobile_no = "mobile_no";
		public static final String email = "email";
		public static final String oct_yn = "oct_yn";
		public static final String vehi_rate = "vehi_rate";
		public static final String e_acc_amt = "acc_amt";
		public static final String e_rto_amt = "rto_amt";
		public static final String e_insu_amt = "insu_amt";
		public static final String e_crtm_amt = "crtm_amt";
		public static final String e_hp_amt = "hp_amt";
		public static final String e_scard_amt = "scard_amt";
		public static final String e_oth_amt = "oth_amt";
		public static final String e_quot_no = "quot_no";
		public static final String e_quot_date = "quot_date";
		public static final String e_ent_code = "ent_code";
		public static final String e_loc_code = "loc_code";
		public static final String e_fin_code = "fin_code";
		public static final String e_sman_code = "sman_code";
		public static final String e_is_acc_amt = "e_is_acc_amt";
		public static final String e_is_hp_amt = "e_is_hp_amt";
		public static final String e_is_rto_amt = "e_is_rto_amt";
		public static final String e_is_crtm_amt = "e_is_crtm_amt";
		public static final String e_is_insu_amt = "e_is_insu_amt";
		public static final String e_is_scard_amt = "e_is_scard_amt";
		public static final String e_is_oth_amt = "e_is_oth_amt";
		
		public static final String user_id = "user_id";
		public static final String machine_id = "machine_id";
		public static final String is_send = "is_send";
		
		public static final String city = "city";
		public static final String pincode = "pincode";
		public static final String e_sman_name = "e_sman_name";
		public static final String e_model_name = "e_model_name";
		public static final String e_color_name = "e_color_name";
		public static final String e_ex_show_price = "e_ex_show_price";
		public static final String e_fin_name = "e_fin_name";
		public static final String e_on_road_price = "e_on_road_price";
		
		
		
		 // fields for sales man table
		public static final String sman_code = "sman_code";
		public static final String sman_name = "sman_name";
		
		
		// fields for financer table
		public static final String finance_code = "fin_code";
		public static final String finance_name = "fin_name";
	
		
		// fields for date table
		public static final String m_date = "m_date";
		public static final String c_date = "c_date";
		public static final String s_date = "s_date";
		public static final String f_date = "f_date";
		
		// fields for pdf table
		public static final String pdf_quot_no = "pdf_quot_no";
		public static final String pdf_cust_name = "pdf_cust_name";
		public static final String pdf_model_name = "pdf_model_name";
		public static final String pdf_quot_date = "pdf_quot_date";
		public static final String pdf_email = "pdf_email";
		
/*
	    "repo_title":"AUTHORISED DEALER FOR HMSI",
	    "lbt_no":"LBT-1234",
	    "cin_no":"CIN-235665135+9686",
	    "state":"MAHARSHTRA",
	    "add3":"CANADA CORNER",
	    "add2":"OPP VASANT MARKET",
	    "city":"NASHIK",
	    "add1":"VATSALYA,",
	    "cst_no":"CST-02567896332 W.E.F. 01\/04\/2014",
	    "lst_no":"VAT-124567891 W.E.F. 01\/04\/2014",
	    "email":"emsys.nsk@gmail.com",
	    "pin":"422002",
	    "phone_no":"0253-222222",
	    "ent_name":"DEMO ENTITY"*/
		
		// fields for entity table
				public static final String ent_repo_title = "ent_repo_title";
				public static final String ent_lbt_no = "ent_lbt_no";
				public static final String ent_cin_no = "ent_cin_no";
				public static final String ent_state = "ent_state";
				public static final String ent_add3 = "ent_add3";
				public static final String ent_add2 = "ent_add2";
				public static final String ent_city = "ent_city";
				public static final String ent_add1 = "ent_add1";
				public static final String ent_cst_no = "ent_cst_no";
				public static final String ent_lst_no = "ent_lst_no";
				public static final String ent_email = "ent_email";
				public static final String ent_pin = "ent_pin";
				public static final String ent_phone_no = "ent_phone_no";
				public static final String ent_name = "ent_name";
				
				
				// fields for email setting table
				public static final String com_email  = "com_email ";
				public static final String com_pass  = "com_pass ";

				
				// fields for email setting table
				public static final String pdf_left_image  = "pdf_left_image ";
				public static final String pdf_right_image  = "pdf_right_image ";
				
				
		
		// login fields table
		private static final String user_table = "users";
		// setting fields table
		private static final String setting_table = "settings";
	
		// String for enquire table
		private static final String enquire_table = "enquire";
		//String for basic details table
		private static final String basic_details_table = "basic";
		
		//String for model table
		private static final String model_table = "models";
		
		//String for color table
		private static final String color_table = "color";
		
		//String for color table
		public static final String financer_table = "financer_Table";
		
		//String for salesman table
		public static final String salesman_table = "salesman_table";
		/*
		//String for date table
		public static final String date_table = "date_table";*/
		
		//String for pdf table
		public static final String pdf_table = "pdf_table";
		
		//String for entity table
		public static final String entity_table = "entity_table";
		
		// email setting fields table
				private static final String email_setting_table = "email_setting_table";
				// mdate  fields table
				private static final String mDate_table = "mDate_table";
				// mdate  fields table
				private static final String cDate_table = "cDate_table";
				// mdate  fields table
				private static final String fDate_table = "fDate_table";
				// mdate  fields table
				private static final String sDate_table = "sDate_table";
				
				// pdf image fields table
				private static final String pdf_image_table = "pdf_image_table";

				
		
		
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "enquire_Db";
	private static final int DATABASE_VERSION = 1;
	private final Context mCtx;
	String[] arr = { "*" };

	// create table string for user and password fields
	private static final String details_tbl_create = "create table "
			+ user_table + " (id integer primary key, " + user_code
			+ " text null," + password + " text null"
			+ ");";

	//create table string for settings fields
	private static final String setting_tbl_create = "create table "
			+ setting_table + " (id integer primary key, " + server_instance
			+ " text null," + ws_interface + " text null," + api_handler
			+ " text null," + ent_code + " text null," + loc_code
			+ " text null," + fyr_code + " text null," + sql_Instance
			+ " text null," + divice_id + " text null" + ");";
	
	
	//String for creating enquire table
	
		private static final String enquiry_tbl_create = "create table "
				+ enquire_table + "(" + customer_name + " text null," + address_line1
				+ " text null," + address_line2 + " text null," + address_line3 +" text null," + phone_no + " text null," + e_model_code
				+ " text null," + e_tax_rate + " real," + e_color_code + " text null," + mobile_no + " text null," +email
				+ " text null," + oct_yn + " boolean," + vehi_rate + " real," + e_acc_amt +" real," + e_rto_amt
				+ " real," + e_insu_amt +" real," + e_crtm_amt + " real," + e_hp_amt +" real," + e_scard_amt + " real," + e_oth_amt
				+ " real," + e_quot_no + " integet," + e_quot_date +" text null," + e_ent_code + " text null," + e_loc_code + " text null," + e_fin_code
				+ " text null," + e_sman_code + " text null," + user_id + " text null," + machine_id + " text null," + is_send + " boolean," + city 
				+ " text null," + pincode + " text null," + e_sman_name + " text null," + e_model_name + " text null," + e_color_name + " text null,"+ e_ex_show_price
				+ " text null," + e_fin_name + " text null,"+ e_tax_amt + " real," + e_is_acc_amt + " boolean," + e_is_hp_amt + " boolean," + e_is_rto_amt + " boolean," + e_is_crtm_amt
				+ " boolean," + e_is_insu_amt + " boolean," + e_is_scard_amt + " boolean," + e_is_oth_amt + " boolean," + e_on_road_price + " text null" + ");";

		// String for only basic data which will sync. from server
		private static final String basic_table_create  = "create table "
				+ basic_details_table + "(" + customer_code + " text null," + date + " text null,"
				 + customer_type + " text null," + model + " text null," + colour + " text null," + 
				ex_show_price + " text null," + on_road_price +" text null" + ");";
		
		// String for creating model table
		private static final String model_table_create  = "create table "
				+ model_table + "(" + model_code + " text null," + wrmod_code + " text null,"
				 + model_name + " text null," + tax_rate + " text null," + mrp_oct + " text null," + 
				 mrp + " text null," + mrp_oct_rate + " text null," + mrp_rate + " text null," + tax_amt_oct + " text null," +
				 tax_amt + " text null," + acc_amt + " text null," + acc_amt_oct + " text null," + rto_amt + " text null," +
				 rto_amt_oct + " text null," + crtm_amt + " text null," + crtm_amt_oct + " text null," + ins_amt + " text null," +
				 ins_amt_oct + " text null," + hp_amt + " text null," + hp_amt_oct + " text null," + scard_amt + " text null," + scard_amt_oct + " text null," +
				 oth_amt + " text null," + oth_amt_oct + " text null" + ");";
		
		// String for creating color table
				private static final String color_table_create  = "create table "
						+ color_table + "(" + m_model_code + " text null," + m_color_code + " text null,"
						+ c_color_name + " text null" + ");";
				
				// String for creating color table
				private static final String financer_table_create  = "create table "
						+ financer_table + "(" + finance_code + " text null," + finance_name + " text null" + ");";
				
				// String for creating sales name table
				private static final String salesman_table_create  = "create table "
						+ salesman_table + "(" + sman_name + " text null," + sman_code + " text null" + ");";

				
				/*// String for creating date table
				private static final String date_table_create  = "create table "
						+ date_table + "(" + m_date + " text null," + c_date + " text null," + s_date + " text null,"
						+ f_date + " text null" + ");";*/
				
				// String for creating pdf table
				private static final String pdf_table_create  = "create table "
						+ pdf_table + "(" + pdf_quot_no + " text null," + pdf_cust_name + " text null," + pdf_model_name + " text null,"
						+ pdf_email + " text null," + pdf_quot_date + " text null" + ");";
				
				
				// String for creating entity table
				private static final String entity_table_create  = "create table "
						+ entity_table + "(" + ent_repo_title + " text null," + ent_lbt_no + " text null," + ent_cin_no + " text null,"
						+ ent_state + " text null," + ent_add3 + " text null," + ent_add2 + " text null," + ent_city + " text null,"
						+ ent_add1 + " text null," + ent_cst_no + " text null," + ent_lst_no + " text null," + ent_email + " text null,"
						+ ent_pin + " text null," + ent_phone_no + " text null," + ent_name + " text null" + ");";
				
				// String for creating email setting  table
				private static final String email_setting_tbl_create = "create table "
						+ email_setting_table  + "(" + com_email
						+ " text null," + com_pass + " text null"
						+ ");";
				
				
				// String for creating mDate  table
				private static final String mDate_tbl_create = "create table "
						+ mDate_table  + "(" + m_date + " text null"
						+ ");";
				
				// String for creating cDate table
				private static final String cDate_tbl_create = "create table "
						+ cDate_table  + "(" + c_date + " text null"
						+ ");";
				// String for creating fDate  table
				private static final String fDate_tbl_create = "create table "
						+ fDate_table  + "(" + f_date + " text null"
						+ ");";
				
				// String for creating sDate  table
				private static final String sDate_tbl_create = "create table "
						+ sDate_table  + "(" + s_date + " text null"
						+ ");";
				
				// String for creating pdf image table
				private static final String pdf_image_tbl_create = "create table "
						+ pdf_image_table  +" (id integer primary key, " + pdf_left_image + " blob not null, " + pdf_right_image + "blob not null"
						+ ");";

	private static class DatabaseHelper extends SQLiteOpenHelper 
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			db.execSQL(details_tbl_create);// executing table create method for details table
			db.execSQL(setting_tbl_create);// executing table create method for setting table
			db.execSQL(enquiry_tbl_create);// executing table create method for enquire table
			db.execSQL(basic_table_create); // executing table create method for basic table table
			db.execSQL(model_table_create); // executing table create method for model table
			db.execSQL(color_table_create); // executing table create method for color table
			db.execSQL(financer_table_create); // executing table create method for financier table
			db.execSQL(salesman_table_create); // executing table create method for salesman table
			//db.execSQL(date_table_create); // executing table create method for date table
			db.execSQL(pdf_table_create); // executing table create method for pdf table
			db.execSQL(entity_table_create); // executing table create method for entity table
			db.execSQL(email_setting_tbl_create); // executing table create method for email setting table
			db.execSQL(mDate_tbl_create); // executing table create method for mdate table
			db.execSQL(cDate_tbl_create); // executing table create method for cdate table
			db.execSQL(fDate_tbl_create); // executing table create method for fdate table
			db.execSQL(sDate_tbl_create); // executing table create method for sdate table
			db.execSQL(pdf_image_tbl_create); // executing table create method for pdf_image table
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + user_table); // on upgrade for user table
			db.execSQL("DROP TABLE IF EXISTS " + setting_table); // on upgrade for settings table
			
			db.execSQL("DROP TABLE IF EXISTS " + enquire_table); // on upgrade for enquire table
			db.execSQL("DROP TABLE IF EXISTS " + basic_details_table); // on upgrade for basic details table
			
			db.execSQL("DROP TABLE IF EXISTS " + model_table); // on upgrade for model table
			db.execSQL("DROP TABLE IF EXISTS " + color_table); // on upgrade for color table
			db.execSQL("DROP TABLE IF EXISTS " + financer_table); // on upgrade for financer table
			db.execSQL("DROP TABLE IF EXISTS " + salesman_table); // on upgrade for salesman table
			//db.execSQL("DROP TABLE IF EXISTS " + date_table); // on upgrade for date table
			db.execSQL("DROP TABLE IF EXISTS " + pdf_table); // on upgrade for pdf table
			db.execSQL("DROP TABLE IF EXISTS " + entity_table); // on upgrade for entity table
			db.execSQL("DROP TABLE IF EXISTS " + email_setting_table); // on upgrade for entity table
			db.execSQL("DROP TABLE IF EXISTS " + mDate_table); // on upgrade for mDate table
			db.execSQL("DROP TABLE IF EXISTS " + cDate_table); // on upgrade for cDate table
			db.execSQL("DROP TABLE IF EXISTS " + fDate_table); // on upgrade for fDate table
			db.execSQL("DROP TABLE IF EXISTS " + sDate_table); // on upgrade for sDate table
			db.execSQL("DROP TABLE IF EXISTS " + pdf_image_table); // on upgrade for pdf image table
			
			onCreate(db);
		}
	}

	public DataManager(Context ctx) {
		this.mCtx = ctx;
	}

	public DataManager open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public Boolean isDbOpen() {
		if (mDb != null)
			return mDb.isOpen();
		else
			return false;
	}

	/*
	 * public long insertDetails(String username, String password) { Long
	 * retvalue; try { ContentValues values = new ContentValues();
	 * values.put(user_name, username); values.put(user_password, password);
	 * 
	 * retvalue = mDb.insert(user_table, null, values); } catch (Exception ex) {
	 * retvalue = Long.valueOf("0"); } return retvalue; }
	 */

	// Method for inserting data into the user table
	/*public long insertUserDetails(List<String> username, List<String> password,
			boolean Is_data) {
		Long retvalue = null;
		
		String strUser = null, strPass = null;
		int i;

		ContentValues values = new ContentValues();

		try {

			for (i = 0; i < username.size(); i++) {

				strUser = username.get(i);
				strPass = password.get(i);

				values.put(user_name, strUser);
				values.put(user_password, strPass);
				values.put(DataManager.flag, Is_data);

				retvalue = mDb.insert(user_table, null, values);

				System.out.println(username.get(i));
				System.out.println(password.get(i));
			}
		} catch (Exception ex) {
			retvalue = Long.valueOf("0");
		}
		return retvalue;
	}
*/
	public void insertUserDetails(JSONArray jSonarray) 
	{
		Long retvalue = null;
		
		//String strUser = null, strPass = null;
		
		ContentValues values = new ContentValues();

		try {
			if(jSonarray!=null)
			{
				mDb.execSQL("delete from "+ user_table);
			
				for(int i =0; i<jSonarray.length(); i++)
				{
					JSONObject jObject = jSonarray.getJSONObject(i);
				
				
					String user_code = jObject.getString("user_code");
					String password  = jObject.getString("password");
				
					//System.out.println("Data Is : " + user_code );
				
					values.put(DataManager.user_code, user_code);
					values.put(DataManager.password, password);
					//values.put("id", 1);
				
					retvalue = mDb.insert(user_table, null, values);
				}
			}
			//Toast.makeText(mCtx, "User Date Inserted Success fully" , Toast.LENGTH_LONG).show();

		}
		catch (Exception ex) 
		{
			retvalue = Long.valueOf("0");
		}
		//return retvalue;
	}
	
	// Method for inserting data into the user table
	/*public long insertUserDetails(List<String> lusername, List<String> lpassword,
			boolean Is_data) {
		Long retvalue = null;
		
		String strUser = null, strPass = null;
		int i;

		ContentValues values = new ContentValues();

		try {

			for (i = 0; i < lusername.size(); i++) {

				strUser = lusername.get(i);
				strPass = lpassword.get(i);

				values.put(user_code, strUser);
				values.put(password, strPass);
				values.put(DataManager.flag, Is_data);

				retvalue = mDb.insert(user_table, null, values);

				System.out.println(username.get(i));
				System.out.println(password.get(i));
				
			}
		} catch (Exception ex) {
			retvalue = Long.valueOf("0");
		}
		return retvalue;
	}*/
	
	/*public long insertUserDetails(JSONArray jArray) {
		Long retvalue = null;
		
		String strUser = null, strPass = null;
		int i;

		ContentValues values = new ContentValues();

		try {

			for (i = 0; i < lusername.size(); i++) {

				strUser = lusername.get(i);
				strPass = lpassword.get(i);

				values.put(user_code, strUser);
				values.put(password, strPass);
				values.put(DataManager.flag, Is_data);

				retvalue = mDb.insert(user_table, null, values);

				System.out.println(username.get(i));
				System.out.println(password.get(i));
				
			}
		} catch (Exception ex) {
			retvalue = Long.valueOf("0");
		}
		return retvalue;
	}*/

	/*
	 * public Boolean updateDetails(String name, String age) { Boolean retvalue
	 * =false; try { ContentValues values = new ContentValues();
	 * values.put(details_age,age); retvalue=mDb.update(details_tbl_name,
	 * values, details_name + "=" + name , null) > 0; } catch(Exception ex) { }
	 * return retvalue; }
	 */

	/*
	 * public int SelectDetails(String name) { Cursor retvalue =null; int
	 * no_cig=0; try { retvalue=mDb.query(details_tbl_name, new String[]
	 * {details_name,details_address,details_age}, details_name + "=" + name ,
	 * null, null, null, null);
	 * 
	 * } catch(Exception ex) { } if(retvalue.moveToFirst()) { return no_cig; }
	 * else return -1;
	 * 
	 * }
	 */
	/*
	 * public Cursor selectOneCalum() { Cursor retvalue =null; try {
	 * 
	 * retvalue=mDb.query(user_table, new String[] {user_name}, null,null, null,
	 * null, null); } catch(Exception ex) { } return retvalue; }
	 */

	/*
	 * public long insertSettingsDetails(String strServerInstaance, String
	 * strWsInterface, String strApiHandler, String strEntCode, String
	 * strLocCode, String strFyrCode, String strSqlInstance, String strDiviceId)
	 * { Long retvalue = null;
	 * 
	 * ContentValues values = new ContentValues();
	 * 
	 * values.put(server_instance, strServerInstaance); values.put(ws_interface,
	 * strWsInterface); values.put(api_handler, strApiHandler);
	 * values.put(ent_code, strEntCode); values.put(loc_code, strLocCode);
	 * values.put(fyr_code, strEntCode); values.put(sql_Instance,
	 * strSqlInstance); values.put(divice_id, strDiviceId);
	 * 
	 * retvalue = mDb.insert(setting_table, null, values);
	 * 
	 * System.out.println("Settings Data Inserted"); Toast.makeText(mCtx,
	 * "Setting Data Inserted", Toast.LENGTH_LONG).show();
	 * 
	 * return retvalue; }
	 */

	// Method for inserting data into the setting table
	public void insertSettingsDetails(String strServerInstaance,
			String strWsInterface, String strApiHandler, String strEntCode,
			String strLocCode, String strFyrCode, String strSqlInstance,
			String strDiviceId) {
		Long retvalue = null;
		
		ContentValues values = new ContentValues();

		/*Cursor c = mDb.rawQuery("SELECT * FROM " + setting_table, null);

		if (c != null && c.getCount() > 0) {
			values.put("id", 1);
			values.put(server_instance, strServerInstaance);
			values.put(ws_interface, strWsInterface);
			values.put(api_handler, strApiHandler);
			values.put(ent_code, strEntCode);
			values.put(loc_code, strLocCode);
			values.put(fyr_code, strFyrCode);
			values.put(sql_Instance, strSqlInstance);
			values.put(divice_id, strDiviceId);
			

			 mDb.update(setting_table, values, "id = '1'", null);

			settingsSelect();
		} */
			
		mDb.execSQL("delete from "+ setting_table);
		
			values.put("id", 1);
			values.put(server_instance, strServerInstaance);
			values.put(ws_interface, strWsInterface);
			values.put(api_handler, strApiHandler);
			values.put(ent_code, strEntCode);
			values.put(loc_code, strLocCode);
			values.put(fyr_code, strFyrCode);
			values.put(sql_Instance, strSqlInstance);
			values.put(divice_id, strDiviceId);
			

			retvalue = mDb.insert(setting_table, null, values);

			//System.out.println("Settings Data Inserted");
			/*Toast.makeText(mCtx, "Setting Data Inserted", Toast.LENGTH_LONG)
					.show();*/
			
		
		//return retvalue = null;

	}
	
	
	/*jsonInsert.put("customer_name", lStrCustName);
	jsonInsert.put("add1", lStrAddress1);
	jsonInsert.put("add2", lStrAddress2);
	jsonInsert.put("add3", lStrAddress3);
	jsonInsert.put("phone_no", lStrPhoneno);
	jsonInsert.put("mobile_no", lStrMobileno);
	jsonInsert.put("email", lStrEmailid);
	jsonInsert.put("model_code", strTmpModelCode); // model code
	jsonInsert.put("color_code", strTmpColorCode); // color code
	jsonInsert.put("fin_code", strFinancerCode); // financer code
	jsonInsert.put("oct_yn", isOctroi);
	jsonInsert.put("vehi_rate", lDecvehi_rate);
	jsonInsert.put("tax_rate", lDectax_rate);
	jsonInsert.put("acc_amt", lDecacc_amt);
	jsonInsert.put("rto_amt", lDecrto_amt);
	jsonInsert.put("insu_amt", lDecinsu_amt);
	jsonInsert.put("crtm_amt", lDeccrtm_amt);
	jsonInsert.put("hp_amt", lDechp_amt);
	jsonInsert.put("scard_amt", lDecscard_amt);
	jsonInsert.put("oth_amt", lDecoth_amt);
	jsonInsert.put("quot_no", lIntQtyNo);
	jsonInsert.put("sman_code", "Sales");
	jsonInsert.put("user_id", "Gouvrav");
	jsonInsert.put("ent_code", LoginInfo.gStrEntCode);
	jsonInsert.put("loc_code", LoginInfo.gStrLocCode);
	jsonInsert.put("machine_id", LoginInfo.gStrDeviceID);*/
	
	
	
	// insert method for inserting all inquire data into the inquire table
	public void insertEnquireDetails(JSONArray jArray ) 
	{
		
		try {
			//mDb.execSQL("delete from "+ enquire_table);
			
			ContentValues values = new ContentValues();
			
			for(int i =0; i<jArray.length(); i++)
			{
				JSONObject jObject = jArray.getJSONObject(i);
				
				
				/*String str = jObject.getString("name");
				System.out.println("Data Is" + str );*/
				
				
				values.put(customer_name, jObject.getString("customer_name"));
				values.put(address_line1, jObject.getString("add1"));
				values.put(address_line2, jObject.getString("add2"));
				values.put(address_line3, jObject.getString("add3"));
				values.put(phone_no, jObject.getString("phone_no"));
				values.put(e_model_code, jObject.getString("model_code"));
				values.put(e_tax_rate, jObject.getDouble("tax_rate"));
				values.put(e_tax_amt, jObject.getDouble("tax_amt"));
				
				values.put(e_color_code, jObject.getString("color_code"));
				values.put(mobile_no, jObject.getString("mobile_no"));
				values.put(email, jObject.getString("email"));
				values.put(oct_yn, jObject.getBoolean("oct_yn"));
				values.put(vehi_rate, jObject.getDouble("vehi_rate"));
				values.put(e_acc_amt, jObject.getDouble("acc_amt"));
				values.put(e_rto_amt, jObject.getDouble("rto_amt"));
				values.put(e_insu_amt, jObject.getDouble("insu_amt"));
				values.put(e_crtm_amt, jObject.getDouble("crtm_amt"));
				values.put(e_hp_amt, jObject.getDouble("hp_amt"));
				values.put(e_scard_amt, jObject.getDouble("scard_amt"));
				values.put(e_oth_amt, jObject.getDouble("oth_amt"));
				values.put(e_quot_no, jObject.getInt("quot_no"));
				values.put(e_quot_date, jObject.getString("quot_date"));
				values.put(e_ent_code, jObject.getString("ent_code"));
				values.put(e_loc_code, jObject.getString("loc_code"));
				values.put(e_sman_code, jObject.getString("sman_code"));
				values.put(user_id, jObject.getString("user_id"));
				values.put(machine_id, jObject.getString("machine_id"));
				values.put(e_fin_code, jObject.getString("fin_code"));
				values.put(is_send, jObject.getString("is_send"));
				values.put(city, jObject.getString("city"));
				values.put(pincode, jObject.getString("pin_code"));
				values.put(e_sman_name, jObject.getString("sman_name"));
				values.put(e_model_name, jObject.getString("model_name"));
				values.put(e_color_name, jObject.getString("color_name"));
				values.put(e_ex_show_price, jObject.getString("ex_show_price"));
				values.put(e_fin_name, jObject.getString("financer_name"));
				values.put(e_on_road_price, jObject.getString("on_road_price"));
				
				values.put(e_is_acc_amt, jObject.getBoolean("is_acc"));
				values.put(e_is_hp_amt, jObject.getBoolean("is_hp"));
				values.put(e_is_rto_amt, jObject.getBoolean("is_rto"));
				values.put(e_is_crtm_amt, jObject.getBoolean("is_crtm"));
				values.put(e_is_insu_amt, jObject.getBoolean("is_insu"));
				values.put(e_is_scard_amt, jObject.getBoolean("is_scard"));
				values.put(e_is_oth_amt, jObject.getBoolean("is_oth"));
				
				 mDb.insert(enquire_table, null, values);
 			}
			
			//System.out.println("Enquiry Is :" + values);
			//Toast.makeText(mCtx, "Inquiry Data Inserted", Toast.LENGTH_LONG).show();
			
		} catch (Exception ex) {
			Long.valueOf("0");
		}
		
//	Toast.makeText(mCtx, "Inquiry Data Inserted", Toast.LENGTH_LONG).show();

	}
		/*public long insertEnquireDetails(String strcust_code , String strdate , String strcust_type , String strcust_name ,
				String strcust_address , String strcity , String strpin_code , String strstate , String strmobile_no ,
				String stremail , String strmodel , String strcolour , boolean blhypothication , boolean blrto , boolean  blinsurance , String strexshow_price , String stronroad_price ) 
		{
			Long retvalue;
			try {
				ContentValues values = new ContentValues();
				values.put(customer_code, strcust_code);
				values.put(date, strdate);
				values.put(customer_type, strcust_type);
				values.put(customer_name, strcust_name);
				values.put(customer_address, strcust_address);
				values.put(city, strcity);
				values.put(pin_code, strpin_code);
				values.put(state, strstate);
				values.put(mobile_no, strmobile_no);
				values.put(email, stremail);
				values.put(model, strmodel);
				values.put(colour, strcolour);
				//values.put(hypothication, strhypothication);
				//values.put(rto, strrto);
				//values.put(insurance, strinsurance);
				values.put(hypothication, blhypothication);
				values.put(rto, blrto);
				values.put(insurance, blinsurance);
				
				values.put(ex_show_price, strexshow_price);
				values.put(on_road_price, stronroad_price);

				retvalue = mDb.insert(enquire_table, null, values);
			} catch (Exception ex) {
				retvalue = Long.valueOf("0");
			}
			return retvalue;
		}*/

		// method for inserting only basic data which will come from server into the table
		public void insertBasicDetails(JSONArray jArray ) 
		{
			// Basic fields for basic details table as well as for enquire table
			
			try {
				ContentValues values = new ContentValues();
				values.put(customer_code, "customer_code");
				values.put(date, "date");
				values.put(customer_type, "customer_type");
				values.put(model, "model");
				values.put(colour, "colour");
				values.put(ex_show_price, "ex_show_price");
				values.put(on_road_price, "on_road_price");
				
				//Toast.makeText(mCtx, "Color data inserted", Toast.LENGTH_SHORT).show();
				
				mDb.insert(basic_details_table, null, values);
			} catch (Exception ex) {
				Long.valueOf("0");
			}
			
		}
		
		// Method for inserting data into the model table
		
		/*public void insertModelDetails(String strmodel_code,String strwrmod_code, String strmodel_name, String strtax_rate,
				String strmrp_oct,String strmrp,String strmrp_oct_rate, String strmrp_rate, String strtax_amt_oct,
				String strtax_amt, String stracc_amt, String stracc_amt_oct, String strrto_amt, String strrto_amt_oct,
				String strcrtm_amt, String strcrtm_amt_oct,	String strins_amt, String strins_amt_oct, String strhp_amt,
				String strhp_amt_oct, String strscard_amt, String strscard_amt_oct, String stroth_amt, String stroth_amt_oct) 
			{
				
				try {
					ContentValues values = new ContentValues();
					values.put(model_code, strmodel_code);
					values.put(wrmod_code, strwrmod_code);
					values.put(model_name, strmodel_name);
					values.put(tax_rate, strtax_rate);
					values.put(mrp_oct, strmrp_oct);
					values.put(mrp, strmrp);
					values.put(mrp_oct_rate, strmrp_oct_rate);
					values.put(mrp_rate, strmrp_rate);
					values.put(tax_amt_oct, strtax_amt_oct);
					values.put(tax_amt, strtax_amt);
					values.put(acc_amt, stracc_amt);
					values.put(acc_amt_oct, stracc_amt_oct);
					values.put(rto_amt, strrto_amt);
					values.put(rto_amt_oct, strrto_amt_oct);
					values.put(crtm_amt, strcrtm_amt);
					values.put(crtm_amt_oct, strcrtm_amt_oct);
					values.put(ins_amt, strins_amt);
					values.put(ins_amt_oct, strins_amt_oct);
					values.put(hp_amt, strhp_amt);
					values.put(hp_amt_oct, strhp_amt_oct);
					values.put(scard_amt, strscard_amt);
					values.put(scard_amt_oct, strscard_amt_oct);
					values.put(oth_amt,stroth_amt);
					values.put(oth_amt_oct, stroth_amt_oct);
					

					mDb.insert(model_table, null, values);
					
					Toast.makeText(mCtx, "Model Data Inserted Successfully", Toast.LENGTH_LONG).show();
				} catch (Exception ex) {
					Long.valueOf("0");
				}
				
			}
*/		
		
		
	public void insertSyncData(JSONArray mJsonArray,JSONArray cJsonArray,JSONArray fJsonArray,JSONArray sJsonArray)
	{
		
		try {
			/*mDb.execSQL("delete from "+ model_table);
			mDb.execSQL("delete from "+ color_table);
			mDb.execSQL("delete from "+ financer_table);
			mDb.execSQL("delete from "+ salesman_table);*/
			
			ContentValues mvalues = new ContentValues();
			ContentValues cValues = new ContentValues();
			ContentValues fValues = new ContentValues();
			ContentValues sValues = new ContentValues();
			JSONObject mJObject = null;
			JSONObject cJObject = null;
			JSONObject fJObject=null;
			JSONObject sJObject = null;
			
			
			if(mJsonArray!=null && mJsonArray.length() > 0)
			{
			
			for(int i =0; i<mJsonArray.length(); i++)
			{
				mJObject = mJsonArray.getJSONObject(i);
				
				
				/*String str = jObject.getString("name");
				System.out.println("Data Is" + str );*/
				
				mvalues.put(model_code, mJObject.getString("model_code"));
				mvalues.put(wrmod_code, mJObject.getString("wrmod_code"));
				mvalues.put(model_name, mJObject.getString("model_name"));
				mvalues.put(tax_rate, mJObject.getString("tax_rate"));
				mvalues.put(mrp_oct, mJObject.getString("mrp_oct"));
				mvalues.put(mrp, mJObject.getString("mrp"));
				mvalues.put(mrp_oct_rate, mJObject.getString("mrp_oct_rate"));
				mvalues.put(mrp_rate, mJObject.getString("mrp_rate"));
				mvalues.put(tax_amt_oct, mJObject.getString("tax_amt_oct"));
				mvalues.put(tax_amt, mJObject.getString("tax_amt"));
				mvalues.put(acc_amt, mJObject.getString("acc_amt"));
				mvalues.put(acc_amt_oct, mJObject.getString("acc_amt_oct"));
				mvalues.put(rto_amt, mJObject.getString("rto_amt"));
				mvalues.put(rto_amt_oct, mJObject.getString("rto_amt_oct"));
				mvalues.put(crtm_amt, mJObject.getString("crtm_amt"));
				mvalues.put(crtm_amt_oct, mJObject.getString("crtm_amt_oct"));
				mvalues.put(ins_amt, mJObject.getString("ins_amt"));
				mvalues.put(ins_amt_oct, mJObject.getString("ins_amt_oct"));
				mvalues.put(hp_amt, mJObject.getString("hp_amt"));
				mvalues.put(hp_amt_oct, mJObject.getString("hp_amt_oct"));
				mvalues.put(scard_amt, mJObject.getString("scard_amt"));
				mvalues.put(scard_amt_oct, mJObject.getString("scard_amt_oct"));
				mvalues.put(oth_amt,mJObject.getString("oth_amt"));
				mvalues.put(oth_amt_oct, mJObject.getString("oth_amt_oct"));
				
				
				mDb.insert(model_table, null, mvalues);
				
			}
			
				String date = mJObject.getString("RunDate");
				ContentValues values_mdate = new ContentValues();
				//String lStr_date = ConvertSQLDateStringToString(date,"yyyy/MM/dd HH:mm:ss");
				values_mdate.put(m_date, date);
				Cursor mCursor = mDb.query(mDate_table, arr, null, null, null, null, null);

				if(mCursor!=null && mCursor.getCount() > 0) 
				{
					//mDb.update(mDate_table, values_date, m_date + " =? " , new String[] {m_date});
				
					mDb.update(mDate_table, values_mdate, null, null);
				}
				else
				{
					mDb.insert(mDate_table, null, values_mdate);
				}
			
			}
			
			
			if(cJsonArray!=null && cJsonArray.length() > 0)
			{
		
			for(int i =0; i<cJsonArray.length(); i++)
			{
				cJObject = cJsonArray.getJSONObject(i);
			
			
			/*String str = jObject.getString("name");
			System.out.println("Data Is" + str );*/
			
				cValues.put(m_model_code, cJObject.getString("model_code"));
				cValues.put(m_color_code, cJObject.getString("color_code"));
				cValues.put(c_color_name, cJObject.getString("color_name"));
				
				
				//String lStr_date = ConvertSQLDateStringToString(date,"yyyy/MM/dd HH:mm:ss");
				
				mDb.insert(color_table, null, cValues);
			
			//Toast.makeText(mCtx, "Database Model Data Inserted Successfully", Toast.LENGTH_LONG).show();
			}
				String date = cJObject.getString("RunDate");
				ContentValues values_cdate = new ContentValues();
				//String lStr_date = ConvertSQLDateStringToString(date,"yyyy/MM/dd HH:mm:ss");
				values_cdate.put(c_date, date);
				Cursor cCursor = mDb.query(cDate_table, arr, null, null, null, null, null);

				if(cCursor!=null && cCursor.getCount() > 0) 
				{
					//mDb.update(mDate_table, values_date, m_date + " =? " , new String[] {m_date});
			
					mDb.update(cDate_table, values_cdate, null, null);
				}
				else
				{
					mDb.insert(cDate_table, null, values_cdate);
				}
			}
			if(fJsonArray!=null && fJsonArray.length() > 0)
			{
				for(int i =0; i<fJsonArray.length(); i++)
				{
					fJObject = fJsonArray.getJSONObject(i);
			
					/*String str = jObject.getString("name");
					System.out.println("Data Is" + str );*/
			
					fValues.put(finance_code, fJObject.getString("fin_code"));
					fValues.put(finance_name, fJObject.getString("fin_name"));
				
					//String lStr_date = ConvertSQLDateStringToString(date,"yyyy/MM/dd HH:mm:ss");
					mDb.insert(financer_table, null, fValues);
				}
				String date = cJObject.getString("RunDate");
				ContentValues values_fdate = new ContentValues();
				//String lStr_date = ConvertSQLDateStringToString(date,"yyyy/MM/dd HH:mm:ss");
				values_fdate.put(f_date, date);
				Cursor fCursor = mDb.query(fDate_table, arr, null, null, null, null, null);

				if(fCursor!=null && fCursor.getCount() > 0) 
				{
					//mDb.update(mDate_table, values_date, m_date + " =? " , new String[] {m_date});
					mDb.update(fDate_table, values_fdate, null, null);
				}
				else
				{
					mDb.insert(fDate_table, null, values_fdate);
				}
			}
			
			if(sJsonArray!=null && sJsonArray.length() > 0)
			{
				for(int i =0; i<sJsonArray.length(); i++)
				{
					sJObject = sJsonArray.getJSONObject(i);
			
			
			/*String str = jObject.getString("name");
			System.out.println("Data Is" + str );*/
			
					sValues.put(sman_code, sJObject.getString("sman_code"));
					sValues.put(sman_name, sJObject.getString("sman_name"));
					
				//String lStr_date = ConvertSQLDateStringToString(date,"yyyy/MM/dd HH:mm:ss");
				
					mDb.insert(salesman_table, null, sValues);
				
				}
				String date = cJObject.getString("RunDate");
				ContentValues values_sdate = new ContentValues();
				//String lStr_date = ConvertSQLDateStringToString(date,"yyyy/MM/dd HH:mm:ss");
				values_sdate.put(s_date, date);
				Cursor sCursor = mDb.query(sDate_table, arr, null, null, null, null, null);

				if(sCursor!=null && sCursor.getCount() > 0) 
				{
					//mDb.update(mDate_table, values_date, m_date + " =? " , new String[] {m_date});
			
					mDb.update(sDate_table, values_sdate, null, null);
				}
				else
				{
					mDb.insert(sDate_table, null, values_sdate);
				}
			}
			
			/*if(!values_date.get(m_date).toString().equals(null)||!values_date.get(c_date).toString().equals(null)||!values_date.get(f_date).toString().equals(null)||!values_date.get(s_date).toString().equals(null))
			{
				mDb.insert(date_table, null, values_date);
				//System.out.println(values_date.toString());
			}*/
			
			/*if(!values_date.equals(null) values_date.get(s_date))
			{
				mDb.execSQL("delete from "+ date_table);
				mDb.insert(date_table, null, values_date);
				System.out.println(values_date.toString());
			}*/
			
		} catch (Exception ex) 
		{
			Long.valueOf("0");
		}
		
					
	}
				
	/*public void insertDate()
	{
		if(!values_date.equals(null))
		{
			mDb.execSQL("delete from "+ date_table);
			mDb.insert(date_table, null, values_date);
			//System.out.println(values_date.toString());
		}
		
	}*/
	
	public void insertPdfDetails(String p_quot_no,String p_cust_name,String p_model_name,String p_quot_date,String p_email)
	{
		ContentValues values = new ContentValues();
		values.put(pdf_quot_no, p_quot_no);
		values.put(pdf_cust_name, p_cust_name);
		values.put(pdf_model_name, p_model_name);
		values.put(pdf_quot_date, p_quot_date);
		values.put(pdf_email, p_email);
		
		mDb.insert(pdf_table, null, values);
		
	}
	
	// Method for ionsert entity data to entity table
	public void insertEntityDetails(JSONArray jArray) 
	{
		
			try {
				mDb.execSQL("delete from "+ entity_table);
				
				ContentValues values = new ContentValues();
				
				for(int i =0; i<jArray.length(); i++)
				{
					JSONObject jObject = jArray.getJSONObject(i);
					
					/*String str = jObject.getString("name");
					System.out.println("Data Is" + str );*/
					
					values.put(ent_repo_title, jObject.getString("repo_title"));
					values.put(ent_lbt_no, jObject.getString("lbt_no"));
					values.put(ent_cin_no, jObject.getString("cin_no"));
					values.put(ent_state, jObject.getString("state"));
					values.put(ent_add3, jObject.getString("add3"));
					values.put(ent_add2, jObject.getString("add2"));
					values.put(ent_city, jObject.getString("city"));
					values.put(ent_add1, jObject.getString("add1"));
					values.put(ent_cst_no, jObject.getString("cst_no"));
					values.put(ent_lst_no, jObject.getString("lst_no"));
					values.put(ent_email, jObject.getString("email"));
					values.put(ent_pin, jObject.getString("pin"));
					values.put(ent_phone_no, jObject.getString("phone_no"));
					values.put(ent_name, jObject.getString("ent_name"));
			
					mDb.insert(entity_table, null, values);
					
				}
				
				//Toast.makeText(mCtx, "Database Model Data Inserted Successfully", Toast.LENGTH_LONG).show();
			} catch (Exception ex) {
				Long.valueOf("0");
			}
			
		}
	
	public void insertEmailSettingDetails(String strSettingEmail, String strSettingPass) 
	{
		Long retvalue = null;
		
		//String strUser = null, strPass = null;
		
		ContentValues values = new ContentValues();

		try {
			
				if(!strSettingEmail.equals(""))
				{
					mDb.execSQL("delete from "+ email_setting_table);
				}
			
				
				String str_setting_email = strSettingEmail; 
				String str_setting_pass = strSettingPass;
				
				values.put(DataManager.com_email, str_setting_email);
				values.put(DataManager.com_pass, str_setting_pass);
				
				retvalue = mDb.insert(email_setting_table, null, values);
			}
			
		
		catch (Exception ex) 
		{
			retvalue = Long.valueOf("0");
		}
		//return retvalue;
	}
	 
	// Method for inserting images into data base
	public void insertPdfImages(Bitmap btm_left_image, Bitmap btm_right_image)
	{
		ContentValues cv = new ContentValues();
		
		if(btm_left_image!=null && btm_right_image==null)
		{
			cv.put(pdf_left_image, Utility.getBytes(btm_left_image));
			mDb.update(pdf_image_table, cv, "id = ?", new String[] { String.valueOf(1) } );
			
		}
		else if(btm_right_image!=null && btm_left_image==null)
		{
			cv.put(pdf_right_image, Utility.getBytes(btm_right_image));
			
			mDb.update(pdf_image_table, cv, "id = ?", new String[] { String.valueOf(1) } );
		}
		else
		{
			cv.put(pdf_left_image, Utility.getBytes(btm_left_image));
			cv.put(pdf_right_image, Utility.getBytes(btm_right_image));
			
			Cursor retvalue = mDb
					.query(pdf_image_table, arr, null, null, null, null, null);
			if(retvalue.moveToFirst())
			{
				mDb.update(pdf_image_table, cv, null, null);
			}
			else
			{
				mDb.insert(pdf_image_table, null, cv);
			}
		}
		
		
		
		
		
		
		
	}
	// Method for select images for pdf file
	public Cursor selectPdfImages() throws SQLException 
	{
		String[] arr = { "*" };
		Cursor retvalue = mDb
				.query(pdf_image_table, arr, null, null, null, null, null);
		
		return retvalue;
	}
	
		
	// Method for select and print all the data of user table
	public Cursor selectAllUser() {
		Cursor retvalue = null;
		try {

			String[] arr = { "*" };
			
			retvalue = mDb
					.query(user_table, arr, null, null, null, null, null);

			if(retvalue!=null && retvalue.getCount() > 0) 
			{
				if (retvalue.moveToFirst()) {
				do {
					/*String strUsername = retvalue.getString(retvalue
							.getColumnIndex(user_code));*/
					String strUsername = retvalue.getString(1);
					/*String strPassword = retvalue.getString(retvalue
							.getColumnIndex(password));*/
					String strPassword = retvalue.getString(2);

					
					// Toast.makeText(mCtx, strUsername + strPassword,
					// Toast.LENGTH_LONG).show();

				} while (retvalue.moveToNext());
			}
			}
		} catch (Exception ex) {
		}
		return retvalue;
	}

	// Method for select and print all the data of setting table
	public Cursor settingsSelect() 
	{
		Cursor cursor = null;
		// Toast.makeText(mCtx, "In Select Setting Method",
		// Toast.LENGTH_LONG).show();

		try {

			String[] arr = { "*" };
			cursor = mDb.query(setting_table, arr, null, null, null, null, null);
 
			/*if (cursor.moveToFirst()) {
				do {
					String strServerInstance = cursor.getString(cursor
							.getColumnIndex(DataManager.server_instance));
					String str_Interface = cursor.getString(cursor
							.getColumnIndex(DataManager.ws_interface));
					String str_Handler = cursor.getString(cursor
							.getColumnIndex(DataManager.api_handler));
					String str_EntCode = cursor.getString(cursor
							.getColumnIndex(DataManager.ent_code));
					String str_FyrCode = cursor.getString(cursor
							.getColumnIndex(DataManager.fyr_code));
					String str_SQLInstance = cursor.getString(cursor
							.getColumnIndex(DataManager.sql_Instance));
					String str_LocCode = cursor.getString(cursor
							.getColumnIndex(DataManager.loc_code));
					String strDiviceId = cursor.getString(cursor
							.getColumnIndex(DataManager.divice_id));
					String strSqlId = cursor.getString(cursor
							.getColumnIndex("id"));

					System.out.println("Server Instance Is: "
							+ strServerInstance);
					System.out.println("Interface Is: " + str_Interface);
					System.out.println("Handler Is: " + str_Handler);
					System.out.println("Entity Code Is: " + str_EntCode);
					System.out.println("Financial Year Is: " + str_FyrCode);
					System.out.println("SQL Instance Is: " + str_SQLInstance);
					System.out.println("Location Code Is: " + str_LocCode);
					System.out.println("Divice Id Is: " + strDiviceId);
					System.out.println("Sql Id Is: " + strSqlId);

				} while (cursor.moveToNext());
				
				*/
			
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		return cursor;
	}
	
	// method for selecting all data from the inquire table
		public Cursor selectAllEnquire() {
			Cursor retvalue = null;
			try {

				String[] arr = { "*" };
				retvalue = mDb.query(enquire_table, arr, null, null, null, null, null);
				
			} catch (Exception ex) {
			}
			return retvalue;
		}
		
		
	// select model details
	public Cursor selectAllModels() {
		Cursor retvalue = null;
		try {

			String[] arr = { "*" };
			retvalue = mDb
					.query(model_table, arr, null, null, null, null, null);
		} catch (Exception ex) {
			Log.e("Error", ex.getMessage());
		}
		return retvalue;
	}
		
	// select color details
	public Cursor selectAllColors() {
		Cursor retvalue = null;
		try {

			String[] arr = { "*" };
			retvalue = mDb
					.query(color_table, arr, null, null, null, null, null);
		} catch (Exception ex) {
			Log.e("Error", ex.getMessage());
		}
		return retvalue;
	}
				
	// select financer details
	public Cursor selectAllFinncer() {
		Cursor retvalue = null;
		try {

			String[] arr = { "*" };
			retvalue = mDb.query(financer_table, arr, null, null, null, null,
					null);
		} catch (Exception ex) {
			Log.e("Error", ex.getMessage());
		}
		return retvalue;
	}
					
	// select Salesman details
	public Cursor selectAllSalesMan() 
	{
		Cursor retvalue = null;
		try {

			String[] arr = { "*" };
			retvalue = mDb.query(salesman_table, arr, null, null, null, null,
					null);
		}

		catch (Exception ex) {
			Log.e("Error", ex.getMessage());
		}
						
		/*if (retvalue.moveToFirst()) {
			do {
			 		System.out.println(retvalue.getString(retvalue.getColumnIndex(DataManager.sman_name)).toString());
			 		 System.out.println(retvalue.getString(retvalue.getColumnIndex(DataManager.sman_code)).toString());
			 									
			} while (retvalue.moveToNext());
		}*/
		return retvalue;
	}
	
	/*// select date 
		public Cursor selectAllDate() {
			Cursor retvalue = null;
			try {

				String[] arr = { "*" };
				
				retvalue = mDb.query(date_table, arr, null, null, null, null,
						null);
			} catch (Exception ex) {
				Log.e("Error", ex.getMessage());
			}
			return retvalue;
		}*/
		
		// select mdate 
				public Cursor selectAllmDate()
				{
					Cursor retvalue = null;
					try {

						String[] arr = { "*" };
						
						retvalue = mDb.query(mDate_table, arr, null, null, null, null,
								null);
					} catch (Exception ex) {
						Log.e("Error", ex.getMessage());
					}
					return retvalue;
				}
				// select cdate 
				public Cursor selectAllcDate()
				{
					Cursor retvalue = null;
					try {

						String[] arr = { "*" };
						
						retvalue = mDb.query(cDate_table, arr, null, null, null, null,
								null);
					} catch (Exception ex) {
						Log.e("Error", ex.getMessage());
					}
					return retvalue;
				}
				// select fdate 
				public Cursor selectAllfDate()
				{
					Cursor retvalue = null;
					try {

						String[] arr = { "*" };
						
						retvalue = mDb.query(fDate_table, arr, null, null, null, null,
								null);
					} catch (Exception ex) {
						Log.e("Error", ex.getMessage());
					}
					return retvalue;
				}
				// select sdate 
				public Cursor selectAllsDate()
				{
					Cursor retvalue = null;
					try {

						String[] arr = { "*" };
						
						retvalue = mDb.query(sDate_table, arr, null, null, null, null,
								null);
					} catch (Exception ex) {
						Log.e("Error", ex.getMessage());
					}
					return retvalue;
				}
		
		// select pdf table details
		public Cursor selectAllPdfDetails() {
			Cursor retvalue = null;
			try {

				String[] arr = { "*" };
				retvalue = mDb
						.query(pdf_table, arr, null, null, null, null, null);
			} catch (Exception ex) {
				Log.e("Error", ex.getMessage());
			}
			return retvalue;
		}
		// select entity table details
				public Cursor selectAllEntityDetails() {
					Cursor retvalue = null;
					try {

						String[] arr = { "*" };
						retvalue = mDb
								.query(entity_table, arr, null, null, null, null, null);
					} catch (Exception ex) {
						Log.e("Error", ex.getMessage());
					}
					return retvalue;
				}
				
				// select entity table details
				public Cursor selectAllEmailSettingDetails() 
				{
					Cursor retvalue = null;
					try {

						String[] arr = { "*" };
						retvalue = mDb
								.query(email_setting_table, arr, null, null, null, null, null);
					} catch (Exception ex) {
						Log.e("Error", ex.getMessage());
					}
					return retvalue;
				}
				
		public Cursor selectTableData(String str_tbl_name)
		{
			String lstr_tbl_name;
			lstr_tbl_name = str_tbl_name;
			Cursor cursor = null;
			String[] arr = { "*" };
			try
			{
				
				if(lstr_tbl_name .equalsIgnoreCase("user_table"))
				{
					cursor =  mDb.query(user_table, arr, null, null, null, null, null);
				}
				else if(lstr_tbl_name .equalsIgnoreCase("setting_table"))
				{
					cursor =  mDb.query(setting_table, arr, null, null, null, null, null);
				
				}
				else if(lstr_tbl_name .equalsIgnoreCase("enquiry_table"))
				{
					cursor =  mDb.query(enquire_table, arr, null, null, null, null, null);
				
				}
				else if(lstr_tbl_name .equalsIgnoreCase("models_table"))
				{
					cursor =  mDb.query(model_table, arr, null, null, null, null, null);
		
				}
				else if(lstr_tbl_name .equalsIgnoreCase("colors_table"))
				{
					cursor =  mDb.query(color_table, arr, null, null, null, null, null);
		
				}
				else if(lstr_tbl_name .equalsIgnoreCase("financer_table"))
				{
					cursor =  mDb.query(financer_table, arr, null, null, null, null, null);
		
				}
				else if(lstr_tbl_name .equalsIgnoreCase("salesman_table"))
				{
					cursor =  mDb.query(salesman_table, arr, null, null, null, null, null);
		
				}
				else if(lstr_tbl_name .equalsIgnoreCase("mdate_table"))
				{
					cursor =  mDb.query(mDate_table, arr, null, null, null, null, null);
		
				}
				else if(lstr_tbl_name .equalsIgnoreCase("cdate_table"))
				{
					cursor =  mDb.query(cDate_table, arr, null, null, null, null, null);
		
				}
				else if(lstr_tbl_name .equalsIgnoreCase("fdate_table"))
				{
					cursor =  mDb.query(fDate_table, arr, null, null, null, null, null);
		
				}
				else if(lstr_tbl_name .equalsIgnoreCase("sdate_table"))
				{
					cursor =  mDb.query(sDate_table, arr, null, null, null, null, null);
		
				}
				else if(lstr_tbl_name .equalsIgnoreCase("pdf_table"))
				{
					cursor =  mDb.query(setting_table, arr, null, null, null, null, null);
		
				}
				else if(lstr_tbl_name .equalsIgnoreCase("entity_table"))
				{
					cursor =  mDb.query(entity_table, arr, null, null, null, null, null);
		
				}
				else if(lstr_tbl_name .equalsIgnoreCase("email_table"))
				{
					cursor =  mDb.query(setting_table, arr, null, null, null, null, null);
		
				}
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				
			}
			
			return cursor;
			
		}
				
		/*Method for testing is data available or not
			if data is available then we can directly open login form else setting form*/
		public boolean test()
		{
			Cursor c = mDb.rawQuery("SELECT * FROM " + setting_table, null);
			boolean flag;
			
			if (c != null && c.getCount() > 0) 
			{
				
				flag = true;
			} 
			else
			{
				flag = false;
			}
			return flag;
		}
		
		
		// Method for get all enquiry data which has been send to server data
		public void deleteAllEnquiryData()
		{
			mDb.execSQL("delete from "+ enquire_table);
			//Toast.makeText(mCtx, "All Enquiry Data Deleted", Toast.LENGTH_LONG).show();
		}
				
		// Method for get all enquiry data by qoutaion no
		public void deleteEnquiryByQuatNo(int IntQuatno)
		{
			 // mDb.execSQL("delete from "+ user_table);
			 mDb.delete(enquire_table, e_quot_no + "=" + IntQuatno, null);
			 // Toast.makeText(mCtx, String.valueOf(IntQuatno)+":Enquiry Data Deleted", Toast.LENGTH_LONG).show();
		}
		 public JSONArray selectAllEnquiryWithJson() throws JSONException
		    {
		    	Cursor retvalue =null;
		    	JSONArray data = new JSONArray();
		    	
		    	JSONObject jObject = new JSONObject();
		    	
		    	//JSONObject jObject = new JSONObject();
		    	
		    	String result = null;
		    	try
		    	{
			    	
		    		String[] arr ={"*"};
			    	retvalue=mDb.query(enquire_table, arr , null,null, null, null, null);
			    	
			    	int lIntQtyNo = 0;

					String  lStrCustName = "",lStrAddress1="",lStrAddress2="",lStrAddress3="",
							 lStrPhoneno = "",	lStrMobileno = "", lStrEmailid = "",lStrSmanCode = "",lStrUserid = "",
							lStrEntCode = "",lStrLocCode = "",lStrMachineId = "",lStrModelCode = "", lStrColorCode ="" ,lStrFinancerCode ="",
							lStrQuatDate="";
					 String Str_City,Str_Pincode,Str_SmanName,Str_Model_Name,Str_Color_Name,Str_Ex_Show_Price,
						Str_Fin_Name,Str_OnRoad_Amt;
					
					int isOctroi;
					int is_send,is_acc,is_hp,is_rto,is_crtm,is_insu,is_scard,is_oth;
					
					double
					lDecvehi_rate = 0, lDectax_rate = 0,lDecacc_amt = 0,
					lDecrto_amt = 0,lDecinsu_amt = 0, lDeccrtm_amt = 0,
				 lDechp_amt = 0,lDecscard_amt = 0, lDecoth_amt = 0,lDectax_amt = 0;;
			
					if(retvalue.moveToFirst())
					{
						do
						{
							JSONObject jsonInsert = new JSONObject();
							
							lStrCustName = retvalue.getString(retvalue.getColumnIndex(DataManager.customer_name));
							lStrAddress1 = retvalue.getString(retvalue.getColumnIndex(DataManager.address_line1));
							lStrAddress2 = retvalue.getString(retvalue.getColumnIndex(DataManager.address_line2));
							lStrAddress3 = retvalue.getString(retvalue.getColumnIndex(DataManager.address_line3));
							lStrPhoneno = retvalue.getString(retvalue.getColumnIndex(DataManager.phone_no));
							lStrMobileno = retvalue.getString(retvalue.getColumnIndex(DataManager.mobile_no));
							lStrEmailid = retvalue.getString(retvalue.getColumnIndex(DataManager.email));
							lStrModelCode =  retvalue.getString(retvalue.getColumnIndex(DataManager.e_model_code));
							lStrColorCode = retvalue.getString(retvalue.getColumnIndex(DataManager.e_color_code));
							lStrFinancerCode = retvalue.getString(retvalue.getColumnIndex(DataManager.e_fin_code));
							isOctroi = retvalue.getInt(retvalue.getColumnIndex(DataManager.oct_yn));
							lDecvehi_rate = retvalue.getDouble(retvalue.getColumnIndex(DataManager.vehi_rate));
							lDectax_rate = retvalue.getDouble(retvalue.getColumnIndex(DataManager.e_tax_rate));
							lDectax_amt = retvalue.getDouble(retvalue.getColumnIndex(DataManager.e_tax_amt));
							lDecacc_amt = retvalue.getDouble(retvalue.getColumnIndex(DataManager.e_acc_amt));
							lDecrto_amt = retvalue.getDouble(retvalue.getColumnIndex(DataManager.e_rto_amt));
							lDecinsu_amt = retvalue.getDouble(retvalue.getColumnIndex(DataManager.e_insu_amt));
							lDeccrtm_amt = retvalue.getDouble(retvalue.getColumnIndex(DataManager.e_crtm_amt));
							lDechp_amt = retvalue.getDouble(retvalue.getColumnIndex(DataManager.e_hp_amt));
							lDecscard_amt = retvalue.getDouble(retvalue.getColumnIndex(DataManager.e_scard_amt));
							lDecoth_amt = retvalue.getDouble(retvalue.getColumnIndex(DataManager.e_oth_amt));
							lStrSmanCode = retvalue.getString(retvalue.getColumnIndex(DataManager.sman_code));
							lStrUserid = retvalue.getString(retvalue.getColumnIndex(DataManager.user_id));
							lStrEntCode = retvalue.getString(retvalue.getColumnIndex(DataManager.e_ent_code));
							lStrLocCode = retvalue.getString(retvalue.getColumnIndex(DataManager.e_loc_code));
							lStrMachineId = retvalue.getString(retvalue.getColumnIndex(DataManager.machine_id));
							lIntQtyNo = retvalue.getInt(retvalue.getColumnIndex(DataManager.e_quot_no));
							is_send = retvalue.getInt(retvalue.getColumnIndex(DataManager.is_send));
							lStrQuatDate = retvalue.getString(retvalue.getColumnIndex(DataManager.e_quot_date));
							Str_City = retvalue.getString(retvalue.getColumnIndex(DataManager.city));
							Str_Pincode = retvalue.getString(retvalue.getColumnIndex(DataManager.pincode));
							Str_SmanName = retvalue.getString(retvalue.getColumnIndex(DataManager.e_sman_name));
							Str_Model_Name = retvalue.getString(retvalue.getColumnIndex(DataManager.e_model_name));
							Str_Color_Name = retvalue.getString(retvalue.getColumnIndex(DataManager.e_color_name));
							Str_Ex_Show_Price = retvalue.getString(retvalue.getColumnIndex(DataManager.e_ex_show_price));
							Str_Fin_Name = retvalue.getString(retvalue.getColumnIndex(DataManager.e_fin_name));
							Str_OnRoad_Amt = retvalue.getString(retvalue.getColumnIndex(DataManager.e_on_road_price));
							
							is_acc = retvalue.getInt(retvalue.getColumnIndex(DataManager.e_is_acc_amt ));
							is_hp = retvalue.getInt(retvalue.getColumnIndex(DataManager.e_is_hp_amt ));
							is_rto = retvalue.getInt(retvalue.getColumnIndex(DataManager.e_is_rto_amt ));
							is_crtm = retvalue.getInt(retvalue.getColumnIndex(DataManager.e_is_crtm_amt ));
							is_insu = retvalue.getInt(retvalue.getColumnIndex(DataManager.e_is_insu_amt ));
							is_scard = retvalue.getInt(retvalue.getColumnIndex(DataManager.e_is_scard_amt ));
							is_oth = retvalue.getInt(retvalue.getColumnIndex(DataManager.e_is_oth_amt ));
							
							 
							
							jsonInsert.put("customer_name", lStrCustName);
					    	jsonInsert.put("add1", lStrAddress1);
					    	jsonInsert.put("add2", lStrAddress2);
					    	jsonInsert.put("add3", lStrAddress3);
					    	jsonInsert.put("phone_no", lStrPhoneno);
					    	jsonInsert.put("mobile_no", lStrMobileno);
					    	jsonInsert.put("email", lStrEmailid);
					    	jsonInsert.put("model_code", lStrModelCode); // model code
					    	jsonInsert.put("color_code", lStrColorCode); // color code
					    	jsonInsert.put("fin_code", lStrFinancerCode); // financer code
					    	jsonInsert.put("oct_yn", isOctroi);
					    	jsonInsert.put("vehi_rate", lDecvehi_rate);
					    	jsonInsert.put("tax_rate", lDectax_rate);
					    	jsonInsert.put("tax_amt", lDectax_amt);
					    	jsonInsert.put("acc_amt", lDecacc_amt);
					    	jsonInsert.put("rto_amt", lDecrto_amt);
					    	jsonInsert.put("insu_amt", lDecinsu_amt);
					    	jsonInsert.put("crtm_amt", lDeccrtm_amt);
					    	jsonInsert.put("hp_amt", lDechp_amt);
					    	jsonInsert.put("scard_amt", lDecscard_amt);
					    	jsonInsert.put("oth_amt", lDecoth_amt);
					    	jsonInsert.put("quot_no", lIntQtyNo);
					    	jsonInsert.put("quot_date", lStrQuatDate);
					    	jsonInsert.put("sman_code", lStrSmanCode);
					    	jsonInsert.put("user_id", lStrUserid);
					    	jsonInsert.put("ent_code", lStrEntCode);
					    	jsonInsert.put("loc_code", lStrLocCode);
					    	jsonInsert.put("machine_id", lStrMachineId);
					    	jsonInsert.put("is_send", is_send);
					    	jsonInsert.put("city", Str_City);
					    	jsonInsert.put("pin_code", Str_Pincode);
					    	jsonInsert.put("sman_name", Str_SmanName);
					    	jsonInsert.put("model_name", Str_Model_Name);
					    	jsonInsert.put("color_name", Str_Color_Name);
					    	jsonInsert.put("ex_show_price", Str_Ex_Show_Price);
					    	jsonInsert.put("financer_name", Str_Fin_Name);
					    	jsonInsert.put("on_road_price", Str_OnRoad_Amt);
					    	
					    	jsonInsert.put("is_acc", is_acc);
					    	jsonInsert.put("is_hp", is_hp);
					    	jsonInsert.put("is_rto", is_rto);
					    	jsonInsert.put("is_crtm", is_crtm);
					    	jsonInsert.put("is_insu", is_insu);
					    	jsonInsert.put("is_scard", is_scard);
					    	jsonInsert.put("is_oth", is_oth);
					    	
					    	
							/*jsonInsert.put("customer_name", "lStrCustName");
					    	jsonInsert.put("add1", ":lStrAddress1");
					    	jsonInsert.put("add2", "lStrAddress2");
					    	jsonInsert.put("add3", "lStrAddress3");
					    	jsonInsert.put("phone_no", "lStrPhoneno");
					    	jsonInsert.put("mobile_no", "lStrMobileno");
					    	jsonInsert.put("email", "lStrEmailid");
					    	jsonInsert.put("model_code", "lStrModelCode"); // model code
					    	jsonInsert.put("color_code", "lStrColorCode"); // color code
					    	jsonInsert.put("fin_code", "lStrFinancerCode"); // financer code
					    	jsonInsert.put("oct_yn", "isOctroi");
					    	jsonInsert.put("vehi_rate", "lDecvehi_rate");
					    	jsonInsert.put("tax_rate", "lDectax_rate");
					    	jsonInsert.put("acc_amt", "lDecacc_amt");
					    	jsonInsert.put("rto_amt", "lDecrto_amt");
					    	jsonInsert.put("insu_amt", "lDecinsu_amt");
					    	jsonInsert.put("crtm_amt", "lDeccrtm_amt");
					    	jsonInsert.put("hp_amt", "lDechp_amt");
					    	jsonInsert.put("scard_amt", "lDecscard_amt");
					    	jsonInsert.put("oth_amt", "lDecoth_amt");
					    	jsonInsert.put("quot_no", "lIntQtyNo");
					    	jsonInsert.put("sman_code", "lStrSmanCode");
					    	jsonInsert.put("user_id", "lStrUserid");
					    	jsonInsert.put("ent_code", "lStrEntCode");
					    	jsonInsert.put("loc_code", "lStrLocCode");
					    	jsonInsert.put("machine_id", "lStrMachineId");*/
						
							data.put(jsonInsert);
							jObject.put("array", data);
							
						}while(retvalue.moveToNext());
						
						/*Log.i("Test", data.toString());
						Log.i("Test", jObject.toString());*/
						/*jObject.put("data", data);
						//jSonObject.put("data", data);
						
						//result = data.toString();
						JSONObject j = new JSONObject(jObject.toString());
						
						JSONArray  jArray = new JSONArray();
						jArray = j.getJSONArray("data");
						
						Log.i("test", "Json Array: "+ jArray.toString());
						
						Log.i("test", jObject.toString());
						
						result = String.valueOf(jObject);*/
					}
		    	}
		    	catch(Exception ex)
		    	{
		    		Log.e("error", ex.getMessage());
		    	}
		    	return data;
		    }
		 
		 public void updateInquiryExisting(JSONArray jArray)
		 {
			 Long retvalue = null;
				try {
					//mDb.execSQL("delete from "+ enquire_table);
					
					ContentValues values = new ContentValues();
					
					for(int i =0; i<jArray.length(); i++)
					{
						JSONObject jObject = jArray.getJSONObject(i);
						
						int qnt_no = jObject.getInt("quot_no");
						
						values.put(customer_name, jObject.getString("customer_name"));
						values.put(address_line1, jObject.getString("add1"));
						values.put(address_line2, jObject.getString("add2"));
						values.put(address_line3, jObject.getString("add3"));
						values.put(phone_no, jObject.getString("phone_no"));
						values.put(e_model_code, jObject.getString("model_code"));
						values.put(e_tax_rate, jObject.getDouble("tax_rate"));
						values.put(e_color_code, jObject.getString("color_code"));
						values.put(mobile_no, jObject.getString("mobile_no"));
						values.put(email, jObject.getString("email"));
						values.put(oct_yn, jObject.getBoolean("oct_yn"));
						values.put(vehi_rate, jObject.getDouble("vehi_rate"));
						values.put(e_acc_amt, jObject.getDouble("acc_amt"));
						values.put(e_rto_amt, jObject.getDouble("rto_amt"));
						values.put(e_insu_amt, jObject.getDouble("insu_amt"));
						values.put(e_crtm_amt, jObject.getDouble("crtm_amt"));
						values.put(e_hp_amt, jObject.getDouble("hp_amt"));
						values.put(e_scard_amt, jObject.getDouble("scard_amt"));
						values.put(e_oth_amt, jObject.getDouble("oth_amt"));
						values.put(e_quot_no, jObject.getInt("quot_no"));
						values.put(e_quot_date, jObject.getString("quot_date"));
						values.put(e_ent_code, jObject.getString("ent_code"));
						values.put(e_loc_code, jObject.getString("loc_code"));
						values.put(e_sman_code, jObject.getString("sman_code"));
						values.put(user_id, jObject.getString("user_id"));
						values.put(machine_id, jObject.getString("machine_id"));
						values.put(e_fin_code, jObject.getString("fin_code"));
						values.put(is_send, jObject.getString("is_send"));
						values.put(city, jObject.getString("city"));
						values.put(pincode, jObject.getString("pin_code"));
						values.put(e_sman_name, jObject.getString("sman_name"));
						values.put(e_model_name, jObject.getString("model_name"));
						values.put(e_color_name, jObject.getString("color_name"));
						values.put(e_ex_show_price, jObject.getString("ex_show_price"));
						values.put(e_fin_name, jObject.getString("financer_name"));
						values.put(e_on_road_price, jObject.getString("on_road_price"));
						
						/*values.put(customer_name, jObject.getString("customer_name"));
						values.put(address_line1, jObject.getString("add1"));
						values.put(address_line2, jObject.getString("add2"));
						values.put(address_line3, jObject.getString("add3"));
						values.put(phone_no, jObject.getString("phone_no"));
						values.put(e_model_code, jObject.getString("model_code"));
						values.put(e_tax_rate, jObject.getDouble("tax_rate"));
						values.put(e_color_code, jObject.getString("color_code"));
						values.put(mobile_no, jObject.getString("mobile_no"));
						values.put(email, jObject.getString("email"));
						values.put(oct_yn,jObject.getBoolean("oct_yn"));
						values.put(vehi_rate, jObject.getDouble("vehi_rate"));
						values.put(e_acc_amt, jObject.getDouble("acc_amt"));
						values.put(e_rto_amt, jObject.getDouble("rto_amt"));
						values.put(e_insu_amt, jObject.getDouble("insu_amt"));
						values.put(e_crtm_amt, jObject.getDouble("crtm_amt"));
						values.put(e_hp_amt, jObject.getDouble("hp_amt"));
						values.put(e_scard_amt, jObject.getDouble("scard_amt"));
						values.put(e_oth_amt, jObject.getDouble("oth_amt"));
						values.put(e_quot_no, jObject.getInt("quot_no"));
						values.put(e_quot_date, jObject.getString("quot_date"));
						values.put(e_ent_code, jObject.getString("ent_code"));
						values.put(e_loc_code, jObject.getString("loc_code"));
						values.put(e_sman_code, jObject.getString("sman_code"));
						values.put(user_id, jObject.getString("user_id"));
						values.put(machine_id, jObject.getString("machine_id"));
						values.put(e_fin_code, jObject.getString("fin_code"));
						values.put(is_send,"is_send");*/
						
				//	 mDb.update(enquire_table, values, null, null);
						 mDb.update(enquire_table, values, e_quot_no + "=" + qnt_no, null);
						
					}
					
					System.out.println("Enquiry Is Updated");
					//Toast.makeText(mCtx, "Inquiry Data Inserted", Toast.LENGTH_LONG).show();
					
				} catch (Exception ex) {
					Long.valueOf("0");
				}
			 
		 }
		 
		 public void updateInquiryData(int IntQntNo)
		 {
			 Long retvalue = null;
				try {
					//mDb.execSQL("delete from "+ enquire_table);
					
					ContentValues values = new ContentValues();
					
					/*for(int i =0; i<jArray.length(); i++)
					{
						JSONObject jObject = jArray.getJSONObject(i);
						
						
						String str = jObject.getString("name");
						System.out.println("Data Is" + str );
						
						
						values.put(customer_name, jObject.getString("customer_name"));
						values.put(address_line1, jObject.getString("add1"));
						values.put(address_line2, jObject.getString("add2"));
						values.put(address_line3, jObject.getString("add3"));
						values.put(phone_no, jObject.getString("phone_no"));
						values.put(e_model_code, jObject.getString("model_code"));
						values.put(e_tax_rate, jObject.getDouble("tax_rate"));
						values.put(e_color_code, jObject.getString("color_code"));
						values.put(mobile_no, jObject.getString("mobile_no"));
						values.put(email, jObject.getString("email"));
						if(jObject.getInt("oct_yn")==1)
						{
							values.put(oct_yn,true);
						}
						else if(jObject.getInt("oct_yn")==0)
						{
							values.put(oct_yn,false);
						}
						
						
						values.put(vehi_rate, jObject.getDouble("vehi_rate"));
						values.put(e_acc_amt, jObject.getDouble("acc_amt"));
						values.put(e_rto_amt, jObject.getDouble("rto_amt"));
						values.put(e_insu_amt, jObject.getDouble("insu_amt"));
						values.put(e_crtm_amt, jObject.getDouble("crtm_amt"));
						values.put(e_hp_amt, jObject.getDouble("hp_amt"));
						values.put(e_scard_amt, jObject.getDouble("scard_amt"));
						values.put(e_oth_amt, jObject.getDouble("oth_amt"));
						values.put(e_quot_no, jObject.getInt("quot_no"));
						values.put(e_quot_date, jObject.getString("quot_date"));
						values.put(e_ent_code, jObject.getString("ent_code"));
						values.put(e_loc_code, jObject.getString("loc_code"));
						values.put(e_sman_code, jObject.getString("sman_code"));
						values.put(user_id, jObject.getString("user_id"));
						values.put(machine_id, jObject.getString("machine_id"));
						values.put(e_fin_code, jObject.getString("fin_code"));
						values.put(is_send,true);
						values.put(is_send, true);
						
					// mDb.update(enquire_table, values, null, null);
					 
					 mDb.update(enquire_table, values, e_quot_no + "=" + qnt_no, null);
						
					}*/
					values.put(is_send, true);
					mDb.update(enquire_table, values, e_quot_no + "=" + IntQntNo, null);
					//System.out.println("Enquiry Is Updated");
					//Toast.makeText(mCtx, "Inquiry Data Inserted", Toast.LENGTH_LONG).show();
					
				} catch (Exception ex) {
					Long.valueOf("0");
				}
			 
		 }
		/* public String ConvertSQLDateStringToString(String pStrDate, String pStrOutputFormat)
		    {
		    	String lStrRetDate = "";
		    	Date lDtmRet = null;
		    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	try {
		    		lDtmRet = sdf.parse(pStrDate);
		    		SimpleDateFormat sdfOutput = new SimpleDateFormat(pStrOutputFormat);
		    		lStrRetDate = sdfOutput.format(lDtmRet);
		    	} catch (java.text.ParseException e) {
		    		e.printStackTrace();
		    	}
		    	return lStrRetDate;
		    }*/
}
