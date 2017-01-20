package com.custom.baselibrary;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;



public class CheckboxListview extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] mStrBatchNos;
	private final String[] mStrBatchStatuses;
	
	public CheckboxListview (Activity context, String[] pStrBatchNos, String[] pStrBatchStatus) 
	{
		super(context, R.layout.checkbox_row, pStrBatchNos);
		this.context = context;
		this.mStrBatchNos = pStrBatchNos;
		this.mStrBatchStatuses = pStrBatchStatus;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) 
	{
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.checkbox_row, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txtBatchNo);
		CheckBox checkboxstatuts = (CheckBox) rowView.findViewById(R.id.checkboxstatuts);
		txtTitle.setText(mStrBatchNos[position]);
		String lStrStatus = mStrBatchStatuses[position];
		if (lStrStatus.equalsIgnoreCase("01"))
			//imageView.setImageResource(R.drawable.new_batch);
			checkboxstatuts.setChecked(true);
		else if (lStrStatus.equalsIgnoreCase("02"))
			
			checkboxstatuts.setChecked(true);
		else if (lStrStatus.equalsIgnoreCase("03"))
			checkboxstatuts.setChecked(true);
		else if (lStrStatus.equalsIgnoreCase("04"))
			checkboxstatuts.setChecked(true);
		else if (lStrStatus.equalsIgnoreCase("05"))
			checkboxstatuts.setChecked(true);
		else if (lStrStatus.equalsIgnoreCase("06"))
			checkboxstatuts.setChecked(true);
		else if (lStrStatus.equalsIgnoreCase("07"))
			
			checkboxstatuts.setChecked(true);
		return rowView;
	}
}
