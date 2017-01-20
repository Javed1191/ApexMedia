package services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility 
{
	private Context context;
	
	public Utility(Context context)
	{
		this.context = context;
	}
	
	/**
	 * This is used to check email format
	 * 
	 * @author USER
	 * @param email
	 * @return
	 */
	public boolean checkEmail(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) 
		{
			isValid = true;
		}
		return isValid;
	}
	
	/**
	 * This is used to check weather Internet is on or off
	 * 
	 * @author USER
	
	 * @return
	 */
	
	public boolean checkInternet() {
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
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
	
	public String getCountryCode()
	{
		
		
		String countryCode = null;
		try {
			TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			  countryCode = tm.getSimCountryIso();
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return countryCode;
		
	}

}
