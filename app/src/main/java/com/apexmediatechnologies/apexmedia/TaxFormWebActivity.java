package com.apexmediatechnologies.apexmedia;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.ChatArrayAdapter;
import services.Application_Constants;
import services.FetchPath;
import services.MultipartUtility;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class TaxFormWebActivity extends AppCompatActivity
{
    private Utility utility;
    private TextView tv_action_title,tv_no_msgs;
    private String jobId,fromUserId,type,project,toUserId,userId,user_country;
    private String strMyMessages = Application_Constants.Main_URL+"keyword=askAdminDirectly";;
    WebView mWebView;
    /*String strTaxFormUSAUrl = "http://development.eweblabs.in/apex/public/taxform/usaapi?type=mobile&user_id=";;
    String strTaxFormRegularUrl = "http://development.eweblabs.in/apex/public/taxform/indiaapi?type=mobile&user_id=";*/
    /*String strTaxFormUSAUrl = "https://key4deal.com/apex/public/taxform/usaapi?type=mobile&user_id=";;
    String strTaxFormRegularUrl = "https://key4deal.com/apex/public/taxform/indiaapi?type=mobile&user_id=";*/

    String strTaxFormUSAUrl = "https://99prosolution.com/apex/public/taxform/usaapi?type=mobile&user_id=";;
    String strTaxFormRegularUrl = "https://99prosolution.com/apex/public/taxform/indiaapi?type=mobile&user_id=";

    String str_call_tax_form_url="";
    ///String strTaxFormReturnUrl = "http://development.eweblabs.in/apex/public/dashboard/index";
    String strTaxFormReturnUrl = "https://99prosolution.com/apex/public/dashboard/index";

    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tax_form_web);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        utility = new Utility(getApplicationContext());

        tv_action_title = (TextView) toolbar.findViewById(R.id.tv_action_title);
        tv_action_title.setText("Tax Form");
        //toolbar.setLogo(R.drawable.actionbar_32);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               onBackPressed();
                /*Intent intent = new Intent(JobDetailProposalActivity.this,MainActivity.class);
                intent.putExtra("menu","dashboard");
                startActivity(intent);*/
            }
        });

        //edt_login_email.requestFocus();
        // to hide key board
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // set color to status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        userId = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_ID,"");
        type = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_TYPE,"");
        user_country = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_COUNTRY,"");



        if(user_country.equalsIgnoreCase("United States"))
        {
            //Toast.makeText(this, "USA", Toast.LENGTH_SHORT).show();

            str_call_tax_form_url = strTaxFormUSAUrl+userId;
        }
        else
        {
           // Toast.makeText(this, "Other", Toast.LENGTH_SHORT).show();

            str_call_tax_form_url = strTaxFormRegularUrl+userId;
        }



        mWebView = (WebView)findViewById(R.id.webView1);
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowContentAccess(true);
        mWebView.loadUrl(str_call_tax_form_url);


        mWebView.setWebChromeClient(new WebChromeClient()
        {
            // For 3.0+ Devices (Start)
            // onActivityResult attached before constructor
            protected void openFileChooser(ValueCallback uploadMsg, String acceptType)
            {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            }


            // For Lollipop 5.0+ Devices
            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
            {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

                uploadMessage = filePathCallback;

                Intent intent = fileChooserParams.createIntent();
                try
                {
                    startActivityForResult(intent, REQUEST_SELECT_FILE);
                } catch (ActivityNotFoundException e)
                {
                    uploadMessage = null;
                    Toast.makeText(getApplicationContext().getApplicationContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

            //For Android 4.1 only
            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
            {
                mUploadMessage = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            protected void openFileChooser(ValueCallback<Uri> uploadMsg)
            {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }
        });



        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
              //  Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
                Log.v("TEST", url);
                if(url.equals(strTaxFormReturnUrl))
                {
                    Toast.makeText(getApplicationContext(), "Tax Form Saved", Toast.LENGTH_SHORT).show();

                    Shared_Preferences_Class.writeString(getApplicationContext(), Shared_Preferences_Class.IS_TAX_FORM_FILLED, "yes");
                    Shared_Preferences_Class.writeString(getApplicationContext(), Shared_Preferences_Class.IS_PSYCHOMETRIC, "no");
                    Intent i = new Intent(TaxFormWebActivity.this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("menu", "dashboard");
                    startActivity(i);
                    finish();


                }
                else{
                    view.loadUrl(url);
                }
                return true;
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (requestCode == REQUEST_SELECT_FILE)
            {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        }
        else if (requestCode == FILECHOOSER_RESULTCODE)
        {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != MainActivity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
        else
            Toast.makeText(getApplicationContext().getApplicationContext(), "Failed to Upload Image", Toast.LENGTH_LONG).show();
    }



}

