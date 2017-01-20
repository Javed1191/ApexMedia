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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.EmployerAwardedJobProposalsAdapter;
import adapter.EmployerJobProposalsAdapter;
import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;


public class FragmentEmployerAwardedJobProposals extends Fragment
{


	View view;

	private Utility utility;
	private String strGetJobProposals = Application_Constants.Main_URL+"keyword=proposalDetail";
	String jobId="",userId="";
	private ImageView img_user;
	private TextView tv_user_name,tv_address,tv_time,tv_hourly_rate,tv_hour,tv_desc,tv_est_duration,tv_approach,
			tv_skills,tv_bid_id,tv_submitted,tv_awarded;
	private RatingBar rating_user;

	public FragmentEmployerAwardedJobProposals()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_employer_awarded_job_proposals,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		utility = new Utility(getActivity());
		img_user = (ImageView) view.findViewById(R.id.img_user);

		tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
		tv_address = (TextView) view.findViewById(R.id.tv_address);
		tv_time = (TextView) view.findViewById(R.id.tv_time);
		tv_hourly_rate = (TextView) view.findViewById(R.id.tv_hourly_rate);
		tv_hour = (TextView) view.findViewById(R.id.tv_hour);
		tv_desc = (TextView) view.findViewById(R.id.tv_desc);
		tv_est_duration = (TextView) view.findViewById(R.id.tv_est_duration);
		tv_approach = (TextView) view.findViewById(R.id.tv_approach);
		tv_skills = (TextView) view.findViewById(R.id.tv_skills);
		tv_bid_id = (TextView) view.findViewById(R.id.tv_bid_id);
		tv_submitted = (TextView) view.findViewById(R.id.tv_submitted);
		tv_awarded = (TextView) view.findViewById(R.id.tv_awarded);

		rating_user = (RatingBar) view.findViewById(R.id.rating_user);

		userId = Shared_Preferences_Class.readString(getActivity(), Shared_Preferences_Class.USER_ID, "");

		try
		{
			Bundle arguments = getArguments();

			if(arguments != null)
			{

					//addData();
					//getJobFeeds(userId);

					jobId = getArguments().getString("proposalId");

					if(utility.checkInternet())
					{
						// new GetProductDetails(product_id).execute();

						getJobProposals(userId,jobId);
					}
					else
					{
						Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_SHORT).show();


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


	private void getJobProposals(String empId, String proposalId)
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("empId", empId);
			params.put("proposalId", proposalId);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			final ArrayList<JobProposals> feedsArrayList = new ArrayList<JobProposals>();

			serviceHandler.registerUser(params, strGetJobProposals, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status, str_msg;
					String senderId, senderName, senderImage, country, hourly_rate, estimated_duration, description,
							approach, skills, rating, bid_id, submitted_date, awarded_date,hours_per_week;
					try {
						if (str_json != null) {
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if (str_status.equalsIgnoreCase("true"))
							{
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("proposalDetail");

								for (int i = 0; i < jArray.length(); i++)
								{
									JSONObject Obj = jArray.getJSONObject(i);
									// A category variables
									senderId = Obj.getString("senderId");
									senderName = Obj.getString("senderName");
									senderImage = Obj.getString("senderImage");
									country = Obj.getString("country");
									hourly_rate = Obj.getString("hourly_rate");
									estimated_duration = Obj.getString("estimated_duration");
									description = Obj.getString("description");
									approach = Obj.getString("approach");
									skills= Obj.getString("skills");
									rating = Obj.getString("rating");
									bid_id = Obj.getString("bid_id");
									submitted_date = Obj.getString("submitted_date");
									awarded_date = Obj.getString("awarded_date");
									hours_per_week  = Obj.getString("hours_per_week");

									tv_user_name.setText(senderName);
									//Download image using picasso library
									Picasso.with(getActivity()).load(senderImage)
											.into(img_user);

									tv_address.setText(country);
									tv_hourly_rate.setText(hourly_rate);
									tv_hour.setText(hours_per_week);
									tv_est_duration.setText(estimated_duration);
									tv_desc.setText(description);
									tv_approach.setText(approach);
									tv_skills.setText(skills);
									rating_user.setRating(Float.parseFloat(rating));
									tv_bid_id.setText(bid_id);
									tv_submitted.setText(submitted_date);
									tv_awarded.setText(awarded_date);

								}

								if (str_status.equals("true"))
								{

								}
								else
								{


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
