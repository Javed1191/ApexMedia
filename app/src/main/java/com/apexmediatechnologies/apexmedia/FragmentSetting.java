package com.apexmediatechnologies.apexmedia;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.PaymentAdapter;
import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;


public class FragmentSetting extends Fragment
{


	View view;
	private Toolbar toolbar;
	private Utility utility;
	private String strSettings = Application_Constants.Main_URL+"keyword=settings";
	String productId="",userId="",str_type="";
	private Switch switch_email;
	private Button btn_close_account;
	int is_email_alert=0;

	public FragmentSetting()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_setting,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		utility = new Utility(getActivity());

		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		toolbar.setTitle("Settings");

		switch_email = (Switch) view.findViewById(R.id.switch_email);
		btn_close_account = (Button) view.findViewById(R.id.btn_close_account);

		userId = Shared_Preferences_Class.readString(getActivity(), Shared_Preferences_Class.USER_ID, "");
		str_type = Shared_Preferences_Class.readString(getActivity(),Shared_Preferences_Class.USER_TYPE,"");

		switch_email.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(isChecked)
				{
					is_email_alert =1;
				}
				else
				{
					is_email_alert=0;
				}

			}
		});

		btn_close_account.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				setSettings(userId,String.valueOf(is_email_alert));
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
