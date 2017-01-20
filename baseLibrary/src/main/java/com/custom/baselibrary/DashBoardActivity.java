package com.custom.baselibrary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.SuperToast;

public abstract class DashBoardActivity extends Activity {

	private LayoutInflater inflater;
	private PopupWindow pw;
	private View popupView;
	private ImageView img;

	private Commons commons;
	public ProgressDialog barProgressDialog;
	public String gStrDateTimePickerValue = "";
	Handler updateBarHandler;
	String ScrnTabCall;
	private DataManager dbManager;
	ProgressDialog dialog; 
	SyncMethods syncMethods;
	LoginInfo loginInfo;
	private ImageView dialogImgRight;
	private ImageView dialogImgLeft;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbManager = new DataManager(DashBoardActivity.this);
		dialog= new ProgressDialog(DashBoardActivity.this);
		loginInfo = new LoginInfo(DashBoardActivity.this);
		commons = new Commons(DashBoardActivity.this);
		syncMethods = new SyncMethods(DashBoardActivity.this);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		popupView = inflater.inflate(R.layout.menu_layout, null, false);
		updateBarHandler = new Handler();
		
	}

	public void setHeader(String title, boolean btnHomeVisible,
			boolean btnFeedbackVisible) {
		ViewStub stub = (ViewStub) findViewById(R.id.vsHeader);
		View inflated = stub.inflate();

		TextView txtTitle = (TextView) inflated.findViewById(R.id.txtHeading);
		txtTitle.setText(title);

		ImageView btnHome = (ImageView) inflated.findViewById(R.id.btnHome);
		if (!btnHomeVisible)
			btnHome.setVisibility(View.INVISIBLE);

		ImageView btnFeedback = (ImageView) inflated
				.findViewById(R.id.btnFeedback);
		if (!btnFeedbackVisible)
			btnFeedback.setVisibility(View.INVISIBLE);
	}

	/**
	 * Home button click handler
	 * 
	 * @param v
	 */
	public void btnHomeClick(View v) 
	{
		// Intent intent = new Intent(getApplicationContext(),
		// SubMenuActivity.class);
		// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// startActivity(intent);
		Intent i1 = new Intent();
		//i1.setClassName(LoginInfo.gStrPackageName, LoginInfo.gStrLaunchActivity);
		
		i1.setClassName(getApplicationContext(), "com.eMsysSolutionsPvtLtd.enquireapp.MenuScreenActivity");
		i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i1);
		
	}


	public void btnFeedbackClick(View view) {
		pw = new PopupWindow(getApplicationContext());
		pw.setTouchable(true);
		pw.setFocusable(true);
		pw.setOutsideTouchable(true);
		pw.setTouchInterceptor(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					pw.dismiss();

					return true;
				}

				return false;
			}
		});

		pw.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		pw.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		pw.setOutsideTouchable(false);
		pw.setContentView(popupView);
		pw.showAsDropDown(view, 0, 0);
	}

	/*public void clickOne(View view) {
		pw.dismiss();
		
		{
			if (commons.checkInternet()) {

				// Internet Connection is Present
				// make HTTP requests
				*//*Intent iLogin = new Intent(DashBoardActivity.this,
						LoginScreenActivity.class);
				iLogin.putExtra("LaunchActivity", LoginInfo.gStrLaunchActivity);
				iLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(iLogin);
				finish();*//*
				Toast.makeText(DashBoardActivity.this, "Print",
						Toast.LENGTH_LONG).show();

			} else {
				// Internet connection is not present
				// Ask user to connect to Internet
				showAlertDialog(DashBoardActivity.this,
						"No Internet Connection",
						"You don't have internet connection.", false);
			}
		}
	}*/

	/*public void clickTwo(View view) {

		
		{
			if (commons.checkInternet()) {
				barProgressDialog = new ProgressDialog(DashBoardActivity.this);

				barProgressDialog.setTitle("eMsys Solutions Pvt Ltd...");
				barProgressDialog.setMessage("Refreshing the application ...");
				barProgressDialog
						.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
				barProgressDialog.setProgress(0);
				barProgressDialog.setMax(100);
				barProgressDialog.show();

				new Thread(new Runnable() {
					@Override
					public void run() {
						try {

							// Here you should write your time consuming task...
							while (barProgressDialog.getProgress() <= barProgressDialog
									.getMax()) {

								Thread.sleep(2000);

								updateBarHandler.post(new Runnable() {

									public void run() {

										barProgressDialog
												.incrementProgressBy(20);

									}

								});

								if (barProgressDialog.getProgress() == barProgressDialog
										.getMax()) {

									barProgressDialog.dismiss();
									

								}
							}
						} catch (Exception e) {
						}
					}
				}).start();
			} else {
				showAlertDialog(DashBoardActivity.this,
						"No Internet Connection",
						"You don't have internet connection.", false);
			}
		}

		pw.dismiss();
		 
	}*/

	public void clickThree(View view) 
	{

		
		final Dialog dialog = new Dialog(DashBoardActivity.this);
		dialog.setContentView(R.layout.activity_email_setting);
		dialog.setTitle("Email Setting...");
		Window window = dialog.getWindow();
		window.setLayout(650, 700);
		//window.setLayout(700, 1000);
		// set the custom dialog components - text, image and button

		Button dialogBtnSave= (Button) dialog.findViewById(R.id.btn_eSave);
		Button dialogBtnCancel = (Button) dialog.findViewById(R.id.btn_eCancel);
		final EditText dialogEdtEmail = (EditText) dialog.findViewById(R.id.edt_d_email);
		final EditText dialogEdtPassword = (EditText) dialog.findViewById(R.id.edt_d_password);
		CheckBox dialogChkEdit = (CheckBox) dialog.findViewById(R.id.chk_edit);
		
		// if button is clicked, close the custom dialog
		dialogBtnSave.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				try
				{
					 String Str_emailId="";
					 String Str_epassword="";
					
					Str_emailId = dialogEdtEmail.getText().toString();
					Str_epassword = dialogEdtPassword.getText().toString();
					
					if(commons.checkEmail(Str_emailId)==true && !Str_emailId.equals("") && !Str_epassword.equals(""))
					{
						Toast.makeText(DashBoardActivity.this, Str_emailId, Toast.LENGTH_SHORT).show();
						
						dbManager.open();
						dbManager.insertEmailSettingDetails(Str_emailId, Str_epassword);
						dbManager.close();
						
						dialog.dismiss();
					}
					else
					{
					
						 SuperToast superToast = new SuperToast(getApplicationContext());  
						 superToast.setDuration(SuperToast.Duration.SHORT); 
						 superToast.setText("Please Enter valid Email Id");  
						 superToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
						 superToast.setBackground(R.drawable.background_kitkat_red);
						 superToast.show();  
						dialogEdtEmail.setError("Enter Valid Email Id");
					}
					if(Str_epassword.equals(""))
					{
						SuperToast superToast = new SuperToast(getApplicationContext());  
						 superToast.setDuration(SuperToast.Duration.SHORT); 
						 superToast.setText("Please Enter Password");  
						 superToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
						 superToast.setBackground(R.drawable.background_kitkat_red);
						 superToast.show();  
						dialogEdtPassword.setError("Enter Password");
					}
							
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
		});
		
		dialogBtnCancel.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				dialog.dismiss();
				
			}
		});
		
		dialogChkEdit.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
			{
				// TODO Auto-generated method stub
				if(isChecked==true)
				{
					dialogEdtEmail.setEnabled(true);
					dialogEdtPassword.setEnabled(true);
				}
				else if(isChecked==false)
				{
					dialogEdtEmail.setEnabled(false);
					dialogEdtPassword.setEnabled(false);
				}
				
				
			}
		});
		
		dbManager.open();
		Cursor c = dbManager.selectAllEmailSettingDetails();
		String str_email,str_pass;
		
		if(c.moveToFirst())
		{
			do
			{
				 str_email = c.getString(0);
				 str_pass = c.getString(1);
				
			}while(c.moveToNext());
			
			dialogEdtEmail.setText(str_email);	
			dialogEdtPassword.setText(str_pass);	
			
			dialogEdtEmail.setEnabled(false);
			dialogEdtPassword.setEnabled(false);
			
			
			
		}
		dbManager.close();

		dialog.show();
	}

	public void clickFour(View view) 
	{
		pw.dismiss();
		Toast.makeText(getApplicationContext(), "Sysnc From Server", Toast.LENGTH_SHORT).show();
		new ExecuteMethods().execute();

	}

	
	public void clickfive(View view) 
	{
		//pw.dismiss();
		
		/* Intent myIntent;
		try 
		{
			Toast.makeText(getApplicationContext(), "Clicked On Five", Toast.LENGTH_SHORT).show();
			myIntent = new Intent(getApplicationContext(),Class.forName("com.eMsysSolutionsPvtLtd.enquireapp.MapActivity"));
			startActivity(myIntent );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		  
		Toast.makeText(getApplicationContext(), "Clicked On Five", Toast.LENGTH_SHORT).show();
		

	}
	
public void clickSix(View view) 
{

		
		Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
		
		 Intent myIntent;
		try 
		{
			if(commons.checkInternet()==true)
			{
				myIntent = new Intent(getApplicationContext(),Class.forName("com.eMsysSolutionsPvtLtd.enquireapp.MapActivity"));
				startActivity(myIntent );
			}
			else
			{
				SuperToast superToast = new SuperToast(getApplicationContext());  
				 superToast.setDuration(SuperToast.Duration.SHORT); 
				 superToast.setText("No Internet Connection...");  
				 superToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
				 superToast.setIcon(R.drawable.bullet,SuperToast.IconPosition.LEFT);
				 superToast.show();  
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pw.dismiss();
		 
	}
/*

public void clickSeven(View view) 
{

		
		Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
		try 
		{
			final Dialog dialog = new Dialog(DashBoardActivity.this);
			dialog.setContentView(R.layout.activity_email_setting);
			dialog.setTitle("Pdf Image Setting...");
			Window window = dialog.getWindow();
			window.setLayout(500, 500);
			//window.setLayout(700, 1000);
			// set the custom dialog components - text, image and button
			
			*/
/*ImageView image = (ImageView) dialog.findViewById(R.id.image);
			image.setImageResource(R.drawable.ic_launcher);*//*

 
			 dialogImgRight= (ImageView) dialog.findViewById(R.id.img_right_image);
			 dialogImgLeft = (ImageView) dialog.findViewById(R.id.img_left_image);
			Button dialogBtnSave = (Button) dialog.findViewById(R.id.btn_save_image);
			CheckBox dialogChkEdit = (CheckBox) dialog.findViewById(R.id.chk_is_image);
			//final ImageView dialogImgLeft = (ImageView) dialog.findViewById(R.id.img_left_image);
			//final ImageView dialogImgRight = (ImageView) dialog.findViewById(R.id.img_right_image);
			
			getPdfImages();
			
			if(dialogChkEdit.isChecked()==true)
			{
				dialogImgLeft.setEnabled(true);
				dialogImgRight.setEnabled(true);
				
			}
			else
			{
				dialogImgLeft.setEnabled(false);
				dialogImgRight.setEnabled(false);
			}
			*/
/*dbManager.open();
			Cursor cur = dbManager.selectPdfImages();
			Bitmap bt_left_image;
			byte[] blb_left_image;
			Bitmap bt_right_image;
			byte[] blb_right_image;
			//byte[] blb_right_image;
			
			if (cur.moveToFirst())
			{
				do
				{
					blb_left_image = cur.getBlob(0);
					blb_right_image = cur.getBlob(1);
					cur.close();
				}
				while(cur.moveToNext());
				
				cur.close();
				dbManager.close();
				
				bt_left_image = Utility.getPhoto(blb_left_image);
				bt_right_image = Utility.getPhoto(blb_right_image);
				
				dialogImgLeft.setImageBitmap(bt_left_image);
				dialogImgRight.setImageBitmap(bt_right_image);
				
				dialogImgLeft.setEnabled(false);
				dialogImgRight.setEnabled(false);
			}*//*

			
			
			// if button is clicked, close the custom dialog
			dialogImgRight.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					try
					{
						Intent myIntent;
						myIntent = new Intent(getApplicationContext(),Class.forName("com.eMsysSolutionsPvtLtd.enquireapp.Sd_image_activity"));
						myIntent.putExtra("pdf_image", "right");
						startActivity(myIntent );
						
						getPdfImages();
						
						*/
/*dbManager.open();
						Cursor cur = dbManager.selectPdfImages();
						
						//byte[] blb_right_image;
						
						if (cur.moveToFirst())
						{
							do
							{
								blb_right_image = cur.getBlob(1);
								//blb_right_image = cur.getBlob(1);
								cur.close();+
							}
							while(cur.moveToNext());
							
							cur.close();
							dbManager.close();
							
							bt_right_image = Utility.getPhoto(blb_right_image);
							dialogImgRight.setImageBitmap(bt_right_image);
						}*//*

						
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
				}
			});
			
			dialogImgLeft.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					
					try
					{
						 Intent myIntent;
						myIntent = new Intent(getApplicationContext(),Class.forName("com.eMsysSolutionsPvtLtd.enquireapp.Sd_image_activity"));
						myIntent.putExtra("pdf_image", "left");
						startActivity(myIntent );
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
				}
			});
			
			dialogChkEdit.setOnCheckedChangeListener(new OnCheckedChangeListener() 
			{
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
				{
					// TODO Auto-generated method stub
					if(isChecked==true)
					{
						dialogImgLeft.setEnabled(true);
						dialogImgRight.setEnabled(true);
						
					}
					else if(isChecked==false)
					{
						dialogImgLeft.setEnabled(false);
						dialogImgRight.setEnabled(false);
					}
					
				}
			});
			dialogBtnSave.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					getPdfImages();
				}
			});
			
			
			dialog.show();
			
			
			
		} 
	catch (Exception e) 
	{
			// TODO Auto-generated catch block
			e.printStackTrace();
	}

		pw.dismiss();
		 
	}
*/


	/*public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting alert dialog icon
		alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}*/

	public boolean CheckMandatory(int pIntActivityLayoutId, int pIntMainLayoutId) {
		// boolean lBlRetVal = true;
		boolean lBlRetVal = true;
		try {
			LayoutInflater lInflater = LayoutInflater
					.from(DashBoardActivity.this);
			ViewGroup lVw = (ViewGroup) lInflater.inflate(pIntActivityLayoutId,
					null);
			View lvMainView = lVw.findViewById(pIntMainLayoutId);
			if (lvMainView != null) {
				for (int i = 0; i < lVw.getChildCount(); i++) {
					View view = lVw.getChildAt(i);
					if (!lBlRetVal)
						break;
					if (view.getId() == lvMainView.getId()) {
						if (lvMainView instanceof ScrollView) {
							lBlRetVal = GetMandatoryOfParentViews(lvMainView);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lBlRetVal;
	}

	private boolean GetMandatoryOfParentViews(View pView) {
		// boolean lBlRetVal = true;
		boolean lBlRetVal = true;
		try {
			if (pView instanceof ScrollView) {
				ScrollView lScrlView = (ScrollView) pView;
				for (int i = 0; i < lScrlView.getChildCount(); i++) {
					View lvwChild = lScrlView.getChildAt(i);
					if (!lBlRetVal)
						break;
					if (lvwChild instanceof ScrollView)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof LinearLayout)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof RelativeLayout)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof TableLayout)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof HorizontalScrollView)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else
						lBlRetVal = CheckMandatoryControls(lvwChild);
				}
			} else if (pView instanceof LinearLayout) {
				LinearLayout lLnLtView = (LinearLayout) pView;
				for (int i = 0; i < lLnLtView.getChildCount(); i++) {
					View lvwChild = lLnLtView.getChildAt(i);
					if (!lBlRetVal)
						break;
					if (lvwChild instanceof ScrollView)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof LinearLayout)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof RelativeLayout)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof TableLayout)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof HorizontalScrollView)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else
						lBlRetVal = CheckMandatoryControls(lvwChild);
				}
			} // else if (pView instanceof LinearLayout)
			else if (pView instanceof RelativeLayout) {
				RelativeLayout lRtLtView = (RelativeLayout) pView;
				for (int i = 0; i < lRtLtView.getChildCount(); i++) {
					View lvwChild = lRtLtView.getChildAt(i);
					if (!lBlRetVal)
						break;
					if (lvwChild instanceof ScrollView)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof LinearLayout)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof RelativeLayout)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof TableLayout)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof HorizontalScrollView)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else
						lBlRetVal = CheckMandatoryControls(lvwChild);
				}
			} // else if (pView instanceof RelativeLayout)
			else if (pView instanceof TableLayout) {
				TableLayout lTblLtView = (TableLayout) pView;
				for (int i = 0; i < lTblLtView.getChildCount(); i++) {
					View lvwChild = lTblLtView.getChildAt(i);
					if (!lBlRetVal)
						break;
					if (lvwChild instanceof ScrollView)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof LinearLayout)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof RelativeLayout)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof TableLayout)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof HorizontalScrollView)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else
						lBlRetVal = CheckMandatoryControls(lvwChild);
				}
			} // else if (pView instanceof TableLayout)
			else if (pView instanceof HorizontalScrollView) {
				HorizontalScrollView lHsvlLtView = (HorizontalScrollView) pView;
				for (int i = 0; i < lHsvlLtView.getChildCount(); i++) {
					View lvwChild = lHsvlLtView.getChildAt(i);
					if (!lBlRetVal)
						break;
					if (lvwChild instanceof ScrollView)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof LinearLayout)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof RelativeLayout)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof TableLayout)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else if (lvwChild instanceof HorizontalScrollView)
						lBlRetVal = GetMandatoryOfParentViews(lvwChild);
					else
						lBlRetVal = CheckMandatoryControls(lvwChild);
				}
			} // else if (pView instanceof HorizontalScrollView)
		} // try
		catch (Exception e) {
			e.printStackTrace();
		}
		return lBlRetVal;
	}

	private boolean CheckMandatoryControls(View pCtrl)
	{
		boolean lBlRetVal = true;
		try {
			Activity lAct = DashBoardActivity.this;
			if (pCtrl instanceof CustomText) {
				CustomText ltv = (CustomText) lAct.findViewById(pCtrl.getId());
				if (ltv.mBlIsMandatory
						&& ltv.getText().toString().length() <= 0) {
					if (ltv.getVisibility() == View.VISIBLE) {
						ltv.setError("Empty Not Allowed");
						lBlRetVal = false;
					}
				}
			} else if (pCtrl instanceof CustomEditText) {
				CustomEditText lCedt = (CustomEditText) lAct.findViewById(pCtrl
						.getId());
				if (lCedt.mBlIsMandatory
						&& lCedt.getText().toString().length() <= 0) {
					if (lCedt.getVisibility() == View.INVISIBLE) {
						Log.d(String.valueOf(lCedt.getId()),
								String.valueOf(lCedt.getVisibility()));
						lCedt.setError("Empty Not Allowed");
						lCedt.requestFocus();
						lBlRetVal = false;
					}
				}
			} else if (pCtrl instanceof CustomButton) {
				CustomButton lCbtn = (CustomButton) lAct.findViewById(pCtrl
						.getId());
				if (lCbtn.mBlIsMandatory
						&& !lCbtn
								.getText()
								.toString()
								.equalsIgnoreCase(
										lCbtn.mStrMandatoryDefaultText)) {
					if (lCbtn.getVisibility() == View.VISIBLE) {
						lCbtn.setError("Option Not Selected");
						lCbtn.requestFocus();
						lBlRetVal = false;
					}
				}
			} else if (pCtrl instanceof cCtrlAutoTextView) {
				cCtrlAutoTextView lCatxt = (cCtrlAutoTextView) lAct
						.findViewById(pCtrl.getId());
				if (lCatxt.mBlIsMandatory
						&& lCatxt.getText().toString().length() <= 0) {
					if (lCatxt.getVisibility() == View.VISIBLE) {
						lCatxt.setError("Empty Not Allowed");
						lCatxt.requestFocus();
						lBlRetVal = false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lBlRetVal;
	}

	public View GetViewFromLayout(int pIntActivityLayoutId, int pIntViewId) {
		View lBlRetVal = null;
		try {
			LayoutInflater lInflater = LayoutInflater
					.from(DashBoardActivity.this);
			ViewGroup lVw = (ViewGroup) lInflater.inflate(pIntActivityLayoutId,
					null);
			if (lVw != null) {
				for (int i = 0; i < lVw.getChildCount(); i++) {
					View view = lVw.getChildAt(i);
					if (lBlRetVal != null)
						break;
					lBlRetVal = GetViewFromLayoutRecursive(view, pIntViewId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lBlRetVal;
	}

	private View GetViewFromLayoutRecursive(View pView, int pIntViewId) {
		// boolean lBlRetVal = true;
		View lBlRetVal = null;
		try {
			if (pView instanceof ScrollView) {
				ScrollView lScrlView = (ScrollView) pView;
				for (int i = 0; i < lScrlView.getChildCount(); i++) {
					View lvwChild = lScrlView.getChildAt(i);
					if (lBlRetVal != null)
						break;
					if (lvwChild instanceof ScrollView)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof LinearLayout)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof RelativeLayout)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof TableLayout)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof HorizontalScrollView)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else {
						if (lvwChild.getId() == pIntViewId)
							lBlRetVal = lvwChild;
					}
				}
			} else if (pView instanceof LinearLayout) {
				LinearLayout lLnLtView = (LinearLayout) pView;
				for (int i = 0; i < lLnLtView.getChildCount(); i++) {
					View lvwChild = lLnLtView.getChildAt(i);
					if (lBlRetVal != null)
						break;
					if (lvwChild instanceof ScrollView)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof LinearLayout)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof RelativeLayout)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof TableLayout)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof HorizontalScrollView)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else {
						if (lvwChild.getId() == pIntViewId)
							lBlRetVal = lvwChild;
					}
					
				}
			} // else if (pView instanceof LinearLayout)
			else if (pView instanceof RelativeLayout) {
				RelativeLayout lRtLtView = (RelativeLayout) pView;
				for (int i = 0; i < lRtLtView.getChildCount(); i++) {
					View lvwChild = lRtLtView.getChildAt(i);
					if (lBlRetVal != null)
						break;
					if (lvwChild instanceof ScrollView)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof LinearLayout)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof RelativeLayout)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof TableLayout)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof HorizontalScrollView)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else {
						if (lvwChild.getId() == pIntViewId)
							lBlRetVal = lvwChild;
					}
				}
			} // else if (pView instanceof RelativeLayout)
			else if (pView instanceof TableLayout) {
				TableLayout lTblLtView = (TableLayout) pView;
				for (int i = 0; i < lTblLtView.getChildCount(); i++) {
					View lvwChild = lTblLtView.getChildAt(i);
					if (lBlRetVal != null)
						break;
					if (lvwChild instanceof ScrollView)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof LinearLayout)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof RelativeLayout)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof TableLayout)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof HorizontalScrollView)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else {
						if (lvwChild.getId() == pIntViewId)
							lBlRetVal = lvwChild;
					}
				}
			} // else if (pView instanceof TableLayout)
			else if (pView instanceof HorizontalScrollView) {
				HorizontalScrollView lHsvlLtView = (HorizontalScrollView) pView;
				for (int i = 0; i < lHsvlLtView.getChildCount(); i++) {
					View lvwChild = lHsvlLtView.getChildAt(i);
					if (lBlRetVal != null)
						break;
					if (lvwChild instanceof ScrollView)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof LinearLayout)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof RelativeLayout)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof TableLayout)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else if (lvwChild instanceof HorizontalScrollView)
						lBlRetVal = GetViewFromLayoutRecursive(lvwChild,
								pIntViewId);
					else {
						if (lvwChild.getId() == pIntViewId)
							lBlRetVal = lvwChild;
					}
				}
			} // else if (pView instanceof HorizontalScrollView)
		} // try
		catch (Exception e) {
			e.printStackTrace();
		}
		return lBlRetVal;
	}

	public boolean setValueToViewFromLayout(int pIntParentLayoutId,
			int pIntReturnViewId, String pStrValue) {
		boolean lBlRetVal = false;
		try {
			View lView = findViewById(pIntReturnViewId);
			// View lView = GetViewFromLayout(pIntParentLayoutId,
			// pIntReturnViewId);
			if (lView != null) {
				if (lView instanceof CustomText) {
					CustomText lTxt = (CustomText) lView;
					lTxt.setText(pStrValue);
				} else if (lView instanceof TextView) {
					TextView lTxt = (TextView) lView;
					lTxt.setText(pStrValue);
				} else if (lView instanceof CustomButton) {
					CustomButton lBtn = (CustomButton) lView;
					lBtn.setText(pStrValue);
				} else if (lView instanceof CustomEditText) {
					CustomEditText lEdtTxt = (CustomEditText) lView;
					lEdtTxt.setText(pStrValue);
				} else if (lView instanceof cCtrlAutoTextView) {
					cCtrlAutoTextView lAutoTxt = (cCtrlAutoTextView) lView;
					lAutoTxt.setText(pStrValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lBlRetVal;
	}

	public void DateTimePickerShow(int pIntDialogType,
			String pStrDateTimeFormat, int pIntParentLayoutId, int pIntViewId) {
		// LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
		// View customView = inflater.inflate(R.layout.reminder_picker, null);

		cCtrlDateTimePicker dtpDialog = new cCtrlDateTimePicker(
				DashBoardActivity.this, pStrDateTimeFormat, pIntDialogType);
		dtpDialog.mIntReturnViewId = pIntViewId;
		dtpDialog.mIntParentLayoutId = pIntParentLayoutId;
		dtpDialog.setIcon(R.drawable.batch_time);
		Calendar c = Calendar.getInstance();

		dtpDialog.setDateTime(c);

		dtpDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Set", dialog_onclick);
		dtpDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
				dialog_onclick);

		dtpDialog.show();
	}
	
	public void DateTimePickerShow(int pIntDialogType,
			String pStrDateTimeFormat, int pIntParentLayoutId, int pIntViewId, String pStrCurrDateTime) {
		// LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
		// View customView = inflater.inflate(R.layout.reminder_picker, null);

		cCtrlDateTimePicker dtpDialog = new cCtrlDateTimePicker(
				DashBoardActivity.this, pStrDateTimeFormat, pIntDialogType);
		dtpDialog.mIntReturnViewId = pIntViewId;
		dtpDialog.mIntParentLayoutId = pIntParentLayoutId;
		dtpDialog.setIcon(R.drawable.batch_time);
		Calendar c = Calendar.getInstance();
		if (pStrCurrDateTime != "")
		{
			try {
				SimpleDateFormat lSdf = new SimpleDateFormat(pStrDateTimeFormat);
				Date lDtmDate =  lSdf.parse(pStrCurrDateTime);
				c.setTime(lDtmDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

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
				cCtrlDateTimePicker reminderDl = (cCtrlDateTimePicker) dialog;
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					Date date = reminderDl.getDate();
					reminderDl.mStrSelectedDateTime = DateTimeHelper
							.dateToString(date, reminderDl.mStrDateTimeFormat);
					if (reminderDl.mIntReturnViewId != 0) {
						setValueToViewFromLayout(reminderDl.mIntParentLayoutId,
								reminderDl.mIntReturnViewId,
								reminderDl.mStrSelectedDateTime);
						
					}
					OnDtpickerPositiveButtonClick();
					/*
					 * txt_date.setText(DateTimeHelper.dateToString(date,
					 * DateTimeHelper.NORMAL_DAY_OF_WEEK_FORMAT));
					 */

					break;
				case DialogInterface.BUTTON_NEGATIVE:
					reminderDl.mStrSelectedDateTime = "";
					break;
				default:
					break;
				}
			} catch (Exception ex) {
				Log.e("MainActivity", ex.getMessage());
			}
		}
	};
	
	public void showProgressDialog(Context pContext, String pStrMessage,
			int pIntDialogStyle, boolean pBlDialogCancelable) {
		barProgressDialog = new ProgressDialog(pContext);
		barProgressDialog.setMessage(pStrMessage);
		barProgressDialog.setProgressStyle(pIntDialogStyle);
		barProgressDialog.setCancelable(pBlDialogCancelable);
		barProgressDialog.show();
	}

	public void closeProgressDialog() {
		if (barProgressDialog != null) {
			barProgressDialog.dismiss();
			barProgressDialog = null;
		}
	}
	
	public void OnDtpickerPositiveButtonClick()
	{
		
	}
	public class ExecuteMethods extends AsyncTask<String,String, String>
	{

		@Override
		protected void onPreExecute() 
		{
			// TODO Auto-generated method stub
			dialog.setTitle("Loading....");
		    dialog.setMessage("Please wait.");
		    dialog.setIndeterminate(true);
		    dialog.setCancelable(false);
		    
		    dialog.show();
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) 
		{
			// TODO Auto-generated method stub
			// executing methods
			
			syncMethods.syncAllMethods();	
						
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) 
		{
		//	StrUserName = spinner1.getSelectedItem().toString();
			// TODO Auto-generated method stub
			if(dialog.isShowing())
			{
				dialog.dismiss();
			}
			super.onPostExecute(result);
		}

	}
	
	public void getPdfImages()
	{
		try
		{
			dbManager.open();
			Cursor cur = dbManager.selectPdfImages();
			Bitmap bt_left_image;
			byte[] blb_left_image;
			Bitmap bt_right_image;
			byte[] blb_right_image;
			//byte[] blb_right_image;
		
			if (cur.moveToFirst())
			{
				do
				{
					int id = cur.getInt(0);
					blb_left_image = cur.getBlob(1);
					blb_right_image = cur.getBlob(2);
					cur.close();
				}
				while(cur.moveToNext());
			
				cur.close();
				dbManager.close();
			
				bt_left_image = Utility.getPhoto(blb_left_image);
				bt_right_image = Utility.getPhoto(blb_right_image);
			
				dialogImgLeft.setImageBitmap(bt_left_image);
				dialogImgRight.setImageBitmap(bt_right_image);
			
				
			}
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	
	
}
