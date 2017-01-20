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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import java.util.List;
import java.util.Map;

import services.Application_Constants;
import services.FetchPath;
import services.MultipartUtility;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;


public class FindTalentActivity extends AppCompatActivity
{

    private String strPostJob = Application_Constants.Main_URL+"keyword=post_job";
    private String strJobList = Application_Constants.Main_URL+"keyword=hire_now_jobList";
    private String strHireNow = Application_Constants.Main_URL+"keyword=hire_now";

    private Utility utility;
    private TextView tv_action_title,tv_name,tv_country;
    private Button btn_send;
    private Spinner spn_jobs;
    private EditText edt_message;

    // parametres
    String userId,submit_amt_later="0",jobId,contractor_id,contractor_name,contractor_country,contractor_image,job_id="" ;
    private RoundedImageView img_user;
    private List<String>list_job_id,list_jobs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_talent);

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
        tv_action_title.setText("Find Talent");
       // toolbar.setLogo(R.drawable.actionbar_32);

        edt_message = (EditText) findViewById(R.id.edt_message);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_country = (TextView) findViewById(R.id.tv_country);
        btn_send= (Button) findViewById(R.id.btn_send);
        img_user = (RoundedImageView) findViewById(R.id.img_user);
        spn_jobs = (Spinner) findViewById(R.id.spn_jobs);
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

            contractor_id  = intent.getStringExtra("contractor_id");
            contractor_name  = intent.getStringExtra("contractor_name");
            contractor_country  = intent.getStringExtra("contractor_country");
            contractor_image  = intent.getStringExtra("contractor_image");

            tv_name.setText(contractor_name);
            tv_country.setText(contractor_country);

            Picasso.with(FindTalentActivity.this).load(contractor_image)
                    .into(img_user);

        }

        getJobs(userId);

        spn_jobs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position>0) {
                    job_id  = list_job_id.get(position);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

              String  mesage = edt_message.getText().toString().trim();


                if (!job_id.equals("")&&!job_id.equals("null"))
                {
                    if (utility.checkInternet())
                    {

                        hireNow(userId,contractor_id,job_id,mesage);
                    } else {
                        Toast.makeText(getApplicationContext(), "Plaese connect to the intenet", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Plaese select any job", Toast.LENGTH_SHORT).show();

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



    private void getJobs(String empId )
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();
            params.put("empId", empId);

            ServiceHandler serviceHandler = new ServiceHandler(FindTalentActivity.this);

            serviceHandler.registerUser(params, strJobList, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status,str_msg;
                    list_jobs = new ArrayList<String>();
                    list_job_id = new ArrayList<String>();
                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            if(str_status.equalsIgnoreCase("true"))
                            {
                                JSONArray jArraySkills = new JSONArray();
                                jArraySkills = jobject.getJSONArray("allJobs");

                                list_jobs.add("Select Jobs");
                                list_job_id.add("");

                                for (int i = 0; i < jArraySkills.length(); i++)
                                {
                                    JSONObject Obj = jArraySkills.getJSONObject(i);

                                    String jobId = Obj.getString("jobId");
                                    String jobName = Obj.getString("jobName");

                                    list_jobs.add(jobName);
                                    list_job_id.add(jobId);

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(FindTalentActivity.this,android.R.layout.simple_spinner_dropdown_item,list_jobs);
                                spn_jobs.setAdapter(adapter);
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

    private void hireNow(String empId, String contractor_id ,String job_id ,String message )
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();
            params.put("empId", empId);
            params.put("contractor_id", contractor_id);
            params.put("job_id", job_id);
            params.put("message", message);

            ServiceHandler serviceHandler = new ServiceHandler(FindTalentActivity.this);

            serviceHandler.registerUser(params, strHireNow, new ServiceHandler.VolleyCallback() {
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
                                Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();

                                finish();

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
