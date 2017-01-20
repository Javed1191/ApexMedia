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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apexmediatechnologies.apexmedia.AwardedJobFeedsEmployer;
import com.apexmediatechnologies.apexmedia.ChatActivity;
import com.apexmediatechnologies.apexmedia.EmployerAwardedJobDetailProposalActivity;
import com.apexmediatechnologies.apexmedia.EmployerJobDetailProposalActivity;
import com.apexmediatechnologies.apexmedia.EmployerMainActivity;
import com.apexmediatechnologies.apexmedia.JobFeedsEmployer;
import com.apexmediatechnologies.apexmedia.R;

import java.util.List;

import services.Shared_Preferences_Class;
import services.Utility;


public class AwardedJobFeedsEmployerAdapter extends RecyclerView.Adapter<AwardedJobFeedsEmployerAdapter.CustomViewHolder> {
    private List<AwardedJobFeedsEmployer> feedList;
    private Context mContext;
    private String ischild="";
    private PopupWindow feedMenuPW;
    private View popupView;
    private LayoutInflater inflater;
    private String siteUserID;
    private Utility utility;
    private EmployerMainActivity mainActivity;

    public AwardedJobFeedsEmployerAdapter(Context context, List<AwardedJobFeedsEmployer> feedList)
    {
        this.feedList = feedList;
        this.mContext = context;

        siteUserID = Shared_Preferences_Class.readString(mContext, Shared_Preferences_Class.USER_ID, "");
        utility = new Utility(mContext);

        mainActivity = (EmployerMainActivity) mContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       /* View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_contact_row_new, null);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);*/


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_employer_awarded_jobs, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        final CustomViewHolder viewHolder = new CustomViewHolder(rootView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final AwardedJobFeedsEmployer jobFeeds = feedList.get(i);
        // position = i;

        //Setting text view title
       customViewHolder.tv_job_title.setText(jobFeeds.getJobName());
        customViewHolder.tv_dsec.setText(jobFeeds.getJobDesc());
        customViewHolder.prog_job_progress.setProgress(Integer.parseInt(jobFeeds.getJobProgress()));
        customViewHolder.tv_posted.setText(jobFeeds.getPostDate());
        customViewHolder.tv_progress.setText(jobFeeds.getJobProgress()+"%");

        customViewHolder.tv_visibility.setText(jobFeeds.getVisibility());
        customViewHolder.tv_posting_type.setText(jobFeeds.getPosting_type());



        customViewHolder.tv_job_title.setTag(customViewHolder);
        customViewHolder.tv_visibility.setTag(customViewHolder);
        customViewHolder.tv_posting_type.setTag(customViewHolder);
        customViewHolder.tv_category.setTag(customViewHolder);
        customViewHolder.tv_dsec.setTag(customViewHolder);
        customViewHolder.tv_posted.setTag(customViewHolder);
        customViewHolder.tv_last_modified.setTag(customViewHolder);
        customViewHolder.lay_job_feed.setTag(customViewHolder);
        customViewHolder.tv_dispute.setTag(customViewHolder);
        customViewHolder.prog_job_progress.setTag(customViewHolder);
        customViewHolder.img_chat.setTag(customViewHolder);
        customViewHolder.tv_progress.setTag(customViewHolder);
        customViewHolder.img_payment.setTag(customViewHolder);


        customViewHolder.lay_job_feed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                AwardedJobFeedsEmployer jobFeeds = feedList.get(position);

                Intent intent = new Intent(mainActivity, EmployerAwardedJobDetailProposalActivity.class);
                intent.putExtra("jobId",jobFeeds.getJobId());
                intent.putExtra("proposalId",jobFeeds.getAwardedProposalId());

                mainActivity.startActivity(intent);
            }
        });

        customViewHolder.img_chat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                AwardedJobFeedsEmployer jobFeeds = feedList.get(position);

                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("jobId",jobFeeds.getJobId());
                intent.putExtra("fromUserId",jobFeeds.getEmpId());
                intent.putExtra("project",jobFeeds.getJobName());
                mContext.startActivity(intent);
            }
        });
        customViewHolder.img_payment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                AwardedJobFeedsEmployer jobFeeds = feedList.get(position);

                Intent intent = new Intent(mContext, EmployerMainActivity.class);
                intent.putExtra("menu","Payment");
                mContext.startActivity(intent);
            }
        });

        customViewHolder.tv_dispute.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                AwardedJobFeedsEmployer jobFeeds = feedList.get(position);

                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("dispute","dispute");
                intent.putExtra("jobId",jobFeeds.getJobId());
                intent.putExtra("toUserId",jobFeeds.getEmpId());
                intent.putExtra("project",jobFeeds.getJobName());
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

         TextView tv_job_title,tv_visibility,tv_posting_type,tv_category,tv_dsec,tv_posted,
                 tv_last_modified,tv_dispute,tv_progress;
        LinearLayout lay_job_feed;
        ProgressBar prog_job_progress;
        ImageView img_chat,img_payment;

        public CustomViewHolder(View view)
        {
            super(view);
            this.tv_job_title = (TextView) view.findViewById(R.id.tv_job_title);
            this.tv_visibility = (TextView) view.findViewById(R.id.tv_visibility);
            this.tv_posting_type = (TextView) view.findViewById(R.id.tv_posting_type);
            this.tv_category = (TextView) view.findViewById(R.id.tv_category);
            this.tv_dsec = (TextView) view.findViewById(R.id.tv_dsec);
            this.tv_posted = (TextView) view.findViewById(R.id.tv_posted);
            this.tv_last_modified = (TextView) view.findViewById(R.id.tv_last_modified);
            this.lay_job_feed = (LinearLayout) view.findViewById(R.id.lay_job_feed);
            this.tv_dispute = (TextView) view.findViewById(R.id.tv_dispute);
            this.prog_job_progress = (ProgressBar) view.findViewById(R.id.prog_job_progress);
            this.img_chat = (ImageView) view.findViewById(R.id.img_chat);
            this.tv_progress = (TextView) view.findViewById(R.id.tv_progress);
            this.img_payment = (ImageView) view.findViewById(R.id.img_payment);


        }
    }

}