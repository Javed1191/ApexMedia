package com.custom.baselibrary;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
 
public class AutoTextViewTextChangedListener implements TextWatcher
{
    public static final String TAG = "AutoTextViewTextChangedListener.java";
    Context context;
    cCtrlAutoTextView mObjAutoText;
    //SQLiteHandler mDbHandler;
    /*public AutoTextViewTextChangedListener(Context context, cCtrlAutoTextView pObjAutoText, SQLiteHandler pDbHandler){
        this.context = context;
        this.mObjAutoText = pObjAutoText;
        this.mDbHandler = pDbHandler;
    }*/
    
    public AutoTextViewTextChangedListener(Context context, cCtrlAutoTextView pObjAutoText)
    {
        this.context = context;
        this.mObjAutoText = pObjAutoText;
        //this.mDbHandler = pDbHandler;
    }
     
    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
         
    }
 
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {
        // TODO Auto-generated method stub
         
    }
 
    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {
 
        try
        {
        	if (count <= 0)
        		return;
        	String lStrTxtVal = mObjAutoText.getText().toString();
        	if (lStrTxtVal.length() == count)
        		return;
        	String lStrOldVal = lStrTxtVal.substring(before, (start - before));
        	if ((start + count) != lStrTxtVal.length())
        	{
        		lStrOldVal = lStrOldVal + lStrTxtVal.substring(start + count, lStrTxtVal.length());
        	}
        	if (mObjAutoText.mArrLstAdapter != null)
        	{
        		boolean lBlIsValidChar = false;
        		for (int i = 0; i < mObjAutoText.mArrLstAdapter.size(); i++) 
        		{
					if (mObjAutoText.mArrLstAdapter.get(i).toUpperCase().startsWith(lStrTxtVal.toUpperCase()))
					{
						lBlIsValidChar = true;
						break;
					}
				}	
        		//!mObjAutoText.mArrLstAdapter.contains(lStrTxtVal)
        		//String lStrOldVal = lStrTxtVal.substring(0,lStrTxtVal.length()-1);
        		if (!lBlIsValidChar)
        			mObjAutoText.setText(lStrOldVal);
        	}
        	// if you want to see in the logcat what the user types
           /* Log.e(TAG, "User input: " + userInput);
            Activity mainActivity = ((Activity) context);
            // update the adapater
            //mainActivity.myAdapter.notifyDataSetChanged();
            // get suggestions from the database
            AdapterObject[] myObjs = mDbHandler.read(userInput.toString());
            // update the adapter
            mObjAutoText.mArrayAdapater = new AutoTextViewAdapter(mainActivity, R.layout.autotextlistviewrow, myObjs);
             
            mObjAutoText.setAdapter(mObjAutoText.mArrayAdapater);*/
             
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
         
    }
}
