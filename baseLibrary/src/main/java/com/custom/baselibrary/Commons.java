package com.custom.baselibrary;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



@SuppressLint("SimpleDateFormat")
public class Commons{

	private Context _context;
	private Spinner spn;
	

	public Commons(Context context) {
		_context = context;
	}

	/**
	 * This is used to check weather Internet is on or off
	 * 
	 * @author MONISH BHOLE
	
	 * @return
	 */
	
	public boolean checkInternet() {
		try {
			ConnectivityManager connectivity = (ConnectivityManager) _context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			if (connectivity != null) {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null)
					for (int i = 0; i < info.length; i++)
						// check if network is connected or device is in range
						if (info[i].getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This is used to check is gps enable or not
	 * 
	 * @author MONISH BHOLE
	 * @return
	 */
	public boolean checkGPS() {
		// create result variable
		boolean canGetLocation = false;
		try {
			// create location manager class
			LocationManager locationManager = (LocationManager) _context
					.getSystemService(Context.LOCATION_SERVICE);
			// getting GPS status
			boolean isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			// getting network status
			boolean isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
				canGetLocation = false;
			} else {
				canGetLocation = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return
		return canGetLocation;
	}

	/**
	 * This is used to get normal font.
	 * 
	 * @author MONISH BHOLE
	 * @return AppleGaramond-Light font
	 */
	public Typeface getNormalFont() {
		return Typeface.createFromAsset(_context.getAssets(),
				"fonts/AppleGaramond-Light.ttf");
	}

	/**
	 * This is used to get Bold font.
	 * 
	 * @author MONISH BHOLE
	 * @return AppleGaramond-Light font
	 */
	public Typeface getBoldFont() {
		return Typeface.createFromAsset(_context.getAssets(),
				"fonts/AppleGaramond-Light.ttf");
	}

	/**
	 * This is used to check email format
	 * 
	 * @author MONISH BHOLE
	 * @param email
	 * @return
	 */
	public boolean checkEmail(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * This is used to show No Internet connection Toast
	 * 
	 * @author MONISH BHOLE
	 */
	public void showNoInternetConnection() {
		// get message from string.xml
		Toast.makeText(_context, "No Internet Connection", Toast.LENGTH_LONG)
				.show();
	}

	/**
	 * This is used to show Data Found Toast
	 * 
	 * @author MONISH BHOLE
	 */
	public void showNoDataFound() {
		// get message from string.xml
		Toast.makeText(_context, "No Data Found", Toast.LENGTH_LONG).show();
	}

	/**
	 * This is used to show Messages from Resource file
	 * 
	 * @author MONISH BHOLE
	 * @param msgId
	 */
	public void showMessage(int msgId) {
		// get message from string.xml
		String message = _context.getString(msgId);
		Toast.makeText(_context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * This is used to show String Message
	 * 
	 * @author MONISH BHOLE
	 * @param message
	 */
	public void showMessage(String message) {
		Toast.makeText(_context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * This is used to get Date in specified format
	 * 
	 * @author MONISH BHOLE
	 * @param pattern
	 *            in which date string is return
	 * @return date in String format
	 */
	@SuppressLint("SimpleDateFormat")
	public String getDateString(String pattern) {
		// create date format
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateString = sdf.format(new Date());
		// return date
		return dateString;
	}

	/**
	 * This is used to convert date and time in MM-dd-yyyy HH:mm PM/AM
	 * 
	 * @author MONISH BHOLE
	 */
	public String[] getDateInFormate(String dateTimeString) {
		Log.e("Incoming Date", dateTimeString);
		// store date in 0 index and time in 1st index
		String dateTime[] = dateTimeString.split("\\s");
		// create result
		String result[] = new String[2];

		String date[] = dateTime[0].split("-");
		String time = dateTime[1];

		// create date format
		DateFormat outputFormat = new SimpleDateFormat("hh:mm:ss a");
		DateFormat inputFormat = new SimpleDateFormat("hh:mm:ss");

		Date d1 = null;
		try {
			d1 = inputFormat.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}

		result[0] = date[1] + "-" + date[2] + "-" + date[0];
		result[1] = (d1 == null) ? "" : outputFormat.format(d1);

		// This is for display purpose only
		ArrayList<String> list = new ArrayList<String>();
		list.add(result[0]);
		list.add(result[1]);
		System.out.println("Outgoing Date" + list);

		return result;
	}

	/**
	 * This is used to get GMT date format
	 * 
	 * @author MONISH BHOLE
	 * @return date in gmt format
	 */
	public String getGMTDate() {
		DateFormat TWENTY_FOUR_TF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TWENTY_FOUR_TF.setTimeZone(TimeZone.getTimeZone("gmt"));
		String gmtDate = TWENTY_FOUR_TF.format(new Date());

		return gmtDate;
	}

	/**
	 * This is used to format double value into 4 digits
	 * 
	 * @param value
	 * @return String value upto 4 decimal places
	 */
	public String formatTo4Digit(double value) {
		// create formatter
		DecimalFormat format = new DecimalFormat("##.####");
		// return formatted value
		return format.format(value);
	}

	/**
	 * This is used to format double value into 4 digits
	 * 
	 * @param value
	 * @return String value upto 4 decimal places
	 */
	public String formatTo4Digit(String string) {
		Log.e("Incoming value", string);
		// convert number into
		double value = Double.parseDouble(string);
		// create formatter
		DecimalFormat format = new DecimalFormat("##.####");

		Log.e("Outgoing value", format.format(value));
		// return formatted value
		return format.format(value);
	}

	/**
	 * 
	 * @author MONISH BHOLE
	 */
	public void addMarqueeEffect(TextView textView) {
		TextView tf = textView;
		tf.setEllipsize(TruncateAt.MARQUEE);
		tf.setMarqueeRepeatLimit(-1);
		tf.setSingleLine(true);
		tf.setSelected(true);
	}
	
	/**
	 * 
	 * @return Device Date And Time In dd/MM/yyyy HH:mm:ss Format
	 */
	public String GetDeviceDateTimeString()
	{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String lStrDeviceDate = sdf.format(c.getTime());
		return lStrDeviceDate;
	}
	
	/**
	 * 
	 * @return Device Date In dd/MM/yyyy Format
	 */
	public String GetDeviceDateString()
	{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String lStrDeviceDate = sdf.format(c.getTime());
		return lStrDeviceDate;
	}
	
	
	
	/**
	 * 
	 * @return Device Time In HH:mm:ss Format
	 */
	public String GetDeviceTimeSecString()
	{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String lStrDeviceDate = sdf.format(c.getTime());
		return lStrDeviceDate;
	}
	
	/**
	 * 
	 * @return Device Time In HH:mm Format
	 */
	public String GetDeviceTimeString()
	{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String lStrDeviceDate = sdf.format(c.getTime());
		return lStrDeviceDate;
	}
	
	
	public ArrayList<String> GetArrayList(JSONObject pObjJSON, String pStrKeyColumn)
    {
    	ArrayList<String> lLstArr = new ArrayList<String>();
    	try
    	{
	    	JSONArray Jsonarr = pObjJSON.getJSONArray("Data");
			if (Jsonarr != null && Jsonarr.length() > 0)
			{
			  for(int i = 0; i<Jsonarr.length();i++)
			  {
					String lStrJson = Jsonarr.getJSONObject(i).optString(pStrKeyColumn);
					if (!lLstArr.contains(lStrJson))
						lLstArr.add(lStrJson);
			  }
			}
    	} 
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
		return lLstArr;
    }
    
    public ArrayList<String> GetArrayList(JSONArray pObjJSON, String pStrKeyColumn)
    {
    	ArrayList<String> lLstArr = new ArrayList<String>();
    	try
    	{
			if (pObjJSON != null && pObjJSON.length() > 0)
			{
			  for(int i = 0; i<pObjJSON.length();i++)
			  {
					String lStrJson = pObjJSON.getJSONObject(i).optString(pStrKeyColumn);
					if (!lLstArr.contains(lStrJson))
						lLstArr.add(lStrJson);
			  }
			}
    	} 
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
		return lLstArr;
    }
    
    public String formateDateFromstring(String inputDate, String inputFormat, String outputFormat){

        Date parsed = null;
        String outputDate = "";

        //SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        //SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat);
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat);

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) { 
            e.fillInStackTrace();
        }
        return outputDate;
    }
    
    public String GetDateInMMddyyyyFormat(String pStrDate)
    {
    	String lStrRetDate = "";
    	String[] lStrArr = pStrDate.split("-");
		if (lStrArr != null && lStrArr.length > 1)
			lStrRetDate += lStrArr[1] + "/";
		if (lStrArr != null && lStrArr.length > 1)
			lStrRetDate += lStrArr[0] + "/";
		if (lStrArr != null && lStrArr.length > 1)
			lStrRetDate += lStrArr[2];			
		return lStrRetDate;
    }
    
    public Date ConvertStringToDate(String pStrDate)
    {
    	Date lDtmRet = null;
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	try {
    		lDtmRet = sdf.parse(pStrDate);
    	} catch (java.text.ParseException e) {
    		e.printStackTrace();
    	}
    	return lDtmRet;
    }
    
    public Date ConvertStringToDate(String pStrDate, String pStrDateFormat)
    {
    	Date lDtmRet = null;
    	SimpleDateFormat sdf = new SimpleDateFormat(pStrDateFormat);
    	try {
    		lDtmRet = sdf.parse(pStrDate);
    	} catch (java.text.ParseException e) {
    		e.printStackTrace();
    	}
    	return lDtmRet;
    }
    
    public String ConvertDateToString(String pStrDate, String pStrOutputFormat)
    {
    	String lStrRetDate = "";
    	Date lDtmRet = null;
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyy hh:mm:ss");
    	try {
    		lDtmRet = sdf.parse(pStrDate);
    		SimpleDateFormat sdfOutput = new SimpleDateFormat(pStrOutputFormat);
    		lStrRetDate = sdfOutput.format(lDtmRet);
    	} catch (java.text.ParseException e) {
    		e.printStackTrace();
    	}
    	return lStrRetDate;
    }
    
    public String ConvertDateToString(Date pDtmDate, String pStrOutputFormat)
    {
    	String lStrRetDate = "";
    	//Date lDtmRet = null;
    	//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	try {
    		//String pStrDate = sdf.format(pDtmDate);
    		//lDtmRet = sdf.parse(pStrDate);
    		SimpleDateFormat sdfOutput = new SimpleDateFormat(pStrOutputFormat);
    		lStrRetDate = sdfOutput.format(pDtmDate);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return lStrRetDate;
    }
    
    public String ConvertSQLDateStringToString(String pStrDate, String pStrOutputFormat)
    {
    	String lStrRetDate = "";
    	Date lDtmRet = null;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	try {
    		lDtmRet = sdf.parse(pStrDate);
    		SimpleDateFormat sdfOutput = new SimpleDateFormat(pStrOutputFormat);
    		lStrRetDate = sdfOutput.format(lDtmRet);
    	} catch (java.text.ParseException e) {
    		e.printStackTrace();
    	}
    	return lStrRetDate;
    }
    
    public String EntryptPassword(String pPassString)
    {
    	String lStrEncrPass = "";
    	try
    	{
            String lstrPSW = "";
            String ch = "";
            pPassString = pPassString.toUpperCase();
            //char[] wCharArr = pPassString.toCharArray();
            int lIntLength = pPassString.trim().length();
            //lIntLength = wCharArr.length;
            for (int ii = 1; ii <= lIntLength; ii++)
            {
              char[] wChrArr = pPassString.substring(ii - 1, ii).toCharArray();
              char wCurrChar = pPassString.substring(ii - 1, ii).toCharArray()[0];
              int wCharAsc = (int)wCurrChar;
              wCharAsc = (wCharAsc + (2 * ii - 1)) * 1035561;
              ch = String.valueOf(wCharAsc);
              lstrPSW += ch;
            }
            lStrEncrPass = lstrPSW;
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	return lStrEncrPass;
    }
    
    public String DecryptPassword(String pPassString)
    {
    	String lStrDecrPass = "";
    	try
    	{
    		int ii = 1;
            int cnt = 1;
            String lstrCH = "";
            String lstrPSW = "";
            for (cnt = 1; cnt <= 14; cnt++)
            {
              ii = (cnt - 1) * 8 + 1;
              if (ii >= pPassString.trim().length())
                break;
              else
              {
                String wstr = pPassString.substring(ii - 1, ii + 7);
                int lIntDiv = (Integer.valueOf(wstr) / 1035561);
                char lChr = (char)(lIntDiv - (2 * cnt - 1));
                lstrCH = String.valueOf(lChr);
                lstrPSW += lstrCH;
              }
            }
            lStrDecrPass = lstrPSW;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return lStrDecrPass;
    }
    
    public boolean LoginUser(AIMSAPI pObjApi, String pStrUserCode, String pStrPassword)
    {
    	boolean lBlLoginSuccessFul = false;
	    try
	    {
	    	String[] lStrArr = new String[2];
			lStrArr[0] = "pStrUserCode";
			lStrArr[1] = "pStrPassword";
			Object[] lObjArr = new Object[2];
			lObjArr[0] = pStrUserCode;
			lObjArr[1] = EntryptPassword(pStrPassword);
		
			JSONObject jsnLogin = AIMSAPI.Instance().GetData("IsLoginSuccessful", lStrArr, lObjArr);
			String lStrStatus = jsnLogin.getString("Status");
			if (lStrStatus.equalsIgnoreCase("Success"))
			{
				lBlLoginSuccessFul = true;
			}
			else
			{
				lBlLoginSuccessFul = false;
				String lStrMsg = jsnLogin.getString("ErrorMessage");
				//Toast.makeText(_context, lStrMsg, Toast.LENGTH_LONG).show();
				//showAlertDialog(_context, "Login Failed", lStrMsg, false);
				LoginScreenActivity lLogAct = (LoginScreenActivity)_context;
				TextView txt_loginStatus = (TextView)lLogAct.findViewById(R.id.txt_LoginStatus);
				txt_loginStatus.setText(lStrMsg);
				txt_loginStatus.setVisibility(View.VISIBLE);
		    }
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
    	return lBlLoginSuccessFul;
    }
    
    public JSONObject GetSiteConnectionDetails(String pStrSiteCode)
    {
    	JSONObject jsonObj = null;
    	try
    	{
    		//String lStrDate = GetDeviceDateString();
    		//java.util.Date lDtmCurrDate = ConvertStringToDate(lStrDate);
    		//WSPSAPI api = new WSPSAPI();
    		String[] lStrArr = new String[2];
    		lStrArr[0] = "pStrSiteCode";
    		lStrArr[1] = "pDtmCurrDate";
    		Object[] lObjArr = new Object[2];
    		lObjArr[0] = pStrSiteCode;
    		lObjArr[1] = GetDeviceDateString();
    		JSONObject json = AIMSAPI.Instance().GetData("GetSiteConnectionDetails", lStrArr, lObjArr);
    		String lStrStatus = json.getString("Status");
    		if (lStrStatus.equalsIgnoreCase("Success"))
    		{
    			JSONArray jsonA = new JSONArray(json.getString("Data"));
    			if (jsonA != null)
    			{
    				JSONObject jsonT = jsonA.getJSONObject(0);
    				jsonObj = new JSONObject();
    				jsonObj.put("StartDate",jsonT.getString("StartDate"));
    				jsonObj.put("EndDate",jsonT.getString("EndDate"));
    				jsonObj.put("MobServerName",jsonT.getString("MobServerName"));
    				jsonObj.put("MobDbName",jsonT.getString("MobDbName"));
    				jsonObj.put("TrnServerName",jsonT.getString("InvServerName"));
    				jsonObj.put("TrnDbName",jsonT.getString("InvDBName"));
    				jsonObj.put("AccServerName",jsonT.getString("AcServerName"));
    				jsonObj.put("AccDbName",jsonT.getString("AcDBName"));
    				jsonObj.put("PRServerName",jsonT.getString("PRServerName"));
    				jsonObj.put("PRDbName",jsonT.getString("PRDbName"));
    				jsonObj.put("HRServerName",jsonT.getString("HRServerName"));
    				jsonObj.put("HRDbName",jsonT.getString("HRDbName"));
    				jsonObj.put("DeviceID","");
    				jsonObj.put("UserCode","");
    			}
    		}
    		else
    		{
    			//Toast.makeText(_context, json.getString("ErrorMessage"), Toast.LENGTH_LONG).show();
    			showAlertDialog(_context, "Connection Details", json.getString("ErrorMessage"), false, 200000);
    		}
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	return jsonObj;
    }
    
    public boolean GetKeysAndValues(JSONObject pJSNObj,String[] pStrKeys, Object[] pStrVals)
    {
    	boolean lBlRetVal = false;
    	try
    	{
    		if (pJSNObj != null)
    		{
				Iterator<String> lItrKeys = pJSNObj.keys();
				pStrKeys = new String[pJSNObj.length()];
				pStrVals = new Object[pJSNObj.length()];
				int lIntInd = 0;
				while (lItrKeys.hasNext()) 
				{
					String lStrKey = lItrKeys.next();
					Object lStrValue = pJSNObj.get(lStrKey);
					pStrKeys[lIntInd] = lStrKey;
					pStrVals[lIntInd] = lStrValue;
					lIntInd++;
				}
				lBlRetVal = true;
    		}
    	}
		catch(Exception e)
		{
			e.printStackTrace();
			lBlRetVal = false;
		}
    	return lBlRetVal = true;
    }
    
    public void showAlertDialog(Context context, String title, String message, Boolean status) throws InterruptedException {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);
		
		// Setting alert dialog icon
		alertDialog.setIcon((status) ? R.drawable.bullet : R.drawable.bullet);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		// Showing Alert Message
		alertDialog.show();
		/*alertDialog.wait(2000);
		alertDialog.dismiss();*/
	}
    
    public void showAlertDialog(Context context, String title, String message, Boolean status, long pLngWait) throws InterruptedException {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);
		
		// Setting alert dialog icon
		alertDialog.setIcon((status) ? R.drawable.bullet : R.drawable.bullet);

		// Setting OK Button
		/*alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});*/

		// Showing Alert Message
		alertDialog.show();
		Thread.sleep(pLngWait);
		alertDialog.dismiss();
	}
    
    public Object GetData(String pStrMethodName)
    {
    	Object lObjRet = null;
    	try
    	{
	    	JSONObject lJsn = AIMSAPI.Instance().GetData(pStrMethodName);
			String lStrStatus = lJsn.getString("Status");
			if(lStrStatus.equalsIgnoreCase("Success"))
			{
				lObjRet = lJsn.get("Data");
			}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return lObjRet;
    }
    
    public Object GetData(String pStrMethodName, String pStrFromDate)
    {
    	Object lObjRet = null;
    	try
    	{
	    	JSONObject lJsn = AIMSAPI.Instance().GetData(pStrMethodName, pStrFromDate);
			String lStrStatus = lJsn.getString("Status");
			if(lStrStatus.equalsIgnoreCase("Success"))
			{
				lObjRet = lJsn.get("Data");
			}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return lObjRet;
    }
    
    public Object GetData(String pStrMethodName, String pStrParamName, Object pObjParamVal)
    {
    	Object lObjRet = null;
    	try
    	{
	    	JSONObject lJsn = AIMSAPI.Instance().GetData(pStrMethodName,pStrParamName,pObjParamVal);
			String lStrStatus = lJsn.getString("Status");
			if(lStrStatus.equalsIgnoreCase("Success"))
			{
				lObjRet = lJsn.get("Data");
			}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return lObjRet;
    }
    
    public Object GetData(String pStrMethodName, String pStrParamName, Object pObjParamVal, String pStrDataHeader)
    {
    	Object lObjRet = null;
    	try
    	{
	    	JSONObject lJsn = AIMSAPI.Instance().GetData(pStrMethodName,pStrParamName,pObjParamVal);
			String lStrStatus = lJsn.getString("Status");
			if(lStrStatus.equalsIgnoreCase("Success"))
			{
				lObjRet = lJsn.get(pStrDataHeader);
			}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return lObjRet;
    }
    
    public Object GetData(String pStrMethodName, String[] pStrParamName, Object[] pObjParamVal)
    {
    	Object lObjRet = null;
    	try
    	{
	    	JSONObject lJsn = AIMSAPI.Instance().GetData(pStrMethodName,pStrParamName,pObjParamVal);
			String lStrStatus = lJsn.getString("Status");
			if(lStrStatus.equalsIgnoreCase("Success"))
			{
				lObjRet = lJsn.get("Data");
			}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return lObjRet;
    }
    
    public Object GetData(String pStrMethodName, String[] pStrParamName, Object[] pObjParamVal, String pStrDataHeader)
    {
    	Object lObjRet = null;
    	try
    	{
	    	JSONObject lJsn = AIMSAPI.Instance().GetData(pStrMethodName,pStrParamName,pObjParamVal);
			String lStrStatus = lJsn.getString("Status");
			if(lStrStatus.equalsIgnoreCase("Success"))
			{
				lObjRet = lJsn.get(pStrDataHeader);
			}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return lObjRet;
    }
    
    public String getDiviceID() 
	{
		TelephonyManager telephonyManager = (TelephonyManager)_context.getSystemService(Context.TELEPHONY_SERVICE);
		String diviceID = telephonyManager.getDeviceId();
		return diviceID;

	}
}
