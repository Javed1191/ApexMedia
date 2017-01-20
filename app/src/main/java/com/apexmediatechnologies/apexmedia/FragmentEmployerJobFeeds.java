package com.apexmediatechnologies.apexmedia;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.FillterSubCatAdapter;
import adapter.JobFeedsAdapter;
import adapter.JobFeedsEmployerAdapter;
import services.Application_Constants;
import services.FetchPath;
import services.MultipartUtility;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;
import views.NonScrollListView;

import static android.R.id.list;
import static android.app.Activity.RESULT_OK;


public class FragmentEmployerJobFeeds extends Fragment
{
	View view;
	private RecyclerView feed_recycler_view;
	private TextView tv_feeds_not_found;
	private JobFeedsEmployerAdapter feedsAdapter;
	private Toolbar toolbar;
	private Utility utility;
	private String strGetfeeds = Application_Constants.Main_URL+"keyword=employer_job_list";
	private String strGetKeywords = Application_Constants.Main_URL+"keyword=job_keywords";
	private String strSearchJob = Application_Constants.Main_URL+"keyword=search_job";
	private String strJobFillterCat = Application_Constants.Main_URL+"keyword=job_filter_params";
	private String strJobFillterSubCat = Application_Constants.Main_URL+"keyword=job_filter_subcategories";
	private String strFilterJob = Application_Constants.Main_URL+"keyword=filter_jobs";
	private String strUploadFile = Application_Constants.Main_URL+"keyword=upload_file_to_job";
	private String strEmployerJobSearch = Application_Constants.Main_URL+"keyword=employer_job_search";




	String productId="",userId="";
	private AutoCompleteTextView auto_seach;
	List<String> searchKeyList,fillterCatList,fillterCatIdList,fillterBudgetList,
			fillterBudgetIdList,fillterDatesList,fillterDatesIdList;
	private ImageView img_search;
	private Button btn_submit;
	private Spinner spn_jobs_by_cat,spn_jobs_budget,spn_jobs_posted_date;
	private NonScrollListView listSubcat;
	private ArrayList<FilterSubCatClass> filterSubCatList;
	private FillterSubCatAdapter fillterSubCatAdapter;
	private  EmployerMainActivity context;
	private static final int HIDE_THRESHOLD = 20;
	private int scrolledDistance = 0;
	private boolean controlsVisible = true;
	private FloatingActionButton fab_fillter;

	// filter parametrs
	private String skill,jobCategory,jobSubCategory,jobBudget,jobDates;

	private int PICK_FILE_REQUEST1 = 1;
	String str_image_path;
	byte[]  byteArray;
	String encodedImage;
	File attachment1;
	boolean is_file1 = false;
	private ProgressDialog dialogTax;
	private ArrayList<JobFeedsEmployer> feedsArrayList;
	private  String jobId="";
	private int pos= -1;
	public FragmentEmployerJobFeeds()
	{
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_employer_job_feeds,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		toolbar.setTitle("My Jobs");
		utility = new Utility(getActivity());
		feed_recycler_view = (RecyclerView)view.findViewById(R.id.job_feed_recycler_view);
		feed_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
		tv_feeds_not_found = (TextView) view.findViewById(R.id.tv_feeds_not_found);
		auto_seach = (AutoCompleteTextView) view.findViewById(R.id.auto_seach);
		img_search = (ImageView) view.findViewById(R.id.img_search);
		fab_fillter = (FloatingActionButton) view.findViewById(R.id.fab_fillter);


		userId = Shared_Preferences_Class.readString(getActivity(), Shared_Preferences_Class.USER_ID, "");
		context = (EmployerMainActivity) getActivity();

		final Animation zoomin = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
		final Animation zoomout = AnimationUtils.loadAnimation(context, R.anim.zoom_out);

		getJobFeeds(userId);



		/*try
		{
			Bundle arguments = getArguments();

			if(arguments != null && arguments.containsKey("jobFeed"))
			{
				if(!getArguments().getString("jobFeed").equals("")&&!getArguments().getString("jobFeed").equals(null))
				{
					Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
					toolbar.setTitle("Job Feeds");



					//addData();
					getJobFeeds(userId);
				}
			}
			else
			{
				Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
				toolbar.setTitle("Look ForJob");

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}*/




		// job search keys
		//getSearchKey();



		/*fab_fillter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();

				LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View builder_view = inflater.inflate(R.layout.dialog_fillter, null);
				builder.setView(builder_view);
				builder.setCancelable(false);

				// builder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

				//auto_jobs_by_cat = (AutoCompleteTextView) builder_view.findViewById(R.id.auto_jobs_by_cat);
				spn_jobs_by_cat = (Spinner) builder_view.findViewById(R.id.spn_jobs_by_cat);
				spn_jobs_budget = (Spinner) builder_view.findViewById(R.id.spn_jobs_budget);
				spn_jobs_posted_date = (Spinner) builder_view.findViewById(R.id.spn_jobs_posted_date);
				btn_submit = (Button) builder_view.findViewById(R.id.btn_submit);
				listSubcat = (NonScrollListView) builder_view.findViewById(R.id.listSubcat);
				ImageView img_close = (ImageView) builder_view.findViewById(R.id.img_close);

				getFillterCat();


				img_close.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						builder.dismiss();
					}
				});

				spn_jobs_by_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
				{
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
					{

						if(position>0)
						{
							listSubcat.setVisibility(View.VISIBLE);

							String str_id = fillterCatIdList.get(position);
							//Toast.makeText(getActivity(), str_id, Toast.LENGTH_SHORT).show();
							getFillterSubCat(str_id);

							jobCategory = str_id;

						}
						else
						{
							listSubcat.setVisibility(View.GONE);
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
				spn_jobs_budget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
				{
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
					{

						if(position>0)
						{
							String str_id = fillterBudgetIdList.get(position);
							jobBudget = str_id;

						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
				spn_jobs_posted_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
					{
						if(position>0) {
							String str_id = fillterDatesIdList.get(position);
							jobDates = str_id;
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});


				btn_submit.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{
						builder.dismiss();

						String str_skill = auto_seach.getText().toString();

						List<String> list_sub_cat = new ArrayList<String>();

						for (int i = 0; i < filterSubCatList.size(); i++) {
							if (filterSubCatList.get(i).is_selected) {
								list_sub_cat.add(filterSubCatList.get(i).subCatId);
							}

						}
						String sub_cat = android.text.TextUtils.join(",", list_sub_cat);
						//Toast.makeText(getActivity(), sub_cat, Toast.LENGTH_SHORT).show();

						getJobFilter(userId,str_skill,jobCategory,sub_cat,jobBudget,jobDates);

					}
				});


				*//*auto_jobs_by_cat.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						auto_jobs_by_cat.showDropDown();
					}
				});
				auto_jobs_by_cat.setOnItemClickListener(new AdapterView.OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id)
					{
						String str_id = fillterCatIdList.get(position);
						Toast.makeText(getActivity(), str_id, Toast.LENGTH_SHORT).show();
					}
				});*//*


				builder.show();
			}
		});*/


		auto_seach.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				auto_seach.showDropDown();
			}
		});

		auto_seach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
				String str_skill = auto_seach.getText().toString();
				searchJobFeeds(userId, str_skill);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		img_search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
				String str_skill = auto_seach.getText().toString();
				if (!str_skill.equals("") && !str_skill.equals(null)) {
					searchJobFeeds(userId, str_skill);
				} else {
					Toast.makeText(getActivity(), "Select skills to search", Toast.LENGTH_SHORT).show();
				}


			}
		});

		auto_seach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				/*InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
				String str_skill = auto_seach.getText().toString();
				getJobSeach(userId,str_skill);*/
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



				JobFeedsEmployer jobFeedsEmployer = feedsArrayList.get(pos);
				new UploadFile(jobFeedsEmployer.getJobId()).execute();

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


	private void getJobFeeds(String userId)
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("empId", userId);
			params.put("filterType", "allJobs");

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			feedsArrayList = new ArrayList<JobFeedsEmployer>();

			serviceHandler.registerUser(params, strGetfeeds, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status,str_msg;
					 String jobId, empId,jobName, job_skills, jobDesc, postDate,proposals,jobProgress,awarded_to_userId,jobSkills,
							 visibility,Posting_type,awarded,awardedProposalId;
					try {
						if(str_json!=null)
						{
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if(str_status.equalsIgnoreCase("true"))
							{
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("allJobs");

								for (int i = 0; i < jArray.length(); i++)
								{
									JSONObject Obj = jArray.getJSONObject(i);
									// A category variables
									jobId = Obj.getString("jobId");
									empId = Obj.getString("empId");
									jobName = Obj.getString("jobName");
									jobDesc = Obj.getString("jobDesc");
									postDate = Obj.getString("postDate");
									proposals  = Obj.getString("proposals");

									jobSkills = Obj.getString("jobSkills");
									visibility = Obj.getString("visibility");
									Posting_type = Obj.getString("Posting_type");
									awarded = Obj.getString("awarded");
									awardedProposalId = Obj.getString("awardedProposalId");
									jobProgress = Obj.getString("jobProgress");
									awarded_to_userId = Obj.getString("awarded_to_userId");


									JobFeedsEmployer feeds = new JobFeedsEmployer(jobId,empId,jobName,jobDesc,postDate,proposals,awardedProposalId,jobProgress,jobSkills,visibility,Posting_type,awarded);
									feedsArrayList.add(feeds);

								}

								if(str_status.equals("true"))
								{
									tv_feeds_not_found.setVisibility(View.GONE);
									feed_recycler_view.setVisibility(View.VISIBLE);
									feedsAdapter = new JobFeedsEmployerAdapter(getActivity(), feedsArrayList, new OnUploadClickListener() {
										@Override
										public void onUploadClick(int position, boolean is_upload)
										{

											pos = position;

											Intent i = new Intent(
													Intent.ACTION_PICK,
													android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

											startActivityForResult(i, PICK_FILE_REQUEST1);

										}
									});
									feed_recycler_view.setAdapter(feedsAdapter);


								}
								else
								{
									tv_feeds_not_found.setVisibility(View.VISIBLE);
									feed_recycler_view.setVisibility(View.GONE);

								}


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


	private void searchJobFeeds(String userId, String search_keyword)
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("empId", userId);
			params.put("search_keyword", search_keyword);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			feedsArrayList = new ArrayList<JobFeedsEmployer>();

			serviceHandler.registerUser(params, strEmployerJobSearch, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status,str_msg;
					String jobId, empId,jobName, job_skills, jobDesc, postDate,proposals,jobProgress,awarded_to_userId,jobSkills,
							visibility,Posting_type,awarded,awardedProposalId;
					try {
						if(str_json!=null)
						{
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if(str_status.equalsIgnoreCase("true"))
							{
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("allJobs");

								for (int i = 0; i < jArray.length(); i++)
								{
									JSONObject Obj = jArray.getJSONObject(i);
									// A category variables
									jobId = Obj.getString("jobId");
									empId = Obj.getString("empId");
									jobName = Obj.getString("jobName");
									jobDesc = Obj.getString("jobDesc");
									postDate = Obj.getString("postDate");
									proposals  = Obj.getString("proposals");

									jobSkills = Obj.getString("jobSkills");
									visibility = Obj.getString("visibility");
									Posting_type = Obj.getString("Posting_type");
									awarded = Obj.getString("awarded");
									awardedProposalId = Obj.getString("awardedProposalId");
									jobProgress = Obj.getString("jobProgress");
									awarded_to_userId = Obj.getString("awarded_to_userId");


									JobFeedsEmployer feeds = new JobFeedsEmployer(jobId,empId,jobName,jobDesc,postDate,proposals,awardedProposalId,jobProgress,jobSkills,visibility,Posting_type,awarded);
									feedsArrayList.add(feeds);

								}

								if(str_status.equals("true"))
								{
									tv_feeds_not_found.setVisibility(View.GONE);
									feed_recycler_view.setVisibility(View.VISIBLE);
									feedsAdapter = new JobFeedsEmployerAdapter(getActivity(), feedsArrayList, new OnUploadClickListener() {
										@Override
										public void onUploadClick(int position, boolean is_upload)
										{

											pos = position;

											Intent i = new Intent(
													Intent.ACTION_PICK,
													android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

											startActivityForResult(i, PICK_FILE_REQUEST1);

										}
									});
									feed_recycler_view.setAdapter(feedsAdapter);


								}
								else
								{
									tv_feeds_not_found.setVisibility(View.VISIBLE);
									feed_recycler_view.setVisibility(View.GONE);

								}


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


/*	private void getJobSeach(String userId, String skill)
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("userId", userId);
			params.put("skill", skill);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			final ArrayList<JobFeeds> feedsArrayList = new ArrayList<JobFeeds>();

			serviceHandler.registerUser(params, strSearchJob, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status,str_msg;
					String jobId, job_budget,work_rate_description, job_skills, work_type, posted_userId, posted_user_firt_name,
							work_rate, start_type, user_country_name, hours_per_week, job_title, created_at, updated_on, job_description,
							posted_user_last_name, user_image, schedule_date;
					try {
						if(str_json!=null)
						{
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if(str_status.equalsIgnoreCase("true"))
							{
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("joblist");

								for (int i = 0; i < jArray.length(); i++)
								{
									JSONObject Obj = jArray.getJSONObject(i);
									// A category variables
									jobId = Obj.getString("jobId");
									job_title = Obj.getString("job_title");
									job_description = Obj.getString("job_description");
									work_type = Obj.getString("work_type");
									work_rate = Obj.getString("work_rate");
									work_rate_description = Obj.getString("work_rate_description");
									hours_per_week = Obj.getString("hours_per_week");
									job_budget = Obj.getString("job_budget");
									job_skills= Obj.getString("job_skills");
									start_type= Obj.getString("start_type");
									schedule_date= Obj.getString("schedule_date");
									updated_on= Obj.getString("updated_on");
									created_at= Obj.getString("created_at");
									posted_userId= Obj.getString("posted_userId");
									posted_user_firt_name= Obj.getString("posted_user_firt_name");
									posted_user_last_name= Obj.getString("posted_user_last_name");
									user_country_name= Obj.getString("user_country_name");
									user_image= Obj.getString("user_image");


									JobFeeds feeds = new JobFeeds("", jobId,  job_budget,  work_rate_description,  job_skills,  work_type,
											posted_userId,  posted_user_firt_name,  work_rate,  start_type,  user_country_name,
											hours_per_week,  job_title,  created_at,  updated_on,  job_description,  posted_user_last_name,
											user_image,  schedule_date,"");
									feedsArrayList.add(feeds);

								}

								if(str_status.equals("true"))
								{
									tv_feeds_not_found.setVisibility(View.GONE);
									feed_recycler_view.setVisibility(View.VISIBLE);
									feedsAdapter = new JobFeedsAdapter(getActivity(), feedsArrayList);
									feed_recycler_view.setAdapter(feedsAdapter);
								}
								else
								{
									tv_feeds_not_found.setVisibility(View.VISIBLE);
									feed_recycler_view.setVisibility(View.GONE);

								}


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

	}*/
/*
	private void getSearchKey()
	{
		searchKeyList = new ArrayList<>();

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();


			ServiceHandler serviceHandler = new ServiceHandler(getActivity());

			serviceHandler.registerUser(params, strGetKeywords, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status,str_msg;
					String   search_key;
					try {
						if(str_json!=null)
						{
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if(str_status.equalsIgnoreCase("true"))
							{
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("keywords");

						JSONObject jsonObject = new JSONObject();
						jsonObject = jobject.getJSONObject("VENUEDATA");


								for (int i = 0; i < jArray.length(); i++)
								{
									//JSONObject Obj = jArray.getJSONObject(i);

									search_key = jArray.getString(i);

									searchKeyList.add(search_key);

								}


								ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,searchKeyList);
								auto_seach.setAdapter(adapter);

								auto_seach.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);

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

		else
		{
			Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_SHORT).show();
		}

	}*/


	private void getFillterCat()
	{
		fillterCatList = new ArrayList<>();
		fillterCatIdList = new ArrayList<>();
		fillterBudgetList = new ArrayList<>();
		fillterBudgetIdList= new ArrayList<>();
		fillterDatesList= new ArrayList<>();
		fillterDatesIdList= new ArrayList<>();

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());

			serviceHandler.registerUser(params, strJobFillterCat, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status,str_msg;
					try {
						if(str_json!=null)
						{
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if(str_status.equalsIgnoreCase("true"))
							{
								JSONArray jArrayCat = new JSONArray();
								JSONArray jArrayBudget = new JSONArray();
								JSONArray jArrayDate = new JSONArray();
								jArrayCat = jobject.getJSONArray("filter_categories");
								jArrayBudget = jobject.getJSONArray("filter_budgets");
								jArrayDate = jobject.getJSONArray("filter_dates");

						JSONObject jsonObject = new JSONObject();
						jsonObject = jobject.getJSONObject("VENUEDATA");

								fillterCatList.add("Select Category");
								fillterCatIdList.add("");

								for (int i = 0; i < jArrayCat.length(); i++)
								{
									JSONObject Obj = jArrayCat.getJSONObject(i);

									String category_name = Obj.getString("category_name");
									String id = Obj.getString("id");
									fillterCatList.add(category_name);
									fillterCatIdList.add(id);

								}
								fillterBudgetList.add("Select Budget");
								fillterBudgetIdList.add("");
								for (int i = 0; i < jArrayBudget.length(); i++)
								{
									JSONObject Obj = jArrayBudget.getJSONObject(i);

									String budget = Obj.getString("budget");
									String id = Obj.getString("id");
									fillterBudgetList.add(budget);
									fillterBudgetIdList.add(id);

								}
								fillterDatesList.add("Select Posted Date");
								fillterDatesIdList.add("");
								for (int i = 0; i < jArrayDate.length(); i++)
								{
									JSONObject Obj = jArrayDate.getJSONObject(i);

									String dateType = Obj.getString("dateType");
									String id = Obj.getString("id");
									fillterDatesList.add(dateType);
									fillterDatesIdList.add(id);

								}


								ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,fillterCatList);
								spn_jobs_by_cat.setAdapter(adapter);

								ArrayAdapter<String> adapterBudget = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,fillterBudgetList);
								spn_jobs_budget.setAdapter(adapterBudget);

								ArrayAdapter<String> adapterDate = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,fillterDatesList);
								spn_jobs_posted_date.setAdapter(adapterDate);
								//auto_jobs_by_cat.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);

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

		else
		{
			Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_SHORT).show();
		}

	}


	private void getFillterSubCat( String categoryId)
	{
		filterSubCatList = new ArrayList<>();

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("categoryId", categoryId);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());

			serviceHandler.registerUser(params, strJobFillterSubCat, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status,str_msg;
					try {
						if(str_json!=null)
						{
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if(str_status.equalsIgnoreCase("true"))
							{
								JSONArray jArrayCat = new JSONArray();
								jArrayCat = jobject.getJSONArray("filter_subcategories");
								FilterSubCatClass filterSubCatClass;
								for (int i = 0; i < jArrayCat.length(); i++)
								{
									filterSubCatClass = new FilterSubCatClass();
									JSONObject Obj = jArrayCat.getJSONObject(i);

									filterSubCatClass.subCatId = Obj.getString("subCatId");
									filterSubCatClass.subCategory_name = Obj.getString("subCategory_name");
									filterSubCatClass.is_selected = false;

									filterSubCatList.add(filterSubCatClass);

								}


								fillterSubCatAdapter.setList(filterSubCatList);
								listSubcat.setAdapter(fillterSubCatAdapter);
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

		else
		{
			Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_SHORT).show();
		}

	}

/*
	private void getJobFilter(String userId, String skill, String jobCategory, String jobSubCategory,String jobBudget, String jobDates)
	{

		if(utility.checkInternet())
		{
			final Map<String, String> params = new HashMap<String, String>();
			params.put("userId", userId);
			params.put("skill", skill);
			params.put("jobCategory", jobCategory);
			params.put("jobSubCategory", jobSubCategory);
			params.put("jobBudget", jobBudget);
			params.put("jobDates", jobDates);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			final ArrayList<JobFeeds> feedsArrayList = new ArrayList<JobFeeds>();

			serviceHandler.registerUser(params, strFilterJob, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status, str_msg;
					String jobId, job_budget, work_rate_description, job_skills, work_type, posted_userId, posted_user_firt_name,
							work_rate, start_type, user_country_name, hours_per_week, job_title, created_at, updated_on, job_description,
							posted_user_last_name, user_image, schedule_date;
					try {
						if (str_json != null) {
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if (str_status.equalsIgnoreCase("true"))
							{
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("joblist");

								for (int i = 0; i < jArray.length(); i++) {
									JSONObject Obj = jArray.getJSONObject(i);
									// A category variables
									jobId = Obj.getString("jobId");
									job_title = Obj.getString("job_title");
									job_description = Obj.getString("job_description");
									work_type = Obj.getString("work_type");
									work_rate = Obj.getString("work_rate");
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


									JobFeeds feeds = new JobFeeds("", jobId, job_budget, work_rate_description, job_skills, work_type,
											posted_userId, posted_user_firt_name, work_rate, start_type, user_country_name,
											hours_per_week, job_title, created_at, updated_on, job_description, posted_user_last_name,
											user_image, schedule_date,"");
									feedsArrayList.add(feeds);

								}

								if (str_status.equals("true"))
								{
									tv_feeds_not_found.setVisibility(View.GONE);
									feed_recycler_view.setVisibility(View.VISIBLE);
									feedsAdapter = new JobFeedsAdapter(getActivity(), feedsArrayList);
									feed_recycler_view.setAdapter(feedsAdapter);
								}
								else
								{
									tv_feeds_not_found.setVisibility(View.VISIBLE);
									feed_recycler_view.setVisibility(View.GONE);

								}


							}
							else
							{
								tv_feeds_not_found.setVisibility(View.VISIBLE);
								feed_recycler_view.setVisibility(View.GONE);

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

	}*/



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
