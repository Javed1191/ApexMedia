package com.apexmediatechnologies.apexmedia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import services.Application_Constants;
import services.FetchPath;
import services.MultipartUtility;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;

public class JobProposalActivity extends AppCompatActivity
{

    private String strSendProposal = Application_Constants.Main_URL+"keyword=send_proposal";
    private String strGetCountyList= Application_Constants.Main_URL+"keyword=countries";
    private String strGetStateList= Application_Constants.Main_URL+"keyword=states";
   // private String strGetProducts ="http://192.168.1.3/hdfc_bso/android_web/action?xAction=products";
    private Utility utility;
    private TextView tv_action_title;
    private LinearLayout lay_products;
    private TextView tv_date;
    private ProgressDialog dialogTax;


    private int PICK_FILE_REQUEST1 = 1,PICK_FILE_REQUEST2 = 2,PICK_FILE_REQUEST3 = 3;
    String str_image_path;
    byte[]  byteArray;
    String encodedImage;
    File attachment1,attachment2,attachment3;
    ImageView img_upload1,img_upload2,img_upload3;
    private EditText edt_exp_qual_dscrip,edt_approach,edt_cost_pr_hour,edt_hours_pr_week,edt_duration;
    private CheckBox chk_sub_amt_alt;
    private Button btn_choose_file,btn_submit;
    private  TextView tv_file1,tv_file2,tv_file3;

    // parametres
    String userId,submit_amt_later="0",jobId;
    boolean is_dob = false,is_file1=false,is_file2=false,is_file3=false;
    private LinearLayout lay_my_earnings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_proposal);

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

       // tv_date = (TextView) findViewById(R.id.tv_date);
        img_upload1 = (ImageView) findViewById(R.id.img_upload1);
        img_upload2 = (ImageView) findViewById(R.id.img_upload2);
        img_upload3 = (ImageView) findViewById(R.id.img_upload3);
        tv_file1 = (TextView) findViewById(R.id.tv_file1);
        tv_file2 = (TextView) findViewById(R.id.tv_file2);
        tv_file3 = (TextView) findViewById(R.id.tv_file3);
        edt_exp_qual_dscrip = (EditText) findViewById(R.id.edt_exp_qual_dscrip);
        edt_approach = (EditText)findViewById(R.id.edt_approach);
        edt_cost_pr_hour= (EditText)findViewById(R.id.edt_cost_pr_hour);
        edt_hours_pr_week= (EditText)findViewById(R.id.edt_hours_pr_week);
        edt_duration= (EditText)findViewById(R.id.edt_duration);
        chk_sub_amt_alt= (CheckBox)findViewById(R.id.chk_sub_amt_alt);
        btn_submit= (Button)findViewById(R.id.btn_submit);
        lay_my_earnings = (LinearLayout) findViewById(R.id.lay_my_earnings);

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


        chk_sub_amt_alt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    lay_my_earnings.setVisibility(View.GONE);
                }
                else
                {
                    lay_my_earnings.setVisibility(View.VISIBLE);
                }

            }
        });

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
        img_upload2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select file"),PICK_FILE_REQUEST2 );

            }
        });
        img_upload3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select file"),PICK_FILE_REQUEST3 );

            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

              String  exp_qual_dscrip = edt_exp_qual_dscrip.getText().toString().trim();
                String approach = edt_approach.getText().toString().trim();
                String costPrHours = edt_cost_pr_hour.getText().toString();
                String hrsPrWeek = edt_hours_pr_week.getText().toString();
                String duration = edt_duration.getText().toString().trim();

                if (exp_qual_dscrip.equals("") || approach.equals("") || costPrHours.equals("") || hrsPrWeek.equals("") || duration.equals("")) {

                    if (exp_qual_dscrip.equals(""))
                    {
                        //Toast.makeText(getApplicationContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
                        // edt_first_name.setError("Enter First Name");
                        edt_exp_qual_dscrip.requestFocus();
                        edt_exp_qual_dscrip.setError("Enter Description");

                    }
                    else if (approach.equals("")) {
                        //  Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
                        // edt_last_name.setError("Enter Last Name");
                        edt_approach.requestFocus();

                        edt_approach.setError("Enter Approach");


                    }
                    else if (lay_my_earnings.getVisibility()==View.VISIBLE)
                    {
                        //  Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
                        // edt_last_name.setError("Enter Last Name");
                        if(costPrHours.equals(""))
                        {
                            edt_cost_pr_hour.requestFocus();

                            edt_cost_pr_hour.setError("Enter Cost Per Houurs");
                        }


                    }
                    else if (hrsPrWeek.equals(""))
                    {
                        //Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                        // edt_password.setError("Enter Password");
                        edt_hours_pr_week.requestFocus();
                        edt_hours_pr_week.setError("Enter Houer Per Week");

                    }
                    else if (duration.equals(""))
                    {
                        //Toast.makeText(getApplicationContext(), "Enter Email Address", Toast.LENGTH_SHORT).show();
                        // edt_email.setError("Enter Email Address");
                        edt_duration.requestFocus();
                        edt_duration.setError("Enter Duration");

                    }


                    //Toast.makeText(getApplicationContext(), "Enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    if (utility.checkInternet())
                    {
                       new JobProposal(userId,jobId,exp_qual_dscrip,approach,costPrHours,hrsPrWeek,duration,submit_amt_later).execute();

                    } else {
                        Toast.makeText(getApplicationContext(), "Plaese connect to the intenet", Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });

        chk_sub_amt_alt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    submit_amt_later="1";
                }
                else
                {
                    submit_amt_later="0";
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
        if (requestCode == PICK_FILE_REQUEST2 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            //  str_image_path = getPath(uri);

            try {
                Uri uri = data.getData();
                str_image_path = FetchPath.getPath(this, uri);

                String str_file_name = uri.getLastPathSegment();
                tv_file2.setText(str_file_name);
                is_file2 = true;


                // code to get image from image view and convert it into file

                File filesDir =getFilesDir();
                attachment2 = new File(str_image_path);
                long file_size =  attachment2.length();
                long file_size_kb = file_size/1024;

                System.out.print(file_size_kb);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if (requestCode == PICK_FILE_REQUEST3 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            //  str_image_path = getPath(uri);

            try {
                Uri uri = data.getData();
                str_image_path = FetchPath.getPath(this, uri);

                String str_file_name = uri.getLastPathSegment();
                tv_file3.setText(str_file_name);
                is_file3 = true;


                // code to get image from image view and convert it into file

                File filesDir =getFilesDir();
                attachment3 = new File(str_image_path);
                long file_size =  attachment3.length();
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
        String userId="",jobId="",userEmail="",exp_qual_dscrip="",approach="",costPrHours="",hrsPrWeek="",duration="",submit_amt_later;
        String str_status,json_responce,str_msg="";

        public JobProposal(String userId, String jobId, String exp_qual_dscrip,String approach,String costPrHours,
                           String hrsPrWeek,
        String duration, String submit_amt_later)
        {
            this.userId = userId;
            this.jobId = jobId;
            this.exp_qual_dscrip = exp_qual_dscrip;
            this.approach = approach;
            this.costPrHours = costPrHours;
            this.duration=duration;
            this.hrsPrWeek = hrsPrWeek;
            this.submit_amt_later = submit_amt_later;
        }

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialogTax = new ProgressDialog(JobProposalActivity.this,R.style.MyTheme);
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
                MultipartUtility multipart = new MultipartUtility(strSendProposal, charset);
                multipart.addHeaderField("User-Agent", "CodeJava");
                multipart.addHeaderField("Test-Header", "Header-Value");
                multipart.addFormField("userId", userId);
                multipart.addFormField("jobId", jobId);
                multipart.addFormField("exp_qual_dscrip", exp_qual_dscrip);
                multipart.addFormField("approach", approach);
                multipart.addFormField("costPrHours", costPrHours);
                multipart.addFormField("hrsPrWeek", hrsPrWeek);
                multipart.addFormField("estimated_duration", duration);
                multipart.addFormField("submit_amt_later", submit_amt_later);

                if(is_file1)
                {
                    multipart.addFilePart("attachment1", attachment1);
                }
                if(is_file2)
                {
                    multipart.addFilePart("attachment2", attachment2);
                }
                if(is_file3)
                {
                    multipart.addFilePart("attachment2", attachment3);
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

                   // onBackPressed();

                Intent i = new Intent(JobProposalActivity.this,MainActivity.class);
                i.putExtra("menu", "MyProposals");
                startActivity(i);

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



}
