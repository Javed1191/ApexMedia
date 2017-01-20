package com.apexmediatechnologies.apexmedia;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

public class AddBankAccountActivity extends AppCompatActivity
{

    private String strAddBankDetails = Application_Constants.Main_URL+"keyword=add_bank_account";
    private String strGetBankDetails = Application_Constants.Main_URL+"keyword=view_bank_account";

   // private String strGetProducts ="http://192.168.1.3/hdfc_bso/android_web/action?xAction=products";
    String  userId;
    private TextView tv_name,tv_country;
    private EditText edt_ac_number,edt_ac_holder_name,edt_bank_name,edt_branch_name,edt_address,edt_city,
            edt_state,edt_pin_code,edt_ifsc_code,edt_swift_code;
    private Button btn_save,btn_cancel;
    private AutoCompleteTextView auto_account_type,auto_country,auto_state;

    private String strGetCountyList= Application_Constants.Main_URL+"keyword=countries";
    private String strGetStateList= Application_Constants.Main_URL+"keyword=states";
    List<String> countryIdList,countryNameList,stateIdList,stateNameList,listAccountType;
    private Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_account);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        //toolbar.setLogo(R.drawable.actionbar_32);

        userId = Shared_Preferences_Class.readString(getApplicationContext(), Shared_Preferences_Class.USER_ID, "");
        TextView tv_action_title = (TextView) toolbar.findViewById(R.id.tv_action_title);
        tv_action_title.setText("Add Bank Account");

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_country = (TextView) findViewById(R.id.tv_country);
        edt_ac_number = (EditText) findViewById(R.id.edt_ac_number);
        edt_ac_holder_name = (EditText) findViewById(R.id.edt_ac_holder_name);
        edt_bank_name = (EditText) findViewById(R.id.edt_bank_name);
        edt_branch_name = (EditText) findViewById(R.id.edt_branch_name);
        edt_city = (EditText) findViewById(R.id.edt_city);
        edt_state = (EditText) findViewById(R.id.edt_state);
        edt_pin_code = (EditText) findViewById(R.id.edt_pin_code);
        edt_ifsc_code = (EditText) findViewById(R.id.edt_ifsc_code);
        edt_swift_code = (EditText) findViewById(R.id.edt_swift_code);


        auto_account_type = (AutoCompleteTextView) findViewById(R.id.auto_account_type);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        listAccountType = new ArrayList<>();

        listAccountType.add("Savings");
        listAccountType.add("Current");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddBankAccountActivity.this,android.R.layout.simple_list_item_1,listAccountType);
        auto_account_type.setAdapter(adapter);
        auto_account_type.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);

        utility = new Utility(getApplicationContext());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
               /* Intent intent = new Intent(JobDetailActivity.this, MainActivity.class);
                intent.putExtra("menu", "dashboard");
                startActivity(intent);*/
            }
        });
        /*toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBankAccountActivity.this, MainActivity.class);
                intent.putExtra("menu", "dashboard");
                startActivity(intent);


            }
        });*/
        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        countryIdList = new ArrayList<String>();
        countryNameList= new ArrayList<String>();

        stateIdList = new ArrayList<String>();
        stateNameList= new ArrayList<String>();


        btn_save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String accNum, accHolderName, bankName, branch, state,
                     city,  pinCode, ifscCode, swiftCode, accountType;

                accNum = edt_ac_number.getText().toString().trim();
                accHolderName = edt_ac_holder_name.getText().toString().trim();
                bankName = edt_bank_name.getText().toString();
                branch = edt_branch_name.getText().toString().trim();
                city = edt_city.getText().toString().trim();
                state = edt_state.getText().toString().trim();
                pinCode = edt_pin_code.getText().toString().trim();
                ifscCode = edt_ifsc_code.getText().toString().trim();
                swiftCode = edt_swift_code.getText().toString().trim();
                accountType = auto_account_type.getText().toString();

                if (accNum.equals("") || accHolderName.equals("") || bankName.equals("") ||
                        city.equals("") || state.equals("") || branch.equals("") || pinCode.equals("")|| ifscCode.equals("")
                        || swiftCode.equals("")|| accountType.equals(""))
                {
                    if (accNum.equals(""))
                    {
                        edt_ac_number.requestFocus();
                        edt_ac_number.setError("Enter Account Number");
                    }
                    else if (accHolderName.equals(""))
                    {
                        edt_ac_holder_name.requestFocus();
                        edt_ac_holder_name.setError("Enter Account Holder Name");
                    }
                   else if (bankName.equals(""))
                    {
                        edt_bank_name.requestFocus();
                        edt_bank_name.setError("Enter Bank Name");

                    } else if (city.equals("")) {
                        edt_city.requestFocus();
                        edt_city.setError("Enter City");

                    }
                    else if (state.equals(""))
                    {
                        edt_state.requestFocus();
                        edt_state.setError("Enter State");

                    }
                    else if (branch.equals("")) {
                        edt_branch_name.requestFocus();
                        edt_branch_name.setError("Enter Branch Name");

                    }
                    else if (pinCode.equals("")) {
                        edt_pin_code.requestFocus();
                        edt_pin_code.setError("Enter Pin Code");

                    }
                    else if (ifscCode.equals("")) {
                        edt_ifsc_code.requestFocus();
                        edt_ifsc_code.setError("Enter IFSC Code");

                    }
                    else if (swiftCode.equals("")) {
                        edt_swift_code.requestFocus();
                        edt_swift_code.setError("Enter Swift Code");

                    }
                    else if (accountType.equals("")) {
                        auto_account_type.requestFocus();
                        auto_account_type.setError("Select Account Type");

                    }

                }
                else
                {

                    if (utility.checkInternet())
                    {
                        updateBankAccount(userId,accNum,accHolderName,bankName,branch,state,city,pinCode,ifscCode,swiftCode,accountType);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Plaese connect to the intenet", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                    finish();
            }
        });

        auto_account_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto_account_type.showDropDown();
            }
        });

        getBankDetails(userId);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

    }


    private void getCountry()
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(AddBankAccountActivity.this);

            serviceHandler.registerUser(params, strGetCountyList, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status,str_msg;
                    String   countryId, country_name;
                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            if(str_status.equalsIgnoreCase("true"))
                            {
                                JSONArray jArray = new JSONArray();
                                jArray = jobject.getJSONArray("countries");

						/*JSONObject jsonObject = new JSONObject();
						jsonObject = jobject.getJSONObject("VENUEDATA");*/


                                for (int i = 0; i < jArray.length(); i++)
                                {
                                    JSONObject Obj = jArray.getJSONObject(i);

                                    countryId = Obj.getString("countryId");
                                    country_name = Obj.getString("country_name");

                                    countryIdList.add(countryId);
                                    countryNameList.add(country_name);

                                }


                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddBankAccountActivity.this,android.R.layout.simple_list_item_1,countryNameList);
                                auto_country.setAdapter(adapter);

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

    private void getState()
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(AddBankAccountActivity.this);

            serviceHandler.registerUser(params, strGetStateList, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status,str_msg;
                    String   stateId, state_name;
                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            if(str_status.equalsIgnoreCase("true"))
                            {
                                JSONArray jArray = new JSONArray();
                                jArray = jobject.getJSONArray("states");

						/*JSONObject jsonObject = new JSONObject();
						jsonObject = jobject.getJSONObject("VENUEDATA");*/


                                for (int i = 0; i < jArray.length(); i++)
                                {
                                    JSONObject Obj = jArray.getJSONObject(i);

                                    stateId = Obj.getString("stateId");
                                    state_name = Obj.getString("state_name");

                                    stateIdList.add(stateId);
                                    stateNameList.add(state_name);

                                }


                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddBankAccountActivity.this,android.R.layout.simple_list_item_1,countryNameList);
                                auto_state.setAdapter(adapter);

                                auto_state.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);

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
            Toast.makeText(AddBankAccountActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateBankAccount(String userId,String accNum,String accHolderName,String bankName,String branch,String state,
                                   String city, String pinCode,String ifscCode,String swiftCode,String accountType)
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();
            params.put("userId", userId);
            params.put("accNum", accNum);
            params.put("accHolderName", accHolderName);
            params.put("bankName", bankName);
            params.put("branch", branch);
            params.put("state", state);
            params.put("city", city);
            params.put("pinCode", pinCode);
            params.put("ifscCode", ifscCode);
            params.put("swiftCode", swiftCode);
            params.put("accountType", accountType);


            ServiceHandler serviceHandler = new ServiceHandler(AddBankAccountActivity.this);

            serviceHandler.registerUser(params, strAddBankDetails, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("strAddBankDetails Json responce" + result);

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

                                String str_type = Shared_Preferences_Class.readString(AddBankAccountActivity.this,Shared_Preferences_Class.USER_TYPE,"");

                                if(str_type.equalsIgnoreCase("Employer"))
                                {
                                    Intent intent = new Intent(AddBankAccountActivity.this,EmployerMainActivity.class);
                                    intent.putExtra("menu","BankAccount");
                                    startActivity(intent);
                                }
                                else {
                                    Intent intent = new Intent(AddBankAccountActivity.this,MainActivity.class);
                                    intent.putExtra("menu","BankAccount");
                                    startActivity(intent);
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
        else
        {
            Toast.makeText(AddBankAccountActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

    }


    private void getBankDetails(String userId)
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();
            params.put("userId", userId);


            ServiceHandler serviceHandler = new ServiceHandler(AddBankAccountActivity.this);

            serviceHandler.registerUser(params, strGetBankDetails, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status,str_msg;
                    String  accNum="",	accHolderName="",	bankName="",branch="",state="",city="",	pinCode="",ifscCode="",swiftCode="",
                            accType="";

                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            if(str_status.equalsIgnoreCase("true"))
                            {


                                JSONObject jsonObject = new JSONObject();
                                jsonObject = jobject.getJSONObject("account");

                                // A category variables
                                accNum = jsonObject.getString("accNum");
                                accHolderName = jsonObject.getString("accHolderName");
                                bankName = jsonObject.getString("bankName");
                                branch = jsonObject.getString("branch");
                                state = jsonObject.getString("state");
                                city = jsonObject.getString("city");
                                pinCode = jsonObject.getString("pinCode");
                                ifscCode = jsonObject.getString("ifscCode");
                                swiftCode= jsonObject.getString("swiftCode");
                                accType= jsonObject.getString("accType");


                                edt_ac_number.setText(accNum);
                                edt_ac_holder_name.setText(accHolderName);
                                edt_bank_name.setText(bankName);
                                edt_branch_name.setText(branch);
                                edt_state.setText(state);
                                edt_city.setText(city);
                                edt_pin_code.setText(pinCode);
                                edt_ifsc_code.setText(ifscCode);
                                edt_swift_code.setText(swiftCode);
                                auto_account_type.setText(accType);


                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), str_msg, Toast.LENGTH_SHORT).show();
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
