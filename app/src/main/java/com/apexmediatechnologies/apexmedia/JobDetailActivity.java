package com.apexmediatechnologies.apexmedia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;

public class JobDetailActivity extends AppCompatActivity
{

    private String strSetJobProgress = Application_Constants.Main_URL+"keyword=setJobProgress";
   // private String strGetProducts ="http://192.168.1.3/hdfc_bso/android_web/action?xAction=products";
    private Utility utility;
    private TextView tv_job_title,tv_posted_date,tv_action_title,tv_budget,tv_job_desc,tv_skills,
            tv_user_name,tv_user_country,tv_start_type,tv_set_status,tv_dispute;
    private ImageView img_user;
    private boolean is_file;
    private RatingBar rating_user;
    private Button btn_view_milestone;
    private String jobProgress,jobId,jobTitle,jobDec, jobSkills, jobStartType,jobPostedDate,jobBudget,
    userName, userCountry,userImage,userId,empId;
    private ProgressBar prog_job_progress;
    int job_status=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project_details);

       /* getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_text);*/

        tv_job_title = (TextView) findViewById(R.id.tv_job_title);
        tv_posted_date= (TextView) findViewById(R.id.tv_posted_date);
        tv_start_type = (TextView) findViewById(R.id.tv_start_type);
        tv_budget = (TextView) findViewById(R.id.tv_budget);
        tv_job_desc = (TextView) findViewById(R.id.tv_job_desc);
        tv_skills = (TextView) findViewById(R.id.tv_skills);
        img_user  = (ImageView) findViewById(R.id.img_user);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_user_country = (TextView) findViewById(R.id.tv_user_country);
        rating_user = (RatingBar) findViewById(R.id.rating_user);
        btn_view_milestone= (Button) findViewById(R.id.btn_view_milestone);
        prog_job_progress = (ProgressBar) findViewById(R.id.prog_job_progress);
        tv_set_status = (TextView) findViewById(R.id.tv_set_status);
        tv_dispute = (TextView) findViewById(R.id.tv_dispute);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

       // toolbar.setLogo(R.drawable.actionbar_32);

        userId = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_ID,"");


        tv_action_title = (TextView) toolbar.findViewById(R.id.tv_action_title);
        tv_action_title.setText("Job Details");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
               /* Intent intent = new Intent(JobDetailActivity.this, MainActivity.class);
                intent.putExtra("menu", "dashboard");
                startActivity(intent);*/
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobDetailActivity.this, MainActivity.class);
                intent.putExtra("menu", "dashboard");
                startActivity(intent);


            }
        });

        tv_job_title = (TextView) findViewById(R.id.tv_job_title);

        utility = new Utility(getApplicationContext());

        Intent intent = getIntent();

        if(intent.getExtras()!=null)
        {

            jobProgress = intent.getStringExtra("jobProgress");
            jobId = intent.getStringExtra("jobId");
            jobTitle = intent.getStringExtra("jobTitle");
            jobDec = intent.getStringExtra("jobDec");
            jobSkills = intent.getStringExtra("jobSkills");
            jobStartType = intent.getStringExtra("jobStartType");
            jobPostedDate = intent.getStringExtra("jobPostedDate");
            jobBudget = intent.getStringExtra("jobBudget");
            userName = intent.getStringExtra("userName");
            userCountry = intent.getStringExtra("userCountry");
            userImage = intent.getStringExtra("userImage");
            empId = intent.getStringExtra("empId");

            prog_job_progress.setProgress(Integer.parseInt(jobProgress));
            tv_job_title.setText(jobTitle);
            tv_job_desc.setText(jobDec);
            tv_skills.setText(jobSkills);
            tv_start_type.setText("Start: "+jobStartType);
            tv_posted_date.setText("Posted: "+jobPostedDate);
            tv_budget.setText("Budget: "+jobBudget);
            tv_user_name.setText(userName);
            tv_user_country.setText(userCountry);

            //Download image using picasso library
            Picasso.with(getApplicationContext()).load(userImage)
                    .into(img_user);
/*
            if(utility.checkInternet())
            {
               // new GetProductDetails(product_id).execute();

                getJobDetails(jobId);
            }
            else
            {
                Toast.makeText(JobDetailActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();


            }
*/

        }

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        btn_view_milestone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobDetailActivity.this,ViewMilstonesActivity.class);
                intent.putExtra("jobId",jobId);
                startActivity(intent);
            }
        });

        tv_set_status.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final AlertDialog builder = new AlertDialog.Builder(JobDetailActivity.this).create();

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View builder_view = inflater.inflate(R.layout.activity_set_status_dialog, null);
                builder.setView(builder_view);
                // builder.setCancelable(false);

                // builder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


                TextView tv_save = (TextView) builder_view.findViewById(R.id.tv_save);
                TextView tv_cancel = (TextView) builder_view.findViewById(R.id.tv_cancel);
                final TextView tv_status = (TextView) builder_view.findViewById(R.id.tv_status);
                final SeekBar skbar_job_status = (SeekBar) builder_view.findViewById(R.id.skbar_job_status);


                skbar_job_status.setProgress(Integer.parseInt(jobProgress));
                tv_status.setText(jobProgress + "%");
                skbar_job_status.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                    {
                        job_status = progress;


                        tv_status.setText(job_status+"%");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });


                tv_save.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        try {

                            setJobStatus(userId, jobId, String.valueOf(skbar_job_status.getProgress()));
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

                            //dialog.dismiss();
                            builder.cancel();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                //dialog.show();
                builder.show();
            }
        });

        tv_dispute.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(JobDetailActivity.this,ChatActivity.class);
                intent.putExtra("dispute","dispute");
                intent.putExtra("toUserId",empId);
                intent.putExtra("jobId",jobId);
                intent.putExtra("type","contractor");
                intent.putExtra("project",jobTitle);
                startActivity(intent);
            }
        });
    }




    private void setJobStatus(String contractorId,String job_id,String progress)
    {

        if(utility.checkInternet())
        {

            // userGcmRegID = Shared_Preferences_Class.readString(LoginActivity.this, Shared_Preferences_Class.GCM_REG_ID, "");


            final Map<String, String> params = new HashMap<String, String>();
            params.put("contractorId", contractorId);
            params.put("job_id", job_id);
            params.put("progress", progress);

            ServiceHandler serviceHandler = new ServiceHandler(JobDetailActivity.this);

            serviceHandler.registerUser(params, strSetJobProgress, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status = "",str_msg;
                    String   jobId, job_title,job_description,work_type, work_rate,work_rate_description,
                    hours_per_week,job_budget,job_skills,start_type,schedule_date,updated_on,
                    created_at,posted_userId,posted_user_firt_name,posted_user_last_name,user_country_name,user_image;
                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");

                            if(str_status.equalsIgnoreCase("true"))
                            {

                                Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();
                                prog_job_progress.setProgress(job_status);
                                jobProgress = String.valueOf(job_status);

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




    /*Show pdf file in available pdf viewer*/
    public void showPdf()
    {
        try
        {
            File file = new File(Environment.getExternalStorageDirectory()+"/BsoPdf/"+userImage);
            PackageManager packageManager = getPackageManager();
            Intent testIntent = new Intent(Intent.ACTION_VIEW);
            testIntent.setType("application/pdf");
            List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
/*check is file exist or not*/

    public boolean isFileExist()
    {
        File file = null;
        try
        {
            file = new File(Environment.getExternalStorageDirectory()+"/BsoPdf/"+userImage);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return  file.exists();
    }



}
