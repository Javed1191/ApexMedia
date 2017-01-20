package com.apexmediatechnologies.apexmedia;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

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

public class EditProfileActivity extends AppCompatActivity
{

    private String strEditProfile = Application_Constants.Main_URL+"keyword=edit_profile";
    private String strGetUserData = Application_Constants.Main_URL+"keyword=view_profile";

   // private String strGetProducts ="http://192.168.1.3/hdfc_bso/android_web/action?xAction=products";
    private ImageView img_user_image;
    private boolean is_file;
    private Button btn_view_milestone;
    String  UserEmail, UserFirstName,UserLastName,phone,UserProfileImage,address,state,city,country,userId;
    private TextView tv_name,tv_country;
    private EditText edt_first_name,edt_last_name,edt_display_name,edt_pay_pal_id,edt_address,edt_city,
            edt_state,edt_country,edt_zip_code,edt_phone,edt_hourly_rate,edt_overview,edt_service_desc,edt_pay_terms,
            edt_keywords;
    private Button btn_save,btn_cancel;
    private RadioGroup rdb_grp_work_type;
    private RadioButton rbtn_work_type,rbtn_individual,rbtn_company;
    private int PICK_FILE_REQUEST = 1;
    private static final int IMAGE_CROP = 201;
    private String str_image_path;
    private File profilePic;
    private ProgressDialog dialogProfile;
    byte[]  byteArray;
    String encodedImage;
    private TextView tv_change_pass;
    private AutoCompleteTextView auto_country,auto_state;

    private String strGetCountyList= Application_Constants.Main_URL+"keyword=countries";
    private String strGetStateList= Application_Constants.Main_URL+"keyword=states";
    List<String> countryIdList,countryNameList,stateIdList,stateNameList;
    private Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        //toolbar.setLogo(R.drawable.actionbar_32);

        userId = Shared_Preferences_Class.readString(getApplicationContext(), Shared_Preferences_Class.USER_ID, "");
        TextView tv_action_title = (TextView) toolbar.findViewById(R.id.tv_action_title);
        tv_action_title.setText("Edit Profile");

        img_user_image = (ImageView) findViewById(R.id.img_user_image);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_country = (TextView) findViewById(R.id.tv_country);
        edt_first_name = (EditText) findViewById(R.id.edt_first_name);
        edt_last_name = (EditText) findViewById(R.id.edt_last_name);
        edt_display_name = (EditText) findViewById(R.id.edt_display_name);
        edt_pay_pal_id = (EditText) findViewById(R.id.edt_pay_pal_id);
        edt_address = (EditText) findViewById(R.id.edt_address);
        edt_city = (EditText) findViewById(R.id.edt_city);
        edt_state = (EditText) findViewById(R.id.edt_state);
        edt_country = (EditText) findViewById(R.id.edt_country);
        edt_zip_code = (EditText) findViewById(R.id.edt_zip_code);
        rbtn_individual = (RadioButton) findViewById(R.id.rbtn_individual);
        rbtn_company = (RadioButton) findViewById(R.id.rbtn_company);

        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_hourly_rate = (EditText) findViewById(R.id.edt_hourly_rate);
        edt_overview = (EditText) findViewById(R.id.edt_overview);
        edt_service_desc = (EditText) findViewById(R.id.edt_service_desc);
        edt_pay_terms = (EditText) findViewById(R.id.edt_pay_terms);
        edt_keywords = (EditText) findViewById(R.id.edt_keywords);

        auto_country = (AutoCompleteTextView) findViewById(R.id.auto_country);
        auto_state = (AutoCompleteTextView) findViewById(R.id.auto_state);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        tv_change_pass = (TextView) findViewById(R.id.tv_change_pass);

        rdb_grp_work_type = (RadioGroup) findViewById(R.id.rdb_grp_work_type);

        utility = new Utility(getApplicationContext());

        getUserData(userId);
/*

        try
        {
            UserFirstName = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_FIRST_NAME,"");
            UserLastName = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_LAST_NAME,"");
            UserEmail = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_EMAIL,"");
            phone = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_PHONE,"");
            UserProfileImage = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_IMAGE,"");
            address = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_ADDRESS,"");
            state = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_STATE,"");
            city = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_CITY,"");
            country = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_COUNTRY,"");

            edt_first_name.setText(UserFirstName);
            edt_last_name.setText(UserLastName);
            edt_phone.setText(phone);
            edt_address.setText(address);
           // edt_state.setText(state);
            edt_city.setText(city);
           // edt_country.setText(country);
            auto_country.setText(country);
            auto_state.setText(state);
            tv_name.setText(UserFirstName+" " + UserLastName);
            tv_country.setText(address);

            if(!UserProfileImage.equals(""))
            {
                //Download image using picasso library
                Picasso.with(getApplicationContext()).load(UserProfileImage)
                        .into(img_user_image);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
*/



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
               /* Intent intent = new Intent(JobDetailActivity.this, MainActivity.class);
                intent.putExtra("menu", "dashboard");
                startActivity(intent);*/
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                intent.putExtra("menu", "dashboard");
                startActivity(intent);


            }
        });


        utility = new Utility(getApplicationContext());
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

        getCountry();
        getState();




        img_user_image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"),PICK_FILE_REQUEST );

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String first_name, last_name, display_name, papal_id, address, city, state, country, zip_code, phone,
                user_type, hourly_rate, overview, service_desc, payment_terms, keywords;

                first_name = edt_first_name.getText().toString().trim();
                last_name = edt_last_name.getText().toString().trim();
                display_name = edt_display_name.getText().toString();
                papal_id = edt_pay_pal_id.getText().toString().trim();
                address = edt_address.getText().toString().trim();
                city = edt_city.getText().toString().trim();
                state = auto_state.getText().toString().trim();
                //country = edt_country.getText().toString().trim();
                country = auto_country.getText().toString().trim();
                zip_code = edt_zip_code.getText().toString().trim();
                phone = edt_phone.getText().toString().trim();
                hourly_rate = edt_hourly_rate.getText().toString();
                overview = edt_overview.getText().toString().trim();
                service_desc = edt_service_desc.getText().toString().trim();
                payment_terms = edt_pay_terms.getText().toString().trim();
                keywords = edt_keywords.getText().toString().trim();

                int selectedId=rdb_grp_work_type.getCheckedRadioButtonId();
                rbtn_work_type=(RadioButton)findViewById(selectedId);

                if(selectedId == R.id.rbtn_company)
                {
                    // company
                    user_type = "company";
                }
                else
                {
                    // individual
                    user_type = "individual";
                }


                if (first_name.equals("") || display_name.equals("") || address.equals("") ||
                        city.equals("") || state.equals("") || country.equals("") || zip_code.equals("")
                        )
                {

                    if (first_name.equals(""))
                    {
                        edt_first_name.requestFocus();
                        edt_first_name.setError("Enter First Name");

                    }
                    else if (display_name.equals("")) {
                        edt_display_name.requestFocus();
                        edt_display_name.setError("Enter Display Name");


                    }
                   else if (address.equals("")) {
                        edt_address.requestFocus();
                        edt_address.setError("Enter Address");

                    } else if (city.equals("")) {
                        edt_city.requestFocus();
                        edt_city.setError("Enter City");

                    }
                    else if (state.equals(""))
                    {
                        auto_state.requestFocus();
                        auto_state.setError("Select State");

                    }
                    else if (country.equals("")) {
                        auto_country.requestFocus();
                        auto_country.setError("Select Country");

                    }
                    else if (zip_code.equals("")) {
                        edt_zip_code.requestFocus();
                        edt_zip_code.setError("Enter Zip Code");

                    }

                }
                else
                {

                    if (utility.checkInternet())
                    {
                        new UpdateProfile(userId,first_name,last_name,display_name,papal_id,address,
                                city,state,country,zip_code,phone,user_type,hourly_rate,overview,service_desc,payment_terms,keywords).execute();

                    } else {
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

        tv_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this,ChangePassswordActivity.class);
                startActivity(intent);
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

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            try {

                Uri uri = data.getData();
                str_image_path = FetchPath.getPath(this, uri);

                String str_file_name = uri.getLastPathSegment();
                is_file = true;

              //  Uri myUri = Uri.parse(str_image_path);
                CropImage.activity(uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                Uri resultUri = result.getUri();
                try
                {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    img_user_image.setImageBitmap(bitmap);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                    byteArray= stream.toByteArray();
                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    BitmapDrawable drawable = (BitmapDrawable) img_user_image.getDrawable();
                    Bitmap bitmap1 = drawable.getBitmap();

                    if (drawable == null)
                    {
                        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.settings);
                    }
                    File filesDir =getFilesDir();
                    profilePic = new File(filesDir, "user_image.jpg");

                    OutputStream os;
                    try {
                        os = new FileOutputStream(profilePic);
                        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, os);
                        os.flush();
                        os.close();
                    } catch (Exception e) {
                        Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                    }

                } catch (IOException e)
                {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }


    class UpdateProfile extends AsyncTask<String, String, String>
    {
        String contractorId,first_name, last_name, display_name, papal_id, address, city, state, country, zip_code, phone,
                user_type, hourly_rate, overview, service_desc, payment_terms, keywords;
        String str_status,json_responce,str_msg="",UserProfileImage="";

        public UpdateProfile(String contractorId, String first_name, String last_name, String display_name, String papal_id,
                             String address, String city, String state, String country, String zip_code, String phone,
                             String user_type, String hourly_rate, String overview, String service_desc, String payment_terms,
                             String keywords)
        {
            this.contractorId = contractorId;
            this.first_name = first_name;
            this.last_name = last_name;
            this.display_name = display_name;
            this.papal_id = papal_id;
            this.address = address;
            this.city = city;
            this.state = state;
            this.country = country;
            this.zip_code = zip_code;
            this.phone = phone;
            this.user_type = user_type;
            this.hourly_rate = hourly_rate;
            this.overview = overview;
            this.service_desc = service_desc;
            this.payment_terms = payment_terms;
            this.keywords = keywords;
        }

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialogProfile = new ProgressDialog(EditProfileActivity.this,R.style.MyTheme);
            dialogProfile.setCancelable(false);
            dialogProfile.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialogProfile.show();

        }

        @Override
        protected String doInBackground(String... params)
        {
            // TODO Auto-generated method stub

            String charset = "UTF-8";

            // File uploadFile1 = new File(str_image_path);
            try {
                MultipartUtility multipart = new MultipartUtility(strEditProfile, charset);
                multipart.addHeaderField("User-Agent", "CodeJava");
                multipart.addHeaderField("Test-Header", "Header-Value");
                multipart.addFormField("description", "Cool Pictures");
                multipart.addFormField("keywords", "Java,upload,Spring");
                multipart.addFormField("contractorId", contractorId);
                multipart.addFormField("first_name", first_name);
                multipart.addFormField("last_name", last_name);
                multipart.addFormField("display_name", display_name);
                multipart.addFormField("papal_id", papal_id);
                multipart.addFormField("address", address);
                multipart.addFormField("city", city);
                multipart.addFormField("state", state);
                multipart.addFormField("country", country);
                multipart.addFormField("zip_code", zip_code);
                multipart.addFormField("phone", phone);
                multipart.addFormField("user_type", user_type);
                multipart.addFormField("hourly_rate", hourly_rate);
                multipart.addFormField("overview", overview);
                multipart.addFormField("service_desc", service_desc);
                multipart.addFormField("payment_terms", payment_terms);
                multipart.addFormField("keywords", keywords);

              /*  contractorId=”106”, first_name=amit, last_name=Srivastava,
                        display_name=amit123, papal_id=amit@gmail.com, address=sector 17, city=gurgaon,
                        state=haryana, country=India, zip_code=122001, phone=8886669990, user_type=individual,
                        hourly_rate=10, overview=overview, service_desc=service,
                        payment_terms=paymentterms, keywords=”php, mysql, javascript, abc”, profilePic=abc.jpg.
                */


                if(is_file)
                {
                    multipart.addFilePart("profilePic", profilePic);
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
                    UserProfileImage = jobject.getString("UserProfileImage");

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
            dialogProfile.dismiss();

            super.onPostExecute(result);
            try
            {
                if(str_status.equalsIgnoreCase("true"))
                {
                    Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();

                    Shared_Preferences_Class.writeString(EditProfileActivity.this, Shared_Preferences_Class.USER_FIRST_NAME, first_name);
                    Shared_Preferences_Class.writeString(EditProfileActivity.this, Shared_Preferences_Class.USER_LAST_NAME, last_name);
                    Shared_Preferences_Class.writeString(EditProfileActivity.this, Shared_Preferences_Class.USER_PHONE, phone);
                    Shared_Preferences_Class.writeString(EditProfileActivity.this, Shared_Preferences_Class.USER_ADDRESS, address);
                    Shared_Preferences_Class.writeString(EditProfileActivity.this, Shared_Preferences_Class.USER_CITY, city);
                    Shared_Preferences_Class.writeString(EditProfileActivity.this, Shared_Preferences_Class.USER_STATE, state);
                    Shared_Preferences_Class.writeString(EditProfileActivity.this, Shared_Preferences_Class.USER_COUNTRY, country);

                    if(!UserProfileImage.equals(""))
                    {
                        Shared_Preferences_Class.writeString(EditProfileActivity.this, Shared_Preferences_Class.USER_IMAGE, UserProfileImage);
                    }


                    String str_type = Shared_Preferences_Class.readString(EditProfileActivity.this,Shared_Preferences_Class.USER_TYPE,"");

                    if(str_type.equalsIgnoreCase("Employer"))
                    {
                        Intent intent = new Intent(EditProfileActivity.this,EmployerMainActivity.class);
                        intent.putExtra("menu","profile");
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(EditProfileActivity.this,MainActivity.class);
                        intent.putExtra("menu","profile");
                        startActivity(intent);

                    }




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


    private void getCountry()
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(EditProfileActivity.this);

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


                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfileActivity.this,android.R.layout.simple_list_item_1,countryNameList);
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


            ServiceHandler serviceHandler = new ServiceHandler(EditProfileActivity.this);

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


                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfileActivity.this,android.R.layout.simple_list_item_1,countryNameList);
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
            Toast.makeText(EditProfileActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void getUserData(String contractorId)
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();
            params.put("contractorId", contractorId);


            ServiceHandler serviceHandler = new ServiceHandler(EditProfileActivity.this);

            serviceHandler.registerUser(params, strGetUserData, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status,str_msg;
                    String  UserId ,UserType, UserName, UserEmail, UserFirstName, UserLastName, company,
                            phone,UserProfileImage,taxForm,address,state,city,country,display_name,paypal_id,zipcode,
                            hourly_rates,overview,service_description,payment_terms,keywords;
                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            if(str_status.equalsIgnoreCase("true"))
                            {


                                UserId = jobject.getString("UserId");
                                UserFirstName = jobject.getString("first_name");
                                UserLastName = jobject.getString("last_name");
                                display_name = jobject.getString("display_name");
                                paypal_id = jobject.getString("paypal_id");
                                phone = jobject.getString("phone");
                                UserProfileImage = jobject.getString("UserProfileImage");
                                address= jobject.getString("address");
                                state = jobject.getString("state");
                                city = jobject.getString("city");
                                country = jobject.getString("country");
                                zipcode = jobject.getString("zipcode");
                                UserType = jobject.getString("type");
                                hourly_rates = jobject.getString("hourly_rates");
                                overview = jobject.getString("overview");
                                service_description = jobject.getString("service_description");
                                payment_terms  = jobject.getString("payment_terms");
                                keywords = jobject.getString("keywords");


                                edt_first_name.setText(UserFirstName);
                                edt_last_name.setText(UserLastName);
                                edt_phone.setText(phone);
                                edt_address.setText(address);
                                // edt_state.setText(state);
                                edt_city.setText(city);
                                // edt_country.setText(country);
                                auto_country.setText(country);
                                auto_state.setText(state);
                                tv_name.setText(UserFirstName+" " + UserLastName);
                                tv_country.setText(address);
                                edt_display_name.setText(display_name);
                                edt_pay_pal_id.setText(paypal_id);
                                edt_zip_code.setText(zipcode);

                                if(UserType.equalsIgnoreCase("individual"))
                                {
                                    rbtn_individual.setChecked(true);
                                }
                                else
                                {
                                    rbtn_company.setChecked(true);
                                }

                                edt_hourly_rate.setText(hourly_rates);
                                edt_overview.setText(overview);
                                edt_service_desc.setText(service_description);
                                edt_pay_terms.setText(payment_terms);
                                edt_keywords.setText(keywords);

                                if(!UserProfileImage.equals(""))
                                {
                                    //Download image using picasso library
                                    Picasso.with(getApplicationContext()).load(UserProfileImage)
                                            .into(img_user_image);
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
            Toast.makeText(EditProfileActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

    }

}
