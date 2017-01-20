package com.apexmediatechnologies.apexmedia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.firebase.iid.FirebaseInstanceId;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import services.Application_Constants;
import services.FetchPath;
import services.MultipartUtility;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;

public class RegistrationActivity extends AppCompatActivity
{
    private Button btn_next,btn_fb_login;
    EditText edt_first_name,edt_last_name,edt_email,edt_password,edt_re_password;
    private String str_first_name,str_last_name,str_country,str_email,str_password,str_re_password;
    private AutoCompleteTextView auto_country;
    private Utility utility;
    private String registration_url = Application_Constants.Main_URL+"keyword=employeer_Registration";
    private String strGetCountyList= Application_Constants.Main_URL+"keyword=countries";
    List<String> countryIdList,countryNameList;


    String str_user_name,str_user_last,str_user_email,str_user_nick_name,str_user_image,userGoogleID="",device_token="",
            userFBID="",registerBy="0",userAppID="",userType;
    private TextView tv_first_name_error,tv_last_name_error,tv_country_error,tv_user_name_error,tv_password_error,tv_re_password_error,
            tv_email_error;
    private ProgressDialog dialogProfile;
    private boolean is_file=false;
    private int PICK_FILE_REQUEST = 1;
    private String str_image_path;
    byte[]  byteArray;
    String encodedImage;
    private File profilePic;
    private RoundedImageView img_user_image;
    private CheckBox chk_policy;
    boolean is_policy=false;

    private CallbackManager mCallbackManager;
    private ProgressDialog progressDialog;
    private String fb_name,fb_email,fb_id,fb_image_url;
    private LinearLayout lay_upload_pic;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_new);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);



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
                        Toast.makeText(RegistrationActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(RegistrationActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                btn_next = (Button) findViewById(R.id.btn_next);
                edt_first_name = (EditText) findViewById(R.id.edt_first_name);
                edt_last_name = (EditText) findViewById(R.id.edt_last_name);
                edt_email = (EditText) findViewById(R.id.edt_email);
                edt_password = (EditText) findViewById(R.id.edt_password);
                edt_re_password = (EditText) findViewById(R.id.edt_re_password);
                auto_country = (AutoCompleteTextView) findViewById(R.id.auto_country);

                tv_first_name_error = (TextView) findViewById(R.id.tv_first_name_error);
                tv_last_name_error = (TextView) findViewById(R.id.tv_last_name_error);
                tv_password_error = (TextView) findViewById(R.id.tv_password_error);
                tv_re_password_error  = (TextView) findViewById(R.id.tv_re_password_error);
                tv_email_error = (TextView) findViewById(R.id.tv_email_error);
                tv_country_error = (TextView) findViewById(R.id.tv_country_error);
                img_user_image = (RoundedImageView) findViewById(R.id.img_user_image);
                chk_policy  = (CheckBox) findViewById(R.id.chk_policy);
                 btn_fb_login = (Button) findViewById(R.id.btn_fb_login);
                lay_upload_pic = (LinearLayout) findViewById(R.id.lay_upload_pic);


        utility = new Utility(RegistrationActivity.this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamMute(AudioManager.STREAM_MUSIC, true);


        VideoView videoView = (VideoView) findViewById(R.id.videoViewRelative);

        DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoView.getLayoutParams();
        params.width =  metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;

	      /*  MediaController mediaController = new MediaController(this);
	        mediaController.setAnchorView(videoView);
	        videoView.setMediaController(mediaController);*/
        videoView.setLayoutParams(params);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.company_profile));
        videoView.start();

        Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {
            userType = intent.getStringExtra("userType");
        }

        countryIdList = new ArrayList<String>();
        countryNameList= new ArrayList<String>();


        //if the device is not already registered
        if (!isRegistered())
        {
            //registering the device
          //  registerDevice();
        }
        else
        {
            //if the device is already registered
            //displaying a toast
            Toast.makeText(RegistrationActivity.this, "Already registered...", Toast.LENGTH_SHORT).show();
        }

        getCountry();


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                str_first_name = edt_first_name.getText().toString().trim();
                str_last_name = edt_last_name.getText().toString().trim();
                str_email = edt_email.getText().toString().trim();
                str_password = edt_password.getText().toString().trim();
                str_country = auto_country.getText().toString().trim();
                str_re_password = edt_re_password.getText().toString().trim();

                if(is_policy)
                {
                    if (str_first_name.equals("") || str_last_name.equals("") || str_country.equals("") || str_email.equals("") || str_password.equals(""))
                    {

                        if (str_first_name.equals("")) {
                            //Toast.makeText(getApplicationContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
                            // edt_first_name.setError("Enter First Name");
                            edt_first_name.requestFocus();

                            tv_first_name_error.setVisibility(View.VISIBLE);
                            tv_first_name_error.setText("Enter First Name");

                        } else if (str_last_name.equals("")) {
                            //  Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
                            // edt_last_name.setError("Enter Last Name");
                            edt_last_name.requestFocus();

                            tv_last_name_error.setVisibility(View.VISIBLE);
                            tv_last_name_error.setText("Enter Last Name");


                        } else if (str_country.equals("")) {
                            //  Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
                            // edt_last_name.setError("Enter Last Name");
                            auto_country.requestFocus();

                            tv_country_error.setVisibility(View.VISIBLE);
                            tv_country_error.setText("Select Country");


                        } else if (str_password.equals("")) {
                            //Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                            // edt_password.setError("Enter Password");
                            edt_password.requestFocus();

                            tv_password_error.setVisibility(View.VISIBLE);
                            tv_password_error.setText("Enter Password");
                        } else if (str_email.equals("")) {
                            //Toast.makeText(getApplicationContext(), "Enter Email Address", Toast.LENGTH_SHORT).show();
                            // edt_email.setError("Enter Email Address");
                            edt_email.requestFocus();

                            tv_email_error.setVisibility(View.VISIBLE);
                            tv_email_error.setText("Enter Email Address");
                        }


                        //Toast.makeText(getApplicationContext(), "Enter all the fields", Toast.LENGTH_SHORT).show();
                    }
                    else if (utility.checkEmail(str_email) == false) {
                        //Toast.makeText(getApplicationContext(), "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                        // edt_email.setError("Enter Valid Email Address");
                        edt_email.requestFocus();

                        tv_email_error.setVisibility(View.VISIBLE);
                        tv_email_error.setText("Enter Valid Email Id");
                    } else if (str_password.length() < 6) {
                        // edt_password.setError("Password atleast 6 characters");
                        edt_password.requestFocus();

                        tv_password_error.setVisibility(View.VISIBLE);
                        tv_password_error.setText("Password Atleast 6 Characters");
                    } else if (!str_password.equals(str_re_password)) {
                        //Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                        // edt_password.setError("Enter Password");
                        edt_re_password.requestFocus();

                        tv_re_password_error.setVisibility(View.VISIBLE);
                        tv_re_password_error.setText("Password Not Match");
                    }
                    else if (is_file==false)
                    {
                        Toast.makeText(getApplicationContext(), "Profile Image Is Compulsory", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        //UserRegistration userRegistration = new UserRegistration(str_first_name, str_last_name, str_email, str_mob, str_password, gcmToken, str_user_nick_name, registerBy = "0", userAppID);
                        //userRegistration.execute();

                        tv_first_name_error.setVisibility(View.GONE);
                        tv_last_name_error.setVisibility(View.GONE);
                        tv_password_error.setVisibility(View.GONE);
                        tv_email_error.setVisibility(View.GONE);
                        tv_country_error.setVisibility(View.GONE);
                        tv_re_password_error.setVisibility(View.GONE);

                   /* Intent intent = new Intent(RegistrationActivity.this,OtherInfoActivity.class);
                    intent.putExtra("firstName",str_first_name);
                    intent.putExtra("lastName",str_last_name);
                    intent.putExtra("email",str_email);
                    intent.putExtra("password",str_password);
                    intent.putExtra("country",str_country);
                    startActivity(intent);*/


                        if (utility.checkInternet())
                        {
                            // registerUser(str_first_name, str_last_name, str_email, str_password, str_country, userType);
                            new RegisterUser(str_first_name, str_last_name, str_email, str_password, str_country, userType,"","",device_token,"android",device_token).execute();

                        } else {
                            Toast.makeText(RegistrationActivity.this, "Plaese connect to the intenet", Toast.LENGTH_SHORT).show();
                        }


                    }
                }
                else
                {
                    Toast.makeText(RegistrationActivity.this, "Please check terms & conditions", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_fb_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                // loginWithFacebook();
                if(utility.checkInternet())
                {
                    LoginManager.getInstance().logInWithReadPermissions(RegistrationActivity.this, Arrays.asList("email","public_profile"));
                }
                else
                {
                    Toast.makeText(RegistrationActivity.this, "Please connect to intenet", Toast.LENGTH_SHORT).show();
                }


            }
        });

        chk_policy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    is_policy = true;
                }
                else
                {
                    is_policy = false;
                }

            }
        });


        edt_first_name.addTextChangedListener(new TextWatcher() {

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
                    edt_first_name.requestFocus();

                    tv_first_name_error.setVisibility(View.VISIBLE);
                    tv_first_name_error.setText("Enter First Name");
                } else {
                    // edt_first_name.setError(null);

                    tv_first_name_error.setVisibility(View.GONE);
                }
            }
        });

        edt_last_name.addTextChangedListener(new TextWatcher()
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
                   // edt_last_name.setError("Enter last name");
                    edt_last_name.requestFocus();

                    tv_last_name_error.setVisibility(View.VISIBLE);
                    tv_last_name_error.setText("Enter Last Name");
                }
                else
                {
                   // edt_last_name.setError(null);

                    tv_last_name_error.setVisibility(View.GONE);
                }
            }
        });


        edt_password.addTextChangedListener(new TextWatcher()
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
                    edt_password.requestFocus();

                    tv_password_error.setVisibility(View.VISIBLE);
                    tv_password_error.setText("Enter Password");
                }
                else
                {
                  //  edt_password.setError(null);

                    tv_password_error.setVisibility(View.GONE);
                }
            }
        });


        edt_email.addTextChangedListener(new TextWatcher()
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
                    edt_email.requestFocus();

                    tv_email_error.setVisibility(View.VISIBLE);
                    tv_email_error.setText("Enter Email Id");
                }
                else
                {
                   // edt_email.setError(null);

                    tv_email_error.setVisibility(View.GONE);
                }
            }
        });

        lay_upload_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_FILE_REQUEST);

            }
        });

       /* edt_country.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                if(s.length()<0)
                {
                    // edt_email.setError("Enter email");
                    edt_country.requestFocus();

                    tv_country_error.setVisibility(View.VISIBLE);
                    tv_country_error.setText("Enter Country");
                }
                else
                {
                    // edt_email.setError(null);

                    tv_country_error.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
*/
      //  final String countryCode = getCountryZipCode();

       /* try
        {
            final String countryCode = getUserCountry(getApplicationContext());
            if(!countryCode.equals("")&&!countryCode.equals(null))
            {
                auto_country.setText(countryCode);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(RegistrationActivity.this, "Please enter country manually", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }*/


        auto_country.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                auto_country.showDropDown();

            }
        });
    }

    private boolean isRegistered() {
        //Getting shared preferences

        //Getting the value from shared preferences
        //The second parameter is the default value
        //if there is no value in sharedprference then it will return false
        //that means the device is not registered
        return Shared_Preferences_Class.readBoolean(getApplicationContext(),Shared_Preferences_Class.REGISTERED,false);
    }
   /* private void registerDevice()
    {
        //Creating a firebase object
        Firebase firebase = new Firebase(Application_Constants.FIREBASE_APP);

        //Pushing a new element to firebase it will automatically create a unique id
        Firebase newFirebase = firebase.push();

        //Creating a map to store name value pair
        Map<String, String> val = new HashMap<>();

        //pushing msg = none in the map
        val.put("msg", "none");

        //saving the map to firebase
        newFirebase.setValue(val);

        //Getting the unique id generated at firebase
        String uniqueId = newFirebase.getKey();

        uniqueId =  FirebaseInstanceId.getInstance().getToken();

        Shared_Preferences_Class.writeString(getApplicationContext(),Shared_Preferences_Class.FCM_ID,uniqueId);

        //Finally we need to implement a method to store this unique id to our server
        // sendIdToServer(uniqueId);

    }*/


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

                lay_upload_pic.setVisibility(View.GONE);
                img_user_image.setVisibility(View.VISIBLE);

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

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }


    // Facebook user details
    private void getUserDetailsFromFB()
    {

        progressDialog = new ProgressDialog(RegistrationActivity.this);
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

                                Toast.makeText(RegistrationActivity.this, "Name: " + fb_name + "Email: " + fb_email+"Fb Id: "+ fb_id, Toast.LENGTH_SHORT).show();

                                new RegisterUser(str_first_name, str_last_name, fb_email, "", "", userType,"facebook",fb_id,device_token,"android",device_token).execute();

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
    // Get country code
    public String getCountryZipCode()
    {
        String CountryID="";
        String CountryZipCode="";

        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID= manager.getSimCountryIso().toUpperCase();
        String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(CountryID.trim())){
                CountryZipCode=g[0];
                break;
            }
        }
        return CountryZipCode;
    }


    public static String getUserCountry(Context context)
    {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US);
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        }
        catch (Exception e) { }
        return null;
    }




    private void registerUser(String userFirstName, String userLastName, String userEmail, String userPassword ,String country,
                              String userType)
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();
            params.put("firstName", userFirstName);
            params.put("lastName", userLastName);
            params.put("UserEmailId", userEmail);
            params.put("UserPassword", userPassword);
            params.put("country", country);
            params.put("userType", userType);


           /* params.put("firstName", "javed");
            params.put("lastName", "shaikh");
            params.put("UserEmailId", "abgggjgc@abc.com");
            params.put("UserPassword", "123456");
            params.put("country", "india");
            params.put("userType", "individual");*/


            ServiceHandler serviceHandler = new ServiceHandler(RegistrationActivity.this);

            serviceHandler.registerUser(params, registration_url, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result)
                {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status,str_msg;
                    String   UserId, UserType,UserName,UserEmail,UserFirstName,UserLastName,
                    company, phone,UserProfileImage,taxForm;
                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            if(str_status.equalsIgnoreCase("true"))
                            {

                                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                                am.setStreamMute(AudioManager.STREAM_MUSIC, false);

                                UserId = jobject.getString("UserId");
                                UserType = jobject.getString("UserType");
                                UserName =jobject.getString("UserName");
                                UserEmail = jobject.getString("UserEmail");
                                UserFirstName = jobject.getString("UserFirstName");
                                UserLastName = jobject.getString("UserLastName");
                                company = jobject.getString("company");
                                phone = jobject.getString("phone");
                                UserProfileImage = jobject.getString("UserProfileImage");
                                taxForm= jobject.getString("taxForm");


                                if(taxForm.equals("1"))
                                {
                                    Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.IS_TAX_FORM_FILLED, "yes");
                                }
                                else
                                {
                                    Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.IS_TAX_FORM_FILLED, "no");
                                }
                                Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.IS_PSYCHOMETRIC, "no");

                                Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.USER_ID, UserId);
                               /* Intent i = new Intent(RegistrationActivity.this,TaxActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.putExtra("registration", "registration");
                                startActivity(i);
                                finish();*/

                                Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("menu","dashboard");
                                startActivity(intent);
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


    private void getCountry()
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(RegistrationActivity.this);

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

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegistrationActivity.this,android.R.layout.simple_list_item_1,countryNameList);
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

    class RegisterUser extends AsyncTask<String, String, String>
    {
        String userFirstName,userLastName, userEmail, userPassword, userType,country,authBy,authId,deviceId,appType;
        String str_status,json_responce,str_msg="";

        String   UserId, UserType,UserName,UserEmail,UserFirstName,UserLastName,
                company, phone,UserProfileImage,taxForm,address,city,state,device_token;

        public RegisterUser(String userFirstName, String userLastName, String userEmail, String userPassword ,String country,
                            String userType, String authBy, String authId, String deviceId, String appType, String device_token)
        {

            this.userFirstName = userFirstName;
            this.userLastName = userLastName;
            this.userEmail = userEmail;
            this.userPassword = userPassword;
            this.country = country;
            this.userType = userType;
            this.authBy=authBy;
            this.authId=authId;
            this.deviceId=deviceId;
            this.appType=appType;
            this.device_token=device_token;
        }

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialogProfile = new ProgressDialog(RegistrationActivity.this,R.style.MyTheme);
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
                MultipartUtility multipart = new MultipartUtility(registration_url, charset);
                multipart.addHeaderField("User-Agent", "CodeJava");
                multipart.addHeaderField("Test-Header", "Header-Value");
                multipart.addFormField("description", "Cool Pictures");
                multipart.addFormField("keywords", "Java,upload,Spring");
                multipart.addFormField("firstName", userFirstName);
                multipart.addFormField("lastName", userLastName);
                multipart.addFormField("UserEmailId", userEmail);
                multipart.addFormField("UserPassword", userPassword);
                multipart.addFormField("country", country);
                multipart.addFormField("authBy", authBy);
                multipart.addFormField("authId", authId);
                multipart.addFormField("deviceId", deviceId);
                multipart.addFormField("appType", appType);
                multipart.addFormField("userType", userType);
                multipart.addFormField("device_token", device_token);
                multipart.addFormField("appType", "Android");



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
                else if(!fb_image_url.isEmpty())
                {
                    multipart.addFormField("profilePic", fb_image_url);
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
                    UserId = jobject.getString("UserId");
                    UserType = jobject.getString("UserType");
                    UserName =jobject.getString("UserName");
                    UserEmail = jobject.getString("UserEmail");
                    UserFirstName = jobject.getString("UserFirstName");
                    UserLastName = jobject.getString("UserLastName");
                    company = jobject.getString("company");
                    phone = jobject.getString("phone");
                    UserProfileImage = jobject.getString("UserProfileImage");
                    taxForm= jobject.getString("taxForm");
                    address= jobject.getString("address");
                    state = jobject.getString("state");
                    city = jobject.getString("city");

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

                    if(taxForm.equals("1"))
                    {
                        Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.IS_TAX_FORM_FILLED, "yes");
                    }
                    else
                    {
                        Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.IS_TAX_FORM_FILLED, "no");
                    }

                    Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.IS_PSYCHOMETRIC, "no");

                    Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.USER_ID, UserId);
                    Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.USER_FIRST_NAME, UserFirstName);
                    Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.USER_LAST_NAME, UserLastName);
                    Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.USER_PHONE, phone);
                    Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.USER_EMAIL, UserEmail);
                    Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.USER_ADDRESS, address);
                    Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.USER_CITY, city);
                    Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.USER_STATE, state);
                    Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.USER_COUNTRY, country);
                    Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.USER_IMAGE, UserProfileImage);
                    Shared_Preferences_Class.writeString(RegistrationActivity.this, Shared_Preferences_Class.USER_TYPE, UserType);




                               /* Intent i = new Intent(RegistrationActivity.this,TaxActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.putExtra("registration", "registration");
                                startActivity(i);
                                finish();*/


                    if(UserType.equalsIgnoreCase("Employer"))
                    {
                        Intent intent = new Intent(RegistrationActivity.this,EmployerMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("menu","dashboard");
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("menu","dashboard");
                        startActivity(intent);
                        finish();
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

}
