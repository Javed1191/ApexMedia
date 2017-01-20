package com.apexmediatechnologies.apexmedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;

public class LoginActivity extends AppCompatActivity
{
    private String str_user_id,str_user_type;
    private EditText edt_email,edt_password;
    private TextView tv_forgot_pass;
    private String loginUrl = Application_Constants.Main_URL+"keyword=User_Login";

    private Utility utility;
    private TextView tv_email_error,tv_password_error;
    private Button btn_login;
    private LinearLayout lay_forgot_pass,lay_create_account;
    private ImageView img_login_fb;
    private CallbackManager mCallbackManager;
    private ProgressDialog progressDialog;
    private String fb_name,fb_email,fb_id,fb_image_url,device_token;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login_new);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        str_user_id = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_ID,"");
        str_user_type = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_TYPE,"");
        device_token = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.FCM_ID,"");



        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>()
                {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        Log.d("Success", loginResult.toString());

                        String str_fg_app_id = loginResult.getAccessToken().getApplicationId();

                        getUserDetailsFromFB();


                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);

        tv_email_error = (TextView) findViewById(R.id.tv_email_error);
        tv_password_error= (TextView) findViewById(R.id.tv_password_error);
        btn_login = (Button) findViewById(R.id.btn_login);
        lay_forgot_pass = (LinearLayout) findViewById(R.id.lay_forgot_pass);
        lay_create_account= (LinearLayout) findViewById(R.id.lay_create_account);
        img_login_fb = (ImageView) findViewById(R.id.img_login_fb);

        utility = new Utility(LoginActivity.this);

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

        if(!str_user_id.equals("")&&!str_user_id.equals(null))
        {

            if(str_user_type.equalsIgnoreCase("Individual"))
            {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
           /* i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);*/
                startActivity(i);
                // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                finish();
            }
            else
            {
                Intent i = new Intent(LoginActivity.this,EmployerMainActivity.class);
           /* i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);*/
                startActivity(i);
                // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                finish();
            }


        }

        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String str_user_name,str_pass;
                str_user_name = edt_email.getText().toString().trim();
                str_pass = edt_password.getText().toString().trim();

                if(str_user_name.equals(""))
                {
                   // edt_user_email.setError("Enter email id");

                    edt_email.requestFocus();
                    tv_email_error.setVisibility(View.VISIBLE);
                    tv_email_error.setText("Enter Email Id");

                }
                else if(!utility.checkEmail(str_user_name))
                {
                   // edt_user_email.setError("Enter valid email id");
                    edt_email.requestFocus();
                    tv_email_error.setVisibility(View.VISIBLE);
                    tv_email_error.setText("Enter Valid Email Id");

                }
                else if(str_pass.equals(""))
                {
                   // edt_user_pass.setError("Enter password");
                    edt_password.requestFocus();
                    tv_password_error.setVisibility(View.VISIBLE);
                    tv_password_error.setText("Enter Password");
                }

                else if(!str_user_name.equals("")&&!str_pass.equals(""))
                {
                    tv_password_error.setVisibility(View.GONE);
                    tv_email_error.setVisibility(View.GONE);
                        //new Login(str_pass,str_user_name).execute();

                    if(utility.checkInternet())
                    {
                       loginUser(str_pass,str_user_name,device_token);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Please connect to intenet", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });

        img_login_fb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                // loginWithFacebook();
                if(utility.checkInternet())
                {
                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email","public_profile"));
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Please connect to intenet", Toast.LENGTH_SHORT).show();
                }


            }
        });
        lay_create_account.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent loginIntent = new Intent(LoginActivity.this,SignupWithActivity.class);
                startActivity(loginIntent);
            }
        });

        lay_forgot_pass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

               Intent loginIntent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(loginIntent);

                /*final AlertDialog builder = new AlertDialog.Builder(LoginActivity.this).create();

                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View builder_view = inflater.inflate(R.layout.activity_forgot_pass_dialog, null);
                builder.setView(builder_view);

                TextView title = new TextView(getApplicationContext());
                // You Can Customise your Title here
                title.setText("Forgot your password?");
                title.setBackgroundColor(Color.parseColor("#9d8a6a"));
                title.setPadding(10, 10, 10, 10);
                title.setGravity(Gravity.CENTER);
                title.setTextColor(Color.BLACK);
                title.setTextSize(20);

                builder.setCustomTitle(title);
                builder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;




                final EditText edt_email = (EditText) builder_view.findViewById(R.id.edt_email);
                Button dialogBtnYes= (Button) builder_view.findViewById(R.id.btn_send);


                dialogBtnYes.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                        String str_email = edt_email.getText().toString().trim();
                        if(utility.checkEmail(str_email))
                        {
                            //Toast.makeText(LoginActivity.this, str_email, Toast.LENGTH_SHORT).show();
                            if(utility.checkInternet())
                            {
                                new ForgotPassword(str_email).execute();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                            }

                            builder.cancel();
                        }
                        else
                        {
                            edt_email.setError("Invalid email id");
                        }
                    }
                });

                builder.show();*/


            }
        });
        edt_email.addTextChangedListener(new TextWatcher() {

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
                    edt_email.requestFocus();

                    tv_email_error.setVisibility(View.VISIBLE);
                    tv_email_error.setText("Enter Email Id");
                } else {

                    tv_email_error.setVisibility(View.GONE);
                }
            }
        });


        edt_password.addTextChangedListener(new TextWatcher() {

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
                    edt_password.requestFocus();
                    tv_password_error.setVisibility(View.VISIBLE);
                    tv_password_error.setText("Enter Password");
                } else {
                    tv_password_error.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // Facebook user details
    private void getUserDetailsFromFB()
    {

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();



        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback()
                {


                    @Override
                    public void onCompleted(JSONObject object,
                                            GraphResponse response)
                    {
                        // TODO Auto-generated method stub

                        try
                        {
                            progressDialog.dismiss();

                            if(object!=null)
                            {
                                fb_name   = object.getString("name");
                                fb_email = object.getString("email");
                                fb_id= object.getString("id");

                                fb_image_url = "https://graph.facebook.com/" + fb_id + "/picture?type=large";

                                String str_fb_pic =fb_image_url;

                                System.out.println(fb_name + fb_email + fb_id + fb_image_url );


                                String[] str_fist = fb_name.split("\\s+");

                                String str_first_name = str_fist[0];
                                String str_last_name = str_fist[1];

                                Toast.makeText(LoginActivity.this, "Name: " + fb_name + "Email: " + fb_email+"Fb Id: "+ fb_id, Toast.LENGTH_SHORT).show();

                               /* SocialFBLogin socialFBLogin = new SocialFBLogin(fb_id,fb_email,str_gcm_reg_id,str_first_name,str_last_name);
                                socialFBLogin.execute();*/

                            }

                        }
                        catch ( Throwable t )
                        {
                            t.printStackTrace();
                        }

                        //System.out.println(response);
                        //System.out.println(object);

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void loginUser(String strPass, String strUserEmail, String device_token)
    {

        if(utility.checkInternet())
        {

           // userGcmRegID = Shared_Preferences_Class.readString(LoginActivity.this, Shared_Preferences_Class.GCM_REG_ID, "");

            final Map<String, String> params = new HashMap<String, String>();
            params.put("UserEmailId", strUserEmail);
            params.put("UserPassword", strPass);
            params.put("device_token", device_token);
            params.put("appType", "Android");


            ServiceHandler serviceHandler = new ServiceHandler(LoginActivity.this);

            serviceHandler.registerUser(params, loginUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status = "",str_msg;
                    String  UserId ,UserType, UserName, UserEmail, UserFirstName, UserLastName, company,
                            phone,UserProfileImage,taxForm,address,state,city,country,pschometric_test;
                    try {
                            if(str_json!=null)
                            {
                                JSONObject jobject = new JSONObject(str_json);
                              str_status = jobject.getString("Result");
                                str_msg = jobject.getString("Message");
                                if(str_status.equalsIgnoreCase("true"))
                                {

                                    UserId = jobject.getString("UserId");
                                    UserType = jobject.getString("UserType");
                                    UserName =jobject.getString("UserName");
                                    UserEmail = jobject.getString("UserEmail");
                                    UserFirstName = jobject.getString("UserFirstName");
                                    UserLastName = jobject.getString("UserLastName");
                                    company = jobject.getString("company");
                                    phone = jobject.getString("phone");
                                    UserProfileImage = jobject.getString("UserProfileImage");
                                    taxForm = jobject.getString("taxForm");
                                    address= jobject.getString("address");
                                    state = jobject.getString("state");
                                    city = jobject.getString("city");
                                    country = jobject.getString("country");
                                    pschometric_test = jobject.getString("pschometric_test");

                                    if(taxForm.equals("1"))
                                    {
                                        Shared_Preferences_Class.writeString(LoginActivity.this, Shared_Preferences_Class.IS_TAX_FORM_FILLED, "yes");
                                    }
                                    else
                                    {
                                        Shared_Preferences_Class.writeString(LoginActivity.this, Shared_Preferences_Class.IS_TAX_FORM_FILLED, "no");

                                    }
                                    if(pschometric_test.equals("1"))
                                    {
                                        Shared_Preferences_Class.writeString(LoginActivity.this, Shared_Preferences_Class.IS_PSYCHOMETRIC, "yes");
                                    }
                                    else
                                    {
                                        Shared_Preferences_Class.writeString(LoginActivity.this, Shared_Preferences_Class.IS_PSYCHOMETRIC, "no");

                                    }

                                    Shared_Preferences_Class.writeString(LoginActivity.this, Shared_Preferences_Class.USER_ID, UserId);
                                    Shared_Preferences_Class.writeString(LoginActivity.this, Shared_Preferences_Class.USER_FIRST_NAME, UserFirstName);
                                    Shared_Preferences_Class.writeString(LoginActivity.this, Shared_Preferences_Class.USER_LAST_NAME, UserLastName);
                                    Shared_Preferences_Class.writeString(LoginActivity.this, Shared_Preferences_Class.USER_PHONE, phone);
                                    Shared_Preferences_Class.writeString(LoginActivity.this, Shared_Preferences_Class.USER_EMAIL, UserEmail);
                                    Shared_Preferences_Class.writeString(LoginActivity.this, Shared_Preferences_Class.USER_ADDRESS, address);
                                    Shared_Preferences_Class.writeString(LoginActivity.this, Shared_Preferences_Class.USER_CITY, city);
                                    Shared_Preferences_Class.writeString(LoginActivity.this, Shared_Preferences_Class.USER_STATE, state);
                                    Shared_Preferences_Class.writeString(LoginActivity.this, Shared_Preferences_Class.USER_COUNTRY, country);
                                    Shared_Preferences_Class.writeString(LoginActivity.this, Shared_Preferences_Class.USER_IMAGE, UserProfileImage);
                                    Shared_Preferences_Class.writeString(LoginActivity.this, Shared_Preferences_Class.USER_TYPE, UserType);



                                    if(UserType.equalsIgnoreCase("Employer"))
                                    {
                                        Intent i = new Intent(LoginActivity.this,EmployerMainActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        startActivity(i);
                                        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                                        finish();
                                    }
                                    else
                                    {
                                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        startActivity(i);
                                        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                                        finish();
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

