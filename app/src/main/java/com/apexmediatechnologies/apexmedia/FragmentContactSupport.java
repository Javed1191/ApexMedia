package com.apexmediatechnologies.apexmedia;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;


public class FragmentContactSupport extends Fragment
{


	View view;
	private Toolbar toolbar;
	private Utility utility;
	private String strSettings = Application_Constants.Main_URL+"keyword=settings";
	String productId="",userId="";
	private TextView tv_email,tv_call;
	private ImageView img_call,img_email;
	private LinearLayout lay_call,lay_email;
	public FragmentContactSupport()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_contact_support,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		utility = new Utility(getActivity());

		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		toolbar.setTitle("Contact Support");

		tv_email = (TextView) view.findViewById(R.id.tv_email);
		tv_call = (TextView) view.findViewById(R.id.tv_call);
		img_call = (ImageView) view.findViewById(R.id.img_call);
		img_email = (ImageView) view.findViewById(R.id.img_email);
		lay_call = (LinearLayout) view.findViewById(R.id.lay_call);
		lay_email = (LinearLayout) view.findViewById(R.id.lay_email);

		userId = Shared_Preferences_Class.readString(getActivity(), Shared_Preferences_Class.USER_ID, "");


		lay_email.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
						"mailto", "info@apex.com", null));
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
				emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
				startActivity(Intent.createChooser(emailIntent, "Send email..."));
			}
		});
		lay_call.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "9999001100"));
				startActivity(intent);
			}
		});


		return view;
	}





	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		return super.onOptionsItemSelected(item);
	}


	private void setSettings(String contractorId, String email_alert)
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("contractorId", contractorId);
			params.put("email_alert", email_alert);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			final ArrayList<Payments> paymentsArrayList = new ArrayList<Payments>();

			serviceHandler.registerUser(params, strSettings, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status, str_msg;
					String paymentId, project, projectId, status, paymentDate, amount, description;
					try {
						if (str_json != null) {
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if (str_status.equalsIgnoreCase("true"))
							{
								Toast.makeText(getActivity(), str_msg, Toast.LENGTH_SHORT).show();

							}
							else
							{
								Toast.makeText(getActivity(), str_msg, Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(getActivity(), "This may be server issue", Toast.LENGTH_SHORT).show();
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}

	}




}
