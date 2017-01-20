package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apexmediatechnologies.apexmedia.ChatActivity;
import com.apexmediatechnologies.apexmedia.JobProposals;
import com.apexmediatechnologies.apexmedia.MyJobProposalDetailActivity;
import com.apexmediatechnologies.apexmedia.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
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


public class EmployerJobProposalsAdapter extends RecyclerView.Adapter<EmployerJobProposalsAdapter.CustomViewHolder> {
    private List<JobProposals> feedList;
    private Context mContext;
    private String ischild="";
    private PopupWindow feedMenuPW;
    private View popupView;
    private LayoutInflater inflater;
    private String siteUserID;
    private Utility utility;
    private String strAwardJob = Application_Constants.Main_URL+"keyword=award_job";
    private boolean is_awarded;


    public EmployerJobProposalsAdapter(Context context, List<JobProposals> feedList,boolean is_awarded) {
        this.feedList = feedList;
        this.mContext = context;
        this.is_awarded=is_awarded;

        siteUserID = Shared_Preferences_Class.readString(mContext, Shared_Preferences_Class.USER_ID, "");
        utility = new Utility(mContext);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       /* View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_contact_row_new, null);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);*/


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_employer_job_proposals_row, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        final CustomViewHolder viewHolder = new CustomViewHolder(rootView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final JobProposals jobFeeds = feedList.get(i);
        // position = i;


        String strImage = jobFeeds.getUserImage();
            //Download image using picasso library
            Picasso.with(mContext).load(jobFeeds.getUserImage())
                    .into(customViewHolder.img_user);




        if(is_awarded)
        {
            customViewHolder.tv_award.setVisibility(View.INVISIBLE);
            customViewHolder.tv_chat.setVisibility(View.INVISIBLE);

        }

        //Setting text view title
        customViewHolder.tv_job_desc.setText(jobFeeds.getDescription());
        customViewHolder.tv_user_name.setText(jobFeeds.getUserName());
        customViewHolder.tv_time.setText(jobFeeds.getCreatedAt());
        customViewHolder.rating_user.setRating(Float.parseFloat(jobFeeds.getRating()));

        customViewHolder.tv_job_desc.setTag(customViewHolder);
        customViewHolder.tv_user_name.setTag(customViewHolder);
        customViewHolder.img_user.setTag(customViewHolder);
        customViewHolder.rating_user.setTag(customViewHolder);
        customViewHolder.lay_job_feed.setTag(customViewHolder);
        customViewHolder.tv_time.setTag(customViewHolder);
        customViewHolder.tv_chat.setTag(customViewHolder);
        customViewHolder.tv_award.setTag(customViewHolder);


        customViewHolder.lay_job_feed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                JobProposals jobFeeds = feedList.get(position);



                /*String str_like_count = jobFeeds.getTokLike();
                String str_is_like = jobFeeds.getIsTokLike();
                String str_tok_id = jobFeeds.getTokId();
                int int_tok_like = Integer.parseInt(str_like_count);*/

                Intent intent = new Intent(mContext, MyJobProposalDetailActivity.class);
                intent.putExtra("porposal_id",jobFeeds.getProposalId());
                mContext.startActivity(intent);
            }
        });




        customViewHolder.tv_award.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                JobProposals jobFeeds = feedList.get(position);

                jobFeeds.setAwarded("1");
                notifyDataSetChanged();

                awardJob(siteUserID,jobFeeds.getJobId(),jobFeeds.getProposalId());

                /*String str_like_count = jobFeeds.getTokLike();
                String str_is_like = jobFeeds.getIsTokLike();
                String str_tok_id = jobFeeds.getTokId();
                int int_tok_like = Integer.parseInt(str_like_count);*/

                /*Intent intent = new Intent(mContext, JobDetailProposalActivity.class);
                intent.putExtra("jobId",jobFeeds.getJobId());
                mContext.startActivity(intent);*/
            }
        });

        customViewHolder.tv_chat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                JobProposals jobFeeds = feedList.get(position);

                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("jobId",jobFeeds.getJobId());
                intent.putExtra("fromUserId",jobFeeds.getContractorId());
                intent.putExtra("project",jobFeeds.getUserName());
                mContext.startActivity(intent);

                /*String str_like_count = jobFeeds.getTokLike();
                String str_is_like = jobFeeds.getIsTokLike();
                String str_tok_id = jobFeeds.getTokId();
                int int_tok_like = Integer.parseInt(str_like_count);*/

                /*Intent intent = new Intent(mContext, JobDetailProposalActivity.class);
                intent.putExtra("jobId",jobFeeds.getJobId());
                mContext.startActivity(intent);*/
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

         TextView tv_job_desc,tv_time,
                 tv_user_name,tv_chat,tv_award;
        LinearLayout lay_job_feed;
        ImageView img_user;
        RatingBar rating_user;


        public CustomViewHolder(View view)
        {
            super(view);
            this.tv_job_desc = (TextView) view.findViewById(R.id.tv_job_desc);
            this.tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
            this.img_user = (ImageView) view.findViewById(R.id.img_user);
            this.rating_user= (RatingBar) view.findViewById(R.id.rating_user);
            this.lay_job_feed = (LinearLayout) view.findViewById(R.id.lay_job_feed);
            this.tv_time = (TextView) view.findViewById(R.id.tv_time);
            this.tv_chat = (TextView) view.findViewById(R.id.tv_chat);
            this.tv_award = (TextView) view.findViewById(R.id.tv_award);


        }
    }



    private void awardJob(String empId,String job_id,String proposal_id)
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();
            params.put("empId", empId);
            params.put("job_id", job_id);
            params.put("proposal_id", proposal_id);

            ServiceHandler serviceHandler = new ServiceHandler(mContext);
            final ArrayList<JobProposals> feedsArrayList = new ArrayList<JobProposals>();

            serviceHandler.registerUser(params, strAwardJob, new ServiceHandler.VolleyCallback() {
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