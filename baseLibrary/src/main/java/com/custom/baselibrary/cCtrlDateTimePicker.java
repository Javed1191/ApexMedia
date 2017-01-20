package com.custom.baselibrary;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class cCtrlDateTimePicker  extends AlertDialog implements
		OnDateChangedListener, OnTimeChangedListener{

    public final static int DATE_PICKER = 1;
    public final static int TIME_PICKER = 2;
    public final static int DATE_TIME_PICKER = 3;
    
    public String mStrDateTimeFormat = "";
	public String mStrSelectedDateTime = "";
	private DatePicker dpReminder;
	private TimePicker tpReminder;
	private Calendar _c;
	TabHost tabHost;
	private float lastX;
	private LinearLayout lndatepicker;
	private LinearLayout lntimepicker;
	private long duration_time = 500;
	private Context mContext;
	private Activity mActCalling;
    public int mIntReturnViewId;
    public int mIntParentLayoutId;
	/** 
	 * Define Dialog type
	 */
    private int DialogType;
    
    private View mVwReturn; 

	/**
	 * @return the dpReminder
	 */
	public DatePicker getDpReminder() {
		return dpReminder;
	}
	
	/**
	 * @param dpReminder
	 *            the dpReminder to set
	 */
	public void setDpReminder(DatePicker dpReminder) {
		this.dpReminder = dpReminder;
	}
	
	/**
	 * @return the tpReminder
	 */
	public TimePicker getTpReminder() {
		
		
		return tpReminder;
	}
	
	/**
	 * @param tpReminder
	 *            the tpReminder to set
	 */
	public void setTpReminder(TimePicker tpReminder) {
		this.tpReminder = tpReminder;
	}
	
	/**
	 * @return the c
	 */
	public Calendar getCal() {
		return _c;
	}
	
	/**
	 * @param c
	 *            the calendar to set
	 */
	public void setCal(Calendar c) {
		this._c = c;
	}
	
	public Date getDate(){
		Calendar cal = Calendar.getInstance();
		if (dpReminder != null)
			cal.setTime(DateTimeHelper.getDateFromDatePicket(dpReminder));
		if (tpReminder != null)
		{
			cal.set(Calendar.HOUR_OF_DAY, tpReminder.getCurrentHour());
			cal.set(Calendar.MINUTE, tpReminder.getCurrentMinute());
		}
		return cal.getTime();
	}
	
	public cCtrlDateTimePicker(Context context, String pStrDateTimeFormat, int DialogType) {
		super(context);

		this.mContext = context;
		this.DialogType = DialogType;
		this.mStrDateTimeFormat = pStrDateTimeFormat;
		this.mActCalling = (Activity)context;
		setDateTimePicker();
	}
	
	public cCtrlDateTimePicker(Context context, String pStrDateTimeFormat, int DialogType, View pVwReturn) {
		super(context);

		this.mContext = context;
		this.DialogType = DialogType;
		this.mStrDateTimeFormat = pStrDateTimeFormat;
		this.mVwReturn = pVwReturn;
		this.mActCalling = (Activity)context;
		setDateTimePicker();
	}
	
	private void setDateTimePicker()
	{
		LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
		View customView = inflater.inflate(R.layout.reminder_picker, null);
		
		tabHost = (TabHost) customView.findViewById(R.id.tabhost);
		tabHost.setup();
		
	
		TabHost.TabSpec spec = tabHost.newTabSpec("tag1");
		switch (this.DialogType) {
		case DATE_TIME_PICKER:
			
			spec.setContent(R.id.datepicker);
			spec.setIndicator("Date");
			tabHost.addTab(spec);
		
			spec = tabHost.newTabSpec("tag2");
			spec.setContent(R.id.timepicker);
			spec.setIndicator("Time");
			tabHost.addTab(spec);
			tabHost.setCurrentTab(1);
			this.setView(customView);
			dpReminder = (DatePicker) customView.findViewById(R.id.dpReminder);
			tpReminder = (TimePicker) customView.findViewById(R.id.tpReminder);
		
			lndatepicker = (LinearLayout) customView.findViewById(R.id.datepicker);
			lntimepicker = (LinearLayout) customView.findViewById(R.id.timepicker);
			
			setTitle("Set Date Time");
			break;
		case DATE_PICKER:
			
			spec.setContent(R.id.datepicker);
			spec.setIndicator("Date");
			tabHost.addTab(spec);
		
			this.setView(customView);
			dpReminder = (DatePicker) customView.findViewById(R.id.dpReminder);
			//tpReminder = (TimePicker) customView.findViewById(R.id.tpReminder);
		
			lndatepicker = (LinearLayout) customView.findViewById(R.id.datepicker);
			lntimepicker = (LinearLayout) customView.findViewById(R.id.timepicker);
			lntimepicker.setVisibility(View.GONE);
			setTitle("Set Date");
			break;

		default:
			spec.setContent(R.id.timepicker);
			spec.setIndicator("Time");
			tabHost.addTab(spec);
			this.setView(customView);
			//dpReminder = (DatePicker) customView.findViewById(R.id.dpReminder);
			tpReminder = (TimePicker) customView.findViewById(R.id.tpReminder);
			lndatepicker = (LinearLayout) customView.findViewById(R.id.datepicker);
			lndatepicker.setVisibility(View.GONE);
			lntimepicker = (LinearLayout) customView.findViewById(R.id.timepicker);
			setTitle("Set Time");
			break;
		}	
		_c = Calendar.getInstance();
	}
	
	public void setDateTime(Calendar cal) {
		this._c.setTime(cal.getTime());
		if (dpReminder != null)
		{
			dpReminder.init(_c.get(Calendar.YEAR), _c.get(Calendar.MONTH),
				_c.get(Calendar.DAY_OF_MONTH), cCtrlDateTimePicker.this);
		}
		if (tpReminder != null)
		{
			tpReminder.setCurrentHour(_c.get(Calendar.HOUR_OF_DAY));
			tpReminder.setCurrentMinute(_c.get(Calendar.MINUTE));
			tpReminder.setIs24HourView(true);
		
			tpReminder.setOnTimeChangedListener(cCtrlDateTimePicker.this);
		}
		String title = DateTimeHelper.dateToString(_c.getTime(),
				DateTimeHelper.NORMAL_DAY_OF_WEEK_FORMAT);
		if (tpReminder != null)
		{
			title += ", "
			+ DateTimeHelper.getStringTimeFromTime(tpReminder.getCurrentHour(),
					tpReminder.getCurrentMinute());
		}
		setTitle(title);
	}
	
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		_c = Calendar.getInstance();
		_c.set(year, monthOfYear, dayOfMonth);
	
		String title = DateTimeHelper.dateToString(_c.getTime(),
				DateTimeHelper.NORMAL_DAY_OF_WEEK_FORMAT);
		if (tpReminder != null)
		{
			title += ", "
			+ DateTimeHelper.getStringTimeFromTimePicker(tpReminder);
		}
		setTitle(title);
	
	}
	
	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
	
		String title = "";
		if (dpReminder != null)
		{
			title += DateTimeHelper.dateToString(
			DateTimeHelper.getDateFromDatePicket(dpReminder),
			DateTimeHelper.NORMAL_DAY_OF_WEEK_FORMAT);
		}
		if (tpReminder != null)
		{
			title += ", "
			+ DateTimeHelper.getStringTimeFromTime(hourOfDay, minute);
		}
		_c.set(Calendar.HOUR_OF_DAY, hourOfDay);
		_c.set(Calendar.MINUTE, minute);
		setTitle(title);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent touchevent) 
	{
		
		switch (touchevent.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			{
				lastX = touchevent.getX();
				break;
			}
			case MotionEvent.ACTION_UP:
			{
				float currentX = touchevent.getX();
	
				// if left to right swipe on screen
				if (lastX < currentX)
				{
	
					switchTabs(false);
				}
	
				// if right to left swipe on screen
				if (lastX > currentX) {
					switchTabs(true);
				}
	
				break;
			}
		}
		
		return super.onTouchEvent(touchevent);
	}
	
	public Animation inFromRightAnimation() {
	
		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(duration_time);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}
	public Animation inFromLeftAnimation() {
	
		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(duration_time);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}
	
	public Animation outToRightAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(duration_time);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}
	
	public Animation outToLeftAnimation()
	{
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(duration_time);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}
	
	
	
	public void switchTabs(boolean direction) {
		if (!direction) // false = left to right
		{
			if (tabHost.getCurrentTab() != 0) {
				tabHost.setCurrentTab(tabHost.getCurrentTab() - 1);
				if (lndatepicker != null)
					lndatepicker.setAnimation(inFromLeftAnimation());
				if (lntimepicker != null)
					lntimepicker.setAnimation(outToRightAnimation());
			}
			// else
			// tabHost.setCurrentTab(tabHost.getTabWidget().getTabCount() - 1);
	
		} else
		// right to left
		{
			if (tabHost.getCurrentTab() != (tabHost.getTabWidget()
					.getTabCount() - 1)) {
				tabHost.setCurrentTab(tabHost.getCurrentTab() + 1);
				if (lndatepicker != null)
					lndatepicker.setAnimation(outToLeftAnimation());
				if (lntimepicker != null)
					lntimepicker.setAnimation(inFromRightAnimation());
			}
			// else
			// tabHost.setCurrentTab(0);
		}
	}
	
	public void Show(int pIntViewId) {
//		LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
//		View customView = inflater.inflate(R.layout.reminder_picker, null);
		this.mIntReturnViewId = pIntViewId;
		cCtrlDateTimePicker dtpDialog = new cCtrlDateTimePicker(mContext, mStrDateTimeFormat, this.DialogType);
		
		dtpDialog.setIcon(R.drawable.batch_time);
		Calendar c = Calendar.getInstance();

		dtpDialog.setDateTime(c);

		dtpDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Set", dialog_onclick);
		dtpDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
				dialog_onclick);
		
		dtpDialog.show();
	}
	
	DialogInterface.OnClickListener dialog_onclick = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			try {
				if (dialog.getClass() != cCtrlDateTimePicker.class) {
					return;
				}
				switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						cCtrlDateTimePicker reminderDl = (cCtrlDateTimePicker) dialog;
	
						Date date = reminderDl.getDate();
						mStrSelectedDateTime = DateTimeHelper.dateToString(date, mStrDateTimeFormat);
						if (mIntReturnViewId != 0)
						{
							//View lVw = mActCalling.findViewById(mIntReturnViewId);
							LayoutInflater lInflater = LayoutInflater.from(mActCalling);
							ViewGroup lVwgrp = (ViewGroup) lInflater.inflate(mIntReturnViewId, null);
							View lVw = lVwgrp.findViewById(mIntReturnViewId);
							if (lVw instanceof CustomButton)
								((CustomButton)lVw).setText(mStrSelectedDateTime);
						}
						/*txt_date.setText(DateTimeHelper.dateToString(date,
								DateTimeHelper.NORMAL_DAY_OF_WEEK_FORMAT));*/
	
						break;
					case DialogInterface.BUTTON_NEGATIVE:
						mStrSelectedDateTime = "";
						break;
					default:
						break;
				}
			} catch (Exception ex) {
				Log.e("MainActivity", ex.getMessage());
			}
		}
	};
}
