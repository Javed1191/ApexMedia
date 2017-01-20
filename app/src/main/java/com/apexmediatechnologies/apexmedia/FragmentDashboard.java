package com.apexmediatechnologies.apexmedia;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import services.Shared_Preferences_Class;
import services.Utility;


public class FragmentDashboard extends Fragment
{


	View view;
	LinearLayout lay_look_job,lay_profile,lay_my_projects,lay_payments,lay_setting,lay_faqs,lay_contact_support,lay_message;
	private Utility utility;
	private String is_tax="no",is_psychometric="no";
	private MainActivity context;

	public FragmentDashboard()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_dashboard,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		toolbar.setTitle("Dashboard");

		context = (MainActivity) getActivity();

		lay_look_job = (LinearLayout) view.findViewById(R.id.lay_look_job);
		lay_profile = (LinearLayout) view.findViewById(R.id.lay_profile);
		lay_my_projects = (LinearLayout) view.findViewById(R.id.lay_my_projects);
		lay_payments = (LinearLayout) view.findViewById(R.id.lay_payments);
		lay_setting  = (LinearLayout) view.findViewById(R.id.lay_setting);
		lay_faqs  = (LinearLayout) view.findViewById(R.id.lay_faqs);
		lay_contact_support = (LinearLayout) view.findViewById(R.id.lay_contact_support);
		lay_message = (LinearLayout) view.findViewById(R.id.lay_message);

		utility = new Utility(getActivity());

		is_tax = Shared_Preferences_Class.readString(getActivity(), Shared_Preferences_Class.IS_TAX_FORM_FILLED, "no");
		is_psychometric = Shared_Preferences_Class.readString(getActivity(), Shared_Preferences_Class.IS_PSYCHOMETRIC, "no");


		if(!is_tax.equals("")&&!is_tax.equals(null))
		{
			if(is_tax.equalsIgnoreCase("no"))
			{
				final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();

				LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View builder_view = inflater1.inflate(R.layout.activity_tax_form_dialog, null);
				builder.setView(builder_view);
				builder.setCancelable(false);

				// builder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


				TextView tv_confirm = (TextView) builder_view.findViewById(R.id.tv_confirm);


				tv_confirm.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {

							Intent intent = new Intent(getActivity(), TaxFormWebActivity.class);
							startActivity(intent);
							getActivity().finish();
							builder.cancel();

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});


				//dialog.show();
				builder.show();
			}
			else if(is_psychometric.equalsIgnoreCase("no"))
			{
				final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();

				LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View builder_view = inflater1.inflate(R.layout.activity_psychometric_dialog, null);
				builder.setView(builder_view);
				builder.setCancelable(false);

				// builder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


				TextView tv_confirm = (TextView) builder_view.findViewById(R.id.tv_confirm);
				TextView tv_cancel = (TextView) builder_view.findViewById(R.id.tv_cancel);


				tv_confirm.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {

							Intent intent = new Intent(getActivity(), PsychometricActivity.class);
							intent.putExtra("is_employer",false);
							startActivity(intent);
							getActivity().finish();
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

							builder.cancel();

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});


				//dialog.show();
				builder.show();
			}
		}

		lay_look_job.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Bundle bundle = new Bundle();
				bundle.putString("lookForJob", "lookForJob");
				FragmentJobFeeds fragment = new FragmentJobFeeds();
				fragment.setArguments(bundle);
				android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("JobFeed");
				fragmentTransaction.commit();

			}
		});

		lay_profile.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				//drawer.closeDrawer(GravityCompat.START);

				FragmentProfile fragment = new FragmentProfile();
				android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Profile");
				fragmentTransaction.commit();

			}
		});

		lay_my_projects.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				FragmentMyProjects fragment = new FragmentMyProjects();
				android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("MyProjects");
				fragmentTransaction.commit();


			}
		});
		lay_payments.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				FragmentPayments fragment = new FragmentPayments();
				android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Payments");
				fragmentTransaction.commit();


			}
		});

		lay_setting.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				FragmentSetting fragment = new FragmentSetting();
				android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Setting");
				fragmentTransaction.commit();




			}
		});
		lay_faqs.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				FragmentFaqs fragment = new FragmentFaqs();
				android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("FAQS");
				fragmentTransaction.commit();


			}
		});

		lay_contact_support.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				FragmentContactSupport fragment = new FragmentContactSupport();
				android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("ContactSupport");
				fragmentTransaction.commit();
			}
		});
		lay_message.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				FragmentMessages fragment = new FragmentMessages();
				android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Message");
				fragmentTransaction.commit();
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

}
