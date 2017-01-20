package com.custom.baselibrary;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AIMSAPI {
	//private final String urlString = "http://www.mungigroup.com/WSPSAPI/WSPSHandler.ashx";
	//private final String urlString = "http://192.168.1.214/WSPSAPI/WSPSHandler.ashx";
	//private final String urlString = "http://10.10.0.23/WSPSAPI/WSPSHandler.ashx";
	//private final String urlString = "http://192.168.1.3/WSPSAPI/WSPSHandler.ashx";
	
	//public String urlString = "http://192.168.1.5/AIMSAPI/AIMSWS.ashx";
	//public String urlString = "http://117.218.30.14:8080/AIMSAPI/AIMSWS.ashx";
	public String urlString = "";
	public String gStrAPI = "AIMSAPI";
	public static JSONObject JsonConnObj = new JSONObject();
	private static final AIMSAPI instance = new AIMSAPI();
	public static AIMSAPI Instance() {return instance;}
	
	public AIMSAPI()
	{
		try
		{
			urlString = "http://" + LoginInfo.gStrServerInstance + "/";
			urlString += LoginInfo.gStrInterface +"/";
			urlString += LoginInfo.gStrHandler + ".ashx";
			
			JsonConnObj.put("ServerInstance",LoginInfo.gStrServerInstance);
			JsonConnObj.put("SQLInstance",LoginInfo.gStrSQLInstance);
			JsonConnObj.put("Ent_Code",LoginInfo.gStrEntCode);
			JsonConnObj.put("Loc_Code",LoginInfo.gStrLocCode);
			JsonConnObj.put("Fyr_Code",LoginInfo.gStrFyrCode);
			JsonConnObj.put("DeviceID","DEVID12345");
			JsonConnObj.put("UserCode","SUPERVISOR");
		}
		catch(Exception e)
		{
			Log.d("CAT", e.getMessage());
		}
	}
	
    

    private static String convertStreamToUTF8String(InputStream stream) throws IOException {
	    String result = "";
	    StringBuilder sb = new StringBuilder();
	    try {
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[4096];
            int readedChars = 0;
            while (readedChars != -1) {
                readedChars = reader.read(buffer);
                if (readedChars > 0)
                   sb.append(buffer, 0, readedChars);
            }
            result = sb.toString();
		} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String load(String contents) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(60000);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        OutputStreamWriter w = new OutputStreamWriter(conn.getOutputStream());
        w.write(contents);
        w.flush();
        InputStream istream = conn.getInputStream();
        String result = convertStreamToUTF8String(istream);
        return result;
    }


    private Object mapObject(Object o) 
    {
		Object finalValue = null;
		if (o.getClass() == String.class) 
		{
			finalValue = o;
		}
		else if (Number.class.isInstance(o)) 
		{
			finalValue = String.valueOf(o);
		} else if (Date.class.isInstance(o)) 
		{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", new Locale("en", "USA"));
			finalValue = sdf.format((Date)o);
		}
		else if (Collection.class.isInstance(o)) 
		{
			Collection<?> col = (Collection<?>) o;
			JSONArray jarray = new JSONArray();
			for (Object item : col) 
			{
				jarray.put(mapObject(item));
			}
			finalValue = jarray;
		} 
		else 
		{
			Map<String, Object> map = new HashMap<String, Object>();
			Method[] methods = o.getClass().getMethods();
			for (Method method : methods) 
			{
				if (method.getDeclaringClass() == o.getClass()
						&& method.getModifiers() == Modifier.PUBLIC
						&& method.getName().startsWith("get")) 
				{
					String key = method.getName().substring(3);
					try {
						Object obj = method.invoke(o, null);
						Object value = mapObject(obj);
						map.put(key, value);
						finalValue = new JSONObject(map);
					} catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}

		}
		return finalValue;
	}
    
    public JSONObject GetData(String lStrMethodName) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface",gStrAPI);
        o.put("method", lStrMethodName);
       // o.put("date","27/02/2015");
        JSONArray jsonArr = new JSONArray();
        jsonArr.put(JsonConnObj);
        p.put("pStrJSONConn",mapObject(jsonArr.toString()));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }
    
    public JSONObject GetData(String lStrMethodName, String pStrParamName) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface",gStrAPI);
        o.put("method", lStrMethodName);
        JSONArray jsonArr = new JSONArray();
        jsonArr.put(JsonConnObj);
        p.put("pStrJSONConn",mapObject(jsonArr.toString()));
        p.put("pStrFromDate",mapObject(pStrParamName));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }
    
    public JSONObject GetData(String lStrMethodName, String pStrParamName, Object pStrParamVal) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface",gStrAPI);
        o.put("method", lStrMethodName);
        JSONArray jsonArr = new JSONArray();
        jsonArr.put(JsonConnObj);
        p.put("pStrJSONConn",mapObject(jsonArr.toString()));
        p.put(pStrParamName,mapObject(pStrParamVal));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }
    
    public JSONObject GetData(String lStrMethodName, String[] pStrParamName, Object[] pStrParamVal) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface",gStrAPI);
        o.put("method", lStrMethodName);
        JSONArray jsonArr = new JSONArray();
        jsonArr.put(JsonConnObj);
        p.put("pStrJSONConn",mapObject(jsonArr.toString()));
        for (int i=pStrParamName.length-1; i>= 0; i--)
        {
        	p.put(pStrParamName[i], mapObject(pStrParamVal[i]));
        }
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }
    
    public JSONObject InsertData(String lStrMethodName, String pStrJSON) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface",gStrAPI);
        o.put("method", lStrMethodName);
        JSONArray jsonArr = new JSONArray();
        jsonArr.put(JsonConnObj);
        p.put("pStrJSONConn",mapObject(jsonArr.toString()));
        p.put("pStrJSON",mapObject(pStrJSON));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }
    
    public JSONObject InsertData(String lStrMethodName, JSONObject pObjJSON) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface",gStrAPI);
        o.put("method", lStrMethodName);
        JSONArray jsonArr = new JSONArray();
        jsonArr.put(JsonConnObj);
        p.put("pStrJSONConn",mapObject(jsonArr.toString()));
        p.put("pStrJSON",mapObject(pObjJSON));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
        
    }
    
    
    public JSONObject InsertData(String lStrMethodName, String[] pStrParamName, Object[] pStrParamVal) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface",gStrAPI);
        o.put("method", lStrMethodName);
        JSONArray jsonArr = new JSONArray();
        jsonArr.put(JsonConnObj);
        p.put("pStrJSONConn",mapObject(jsonArr.toString()));
        for (int i=pStrParamName.length-1; i>= 0; i--)
        {
        	p.put(pStrParamName[i], mapObject(pStrParamVal[i]));
        }
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }
    
  
}