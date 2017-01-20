package com.apexmediatechnologies.apexmedia;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import services.Application_Constants;
import services.FetchPath;
import services.MultipartUtility;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;

import static android.app.Activity.RESULT_OK;


public class FragmentEmployerJobDetails extends Fragment
{


	View view;
	private String strGetJobDetails = Application_Constants.Main_URL+"keyword=jobDetail";
	private String strDeleteJob = Application_Constants.Main_URL+"keyword=delete_job";
	private String strUploadFile = Application_Constants.Main_URL+"keyword=upload_file_to_job";


	// private String strGetProducts ="http://192.168.1.3/hdfc_bso/android_web/action?xAction=products";
	private Utility utility;
	private String jobId="",employe_code="",product_image="",product_title="";
	private TextView tv_job_title,tv_posted_date,tv_action_title,tv_budget,tv_job_desc,tv_skills,
			tv_user_name,tv_user_country,tv_start_type,tv_job_bids;
	private ImageView img_view,img_edit,img_delete,img_upload;
	private LinearLayout lay_product_details,lay_view_pdf;
	private boolean is_file;
	private RatingBar rating_user;
	private Button btn_send_proposal;
	private int PICK_FILE_REQUEST1 = 1;
	String str_image_path;
	byte[]  byteArray;
	String encodedImage;
	File attachment1;
	boolean is_file1 = false;
	private ProgressDialog dialogTax;

	public FragmentEmployerJobDetails()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_employer_job_details,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		utility = new Utility(getActivity());

		tv_job_title = (TextView) view.findViewById(R.id.tv_job_title);
		tv_posted_date= (TextView) view.findViewById(R.id.tv_posted_date);
		tv_start_type = (TextView)view. findViewById(R.id.tv_start_type);
		tv_budget = (TextView)view. findViewById(R.id.tv_budget);
		tv_job_desc = (TextView) view.findViewById(R.id.tv_job_desc);
		tv_skills = (TextView) view.findViewById(R.id.tv_skills);
		tv_job_bids = (TextView) view.findViewById(R.id.tv_job_bids);
		img_view = (ImageView) view.findViewById(R.id.img_view);
		img_edit = (ImageView) view.findViewById(R.id.img_edit);
		img_delete = (ImageView) view.findViewById(R.id.img_delete);
		img_upload = (ImageView) view.findViewById(R.id.img_upload);


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


		img_edit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),PostJobActivity.class);
				intent.putExtra("is_update","is_update");
				intent.putExtra("jobId",jobId);

				startActivity(intent);
			}
		});
		img_delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{


				final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();

				LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View builder_view = inflater.inflate(R.layout.activity_delete_dialog, null);
				builder.setView(builder_view);
				// builder.setCancelable(false);

				// builder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


				TextView tv_confirm = (TextView) builder_view.findViewById(R.id.tv_confirm);
				TextView tv_cancel = (TextView) builder_view.findViewById(R.id.tv_cancel);


				tv_confirm.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {


							deleteJob(jobId);
							builder.cancel();

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});

				tv_cancel.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {

							//dialog.dismiss();
							builder.cancel();

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
				//dialog.show();
				builder.show();



			}
		});
		img_upload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, PICK_FILE_REQUEST1);
			}
		});



		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		//GETTING IMAGE FROM GALLERY

		if (requestCode == PICK_FILE_REQUEST1 && resultCode == RESULT_OK && data != null && data.getData() != null)
		{
			//  str_image_path = getPath(uri);

			try {

				Uri uri = data.getData();
				// str_image_path = uri.getPath();
				str_image_path = FetchPath.getPath(getActivity(), uri);



				String str_file_name = uri.getLastPathSegment();
				//tv_file1.setText(str_file_name);
				is_file1 = true;


				// code to get image from image view and convert it into file

				File filesDir = getActivity().getFilesDir();
				attachment1 = new File(str_image_path);
				long file_size =  attachment1.length();
				long file_size_kb = file_size/1024;


				new UploadFile(jobId).execute();

				System.out.print(file_size_kb);

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		/*if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)
		{
			Uri selectedImage = data.getData();
			String[] filePathColumn = {MediaStore.Images.Media.DATA};

			Cursor cursor = this.getActivity().getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();


			profImg.setImageBitmap(BitmapFactory.decodeFile(picturePath));

		}*/
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
							created_at,posted_userId,posted_user_firt_name,posted_user_last_name,user_country_name,
							user_image,total_proposals;
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
								total_proposals = jobDetailObj.getString("total_proposals");



								tv_job_title.setText(job_title);
								tv_job_desc.setText(job_description);
								tv_skills.setText(job_skills);
								tv_start_type.setText("Start: "+start_type);
								tv_posted_date.setText("Posted: "+schedule_date);
								tv_budget.setText("Budget: "+job_budget);
								tv_job_bids.setText("Proposals "+total_proposals);

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


	private void deleteJob(String jobId)
	{

		if(utility.checkInternet())
		{

			// userGcmRegID = Shared_Preferences_Class.readString(LoginActivity.this, Shared_Preferences_Class.GCM_REG_ID, "");


			final Map<String, String> params = new HashMap<String, String>();
			params.put("jobId", jobId);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());

			serviceHandler.registerUser(params, strDeleteJob, new ServiceHandler.VolleyCallback() {
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



							if(str_status.equalsIgnoreCase("true"))
							{
								Toast.makeText(getActivity(), str_msg,Toast.LENGTH_SHORT).show();

								Intent intent = new Intent(getActivity(),EmployerMainActivity.class);
								intent.putExtra("menu","jobFeed");
								startActivity(intent);
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


	class UploadFile extends AsyncTask<String, String, String>
	{
		String jobId="",jobName,jobDesc,jobCatId,jobSubCatId,jobSkills,jobWorkType,jobWorkTypeRate,jobHourlyRate,jobDuration,
				jobVisibility,jobPropStartType;
		String str_status,json_responce,str_msg="";

		public UploadFile(String jobId)
		{
			this.jobId = jobId;
		}

		@Override
		protected void onPreExecute()
		{
			// TODO Auto-generated method stub
			super.onPreExecute();

			dialogTax = new ProgressDialog(getActivity(),R.style.MyTheme);
			dialogTax.setCancelable(false);
			dialogTax.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
			dialogTax.show();

		}

		@Override
		protected String doInBackground(String... params)
		{
			// TODO Auto-generated method stub

			String charset = "UTF-8";

			// File uploadFile1 = new File(str_image_path);
			try {
				MultipartUtility multipart = new MultipartUtility(strUploadFile, charset);
				multipart.addHeaderField("User-Agent", "CodeJava");
				multipart.addHeaderField("Test-Header", "Header-Value");
				multipart.addFormField("jobId", jobId);
				if(is_file1)
				{
					multipart.addFilePart("jobAttachment", attachment1);
				}
				json_responce= multipart.finish();


			} catch (IOException ex) {
				System.err.println(ex);
			}

			try {
				// str_json = sh.makeServiceCall(registration_url, ServiceHandler.POST, list_param);
				if(json_responce!=null)
				{

					JSONObject jobject = new JSONObject(json_responce);
					//JSONObject str_status_obj =jobject.getJSONObject("STATUS");
					str_msg= jobject.getString("Message");
					str_status = jobject.getString("Result");



				}
				else
				{
					//Toast.makeText(getActivity(), "This may be server issue", Toast.LENGTH_SHORT).show();
				}

			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result)
		{
			// TODO Auto-generated method stub
			//pDialog.dismiss();
			dialogTax.dismiss();

			super.onPostExecute(result);
			try
			{
				if(str_status.equalsIgnoreCase("true"))
				{
					Toast.makeText(getActivity(), str_msg, Toast.LENGTH_SHORT).show();

				}
				else if(str_status.equalsIgnoreCase("false"))
				{

					Toast.makeText(getActivity(), str_msg, Toast.LENGTH_SHORT).show();

				}
				else
				{
					Toast.makeText(getActivity(), str_msg, Toast.LENGTH_SHORT).show();
				}


			}
			catch (Exception e)
			{
				e.printStackTrace();
			}


		}

	}



}
