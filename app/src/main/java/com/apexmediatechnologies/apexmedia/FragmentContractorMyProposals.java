package com.apexmediatechnologies.apexmedia;


import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.FillterSubCatAdapter;
import adapter.JobFeedsAdapter;
import adapter.MyProposalsAdapter;
import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;
import views.NonScrollListView;


public class FragmentContractorMyProposals extends Fragment
{
	View view;
	private RecyclerView feed_recycler_view;
	private TextView tv_feeds_not_found;
	private MyProposalsAdapter myProposalsAdapter;
	private Toolbar toolbar;
	private Utility utility;
	private String strMyProposals = Application_Constants.Main_URL+"keyword=my_proposals";
	private String strMyProposalsFillter = Application_Constants.Main_URL+"keyword=my_proposal_filter";
	private String strSearchJob = Application_Constants.Main_URL+"keyword=search_job";
	private String strJobFillterCat = Application_Constants.Main_URL+"keyword=job_filter_params";
	private String strJobFillterSubCat = Application_Constants.Main_URL+"keyword=job_filter_subcategories";
	private String strFilterJob = Application_Constants.Main_URL+"keyword=filter_jobs";


	String productId="",userId="";
	List<String> list_status,list_order,list_status_id;
	private FloatingActionButton fab_fillter;
	private Button btn_save,btn_cancel;
	private Spinner spn_status,spn_order,spn_jobs_posted_date;
	private ArrayList<FilterSubCatClass> filterSubCatList;
	private FillterSubCatAdapter fillterSubCatAdapter;
	private  MainActivity context;
	private static final int HIDE_THRESHOLD = 20;
	private int scrolledDistance = 0;
	private boolean controlsVisible = true;

	// filter parametrs
	private String status,order,jobSubCategory,jobBudget,jobDates;

	public FragmentContractorMyProposals()
	{
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_contractor_my_proposals,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		utility = new Utility(getActivity());
		feed_recycler_view = (RecyclerView)view.findViewById(R.id.job_feed_recycler_view);
		feed_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
		tv_feeds_not_found = (TextView) view.findViewById(R.id.tv_feeds_not_found);
		fab_fillter = (FloatingActionButton) view.findViewById(R.id.fab_fillter);

		userId = Shared_Preferences_Class.readString(getActivity(), Shared_Preferences_Class.USER_ID, "");
		context = (MainActivity) getActivity();

		final Animation zoomin = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
		final Animation zoomout = AnimationUtils.loadAnimation(context, R.anim.zoom_out);

		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		toolbar.setTitle(" My Proposals");


		getMyProposals(userId);


		fillterSubCatAdapter = new FillterSubCatAdapter(context, new OnSubFilterCheckedChangeListener()
		{
			@Override
			public void OnCartButtonClick(int position, boolean is_checked)
			{
				filterSubCatList.get(position).is_selected = is_checked;
			}
		});


		fab_fillter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();

				LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View builder_view = inflater.inflate(R.layout.dialog_proposal_fillter, null);
				builder.setView(builder_view);
				builder.setCancelable(false);

				// builder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

				//auto_jobs_by_cat = (AutoCompleteTextView) builder_view.findViewById(R.id.auto_jobs_by_cat);
				spn_status = (Spinner) builder_view.findViewById(R.id.spn_status);
				spn_order = (Spinner) builder_view.findViewById(R.id.spn_order);
				btn_save = (Button) builder_view.findViewById(R.id.btn_save);
				btn_cancel = (Button) builder_view.findViewById(R.id.btn_cancel);
				ImageView img_close = (ImageView) builder_view.findViewById(R.id.img_close);



				setFillterData();


				img_close.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						builder.dismiss();
					}
				});

				spn_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
				{
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
					{

						if(position>0)
						{

							String str_id = list_status_id.get(position);
							//Toast.makeText(getActivity(), str_id, Toast.LENGTH_SHORT).show();

							status = str_id;

						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
				spn_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
				{
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
					{

						if(position>0)
						{
							order = spn_order.getSelectedItem().toString();

						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});

				btn_save.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{
						builder.dismiss();

						getMyProposalsFillter(userId,status,order);

					}
				});

				btn_cancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						builder.dismiss();
					}
				});

				builder.show();
			}
		});

		feed_recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener()
		{

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy)
			{
				super.onScrolled(recyclerView, dx, dy);
				/*if (dy > 0)
				{
					// Scrolling up
					fab_fillter.setVisibility(View.INVISIBLE);
					fab_fillter.startAnimation(zoomout);
				}
				else
				{
					// Scrolling down
					fab_fillter.setVisibility(View.VISIBLE);
					fab_fillter.startAnimation(zoomin);

				}*/

				if (scrolledDistance > HIDE_THRESHOLD && controlsVisible)
				{
					fab_fillter.setVisibility(View.INVISIBLE);
					fab_fillter.startAnimation(zoomout);



					/*FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) fab_fillter.getLayoutParams();
					int fabBottomMargin = lp.bottomMargin;
					fab_fillter.animate().translationY(fab_fillter.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();*/


					controlsVisible = false;
					scrolledDistance = 0;
				}
				else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible)
				{
					fab_fillter.setVisibility(View.VISIBLE);
					fab_fillter.startAnimation(zoomin);
					controlsVisible = true;
					scrolledDistance = 0;
				}

				if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
					scrolledDistance += dy;
				}
			}

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);



				if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING)
				{
					// Do something
				}
				else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
				{
					// Do something
				}
				else
				{
					// Do something
				}
			}
		});



		return view;
	}


	public void setFillterData()
	{
		list_status = new ArrayList<String>();
		list_order = new ArrayList<String>();
		list_status_id = new ArrayList<String>();

		list_status.add("Status");
		list_status.add("Close");
		list_status.add("Open");

		list_status_id.add("2");
		list_status_id.add("0");
		list_status_id.add("1");

		list_order.add("Order");
		list_order.add("Latest");
		list_order.add("Oldest");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,list_status);
		spn_status.setAdapter(adapter);

		ArrayAdapter<String> adapterOrder = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,list_order);
		spn_order.setAdapter(adapterOrder);

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


	private void getMyProposals(String userId)
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("contractorId", userId);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			final ArrayList<MyProposals> feedsArrayList = new ArrayList<MyProposals>();

			serviceHandler.registerUser(params, strMyProposals, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status,str_msg;
					 String jobId, status,job_progress_status, total_proposals,job_title="", created_at,employer_id="",porposal_id="";
					try {
						if(str_json!=null)
						{
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if(str_status.equalsIgnoreCase("true"))
							{
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("Propojals");

								for (int i = 0; i < jArray.length(); i++)
								{
									JSONObject Obj = jArray.getJSONObject(i);
									// A category variables
									jobId = Obj.getString("jobId");
									job_title = Obj.getString("job_name");
									status = Obj.getString("status");
									job_progress_status = Obj.getString("job_progress_status");
									created_at = Obj.getString("created_at");
									total_proposals = Obj.getString("total_proposals");
									employer_id = Obj.getString("employer_id");
									porposal_id = Obj.getString("porposal_id");



									MyProposals feeds = new MyProposals(jobId,job_title,created_at,status,job_progress_status,total_proposals,employer_id,porposal_id);
									feedsArrayList.add(feeds);

								}

								if(str_status.equals("true"))
								{
									tv_feeds_not_found.setVisibility(View.GONE);
									feed_recycler_view.setVisibility(View.VISIBLE);
									myProposalsAdapter = new MyProposalsAdapter(getActivity(), feedsArrayList);
									feed_recycler_view.setAdapter(myProposalsAdapter);
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


	private void getMyProposalsFillter(String userId, String status, String order)
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("contractorId", userId);
			params.put("status", status);
			params.put("order", order);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			final ArrayList<MyProposals> feedsArrayList = new ArrayList<MyProposals>();

			serviceHandler.registerUser(params, strMyProposalsFillter, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status,str_msg;
					String jobId, status,job_progress_status, total_proposals,job_title="", created_at,employer_id="",porposal_id="";
					try {
						if(str_json!=null)
						{
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if(str_status.equalsIgnoreCase("true"))
							{
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("Propojals");

								for (int i = 0; i < jArray.length(); i++)
								{
									JSONObject Obj = jArray.getJSONObject(i);
									// A category variables
									jobId = Obj.getString("jobId");
									job_title = Obj.getString("job_name");
									status = Obj.getString("status");
									job_progress_status = Obj.getString("job_progress_status");
									created_at = Obj.getString("created_at");
									total_proposals = Obj.getString("total_proposals");
									employer_id = Obj.getString("employer_id");
									porposal_id = Obj.getString("porposal_id");



									MyProposals feeds = new MyProposals(jobId,job_title,created_at,status,job_progress_status,total_proposals,employer_id,porposal_id);
									feedsArrayList.add(feeds);

								}

								if(str_status.equals("true"))
								{
									tv_feeds_not_found.setVisibility(View.GONE);
									feed_recycler_view.setVisibility(View.VISIBLE);
									myProposalsAdapter = new MyProposalsAdapter(getActivity(), feedsArrayList);
									feed_recycler_view.setAdapter(myProposalsAdapter);
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

}
