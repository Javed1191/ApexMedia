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

public class AskAdminActivity extends AppCompatActivity
{
    private Utility utility;
    private TextView tv_action_title,tv_no_msgs;
    private String jobId,fromUserId,type,project,toUserId;
    private static final String TAG = "ChatActivity";

    private ChatArrayAdapter chatArrayAdapter;

    private ListView listView;
    private EditText chatText;
    private boolean side = false;
    private String strMyMessages = Application_Constants.Main_URL+"keyword=askAdminDirectly";;

    private String userId="";
    private ProgressDialog dialogProfile;

    private int PICK_FILE_REQUEST1 = 1;
    String str_image_path;
    boolean is_file1=false;
    File attachment1;
    ImageView img_send;

    private boolean is_dispute = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ask_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        utility = new Utility(getApplicationContext());

        tv_action_title = (TextView) toolbar.findViewById(R.id.tv_action_title);
        tv_action_title.setText("Chat");
       // toolbar.setLogo(R.drawable.actionbar_32);

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

        img_send = (ImageView) findViewById(R.id.img_send);
        tv_no_msgs = (TextView) findViewById(R.id.tv_no_msgs);


        listView = (ListView) findViewById(R.id.msgview);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.list_row_layout_even);
        listView.setAdapter(chatArrayAdapter);




        chatText = (EditText) findViewById(R.id.msg);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
               // sendChatMessage();

                if(chatText.getText().toString().isEmpty())
                {
                    chatText.requestFocus();
                    chatText.setError("Message should not empty");
                }
                else
                {
                    if(utility.checkInternet())
                    {

                            new SendMessage(userId,jobId,chatText.getText().toString(),"send").execute();


                    }
                    else
                    {
                        Toast.makeText(AskAdminActivity.this, "Please connnect to intrnet", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });



        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

        userId = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_ID,"");
        type = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_TYPE,"");

        Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {



            jobId = intent.getStringExtra("jobId");
            project = intent.getStringExtra("project");

            tv_action_title.setText(" "+project);
            getMessages(userId,jobId,"","get");


           /* if(intent.getStringExtra("dispute")!=null)
            {
                toUserId = intent.getStringExtra("toUserId");
                jobId = intent.getStringExtra("jobId");
                project = intent.getStringExtra("project");

                tv_action_title.setText(" "+project);


                strMyMessages = Application_Constants.Main_URL+"keyword=disputeDetail";

                is_dispute = true;

                getDisputeMessages(userId,toUserId,jobId,type);

            }
            else
            {
                is_dispute = false;
                strMyMessages = Application_Constants.Main_URL+"keyword=messageDetail";

                jobId = intent.getStringExtra("jobId");
                fromUserId = intent.getStringExtra("fromUserId");
                project = intent.getStringExtra("project");

                tv_action_title.setText(" "+project);
                getMessages(userId,fromUserId,jobId,type);
            }*/



        }

    }



    private boolean sendChatMessage()
    {
        chatArrayAdapter.add(new ChatMessage(side, chatText.getText().toString(),"","",""));
        chatText.setText("");
        side = !side;
        return true;
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

        if (requestCode == PICK_FILE_REQUEST1 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            //  str_image_path = getPath(uri);

            try {

                Uri uri = data.getData();
                // str_image_path = uri.getPath();
                str_image_path = FetchPath.getPath(this, uri);



                String str_file_name = uri.getLastPathSegment();
                is_file1 = true;


                // code to get image from image view and convert it into file

                File filesDir =getFilesDir();
                attachment1 = new File(str_image_path);
                long file_size =  attachment1.length();
                long file_size_kb = file_size/1024;

                System.out.print(file_size_kb);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


    }

    private void getMessages(final String empId, String jobId, String message, String action)
    {

        if(utility.checkInternet())
        {
            final Map<String, String> params = new HashMap<String, String>();
            params.put("empId", empId);
            params.put("jobId", jobId);
            params.put("message", message);
            params.put("action", action);

//            params.put("userId", "103");
//            params.put("fromUserId", "115");
//            params.put("jobId", "16");
//            params.put("type", "contractor");

            ServiceHandler serviceHandler = new ServiceHandler(AskAdminActivity.this);
            final ArrayList<Messages> messagesArrayList = new ArrayList<Messages>();

            serviceHandler.registerUser(params, strMyMessages, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status, str_msg;
                    String id, fromUser, project, message, toUser,job_name;
                    try {
                        if (str_json != null) {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            job_name = jobject.getString("job_name");
                            if (str_status.equalsIgnoreCase("true"))
                            {
                                JSONArray jArray = new JSONArray();
                                jArray = jobject.getJSONArray("admin_messages");

                                for (int i = 0; i < jArray.length(); i++)
                                {
                                    JSONObject Obj = jArray.getJSONObject(i);
                                    // A category variables
                                    id = Obj.getString("messageId");
                                    message = Obj.getString("message");



                                  //  Messages messages = new Messages(id,msg_from,project,message,date_time);
                                   // messagesArrayList.add(messages);

                                    chatArrayAdapter.add(new ChatMessage(side, message,id,empId,empId));

                                }

                                if (str_status.equals("true"))
                                {

                                }



                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), str_status, Toast.LENGTH_SHORT).show();


                                listView.setVisibility(View.INVISIBLE);
                                tv_no_msgs.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    class SendMessage extends AsyncTask<String, String, String>
    {
        String empId,toUserId, jobId, action, message;
        String str_status,json_responce,str_msg="",UserProfileImage="";

        public SendMessage(String empId,String jobId, String message, String action)
        {
            this.empId = empId;
            this.jobId = jobId;
            this.message = message;
            this.action = action;
        }

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialogProfile = new ProgressDialog(AskAdminActivity.this,R.style.MyTheme);
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
                MultipartUtility multipart = new MultipartUtility(strMyMessages, charset);
                multipart.addHeaderField("User-Agent", "CodeJava");
                multipart.addHeaderField("Test-Header", "Header-Value");
                multipart.addFormField("description", "Cool Pictures");
                multipart.addFormField("keywords", "Java,upload,Spring");
                multipart.addFormField("empId", empId);
                multipart.addFormField("jobId", jobId);
                multipart.addFormField("message", message);
                multipart.addFormField("action", action);

              /*  contractorId=”106”, first_name=amit, last_name=Srivastava,
                        display_name=amit123, papal_id=amit@gmail.com, address=sector 17, city=gurgaon,
                        state=haryana, country=India, zip_code=122001, phone=8886669990, user_type=individual,
                        hourly_rate=10, overview=overview, service_desc=service,
                        payment_terms=paymentterms, keywords=”php, mysql, javascript, abc”, profilePic=abc.jpg.
                */

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


                    chatArrayAdapter.add(new ChatMessage(side, message,jobId,userId,toUserId));
                    chatText.setText("");

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

