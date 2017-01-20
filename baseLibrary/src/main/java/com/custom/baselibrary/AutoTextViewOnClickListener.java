package com.custom.baselibrary;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class AutoTextViewOnClickListener implements OnClickListener 
{
	public static final String TAG = "AutoTextViewOnClickListener.java";
	Context context;
	cCtrlAutoTextView  mCtrlAutoText; 
	public AutoTextViewOnClickListener(Context context, cCtrlAutoTextView autotextview)
	{
        this.context = context;
        this.mCtrlAutoText = autotextview;
    }
	
	@Override
	public void onClick(View v)
	{
		if (mCtrlAutoText.mBlShowDropDownOnFocus && !mCtrlAutoText.isPopupShowing())
			mCtrlAutoText.showDropDown();
	}
}
