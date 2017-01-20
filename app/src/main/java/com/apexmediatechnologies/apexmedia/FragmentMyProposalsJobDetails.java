package com.apexmediatechnologies.apexmedia;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.Utility;

public class FragmentMyProposalsJobDetails extends Fragment
{


	View view;
	private String strGetJobDetails = Application_Constants.Main_URL+"keyword=jobDetail";
	// private String strGetProducts ="http://192.168.1.3/hdfc_bso/android_web/action?xAction=products";
	private Utility utility;
	private String jobId="",employe_code="",product_image="",product_title="";
	private TextView tv_job_title,tv_posted_date,tv_action_title,tv_budget,tv_job_desc,tv_skills,
			tv_user_name,tv_user_country,tv_start_type;
	private ImageView img_user;
	private LinearLayout lay_product_details,lay_view_pdf;
	private boolean is_file;
	private RatingBar rating_user;
	private Button btn_send_proposal;

	public FragmentMyProposalsJobDetails()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_my_proposal_job_details,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		utility = new Utility(getActivity());

		tv_job_title = (TextView) view.findViewById(R.id.tv_job_title);
		tv_posted_date= (TextView) view.findViewById(R.id.tv_posted_date);
		tv_start_type = (TextView)view. findViewById(R.id.tv_start_type);
		tv_budget = (TextView)view. findViewById(R.id.tv_budget);
		tv_job_desc = (TextView) view.findViewById(R.id.tv_job_desc);
		tv_skills = (TextView) view.findViewById(R.id.tv_skills);
		img_user  = (ImageView)view. findViewById(R.id.img_user);
		tv_user_name = (TextView)view. findViewById(R.id.tv_user_name);
		tv_user_country = (TextView)view. findViewById(R.id.tv_user_country);
		rating_user = (RatingBar) view.findViewById(R.id.rating_user);
		btn_send_proposal= (Button) view.findViewById(R.id.btn_send_proposal);


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

						getJobDetails(jobId);
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




		btn_send_proposal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),JobProposalActivity.class);
				intent.putExtra("jobId",jobId);
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




	private void getJobDetails(String jobId)
	{

		if(utility.checkInternet())
		{

			// userGcmRegID = Shared_Preferences_Class.readString(LoginActivity.this, Shared_Preferences_Class.GCM_REG_ID, "");


			final Map<String, String> params = new HashMap<String, String>();
			params.put("jobId", jobId);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());

			serviceHandler.registerUser(params, strGetJobDetails, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status = "",str_msg;
					String   jobId, job_title,job_description,work_type, work_rate,work_rate_description,
							hours_per_week,job_budget,job_skills,start_type,schedule_date,updated_on,
							created_at,posted_userId,posted_user_firt_name,posted_user_last_name,user_country_name,user_image;
					try {
						if(str_json!=null)
						{
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");

							JSONObject jobDetailObj = new JSONObject(str_json);
							jobDetailObj = jobject.getJSONObject("jobDetail");


							if(str_status.equalsIgnoreCase("true"))
							{

								jobId = jobDetailObj.getString("jobId");
								job_title = jobDetailObj.getString("job_title");
								job_description =jobDetailObj.getString("job_description");
								work_type = jobDetailObj.getString("work_type");
								work_rate = jobDetailObj.getString("work_rate");
								work_rate_description = jobDetailObj.getString("work_rate_description");
								hours_per_week = jobDetailObj.getString("hours_per_week");
								job_budget = jobDetailObj.getString("job_budget");
								job_skills = jobDetailObj.getString("job_skills");
								start_type = jobDetailObj.getString("start_type");
								schedule_date = jobDetailObj.getString("schedule_date");
								updated_on = jobDetailObj.getString("updated_on");
								created_at = jobDetailObj.getString("created_at");
								posted_userId = jobDetailObj.getString("posted_userId");
								posted_user_firt_name = jobDetailObj.getString("posted_user_firt_name");
								posted_user_last_name = jobDetailObj.getString("posted_user_last_name");
								user_country_name = jobDetailObj.getString("user_country_name");
								user_image = jobDetailObj.getString("user_image");

								tv_job_title.setText(job_title);
								tv_job_desc.setText(job_description);
								tv_skills.setText(job_skills);
								tv_start_type.setText("Start: "+start_type);
								tv_posted_date.setText("Posted: "+schedule_date);
								tv_budget.setText("Budget: "+job_budget);
								tv_user_name.setText(posted_user_firt_name+" " + posted_user_last_name);
								tv_user_country.setText(user_country_name);

								//Download image using picasso library
								Picasso.with(getActivity()).load(user_image)
										.into(img_user);

							}
							else
							{
								Toast.makeText(getActivity(), str_msg,Toast.LENGTH_SHORT).show();


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

	}




}
