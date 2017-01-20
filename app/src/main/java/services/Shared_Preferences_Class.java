package services;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Shared_Preferences_Class 
{
	public static final String PREF_NAME = "TALENT_SPRINT_PREFERENCES";
	public static final int MODE = Context.MODE_PRIVATE;
	public static final String APP_USER_ID = "app_user_id";
	public static final String USER_ID = "user_id";
	public static final String USER_TYPE = "user_type";
	//public static final String USER_INTEREST_ID = "user_interest_id"; 
	public static final String USER_EMAIL = "user_email";
	public static final String USER_IMAGE = "user_image";
	public static final String USER_FIRST_NAME = "user_first_name";
	public static final String USER_LAST_NAME = "user_last_name";
	public static final String USER_MOBILE = "user_mobile";
	public static final String USER_PHONE = "user_phone";
	public static final String USER_ADDRESS = "user_address";
	public static final String USER_CITY = "user_city";
	public static final String USER_STATE = "user_state";
	public static final String USER_COUNTRY = "user_country";
	public static final String IS_TAX_FORM_FILLED = "is_tax_form_filled";
	public static final String IS_PSYCHOMETRIC = "is_psychometric";
	public static final String FCM_ID = "fcm_id";
	public static final String NOTIFICATION_COUNT = "notification_count";

	//To store boolean in shared preferences for if the device is registered to not
	public static final String REGISTERED = "registered";

	//To store the firebase id in shared preferences
	public static final String UNIQUE_ID = "uniqueid";




	/*UserType = jobject.getString("UserType");
	UserName =jobject.getString("UserName");
	company = jobject.getString("company");
	UserProfileImage = jobject.getString("UserProfileImage");
	address= jobject.getString("address");
	state = jobject.getString("state");
	city = jobject.getString("city");
	country = jobject.getString("country");*/











	public static void writeBoolean(Context context, String key, boolean value)
	{
		getEditor(context).putBoolean(key, value).commit();
	}

	public static boolean readBoolean(Context context, String key,
			boolean defValue) {
		return getPreferences(context).getBoolean(key, defValue);
	}

	public static void writeInteger(Context context, String key, int value) {
		getEditor(context).putInt(key, value).commit();
	}

	public static int readInteger(Context context, String key, int defValue) {
		return getPreferences(context).getInt(key, defValue);
	}

	public static void writeString(Context context, String key, String value) {
		getEditor(context).putString(key, value).commit();
	}

	public static String readString(Context context, String key, String defValue) {
		return getPreferences(context).getString(key, defValue);
	}


	public static void writeFloat(Context context, String key, float value) {
		getEditor(context).putFloat(key, value).commit();
	}

	public static float readFloat(Context context, String key, float defValue) {
		return getPreferences(context).getFloat(key, defValue);
	}

	public static void writeLong(Context context, String key, long value) {
		getEditor(context).putLong(key, value).commit();
	}

	public static long readLong(Context context, String key, long defValue) {
		return getPreferences(context).getLong(key, defValue);
	}

	public static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(PREF_NAME, MODE);
	}

	public static Editor getEditor(Context context) {
		return getPreferences(context).edit();
	}

}
