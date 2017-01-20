package com.apexmediatechnologies.apexmedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
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

public class TestingActivity extends AppCompatActivity
{
    private Utility utility;
    private TextView tv_action_title;
    private String jobId,fromUserId,type,project,toUserId;
    private static final String TAG = "ChatActivity";

    private ChatArrayAdapter chatArrayAdapter;

    private ListView listView;
    private EditText chatText;
    private boolean side = false;
    private String strMyMessages="";
    private String strSendMessage="";

    private String userId="";
    private ProgressDialog dialogProfile;

    private int PICK_FILE_REQUEST1 = 1;
    String str_image_path;
    boolean is_file1=false;
    File attachment1;
    ImageView img_send,img_attach;

    private boolean is_dispute = false;
    private Button btn_log_out;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_testing);

        btn_log_out = (Button) findViewById(R.id.btn_log_out);

        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared_Preferences_Class.getEditor(getApplicationContext()).remove(Shared_Preferences_Class.USER_ID).commit();
                Shared_Preferences_Class.getEditor(getApplicationContext()).remove(Shared_Preferences_Class.USER_FIRST_NAME).commit();
                Shared_Preferences_Class.getEditor(getApplicationContext()).remove(Shared_Preferences_Class.USER_LAST_NAME).commit();
                Shared_Preferences_Class.getEditor(getApplicationContext()).remove(Shared_Preferences_Class.USER_PHONE).commit();
                Shared_Preferences_Class.getEditor(getApplicationContext()).remove(Shared_Preferences_Class.USER_EMAIL).commit();
                Shared_Preferences_Class.getEditor(getApplicationContext()).remove(Shared_Preferences_Class.USER_ADDRESS).commit();
                Shared_Preferences_Class.getEditor(getApplicationContext()).remove(Shared_Preferences_Class.USER_CITY).commit();
                Shared_Preferences_Class.getEditor(getApplicationContext()).remove(Shared_Preferences_Class.USER_STATE).commit();
                Shared_Preferences_Class.getEditor(getApplicationContext()).remove(Shared_Preferences_Class.USER_COUNTRY).commit();
                Shared_Preferences_Class.getEditor(getApplicationContext()).remove(Shared_Preferences_Class.USER_IMAGE).commit();
                Shared_Preferences_Class.getEditor(getApplicationContext()).remove(Shared_Preferences_Class.USER_TYPE).commit();

                Intent intent = new Intent(TestingActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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




    }

}

