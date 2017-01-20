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
import adapter.MyProjectsAdapter;
import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;


public class FragmentMyProjects extends Fragment
{


	View view;
	private RecyclerView my_projects_recycler_view;
	private TextView tv_my_projects_not_found;
	private MyProjectsAdapter myProjectsAdapter;
	private Toolbar toolbar;
	private Utility utility;
	private String strGetMyProjects = Application_Constants.Main_URL+"keyword=get_myAcceptedJobs";
	String productId="",userId="";

	public FragmentMyProjects()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_my_projects,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		utility = new Utility(getActivity());
		my_projects_recycler_view = (RecyclerView)view.findViewById(R.id.my_projects_recycler_view);
		my_projects_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
		tv_my_projects_not_found = (TextView) view.findViewById(R.id.tv_my_projects_not_found);


		toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		toolbar.setTitle("My Jobs");


		userId = Shared_Preferences_Class.readString(getActivity(), Shared_Preferences_Class.USER_ID, "");

		getMyProjects(userId);

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


	private void getMyProjects(String contractorId)
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("contractorId", contractorId);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			final ArrayList<JobFeeds> feedsArrayList = new ArrayList<JobFeeds>();

			serviceHandler.registerUser(params, strGetMyProjects, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status, str_msg;
					String job_progress_status,jobId, job_budget, work_rate_description, job_skills, work_type, posted_userId, posted_user_firt_name,
							work_rate, start_type, user_country_name, hours_per_week, job_title, created_at, updated_on, job_description,
							posted_user_last_name, user_image, schedule_date,empId;
					try {
						if (str_json != null) {
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if (str_status.equalsIgnoreCase("true")) {
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("joblist");
								for (int i = 0; i < jArray.length(); i++) {
									JSONObject Obj = jArray.getJSONObject(i);
									// A category variables
									job_progress_status = Obj.getString("job_progress_status");
									jobId = Obj.getString("jobId");
									job_title = Obj.getString("job_title");
									job_description = Obj.getString("job_description");
									work_type = Obj.getString("work_type");
									work_rate = Obj.getString("work_rate");
									empId = Obj.getString("empId");
									work_rate_description = Obj.getString("work_rate_description");
									hours_per_week = Obj.getString("hours_per_week");
									job_budget = Obj.getString("job_budget");
									job_skills = Obj.getString("job_skills");
									start_type = Obj.getString("start_type");
									schedule_date = Obj.getString("schedule_date");
									updated_on = Obj.getString("updated_on");
									created_at = Obj.getString("created_at");
									posted_userId = Obj.getString("posted_userId");
									posted_user_firt_name = Obj.getString("posted_user_firt_name");
									posted_user_last_name = Obj.getString("posted_user_last_name");
									user_country_name = Obj.getString("user_country_name");
									user_image = Obj.getString("user_image");


									JobFeeds feeds = new JobFeeds(job_progress_status,jobId, job_budget, work_rate_description, job_skills, work_type,
											posted_userId, posted_user_firt_name, work_rate, start_type, user_country_name,
											hours_per_week, job_title, created_at, updated_on, job_description, posted_user_last_name,
											user_image, schedule_date,empId);
									feedsArrayList.add(feeds);

								}

								if (str_status.equals("true")) {
									tv_my_projects_not_found.setVisibility(View.GONE);
									my_projects_recycler_view.setVisibility(View.VISIBLE);
									myProjectsAdapter = new MyProjectsAdapter(getActivity(), feedsArrayList);
									my_projects_recycler_view.setAdapter(myProjectsAdapter);
								} else {
									tv_my_projects_not_found.setVisibility(View.VISIBLE);
									my_projects_recycler_view.setVisibility(View.GONE);

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
