package com.apexmediatechnologies.apexmedia;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;


public class FragmentBankAccount extends Fragment
{


	View view;
	private String strGetBankDetails = Application_Constants.Main_URL+"keyword=view_bank_account";
	private Utility utility;
	private TextView tv_ac_number,tv_ac_holder_name,tv_bank_name,tv_branch_name,tv_ifsc_code,tv_swift_code,
			tv_ac_type,tv_city,tv_state,tv_pin_code;

	String userId;

	public FragmentBankAccount()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_bank_account,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		toolbar.setTitle("Bank Account");

		utility = new Utility(getActivity());
		userId = Shared_Preferences_Class.readString(getActivity(), Shared_Preferences_Class.USER_ID, "");

		tv_ac_number = (TextView) view.findViewById(R.id.tv_ac_number);
		tv_ac_holder_name= (TextView) view.findViewById(R.id.tv_ac_holder_name);
		tv_bank_name = (TextView)view. findViewById(R.id.tv_bank_name);
		tv_branch_name = (TextView)view. findViewById(R.id.tv_branch_name);
		tv_ifsc_code = (TextView) view.findViewById(R.id.tv_ifsc_code);
		tv_swift_code = (TextView) view.findViewById(R.id.tv_swift_code);
		tv_ac_type  = (TextView)view. findViewById(R.id.tv_ac_type);
		tv_state = (TextView)view. findViewById(R.id.tv_state);
		tv_city = (TextView)view. findViewById(R.id.tv_city);
		tv_pin_code = (TextView) view.findViewById(R.id.tv_pin_code);

		getBankDetails(userId);


		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO Add your menu entries here
		super.onCreateOptionsMenu(menu, inflater);

		MenuItem edit_ac = menu.findItem(R.id.action_edit_ac);
		edit_ac.setVisible(true);
		//getActivity().getMenuInflater().inflate(R.menu.main, menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.


		return super.onOptionsItemSelected(item);
	}

	private void getBankDetails(String userId)
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("userId", userId);


			ServiceHandler serviceHandler = new ServiceHandler(getActivity());

			serviceHandler.registerUser(params, strGetBankDetails, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status,str_msg;
					String  accNum="",	accHolderName="",	bankName="",branch="",state="",city="",	pinCode="",ifscCode="",swiftCode="",
					accType="";

					try {
						if(str_json!=null)
						{
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if(str_status.equalsIgnoreCase("true"))
							{


								JSONObject jsonObject = new JSONObject();
								jsonObject = jobject.getJSONObject("account");

									// A category variables
									accNum = jsonObject.getString("accNum");
									accHolderName = jsonObject.getString("accHolderName");
									bankName = jsonObject.getString("bankName");
									branch = jsonObject.getString("branch");
									state = jsonObject.getString("state");
									city = jsonObject.getString("city");
									pinCode = jsonObject.getString("pinCode");
									ifscCode = jsonObject.getString("ifscCode");
									swiftCode= jsonObject.getString("swiftCode");
									accType= jsonObject.getString("accType");


								tv_ac_number.setText(accNum);
								tv_ac_holder_name.setText(accHolderName);
								tv_bank_name.setText(bankName);
								tv_branch_name.setText(branch);
								tv_state.setText(state);
								tv_city.setText(city);
								tv_pin_code.setText(pinCode);
								tv_ifsc_code.setText(ifscCode);
								tv_swift_code.setText(swiftCode);
								tv_ac_type.setText(accType);


							}
							else
							{
								Toast.makeText(getActivity(), str_msg, Toast.LENGTH_SHORT).show();
							}
						}
						else
						{
							Toast.makeText(getActivity(), "This may be server issue",Toast.LENGTH_SHORT).show();
						}

					} catch (JSONException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		else
		{
			Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_SHORT).show();
		}

	}

}
