package com.apexmediatechnologies.apexmedia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
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

import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;

import static com.apexmediatechnologies.apexmedia.R.id.img_user;
import static com.apexmediatechnologies.apexmedia.R.id.prog_job_progress;
import static com.apexmediatechnologies.apexmedia.R.id.rating_user;

public class PsychometricActivity extends AppCompatActivity
{

    private String strPsychometric = Application_Constants.Main_URL+"keyword=question_ans";
    private String strSavePsychometric = Application_Constants.Main_URL+"keyword=save_psychometric";

    private Utility utility;
    private TextView tv_job_title,tv_question,tv_action_title,tv_question_no,tv_job_desc,tv_skills,
            tv_user_name,tv_user_country,tv_start_type,tv_set_status,tv_dispute,tv_q_no;
    private Button btn_previous,btn_next;
    private String jobProgress,jobId,jobTitle,jobDec, jobSkills, jobStartType,jobPostedDate,jobBudget,
    userName, userCountry,userImage,userId,empId;

    private RadioGroup rdo_group_opt;
    private RadioButton rdo_opt_1,rdo_opt_2,rdo_opt_3,rdo_opt_4,rdo_opt_5;
    private ArrayList<Psychometric> psychometricArrayList;
    private int cnt=0;
    HashMap<String, String> ansMap=new HashMap<String, String>();
    private LinearLayout lay_button;
    private boolean is_employer = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychometric);

       /* getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_text);*/

        rdo_group_opt = (RadioGroup) findViewById(R.id.rdo_group_opt);
        tv_question= (TextView) findViewById(R.id.tv_question);
        rdo_opt_1 = (RadioButton) findViewById(R.id.rdo_opt_1);
        rdo_opt_2 = (RadioButton) findViewById(R.id.rdo_opt_2);
        rdo_opt_3 = (RadioButton) findViewById(R.id.rdo_opt_3);
        rdo_opt_4 = (RadioButton) findViewById(R.id.rdo_opt_4);
        rdo_opt_5 = (RadioButton) findViewById(R.id.rdo_opt_5);
        tv_question_no = (TextView) findViewById(R.id.tv_question_no);
        tv_q_no  = (TextView) findViewById(R.id.tv_q_no);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_previous = (Button) findViewById(R.id.btn_previous);
        lay_button = (LinearLayout) findViewById(R.id.lay_button);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

       // toolbar.setLogo(R.drawable.actionbar_32);

        userId = Shared_Preferences_Class.readString(getApplicationContext(),Shared_Preferences_Class.USER_ID,"");


        tv_action_title = (TextView) toolbar.findViewById(R.id.tv_action_title);
        tv_action_title.setText("Psychometric Test");

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
                Intent intent = new Intent(PsychometricActivity.this, MainActivity.class);
                intent.putExtra("menu", "dashboard");
                startActivity(intent);


            }
        });

        tv_job_title = (TextView) findViewById(R.id.tv_job_title);

        utility = new Utility(getApplicationContext());


        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }


        Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {
            is_employer = intent.getBooleanExtra("is_employer",false);
        }


        getQuestions(userId);



        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {

                    if (!rdo_opt_1.isChecked()&&!rdo_opt_2.isChecked()&&!rdo_opt_3.isChecked()&&!rdo_opt_4.isChecked()&&!rdo_opt_5.isChecked())
                    {
                        Toast.makeText(PsychometricActivity.this, "Select Any Answer", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        if(cnt<psychometricArrayList.size()-1)
                        {
                            Psychometric feedsAns = psychometricArrayList.get(cnt);

                            cnt++;
                            Psychometric feeds = psychometricArrayList.get(cnt);
                            String str_ques_id = feedsAns.getQuestionId();
                            tv_question.setText(feeds.getQuestion());

                            tv_q_no.setText(String.valueOf(cnt+1)+ " of " + psychometricArrayList.size());
                            tv_question_no.setText(String.valueOf(cnt+1));

                            List<String>list_opt_1 = feedsAns.getOtp();

                            rdo_opt_1.setText(list_opt_1.get(0));
                            rdo_opt_2.setText(list_opt_1.get(1));
                            rdo_opt_3.setText(list_opt_1.get(2));
                            rdo_opt_4.setText(list_opt_1.get(3));
                            rdo_opt_5.setText(list_opt_1.get(4));

                            int selectedId = rdo_group_opt.getCheckedRadioButtonId();

                            // find the radiobutton by returned id
                            RadioButton radioButton = (RadioButton) findViewById(selectedId);
                            String str_opt="";

                            List<String>list_op = feeds.getOtpIdList();

                            if(selectedId == R.id.rdo_opt_1)
                            {
                                str_opt = list_op.get(0);
                            }
                            else if(selectedId == R.id.rdo_opt_2)
                            {
                                str_opt = list_op.get(1);
                            }
                            else if(selectedId == R.id.rdo_opt_3)
                            {
                                str_opt = list_op.get(2);
                            }
                            else if(selectedId == R.id.rdo_opt_4)
                            {
                                str_opt = list_op.get(3);
                            }
                            else if(selectedId == R.id.rdo_opt_5)
                            {
                                str_opt = list_op.get(4);
                            }

                            ansMap.put(str_ques_id,str_opt);

                            // Toast.makeText(getApplicationContext(),str_opt,Toast.LENGTH_SHORT);

                            rdo_group_opt.clearCheck();

                            /*rdo_opt_1.setChecked(false);
                            rdo_opt_2.setChecked(false);
                            rdo_opt_3.setChecked(false);
                            rdo_opt_4.setChecked(false);
                            rdo_opt_5.setChecked(false);
*/
                        }
                        else
                        {
                            JSONObject mJobject = new JSONObject(ansMap);

                            sendAns(userId,mJobject.toString());

                            //Toast.makeText(PsychometricActivity.this, "No values in next", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                   // Toast.makeText(getApplicationContext(),"No values in next",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(cnt>0)
                {
                    try
                    {
                        Psychometric feedsAns = psychometricArrayList.get(cnt);

                        cnt--;
                        tv_q_no.setText(String.valueOf(cnt+1)+ " of " + psychometricArrayList.size());
                        tv_question_no.setText(String.valueOf(cnt+1));

                        Psychometric feeds = psychometricArrayList.get(cnt);
                        String str_ques_id = feedsAns.getQuestionId();
                        tv_question.setText(feeds.getQuestion());

                        List<String>list_opt_1 = feeds.getOtp();

                        rdo_opt_1.setText(list_opt_1.get(0));
                        rdo_opt_2.setText(list_opt_1.get(1));
                        rdo_opt_3.setText(list_opt_1.get(2));
                        rdo_opt_4.setText(list_opt_1.get(3));
                        rdo_opt_5.setText(list_opt_1.get(4));

                        int selectedId = rdo_group_opt.getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                        RadioButton radioButton = (RadioButton) findViewById(selectedId);
                        String str_opt="";


                        if(selectedId == R.id.rdo_opt_1)
                        {
                            str_opt = list_opt_1.get(0);
                        }
                        else if(selectedId == R.id.rdo_opt_2)
                        {
                            str_opt = list_opt_1.get(1);
                        }
                        else if(selectedId == R.id.rdo_opt_3)
                        {
                            str_opt = list_opt_1.get(2);
                        }
                        else if(selectedId == R.id.rdo_opt_4)
                        {
                            str_opt = list_opt_1.get(3);
                        }
                        else if(selectedId == R.id.rdo_opt_5)
                        {
                            str_opt = list_opt_1.get(4);
                        }

                        ansMap.put(str_ques_id,str_opt);

                       // Toast.makeText(getApplicationContext(),str_opt,Toast.LENGTH_SHORT);


                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
                else
                {
                   // Toast.makeText(PsychometricActivity.this, "No values in privious", Toast.LENGTH_SHORT).show();
                }



            }
        });


    }




    private void getQuestions(String userId)
    {

        if(utility.checkInternet())
        {
            final Map<String, String> params = new HashMap<String, String>();
            params.put("userId", userId);

            ServiceHandler serviceHandler = new ServiceHandler(PsychometricActivity.this);
            psychometricArrayList = new ArrayList<Psychometric>();

            serviceHandler.registerUser(params, strPsychometric, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status = "",str_msg;
                    String   qId, question,keyed,ansText,ansId;
                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            List<String>list_opt = null;
                            List<String>list_opt_id = null;
                            List<Options>list_opt_obj = null;

                            if(str_status.equalsIgnoreCase("true"))
                            {
                                lay_button.setVisibility(View.VISIBLE);

                                Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();
                                JSONArray jArray = new JSONArray();
                                jArray = jobject.getJSONArray("Data");

                                for (int i = 0; i < jArray.length(); i++)
                                {
                                    JSONObject Obj = jArray.getJSONObject(i);
                                    // A category variables
                                    qId = Obj.getString("qId");
                                    question = Obj.getString("question");
                                    keyed = Obj.getString("keyed");

                                    JSONArray ansOpt = Obj.getJSONArray("ansOpt");
                                    if(ansOpt.length()>0)
                                    {
                                        list_opt = new ArrayList<String>();
                                        list_opt_id = new ArrayList<String>();
                                        list_opt_obj = new ArrayList<Options>();

                                        for (int j = 0; j < ansOpt.length(); j++)
                                        {
                                            JSONObject ObjOpt = ansOpt.getJSONObject(j);
                                            ansText  = ObjOpt.getString("ansText");
                                            ansId = ObjOpt.getString("ansId");
                                            list_opt.add((ansText));
                                            list_opt_id.add(ansId);

                                            Options options = new Options(ansId,ansText);

                                            list_opt_obj.add(options);
                                        }
                                    }

                                    Psychometric feeds = new Psychometric(qId,question,keyed,list_opt,list_opt_obj,list_opt_id);
                                    psychometricArrayList.add(feeds);

                                }

                                Psychometric feeds = psychometricArrayList.get(0);

                                tv_question_no.setText("1");
                                tv_question.setText(feeds.getQuestion());

                                List<String>list_opt_1 = feeds.getOtp();
                                List<Options>list_opt_obj1 = feeds.getOtpObjList();

                                rdo_opt_1.setText(list_opt_1.get(0));
                                rdo_opt_2.setText(list_opt_1.get(1));
                                rdo_opt_3.setText(list_opt_1.get(2));
                                rdo_opt_4.setText(list_opt_1.get(3));
                                rdo_opt_5.setText(list_opt_1.get(4));

                                tv_q_no.setText("1 of " + psychometricArrayList.size());

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

    private void sendAns(String userId, String userResponses)
    {

        if(utility.checkInternet())
        {
            final Map<String, String> params = new HashMap<String, String>();
            params.put("userId", userId);
            params.put("userResponses", userResponses);


            ServiceHandler serviceHandler = new ServiceHandler(PsychometricActivity.this);

            serviceHandler.registerUser(params, strSavePsychometric, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status = "",str_msg;
                    try {
                        if(str_json!=null)
                        {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");

                            if(str_status.equalsIgnoreCase("true"))
                            {

                                Toast.makeText(getApplicationContext(), str_msg,Toast.LENGTH_SHORT).show();

                                Shared_Preferences_Class.writeString(PsychometricActivity.this, Shared_Preferences_Class.IS_PSYCHOMETRIC, "yes");

                                if(is_employer)
                                {
                                    Intent intent = new Intent(PsychometricActivity.this, EmployerMainActivity.class);
                                    intent.putExtra("menu","dashboard");
                                    startActivity(intent);
                                }
                                else
                                {
                                    Intent intent = new Intent(PsychometricActivity.this, MainActivity.class);
                                    intent.putExtra("menu","dashboard");
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

    }




    /*Show pdf file in available pdf viewer*/
    public void showPdf()
    {
        try
        {
            File file = new File(Environment.getExternalStorageDirectory()+"/BsoPdf/"+userImage);
            PackageManager packageManager = getPackageManager();
            Intent testIntent = new Intent(Intent.ACTION_VIEW);
            testIntent.setType("application/pdf");
            List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
/*check is file exist or not*/

    public boolean isFileExist()
    {
        File file = null;
        try
        {
            file = new File(Environment.getExternalStorageDirectory()+"/BsoPdf/"+userImage);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return  file.exists();
    }



}
