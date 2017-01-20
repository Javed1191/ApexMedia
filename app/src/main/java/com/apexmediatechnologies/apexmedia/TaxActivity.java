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
import android.media.AudioManager;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import services.Application_Constants;
import services.MultipartUtility;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;

public class TaxActivity extends AppCompatActivity
{

    private String strTaxForm = Application_Constants.Main_URL+"keyword=tax_form";
    private String strGetCountyList= Application_Constants.Main_URL+"keyword=countries";
    private String strGetStateList= Application_Constants.Main_URL+"keyword=states";
   // private String strGetProducts ="http://192.168.1.3/hdfc_bso/android_web/action?xAction=products";
    private Utility utility;
    private TextView tv_action_title;
    private LinearLayout lay_products;
    private ProgressDialog dialogTax;

    private Calendar cal;
    private int day;
    private int month;
    private int year;

    private int PICK_IMAGE_REQUEST = 1;
    String str_image_path;
    byte[]  byteArray;
    String encodedImage;
    File imageFile;
    ImageView img_sinature;
    private EditText edt_name,edt_address,edt_postal_code,edt_email,edt_postal_code1,
            edt_us_tax_no,edt_fti_no,edt_ref_no,edt_name_signer,edt_capacity_acting,edt_date,edt_dob,
            edt_residence,edt_article,edt_claim,edt_withholding,edt_tready;
    private AutoCompleteTextView auto_country_citizen,auto_country,auto_country1,auto_city,auto_state,auto_city1,auto_state1;
    private Button btn_choose_file,btn_submit,btn_skip;

    // parametres
    String userId, str_name,str_country_citizen,str_address,str_city,str_state,str_country,str_email,str_city1,
    str_state1,str_pin_code,str_pin_code1,str_country1,str_us_tax_no,str_fti_no,str_ref_no,str_dob,
    str_date,str_signer_name,str_capacity, str_residence,str_article,str_claim,str_withholding,str_tready;
    boolean is_dob = false,is_image=false;

    String[] languages={"Android ","java","IOS","SQL","JDBC","Web services"};
    List<String> countryIdList,countryNameList,stateIdList,stateNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax);

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
        tv_action_title.setText("Tax Form");
       // toolbar.setLogo(R.drawable.actionbar_32);

        edt_date = (EditText) findViewById(R.id.edt_date);
        img_sinature = (ImageView) findViewById(R.id.img_sinature);
        edt_name = (EditText) findViewById(R.id.edt_name);
        auto_country_citizen = (AutoCompleteTextView) findViewById(R.id.auto_country_citizen);
        edt_address = (EditText)findViewById(R.id.edt_address);
        auto_city = (AutoCompleteTextView) findViewById(R.id.auto_city);
        auto_state= (AutoCompleteTextView)findViewById(R.id.auto_state);
        edt_postal_code= (EditText)findViewById(R.id.edt_postal_code);
        auto_country= (AutoCompleteTextView) findViewById(R.id.auto_country);
        edt_email= (EditText)findViewById(R.id.edt_email);
        auto_city1= (AutoCompleteTextView)findViewById(R.id.auto_city1);
        auto_state1= (AutoCompleteTextView)findViewById(R.id.auto_state1);
        edt_postal_code1= (EditText)findViewById(R.id.edt_postal_code1);
        auto_country1= (AutoCompleteTextView) findViewById(R.id.auto_country1);
        edt_us_tax_no= (EditText)findViewById(R.id.edt_us_tax_no);
        edt_fti_no= (EditText)findViewById(R.id.edt_fti_no);
        edt_ref_no= (EditText)findViewById(R.id.edt_ref_no);
        edt_dob = (EditText) findViewById(R.id.edt_dob);

        edt_residence = (EditText) findViewById(R.id.edt_residence);
        edt_article = (EditText) findViewById(R.id.edt_article);
        edt_claim = (EditText) findViewById(R.id.edt_claim);
        edt_withholding = (EditText) findViewById(R.id.edt_withholding);
        edt_tready = (EditText) findViewById(R.id.edt_tready);

        btn_choose_file = (Button) findViewById(R.id.btn_choose_file);
        edt_name_signer = (EditText)findViewById(R.id.edt_name_signer);
        edt_capacity_acting= (EditText)findViewById(R.id.edt_capacity_acting);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_skip= (Button) findViewById(R.id.btn_skip);

        countryIdList = new ArrayList<String>();
        countryNameList= new ArrayList<String>();
        stateIdList = new ArrayList<String>();
        stateNameList= new ArrayList<String>();


        userId = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_ID,"");

        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();

                Intent intent = new Intent(TaxActivity.this,MainActivity.class);
                intent.putExtra("menu","dashboard");
                startActivity(intent);
            }
        });

        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
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

        // get country list
        getCountry();
        // get state list
        getState();




        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        edt_date.setOnClickListener(new View.OnClickListener()
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
        edt_dob.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                // TODO Auto-generated method stub
                is_dob = true;

                InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                showDialog(0);
            }

        });

        btn_choose_file.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent i = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, PICK_IMAGE_REQUEST);

            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                str_name = edt_name.getText().toString().trim();
                str_country_citizen = auto_country_citizen.getText().toString();
                str_address = edt_address.getText().toString().trim();
                str_city = auto_city.getText().toString().trim();
                str_state = auto_state.getText().toString();
                str_country = auto_country.getText().toString();
                str_pin_code = edt_postal_code.getText().toString();
                str_pin_code1 = edt_postal_code1.getText().toString();
                str_email = edt_email.getText().toString().trim();
                str_city1 = auto_city1.getText().toString();
                str_state1 = auto_state1.getText().toString();
                str_country1 = auto_country1.getText().toString();
                str_us_tax_no = edt_us_tax_no.getText().toString().trim();
                str_fti_no = edt_fti_no.getText().toString().trim();
                str_ref_no =  edt_ref_no.getText().toString().trim();
                str_dob = edt_dob.getText().toString();
                str_date = edt_date.getText().toString();
                str_signer_name = edt_name_signer.getText().toString().trim();
                str_capacity = edt_capacity_acting.getText().toString().trim();

                str_residence = edt_residence.getText().toString().trim();
                str_article = edt_article.getText().toString().trim();
                str_claim = edt_claim.getText().toString().trim();
                str_withholding = edt_withholding.getText().toString().trim();
                str_tready = edt_tready.getText().toString().trim();

                if(str_name.equals("")||str_country_citizen.equals("")||str_address.equals("")||str_city.equals("")||str_state.equals("")||str_country.equals("")||
                        str_pin_code.equals("")||str_pin_code1.equals("")||
                        str_us_tax_no.equals("")||str_fti_no.equals("")||str_ref_no.equals("")||str_dob.equals("")||str_date.equals("")||str_signer_name.equals("")||
                        str_capacity.equals("")||str_residence.equals("")||str_article.equals("")||str_claim.equals("")||str_withholding.equals("")||str_tready.equals(""))
                {
                    if(str_name.equals(""))
                    {
                        edt_name.setError("Enter Name");
                    }
                    else if(str_country_citizen.equals(""))
                    {
                        auto_country_citizen.setError("Select  Country");
                    }
                    else if(str_address.equals(""))
                    {
                        edt_address.setError("Enter Address");
                    }
                    else if(str_city.equals(""))
                    {
                        auto_city.setError("Select City");
                    }
                    else if(str_state.equals(""))
                    {
                        auto_state.setError("Select State");
                    }
                    else if(str_country.equals(""))
                    {
                        auto_country.setError("Select Country");
                    }
                    else if(str_pin_code.equals(""))
                    {
                        edt_postal_code.setError("Enter Pin Code");
                    }
                    else if(str_pin_code1.equals(""))
                    {
                        edt_postal_code1.setError("Enter Pin Code");
                    }
                   /* else if(str_email.equals(""))
                    {
                        edt_email.setError("Enter Email Id");
                    }
                    else if(str_city1.equals(""))
                    {
                        auto_city1.setError("Select City");
                    }
                    else if(str_state1.equals(""))
                    {
                        auto_state1.setError("Select State");
                    }
                    else if(str_country1.equals(""))
                    {
                        auto_country1.setError("Select Country"); // skip
                    }*/
                    else if(str_fti_no.equals(""))
                    {
                        edt_fti_no.setError("Enter Fti No");
                    }
                    else if(str_us_tax_no.equals(""))
                    {
                        edt_us_tax_no.setError("Enter Tax No");
                    }
                    else if(str_ref_no.equals(""))
                    {
                        edt_ref_no.setError("Enter Reference No");
                    }
                    else if(str_dob.equals(""))
                    {
                        edt_dob.setError("Select DOB");
                    }
                    else if(str_date.equals(""))
                    {
                        edt_date.setError("Select Date");
                    }
                    else if(str_signer_name.equals(""))
                    {
                        edt_name_signer.setError("Enter Signature Name");
                    }
                    else if(str_capacity.equals(""))
                    {
                        edt_capacity_acting.setError("Enter Capacity");
                    }
                    else if(str_residence.equals(""))
                    {
                        edt_residence.setError("Enter Residence");
                    }
                    else if(str_article.equals(""))
                    {
                        edt_article.setError("Enter This Field");
                    }
                    else if(str_claim.equals(""))
                    {
                        edt_claim.setError("Enter This Field");
                    }
                    else if(str_withholding.equals(""))
                    {
                        edt_withholding.setError("Enter This Field");
                    }
                    else if(str_tready.equals(""))
                    {
                        edt_tready.setError("Enter This Field");
                    }

                }
                else
                {
                    new TaxForm().execute();

                }

            }
        });

        btn_skip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Shared_Preferences_Class.writeString(getApplicationContext(),Shared_Preferences_Class.IS_TAX_FORM_FILLED,"no");

                Intent i = new Intent(TaxActivity.this,MainActivity.class);
                i.putExtra("menu", "dashboard");
                startActivity(i);
                finish();
            }
        });

        auto_country.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                auto_country.showDropDown();

            }
        });
        auto_country1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                auto_country1.showDropDown();

            }
        });
        auto_country_citizen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                auto_country_citizen.showDropDown();

            }
        });

        auto_state.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                auto_state.showDropDown();

            }
        });

        auto_state1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                auto_state1.showDropDown();

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

            if(is_dob)
            {
               /* tv_dob.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                        + selectedYear);*/
                edt_dob.setText(selectedYear + " - " + (selectedMonth + 1) + " - "
                        + selectedDay);
            }
            else {
                edt_date.setText(selectedYear + " - " + (selectedMonth + 1) + " - "
                        + selectedDay);
            }




        }
    };

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(TaxActivity.this,MainActivity.class);
        intent.putExtra("menu","dashboard");
        startActivity(intent);
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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {

            Uri uri = data.getData();

            str_image_path = getPath(uri);

            try {
                is_image = true;

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                img_sinature.setVisibility(View.VISIBLE);

                img_sinature.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);


                byteArray= stream.toByteArray();
                // String  imageString= Base64.encode(byteArray);

                encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                // String url_image = str_change_profile_url+userId;
                // new post_Async(url_image).execute();

                // btn_submit_photo.setVisibility(View.VISIBLE);


                    BitmapDrawable drawable = (BitmapDrawable) img_sinature.getDrawable();
                    Bitmap bitmap1 = drawable.getBitmap();


                    if (drawable == null)
                    {
                        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.settings);
                    }
                       /* ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                        byte[] data = bos.toByteArray();
                        String file = Base64.encodeBytes(data);

                        encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);*/

                    // code to get image from image view and convert it into file

                    File filesDir =getFilesDir();
                    imageFile = new File(filesDir, "user_image.jpg");

                    OutputStream os;
                    try {
                        os = new FileOutputStream(imageFile);
                        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, os);
                        os.flush();
                        os.close();
                    } catch (Exception e) {
                        Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                    }





            }
            catch (IOException e)
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


    private void getCountry()
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(TaxActivity.this);

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


                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TaxActivity.this,android.R.layout.simple_list_item_1,countryNameList);
                                auto_country.setAdapter(adapter);
                                auto_country1.setAdapter(adapter);
                                auto_country_citizen.setAdapter(adapter);

                                auto_country.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
                                auto_country1.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
                                auto_country_citizen.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);

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
            Toast.makeText(TaxActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

    }


    private void getState()
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(TaxActivity.this);

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


                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TaxActivity.this,android.R.layout.simple_list_item_1,countryNameList);
                                auto_state.setAdapter(adapter);
                                auto_state1.setAdapter(adapter);

                                auto_state.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
                                auto_state1.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);

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
            Toast.makeText(TaxActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

    }

    class TaxForm extends AsyncTask<String, String, String>
    {
        String str_status,server_jwt_token;
        String userFirstName="",userLastName="",userEmail="",mobileNumber="",countryCodeID="",userProfileImage="",str_msg="",userPassword="",strUserGcmRegID="",siteUserID;
        int intCountryCodeId;
        String url;
        int stateID;

        //Array list of countries
        // ArrayList<UserData> countryList = new ArrayList<UserData>();

        // SharedPreferences  sp = getActivity().getSharedPreferences("MyPref", getActivity().MODE_PRIVATE);
        //String str_user_id = sp.getString(Shared_Preferences_Class.USER_ID, ""); // user id to pass with webservice
        //String str_user_name = edt_name.getText().toString();
        boolean home_zip=false;
        String str_quickly_id,json_responce;
        int intRegisterBy;

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialogTax = new ProgressDialog(TaxActivity.this,R.style.MyTheme);
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
                MultipartUtility multipart = new MultipartUtility(strTaxForm, charset);
                multipart.addHeaderField("User-Agent", "CodeJava");
                multipart.addHeaderField("Test-Header", "Header-Value");
                multipart.addFormField("description", "Cool Pictures");
                multipart.addFormField("keywords", "Java,upload,Spring");
                multipart.addFormField("userId", userId);
                multipart.addFormField("benificialName", str_name);
                multipart.addFormField("CountryOfCitizenship", str_country_citizen);
                multipart.addFormField("PermanentRegAddress", str_address);
                multipart.addFormField("CityOrTown", str_city);
                multipart.addFormField("State", str_state);
                multipart.addFormField("PostalCode", str_pin_code);
                multipart.addFormField("Country", str_country);
                multipart.addFormField("mailingAddress", str_email);
                multipart.addFormField("mailingCity", str_city1);
                multipart.addFormField("mailingState", str_state1);
                multipart.addFormField("mailingPotalCode", str_pin_code1);
                multipart.addFormField("mailingCountry", str_country);
                multipart.addFormField("USTaxpayerIdentificaitonNumber", str_us_tax_no);
                multipart.addFormField("ForeignTaxIdedntificaitonNumber", str_fti_no);
                multipart.addFormField("refrenceNumber", str_ref_no);
                multipart.addFormField("DOB", str_dob);
                multipart.addFormField("residentOf", str_residence);
                multipart.addFormField("article", str_article);
                multipart.addFormField("ratePerc", str_claim);
                multipart.addFormField("income", str_withholding);
                multipart.addFormField("reason", str_tready);
                multipart.addFormField("date", str_date);
                multipart.addFormField("nameOfSigner", str_signer_name);
                multipart.addFormField("capacity", str_capacity);

                if(is_image)
                {
                    multipart.addFilePart("signatureFile", imageFile);
                }

              /*  MultipartUtility multipart = new MultipartUtility(strTaxForm, charset);
                multipart.addHeaderField("User-Agent", "CodeJava");
                multipart.addHeaderField("Test-Header", "Header-Value");
                multipart.addFormField("description", "Cool Pictures");
                multipart.addFormField("keywords", "Java,upload,Spring");
                multipart.addFormField("userId", "12");
                multipart.addFormField("benificialName", "Anil Singh");
                multipart.addFormField("CountryOfCitizenship", "India");
                multipart.addFormField("PermanentRegAddress", "sector 18");
                multipart.addFormField("CityOrTown", "Gurgaon");
                multipart.addFormField("State", "Haryana");
                multipart.addFormField("PostalCode", "122001");
                multipart.addFormField("Country", "India");
                multipart.addFormField("mailingAddress", "sector 17");
                multipart.addFormField("mailingCity", "Gurgaon");
                multipart.addFormField("mailingState", "Haryana");
                multipart.addFormField("mailingPotalCode", "122001");
                multipart.addFormField("mailingCountry", "India");
                multipart.addFormField("USTaxpayerIdentificaitonNumber", "8556asdfa");
                multipart.addFormField("ForeignTaxIdedntificaitonNumber", "852asdfa");
                multipart.addFormField("refrenceNumber", "2558asdfasd");
                multipart.addFormField("DOB", "1988-01-25");
                multipart.addFormField("residentOf", "asdfasd");
                multipart.addFormField("article", "asdfads");
                multipart.addFormField("ratePerc", "asdfads");
                multipart.addFormField("income", "asdfads");
                multipart.addFormField("reason", "dasfasd");
                multipart.addFormField("date", "2016-06-25");
                multipart.addFormField("article", "article");
                multipart.addFormField("nameOfSigner", "anil");
                multipart.addFormField("capacity", "asdfasd");
                multipart.addFilePart("signatureFile", imageFile);*/



               /* userId:
                benificialName:
                CountryOfCitizenship:
                PermanentRegAddress:
                CityOrTown:
                State:
                PostalCode:
                Country:
                mailingAddress:
                mailingCity:
                mailingState:
                mailingPotalCode:
                mailingCountry:
                USTaxpayerIdentificaitonNumber:
                ForeignTaxIdedntificaitonNumber:
                refrenceNumber:
                DOB:
                residentOf:
                article:
                ratePerc:
                income:
                reason:
                signatureFile: This will be the signature image file
                date:
                nameOfSigner:
                capacity: */

                // response = multipart.finish();
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
                    Toast.makeText(getApplicationContext(), "This may be server issue", Toast.LENGTH_SHORT).show();
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

            if(str_status.equals("true"))
            {
                Shared_Preferences_Class.writeString(getApplicationContext(), Shared_Preferences_Class.IS_TAX_FORM_FILLED, "yes");
                Shared_Preferences_Class.writeString(getApplicationContext(), Shared_Preferences_Class.IS_PSYCHOMETRIC, "no");
                Intent i = new Intent(TaxActivity.this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("menu", "dashboard");
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

    }




}
