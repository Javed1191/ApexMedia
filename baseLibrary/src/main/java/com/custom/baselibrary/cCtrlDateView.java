package com.custom.baselibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

public class cCtrlDateView extends TextView
{
	public cCtrlDateView(Context context)
	{
		super(context);
	}
	
	public cCtrlDateView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.cCtrlDateView); 
		final int N = a.getIndexCount();
		for(int i = 0; i < N; i++)
		{
			int attr = a.getIndex(i);
			switch(attr)
			{
			case R.styleable.cCtrlDateView_delimeter:
				String delimeter = a.getString(attr);
				//...do something with delimeter...
				break;
			case R.styleable.cCtrlDateView_fancyText:
				boolean fancyText = a.getBoolean(attr, false);
				//... do something with fancyText...
				break;
			}
		}
		a.recycle();
	}
	
	public cCtrlDateView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.cCtrlDateView); 
		final int N = a.getIndexCount();
		for(int i = 0; i < N; i++)
		{
			int attr = a.getIndex(i);
			switch(attr)
			{
			case R.styleable.cCtrlDateView_delimeter:
				String delimeter = a.getString(attr);
				//...do something with delimeter...
				break;
			case R.styleable.cCtrlDateView_fancyText:
				boolean fancyText = a.getBoolean(attr, false);
				//... do something with fancyText...
				break;
			}
		}
		a.recycle();
	}
}
