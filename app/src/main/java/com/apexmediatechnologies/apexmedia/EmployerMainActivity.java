package com.apexmediatechnologies.apexmedia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import services.Shared_Preferences_Class;

public class EmployerMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout lay_my_job,lay_post_jobs,lay_home,lay_my_projects,lay_profile,lay_payments,lay_settings,lay_faq,
            lay_contact_support,lay_message,lay_switch_ac,lay_change_pass,lay_logout,lay_bank_account,lay_job_awarded,
            lay_job_request,lay_send_audio,lay_notification;
    public NavigationView navigationView;
    public  DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //toolbar.setLogo(R.drawable.actionbar_32);


      //  toolbar.setTitle("Dashboard");
        // status bar color change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        // initilizing variables
        init();


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {
            if(intent.getStringExtra("menu")!=null)
            {
                String str_menu = intent.getStringExtra("menu");
                if(str_menu.equals("dashboard"))
                {
                    FragmentEmployerDashboard fragment = new FragmentEmployerDashboard();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment);//.addToBackStack("Dashboard");
                    fragmentTransaction.commit();
                }
                else if(str_menu.equals("jobFeed"))
                {
                    FragmentEmployerJobFeeds fragment = new FragmentEmployerJobFeeds();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("EmployerJobFeeds");
                    fragmentTransaction.commit();
                }
                else if(str_menu.equals("profile"))
                {
                    FragmentProfile fragment = new FragmentProfile();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Profile");
                    fragmentTransaction.commit();
                }
                else if(str_menu.equals("BankAccount"))
                {
                    FragmentBankAccount fragment = new FragmentBankAccount();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("BankAccount");
                    fragmentTransaction.commit();
                }

                else if(str_menu.equals("Payment"))
                {
                    FragmentEmployerPayments fragment = new FragmentEmployerPayments();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Payments");
                    fragmentTransaction.commit();
                }
            }
        }
        else
        {
            FragmentEmployerDashboard fragment = new FragmentEmployerDashboard();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);//.addToBackStack("Dashboard");
            fragmentTransaction.commit();
        }



        lay_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog builder = new AlertDialog.Builder(EmployerMainActivity.this).create();

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View builder_view = inflater.inflate(R.layout.activity_logout_dialog, null);
                builder.setView(builder_view);
               // builder.setCancelable(false);

                // builder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


                TextView tv_confirm = (TextView) builder_view.findViewById(R.id.tv_confirm);
                TextView tv_cancel = (TextView) builder_view.findViewById(R.id.tv_cancel);


                tv_confirm.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        try {


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
                            Shared_Preferences_Class.getEditor(getApplicationContext()).remove(Shared_Preferences_Class.IS_PSYCHOMETRIC).commit();


                            Intent intent = new Intent(EmployerMainActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            builder.cancel();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                tv_cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        try {

                            //dialog.dismiss();
                            builder.cancel();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                //dialog.show();
                builder.show();
            }
        });


        lay_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                FragmentEmployerDashboard fragment = new FragmentEmployerDashboard();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Dashboard");
                fragmentTransaction.commit();
            }
        });



        lay_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                FragmentProfile fragment = new FragmentProfile();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Profile");
                fragmentTransaction.commit();
            }
        });

        lay_payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
               /* FragmentPayments fragment = new FragmentPayments();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Payments");
                fragmentTransaction.commit();*/
            }
        });
        lay_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                FragmentSetting fragment = new FragmentSetting();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Setting");
                fragmentTransaction.commit();
            }
        });

        lay_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                FragmentFaqs fragment = new FragmentFaqs();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("FAQS");
                fragmentTransaction.commit();
            }
        });

        lay_change_pass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                Intent intent = new Intent(EmployerMainActivity.this,ChangePassswordActivity.class);
                startActivity(intent);
            }
        });

        lay_contact_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                FragmentContactSupport fragment = new FragmentContactSupport();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("ContactSupport");
                fragmentTransaction.commit();
            }
        });

        lay_switch_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog builder = new AlertDialog.Builder(EmployerMainActivity.this).create();

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View builder_view = inflater.inflate(R.layout.activity_ac_switch_dialog, null);
                builder.setView(builder_view);
                // builder.setCancelable(false);

                // builder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


                TextView tv_confirm = (TextView) builder_view.findViewById(R.id.tv_confirm);
                TextView tv_cancel = (TextView) builder_view.findViewById(R.id.tv_cancel);


                tv_confirm.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        try {
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
                            Shared_Preferences_Class.getEditor(getApplicationContext()).remove(Shared_Preferences_Class.IS_PSYCHOMETRIC).commit();

                            Intent intent = new Intent(EmployerMainActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            builder.cancel();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                tv_cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        try {

                            //dialog.dismiss();
                            builder.cancel();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                //dialog.show();
                builder.show();
            }
        });

        lay_bank_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                FragmentBankAccount fragment = new FragmentBankAccount();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("BankAccount");
                fragmentTransaction.commit();
            }
        });
        lay_my_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                FragmentEmployerJobFeeds fragment = new FragmentEmployerJobFeeds();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("EmployerJobFeeds");
                fragmentTransaction.commit();
            }
        });
        lay_job_awarded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                FragmentEmployerAwardedJobFeeds fragment = new FragmentEmployerAwardedJobFeeds();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Awarded Jobs");
                fragmentTransaction.commit();
            }
        });

        lay_payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                FragmentEmployerPayments fragment = new FragmentEmployerPayments();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Payments");
                fragmentTransaction.commit();
            }
        });
        lay_post_jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                Intent intent = new Intent(EmployerMainActivity.this,PostJobActivity.class);
                startActivity(intent);
            }
        });

        lay_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                FragmentMessages fragment = new FragmentMessages();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Message");
                fragmentTransaction.commit();
            }
        });

        lay_job_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                FragmentEmployerJobRequest fragment = new FragmentEmployerJobRequest();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Job Request");
                fragmentTransaction.commit();
            }
        });
        lay_send_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                FragmentSendAudio fragment = new FragmentSendAudio();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Send Audio");
                fragmentTransaction.commit();
            }
        });
        lay_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
              Intent intent = new Intent(EmployerMainActivity.this,NotificationEmployerActivity.class);
                startActivity(intent);
            }
        });


    }

    public void init()
    {
        lay_my_job = (LinearLayout) findViewById(R.id.lay_my_job);
        lay_post_jobs= (LinearLayout) findViewById(R.id.lay_post_jobs);
        lay_home = (LinearLayout) findViewById(R.id.lay_home);
        lay_profile = (LinearLayout) findViewById(R.id.lay_profile);
        lay_payments = (LinearLayout) findViewById(R.id.lay_payments);
        lay_settings  = (LinearLayout) findViewById(R.id.lay_settings);
        lay_faq = (LinearLayout) findViewById(R.id.lay_faq);
        lay_contact_support = (LinearLayout) findViewById(R.id.lay_contact_support);
        lay_message = (LinearLayout) findViewById(R.id.lay_message);
        lay_switch_ac = (LinearLayout) findViewById(R.id.lay_switch_ac);
        lay_change_pass = (LinearLayout) findViewById(R.id.lay_change_pass);
        lay_logout = (LinearLayout) findViewById(R.id.lay_logout);
        lay_bank_account = (LinearLayout) findViewById(R.id.lay_bank_account);
        lay_job_awarded = (LinearLayout) findViewById(R.id.lay_job_awarded);
        lay_job_request = (LinearLayout) findViewById(R.id.lay_job_request);
        lay_send_audio = (LinearLayout) findViewById(R.id.lay_send_audio);
        lay_notification = (LinearLayout) findViewById(R.id.lay_notification);


    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit)
        {
            Intent intent = new Intent(EmployerMainActivity.this,EditProfileActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.action_edit_ac)
        {
            Intent intent = new Intent(EmployerMainActivity.this,AddBankAccountActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
