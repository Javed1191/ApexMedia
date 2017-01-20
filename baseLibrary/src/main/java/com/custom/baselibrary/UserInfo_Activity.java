package com.custom.baselibrary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfo_Activity extends DashBoardActivity
{

	private TextView txt_EmpDptName, txt_Empecode, txt_EmpName,
			txt_EmpDesgName, txt_EmpWorkEmail, txt_EmpWorkMobile,
			txt_EmpPersonalMobile, txt_EmpReportsToName,txt_lblname;
	private Commons common;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_info_);
		setHeader("User Information", true, true);

		txt_Empecode = (TextView) findViewById(R.id.txt_Empecode);
		txt_EmpName = (TextView) findViewById(R.id.txt_EmpName);
		txt_EmpDptName = (TextView) findViewById(R.id.txt_EmpDptName);
		txt_EmpDesgName = (TextView) findViewById(R.id.txt_EmpDesgName);
		txt_EmpWorkEmail = (TextView) findViewById(R.id.txt_EmpWorkEmail);
		txt_EmpWorkMobile = (TextView) findViewById(R.id.txt_EmpWorkMobile);
		txt_EmpPersonalMobile = (TextView) findViewById(R.id.txt_EmpPersonalMobile);
		txt_EmpReportsToName = (TextView) findViewById(R.id.txt_EmpReportsToName);
	
		common = new Commons(UserInfo_Activity.this);
		if (common.checkInternet()) {
			new GetEmpInfo().execute();
		

		} else {

			try {

				common.showAlertDialog(UserInfo_Activity.this,
						"No Internet Connection",
						"You don't have internet connection.", false);
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class GetEmpInfo extends AsyncTask<Void, Void, Void> {
		// private ProgressDialog dialog1;
		String status;
		String msg;

		JSONObject jsonEmpInfo;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgressDialog(UserInfo_Activity.this,
					"Loading....", ProgressDialog.STYLE_SPINNER,
					false);

		}

		@Override
		protected Void doInBackground(Void... params) {
			try {

				JSONObject jsonOutput = AIMSAPI.Instance().GetData(
						"GetEmpInfo", "pStrEmpCode", LoginInfo.gStrUserCode);
				status = jsonOutput.getString("Status");
				if (status.equalsIgnoreCase("Success")) 
				{
					JSONArray jsonArray = jsonOutput.getJSONArray("Data");
					if (jsonArray != null && jsonArray.length() > 0)
						jsonEmpInfo = jsonArray.getJSONObject(0);
				} else
					msg = jsonOutput.getString("ErrorMessage");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void args) 
		{
			closeProgressDialog();
			if (status != null && !status.equals("")) {
				if (status.equalsIgnoreCase("Success")) {
					try {
						if (jsonEmpInfo != null && jsonEmpInfo.length() > 0) {
							txt_Empecode.setText(jsonEmpInfo
									.getString("EmpCode"));
							txt_EmpName.setText(jsonEmpInfo
									.getString("EmpName"));
							txt_EmpDptName.setText(jsonEmpInfo
									.getString("DeptName"));
							txt_EmpDesgName.setText(jsonEmpInfo
									.getString("DesgName"));
							txt_EmpWorkEmail.setText(jsonEmpInfo
									.getString("WorkEmail"));
							txt_EmpWorkMobile.setText(jsonEmpInfo
									.getString("WorkMobile"));
							txt_EmpPersonalMobile.setText(jsonEmpInfo
									.getString("PersonalMobile"));
							txt_EmpReportsToName.setText(jsonEmpInfo
									.getString("ReportsToName"));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					Toast.makeText(UserInfo_Activity.this, msg,
							Toast.LENGTH_LONG).show();
				}
			}
		}

		/**
		 * Function to display simple Alert Dialog
		 * 
		 * @param context
		 *            - application context
		 * @param title
		 *            - alert dialog title
		 * @param message
		 *            - alert message
		 * @param status
		 *            - success/failure (used to set icon)
		 * */
		public void showAlertDialog(Context context, String title,
				String message, Boolean status) {
			AlertDialog alertDialog = new AlertDialog.Builder(context).create();

			// Setting Dialog Title
			alertDialog.setTitle(title);

			// Setting Dialog Message
			alertDialog.setMessage(message);

			// Setting alert dialog icon
			alertDialog
					.setIcon((status) ? R.drawable.bullet : R.drawable.bullet);

			// Setting OK Button
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});

			// Showing Alert Message
			alertDialog.show();
		}
	}

}
