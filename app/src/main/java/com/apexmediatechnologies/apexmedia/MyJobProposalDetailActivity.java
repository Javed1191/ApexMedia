package com.apexmediatechnologies.apexmedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import services.Application_Constants;
import services.FetchPath;
import services.MultipartUtility;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;

public class MyJobProposalDetailActivity extends AppCompatActivity
{

    private Utility utility;
    private String strGetJobProposals = Application_Constants.Main_URL+"keyword=proposalDetail";
    String porposal_id="",userId="";
    private ImageView img_user;
    private TextView tv_user_name,tv_address,tv_time,tv_hourly_rate,tv_hour,tv_desc,tv_est_duration,tv_approach,
            tv_skills,tv_bid_id,tv_submitted,tv_awarded;
    private RatingBar rating_user;
    private TextView tv_action_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_job_proposals_detail);

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
        tv_action_title.setText("Job Proposal");
        //toolbar.setLogo(R.drawable.actionbar_32);

        utility = new Utility(getApplicationContext());
        img_user = (ImageView) findViewById(R.id.img_user);

        tv_user_name = (TextView)findViewById(R.id.tv_user_name);
        tv_address = (TextView)findViewById(R.id.tv_address);
        tv_time = (TextView)findViewById(R.id.tv_time);
        tv_hourly_rate = (TextView) findViewById(R.id.tv_hourly_rate);
        tv_hour = (TextView) findViewById(R.id.tv_hour);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_est_duration = (TextView) findViewById(R.id.tv_est_duration);
        tv_approach = (TextView) findViewById(R.id.tv_approach);
        tv_skills = (TextView) findViewById(R.id.tv_skills);
        tv_bid_id = (TextView) findViewById(R.id.tv_bid_id);
        tv_submitted = (TextView) findViewById(R.id.tv_submitted);
        tv_awarded = (TextView) findViewById(R.id.tv_awarded);

        rating_user = (RatingBar) findViewById(R.id.rating_user);

        userId = Shared_Preferences_Class.readString(getApplicationContext(), Shared_Preferences_Class.USER_ID, "");

        try
        {
            Intent intent = getIntent();

            if(intent!= null)
            {

                porposal_id = intent.getStringExtra("porposal_id");

                if(utility.checkInternet())
                {
                    // new GetProductDetails(product_id).execute();

                    getJobProposals(userId,porposal_id);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please connect to internet", Toast.LENGTH_SHORT).show();


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



    }






    private void getJobProposals(String empId, String proposalId)
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();
            params.put("empId", empId);
            params.put("proposalId", porposal_id);

            ServiceHandler serviceHandler = new ServiceHandler(MyJobProposalDetailActivity.this);
            final ArrayList<JobProposals> feedsArrayList = new ArrayList<JobProposals>();

            serviceHandler.registerUser(params, strGetJobProposals, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status, str_msg;
                    String senderId, senderName, senderImage, country, hourly_rate, estimated_duration, description,
                            approach, skills, rating, bid_id, submitted_date, awarded_date,hours_per_week;
                    try {
                        if (str_json != null) {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            if (str_status.equalsIgnoreCase("true"))
                            {
                                JSONArray jArray = new JSONArray();
                                jArray = jobject.getJSONArray("proposalDetail");

                                for (int i = 0; i < jArray.length(); i++)
                                {
                                    JSONObject Obj = jArray.getJSONObject(i);
                                    // A category variables
                                    senderId = Obj.getString("senderId");
                                    senderName = Obj.getString("senderName");
                                    senderImage = Obj.getString("senderImage");
                                    country = Obj.getString("country");
                                    hourly_rate = Obj.getString("hourly_rate");
                                    estimated_duration = Obj.getString("estimated_duration");
                                    description = Obj.getString("description");
                                    approach = Obj.getString("approach");
                                    skills= Obj.getString("skills");
                                    rating = Obj.getString("rating");
                                    bid_id = Obj.getString("bid_id");
                                    submitted_date = Obj.getString("submitted_date");
                                    awarded_date = Obj.getString("awarded_date");
                                    hours_per_week  = Obj.getString("hours_per_week");

                                    tv_user_name.setText(senderName);
                                    //Download image using picasso library
                                    Picasso.with(getApplicationContext()).load(senderImage)
                                            .into(img_user);

                                    tv_address.setText(country);
                                    tv_hourly_rate.setText(hourly_rate);
                                    tv_hour.setText(hours_per_week);
                                    tv_est_duration.setText(estimated_duration);
                                    tv_desc.setText(description);
                                    tv_approach.setText(approach);
                                    tv_skills.setText(skills);
                                    rating_user.setRating(Float.parseFloat(rating));
                                    tv_bid_id.setText(bid_id);
                                    tv_submitted.setText(submitted_date);
                                    tv_awarded.setText(awarded_date);

                                }

                                if (str_status.equals("true"))
                                {

                                }
                                else
                                {


                                }


                            } else {
                                Toast.makeText(getApplicationContext(), str_msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "This may be server issue", Toast.LENGTH_SHORT).show();
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
