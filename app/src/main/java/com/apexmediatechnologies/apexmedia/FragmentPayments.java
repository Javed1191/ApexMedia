package com.apexmediatechnologies.apexmedia;


import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.JobFeedsAdapter;
import adapter.PaymentAdapter;
import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;


public class FragmentPayments extends Fragment
{


	View view;
	private RecyclerView payment_recycler_view;
	private TextView tv_paments_not_found;
	private PaymentAdapter paymentAdapter;
	private Toolbar toolbar;
	private Utility utility;
	private String strViewPayments = Application_Constants.Main_URL+"keyword=view_payments";
	String productId="",userId="";
	List<String> statusList;
	private Spinner spn_status;

	public FragmentPayments()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_payments,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		utility = new Utility(getActivity());

		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		toolbar.setTitle("Payments");

		spn_status = (Spinner) view.findViewById(R.id.spn_status);
		payment_recycler_view = (RecyclerView)view.findViewById(R.id.payment_recycler_view);


		payment_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
		tv_paments_not_found = (TextView) view.findViewById(R.id.tv_paments_not_found);

		userId = Shared_Preferences_Class.readString(getActivity(), Shared_Preferences_Class.USER_ID, "");

		statusList = new ArrayList<>();
		statusList.add("All");
		statusList.add("Pending");
		statusList.add("Completed");
		statusList.add("Cancelled");

		ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item,
				statusList);
		spn_status.setAdapter(spinnerArrayAdapter);

		spn_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				getPayments(userId,spn_status.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});

		return view;
	}


	/*public void addData()
	{
		final ArrayList<Feeds> feedsArrayList = new ArrayList<Feeds>();


		for (int i = 0; i < 10; i++)
		{

			Feeds feeds = new Feeds("User Name "+i);
			feedsArrayList.add(feeds);

		}

		tv_feeds_not_found.setVisibility(View.GONE);
		feed_recycler_view.setVisibility(View.VISIBLE);
		feedsAdapter = new FeedsAdapter(getActivity(), feedsArrayList);
		feed_recycler_view.setAdapter(feedsAdapter);
	}*/


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		return super.onOptionsItemSelected(item);
	}


	private void getPayments(String contractorId, String status)
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("contractorId", contractorId);
			params.put("status", status);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			final ArrayList<Payments> paymentsArrayList = new ArrayList<Payments>();

			serviceHandler.registerUser(params, strViewPayments, new ServiceHandler.VolleyCallback() {
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
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("Paymentlist");

								for (int i = 0; i < jArray.length(); i++)
								{
									JSONObject Obj = jArray.getJSONObject(i);
									// A category variables
									paymentId = Obj.getString("paymentId");
									project = Obj.getString("project");
									projectId = Obj.getString("projectId");
									status = Obj.getString("status");
									paymentDate = Obj.getString("paymentDate");
									amount = Obj.getString("amount");
									description = Obj.getString("description");

									Payments payments = new Payments(paymentId,status,paymentDate,amount,description,project,projectId);
									paymentsArrayList.add(payments);

								}

								if (str_status.equals("true")) {
									tv_paments_not_found.setVisibility(View.GONE);
									payment_recycler_view.setVisibility(View.VISIBLE);
									paymentAdapter = new PaymentAdapter(getActivity(), paymentsArrayList);
									payment_recycler_view.setAdapter(paymentAdapter);
								} else {
									tv_paments_not_found.setVisibility(View.VISIBLE);
									payment_recycler_view.setVisibility(View.GONE);

								}


							} else {
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
