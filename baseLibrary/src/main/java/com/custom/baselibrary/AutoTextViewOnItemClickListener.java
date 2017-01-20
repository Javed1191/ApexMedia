package com.custom.baselibrary;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class AutoTextViewOnItemClickListener implements OnItemClickListener 
{
	public static final String TAG = "AutoTextViewOnItemClickListener.java";
	Context mContext;
	cCtrlAutoTextView  mCtrlAutoText; 
	public AutoTextViewOnItemClickListener(Context context, cCtrlAutoTextView autotextview)
	{
        this.mContext = context;
        this.mCtrlAutoText = autotextview;
    }
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	{
		if (arg2 != mCtrlAutoText.mIntSelectedItemIndex)
			mCtrlAutoText.mBlIsItemChanged = true;
		mCtrlAutoText.mIntSelectedItemIndex = arg2;
		mCtrlAutoText.setError(null);
		//Toast.makeText(mContext, "Base OnItemClick Fired", Toast.LENGTH_LONG).show();
	}
}
