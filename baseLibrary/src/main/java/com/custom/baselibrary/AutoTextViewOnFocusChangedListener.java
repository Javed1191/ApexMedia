package com.custom.baselibrary;

import android.content.Context;
import android.view.View;
import android.view.View.OnFocusChangeListener;

public class AutoTextViewOnFocusChangedListener implements OnFocusChangeListener
{
	public static final String TAG = "AutoTextViewOnFocusChangedListener.java";
	Context context;
	cCtrlAutoTextView  mCtrlAutoText; 
	public AutoTextViewOnFocusChangedListener(Context context, cCtrlAutoTextView autotextview)
	{
        this.context = context;
        this.mCtrlAutoText = autotextview;
    }
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) 
	{
		// TODO Auto-generated method stub
		if (hasFocus)
		{
			mCtrlAutoText.mBlIsItemChanged = false;
			if (mCtrlAutoText.mArrLstAdapter != null && mCtrlAutoText.mArrLstAdapter.size() > 0)
			{
				String lStrSelectedItem = mCtrlAutoText.getText().toString();
				if (lStrSelectedItem != null && lStrSelectedItem != "" && lStrSelectedItem.length() > 0)
				{
					mCtrlAutoText.mIntSelectedItemIndex = mCtrlAutoText.mArrLstAdapter.indexOf(lStrSelectedItem);
				}
			}
			if (mCtrlAutoText.mBlIsSelectAllOnFocus)
			{
				mCtrlAutoText.selectAll();
			}
			if (mCtrlAutoText.mBlShowDropDownOnFocus)
			{
				if (!mCtrlAutoText.isPopupShowing() && 
						mCtrlAutoText.mArrLstAdapter != null && 
						mCtrlAutoText.mArrLstAdapter.size() > 0)
					mCtrlAutoText.showDropDown();
			}
		}
			
	}
}
