package com.custom.baselibrary;



import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
/**
	 * @author MONISH BHOLE
*/
public class CustomText extends TextView {
	private Context mContext;
	public boolean mBlIsMandatory = false;
	public CustomText(Context context, AttributeSet attrs) {
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
			//a.recycle();
		}
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
    	TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CustomText); 
		final int N = a.getIndexCount();
		for(int i = 0; i < N; i++)
		{
			int attr = a.getIndex(i);
			switch(attr)
			{
			case R.styleable.CustomText_IsTextMandatory:
				mBlIsMandatory = a.getBoolean(attr, false);
				break;
			} // switch(attr)
		} // for(int i = 0; i < N; i++)
	//	a.recycle();
    }
}