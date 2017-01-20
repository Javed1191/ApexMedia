package com.custom.baselibrary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MultiSelectionSpinner extends Spinner implements
		OnMultiChoiceClickListener {
	String[] _items = null;
	boolean[] mSelection = null;
	private Context mContext = null;
	private String mStrOnClickMethod = "";
	private ArrayAdapter<String> simple_adapter;
	public MultiSelectionSpinner mCtrlCurr;
	public boolean mBlIsMandatory = false;
	public MultiSelectionSpinner(Context context) {
		super(context);

		simple_adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item);
		super.setAdapter(simple_adapter);
		this.mContext = context;
		mCtrlCurr = this;
	}

	public MultiSelectionSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.mContext = context;
		simple_adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item);
		super.setAdapter(simple_adapter);
		FetchAttributes(attrs);
	}
	
	private void FetchAttributes(AttributeSet attrs)
    {
    	TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.MultiSelectionSpinner); 
		final int N = a.getIndexCount();
		for(int i = 0; i < N; i++)
		{
			int attr = a.getIndex(i);
			switch(attr)
			{
			case R.styleable.MultiSelectionSpinner_OnMultiSpinnerClick:
				mStrOnClickMethod = a.getString(attr);
				break; // case R.styleable.cCtrlAutoTextView_OnClick:
			case R.styleable.MultiSelectionSpinner_IsMultiSpinnerMandatory:
				mBlIsMandatory = a.getBoolean(attr, false);
				break; // case R.styleable.cCtrlAutoTextView_OnClick:
			} // switch(attr)
		} // for(int i = 0; i < N; i++)
		a.recycle();
    }

	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		if (mSelection != null && which < mSelection.length) {
			mSelection[which] = isChecked;

			simple_adapter.clear();
			simple_adapter.add(buildSelectedItemString());
			
			if (mContext.isRestricted()) 
			{
		        throw new IllegalStateException();
		    }
		    if (mStrOnClickMethod != null && mStrOnClickMethod != "") 
		    {
		    		Method mHandler = null;
	                if (mHandler == null) {
	                    try {
	                    	mHandler = getContext().getClass().getDeclaredMethod(mStrOnClickMethod, DialogInterface.class, int.class, boolean.class);
	                    } catch (NoSuchMethodException e) {
	                        throw new IllegalStateException();
	                    }
	                }
	 
	                try {
	                    mHandler.invoke(getContext(), dialog, which, isChecked);
	                } catch (IllegalAccessException e) {
	                    throw new IllegalStateException();
	                } catch (InvocationTargetException e) {
	                    throw new IllegalStateException();
	                }
		    } // if (mStrOnClickMethod != null && mStrOnClickMethod != "")
		} else {
			throw new IllegalArgumentException(
					"Argument 'which' is out of bounds.");
		}
	}

	@Override
	public boolean performClick() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setMultiChoiceItems(_items, mSelection, this);
		builder.show();
		return true;
	}

	@Override
	public void setAdapter(SpinnerAdapter adapter) {
		throw new RuntimeException(
				"setAdapter is not supported by MultiSelectSpinner.");
	}

	public void setItems(String[] items) {
		_items = items;
		mSelection = new boolean[_items.length];
		simple_adapter.clear();
		simple_adapter.add(_items[0]);
		Arrays.fill(mSelection, false);
	}

	public void setItems(List<String> items) {
		_items = items.toArray(new String[items.size()]);
		mSelection = new boolean[_items.length];
		simple_adapter.clear();
		//simple_adapter.add(_items[0]);
		Arrays.fill(mSelection, false);
	}

	public void setSelection(String[] selection) {
		for (String cell : selection) {
			for (int j = 0; j < _items.length; ++j) {
				if (_items[j].equals(cell)) {
					mSelection[j] = true;
				}
			}
		}
	}

	public void setSelection(List<String> selection) {
		for (int i = 0; i < mSelection.length; i++) {
			mSelection[i] = false;
		}
		for (String sel : selection) {
			for (int j = 0; j < _items.length; ++j) {
				if (_items[j].equals(sel)) {
					mSelection[j] = true;
				}
			}
		}
		simple_adapter.clear();
		simple_adapter.add(buildSelectedItemString());
	}

	public void setSelection(int index) {
		for (int i = 0; i < mSelection.length; i++) {
			mSelection[i] = false;
		}
		if (index >= 0 && index < mSelection.length) {
			mSelection[index] = true;
		} else {
			throw new IllegalArgumentException("Index " + index
					+ " is out of bounds.");
		}
		simple_adapter.clear();
		simple_adapter.add(buildSelectedItemString());
	}

	public void setSelection(int[] selectedIndicies) {
		for (int i = 0; i < mSelection.length; i++) {
			mSelection[i] = false;
		}
		for (int index : selectedIndicies) {
			if (index >= 0 && index < mSelection.length) {
				mSelection[index] = true;
			} else {
				throw new IllegalArgumentException("Index " + index
						+ " is out of bounds.");
			}
		}
		simple_adapter.clear();
		simple_adapter.add(buildSelectedItemString());
	}

	public List<String> getSelectedStrings() {
		List<String> selection = new LinkedList<String>();
		for (int i = 0; i < _items.length; ++i) {
			if (mSelection[i]) {
				selection.add(_items[i]);
			}
		}
		return selection;
	}

	public List<Integer> getSelectedIndicies() {
		List<Integer> selection = new LinkedList<Integer>();
		for (int i = 0; i < _items.length; ++i) {
			if (mSelection[i]) {
				selection.add(i);
			}
		}
		return selection;
	}

	private String buildSelectedItemString() {
		StringBuilder sb = new StringBuilder();
		boolean foundOne = false;

		for (int i = 0; i < _items.length; ++i) {
			if (mSelection[i]) {
				if (foundOne) {
					sb.append(", ");
				}
				foundOne = true;

				sb.append(_items[i]);
			}
		}
		return sb.toString();
	}

	public String getSelectedItemsAsString() {
		StringBuilder sb = new StringBuilder();
		boolean foundOne = false;

		for (int i = 0; i < _items.length; ++i) {
			if (mSelection[i]) {
				if (foundOne) {
					sb.append(", ");
				}
				foundOne = true;
				sb.append(_items[i]);
			}
		}
		return sb.toString();
	}

	public JSONArray getSelectedItemsCodeArray(ArrayList<String> pArrLstCode,
			String pStrKey) {
		JSONArray lJsnArr = null;
		try {
			if (_items != null && mSelection != null && _items.length > 0
					&& mSelection.length > 0) {
				lJsnArr = new JSONArray();
				for (int i = 0; i < _items.length; ++i) {
					if (mSelection[i]) {
							JSONObject lJsn = new JSONObject();
							lJsn.put(pStrKey, pArrLstCode.get(i));
							lJsnArr.put(lJsn);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lJsnArr;
	}
}
