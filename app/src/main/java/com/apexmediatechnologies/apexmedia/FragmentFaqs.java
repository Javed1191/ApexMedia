package com.apexmediatechnologies.apexmedia;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.ExpandableListAdapter;
import adapter.JobFeedsAdapter;
import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;


public class FragmentFaqs extends Fragment
{


	View view;
	private Toolbar toolbar;
	private Utility utility;
	private String strGetFAQ = Application_Constants.Main_URL+"keyword=faq";
	String productId="",userId="";
	int is_email_alert=0;
	private ExpandableListView lvExp;
	ExpandableListAdapter listAdapter;
	List<String> listDataHeader;
	List<String> list_is_header_click;
	HashMap<String, List<String>> listDataChild;

	public FragmentFaqs()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_faqs,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		utility = new Utility(getActivity());

		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		toolbar.setTitle("FAQs");


		lvExp = (ExpandableListView) view.findViewById(R.id.lvExp);
		lvExp.setGroupIndicator(null);

		userId = Shared_Preferences_Class.readString(getActivity(), Shared_Preferences_Class.USER_ID, "");

		// preparing list data
		//prepareListData();

	/*	listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

		// setting list adapter
		lvExp.setAdapter(listAdapter);*/

		getFAQs();


		lvExp.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener()
		{
			@Override
			public void onGroupCollapse(int groupPosition)
			{
				listAdapter.setGroupIndicator(groupPosition,false);
				//Toast.makeText(getActivity(), "Group Collapse", Toast.LENGTH_SHORT).show();

			}
		});
		lvExp.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener()
		{
			@Override
			public void onGroupExpand(int groupPosition)
			{
				//Toast.makeText(getActivity(), "Group Expand ", Toast.LENGTH_SHORT).show();
				listAdapter.setGroupIndicator(groupPosition,true);
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

	/*
     * Preparing the list data
     */
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("Top 250");
		listDataHeader.add("Now Showing");
		listDataHeader.add("Coming Soon..");

		// Adding child data
		List<String> top250 = new ArrayList<String>();
		top250.add("The Shawshank Redemption");
		top250.add("The Godfather");
		top250.add("The Godfather: Part II");
		top250.add("Pulp Fiction");
		top250.add("The Good, the Bad and the Ugly");
		top250.add("The Dark Knight");
		top250.add("12 Angry Men");

		List<String> nowShowing = new ArrayList<String>();
		nowShowing.add("The Conjuring");
		nowShowing.add("Despicable Me 2");
		nowShowing.add("Turbo");
		nowShowing.add("Grown Ups 2");
		nowShowing.add("Red 2");
		nowShowing.add("The Wolverine");

		List<String> comingSoon = new ArrayList<String>();
		comingSoon.add("2 Guns");
		comingSoon.add("The Smurfs 2");
		comingSoon.add("The Spectacular Now");
		comingSoon.add("The Canyons");
		comingSoon.add("Europa Report");

		listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
		listDataChild.put(listDataHeader.get(1), nowShowing);
		listDataChild.put(listDataHeader.get(2), comingSoon);
	}


	private void getFAQs()
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("userId", userId);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			final ArrayList<JobFeeds> feedsArrayList = new ArrayList<JobFeeds>();

			serviceHandler.registerUser(params, strGetFAQ, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status,str_msg;
					String faqId, title,description;
					listDataHeader = new ArrayList<String>();
					listDataChild = new HashMap<String, List<String>>();
					list_is_header_click = new ArrayList<String>();
					try {
						if(str_json!=null)
						{
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if(str_status.equalsIgnoreCase("true"))
							{
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("List");

								for (int i = 0; i < jArray.length(); i++)
								{
									JSONObject Obj = jArray.getJSONObject(i);
									// A category variables
									faqId = Obj.getString("faqId");
									title = Obj.getString("title");
									description = Obj.getString("description");

									List<String> list_desc = new ArrayList<String>();
									list_desc.add(description);

									listDataHeader.add(title);
									listDataChild.put(listDataHeader.get(i), list_desc);
									list_is_header_click.add("no");
								}

								if(str_status.equals("true"))
								{
									listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild,list_is_header_click);

									// setting list adapter
									lvExp.setAdapter(listAdapter);
								}
								else
								{

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
