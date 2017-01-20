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
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.Utility;


public class FragmentEmployerAwardedJobDetails extends Fragment
{


	View view;
	private String strGetJobDetails = Application_Constants.Main_URL+"keyword=jobDetail";
	// private String strGetProducts ="http://192.168.1.3/hdfc_bso/android_web/action?xAction=products";
	private Utility utility;
	private String jobId="",employe_code="",product_image="",product_title="";
	private TextView tv_job_title,tv_posted_date,tv_action_title,tv_budget,tv_job_desc,tv_skills,
			tv_user_name,tv_user_country,tv_start_type,tv_job_bids,tv_staus,tv_posting_type,tv_progress;
	private ImageView img_view,img_payment,img_delete,img_upload,img_chat;
	private LinearLayout lay_product_details,lay_view_pdf;
	private boolean is_file;
	private RatingBar rating_user;
	private Button btn_send_proposal;
	private ProgressBar prog_job_progress;
	private String awarded_to_userId="",job_title="";

	public FragmentEmployerAwardedJobDetails()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_employer_awarded_job_details,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		utility = new Utility(getActivity());

		tv_job_title = (TextView) view.findViewById(R.id.tv_job_title);
		tv_posted_date= (TextView) view.findViewById(R.id.tv_posted_date);
		tv_start_type = (TextView)view. findViewById(R.id.tv_start_type);
		tv_budget = (TextView)view. findViewById(R.id.tv_budget);
		tv_job_desc = (TextView) view.findViewById(R.id.tv_job_desc);
		tv_skills = (TextView) view.findViewById(R.id.tv_skills);
		tv_job_bids = (TextView) view.findViewById(R.id.tv_job_bids);
		tv_staus  = (TextView) view.findViewById(R.id.tv_staus);
		tv_posting_type = (TextView) view.findViewById(R.id.tv_posting_type);
		img_view = (ImageView) view.findViewById(R.id.img_view);
		img_payment = (ImageView) view.findViewById(R.id.img_payment);
		img_chat = (ImageView) view.findViewById(R.id.img_chat);
		tv_progress = (TextView) view.findViewById(R.id.tv_progress);
		prog_job_progress = (ProgressBar) view.findViewById(R.id.prog_job_progress);


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


		img_chat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getActivity(), ChatActivity.class);
				intent.putExtra("jobId",jobId);
				intent.putExtra("fromUserId",awarded_to_userId);
				intent.putExtra("project",job_title);
				startActivity(intent);
			}
		});

		img_payment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getActivity(), EmployerMainActivity.class);
				intent.putExtra("menu","Payment");
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
					String   jobId,job_description,work_type, work_rate,work_rate_description,
							hours_per_week,job_budget,job_skills,start_type,start_type_text,schedule_date,updated_on,
							created_at,posted_userId,posted_user_firt_name,posted_user_last_name,user_country_name,
							user_image,total_proposals,jobProgress,job_status,job_posting_type_text;
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
								start_type_text = jobDetailObj.getString("start_type_text");
								schedule_date = jobDetailObj.getString("schedule_date");
								updated_on = jobDetailObj.getString("updated_on");
								created_at = jobDetailObj.getString("created_at");
								posted_userId = jobDetailObj.getString("posted_userId");
								posted_user_firt_name = jobDetailObj.getString("posted_user_firt_name");
								posted_user_last_name = jobDetailObj.getString("posted_user_last_name");
								user_country_name = jobDetailObj.getString("user_country_name");
								user_image = jobDetailObj.getString("user_image");
								total_proposals = jobDetailObj.getString("total_proposals");
								awarded_to_userId = jobDetailObj.getString("awarded_to_userId");
								jobProgress = jobDetailObj.getString("jobProgress");
								job_status = jobDetailObj.getString("job_status");
								job_posting_type_text = jobDetailObj.getString("job_posting_type_text");

								tv_job_title.setText(job_title);
								tv_job_desc.setText(job_description);
								tv_skills.setText(job_skills);
								tv_start_type.setText("Start: "+start_type_text);
								tv_posted_date.setText("Posted: "+schedule_date);
								tv_budget.setText("Budget: "+job_budget);
								tv_job_bids.setText("Proposals "+total_proposals);
								tv_progress.setText(jobProgress+"%");
								tv_staus.setText(job_status);
								tv_posting_type.setText(job_posting_type_text);

								prog_job_progress.setProgress(Integer.parseInt(jobProgress));

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
