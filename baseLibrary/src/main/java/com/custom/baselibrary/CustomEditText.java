package com.custom.baselibrary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.custom.baselibrary.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

/**
 * @author MONISH BHOLE
 */
public class CustomEditText extends EditText implements OnFocusChangeListener {
	private Context mContext;
	public boolean mBlIsMandatory = false, mBlIsSelectAllOnFocus = true;
	public String mStrOldValue = "";
	private String mStrOnGotFocusMethod = "", mStrOnLostFocusMethod = "";

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
		this.mContext = context;
		FetchAttributes(attrs);
		this.setOnFocusChangeListener(this);
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
			a.recycle();
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

	private void FetchAttributes(AttributeSet attrs) {
		TypedArray a = mContext.obtainStyledAttributes(attrs,
				R.styleable.CustomEditText);
		final int N = a.getIndexCount();
		for (int i = 0; i < N; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.CustomEditText_IsEditTextMandatory:
				mBlIsMandatory = a.getBoolean(attr, false);
				break;
			case R.styleable.CustomEditText_EditTextSelectAllOnFocus:
				mBlIsSelectAllOnFocus = a.getBoolean(attr, true);
				break;
			case R.styleable.CustomEditText_EditTextOnGotFocus:
				mStrOnGotFocusMethod = a.getString(attr);
				break;
			case R.styleable.CustomEditText_EditTextOnLostFocus:
				mStrOnLostFocusMethod = a.getString(attr);
				break;
			} // switch(attr)
		} // for(int i = 0; i < N; i++)
		a.recycle();
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		try {
			if (hasFocus) {
				mStrOldValue = this.getText().toString();
				if (mBlIsSelectAllOnFocus)
					this.selectAll();
				if (mContext.isRestricted()) {
					throw new IllegalStateException();
				}
				if (mStrOnGotFocusMethod != null && mStrOnGotFocusMethod != "") {
					Method mHandler = null;
					if (mHandler == null) {
						try {
							mHandler = getContext().getClass()
									.getDeclaredMethod(mStrOnGotFocusMethod,
											View.class, boolean.class);
						} catch (NoSuchMethodException e) {
							throw new IllegalStateException();
						}
					}

					try {
						mHandler.invoke(getContext(), v, hasFocus);
					} catch (IllegalAccessException e) {
						throw new IllegalStateException();
					} catch (InvocationTargetException e) {
						throw new IllegalStateException();
					}
				} // if (mStrOnGotFocusMethod != null && mStrOnGotFocusMethod !=
					// "")
			} // if (hasFocus)
			else {
				if (mStrOnLostFocusMethod != null
						&& mStrOnLostFocusMethod != "") {
					Method mHandler = null;
					if (mHandler == null) {
						try {
							mHandler = getContext().getClass()
									.getDeclaredMethod(mStrOnLostFocusMethod,
											View.class, boolean.class);
						} catch (NoSuchMethodException e) {
							throw new IllegalStateException();
						}
					}

					try {
						mHandler.invoke(getContext(), v, hasFocus);
					} catch (IllegalAccessException e) {
						throw new IllegalStateException();
					} catch (InvocationTargetException e) {
						throw new IllegalStateException();
					}
				} // if (mStrOnGotFocusMethod != null && mStrOnGotFocusMethod !=
					// "")
			} // Else Part of if (hasFocus)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}