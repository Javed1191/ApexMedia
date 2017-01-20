package com.apexmediatechnologies.apexmedia;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;

public class SignupWithActivity extends AppCompatActivity
{
    private String str_user_id;
    private EditText edt_email,edt_password;
    private TextView tv_forgot_pass;
    private String loginUrl = Application_Constants.Main_URL+"keyword=User_Login";

    private Utility utility;
    private TextView tv_email_error,tv_password_error;
    private Button btn_employer,btn_contractor;
    private LinearLayout lay_forgot_pass,lay_create_account;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_signupwith);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        str_user_id = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_ID,"");


        btn_contractor = (Button) findViewById(R.id.btn_contractor);
        btn_employer = (Button) findViewById(R.id.btn_employer);

        //edt_login_email.requestFocus();
        // to hide key board
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // set color to status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }


        btn_contractor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent loginIntent = new Intent(SignupWithActivity.this,RegistrationActivity.class);
                loginIntent.putExtra("userType","Individual");
                startActivity(loginIntent);
            }
        });
        btn_employer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent loginIntent = new Intent(SignupWithActivity.this,RegistrationActivity.class);
                loginIntent.putExtra("userType","Employer");
                startActivity(loginIntent);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);




       // ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }


    /*class ForgotPassword extends AsyncTask<String, String, String>
    {
        String str_json;
        String str_user_email = "", str_password = "", name = "", email = "", usr_id = "", str_status = "", str_msg = "", str_msg_count = "";
        String url;
        String siteUserID, userFirstName, userLastName, userEmail, mobileNum,loyaltyPoints,userProfileImage;

        //Array list of countries
        // ArrayList<UserData> countryList = new ArrayList<UserData>();

        // SharedPreferences  sp = getActivity().getSharedPreferences("MyPref", getActivity().MODE_PRIVATE);
        //String str_user_id = sp.getString(Shared_Preferences_Class.USER_ID, ""); // user id to pass with webservice
        //String str_user_name = edt_name.getText().toString();
        boolean home_zip = false;
        String strUserAppId = "", strUserFirstName = "", strUserLastName = "", strMobileNum = "", strUserEmail = "", strPass = "", isVerified;
        int intRegisterBy;

        public ForgotPassword( String strUserEmail)
        {
	    	   *//*this.strUserAppId = strUserAppId;
	    	   this.strUserFirstName = strUserFirstName;
	    	   this.strUserLastName = strUserLastName;
	    	   this.strMobileNum = strMobileNum;*//*
            this.strUserEmail = strUserEmail;
            //this.intRegisterBy = intRegisterBy;

        }


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pd = new ProgressDialog(LoginActivity.this);
            pd.setCancelable(false);
            pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub


            ServiceHandler sh = new ServiceHandler();
            //String str = Shared_Preferences_Class.USER_ID;
            list_param.add(new BasicNameValuePair("userEmail", strUserEmail));


            try {
                str_json = sh.makeServiceCall(str_forgort_pass_url, ServiceHandler.POST, list_param);
                JSONObject jobject = new JSONObject(str_json);
               // JSONObject str_status_obj =jobject.getJSONObject("STATUS");

               // str_status = str_status_obj.getString("status");
                //isVerified = jobject.getString("isVerified");
                str_msg = jobject.getString("MSG");


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
            //System.out.println(str_json);
            pd.dismiss();
            super.onPostExecute(result);

            Toast.makeText(LoginActivity.this, str_msg, Toast.LENGTH_SHORT).show();




        }

    }
*/




}

