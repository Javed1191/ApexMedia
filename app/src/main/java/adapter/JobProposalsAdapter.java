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

import com.apexmediatechnologies.apexmedia.JobDetailProposalActivity;
import com.apexmediatechnologies.apexmedia.JobFeeds;
import com.apexmediatechnologies.apexmedia.JobProposals;
import com.apexmediatechnologies.apexmedia.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import services.Shared_Preferences_Class;
import services.Utility;


public class JobProposalsAdapter extends RecyclerView.Adapter<JobProposalsAdapter.CustomViewHolder> {
    private List<JobProposals> feedList;
    private Context mContext;
    private String ischild="";
    private PopupWindow feedMenuPW;
    private View popupView;
    private LayoutInflater inflater;
    private String siteUserID;
    private Utility utility;

    public JobProposalsAdapter(Context context, List<JobProposals> feedList) {
        this.feedList = feedList;
        this.mContext = context;

        siteUserID = Shared_Preferences_Class.readString(mContext, Shared_Preferences_Class.USER_ID, "");
        utility = new Utility(mContext);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       /* View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_contact_row_new, null);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);*/


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_job_proposals_row, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        final CustomViewHolder viewHolder = new CustomViewHolder(rootView);


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final JobProposals jobFeeds = feedList.get(i);
        // position = i;


            //Download image using picasso library
            Picasso.with(mContext).load(jobFeeds.getUserImage())
                    .into(customViewHolder.img_user);




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
                 tv_user_name;
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


        }
    }

}