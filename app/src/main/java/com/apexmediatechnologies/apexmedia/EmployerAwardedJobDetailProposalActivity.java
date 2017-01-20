package com.apexmediatechnologies.apexmedia;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import services.Utility;

public class EmployerAwardedJobDetailProposalActivity extends AppCompatActivity
{
    private Utility utility;
    private TextView tv_action_title;
    private String jobId,proposalId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_job_details_proposals);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        utility = new Utility(getApplicationContext());

        tv_action_title = (TextView) toolbar.findViewById(R.id.tv_action_title);
        tv_action_title.setText("Job Details");
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

        Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {
            jobId = intent.getStringExtra("jobId");
            proposalId = intent.getStringExtra("proposalId");

        }



        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Job Details"));
        tabLayout.addTab(tabLayout.newTab().setText("Job Proposals"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        // tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        final EmployerAwardedJobDetailsProposalTabAdapter adapter = new EmployerAwardedJobDetailsProposalTabAdapter(fm, tabLayout.getTabCount(),jobId,proposalId);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0)
                {

                    /*if (utility.checkInternet())
                    {
                        // ((FragmentScoreCard)adapter.getItem(0)).new GetScores(employe_code,getActivity()).execute();
                    } else {

                        Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                    }*/

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }









    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);




    }





}

