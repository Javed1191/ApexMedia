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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.ContractorJobRequestAdapter;
import adapter.EmployerJobRequestAdapter;
import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;


public class FragmentContractorJobRequest extends Fragment
{


	View view;
	private RecyclerView job_proposla_recycler_view;
	private TextView tv_proposals_not_found;
	private ContractorJobRequestAdapter proposalsAdapter;
	private Utility utility;
	private String strGetJobRequest = Application_Constants.Main_URL+"keyword=contractor_job_requests";
	String jobId="",siteUserID="";
	private Button btn_find_talent;

	public FragmentContractorJobRequest()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_contractor_job_request,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		toolbar.setTitle("Job Requests");

		utility = new Utility(getActivity());
		job_proposla_recycler_view = (RecyclerView)view.findViewById(R.id.job_proposla_recycler_view);
		job_proposla_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
		tv_proposals_not_found = (TextView) view.findViewById(R.id.tv_proposals_not_found);
		btn_find_talent = (Button) view.findViewById(R.id.btn_find_talent);
		siteUserID = Shared_Preferences_Class.readString(getActivity(), Shared_Preferences_Class.USER_ID, "");


		getJobRequest(siteUserID);

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


	private void getJobRequest(String empId)
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("contractorId", empId);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			final ArrayList<JobRequest> feedsArrayList = new ArrayList<JobRequest>();

			serviceHandler.registerUser(params, strGetJobRequest, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status, str_msg;
					String requsestId, job_id, job_name, clint_id, clint_info, message, posted_date,client_pic;
					try {
						if (str_json != null) {
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if (str_status.equalsIgnoreCase("true")) {
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("Paymentlist");

								for (int i = 0; i < jArray.length(); i++)
								{
									JSONObject Obj = jArray.getJSONObject(i);
									// A category variables
									requsestId = Obj.getString("requsestId");
									job_id = Obj.getString("job_id");
									job_name = Obj.getString("job_name");
									clint_id= Obj.getString("clint_id");

									clint_info = Obj.getString("clint_info");
									client_pic = Obj.getString("client_pic");
									message = Obj.getString("message");
									posted_date = Obj.getString("posted_date");


									JobRequest jobProposals = new JobRequest(requsestId,job_id,job_name,clint_info,client_pic,message,posted_date);
									feedsArrayList.add(jobProposals);

								}

								if (str_status.equals("true")) {
									tv_proposals_not_found.setVisibility(View.GONE);
									job_proposla_recycler_view.setVisibility(View.VISIBLE);
									proposalsAdapter = new ContractorJobRequestAdapter(getActivity(), feedsArrayList);
									job_proposla_recycler_view.setAdapter(proposalsAdapter);
								} else {
									tv_proposals_not_found.setVisibility(View.VISIBLE);
									job_proposla_recycler_view.setVisibility(View.GONE);

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
