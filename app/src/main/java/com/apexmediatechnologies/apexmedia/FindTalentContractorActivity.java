package com.apexmediatechnologies.apexmedia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

import adapter.FindTalentContractorAdapter;
import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;

public class FindTalentContractorActivity extends AppCompatActivity
{

    private String strFindTalent = Application_Constants.Main_URL+"keyword=find_talent";
    private String strGetSkills = Application_Constants.Main_URL+"keyword=getSkills";
    private String strGetHourlyRate = Application_Constants.Main_URL+"keyword=getHourlyRate";
    private String strGetCountries = Application_Constants.Main_URL+"keyword=countries";

   // private String strGetProducts ="http://192.168.1.3/hdfc_bso/android_web/action?xAction=products";
    private Utility utility;
    private TextView tv_action_title;
    private  TextView tv_contractor_not_found;

    // parametres
    String userId,submit_amt_later="0",jobId;
    private RecyclerView contractor_recycler_view;
    private FindTalentContractorAdapter findTalentContractorAdapter;
    private String country_id="",skills="",hourly_rate="";
    private FloatingActionButton fab_fillter;
    private  Spinner spn_hourly_rate,spn_location;
    private AutoCompleteTextView auto_skils;
    private Button btn_search,btn_cancel;
    private List<String>list_skills,list_list_skills_id,list_hourly_rate,list_hourly_rate_id,list_location,list_location_id;

    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_talent_contractors);

       /* getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_text);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        utility = new Utility(getApplicationContext());

        tv_action_title = (TextView) toolbar.findViewById(R.id.tv_action_title);
        tv_action_title.setText("Contractors");
       // toolbar.setLogo(R.drawable.actionbar_32);

        userId = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_ID,"");

        contractor_recycler_view = (RecyclerView)findViewById(R.id.job_feed_recycler_view);
        contractor_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tv_contractor_not_found = (TextView)findViewById(R.id.tv_feeds_not_found);
        fab_fillter = (FloatingActionButton) findViewById(R.id.fab_fillter);

        final Animation zoomin = AnimationUtils.loadAnimation(FindTalentContractorActivity.this, R.anim.zoom_in);
        final Animation zoomout = AnimationUtils.loadAnimation(FindTalentContractorActivity.this, R.anim.zoom_out);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        findTalent(userId,country_id,skills,hourly_rate);




        fab_fillter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog builder = new AlertDialog.Builder(FindTalentContractorActivity.this).create();

                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View builder_view = inflater.inflate(R.layout.dialog_find_talent_fillter, null);
                builder.setView(builder_view);
                builder.setCancelable(true);

                list_skills = new ArrayList<String>();
                list_list_skills_id= new ArrayList<String>();
                list_hourly_rate= new ArrayList<String>();
                list_hourly_rate_id= new ArrayList<String>();
                list_location= new ArrayList<String>();
                list_location_id= new ArrayList<String>();

                // builder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

                //auto_jobs_by_cat = (AutoCompleteTextView) builder_view.findViewById(R.id.auto_jobs_by_cat);
                auto_skils = (AutoCompleteTextView) builder_view.findViewById(R.id.auto_skils);
                spn_hourly_rate = (Spinner) builder_view.findViewById(R.id.spn_hourly_rate);
                spn_location = (Spinner) builder_view.findViewById(R.id.spn_location);
                btn_search = (Button) builder_view.findViewById(R.id.btn_search);
                btn_cancel = (Button) builder_view.findViewById(R.id.btn_cancel);

                getSkills();
                getHourlyrate();
                getLocation();


              /*  auto_skils.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        int index = list_skills.indexOf(auto_skils.getText().toString());
                        skills = list_list_skills_id.get(index);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                */
                auto_skils.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        int index = list_skills.indexOf(auto_skils.getText().toString());
                       // skills = list_skills.get(index);
                        skills = auto_skils.getText().toString();
                    }
                });

                /*auto_skils.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {

                        if(position>0)
                        {
                            skills = list_list_skills_id.get(position);

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/
                spn_hourly_rate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {

                        if(position>0)
                        {
                            hourly_rate = list_hourly_rate_id.get(position);

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spn_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        if(position>0) {
                            country_id = list_location_id.get(position);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                btn_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        builder.dismiss();

                        skills = auto_skils.getText().toString();
                        findTalent(userId,country_id,skills,hourly_rate);

                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        builder.dismiss();

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



        contractor_recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener()
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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void findTalent(String empId, String country_id, String skills, String hourly_rate)
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();
            params.put("empId", empId);
            params.put("country_id", country_id);
            params.put("skills", skills);
            params.put("hourly_rate", hourly_rate);
            ServiceHandler serviceHandler = new ServiceHandler(FindTalentContractorActivity.this);
            final ArrayList<FindTalent> feedsArrayList = new ArrayList<FindTalent>();

            serviceHandler.registerUser(params, strFindTalent, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status,str_msg;
                    String userId, contractor_name,contractor_country, overView, hourly_rates, contractor_pic;
                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            if(str_status.equalsIgnoreCase("true"))
                            {
                                JSONArray jArray = new JSONArray();
                                jArray = jobject.getJSONArray("Contractors");

                                for (int i = 0; i < jArray.length(); i++)
                                {
                                    JSONObject Obj = jArray.getJSONObject(i);
                                    // A category variables
                                    userId = Obj.getString("userId");
                                    contractor_name = Obj.getString("contractor_name");
                                    contractor_country = Obj.getString("contractor_country");
                                    overView = Obj.getString("overView");
                                    hourly_rates = Obj.getString("hourly_rates");
                                    contractor_pic = Obj.getString("contractor_pic");

                                    JSONArray skillsArray = new JSONArray();
                                    skillsArray = Obj.getJSONArray("skills");

                                    String str_skills="";
                                    for (int j = 0; j < skillsArray.length(); j++) {
                                        //JSONObject ObjSkill = skillsArray.getJSONObject(i);

                                        str_skills = str_skills +", "+ skillsArray.getString(j);
                                    }


                                    FindTalent feeds = new FindTalent(userId,contractor_name,contractor_country,overView,contractor_pic,hourly_rates,str_skills);
                                    feedsArrayList.add(feeds);

                                }

                                if(str_status.equals("true"))
                                {
                                    tv_contractor_not_found.setVisibility(View.GONE);
                                    contractor_recycler_view.setVisibility(View.VISIBLE);
                                    findTalentContractorAdapter = new FindTalentContractorAdapter(FindTalentContractorActivity.this, feedsArrayList);
                                    contractor_recycler_view.setAdapter(findTalentContractorAdapter);
                                }
                                else
                                {
                                    tv_contractor_not_found.setVisibility(View.VISIBLE);
                                    contractor_recycler_view.setVisibility(View.GONE);

                                }


                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "This may be server issue",Toast.LENGTH_SHORT).show();
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

    private void getSkills()
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();
           // params.put("skill_keyword", "php");

            ServiceHandler serviceHandler = new ServiceHandler(FindTalentContractorActivity.this);

            serviceHandler.registerUser(params, strGetSkills, new ServiceHandler.VolleyCallback() {
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
                                JSONArray jArraySkills = new JSONArray();
                                jArraySkills = jobject.getJSONArray("skillList");

                               /* list_skills.add("Select Skill");
                                list_list_skills_id.add("");*/

                                for (int i = 0; i < jArraySkills.length(); i++)
                                {
                                    JSONObject Obj = jArraySkills.getJSONObject(i);

                                    String skill_id = Obj.getString("skill_id");
                                    String skill_name = Obj.getString("skill_name");

                                    list_skills.add(skill_name);
                                    list_list_skills_id.add(skill_id);

                                }

                               /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(FindTalentContractorActivity.this,android.R.layout.simple_spinner_dropdown_item,list_skills);
                                auto_skils.setAdapter(adapter);*/
                                //auto_jobs_by_cat.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                        (FindTalentContractorActivity.this,android.R.layout.simple_list_item_1,list_skills);
                                auto_skils.setAdapter(adapter);
                               // auto_skils.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);


                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "This may be server issue",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(), "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

    }
    private void getHourlyrate()
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(FindTalentContractorActivity.this);

            serviceHandler.registerUser(params, strGetHourlyRate, new ServiceHandler.VolleyCallback() {
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
                                JSONArray jArraySkills = new JSONArray();
                                jArraySkills = jobject.getJSONArray("skillList");

                                list_hourly_rate.add("Select Hourly Rate");
                                list_hourly_rate_id.add("");

                                for (int i = 0; i < jArraySkills.length(); i++)
                                {
                                    JSONObject Obj = jArraySkills.getJSONObject(i);

                                    String rate_id = Obj.getString("rate_id");
                                    String names = Obj.getString("names");

                                    list_hourly_rate.add(names);
                                    list_hourly_rate_id.add(rate_id);

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(FindTalentContractorActivity.this,android.R.layout.simple_spinner_dropdown_item,list_hourly_rate);
                                spn_hourly_rate.setAdapter(adapter);
                                //auto_jobs_by_cat.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "This may be server issue",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(), "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void getLocation()
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(FindTalentContractorActivity.this);

            serviceHandler.registerUser(params, strGetCountries, new ServiceHandler.VolleyCallback() {
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
                                JSONArray jArraySkills = new JSONArray();
                                jArraySkills = jobject.getJSONArray("countries");

                                list_location.add("Select Location");
                                list_location_id.add("");

                                for (int i = 0; i < jArraySkills.length(); i++)
                                {
                                    JSONObject Obj = jArraySkills.getJSONObject(i);

                                    String countryId = Obj.getString("countryId");
                                    String country_name = Obj.getString("country_name");

                                    list_location.add(country_name);
                                    list_location_id.add(countryId);

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(FindTalentContractorActivity.this,android.R.layout.simple_spinner_dropdown_item,list_location);
                                spn_location.setAdapter(adapter);
                                //auto_jobs_by_cat.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "This may be server issue",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(), "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

    }

}
