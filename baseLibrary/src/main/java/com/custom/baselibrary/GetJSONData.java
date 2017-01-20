package com.custom.baselibrary;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class GetJSONData extends AsyncTask<Object, Void, JSONObject> 
{
	AIMSAPI api = new AIMSAPI();
	JSONObject jsonObj = null;
	@Override
	protected JSONObject doInBackground(Object[] params) 
	{
		try 
		{
			String mStrMethodName = (String)params[0];
			if (params[1] instanceof String[])
			{
				String[] lStrParam = (String[])params[1];
				Object[] lObjParamVals = (Object[])params[2];
				jsonObj = api.GetData(mStrMethodName, lStrParam, lObjParamVals);
			}
			else if (params[1] instanceof String)
			{
				String lStrParam = (String)params[1];
				Object lObjParamVals = (Object)params[2];
				jsonObj = api.GetData(mStrMethodName, lStrParam, lObjParamVals);
			}
		} 
		catch (Exception e) 
		{
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return jsonObj;
	}
	
	@Override
	protected void onPostExecute(JSONObject json) 
	{
		return;
	}
}
