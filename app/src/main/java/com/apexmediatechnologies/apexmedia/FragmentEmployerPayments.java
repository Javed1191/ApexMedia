package com.apexmediatechnologies.apexmedia;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import adapter.EmployerPaymentAdapter;
import adapter.PaymentAdapter;
import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;
import views.NonScrollListView;


public class FragmentEmployerPayments extends Fragment implements DatePickerDialog.OnDateSetListener
{


	View view;
	private RecyclerView payment_recycler_view;
	private TextView tv_paments_not_found,tv_total_payment;
	private EmployerPaymentAdapter paymentAdapter;
	private Toolbar toolbar;
	private Utility utility;
	private String strViewPayments = Application_Constants.Main_URL+"keyword=view_payments_employer";

	private String strViewPaymentsFillter = Application_Constants.Main_URL+"keyword=payments_filter_employer";


	String productId="",userId="";
	List<String> statusList;
	private FloatingActionButton fab_fillter;
	private Spinner spn_staus;
	private EditText edt_from_date,edt_to_date;
	private Button btn_submit;
	private ArrayAdapter spinnerArrayAdapter;

	private Calendar cal;
	private int day;
	private int month;
	private int year;
	boolean is_from_date = false;
	private DatePickerDialog dialog;
	public FragmentEmployerPayments()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_employer_payments,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		utility = new Utility(getActivity());

		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		toolbar.setTitle("Payments");

		fab_fillter = (FloatingActionButton) view.findViewById(R.id.fab_fillter);
		payment_recycler_view = (RecyclerView)view.findViewById(R.id.payment_recycler_view);


		payment_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
		tv_paments_not_found = (TextView) view.findViewById(R.id.tv_paments_not_found);

		userId = Shared_Preferences_Class.readString(getActivity(), Shared_Preferences_Class.USER_ID, "");

		statusList = new ArrayList<>();
		statusList.add("Pending");
		statusList.add("Completed");
		statusList.add("Cancelled");

		spinnerArrayAdapter = new ArrayAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item,
				statusList);


		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		is_from_date = false;


		dialog = new DatePickerDialog(getActivity(),this, year, month, day);

/*
		spn_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				getPayments(userId,spn_status.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});*/


		fab_fillter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();

				LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View builder_view = inflater.inflate(R.layout.dialog_payment_fillter, null);
				builder.setView(builder_view);
				builder.setCancelable(false);

				// builder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

				//auto_jobs_by_cat = (AutoCompleteTextView) builder_view.findViewById(R.id.auto_jobs_by_cat);
				spn_staus = (Spinner) builder_view.findViewById(R.id.spn_staus);
				edt_from_date = (EditText) builder_view.findViewById(R.id.edt_from_date);
				edt_to_date = (EditText) builder_view.findViewById(R.id.edt_to_date);
				btn_submit = (Button) builder_view.findViewById(R.id.btn_submit);
				ImageView img_close = (ImageView) builder_view.findViewById(R.id.img_close);

				spn_staus.setAdapter(spinnerArrayAdapter);



				img_close.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						builder.dismiss();
					}
				});

				edt_from_date.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						/*cal = Calendar.getInstance();
						day = cal.get(Calendar.DAY_OF_MONTH);
						month = cal.get(Calendar.MONTH);
						year = cal.get(Calendar.YEAR);
						is_from_date = true;

						InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

						getActivity().showDialog(0);*/

						is_from_date = true;

						new DatePickerDialog(getActivity(), date, myCalendar
								.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
								myCalendar.get(Calendar.DAY_OF_MONTH)).show();

					}
				});

				edt_to_date.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{
						is_from_date = false;

						new DatePickerDialog(getActivity(), date, myCalendar
								.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
								myCalendar.get(Calendar.DAY_OF_MONTH)).show();
					}
				});


				btn_submit.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{
						builder.dismiss();

						String str_status = spn_staus.getSelectedItem().toString();

						getPaymentsFillter(userId,str_status,edt_from_date.getText().toString(),edt_to_date.getText().toString());

					}
				});

				/*auto_jobs_by_cat.setOnClickListener(new View.OnClickListener() {
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
				});*/


				builder.show();
			}
		});


		getPayments(userId);

		return view;
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


	private void getPayments(String empId)
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("empId", empId);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			final ArrayList<Payments> paymentsArrayList = new ArrayList<Payments>();

			serviceHandler.registerUser(params, strViewPayments, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status, str_msg;
					String paymentId, project, projectId="", status, paymentDate, amount, description;
					try {
						if (str_json != null) {
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if (str_status.equalsIgnoreCase("true"))
							{
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("Paymentlist");

								for (int i = 0; i < jArray.length(); i++)
								{
									JSONObject Obj = jArray.getJSONObject(i);
									// A category variables
									paymentId = Obj.getString("paymentId");
									project = Obj.getString("description");
									status = Obj.getString("status");
									paymentDate = Obj.getString("paymentDate");
									amount = Obj.getString("amount");
									description = Obj.getString("description");

									Payments payments = new Payments(paymentId,status,paymentDate,amount,description,project,projectId);
									paymentsArrayList.add(payments);

								}

								if (str_status.equals("true")) {
									tv_paments_not_found.setVisibility(View.GONE);
									payment_recycler_view.setVisibility(View.VISIBLE);
									paymentAdapter = new EmployerPaymentAdapter(getActivity(), paymentsArrayList);
									payment_recycler_view.setAdapter(paymentAdapter);
								} else {
									tv_paments_not_found.setVisibility(View.VISIBLE);
									payment_recycler_view.setVisibility(View.GONE);

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



	private void getPaymentsFillter(String empId, String status, String from_date, String to_date)
	{

		if(utility.checkInternet())
		{

			final Map<String, String> params = new HashMap<String, String>();
			params.put("empId", empId);
			params.put("status", status);
			params.put("from_date", from_date);
			params.put("to_date", to_date);

			ServiceHandler serviceHandler = new ServiceHandler(getActivity());
			final ArrayList<Payments> paymentsArrayList = new ArrayList<Payments>();

			serviceHandler.registerUser(params, strViewPaymentsFillter, new ServiceHandler.VolleyCallback() {
				@Override
				public void onSuccess(String result) {
					System.out.println("Json responce" + result);

					//Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
					String str_json = result;
					String str_status, str_msg;
					String paymentId, project, projectId="", status, paymentDate, amount, description;
					try {
						if (str_json != null) {
							JSONObject jobject = new JSONObject(str_json);
							str_status = jobject.getString("Result");
							str_msg = jobject.getString("Message");
							if (str_status.equalsIgnoreCase("true"))
							{
								JSONArray jArray = new JSONArray();
								jArray = jobject.getJSONArray("Paymentlist");

								for (int i = 0; i < jArray.length(); i++)
								{
									JSONObject Obj = jArray.getJSONObject(i);
									// A category variables
									paymentId = Obj.getString("paymentId");
									project = Obj.getString("description");
									status = Obj.getString("status");
									paymentDate = Obj.getString("paymentDate");
									amount = Obj.getString("amount");
									description = Obj.getString("description");

									Payments payments = new Payments(paymentId,status,paymentDate,amount,description,project,projectId);
									paymentsArrayList.add(payments);

								}

								if (str_status.equals("true")) {
									tv_paments_not_found.setVisibility(View.GONE);
									payment_recycler_view.setVisibility(View.VISIBLE);
									paymentAdapter = new EmployerPaymentAdapter(getActivity(), paymentsArrayList);
									payment_recycler_view.setAdapter(paymentAdapter);
								} else {
									tv_paments_not_found.setVisibility(View.VISIBLE);
									payment_recycler_view.setVisibility(View.GONE);

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


	Calendar myCalendar = Calendar.getInstance();

	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
							  int dayOfMonth) {
			// TODO Auto-generated method stub
			myCalendar.set(Calendar.YEAR, year);
			myCalendar.set(Calendar.MONTH, monthOfYear);
			myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			updateLabel();
		}

	};

	private void updateLabel() {

		//String myFormat = "MM/dd/yy"; //In which you need put here

		String myFormat = "yyyy/MM/dd";
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

		if(is_from_date)
		{
			edt_from_date.setText(sdf.format(myCalendar.getTime()));
		}
		else
		{
			edt_to_date.setText(sdf.format(myCalendar.getTime()));
		}


	}


	/*public void onClick(View v)
	{
		getActivity().showDialog(0);
	}

	@Deprecated
	protected Dialog onCreateDialog(int id)
	{
		return new DatePickerDialog(getActivity(), datePickerListener, year, month, day);
	}


	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener()
	{
		public void onDateSet(DatePicker view, int selectedYear,
							  int selectedMonth, int selectedDay)
		{

			if(is_from_date)
			{
               *//* tv_dob.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                        + selectedYear);*//*
				edt_from_date.setText(selectedYear + " - " + (selectedMonth + 1) + " - "
						+ selectedDay);
			}
			else {
				edt_to_date.setText(selectedYear + " - " + (selectedMonth + 1) + " - "
						+ selectedDay);
			}




		}
	};
*/
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		edt_to_date.setText(year+"/"+monthOfYear+"/"+dayOfMonth);
	}
}
