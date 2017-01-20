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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.JobFeedsAdapter;
import adapter.ViewMilstonesAdapter;
import services.Application_Constants;
import services.MultipartUtility;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;

public class ViewMilstonesActivity extends AppCompatActivity
{

    private String strViewMilestones = Application_Constants.Main_URL+"keyword=view_paymentOrMilestone";
    private RecyclerView milestone_recycler_view;
    private TextView tv_milestone_not_found;
    private ViewMilstonesAdapter viewMilstonesAdapter;
    private Toolbar toolbar;
    private Utility utility;
    private TextView tv_action_title;

    private Button btn_add_milestone;

    // parametres
    String userId,jobId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_milstones);

       /* getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_text);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        //toolbar.setLogo(R.drawable.actionbar_32);

        utility = new Utility(getApplicationContext());
        milestone_recycler_view = (RecyclerView)findViewById(R.id.milestone_recycler_view);
        milestone_recycler_view.setLayoutManager(new LinearLayoutManager(ViewMilstonesActivity.this));
        tv_milestone_not_found = (TextView) findViewById(R.id.tv_milestone_not_found);

        tv_action_title = (TextView) toolbar.findViewById(R.id.tv_action_title);
        tv_action_title.setText("Milestones");

        btn_add_milestone= (Button)findViewById(R.id.btn_add_milestone);

        userId = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_ID,"");

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

        Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {
            jobId = intent.getStringExtra("jobId");

            getMilestone(userId,jobId);
        }

        btn_add_milestone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ViewMilstonesActivity.this,AddMilestoneActivity.class);
                intent.putExtra("jobId",jobId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);


    }

    private void getMilestone(String userId, String jobId)
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();
            params.put("contractorId", userId);
            params.put("jobId", jobId);

            ServiceHandler serviceHandler = new ServiceHandler(ViewMilstonesActivity.this);
            final ArrayList<ViewMilestones> viewMilestonesArrayList = new ArrayList<ViewMilestones>();

            serviceHandler.registerUser(params, strViewMilestones, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status,str_msg;
                    String paymentId,status,paymentDate,amount,description,attachment;
                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            if(str_status.equalsIgnoreCase("true"))
                            {
                                JSONArray jArray = new JSONArray();
                                jArray = jobject.getJSONArray("Paymentlist");

                                for (int i = 0; i < jArray.length(); i++)
                                {
                                    JSONObject Obj = jArray.getJSONObject(i);
                                    // A category variables
                                    paymentId = Obj.getString("paymentId");
                                    status = Obj.getString("status");
                                    paymentDate = Obj.getString("paymentDate");
                                    amount = Obj.getString("amount");
                                    description = Obj.getString("description");
                                    attachment = Obj.getString("attachment");

                                    ViewMilestones viewMilestones = new ViewMilestones(paymentId, status, paymentDate, amount,  description,
                                            attachment);
                                    viewMilestonesArrayList.add(viewMilestones);

                                }

                                if(str_status.equals("true"))
                                {
                                    tv_milestone_not_found.setVisibility(View.GONE);
                                    milestone_recycler_view.setVisibility(View.VISIBLE);
                                    viewMilstonesAdapter = new ViewMilstonesAdapter(getApplicationContext(), viewMilestonesArrayList);
                                    milestone_recycler_view.setAdapter(viewMilstonesAdapter);
                                }
                                else
                                {
                                    tv_milestone_not_found.setVisibility(View.VISIBLE);
                                    milestone_recycler_view.setVisibility(View.GONE);

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



}
