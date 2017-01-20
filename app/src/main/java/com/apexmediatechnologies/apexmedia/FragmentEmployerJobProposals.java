package com.apexmediatechnologies.apexmedia;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.EmployerJobProposalsAdapter;
import adapter.JobProposalsAdapter;
import services.Application_Constants;
import services.ServiceHandler;
import services.Utility;


public class FragmentEmployerJobProposals extends Fragment
{


	View view;
	private RecyclerView job_proposla_recycler_view;
	private TextView tv_proposals_not_found;
	private EmployerJobProposalsAdapter proposalsAdapter;
	private Utility utility;
	private String strGetJobProposals = Application_Constants.Main_URL+"keyword=getJobProposals";
	String jobId="";

	public FragmentEmployerJobProposals()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_employer_job_proposals,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		utility = new Utility(getActivity());
		job_proposla_recycler_view = (RecyclerView)view.findViewById(R.id.job_proposla_recycler_view);
		job_proposla_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
		tv_proposals_not_found = (TextView) view.findViewById(R.id.tv_proposals_not_found);

		try
		{
			Bundle arguments = getArguments();

			if(arguments != null && arguments.containsKey("jobId"))
			{
				if(!getArguments().getString("jobId").equals("")&&!getArguments().getString("jobId").equals(null))
				{
					//addData();
					//getJobFeeds(userId);

					jobId = getArguments().getString("jobId");

					if(utility.checkInternet())
					{
						// new GetProductDetails(product_id).execute();

						getJobProposals(jobId);
					}
					else
					{
						Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_SHORT).show();


					}
				}
			}
			else
			{

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

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


	private void getJobProposals(String jobId)
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("jobId", jobId);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			final ArrayList<JobProposals> feedsArrayList = new ArrayList<JobProposals>();

			serviceHandler.registerUser(params, strGetJobProposals, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status, str_msg;
					String jobId, rating, amount_per_hour, estimated_duration, work_type, posted_userId, contractor_first_name,
							work_rate, start_type, user_country_name, hours_per_week, job_title, created_at, updated_on, job_description,
							contractor_last_name, user_image, schedule_date,awarded,proposalId,contractorId;
					boolean is_awarded =false;
					try {
						if (str_json != null) {
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if (str_status.equalsIgnoreCase("true")) {
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("Propojals");

								for (int i = 0; i < jArray.length(); i++)
								{
									JSONObject Obj = jArray.getJSONObject(i);
									// A category variables
									jobId = Obj.getString("jobId");
									job_description = Obj.getString("description");
									rating = Obj.getString("rating");
									amount_per_hour = Obj.getString("amount_per_hour");
									hours_per_week = Obj.getString("hours_per_week");
									estimated_duration = Obj.getString("estimated_duration");
									created_at = Obj.getString("created_at");
									user_image = Obj.getString("user_image");
									contractor_first_name= Obj.getString("contractor_first_name");
									contractor_last_name = Obj.getString("contractor_last_name");
									awarded = Obj.getString("awarded");
									proposalId  = Obj.getString("proposalId");
									contractorId  = Obj.getString("contractorId");

									if(awarded.equalsIgnoreCase("1"))
									{
										is_awarded = true;
									}



									JobProposals jobProposals = new JobProposals(jobId,rating,amount_per_hour,estimated_duration,job_description,created_at,contractor_first_name+" "+contractor_last_name,hours_per_week,user_image,awarded,proposalId,contractorId);
									feedsArrayList.add(jobProposals);

								}

								if (str_status.equals("true")) {
									tv_proposals_not_found.setVisibility(View.GONE);
									job_proposla_recycler_view.setVisibility(View.VISIBLE);
									proposalsAdapter = new EmployerJobProposalsAdapter(getActivity(), feedsArrayList,is_awarded);
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
