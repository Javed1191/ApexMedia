package com.apexmediatechnologies.apexmedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

public class OtherInfoActivity extends AppCompatActivity
{
    private Button btn_next;
    private EditText edt_hobbies,edt_sports,edt_skills,edt_business;
    AutoCompleteTextView auto_country;
    private String  str_hobbies,str_sports, str_skills,str_business;
    private Utility utility;
    private String registration_url = Application_Constants.Main_URL+"xAction=userRegistration";
    String  str_first_name, str_last_name,str_email, str_password, str_country;
    private TextView tv_hobbies_error,tv_sports_error,tv_skills_error,tv_business_error;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_info);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btn_next = (Button) findViewById(R.id.btn_next);
        edt_hobbies = (EditText) findViewById(R.id.edt_hobbies);
        edt_sports = (EditText) findViewById(R.id.edt_sports);
        edt_skills = (EditText) findViewById(R.id.edt_skills);
        edt_business = (EditText) findViewById(R.id.edt_business);

        tv_hobbies_error = (TextView) findViewById(R.id.tv_hobbies_error);
        tv_sports_error= (TextView) findViewById(R.id.tv_sports_error);
        tv_skills_error= (TextView) findViewById(R.id.tv_skills_error);
        tv_business_error= (TextView) findViewById(R.id.tv_business_error);



        utility = new Utility(OtherInfoActivity.this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent intent = getIntent();

        if(intent.getExtras()!=null)
        {

            str_first_name = intent.getStringExtra("firstName");
            str_last_name = intent.getStringExtra("lastName");
            str_email = intent.getStringExtra("email");
            str_password = intent.getStringExtra("password");
            str_country = intent.getStringExtra("country");

        }


        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                str_hobbies = edt_hobbies.getText().toString().trim();
                str_sports = edt_sports.getText().toString().trim();
                str_skills = edt_skills.getText().toString().trim();
                str_business = edt_business.getText().toString().trim();

                if ( str_skills.equals("") )
                {

                   if (str_skills.equals(""))
                   {
                        //Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                        // edt_password.setError("Enter Password");
                        edt_skills.requestFocus();

                        tv_skills_error.setVisibility(View.VISIBLE);
                        tv_skills_error.setText("Enter Skills");
                    }




                    //Toast.makeText(getApplicationContext(), "Enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //UserRegistration userRegistration = new UserRegistration(str_first_name, str_last_name, str_email, str_mob, str_password, gcmToken, str_user_nick_name, registerBy = "0", userAppID);
                    //userRegistration.execute();

                    tv_hobbies_error.setVisibility(View.GONE);
                    tv_sports_error.setVisibility(View.GONE);
                    tv_skills_error.setVisibility(View.GONE);
                    tv_business_error.setVisibility(View.GONE);

                    if (utility.checkInternet())
                    {
                      //  registerUser(str_first_name, str_last_name, str_email, str_country, str_password, gcmToken, str_user_nick_name, registerBy, userAppID);
                    } else {
                        Toast.makeText(OtherInfoActivity.this, "Plaese connect to the intenet", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


       /* btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                str_hobbies = edt_hobbies.getText().toString().trim();
                str_sports = edt_sports.getText().toString().trim();
                str_skills = edt_skills.getText().toString().trim();
                str_business = edt_business.getText().toString().trim();

                if (str_hobbies.equals("") || str_sports.equals("") || str_skills.equals("") || str_business.equals(""))
                {

                    if (str_hobbies.equals("")) {
                        //Toast.makeText(getApplicationContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
                        // edt_first_name.setError("Enter First Name");
                        edt_hobbies.requestFocus();

                        tv_hobbies_error.setVisibility(View.VISIBLE);
                        tv_hobbies_error.setText("Enter Hobbies");

                    } else if (str_sports.equals("")) {
                        //  Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
                        // edt_last_name.setError("Enter Last Name");
                        edt_sports.requestFocus();

                        tv_sports_error.setVisibility(View.VISIBLE);
                        tv_sports_error.setText("Enter Sports");


                    } else if (str_skills.equals("")) {
                        //Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                        // edt_password.setError("Enter Password");
                        edt_skills.requestFocus();

                        tv_skills_error.setVisibility(View.VISIBLE);
                        tv_skills_error.setText("Enter Skills");
                    } else if (str_business.equals("")) {
                        //Toast.makeText(getApplicationContext(), "Enter Email Address", Toast.LENGTH_SHORT).show();
                        // edt_email.setError("Enter Email Address");
                        edt_business.requestFocus();

                        tv_business_error.setVisibility(View.VISIBLE);
                        tv_business_error.setText("Enter Email Address");
                    }


                    //Toast.makeText(getApplicationContext(), "Enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //UserRegistration userRegistration = new UserRegistration(str_first_name, str_last_name, str_email, str_mob, str_password, gcmToken, str_user_nick_name, registerBy = "0", userAppID);
                    //userRegistration.execute();

                    tv_hobbies_error.setVisibility(View.GONE);
                    tv_sports_error.setVisibility(View.GONE);
                    tv_skills_error.setVisibility(View.GONE);
                    tv_business_error.setVisibility(View.GONE);

                    if (utility.checkInternet()) {
                        registerUser(str_first_name, str_last_name, str_email, str_country, str_password, gcmToken, str_user_nick_name, registerBy, userAppID);
                    } else {
                        Toast.makeText(OtherInfoActivity.this, "Plaese connect to the intenet", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });*/


        edt_hobbies.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() < 0) {
                   // edt_first_name.setError("Enter first name");
                    edt_hobbies.requestFocus();

                    tv_hobbies_error.setVisibility(View.VISIBLE);
                    tv_hobbies_error.setText("Enter Hobbies");
                } else {
                   // edt_first_name.setError(null);

                    tv_hobbies_error.setVisibility(View.GONE);
                }
            }
        });

        edt_sports.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() < 0) {
                    // edt_last_name.setError("Enter last name");
                    edt_sports.requestFocus();

                    tv_sports_error.setVisibility(View.VISIBLE);
                    tv_sports_error.setText("Enter Sports");
                } else {
                    // edt_last_name.setError(null);

                    tv_sports_error.setVisibility(View.GONE);
                }
            }
        });


        edt_skills.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count)
            {
                if(s.length()<0)
                {
                   // edt_password.setError("Enter password");
                    edt_skills.requestFocus();

                    tv_skills_error.setVisibility(View.VISIBLE);
                    tv_skills_error.setText("Enter Skills");
                }
                else
                {
                  //  edt_password.setError(null);

                    tv_skills_error.setVisibility(View.GONE);
                }
            }
        });


        edt_business.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count)
            {
                if(s.length()<0)
                {
                   // edt_email.setError("Enter email");
                    edt_business.requestFocus();

                    tv_business_error.setVisibility(View.VISIBLE);
                    tv_business_error.setText("Enter Business");
                }
                else
                {
                   // edt_email.setError(null);

                    tv_business_error.setVisibility(View.GONE);
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




    private void registerUser(String userFirstName, String userLastName, String userEmail, String mobileNumber,
                              String userPassword,String gcmToken,String userName, String registerBy, String userAppID)
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();
            params.put("userFirstName", userFirstName);
            params.put("userLastName", userLastName);
            params.put("userEmail", userEmail);
            params.put("mobileNumber", mobileNumber);
            params.put("userPassword", userPassword);
            params.put("userName", userName);
            params.put("gcmToken", gcmToken);
            params.put("registerBy", registerBy);
            params.put("userAppID", userAppID);

            ServiceHandler serviceHandler = new ServiceHandler(OtherInfoActivity.this);

            serviceHandler.registerUser(params, registration_url, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status,str_msg;
                    String siteUserID,userFirstName, userLastName,userEmail,mobileNumber;
                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("status");
                            str_msg = jobject.getString("msg");
                            if(str_status.equalsIgnoreCase("success"))
                            {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject = jobject.getJSONObject("userInfo");

                                siteUserID = jsonObject.getString("siteUserID");
                                userFirstName = jsonObject.getString("userFirstName");
                                userLastName =jsonObject.getString("userLastName");
                                userEmail = jsonObject.getString("userEmail");
                                mobileNumber = jsonObject.getString("mobileNumber");

                                Shared_Preferences_Class.writeString(OtherInfoActivity.this, Shared_Preferences_Class.USER_ID, siteUserID);
                                Shared_Preferences_Class.writeString(OtherInfoActivity.this, Shared_Preferences_Class.USER_FIRST_NAME, userFirstName);
                                Shared_Preferences_Class.writeString(OtherInfoActivity.this, Shared_Preferences_Class.USER_LAST_NAME, userLastName);
                                Shared_Preferences_Class.writeString(OtherInfoActivity.this, Shared_Preferences_Class.USER_EMAIL, userEmail);
                                Shared_Preferences_Class.writeString(OtherInfoActivity.this, Shared_Preferences_Class.USER_MOBILE, mobileNumber);

                                Intent i = new Intent(OtherInfoActivity.this,MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.putExtra("registration","registration");
                                startActivity(i);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

    }

}
