package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.apexmediatechnologies.apexmedia.FindTalent;
import com.apexmediatechnologies.apexmedia.FindTalentActivity;
import com.apexmediatechnologies.apexmedia.JobProposalActivity;
import com.apexmediatechnologies.apexmedia.JobProposals;
import com.apexmediatechnologies.apexmedia.JobRequest;
import com.apexmediatechnologies.apexmedia.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;


public class FindTalentContractorAdapter extends RecyclerView.Adapter<FindTalentContractorAdapter.CustomViewHolder> {
    private List<FindTalent> feedList;
    private Context mContext;
    private String ischild="";
    private PopupWindow feedMenuPW;
    private View popupView;
    private LayoutInflater inflater;
    private String siteUserID;
    private Utility utility;
    private String strDeclineJob = Application_Constants.Main_URL+"keyword=contractor_decline_request";


    public FindTalentContractorAdapter(Context context, List<FindTalent> feedList) {
        this.feedList = feedList;
        this.mContext = context;

        siteUserID = Shared_Preferences_Class.readString(mContext, Shared_Preferences_Class.USER_ID, "");
        utility = new Utility(mContext);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       /* View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_contact_row_new, null);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);*/


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_find_talent_contractor__row, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        final CustomViewHolder viewHolder = new CustomViewHolder(rootView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final FindTalent jobFeeds = feedList.get(i);
        // position = i;


        String strImage = jobFeeds.getContractor_pic();
            //Download image using picasso library
            Picasso.with(mContext).load(jobFeeds.getContractor_pic())
                    .into(customViewHolder.img_user);


        //Setting text view title
        customViewHolder.tv_user_name.setText(jobFeeds.getContractor_name());
        customViewHolder.tv_posted.setText(jobFeeds.getContractor_country());
        customViewHolder.tv_contactor_info.setText(jobFeeds.getOverView());
        customViewHolder.tv_desc.setText(jobFeeds.getStr_skills());

        customViewHolder.tv_contactor_info.setTag(customViewHolder);
        customViewHolder.tv_user_name.setTag(customViewHolder);
        customViewHolder.img_user.setTag(customViewHolder);
        customViewHolder.tv_posted.setTag(customViewHolder);
        customViewHolder.tv_desc.setTag(customViewHolder);
        customViewHolder.lay_job_feed.setTag(customViewHolder);
        customViewHolder.btn_hire_now.setTag(customViewHolder);



        customViewHolder.btn_hire_now.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                FindTalentContractorAdapter.CustomViewHolder holder = (FindTalentContractorAdapter.CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                FindTalent jobFeeds = feedList.get(position);

                /*String str_like_count = jobFeeds.getTokLike();
                String str_is_like = jobFeeds.getIsTokLike();
                String str_tok_id = jobFeeds.getTokId();
                int int_tok_like = Integer.parseInt(str_like_count);*/

                Intent intent = new Intent(mContext, FindTalentActivity.class);
                intent.putExtra("contractor_id",jobFeeds.getUserId());
                intent.putExtra("contractor_name",jobFeeds.getContractor_name());
                intent.putExtra("contractor_country",jobFeeds.getContractor_country());
                intent.putExtra("contractor_image",jobFeeds.getContractor_pic());
                mContext.startActivity(intent);

            }
        });




        View.OnClickListener clickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                CustomViewHolder holder = (CustomViewHolder) view.getTag();
                int position = holder.getPosition();

                //Products Products = ProductsList.get(position);
               // Toast.makeText(mContext, Products.getEmployeeName()+"="+ position, Toast.LENGTH_SHORT).show();
            }
        };
    }


    @Override
    public int getItemCount()
    {
        int list_size = feedList.size();

        return (null != feedList ? feedList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder
    {

         TextView tv_posted,tv_contactor_info,
                 tv_user_name,tv_desc;
        LinearLayout lay_job_feed;
        ImageView img_user;
        Button btn_hire_now;


        public CustomViewHolder(View view)
        {
            super(view);
            this.tv_posted = (TextView) view.findViewById(R.id.tv_posted);
            this.tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
            this.img_user = (ImageView) view.findViewById(R.id.img_user);
            this.lay_job_feed = (LinearLayout) view.findViewById(R.id.lay_job_feed);
            this.tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            this.tv_contactor_info = (TextView) view.findViewById(R.id.tv_contactor_info);

            this.btn_hire_now = (Button) view.findViewById(R.id.btn_hire_now);


        }
    }



    private void declineJob(String contractorId,String requestId )
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();
            params.put("contractorId", contractorId);
            params.put("requestId", requestId);

            ServiceHandler serviceHandler = new ServiceHandler(mContext);
            final ArrayList<JobProposals> feedsArrayList = new ArrayList<JobProposals>();

            serviceHandler.registerUser(params, strDeclineJob, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status, str_msg;
                    String jobId, rating, amount_per_hour, estimated_duration, work_type, posted_userId, contractor_first_name,
                            work_rate, start_type, user_country_name, hours_per_week, job_title, created_at, updated_on, job_description,
                            contractor_last_name, user_image, schedule_date;
                    try {
                        if (str_json != null) {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            if (str_status.equalsIgnoreCase("true"))
                            {

                                Toast.makeText(mContext, str_msg, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(mContext, str_msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

    }



}