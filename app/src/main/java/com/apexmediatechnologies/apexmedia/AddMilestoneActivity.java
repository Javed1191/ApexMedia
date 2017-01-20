package com.apexmediatechnologies.apexmedia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import services.Application_Constants;
import services.FetchPath;
import services.MultipartUtility;
import services.Shared_Preferences_Class;
import services.Utility;

public class AddMilestoneActivity extends AppCompatActivity
{

    private String strAddMilestone = Application_Constants.Main_URL+"keyword=add_milestone";
    private Utility utility;
    private TextView tv_action_title;
    private ProgressDialog dialogMilestone;

    private int PICK_FILE_REQUEST = 1;
    String str_image_path;
    byte[]  byteArray;
    String encodedImage;
    File milestoneAttachment;
    ImageView img_upload_image;
    private EditText edt_amount,edt_desc;
    private Button btn_save;
    private  TextView tv_upload_file,tv_date;

    // parametres
    String userId,jobId;
    boolean is_dob = false,is_file1=false;

    private Calendar cal;
    private int day;
    private int month;
    private int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_milestone);

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

        tv_action_title = (TextView) toolbar.findViewById(R.id.tv_action_title);
        tv_action_title.setText("Add Milestone");

       // tv_date = (TextView) findViewById(R.id.tv_date);
        img_upload_image = (ImageView) findViewById(R.id.img_upload_image);
        tv_upload_file = (TextView) findViewById(R.id.tv_upload_file);
        tv_date = (TextView) findViewById(R.id.tv_date);
        edt_amount = (EditText) findViewById(R.id.edt_amount);
        edt_desc = (EditText)findViewById(R.id.edt_desc);
        btn_save= (Button)findViewById(R.id.btn_save);

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
        img_upload_image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select file"),PICK_FILE_REQUEST );

            }
        });

        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        tv_date.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                // TODO Auto-generated method stub
                is_dob = false;

                InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                showDialog(0);
            }

        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_amount = edt_amount.getText().toString().trim();
                String str_description = edt_desc.getText().toString().trim();
                String str_date = tv_date.getText().toString();

                if (str_amount.equals("") || str_description.equals("") || str_date.equals("")) {

                    if (str_amount.equals("")) {
                        //Toast.makeText(getApplicationContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
                        // edt_first_name.setError("Enter First Name");
                        edt_amount.requestFocus();
                        edt_amount.setError("Enter Amount");

                    } else if (str_description.equals("")) {
                        //  Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
                        // edt_last_name.setError("Enter Last Name");
                        edt_desc.requestFocus();
                        edt_desc.setError("Enter Description");


                    } else if (str_date.equals("")) {
                        //  Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
                        // edt_last_name.setError("Enter Last Name");
                        tv_date.requestFocus();

                        tv_date.setError("Enter Select Date");

                    }
                    //Toast.makeText(getApplicationContext(), "Enter all the fields", Toast.LENGTH_SHORT).show();
                } else {

                    if (utility.checkInternet()) {
                        new AddMileStone("103", userId, jobId, str_amount, str_description, str_date).execute();

                    } else {
                        Toast.makeText(getApplicationContext(), "Plaese connect to the intenet", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });



    }


    public void onClick(View v)
    {
        showDialog(0);
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id)
    {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }


    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay)
        {

                tv_date.setText(selectedYear + " - " + (selectedMonth + 1) + " - "
                        + selectedDay);

        }
    };



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);


       /* if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img_prescription.setImageBitmap(photo);
        }*/

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
          //  str_image_path = getPath(uri);

            try {

                Uri uri = data.getData();
               // str_image_path = uri.getPath();
                str_image_path = FetchPath.getPath(this, uri);

                String str_file_name = uri.getLastPathSegment();
                tv_upload_file.setText(str_file_name);
                is_file1 = true;

                    // code to get image from image view and convert it into file

                    File filesDir =getFilesDir();
                milestoneAttachment  = new File(str_image_path);
               long file_size =  milestoneAttachment .length();
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


    class AddMileStone extends AsyncTask<String, String, String>
    {
        String employerId="",jobId="",contractorId="",exp_qual_dscrip="",milestoneAmt="",description="",hrsPrWeek="",
                mileStoneCompletionDate="";
        String str_status,json_responce,str_msg="";

        public AddMileStone(String employerId, String contractorId, String jobId,String milestoneAmt,String description,
                            String mileStoneCompletionDate)
        {
            this.employerId = employerId;
            this.contractorId = contractorId;
            this.jobId=jobId;
            this.milestoneAmt = milestoneAmt;
            this.description = description;
            this.mileStoneCompletionDate = mileStoneCompletionDate;
        }

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialogMilestone = new ProgressDialog(AddMilestoneActivity.this,R.style.MyTheme);
            dialogMilestone.setCancelable(false);
            dialogMilestone.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialogMilestone.show();

        }

        @Override
        protected String doInBackground(String... params)
        {
            // TODO Auto-generated method stub

            String charset = "UTF-8";

            // File uploadFile1 = new File(str_image_path);
            try {
                MultipartUtility multipart = new MultipartUtility(strAddMilestone, charset);
                multipart.addHeaderField("User-Agent", "CodeJava");
                multipart.addHeaderField("Test-Header", "Header-Value");
                multipart.addFormField("description", "Cool Pictures");
                multipart.addFormField("keywords", "Java,upload,Spring");
                multipart.addFormField("employerId", employerId);
                multipart.addFormField("contractorId", contractorId);
                multipart.addFormField("jobId", jobId);
                multipart.addFormField("milestoneAmt", milestoneAmt);
                multipart.addFormField("description", description);
                multipart.addFormField("mileStoneCompletionDate", mileStoneCompletionDate);
                if(is_file1)
                {
                    multipart.addFilePart("milestoneAttachment", milestoneAttachment );
                }
                json_responce= multipart.finish();
                System.out.println("Responce: " + json_responce);


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
            dialogMilestone.dismiss();

            super.onPostExecute(result);
            try
            {
                if(json_responce!=null) {

                    if (str_status.equalsIgnoreCase("true")) {
                        Toast.makeText(getApplicationContext(), str_msg, Toast.LENGTH_SHORT).show();

                        if (str_status.equalsIgnoreCase("true"))
                        {
                            Intent intent = new Intent(AddMilestoneActivity.this, ViewMilstonesActivity.class);
                            intent.putExtra("jobId", jobId);
                            startActivity(intent);
                        }

                    } else if (str_status.equalsIgnoreCase("false")) {

                        Toast.makeText(getApplicationContext(), str_msg, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), str_msg, Toast.LENGTH_SHORT).show();
                    }
                }


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }

    }



}
