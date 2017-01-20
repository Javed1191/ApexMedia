package com.apexmediatechnologies.apexmedia;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class ChangePassswordActivity extends AppCompatActivity
{

    private EditText edt_old_password,edt_new_password,edt_re_type_password;

    private String strChangeUrl = Application_Constants.Main_URL+"keyword=change_password";
    private TextView tv_old_password_error,tv_new_password_error,tv_re_password_error,tv_action_title;
    private Button btn_confirm;
    private  String siteUserID;

    private Utility utility;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Password");

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePassswordActivity.this, MainActivity.class);
                intent.putExtra("menu", "dashboard");
                startActivity(intent);


            }
        });

        btn_confirm = (Button) findViewById(R.id.btn_confirm);

        edt_old_password = (EditText) findViewById(R.id.edt_old__password);
        edt_new_password= (EditText) findViewById(R.id.edt_new_password);
        edt_re_type_password= (EditText) findViewById(R.id.edt_re_type_password);

        tv_old_password_error = (TextView)findViewById(R.id.tv_old_password_error);
        tv_new_password_error = (TextView)findViewById(R.id.tv_new_password_error);
        tv_re_password_error = (TextView)findViewById(R.id.tv_re_password_error);


        utility = new Utility(getApplicationContext());


        siteUserID = Shared_Preferences_Class.readString(getApplicationContext(), Shared_Preferences_Class.USER_ID, "");


        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  str_old_pass, str_new_pass, str_re_pass;
                str_old_pass = edt_old_password.getText().toString().trim();
                str_new_pass = edt_new_password.getText().toString().trim();
                str_re_pass = edt_re_type_password.getText().toString().trim();
                ;


                if (str_old_pass.equals("") || str_new_pass.equals("") || str_re_pass.equals("")) {
                    // edt_user_email.setError("Enter email id");


                    if (str_old_pass.equals(""))
                    {
                        edt_old_password.requestFocus();
                        tv_old_password_error.setVisibility(View.VISIBLE);
                        tv_old_password_error.setText("Enter old password");
                    }
                    else if (str_new_pass.equals(""))
                    {
                        edt_new_password.requestFocus();
                        tv_new_password_error.setVisibility(View.VISIBLE);
                        tv_new_password_error.setText("Enter New  password");
                    }
                    else if (str_re_pass.equals(""))
                    {
                        edt_re_type_password.requestFocus();

                        tv_re_password_error.setVisibility(View.VISIBLE);
                        tv_re_password_error.setText("Re Type New Password");
                    }
                }
                else if (!str_new_pass.equals(str_re_pass))
                {

                    edt_re_type_password.requestFocus();
                    tv_re_password_error.setVisibility(View.VISIBLE);
                    tv_re_password_error.setText("Password Do Not Match");
                }
                else
                {

                    tv_old_password_error.setVisibility(View.GONE);
                    tv_new_password_error.setVisibility(View.GONE);
                    tv_re_password_error.setVisibility(View.GONE);

                    if (utility.checkInternet())
                    {
                        changePassword(siteUserID, str_old_pass, str_new_pass,str_re_pass);
                    } else {
                        Toast.makeText(ChangePassswordActivity.this, "Please connect to intenet", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });

        edt_old_password.addTextChangedListener(new TextWatcher() {

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
                    edt_old_password.requestFocus();
                    tv_old_password_error.setVisibility(View.VISIBLE);
                    tv_old_password_error.setText("Enter Old Password");
                } else {
                    tv_old_password_error.setVisibility(View.GONE);
                }
            }
        });

        edt_new_password.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count)
            {
                if (s.length() < 0)
                {
                    // edt_first_name.setError("Enter first name");
                    edt_new_password.requestFocus();
                    tv_new_password_error.setVisibility(View.VISIBLE);
                    tv_new_password_error.setText("Enter New Password");
                } else {
                    tv_new_password_error.setVisibility(View.GONE);
                }
            }
        });
        edt_re_type_password.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count)
            {
                if (s.length() < 0)
                {
                    // edt_first_name.setError("Enter first name");
                    edt_re_type_password.requestFocus();
                    tv_re_password_error.setVisibility(View.VISIBLE);
                    tv_re_password_error.setText("Re-Type Password");
                } else {
                    tv_re_password_error.setVisibility(View.GONE);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main, menu );
        return true;
    }



    private void changePassword(String user_id, String old_password, String new_password, String confirm_password)
    {

        if(utility.checkInternet())
        {
            final Map<String, String> params = new HashMap<String, String>();
            params.put("user_id", user_id);
            params.put("old_password", old_password);
            params.put("new_password", new_password);
            params.put("confirm_password", confirm_password);


            ServiceHandler serviceHandler = new ServiceHandler(ChangePassswordActivity.this);

            serviceHandler.registerUser(params, strChangeUrl, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status,str_msg;
                    String siteUserID,userFirstName, userLastName,userEmail,mobileNumber,userProfileImage;
                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            if(str_status.equalsIgnoreCase("true"))
                            {
                                  /*  edt_new_password.setText("");
                                edt_old_password.setText("");
                                edt_re_type_password.setText("");*/
                                Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ChangePassswordActivity.this,MainActivity.class);
                                intent.putExtra("menu","profile");
                                startActivity(intent);


                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();
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
