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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import static com.apexmediatechnologies.apexmedia.R.id.edt_address;
import static com.apexmediatechnologies.apexmedia.R.id.edt_first_name4;
import static com.apexmediatechnologies.apexmedia.R.id.edt_forein_pass_no;
import static com.apexmediatechnologies.apexmedia.R.id.edt_last_name4;
import static com.apexmediatechnologies.apexmedia.R.id.edt_listb_exp_date;
import static com.apexmediatechnologies.apexmedia.R.id.edt_name_signer;
import static com.apexmediatechnologies.apexmedia.R.id.edt_ref_no;

public class TaxUsActivity extends AppCompatActivity
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
    File signature1,signature2,signature3,signature4;
    ImageView img_sinature_1,img_sinature3;
    private EditText edt_last_name1,edt_first_name1,edt_middel_name1,edt_other_name,edt_address1,edt_apt_no,
            edt_zip_code,edt_fti_no,edt_dob,edt_social_sec_no,edt_email,edt_phone,
            edt_rdo_btn3,edt_rdo_btn4,edt_alien_reg_no,edt_admission_no,edt_forein_pass_no,edt_country_of_issuance,
            edt_date1,edt_date2,edt_last_name2,edt_first_name2,edt_address2,edt_city2,edt_zip_code2,edt_sec2_full_name,
            edt_lista_doc_title1,edt_lista_issuing_athority1,edt_lista_doc_no1,edt_lista_exp_date1, edt_lista_doc_title2,
            edt_lista_issuing_athority2,edt_lista_doc_no2,edt_lista_exp_date2, edt_lista_doc_title3,edt_lista_issuing_athority3,
            edt_lista_doc_no3,edt_lista_exp_date3,edt_listb_doc_title, edt_listb_issuing_athority,edt_listb_doc_no,
            edt_listb_exp_date,edt_listc_doc_title,edt_listc_issuing_athority,
            edt_listc_doc_no,edt_listc_exp_date,edt_emp_date,edt_date_2,edt_title_emp_autho,edt_last_name3,edt_first_name3,
            edt_emp_busi_name,edt_emp_busi_add,edt_city3,edt_zip_code4,edt_last_name4,edt_first_name4,edt_middel_name4,
            edt_date_of_rehire,edt_c_doc_title,edt_c_doc_no,edt_c_exp_date,edt_date3,edt_emp_otho_name;
    private AutoCompleteTextView auto_sec1_city,auto_sec1_state,auto_state2,auto_state3,auto_state,auto_city1,auto_state1;
    private Button btn_choose_file1,btn_choose_file2,btn_choose_file_1,btn_choose_file3,btn_submit;
    private RadioGroup rdo_grp;
    private RadioButton rdo_btn1,rdo_btn2,rdo_btn3,rdo_btn4;

    // parametres
    String userId,first_name,last_name,middle_name,other_name,address,apt_number,city,state,zipcode,dob,us_ssn,email,
           phone,perjury_penalty,reg_or_uscis_number,alien_authorized_exp_date,alien_reg_or_uscis_number,admission_number,
        foreign_passport_number,country_issuance,signature_date,preparer_signature_date,preparer_first_name,preparer_last_name,
    preparer_address,preparer_city,preparer_state,preparer_zipcode,employee_fullname,list_a_doc_title_1,list_a_authority_1,
    list_a_doc_number_1,list_a_exp_date_1,list_a_doc_title_2,list_a_authority_2,list_a_doc_number_2,list_a_exp_date_2,
    list_a_doc_title_3,list_a_authority_3,list_a_doc_number_3,list_a_exp_date_3,list_b_doc_title,list_b_authority,list_b_doc_number,
    list_b_exp_date,list_c_doc_title,list_c_authority,list_c_doc_number,list_c_exp_date,first_day_employment_date,
            employer_signature_date,employer_title,employer_first_name,employer_last_name,employer_business_name,
            employer_business_address,employer_business_city,employer_business_state,employer_business_zipcode,section_3_first_name,
    section_3_last_name,section_3_middle_name,section_3_rehire_date,section_3_doc_title,section_3_doc_number,section_3_exp_date,
    section_3_sign_date,section_3_employer_name;
    boolean is_dob = false,is_image=false;

    String[] languages={"Android ","java","IOS","SQL","JDBC","Web services"};
    List<String> countryIdList,countryNameList,stateIdList,stateNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_us);

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
        //toolbar.setLogo(R.drawable.actionbar_32);


        edt_last_name1 = (EditText) findViewById(R.id.edt_last_name1);
        edt_first_name1 = (EditText)findViewById(R.id.edt_first_name1);
        edt_middel_name1 = (EditText) findViewById(R.id.edt_middel_name1);
        edt_other_name = (EditText) findViewById(R.id.edt_other_name);
        edt_address1 = (EditText) findViewById(R.id.edt_address1);
        edt_apt_no= (EditText)findViewById(R.id.edt_apt_no);
        auto_sec1_city= (AutoCompleteTextView)findViewById(R.id.auto_sec1_city);
        auto_sec1_state= (AutoCompleteTextView) findViewById(R.id.auto_sec1_state);
        edt_zip_code= (EditText)findViewById(R.id.edt_zip_code);
        auto_city1= (AutoCompleteTextView)findViewById(R.id.auto_city1);
        edt_dob= (EditText) findViewById(R.id.edt_dob);
        edt_social_sec_no= (EditText)findViewById(R.id.edt_social_sec_no);
        edt_email= (EditText) findViewById(R.id.edt_email);
        edt_phone= (EditText)findViewById(R.id.edt_phone);

        rdo_grp = (RadioGroup) findViewById(R.id.rdo_grp);
        rdo_btn1 = (RadioButton) findViewById(R.id.rdo_btn1);
        rdo_btn2 = (RadioButton) findViewById(R.id.rdo_btn2);
        rdo_btn3 = (RadioButton) findViewById(R.id.rdo_btn3);
        edt_rdo_btn3= (EditText)findViewById(R.id.edt_rdo_btn3);
        rdo_btn4 = (RadioButton) findViewById(R.id.rdo_btn4);
        edt_rdo_btn4= (EditText)findViewById(R.id.edt_rdo_btn4);
        edt_alien_reg_no = (EditText) findViewById(R.id.edt_alien_reg_no);
        edt_admission_no = (EditText) findViewById(R.id.edt_admission_no);
        edt_forein_pass_no = (EditText) findViewById(R.id.edt_forein_pass_no);
        edt_country_of_issuance = (EditText) findViewById(R.id.edt_country_of_issuance);
        btn_choose_file1 = (Button) findViewById(R.id.btn_choose_file1);
        edt_date1 = (EditText) findViewById(R.id.edt_date1);
        btn_choose_file2 = (Button) findViewById(R.id.btn_choose_file2);
        edt_date2 = (EditText)findViewById(R.id.edt_date2);
        edt_last_name2= (EditText)findViewById(R.id.edt_last_name2);
        edt_first_name2 = (EditText) findViewById(R.id.edt_first_name2);
        edt_address2 = (EditText) findViewById(R.id.edt_address2);
        edt_city2 = (EditText) findViewById(R.id.edt_city2);
        auto_state2 = (AutoCompleteTextView) findViewById(R.id.auto_state2);
        edt_zip_code2 = (EditText) findViewById(R.id.edt_zip_code2);
        edt_sec2_full_name = (EditText) findViewById(R.id.edt_sec2_full_name);

        edt_lista_doc_title1 = (EditText) findViewById(R.id.edt_lista_doc_title1);
        edt_lista_issuing_athority1 = (EditText) findViewById(R.id.edt_lista_issuing_athority1);
        edt_lista_doc_no1 = (EditText) findViewById(R.id.edt_lista_doc_no1);
        edt_lista_exp_date1 = (EditText) findViewById(R.id.edt_lista_exp_date1);

        edt_lista_doc_title2 = (EditText) findViewById(R.id.edt_lista_doc_title2);
        edt_lista_issuing_athority2 = (EditText) findViewById(R.id.edt_lista_issuing_athority2);
        edt_lista_doc_no2 = (EditText) findViewById(R.id.edt_lista_doc_no2);
        edt_lista_exp_date2 = (EditText) findViewById(R.id.edt_lista_exp_date2);

        edt_lista_doc_title3 = (EditText) findViewById(R.id.edt_lista_doc_title3);
        edt_lista_issuing_athority3 = (EditText) findViewById(R.id.edt_lista_issuing_athority3);
        edt_lista_doc_no3 = (EditText) findViewById(R.id.edt_lista_doc_no3);
        edt_lista_exp_date3 = (EditText) findViewById(R.id.edt_lista_exp_date3);

        edt_listb_doc_title = (EditText) findViewById(R.id.edt_listb_doc_title);
        edt_listb_issuing_athority = (EditText) findViewById(R.id.edt_listb_issuing_athority);
        edt_listb_doc_no = (EditText) findViewById(R.id.edt_listb_doc_no);
        edt_listb_exp_date = (EditText) findViewById(R.id.edt_listb_exp_date);
        edt_listc_doc_title = (EditText) findViewById(R.id.edt_listc_doc_title);
        edt_listc_issuing_athority = (EditText) findViewById(R.id.edt_listc_issuing_athority);
        edt_listc_doc_no = (EditText) findViewById(R.id.edt_listc_doc_no);
        edt_listc_exp_date = (EditText) findViewById(R.id.edt_listc_exp_date);
        edt_emp_date = (EditText) findViewById(R.id.edt_emp_date);
        btn_choose_file_1 = (Button) findViewById(R.id.btn_choose_file_1);
        img_sinature_1 = (ImageView) findViewById(R.id.img_sinature_1);
        edt_date_2 = (EditText) findViewById(R.id.edt_date_2);
        edt_title_emp_autho  = (EditText) findViewById(R.id.edt_title_emp_autho);
        edt_last_name3 = (EditText) findViewById(R.id.edt_last_name3);
        edt_first_name3 = (EditText) findViewById(R.id.edt_first_name3);
        edt_emp_busi_name = (EditText) findViewById(R.id.edt_emp_busi_name);
        edt_emp_busi_add  = (EditText) findViewById(R.id.edt_emp_busi_add);
        edt_city3 = (EditText) findViewById(R.id.edt_city3);
        auto_state3 = (AutoCompleteTextView) findViewById(R.id.auto_state3);
        edt_zip_code4 = (EditText) findViewById(R.id.edt_zip_code4);
        edt_last_name4 = (EditText) findViewById(R.id.edt_last_name4);
        edt_first_name4 = (EditText) findViewById(R.id.edt_first_name4);
        edt_middel_name4 = (EditText) findViewById(R.id.edt_middel_name4);
        edt_date_of_rehire = (EditText) findViewById(R.id.edt_date_of_rehire);
        edt_c_doc_title = (EditText) findViewById(R.id.edt_c_doc_title);
        edt_c_doc_no = (EditText) findViewById(R.id.edt_c_doc_no);
        edt_c_exp_date = (EditText) findViewById(R.id.edt_c_exp_date);
        btn_choose_file3 = (Button) findViewById(R.id.btn_choose_file3);
        img_sinature3 = (ImageView) findViewById(R.id.img_sinature3);
        edt_date3 = (EditText) findViewById(R.id.edt_date3);
        edt_emp_otho_name = (EditText) findViewById(R.id.edt_emp_otho_name);
        btn_submit = (Button) findViewById(R.id.btn_submit);

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

                Intent intent = new Intent(TaxUsActivity.this,MainActivity.class);
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

        edt_date1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                //showDatePicker();

            }
        });

        // get country list
       // getCountry();
        // get state list
        //getState();




        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        edt_date1.setOnClickListener(new View.OnClickListener()
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

        btn_choose_file1.setOnClickListener(new View.OnClickListener()
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

              /*  first_name = edt_first_name1.getText().toString();
                last_name= edt_last_name1.getText().toString();
                middle_name = edt_middel_name1.getText().toString();
                other_name = edt_other_name.getText().toString();
                address = edt_address1.getText().toString();
                apt_number = edt_apt_no.getText().toString();
                city = auto_city1.getText().toString();
                state = auto_state1.getText().toString();
                zipcode= edt_zip_code.getText().toString();
                dob = edt_dob.getText().toString();
                us_ssn= edt_social_sec_no.getText().toString();
                email = edt_email.getText().toString();
                phone= edt_phone.getText().toString();

                int selectedId = rdo_grp.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);

                perjury_penalty= radioButton.getText().toString();

                reg_or_uscis_number= edt_rdo_btn3.getText().toString();
                alien_authorized_exp_date= edt_rdo_btn4.getText().toString();
                alien_reg_or_uscis_number= edt_alien_reg_no.getText().toString();
                admission_number= edt_admission_no.getText().toString();
                foreign_passport_number= edt_forein_pass_no.getText().toString();
                country_issuance= edt_country_of_issuance.getText().toString();
                signature_date = edt_date1.getText().toString();
                preparer_signature_date= edt_date2.getText().toString();
                preparer_first_name= edt_first_name2.getText().toString();
                preparer_last_name = edt_last_name2.getText().toString();
                preparer_address= edt_address2.getText().toString();
                preparer_city= edt_city2.getText().toString();
                preparer_state= auto_state2.getText().toString();
                preparer_zipcode = edt_zip_code2.getText().toString();
                employee_fullname = edt_sec2_full_name.getText().toString();
                list_a_doc_title_1 = edt_lista_doc_title1.getText().toString();
                list_a_authority_1= edt_lista_issuing_athority1.getText().toString();
                list_a_doc_number_1= edt_lista_doc_no1.getText().toString();
                list_a_exp_date_1= edt_lista_exp_date1.getText().toString();
                list_a_doc_title_2 = edt_lista_doc_title2.getText().toString();
                list_a_authority_2 = edt_lista_issuing_athority2.getText().toString();
                list_a_doc_number_2 = edt_lista_doc_no2.getText().toString();
                list_a_exp_date_2 = edt_lista_exp_date2.getText().toString();
                list_a_doc_title_3 = edt_lista_doc_title3.getText().toString();
                list_a_authority_3 = edt_lista_issuing_athority3.getText().toString();
                list_a_doc_number_3 = edt_lista_doc_no3.getText().toString();
                list_a_exp_date_3= edt_lista_exp_date3.getText().toString();

                list_b_doc_title = edt_listb_doc_title.getText().toString();
                list_b_authority= edt_listb_issuing_athority.getText().toString();
                list_b_doc_number= edt_listb_doc_no.getText().toString();
                list_b_exp_date = edt_listb_exp_date.getText().toString();
                list_c_doc_title= edt_listc_doc_title.getText().toString();
                list_c_authority= edt_listc_issuing_athority.getText().toString();
                list_c_doc_number= edt_listc_doc_no.getText().toString();
                list_c_exp_date= edt_listc_exp_date.getText().toString();
                first_day_employment_date = edt_emp_date.getText().toString();
                employer_signature_date= edt_date_2.getText().toString();
                employer_title=edt_title_emp_autho.getText().toString();
                employer_first_name=edt_first_name3.getText().toString();
                employer_last_name =edt_last_name3.getText().toString();
                employer_business_name =edt_emp_busi_name.getText().toString();
                employer_business_address =edt_emp_busi_add.getText().toString();
                employer_business_city=edt_city3.getText().toString();
                employer_business_state=auto_state3.getText().toString();
                employer_business_zipcode = edt_zip_code4.getText().toString();
                section_3_first_name = edt_first_name4.getText().toString();
                section_3_last_name = edt_last_name4.getText().toString();
                section_3_middle_name = edt_middel_name4.getText().toString();
                section_3_rehire_date = edt_date_of_rehire.getText().toString();
                section_3_doc_title = edt_c_doc_title.getText().toString();
                section_3_doc_number = edt_c_doc_no.getText().toString();
                section_3_exp_date = edt_c_exp_date.getText().toString();
                section_3_sign_date = edt_date3.getText().toString();
                section_3_employer_name = edt_emp_otho_name.getText().toString();*/

               /* if(str_name.equals("")||str_country_citizen.equals("")||str_address.equals("")||str_city.equals("")||str_state.equals("")||str_country.equals("")||
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
                    else if(str_email.equals(""))
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
                    }
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

                }*/

                Toast.makeText(TaxUsActivity.this, "form submitted successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(TaxUsActivity.this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("menu", "dashboard");
                startActivity(i);
                finish();

               // new TaxForm().execute();

            }
        });




        /*auto_state1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                auto_state1.showDropDown();

            }
        });

        auto_state1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                auto_state1.showDropDown();

            }
        });*/



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
                edt_date1.setText(selectedYear + " - " + (selectedMonth + 1) + " - "
                        + selectedDay);
            }




        }
    };

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(TaxUsActivity.this,MainActivity.class);
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

                img_sinature_1.setVisibility(View.VISIBLE);

                img_sinature_1.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);


                byteArray= stream.toByteArray();
                // String  imageString= Base64.encode(byteArray);

                encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                // String url_image = str_change_profile_url+userId;
                // new post_Async(url_image).execute();

                // btn_submit_photo.setVisibility(View.VISIBLE);


                    BitmapDrawable drawable = (BitmapDrawable) img_sinature_1.getDrawable();
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
                    signature1 = new File(filesDir, "user_image.jpg");

                    OutputStream os;
                    try {
                        os = new FileOutputStream(signature1);
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


            ServiceHandler serviceHandler = new ServiceHandler(TaxUsActivity.this);

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


                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TaxUsActivity.this,android.R.layout.simple_list_item_1,countryNameList);
                               /* auto_country.setAdapter(adapter);
                                auto_country1.setAdapter(adapter);
                                auto_country_citizen.setAdapter(adapter);

                                auto_country.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
                                auto_country1.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
                                auto_country_citizen.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);*/

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
            Toast.makeText(TaxUsActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

    }


    private void getState()
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();


            ServiceHandler serviceHandler = new ServiceHandler(TaxUsActivity.this);

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


                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TaxUsActivity.this,android.R.layout.simple_list_item_1,countryNameList);
                                auto_state.setAdapter(adapter);
                                auto_state1.setAdapter(adapter);
                                auto_state3.setAdapter(adapter);

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
            Toast.makeText(TaxUsActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();
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

            dialogTax = new ProgressDialog(TaxUsActivity.this,R.style.MyTheme);
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
                multipart.addFormField("user_id", userId);
                multipart.addFormField("first_name", first_name);
                multipart.addFormField("last_name", last_name);
                multipart.addFormField("middle_name", middle_name);
                multipart.addFormField("other_name", other_name);
                multipart.addFormField("address", address);
                multipart.addFormField("apt_number", apt_number);
                multipart.addFormField("city", city);
                multipart.addFormField("state", state);
                multipart.addFormField("zipcode", zipcode);
                multipart.addFormField("dob", dob);
                multipart.addFormField("us_ssn", us_ssn);
                multipart.addFormField("email", email);
                multipart.addFormField("phone", phone);
                multipart.addFormField("perjury_penalty", perjury_penalty);
                multipart.addFormField("reg_or_uscis_number", reg_or_uscis_number);
                multipart.addFormField("alien_authorized_exp_date", alien_authorized_exp_date);
                multipart.addFormField("alien_reg_or_uscis_number", alien_reg_or_uscis_number);
                multipart.addFormField("admission_number", admission_number);
                multipart.addFormField("foreign_passport_number", foreign_passport_number);
                multipart.addFormField("country_issuance", country_issuance);
                multipart.addFormField("signature_date", signature_date);
                multipart.addFormField("preparer_signature_date", preparer_signature_date);
                multipart.addFormField("preparer_first_name", preparer_first_name);
                multipart.addFormField("preparer_last_name", preparer_last_name);
                multipart.addFormField("preparer_address", preparer_address);
                multipart.addFormField("preparer_city", preparer_city);
                multipart.addFormField("preparer_state", preparer_state);
                multipart.addFormField("preparer_zipcode", preparer_zipcode);
                multipart.addFormField("employee_fullname", employee_fullname);
                multipart.addFormField("list_a_doc_title_1", list_a_doc_title_1);
                multipart.addFormField("list_a_authority_1", list_a_authority_1);
                multipart.addFormField("list_a_doc_number_1", list_a_doc_number_1);
                multipart.addFormField("list_a_exp_date_1", list_a_exp_date_1);
                multipart.addFormField("list_a_doc_title_2", list_a_doc_title_2);
                multipart.addFormField("list_a_authority_2", list_a_authority_2);
                multipart.addFormField("list_a_doc_number_2", list_a_doc_number_2);
                multipart.addFormField("list_a_exp_date_2", list_a_exp_date_2);
                multipart.addFormField("list_a_doc_title_3", list_a_doc_title_3);
                multipart.addFormField("list_a_authority_3", list_a_authority_3);
                multipart.addFormField("list_a_doc_number_3", list_a_doc_number_3);
                multipart.addFormField("list_a_exp_date_3", list_a_exp_date_3);
                multipart.addFormField("list_b_doc_title", list_b_doc_title);
                multipart.addFormField("list_b_authority", list_b_authority);
                multipart.addFormField("list_b_doc_number", list_b_doc_number);
                multipart.addFormField("list_b_exp_date", list_b_exp_date);
                multipart.addFormField("list_c_doc_title", list_c_doc_title);
                multipart.addFormField("list_c_authority", list_c_authority);
                multipart.addFormField("list_c_doc_number", list_c_doc_number);
                multipart.addFormField("list_c_exp_date", list_c_exp_date);
                multipart.addFormField("first_day_employment_date", first_day_employment_date);
                multipart.addFormField("employer_signature_date", employer_signature_date);
                multipart.addFormField("employer_title", employer_title);
                multipart.addFormField("employer_first_name", employer_first_name);
                multipart.addFormField("employer_last_name", employer_last_name);
                multipart.addFormField("employer_business_name", employer_business_name);
                multipart.addFormField("employer_business_address", employer_business_address);
                multipart.addFormField("employer_business_city", employer_business_city);
                multipart.addFormField("employer_business_state", employer_business_state);
                multipart.addFormField("employer_business_zipcode", employer_business_zipcode);
                multipart.addFormField("section_3_first_name", section_3_first_name);
                multipart.addFormField("section_3_last_name", section_3_last_name);
                multipart.addFormField("section_3_middle_name", section_3_middle_name);
                multipart.addFormField("section_3_rehire_date", section_3_rehire_date);
                multipart.addFormField("section_3_doc_title", section_3_doc_title);
                multipart.addFormField("section_3_doc_number", section_3_doc_number);

                multipart.addFormField("section_3_exp_date", section_3_exp_date);
                multipart.addFormField("section_3_sign_date", section_3_sign_date);
                multipart.addFormField("section_3_employer_name", section_3_employer_name);


                if(is_image)
                {
                    multipart.addFilePart("signature1", signature1);
                    multipart.addFilePart("signature2", signature2);
                    multipart.addFilePart("signature3", signature3);
                    multipart.addFilePart("signature4", signature4);
                }


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
                Intent i = new Intent(TaxUsActivity.this,MainActivity.class);
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
