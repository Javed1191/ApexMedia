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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import adapter.MessageAdapter;
import adapter.PaymentAdapter;
import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;


public class FragmentMessages extends Fragment
{


	View view;
	private RecyclerView message_recycler_view;
	private TextView tv_message_not_found;
	private MessageAdapter messageAdapter;
	private Toolbar toolbar;
	private Utility utility;
	private String strMyMessages = Application_Constants.Main_URL+"keyword=mymessagesList";
	String productId="",userId="";
	List<String> statusList;
	private Spinner spn_status;

	public FragmentMessages()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_messages,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		utility = new Utility(getActivity());

		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		toolbar.setTitle("Messages");
		message_recycler_view = (RecyclerView)view.findViewById(R.id.message_recycler_view);
		message_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
		tv_message_not_found = (TextView) view.findViewById(R.id.tv_message_not_found);
		userId = Shared_Preferences_Class.readString(getActivity(), Shared_Preferences_Class.USER_ID, "");

		getMessages(userId);

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


	private void getMessages(String userId)
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("userId", userId);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			final ArrayList<Messages> messagesArrayList = new ArrayList<Messages>();

			serviceHandler.registerUser(params, strMyMessages, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status, str_msg;
					String id, msg_from, project, message, date_time,job_id;
					try {
						if (str_json != null) {
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if (str_status.equalsIgnoreCase("true"))
							{
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("allMessages");

								for (int i = 0; i < jArray.length(); i++)
								{
									JSONObject Obj = jArray.getJSONObject(i);
									// A category variables
									id = Obj.getString("id");
									msg_from = Obj.getString("msg_from");
									project = Obj.getString("project");
									message = Obj.getString("message");
									date_time = Obj.getString("date_time");
									job_id = Obj.getString("job_id");

									Messages messages = new Messages(id,msg_from,project,message,date_time,job_id);
									messagesArrayList.add(messages);

								}

								if (str_status.equals("true")) {
									tv_message_not_found.setVisibility(View.GONE);
									message_recycler_view.setVisibility(View.VISIBLE);
									messageAdapter = new MessageAdapter(getActivity(), messagesArrayList);
									message_recycler_view.setAdapter(messageAdapter);
								} else {
									tv_message_not_found.setVisibility(View.VISIBLE);
									message_recycler_view.setVisibility(View.GONE);

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
