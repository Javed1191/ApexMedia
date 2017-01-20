package com.apexmediatechnologies.apexmedia;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import services.Shared_Preferences_Class;
import services.Utility;


public class FragmentProfile extends Fragment
{


	View view;
	private String strGetJobDetails = Application_Constants.Main_URL+"keyword=jobDetail";
	// private String strGetProducts ="http://192.168.1.3/hdfc_bso/android_web/action?xAction=products";
	private Utility utility;
	private String jobId="",employe_code="",product_image="",product_title="";
	private TextView tv_name,tv_country,tv_first_name,tv_last_name,tv_phone,tv_email,
			tv_address,tv_city,tv_state,tv_country_bottom;
	private ImageView img_user_image;
	private boolean is_file;

	String  UserEmail, UserFirstName,UserLastName,phone,UserProfileImage,address,state,city,country;

	public FragmentProfile()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_profile,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		toolbar.setTitle("Profile");

		utility = new Utility(getActivity());

		tv_name = (TextView) view.findViewById(R.id.tv_name);
		tv_country= (TextView) view.findViewById(R.id.tv_country);
		tv_first_name = (TextView)view. findViewById(R.id.tv_first_name);
		tv_last_name = (TextView)view. findViewById(R.id.tv_last_name);
		tv_phone = (TextView) view.findViewById(R.id.tv_phone);
		tv_email = (TextView) view.findViewById(R.id.tv_email);
		img_user_image  = (ImageView)view. findViewById(R.id.img_user_image);
		tv_address = (TextView)view. findViewById(R.id.tv_address);
		tv_city = (TextView)view. findViewById(R.id.tv_city);
		tv_state = (TextView) view.findViewById(R.id.tv_state);
		tv_country_bottom= (TextView) view.findViewById(R.id.tv_country_bottom);


		try
		{
			UserFirstName = Shared_Preferences_Class.readString(getActivity(),Shared_Preferences_Class.USER_FIRST_NAME,"");
			UserLastName = Shared_Preferences_Class.readString(getActivity(),Shared_Preferences_Class.USER_LAST_NAME,"");
			UserEmail = Shared_Preferences_Class.readString(getActivity(),Shared_Preferences_Class.USER_EMAIL,"");
			phone = Shared_Preferences_Class.readString(getActivity(),Shared_Preferences_Class.USER_PHONE,"");
			UserProfileImage = Shared_Preferences_Class.readString(getActivity(),Shared_Preferences_Class.USER_IMAGE,"");
			address = Shared_Preferences_Class.readString(getActivity(),Shared_Preferences_Class.USER_ADDRESS,"");
			state = Shared_Preferences_Class.readString(getActivity(),Shared_Preferences_Class.USER_STATE,"");
			city = Shared_Preferences_Class.readString(getActivity(),Shared_Preferences_Class.USER_CITY,"");
			country = Shared_Preferences_Class.readString(getActivity(),Shared_Preferences_Class.USER_COUNTRY,"");

			tv_name.setText(UserFirstName + " "+ UserLastName);
			tv_country.setText(address);

			tv_first_name.setText(UserFirstName);
			tv_last_name.setText(UserLastName);
			tv_email.setText(UserEmail);
			tv_phone.setText(phone);
			tv_address.setText(address);
			tv_state.setText(state);
			tv_city.setText(city);
			tv_country_bottom.setText(country);

			if(!UserProfileImage.equals(""))
			{
				//Download image using picasso library
				Picasso.with(getActivity()).load(UserProfileImage)
						.into(img_user_image);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO Add your menu entries here
		super.onCreateOptionsMenu(menu, inflater);

		MenuItem register = menu.findItem(R.id.action_edit);
		register.setVisible(true);
		//getActivity().getMenuInflater().inflate(R.menu.main, menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.


		return super.onOptionsItemSelected(item);
	}

}
