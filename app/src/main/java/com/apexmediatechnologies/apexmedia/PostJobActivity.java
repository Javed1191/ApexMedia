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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.ExpandableListAdapter;
import services.Application_Constants;
import services.FetchPath;
import services.MultipartUtility;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;


public class PostJobActivity extends AppCompatActivity
{

    private String strPostJob = Application_Constants.Main_URL+"keyword=post_job";
    private String strUpdateJob = Application_Constants.Main_URL+"keyword=update_job";
    private String strGetCountyList= Application_Constants.Main_URL+"keyword=countries";
    private String strGetStateList= Application_Constants.Main_URL+"keyword=states";
    private String strJobCategoryList= Application_Constants.Main_URL+"keyword=job_category";
    private String strJobSubCategoryList= Application_Constants.Main_URL+"keyword=job_subcategory";
    private String strJobDropdownLists= Application_Constants.Main_URL+"keyword=postJobDropDowns";
    private String strGetJobDetails = Application_Constants.Main_URL+"keyword=jobDetail";


   // private String strGetProducts ="http://192.168.1.3/hdfc_bso/android_web/action?xAction=products";
    private Utility utility;
    private TextView tv_action_title;
    private LinearLayout lay_products;
    private TextView tv_date;
    private ProgressDialog dialogTax;


    private int PICK_FILE_REQUEST1 = 1;
    String str_image_path;
    byte[]  byteArray;
    String encodedImage;
    File attachment1;
    ImageView img_upload1;
    private EditText edt_job_name,edt_job_decs,edt_skills,edt_hourly_rate,edt_budget;
    private CheckBox chk_sub_amt_alt;
    private Button btn_choose_file,btn_submit;
    private  TextView tv_file1;
    private AutoCompleteTextView auto_category,auto_sub_category,auto_work_type,auto_work_type_rate,auto_duration;;

    // parametres
    String userId,submit_amt_later="0",jobId;
    boolean is_dob = false,is_file1=false,is_file2=false,is_file3=false;
    private RadioGroup rdo_grp_public_private,rdo_grp_propose_start_date;
    private RadioButton rdo_btn_public,rdo_btn_private,rdo_btn_immediately,rdo_btn_scheduled;
    private List<String>list_job_cat_name,list_job_cat_id,list_job_sub_cat_name,list_job_sub_cat_id;

    private List<String>list_durstion_name,list_durstion_id,list_work_types_name,list_work_types_id,
            list_work_type_rate_name,list_work_type_rate_id;

    String jobCatId="",jobSubCatId="",jobWorkType="",jobWorkTypeRate="",jobDuration="",jobHourlyRate="",fixed_budget="";
    private boolean is_update = false;
    private LinearLayout lay_work_arrangment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

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
        tv_action_title.setText("Post Job");
        //toolbar.setLogo(R.drawable.actionbar_32);

       // tv_date = (TextView) findViewById(R.id.tv_date);
        img_upload1 = (ImageView) findViewById(R.id.img_upload1);
        tv_file1 = (TextView) findViewById(R.id.tv_file1);
        edt_job_name = (EditText) findViewById(R.id.edt_job_name);
        edt_job_decs = (EditText)findViewById(R.id.edt_job_decs);
        edt_hourly_rate = (EditText)findViewById(R.id.edt_hourly_rate);
        auto_category = (AutoCompleteTextView) findViewById(R.id.auto_category);
        auto_sub_category = (AutoCompleteTextView) findViewById(R.id.auto_sub_category);
        edt_skills= (EditText)findViewById(R.id.edt_skills);
        auto_work_type = (AutoCompleteTextView) findViewById(R.id.auto_work_type);
        auto_work_type_rate = (AutoCompleteTextView) findViewById(R.id.auto_work_type_rate);
        auto_duration = (AutoCompleteTextView) findViewById(R.id.auto_duration);
        lay_work_arrangment = (LinearLayout) findViewById(R.id.lay_work_arrangment);
        edt_budget = (EditText) findViewById(R.id.edt_budget);

        rdo_grp_public_private = (RadioGroup) findViewById(R.id.rdo_grp_public_private);
        rdo_btn_public = (RadioButton) findViewById(R.id.rdo_btn_public);
        rdo_btn_private = (RadioButton) findViewById(R.id.rdo_btn_private);

        rdo_grp_propose_start_date = (RadioGroup) findViewById(R.id.rdo_grp_propose_start_date);
        rdo_btn_immediately = (RadioButton) findViewById(R.id.rdo_btn_immediately);
        rdo_btn_scheduled = (RadioButton) findViewById(R.id.rdo_btn_scheduled);

        btn_submit= (Button)findViewById(R.id.btn_submit);

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

        list_job_cat_name = new ArrayList<>();
        list_job_cat_id = new ArrayList<>();



        Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {



            if(intent.getStringExtra("is_update")!=null)
            {
                jobId = intent.getStringExtra("jobId");
                tv_action_title.setText("Update Job");
                is_update = true;

                getCategories();
                getDropdownLists();


            }
            else
            {
                getCategories();
                getDropdownLists();
            }

        }
        else
        {
            getCategories();
            getDropdownLists();
        }




       /* edt_date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                showDatePicker();

            }
        });*/

        img_upload1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select file"),PICK_FILE_REQUEST1 );

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

              String  jobName = edt_job_name.getText().toString().trim();
                String jobDesc = edt_job_decs.getText().toString().trim();

                String jobSkills = edt_skills.getText().toString().trim();
                String jobHourlyRate = edt_hourly_rate.getText().toString();
                //String jobVisibility = spn_duration.getSelectedItem().toString();
                String jobVisibility="Public",jobPropStartType="1";

                // get selected radio button from radioGroup
                int selectedId = rdo_grp_public_private.getCheckedRadioButtonId();
                int selectedId1 = rdo_grp_propose_start_date.getCheckedRadioButtonId();
                fixed_budget = edt_budget.getText().toString();


                if(selectedId== R.id.rdo_btn_public)
                {
                    jobVisibility ="Public";
                }
                else {
                    jobVisibility ="Private";
                }
                if(selectedId== R.id.rdo_btn_immediately)
                {
                    jobPropStartType="1";
                }
                else
                {
                    jobPropStartType="0";
                }


                if (jobName.equals("") || jobDesc.equals("")|| jobCatId.equals("")|| jobSubCatId.equals("") ) {

                    if (jobName.equals(""))
                    {
                        //Toast.makeText(getApplicationContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
                        // edt_first_name.setError("Enter First Name");
                        edt_job_name.requestFocus();
                        edt_job_name.setError("Enter Job Name");

                    }
                    else if (jobDesc.equals("")) {
                        //  Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
                        // edt_last_name.setError("Enter Last Name");
                        edt_job_decs.requestFocus();

                        edt_job_decs.setError("Enter Job Description");
                    }
                    else if (jobCatId.equals("")) {
                        //  Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
                        // edt_last_name.setError("Enter Last Name");
                        auto_category.requestFocus();

                        auto_category.setError("Select Job Category");


                    }
                    else if (jobSubCatId.equals("")) {
                        //  Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
                        // edt_last_name.setError("Enter Last Name");
                        auto_sub_category.requestFocus();

                        auto_sub_category.setError("Select Job Sub Category");


                    }


                    //Toast.makeText(getApplicationContext(), "Enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    if(is_update)
                    {
                        if (utility.checkInternet())
                        {

                            new JobUpdate(jobId,jobName,jobDesc,jobCatId,jobSubCatId,jobSkills,jobWorkType,jobWorkTypeRate,
                                    jobHourlyRate,jobDuration,jobVisibility,jobPropStartType,fixed_budget).execute();

                        } else {
                            Toast.makeText(getApplicationContext(), "Plaese connect to the intenet", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        if (utility.checkInternet())
                        {


                            new JobProposal(userId,jobName,jobDesc,jobCatId,jobSubCatId,jobSkills,jobWorkType,jobWorkTypeRate,
                                    jobHourlyRate,jobDuration,jobVisibility,jobPropStartType,fixed_budget).execute();

                        } else {
                            Toast.makeText(getApplicationContext(), "Plaese connect to the intenet", Toast.LENGTH_SHORT).show();
                        }
                    }




                }


            }
        });

        auto_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto_category.showDropDown();
            }
        });
        auto_category.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    auto_category.showDropDown();
                }
            }
        });


        auto_category.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                int index = list_job_cat_name.indexOf(auto_category.getText().toString());
                jobCatId = list_job_cat_id.get(index);
                getSubCategories(jobCatId);
            }
        });

        auto_sub_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = list_job_sub_cat_name.indexOf(auto_sub_category.getText().toString());

                jobSubCatId = list_job_sub_cat_id.get(index);
            }
        });

        auto_sub_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto_sub_category.showDropDown();
            }
        });

        auto_sub_category.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    auto_sub_category.showDropDown();
                }
            }
        });


        auto_work_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                int index = list_work_types_name.indexOf(auto_work_type.getText().toString());
                jobWorkType = list_work_types_id.get(index);

                if(auto_work_type.getText().toString().equalsIgnoreCase("Fixed"))
                {
                    lay_work_arrangment.setVisibility(View.GONE);
                    edt_budget.setVisibility(View.VISIBLE);
                }
                else
                {
                    lay_work_arrangment.setVisibility(View.VISIBLE);
                    edt_budget.setVisibility(View.GONE);
                    edt_budget.setText("");
                }
            }
        });

        auto_work_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto_work_type.showDropDown();
            }
        });

        auto_work_type.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    auto_work_type.showDropDown();
                }
            }
        });


        auto_work_type_rate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = list_work_type_rate_name.indexOf(auto_work_type_rate.getText().toString());

                jobWorkTypeRate = list_work_type_rate_id.get(index);

              //  jobWorkTypeRate = auto_work_type_rate.getText().toString();
            }
        });

        auto_work_type_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto_work_type_rate.showDropDown();
            }
        });

        auto_work_type_rate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    auto_work_type_rate.showDropDown();
                }
            }
        });
        auto_duration.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = list_durstion_name.indexOf(auto_duration.getText().toString());

                jobDuration = list_durstion_id.get(index);
            }
        });

        auto_duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto_duration.showDropDown();
            }
        });

        auto_duration.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    auto_duration.showDropDown();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);


       /* if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img_prescription.setImageBitmap(photo);
        }*/

        if (requestCode == PICK_FILE_REQUEST1 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
          //  str_image_path = getPath(uri);

            try {

                Uri uri = data.getData();
               // str_image_path = uri.getPath();
                str_image_path = FetchPath.getPath(this, uri);



                String str_file_name = uri.getLastPathSegment();
                tv_file1.setText(str_file_name);
                is_file1 = true;


                    // code to get image from image view and convert it into file

                    File filesDir =getFilesDir();
                attachment1 = new File(str_image_path);
               long file_size =  attachment1.length();
                long file_size_kb = file_size/1024;

                System.out.print(file_size_kb);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    class JobProposal extends AsyncTask<String, String, String>
    {
        String userId="",jobName,jobDesc,jobCatId,jobSubCatId,jobSkills,jobWorkType,jobWorkTypeRate,jobHourlyRate,jobDuration,
                jobVisibility,jobPropStartType,fixed_budget ;
        String str_status,json_responce,str_msg="";

        public JobProposal(String userId, String jobName, String jobDesc,String jobCatId,String jobSubCatId,String jobSkills,
        String jobWorkType, String jobWorkTypeRate, String jobHourlyRate, String jobDuration, String jobVisibility, String jobPropStartType, String fixed_budget)
        {
            this.userId = userId;
            this.jobName = jobName;
            this.jobDesc = jobDesc;
            this.jobCatId = jobCatId;
            this.jobSubCatId = jobSubCatId;
            this.jobSkills = jobSkills;
            this.jobWorkType = jobWorkType;
            this.jobWorkTypeRate = jobWorkTypeRate;
            this.jobHourlyRate = jobHourlyRate;
            this.jobDuration = jobDuration;
            this.jobVisibility = jobVisibility;
            this.jobPropStartType = jobPropStartType;
            this.fixed_budget=fixed_budget;
        }

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialogTax = new ProgressDialog(PostJobActivity.this,R.style.MyTheme);
            dialogTax.setCancelable(false);
            dialogTax.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialogTax.show();

        }

        @Override
        protected String doInBackground(String... params)
        {
            // TODO Auto-generated method stub

            String charset = "UTF-8";

            // File uploadFile1 = new File(str_image_path);
            try {
                MultipartUtility multipart = new MultipartUtility(strPostJob, charset);
                multipart.addHeaderField("User-Agent", "CodeJava");
                multipart.addHeaderField("Test-Header", "Header-Value");
                multipart.addFormField("description", "Cool Pictures");
                multipart.addFormField("keywords", "Java,upload,Spring");
                multipart.addFormField("empId", userId);
                multipart.addFormField("jobName", jobName);
                multipart.addFormField("jobDesc", jobDesc);
                multipart.addFormField("jobCatId", jobCatId);
                multipart.addFormField("jobSubCatId", jobSubCatId);
                multipart.addFormField("jobSkills", jobSkills);
                multipart.addFormField("jobWorkType", jobWorkType);
                multipart.addFormField("jobWorkTypeRate", jobWorkTypeRate);
                multipart.addFormField("jobHourlyRate", jobHourlyRate);
                multipart.addFormField("jobDuration", jobDuration);
                multipart.addFormField("jobVisibility", jobVisibility);
                multipart.addFormField("jobPropStartType", jobPropStartType);
                multipart.addFormField("jobStartDate", "2016-08-20");
                multipart.addFormField("fixed_budget", fixed_budget);


                /* empId= 115, jobName=PHP Developer,
                    jobDesc=Minimum 2 year of experience in relevent field, jobCatId=1,
                    jobSubCatId=15, jobSkills=Core PHP, Javascript, Jquery, Codeigniter,
                    Word Press, jobWorkType=1, jobWorkTypeRate=15, jobHourlyRate=50, jobDuration=7,
                    jobVisibility=Public, jobPropStartType=1, jobStartDate=2016-08-20,
                    jobAttachment=step2.png
*/

                if(is_file1)
                {
                    multipart.addFilePart("jobAttachment", attachment1);
                }
                json_responce= multipart.finish();


            } catch (IOException ex) {
                System.err.println(ex);
            }

            try {
                // str_json = sh.makeServiceCall(registration_url, ServiceHandler.POST, list_param);
                if(json_responce!=null)
                {

                    JSONObject jobject = new JSONObject(json_responce);
                    //JSONObject str_status_obj =jobject.getJSONObject("STATUS");
                    str_msg= jobject.getString("Message");
                    str_status = jobject.getString("Result");

                }
                else
                {
                   // Toast.makeText(getApplicationContext(), "This may be server issue", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            // TODO Auto-generated method stub
            //pDialog.dismiss();
            dialogTax.dismiss();

            super.onPostExecute(result);
            try
            {
                if(str_status.equalsIgnoreCase("true"))
                {
                    Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(PostJobActivity.this,EmployerMainActivity.class);
                    i.putExtra("menu", "jobFeed");
                    startActivity(i);
                    finish();

                }
                else if(str_status.equalsIgnoreCase("false"))
                {

                    Toast.makeText(getApplicationContext(), str_msg, Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();
                }


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }

    }



    class JobUpdate extends AsyncTask<String, String, String>
    {
        String jobId="",jobName,jobDesc,jobCatId,jobSubCatId,jobSkills,jobWorkType,jobWorkTypeRate,jobHourlyRate,jobDuration,
                jobVisibility,jobPropStartType,fixed_budget;
        String str_status,json_responce,str_msg="";

        public JobUpdate(String jobId, String jobName, String jobDesc,String jobCatId,String jobSubCatId,String jobSkills,
                           String jobWorkType, String jobWorkTypeRate, String jobHourlyRate, String jobDuration,
                         String jobVisibility, String jobPropStartType,String fixed_budget)
        {
            this.jobId = jobId;
            this.jobName = jobName;
            this.jobDesc = jobDesc;
            this.jobCatId = jobCatId;
            this.jobSubCatId = jobSubCatId;
            this.jobSkills = jobSkills;
            this.jobWorkType = jobWorkType;
            this.jobWorkTypeRate = jobWorkTypeRate;
            this.jobHourlyRate = jobHourlyRate;
            this.jobDuration = jobDuration;
            this.jobVisibility = jobVisibility;
            this.jobPropStartType = jobPropStartType;
            this.fixed_budget=fixed_budget;
        }

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialogTax = new ProgressDialog(PostJobActivity.this,R.style.MyTheme);
            dialogTax.setCancelable(false);
            dialogTax.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialogTax.show();

        }

        @Override
        protected String doInBackground(String... params)
        {
            // TODO Auto-generated method stub

            String charset = "UTF-8";

            // File uploadFile1 = new File(str_image_path);
            try {
                MultipartUtility multipart = new MultipartUtility(strUpdateJob, charset);
                multipart.addHeaderField("User-Agent", "CodeJava");
                multipart.addHeaderField("Test-Header", "Header-Value");
                multipart.addFormField("description", "Cool Pictures");
                multipart.addFormField("keywords", "Java,upload,Spring");
                multipart.addFormField("jobId", jobId);
                multipart.addFormField("jobName", jobName);
                multipart.addFormField("jobDesc", jobDesc);
                multipart.addFormField("jobCatId", jobCatId);
                multipart.addFormField("jobSubCatId", jobSubCatId);
                multipart.addFormField("jobSkills", jobSkills);
                multipart.addFormField("jobWorkType", jobWorkType);
                multipart.addFormField("jobWorkTypeRate", jobWorkTypeRate);
                multipart.addFormField("jobHourlyRate", jobHourlyRate);
                multipart.addFormField("jobDuration", jobDuration);
                multipart.addFormField("jobVisibility", jobVisibility);
                multipart.addFormField("jobPropStartType", jobPropStartType);
                multipart.addFormField("jobStartDate", "2016-08-20");
                multipart.addFormField("fixed_budget", fixed_budget);

                if(is_file1)
                {
                    multipart.addFilePart("jobAttachment", attachment1);
                }
                json_responce= multipart.finish();


            } catch (IOException ex) {
                System.err.println(ex);
            }

            try {
                // str_json = sh.makeServiceCall(registration_url, ServiceHandler.POST, list_param);
                if(json_responce!=null)
                {

                    JSONObject jobject = new JSONObject(json_responce);
                    //JSONObject str_status_obj =jobject.getJSONObject("STATUS");
                    str_msg= jobject.getString("Message");
                    str_status = jobject.getString("Result");

                }
                else
                {
                    // Toast.makeText(getApplicationContext(), "This may be server issue", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            // TODO Auto-generated method stub
            //pDialog.dismiss();
            dialogTax.dismiss();

            super.onPostExecute(result);
            try
            {
                if(str_status.equalsIgnoreCase("true"))
                {
                    Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(PostJobActivity.this,EmployerMainActivity.class);
                    i.putExtra("menu", "jobFeed");
                    startActivity(i);
                    finish();

                }
                else if(str_status.equalsIgnoreCase("false"))
                {

                    Toast.makeText(getApplicationContext(), str_msg, Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();
                }


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }

    }


    private void getCategories()
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(PostJobActivity.this);
            final ArrayList<JobFeeds> feedsArrayList = new ArrayList<JobFeeds>();

            serviceHandler.registerUser(params, strJobCategoryList, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status,str_msg;
                    String catId, catName,description;

                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            if(str_status.equalsIgnoreCase("true"))
                            {
                                JSONArray jArray = new JSONArray();
                                jArray = jobject.getJSONArray("category");

                                /*list_job_cat_id.add("-1");
                                list_job_cat_name.add("Select Category");*/

                                for (int i = 0; i < jArray.length(); i++)
                                {
                                    JSONObject Obj = jArray.getJSONObject(i);
                                    // A category variables
                                    catId = Obj.getString("catId");
                                    catName = Obj.getString("catName");

                                    list_job_cat_id.add(catId);
                                    list_job_cat_name.add(catName);
                                }

                                if(str_status.equals("true"))
                                {
                                    //Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();

                                    ArrayAdapter<String> cat_name_adapter = new ArrayAdapter<String>
                                            (PostJobActivity.this,android.R.layout.simple_list_item_1,list_job_cat_name);
                                    auto_category.setAdapter(cat_name_adapter);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();
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

    private void getSubCategories(String catId)
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();
            params.put("catId", catId);

            ServiceHandler serviceHandler = new ServiceHandler(PostJobActivity.this);
            final ArrayList<JobFeeds> feedsArrayList = new ArrayList<JobFeeds>();

            list_job_sub_cat_name = new ArrayList<>();
            list_job_sub_cat_id = new ArrayList<>();

            serviceHandler.registerUser(params, strJobSubCategoryList, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status,str_msg;
                    String catId, catName,description;

                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            if(str_status.equalsIgnoreCase("true"))
                            {
                                JSONArray jArray = new JSONArray();
                                jArray = jobject.getJSONArray("subCategory");

                                for (int i = 0; i < jArray.length(); i++)
                                {
                                    JSONObject Obj = jArray.getJSONObject(i);
                                    // A category variables
                                    catId = Obj.getString("catId");
                                    catName = Obj.getString("catName");

                                    list_job_sub_cat_id.add(catId);
                                    list_job_sub_cat_name.add(catName);
                                }

                                if(str_status.equals("true"))
                                {
                                   // Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();


                                    ArrayAdapter<String> cat_name_adapter = new ArrayAdapter<String>
                                            (PostJobActivity.this,android.R.layout.simple_list_item_1,list_job_sub_cat_name);
                                    auto_sub_category.setAdapter(cat_name_adapter);

                                    if(is_update)
                                    {
                                        auto_sub_category.setText(list_job_sub_cat_name.get(list_job_sub_cat_id.indexOf(jobSubCatId)));
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();
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

    private void getDropdownLists()
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();

            ServiceHandler serviceHandler = new ServiceHandler(PostJobActivity.this);
            final ArrayList<JobFeeds> feedsArrayList = new ArrayList<JobFeeds>();

            list_durstion_name= new ArrayList<>();
            list_durstion_id= new ArrayList<>();
            list_work_types_name= new ArrayList<>();
            list_work_types_id= new ArrayList<>();
            list_work_type_rate_name= new ArrayList<>();
            list_work_type_rate_id= new ArrayList<>();
            serviceHandler.registerUser(params, strJobDropdownLists, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status,str_msg;
                    String durId, duration,workTypeId,workType,workTypeRateId,workRate,workDesc;

                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            if(str_status.equalsIgnoreCase("true"))
                            {
                                JSONArray durationArray = new JSONArray();
                                durationArray = jobject.getJSONArray("duration");

                                JSONArray workTypesArray = new JSONArray();
                                workTypesArray = jobject.getJSONArray("workTypes");

                                JSONArray workTypeRateArray = new JSONArray();
                                workTypeRateArray = jobject.getJSONArray("workTypeRate");

                                if(durationArray.length()>0)
                                {
                                    for (int i = 0; i < durationArray.length(); i++)
                                    {
                                        JSONObject Obj = durationArray.getJSONObject(i);
                                        // A category variables
                                        durId = Obj.getString("durId");
                                        duration = Obj.getString("duration");

                                        list_durstion_name.add(duration);
                                        list_durstion_id.add(durId);
                                    }

                                    for (int i = 0; i < workTypesArray.length(); i++)
                                    {
                                        JSONObject Obj = workTypesArray.getJSONObject(i);
                                        // A category variables
                                        workTypeId = Obj.getString("workTypeId");
                                        workType = Obj.getString("workType");

                                        list_work_types_id.add(workTypeId);
                                        list_work_types_name.add(workType);
                                    }

                                    for (int i = 0; i < workTypeRateArray.length(); i++)
                                    {
                                        JSONObject Obj = workTypeRateArray.getJSONObject(i);
                                        // A category variables
                                        workTypeRateId = Obj.getString("workTypeRateId");
                                        workTypeId = Obj.getString("workTypeId");
                                        workRate = Obj.getString("workRate");
                                        workDesc = Obj.getString("workDesc");

                                        list_work_type_rate_id.add(workTypeRateId);
                                        list_work_type_rate_name.add(workDesc);
                                    }
                                }

                                if(str_status.equals("true"))
                                {
                                   // Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();

                                    ArrayAdapter<String> cat_name_adapter = new ArrayAdapter<String>
                                            (PostJobActivity.this,android.R.layout.simple_list_item_1,list_durstion_name);
                                    auto_duration.setAdapter(cat_name_adapter);


                                    ArrayAdapter<String> work_type_adapter = new ArrayAdapter<String>
                                            (PostJobActivity.this,android.R.layout.simple_list_item_1,list_work_types_name);
                                    auto_work_type.setAdapter(work_type_adapter);


                                    ArrayAdapter<String> work_type_rate_adapter = new ArrayAdapter<String>
                                            (PostJobActivity.this,android.R.layout.simple_list_item_1,list_work_type_rate_name);
                                    auto_work_type_rate.setAdapter(work_type_rate_adapter);

                                    if(is_update)
                                    {
                                        getJobDetails(jobId);
                                    }




                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();
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



    private void getJobDetails(String jobId)
    {

        if(utility.checkInternet())
        {

            // userGcmRegID = Shared_Preferences_Class.readString(LoginActivity.this, Shared_Preferences_Class.GCM_REG_ID, "");


            final Map<String, String> params = new HashMap<String, String>();
            params.put("jobId", jobId);

            ServiceHandler serviceHandler = new ServiceHandler(PostJobActivity.this);

            serviceHandler.registerUser(params, strGetJobDetails, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status = "",str_msg;
                    String   jobId, job_title,job_description,visibility,work_rate_description,
                            hours_per_week,job_budget,job_skills,start_type,schedule_date,updated_on,
                            created_at,posted_userId,posted_user_firt_name,posted_user_last_name,user_country_name,user_image;
                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");

                            JSONObject jobDetailObj = new JSONObject(str_json);
                            jobDetailObj = jobject.getJSONObject("jobDetail");


                            if(str_status.equalsIgnoreCase("true"))
                            {

                                jobId = jobDetailObj.getString("jobId");
                                job_title = jobDetailObj.getString("job_title");
                                job_description =jobDetailObj.getString("job_description");
                                jobWorkType = jobDetailObj.getString("work_type");
                                jobWorkTypeRate = jobDetailObj.getString("work_rate");
                                jobCatId = jobDetailObj.getString("jobCategory");
                                jobSubCatId = jobDetailObj.getString("jobSubCategory");
                                work_rate_description = jobDetailObj.getString("work_rate_description");
                                hours_per_week = jobDetailObj.getString("hours_per_week");
                                job_budget = jobDetailObj.getString("job_budget");
                                job_skills = jobDetailObj.getString("job_skills");
                                start_type = jobDetailObj.getString("start_type");
                                schedule_date = jobDetailObj.getString("schedule_date");
                                updated_on = jobDetailObj.getString("updated_on");
                                created_at = jobDetailObj.getString("created_at");
                                posted_userId = jobDetailObj.getString("posted_userId");
                                posted_user_firt_name = jobDetailObj.getString("posted_user_firt_name");
                                posted_user_last_name = jobDetailObj.getString("posted_user_last_name");
                                user_country_name = jobDetailObj.getString("user_country_name");
                                user_image = jobDetailObj.getString("user_image");
                                jobDuration = jobDetailObj.getString("duration_id");
                                visibility = jobDetailObj.getString("visibility");


                                edt_job_name.setText(job_title);
                                edt_job_decs.setText(job_description);
                                edt_hourly_rate.setText(hours_per_week);

                                try {
                                    auto_category.setText(list_job_cat_name.get(list_job_cat_id.indexOf(jobCatId)));
                                }
                                catch (ArrayIndexOutOfBoundsException e)
                                {
                                    e.printStackTrace();
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }



                                edt_skills.setText(job_skills);
                                auto_work_type.setText(list_work_types_name.get(list_work_types_id.indexOf(jobWorkType)));

                                if(jobWorkType.equalsIgnoreCase("2"))
                                {
                                    lay_work_arrangment.setVisibility(View.GONE);
                                    edt_budget.setVisibility(View.VISIBLE);
                                    edt_budget.setText(job_budget);
                                }
                                else
                                {
                                    lay_work_arrangment.setVisibility(View.VISIBLE);
                                    edt_budget.setVisibility(View.GONE);
                                    auto_work_type_rate.setText(list_work_type_rate_name.get(list_work_type_rate_id.indexOf(jobWorkTypeRate)));
                                    auto_duration.setText(list_durstion_name.get(list_durstion_id.indexOf(jobDuration)));
                                }



                                if(!visibility.isEmpty())
                                {
                                    if(visibility.equalsIgnoreCase("Public"))
                                    {
                                        rdo_btn_public.setChecked(true);
                                    }
                                    else
                                    {
                                        rdo_btn_private.setChecked(true);
                                    }
                                }
                                if(!start_type.isEmpty())
                                {
                                    if(start_type.equalsIgnoreCase("0"))
                                    {
                                        rdo_btn_immediately.setChecked(true);
                                    }
                                    else
                                    {
                                        rdo_btn_scheduled.setChecked(true);
                                    }
                                }

                                getSubCategories(jobCatId);

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
