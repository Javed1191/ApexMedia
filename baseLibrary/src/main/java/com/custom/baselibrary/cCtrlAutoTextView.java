package com.custom.baselibrary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;


public class cCtrlAutoTextView extends AutoCompleteTextView  
{
	interface CallBackListner { public void Callback(); }
	//private WSPSAPI mApi = new WSPSAPI();
	private Commons common;
	private ArrayList<String> mArrLstData;
	private Context mContext = null;
	private String mStrOnClickMethod = "";
	private String mStrOnItemClickMethod = "";
	private String mStrOnFocusChangedMethod = "";
	private String mStrOnTextChangedMethod = "";
	public String mStrDataSourceMethodName;
	public String mStrSourceColumn;
	public JSONObject mJSNDataSourceParams;
	public String mStrDataHeader = "Data";
	public boolean mBlIsSelectAllOnFocus = true;
	public boolean mBlShowDropDownOnFocus = true;
	public boolean mBlIsMandatory = false;
	public boolean mBlIsAutoSelectSingleItem = true;
	public ArrayList<String> mArrLstAdapter;
	public ArrayAdapter<AdapterObject> mArrayAdapater;
	public int mIntSelectedItemIndex = -1;
	public boolean mBlIsItemChanged = false;
	cCtrlAutoTextView mCtrlCurr;
	private int mIntLayoutId;
	JSONArray mJSONArrReturn = null;
	private int mIntlayoutId;
	public cCtrlAutoTextView(Context context) 
	{
        super(context);
        mContext = context;
        common = new Commons(context);
        mCtrlCurr = this;
        if (mStrOnClickMethod == null || mStrOnClickMethod == "")
        	this.setOnClickListener(new AutoTextViewOnClickListener(context, this));
        if (mStrOnItemClickMethod == null || mStrOnItemClickMethod == "")
        	this.setOnItemClickListener(new AutoTextViewOnItemClickListener(context, this));
        if (mStrOnFocusChangedMethod == null || mStrOnFocusChangedMethod == "")
        	this.setOnFocusChangeListener(new AutoTextViewOnFocusChangedListener(context, this));
        if (mStrOnTextChangedMethod == null || mStrOnTextChangedMethod == "")
        	this.addTextChangedListener(new AutoTextViewTextChangedListener(context, this));
    }
     
    public cCtrlAutoTextView(Context context, AttributeSet attrs) 
    {
        super(context, attrs);
        mContext = context;
        common = new Commons(context);
        FetchAttributes(attrs);
        mCtrlCurr = this;
        if (mStrOnClickMethod == null || mStrOnClickMethod == "")
        	this.setOnClickListener(new AutoTextViewOnClickListener(context, this));
        if (mStrOnItemClickMethod == null || mStrOnItemClickMethod == "")
        	this.setOnItemClickListener(new AutoTextViewOnItemClickListener(context, this));        
        if (mStrOnFocusChangedMethod == null || mStrOnFocusChangedMethod == "")
        	this.setOnFocusChangeListener(new AutoTextViewOnFocusChangedListener(context, this));
        if (mStrOnTextChangedMethod == null || mStrOnTextChangedMethod == "")
        	this.addTextChangedListener(new AutoTextViewTextChangedListener(context, this));
    }
 
    public cCtrlAutoTextView(Context context, AttributeSet attrs, int defStyle) 
    {
        super(context, attrs, defStyle);
        mContext = context;
        common = new Commons(context);
        FetchAttributes(attrs);
        mCtrlCurr = this;
        if (mStrOnClickMethod == null || mStrOnClickMethod == "")
        	this.setOnClickListener(new AutoTextViewOnClickListener(context, this));
        if (mStrOnItemClickMethod == null || mStrOnItemClickMethod == "")
        	this.setOnItemClickListener(new AutoTextViewOnItemClickListener(context, this));        
        if (mStrOnFocusChangedMethod == null || mStrOnFocusChangedMethod == "")
        	this.setOnFocusChangeListener(new AutoTextViewOnFocusChangedListener(context, this));
        if (mStrOnTextChangedMethod == null || mStrOnTextChangedMethod == "")
        	this.addTextChangedListener(new AutoTextViewTextChangedListener(context, this));
    }
    
    private void FetchAttributes(AttributeSet attrs)
    {
    	TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.cCtrlAutoTextView); 
		final int N = a.getIndexCount();
		for(int i = 0; i < N; i++)
		{
			int attr = a.getIndex(i);
			switch(attr)
			{
			case R.styleable.cCtrlAutoTextView_DataSourceMethod:
				mStrDataSourceMethodName = a.getString(attr);
				break;
			case R.styleable.cCtrlAutoTextView_DataSourceParams:
				try 
				{
					mJSNDataSourceParams = new JSONObject(a.getString(attr));
				} catch (JSONException e) 
				{
					e.printStackTrace();
				}
				break;
			case R.styleable.cCtrlAutoTextView_DataSourceColumn:
				mStrSourceColumn = a.getString(attr);
				//... do something with DbHelper...
				break;
			case R.styleable.cCtrlAutoTextView_DataHeader:
				mStrDataHeader = a.getString(attr);
				break;
			case R.styleable.cCtrlAutoTextView_ShowDropDownOnFocus:
				mBlShowDropDownOnFocus = a.getBoolean(attr, true);
				break;
			case R.styleable.cCtrlAutoTextView_IsAutoTextMandatory:
				mBlIsMandatory = a.getBoolean(attr, false);
				break;
			case R.styleable.cCtrlAutoTextView_IsAutoSelectSingleItem:
				mBlIsAutoSelectSingleItem = a.getBoolean(attr, true);
			case R.styleable.cCtrlAutoTextView_OnClick:
				mStrOnClickMethod = a.getString(attr);
				if (mContext.isRestricted()) 
				{
			        throw new IllegalStateException();
			    }
			 
			    if (mStrOnClickMethod != null && mStrOnClickMethod != "") 
			    {
			        this.setOnClickListener(new OnClickListener() 
			        {
			            private Method mHandler;
			 
			            public void onClick(View v) 
			            {
			            	AutoTextViewOnClickListener a = new AutoTextViewOnClickListener(mContext, mCtrlCurr);
			            	a.onClick(v);
			                if (mHandler == null) {
			                    try {
			                    	mHandler = getContext().getClass().getDeclaredMethod(mStrOnClickMethod, View.class);
			                    } catch (NoSuchMethodException e) {
			                        throw new IllegalStateException();
			                    }
			                }
			 
			                try {
			                    mHandler.invoke(getContext(), v);
			                } catch (IllegalAccessException e) {
			                    throw new IllegalStateException();
			                } catch (InvocationTargetException e) {
			                    throw new IllegalStateException();
			                }
			            } // public void onClick(View v) 
			        }); // this.setOnClickListener
			    } // if (mStrOnClickMethod != null && mStrOnClickMethod != "")
				break; // case R.styleable.cCtrlAutoTextView_OnClick:
			case R.styleable.cCtrlAutoTextView_OnFocusChanged:
				mStrOnFocusChangedMethod = a.getString(attr);
				if (mContext.isRestricted()) 
				{
			        throw new IllegalStateException();
			    }
			 
			    if (mStrOnFocusChangedMethod != null && mStrOnFocusChangedMethod != "") 
			    {
			        this.setOnFocusChangeListener(new OnFocusChangeListener() 
			        {
			        	private Method mHandler;
						@Override
						public void onFocusChange(View v, boolean hasFocus) 
						{
							AutoTextViewOnFocusChangedListener a = new AutoTextViewOnFocusChangedListener(mContext, mCtrlCurr);
				        	a.onFocusChange(v, hasFocus);
							if (mHandler == null) 
			                {
			                    try 
			                    {
			                    	mHandler = getContext().getClass().getDeclaredMethod(mStrOnFocusChangedMethod, View.class, boolean.class);
			                    } 
			                    catch (NoSuchMethodException e) 
			                    {
			                        throw new IllegalStateException();
			                    }
			                }
			 
			                try 
			                {
			                    mHandler.invoke(getContext(), v, hasFocus);
			                } 
			                catch (IllegalAccessException e) 
			                {
			                    throw new IllegalStateException();
			                } 
			                catch (InvocationTargetException e) 
			                {
			                    throw new IllegalStateException();
			                }
						} // public void onFocusChange(View v, boolean hasFocus) 
					}); // this.setOnFocusChangeListener
			    } // if (mStrOnFocusChangedMethod != null && mStrOnFocusChangedMethod != "") 
				break; // case R.styleable.cCtrlAutoTextView_OnFocusChanged:
			case R.styleable.cCtrlAutoTextView_OnTextChanged:
				mStrOnTextChangedMethod = a.getString(attr);
				if (mContext.isRestricted()) 
				{
			        throw new IllegalStateException();
			    }
			 
			    if (mStrOnTextChangedMethod != null && mStrOnTextChangedMethod != "") 
			    {
			    	this.addTextChangedListener(new TextWatcher() {
			    		private Method mHandler;
						@Override
						public void onTextChanged(CharSequence s, int start, int before, int count) {
							// TODO Auto-generated method stub
							AutoTextViewTextChangedListener a = new AutoTextViewTextChangedListener(mContext, mCtrlCurr);
				        	a.onTextChanged(s, start, before, count);
				        	if (mHandler == null) {
			                    try {
			                    	mHandler = getContext().getClass().getDeclaredMethod(mStrOnTextChangedMethod, CharSequence.class, int.class, int.class, int.class);
			                    } catch (NoSuchMethodException e) {
			                        throw new IllegalStateException();
			                    }
			                }
			 
			                try {
			                    mHandler.invoke(getContext(), s, start, before, count);
			                } catch (IllegalAccessException e) {
			                    throw new IllegalStateException();
			                } catch (InvocationTargetException e) {
			                    throw new IllegalStateException();
			                }
						}
						
						@Override
						public void beforeTextChanged(CharSequence s, int start, int count,
								int after) {
							// TODO Auto-generated method stub
							AutoTextViewTextChangedListener a = new AutoTextViewTextChangedListener(mContext, mCtrlCurr);
				        	a.beforeTextChanged(s, start, count, after);
						}
						
						@Override
						public void afterTextChanged(Editable s) {
							// TODO Auto-generated method stub
							AutoTextViewTextChangedListener a = new AutoTextViewTextChangedListener(mContext, mCtrlCurr);
				        	a.afterTextChanged(s);
						}
					}); // this.addTextChangedListener(
			    } // if (mStrOnTextChangedMethod != null && mStrOnTextChangedMethod != "") 
				break; // case R.styleable.cCtrlAutoTextView_OnTextChanged:
			case R.styleable.cCtrlAutoTextView_OnItemClick:
				mStrOnItemClickMethod = a.getString(attr);
				if (mContext.isRestricted()) 
				{
			        throw new IllegalStateException();
			    }
			 
			    if (mStrOnItemClickMethod != null && mStrOnItemClickMethod != "") 
			    {
			    	this.setOnItemClickListener(new OnItemClickListener() {
			    		
			    		private Method mHandler;
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
						
							AutoTextViewOnItemClickListener a = new AutoTextViewOnItemClickListener(mContext, mCtrlCurr);
			            	a.onItemClick(arg0, arg1, arg2, arg3);
			                if (mHandler == null) {
			                    try {
			                    	mHandler = getContext().getClass().getDeclaredMethod(mStrOnItemClickMethod, View.class, int.class, long.class);
			                    } catch (NoSuchMethodException e) {
			                        throw new IllegalStateException();
			                    }
			                }
			 
			                try
			                {
			                    mHandler.invoke(getContext(), arg1, arg2, arg3);
			                } catch (IllegalAccessException e) 
			                {
			                    throw new IllegalStateException();
			                } catch (InvocationTargetException e)
			                {
			                    throw new IllegalStateException();
			                }
						} // public void onItemClick
					}); // this.setOnItemClickListener
			    } // if (mStrOnItemClickMethod != null && mStrOnItemClickMethod != "")
				break; // case R.styleable.cCtrlAutoTextView_OnItemClick:
			} // switch(attr)
		} // for(int i = 0; i < N; i++)
		a.recycle();
    }
    
    public void setAdapter(Context pContext,int pLayoutId, ArrayList<String> pArrLst)
    {
    	try
    	{
	    	mContext = pContext;
	    	mIntlayoutId = pLayoutId;
	    	mArrLstData = pArrLst;
	    	if (mArrLstData != null)
			{
				((Activity)pContext).runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						mCtrlCurr.setAdapter(new ArrayAdapter<String>(mContext, mIntlayoutId, mArrLstData));
						mArrLstAdapter = mArrLstData;
						if (mBlIsAutoSelectSingleItem && mArrLstData != null && mArrLstData.size() == 1)
						{
							mCtrlCurr.setText(mArrLstData.get(0));
							mCtrlCurr.mIntSelectedItemIndex = 0;
						}
					}
				});
				
			}
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    public JSONArray setAdapter(Context pContext,int pLayoutId)
    {
    	mJSONArrReturn = null;
    	try
    	{
    		mIntlayoutId = pLayoutId;
    		if (mStrDataSourceMethodName != "")
    		{
    			JSONObject lJSNObject = null;
    			if (mJSNDataSourceParams == null)
    				lJSNObject = AIMSAPI.Instance().GetData(mStrDataSourceMethodName);
    			else
    			{
    				Iterator<String> lItrKeys = mJSNDataSourceParams.keys();
    				String[] lStrParams = new String[mJSNDataSourceParams.length()];
    				Object[] lStrParamVals = new Object[mJSNDataSourceParams.length()];
    				int lIntInd = 0;
    				while (lItrKeys.hasNext()) 
    				{
    					String lStrKey = lItrKeys.next();
    					Object lStrValue = mJSNDataSourceParams.get(lStrKey);
    					lStrParams[lIntInd] = lStrKey;
    					lStrParamVals[lIntInd] = lStrValue;
    					lIntInd++;
    				}
    				lJSNObject = AIMSAPI.Instance().GetData(mStrDataSourceMethodName, lStrParams, lStrParamVals);
    			}
				String lStrStatus = lJSNObject.getString("Status");
				 if (lStrStatus.equals("Success"))
				 {
					 mArrLstData = common.GetArrayList(lJSNObject, mStrSourceColumn);
					 mJSONArrReturn = new JSONArray(lJSNObject.getString(mStrDataHeader));
				 }
    		}
    		if (mArrLstData != null)
    		{
    			((Activity)pContext).runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						mCtrlCurr.setAdapter(new ArrayAdapter<String>(mContext, mIntlayoutId, mArrLstData));
						mArrLstAdapter = mArrLstData;
						if (mBlIsAutoSelectSingleItem && mArrLstData != null && mArrLstData.size() == 1)
						{
							mCtrlCurr.setText(mArrLstData.get(0));
							mCtrlCurr.mIntSelectedItemIndex = 0;
						}
					}
				});
    			
    		}
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	return mJSONArrReturn;
    }
    
    @Override
    protected void performFiltering(final CharSequence text, final int keyCode) 
    {
        //String filterText = "";
        super.performFiltering(text, keyCode);
    }
 
    /*
     * after a selection we have to capture the new value and append to the existing text
     */
    @Override
    protected void replaceText(final CharSequence text) 
    {
        super.replaceText(text);
    }
    
    public class setAdapterFromJSON extends AsyncTask<Integer, Void, JSONArray> 
    {
		@Override
		protected JSONArray doInBackground(Integer... layoutIds) {

			mArrLstData = new ArrayList<String>();
			mIntlayoutId = layoutIds[0];
			JSONArray jsonArr = null;
			JSONObject json = null;
			try 
			{
				if (mStrDataSourceMethodName != "")
	    		{
	    			if (mJSNDataSourceParams == null)
	    				json = AIMSAPI.Instance().GetData(mStrDataSourceMethodName);
	    			else
	    			{
	    				String[] lStrParams = new String[0];
	    				String[] lStrParamVals = new String[0];
	    				if (common.GetKeysAndValues(mJSNDataSourceParams, lStrParams, lStrParamVals))
	    				{
	    					json = AIMSAPI.Instance().GetData(mStrDataSourceMethodName, lStrParams, lStrParamVals);
	    				}
	    			}
					String lStrStatus = json.getString("Status");
					 if (lStrStatus.equals("Success"))
					 {
						 mArrLstData = common.GetArrayList(json, mStrSourceColumn);
						 jsonArr = new JSONArray(json.getString(mStrDataHeader));
					 }
	    		}

			} 
			catch (Exception e) 
			{
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return jsonArr;
		}
		
		@Override
		protected void onPostExecute(JSONArray json) {
			if (mArrLstData != null)
    			mCtrlCurr.setAdapter(new  ArrayAdapter<String>(mContext, mIntlayoutId, mArrLstData));
		}
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
}

