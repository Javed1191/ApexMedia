package com.custom.baselibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class cCtrlSpinner extends Spinner 
{
	public cCtrlSpinner(Context context)
	{
		super(context);
	}
	
	public cCtrlSpinner(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	public cCtrlSpinner(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
	
	public void OnItemSelectedListener(AdapterView<?> arg0,
			View arg1, int position, long arg3)
	{
		
	}
}
