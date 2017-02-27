package com.apexmediatechnologies.apexmedia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.NotificationContractorAdapter;
import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;

public class NotificationContractorActivity extends AppCompatActivity
{

   // private String strGetProducts ="http://192.168.1.3/hdfc_bso/android_web/action?xAction=products";
    private Utility utility;
    private TextView tv_action_title;
    private String strGetNotification = Application_Constants.Main_URL+"keyword=get_push_notifications";
    private RecyclerView notification_recycler_view;
    private TextView tv_feeds_not_found;
    private String userId;
    private NotificationContractorAdapter notificationContractorAdapter;
    RelativeLayout notificationCount1;
    public TextView badge_notification_1;
    public RelativeLayout relative_layout_item_count;
    String notification_count="",job_id="",category_id="",screen="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_notifications);

       /* getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_text);*/

        notification_recycler_view = (RecyclerView)findViewById(R.id.notification_recycler_view);
        notification_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tv_feeds_not_found = (TextView) findViewById(R.id.tv_feeds_not_found);
        userId = Shared_Preferences_Class.readString(getApplicationContext(), Shared_Preferences_Class.USER_ID, "");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

       // toolbar.setLogo(R.drawable.actionbar_32);

        userId = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_ID,"");


        tv_action_title = (TextView) toolbar.findViewById(R.id.tv_action_title);
        tv_action_title.setText("Notifications");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
               /* Intent intent = new Intent(JobDetailActivity.this, MainActivity.class);
                intent.putExtra("menu", "dashboard");
                startActivity(intent);*/
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationContractorActivity.this, MainActivity.class);
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
/*
        String notification = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.NOTIFICATION_DATA,"");
        Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {
            String menu = intent.getStringExtra("menu");

            for (String key : getIntent().getExtras().keySet())
            {
                String value = getIntent().getExtras().getString(key);

                Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
                Log.d("APEX", "Key: " + key + " Value: " + value);

            }

            Toast.makeText(this, menu, Toast.LENGTH_SHORT).show();
        }*/
        if (getIntent().getExtras() != null)
        {

            if(getIntent().getExtras().getString("menu")!=null)
            {
                job_id = getIntent().getExtras().getString("job_id");
                category_id = getIntent().getExtras().getString("category_id");
                screen = getIntent().getExtras().getString("screen");

                Toast.makeText(this, "Job_id: " + job_id, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "category_id: " + category_id, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "screen: " + screen, Toast.LENGTH_SHORT).show();

            }
            else
            {
                job_id = getIntent().getExtras().getString("job_id");
                category_id = getIntent().getExtras().getString("category_id");
                screen = getIntent().getExtras().getString("screen");



                Toast.makeText(this, "Back Job_id: " + job_id, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Back category_id: " + category_id, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Back screen: " + screen, Toast.LENGTH_SHORT).show();

               /* String valuea = getIntent().getExtras().getString("job_id");

                Toast.makeText(this, "job_id "+ valuea, Toast.LENGTH_SHORT).show();

                for (String key : getIntent().getExtras().keySet())
                {
                    String value = getIntent().getExtras().getString(key);

                    Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
                    Log.d("APEX", "Key: " + key + " Value: " + value);

              *//*  if(value.equalsIgnoreCase("Notification"))
                {
                    break;
                }*//*
                }*/
            }


        }


        getNotifications(userId);


    }

    @Override
    protected void onResume()
    {
        super.onResume();

       /* if (getIntent().getExtras() != null)
        {

           *//* String valuea = getIntent().getExtras().getString("menu");

            Toast.makeText(this, "Before "+ valuea, Toast.LENGTH_SHORT).show();*//*

            for (String key : getIntent().getExtras().keySet())
            {
                String value = getIntent().getExtras().getString(key);

                Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
                Log.d("APEX", "Key: " + key + " Value: " + value);

               *//* if(value.equalsIgnoreCase("Notification"))
                {
                    break;
                }*//*
            }
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);


       /* notification_count = Shared_Preferences_Class.readString(getApplicationContext(), Shared_Preferences_Class.NOTIFICATION_COUNT, "");

        notification_count = "10";
        MenuItem item1 = menu.findItem(R.id.action_notification);
        MenuItemCompat.setActionView(item1, R.layout.notification_update_count_layout);
        notificationCount1 = (RelativeLayout) MenuItemCompat.getActionView(item1);
        relative_layout_item_count = (RelativeLayout) notificationCount1.findViewById(R.id.relative_layout_item_count);
        badge_notification_1 = (TextView) notificationCount1.findViewById(R.id.badge_notification_1);

        if(notification_count.equals("")||notification_count.equals(null))
        {
            badge_notification_1.setVisibility(View.GONE);
        }
        else
        {
            // relative_layout_item_count.setVisibility(View.VISIBLE);
            badge_notification_1.setText(notification_count);
        }

        relative_layout_item_count.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(NotificationContractorActivity.this,NotificationContractorActivity.class);
                startActivity(intent);
            }
        });
*/


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification)
        {
           /* Intent intent = new Intent(MainActivity.this,EditProfileActivity.class);
            startActivity(intent);*/
        }

        return super.onOptionsItemSelected(item);
    }


    private void getNotifications(String userId)
    {

        if(utility.checkInternet())
        {
            final Map<String, String> params = new HashMap<String, String>();
            params.put("login_userId", userId);
            params.put("device_type", "Android");

            ServiceHandler serviceHandler = new ServiceHandler(NotificationContractorActivity.this);
            final ArrayList<Notification> feedsArrayList = new ArrayList<Notification>();

            serviceHandler.registerUser(params, strGetNotification, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status,str_msg;
                    String title, message,job_id, proposal_id, from_user_id, to_user_id, read,
                            screen, module, date_time;
                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            if(str_status.equalsIgnoreCase("true"))
                            {
                                JSONArray jArray = new JSONArray();
                                jArray = jobject.getJSONArray("all_notifications");

                                for (int i = 0; i < jArray.length(); i++)
                                {
                                    JSONObject Obj = jArray.getJSONObject(i);
                                    // A category variables
                                    title = Obj.getString("title");
                                    message = Obj.getString("message");
                                    job_id = Obj.getString("job_id");
                                    proposal_id = Obj.getString("proposal_id");
                                    from_user_id = Obj.getString("from_user_id");
                                    to_user_id = Obj.getString("to_user_id");
                                    read = Obj.getString("read");
                                    screen = Obj.getString("screen");
                                    module= Obj.getString("module");
                                    date_time= Obj.getString("date_time");


                                    Notification notification = new Notification(title,job_id,proposal_id,from_user_id,
                                            to_user_id,read,screen,module,date_time,message);
                                    feedsArrayList.add(notification);

                                }

                                if(str_status.equals("true"))
                                {
                                    tv_feeds_not_found.setVisibility(View.GONE);
                                    notification_recycler_view.setVisibility(View.VISIBLE);
                                    notificationContractorAdapter = new NotificationContractorAdapter(NotificationContractorActivity.this, feedsArrayList);
                                    notification_recycler_view.setAdapter(notificationContractorAdapter);
                                }
                                else
                                {
                                    tv_feeds_not_found.setVisibility(View.VISIBLE);
                                    notification_recycler_view.setVisibility(View.GONE);

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
