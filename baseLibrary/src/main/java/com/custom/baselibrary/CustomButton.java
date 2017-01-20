package com.custom.baselibrary;





import com.custom.baselibrary.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
/**
	 * @author MONISH BHOLE
*/
public class CustomButton extends Button {
	
	private Context mContext;
	public boolean mBlIsMandatory = false;
	public String mStrMandatoryDefaultText = "";		
	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
		this.mContext = context;
		FetchAttributes(attrs);
	}

	public void init(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.customfont);
		String fontFamily = null;
		final int n = a.getIndexCount();
		for (int i = 0; i < n; ++i) {
			int attr = a.getIndex(i);
			if (attr == R.styleable.customfont_android_fontFamily) {
				fontFamily = a.getString(attr);
			}
		}
		a.recycle();
		if (!isInEditMode()) {
			try {
				Typeface tf = Typeface.createFromAsset(
						getContext().getAssets(), fontFamily);
				setTypeface(tf);
			} catch (Exception e) {
			}
		}
	}
	
	private void FetchAttributes(AttributeSet attrs)
    {
    	TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CustomButton); 
		final int N = a.getIndexCount();
		for(int i = 0; i < N; i++)
		{
			int attr = a.getIndex(i);
			switch(attr)
			{
				case R.styleable.CustomButton_IsButtonMandatory:
					mBlIsMandatory = a.getBoolean(attr, false);
					break;
				case R.styleable.CustomButton_MandatoryDefaultText:
					mStrMandatoryDefaultText = a.getString(attr);
					break;					
			} // switch(attr)
		} // for(int i = 0; i < N; i++)
		a.recycle();
		if (mStrMandatoryDefaultText == null || mStrMandatoryDefaultText == "")
			mStrMandatoryDefaultText = this.getText().toString();
		//this.setBackgroundResource(R.drawable.rounder_border);
	//	this.setTextColor(R.drawable.txt_color);
		//android:background="@drawable/btn_bckgroun"
        //android:textColor="@drawable/txt_color"
    }
	
	/*public void setEnabled()
	{
		this.setEnabled(true);
		this.setBackgroundResource(R.drawable.rounder_border);
		//this.setTextColor(R.drawable.txt_color);
	}
	
	public void setDisabled()
	{
		this.setEnabled(false);
		this.setBackgroundResource(R.drawable.rounder_border);
		//this.setTextColor(R.drawable.txt_color);
	}*/
}
