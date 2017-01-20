package adapter;

import android.app.Activity;
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

import com.apexmediatechnologies.apexmedia.JobDetailActivity;
import com.apexmediatechnologies.apexmedia.JobDetailProposalActivity;
import com.apexmediatechnologies.apexmedia.JobDetailsProposalTabAdapter;
import com.apexmediatechnologies.apexmedia.JobFeeds;
import com.apexmediatechnologies.apexmedia.MainActivity;
import com.apexmediatechnologies.apexmedia.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import services.Application_Constants;
import services.Shared_Preferences_Class;
import services.Utility;


public class JobFeedsAdapter extends RecyclerView.Adapter<JobFeedsAdapter.CustomViewHolder> {
    private List<JobFeeds> feedList;
    private Context mContext;
    private String ischild="";
    private PopupWindow feedMenuPW;
    private View popupView;
    private LayoutInflater inflater;
    private String siteUserID;
    private Utility utility;
    private MainActivity mainActivity;

    public JobFeedsAdapter(Context context, List<JobFeeds> feedList)
    {
        this.feedList = feedList;
        this.mContext = context;

        siteUserID = Shared_Preferences_Class.readString(mContext, Shared_Preferences_Class.USER_ID, "");
        utility = new Utility(mContext);

        mainActivity = (MainActivity) mContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       /* View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_contact_row_new, null);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);*/


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_job_feed_row, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        final CustomViewHolder viewHolder = new CustomViewHolder(rootView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final JobFeeds jobFeeds = feedList.get(i);
        // position = i;


            //Download image using picasso library
            Picasso.with(mContext).load(jobFeeds.getUser_image())
                    .into(customViewHolder.img_user);




        //Setting text view title
       customViewHolder.tv_job_title.setText(jobFeeds.getJob_title());
        customViewHolder.tv_job_desc.setText(jobFeeds.getJob_description());
        customViewHolder.tv_posted_date.setText(jobFeeds.getSchedule_date());
        customViewHolder.tv_start_type.setText(jobFeeds.getStart_type());
        customViewHolder.tv_skills.setText(jobFeeds.getJob_skills());
        customViewHolder.tv_user_name.setText(jobFeeds.getPosted_user_firt_name()+" "+ jobFeeds.getPosted_user_last_name());
        customViewHolder.tv_user_country.setText(jobFeeds.getUser_country_name());


        customViewHolder.tv_job_title.setTag(customViewHolder);
        customViewHolder.tv_job_desc.setTag(customViewHolder);
        customViewHolder.tv_posted_date.setTag(customViewHolder);
        customViewHolder.tv_start_type.setTag(customViewHolder);
        customViewHolder.tv_budget.setTag(customViewHolder);
        customViewHolder.tv_skills.setTag(customViewHolder);
        customViewHolder.tv_user_name.setTag(customViewHolder);
        customViewHolder.tv_user_country.setTag(customViewHolder);
        customViewHolder.img_user.setTag(customViewHolder);
        customViewHolder.rating_user.setTag(customViewHolder);
        customViewHolder.lay_job_feed.setTag(customViewHolder);

        customViewHolder.lay_job_feed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                JobFeeds jobFeeds = feedList.get(position);

                /*String str_like_count = jobFeeds.getTokLike();
                String str_is_like = jobFeeds.getIsTokLike();
                String str_tok_id = jobFeeds.getTokId();
                int int_tok_like = Integer.parseInt(str_like_count);*/

                Intent intent = new Intent(mainActivity, JobDetailProposalActivity.class);
                intent.putExtra("jobId",jobFeeds.getJobId());
                mainActivity.startActivity(intent);
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

         TextView tv_job_title,tv_job_desc,tv_posted_date,tv_start_type,tv_budget,tv_skills,
                 tv_user_name,tv_user_country;
        LinearLayout lay_job_feed;
        ImageView img_user;
        RatingBar rating_user;


        public CustomViewHolder(View view)
        {
            super(view);
            this.tv_job_title = (TextView) view.findViewById(R.id.tv_job_title);
            this.tv_job_desc = (TextView) view.findViewById(R.id.tv_job_desc);
            this.tv_posted_date = (TextView) view.findViewById(R.id.tv_posted_date);
            this.tv_start_type = (TextView) view.findViewById(R.id.tv_start_type);
            this.tv_budget = (TextView) view.findViewById(R.id.tv_budget);
            this.tv_skills = (TextView) view.findViewById(R.id.tv_skills);
            this.tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
            this.tv_user_country = (TextView) view.findViewById(R.id.tv_user_country);
            this.img_user = (ImageView) view.findViewById(R.id.img_user);
            this.rating_user= (RatingBar) view.findViewById(R.id.rating_user);
            this.lay_job_feed = (LinearLayout) view.findViewById(R.id.lay_job_feed);

        }
    }

}